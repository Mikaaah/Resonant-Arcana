package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;

/** Actual combat events and native recasts, not just direct helper calls. */
final class AdvancedChecks {
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        var p=new MovementPlayer(level,new GameProfile(UUID.fromString("d326e45e-e759-47df-a224-52c0a43e74a5"),"AdvancedSpellTest"));
        var difficulty=level.getDifficulty();level.getServer().setDifficulty(net.minecraft.world.Difficulty.NORMAL,true);
        p.setGameMode(GameType.SURVIVAL);var attack=p.getAttribute(Attributes.ATTACK_DAMAGE);double old=attack.getBaseValue();attack.setBaseValue(10);
        try{
            for(int rank=1;rank<=5;rank++){
                reset(p);var target=enemy(level,0,6);float power=Spell.RIFT_DRAW.power(rank);
                cast(p,Spell.RIFT_DRAW,rank,check);advance(11,target);check.accept(target.getHealth()==200,"rift draw waits for warning: "+rank);advance(4,target);
                check.accept(close(200-target.getHealth(),(16+5)*power),"rift draw weapon and rank damage: "+rank);target.discard();
                reset(p);target=enemy(level,0,4);cast(p,Spell.CRIMSON_REVERSAL,rank,check);
                check.accept(p.getZ()>6&&close(200-target.getHealth(),(10+5)*power),"reversal dash cuts its path once: "+rank+" z="+p.getZ()+" hp="+target.getHealth()+" atk="+p.getAttributeValue(Attributes.ATTACK_DAMAGE));target.discard();
                reset(p);target=enemy(level,0,2);cast(p,Spell.ASHEN_REBIRTH,rank,check);advance(15,target);
                check.accept(p.getZ()>6&&target.getHealth()==200,"rebirth moves but leaves delayed damage: "+rank);advance(2,target);
                check.accept(close(200-target.getHealth(),18*power),"rebirth detonation scales: "+rank);target.discard();
                reset(p);p.setXRot(60);p.setHealth(5);cast(p,Spell.LAST_STAND,rank,check);
                check.accept(close(p.getAbsorptionAmount(),4*power)&&p.hasEffect(MobEffects.DAMAGE_RESISTANCE),"last stand grants scaled shield and resistance: "+rank);
                BlockPos banner=findBanner(level,p.blockPosition());check.accept(banner!=null,"last stand plants a temporary yellow banner: "+rank);
                p.setAbsorptionAmount(1);advance(30);check.accept(close(p.getAbsorptionAmount(),1),"banner does not refill spent shield: "+rank);advance(91);
                check.accept(close(p.getHealth(),5+6*power),"last stand final heal scales: "+rank);
                if(banner!=null)check.accept(level.getBlockState(banner).isAir(),"last stand removes its temporary banner on expiry: "+rank);
                reset(p);target=enemy(level,0,3);cast(p,Spell.GLACIAL_COUNTER,rank,check);advance(3,target);p.hurt(level.damageSources().mobAttack(target),5);
                check.accept(p.getHealth()==20&&close(200-target.getHealth(),12*power*1.75f),"perfect guard blocks and counters: "+rank+" player="+p.getHealth()+" target="+target.getHealth()+" guard="+p.hasEffect(ArcanaEffects.GLACIAL_GUARD));target.discard();
                reset(p);target=enemy(level,0,3);cast(p,Spell.GLACIAL_COUNTER,rank,check);advance(8,target);p.hurt(level.damageSources().mobAttack(target),5);
                check.accept(close(p.getHealth(),18.5f)&&close(200-target.getHealth(),12*power),"late guard mitigates and counters: "+rank);target.discard();
                reset(p);target=enemy(level,0,3);var secondary=enemy(level,3,3);cast(p,Spell.THUNDERBRAND,rank,check);
                for(int i=0;i<2;i++){target.invulnerableTime=0;target.hurt(level.damageSources().playerAttack(p),2);}
                check.accept(close(target.getHealth(),196)&&secondary.getHealth()==200,"brand waits for third melee hit: "+rank);
                target.invulnerableTime=0;target.hurt(level.damageSources().playerAttack(p),2);
                check.accept(close(200-target.getHealth(),6+8*power)&&close(200-secondary.getHealth(),8*power),"brand empowers third hit and chains once: "+rank);
                target.discard();secondary.discard();
                reset(p);target=enemy(level,0,3);cast(p,Spell.PHANTOM_FORMATION,rank,check);target.hurt(level.damageSources().playerAttack(p),2);advance(10,target);
                check.accept(close(target.getHealth(),198),"formation has an arming flight: "+rank);advance(35,target);
                check.accept(close(200-target.getHealth(),2+8*power),"one melee hit launches one nonrecursive blade: "+rank);target.discard();
                reset(p);target=enemy(level,0,5);cast(p,Spell.FUNERAL_BELL,rank,check);advance(65,target);
                check.accept(close(200-target.getHealth(),40*power),"funeral bell has two normal and one heavy hit: "+rank);target.discard();
                reset(p);target=enemy(level,0,4);var behind=enemy(level,0,-4);
                var arrow=new Arrow(EntityType.ARROW,level);arrow.setOwner(target);arrow.setPos(0,82,2);level.addFreshEntity(arrow);
                var owned=new Arrow(EntityType.ARROW,level);owned.setOwner(p);owned.setPos(1,82,2);level.addFreshEntity(owned);
                cast(p,Spell.SPELLBREAKER,rank,check);
                check.accept(arrow.isRemoved()&&!owned.isRemoved()&&behind.getHealth()==200&&close(200-target.getHealth(),(6+5)*power),"breaker handles hostile projectiles and forward weapon sweep: "+rank);
                target.discard();behind.discard();owned.discard();
                if(BossesRiseCompat.available()){
                    reset(p);target=enemy(level,0,6);var ring=enemy(level,3,6);cast(p,Spell.KRAKENS_WRATH,rank,check);advance(46,target,ring);
                    check.accept(target.getHealth()==200&&ring.getHealth()==200,"wrath has no premature damage: "+rank);advance(105,target,ring);
                    check.accept(close(200-target.getHealth(),36*power)&&close(200-ring.getHealth(),48*power),"wrath outer hits bounded and centre triples: "+rank);target.discard();ring.discard();
                }
                SpellEngine.clear();check.accept(SpellEngine.activeCount()==0,"advanced spell cleanup: "+rank);
            }
            nativeReversal(level,p,check);edgeCases(level,p,check);interruption(level,p,check);protection(level,p,check);
        }finally{level.getServer().setDifficulty(difficulty,true);SpellEngine.clear();attack.setBaseValue(old);p.removeAllEffects();MagicData.getPlayerMagicData(p).getPlayerRecasts().removeAll(io.redspace.ironsspellbooks.capabilities.magic.RecastResult.COMMAND);}
    }
    private static void nativeReversal(ServerLevel level,FakePlayer p,BiConsumer<Boolean,String> check){
        reset(p);var data=MagicData.getPlayerMagicData(p);if(data.getSyncedData()==null)data.setSyncedData(new io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData(p));
        var spell=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.CRIMSON_REVERSAL));data.getPlayerCooldowns().clearCooldowns();data.setMana(100);
        spell.castSpell(level,1,p,CastSource.SPELLBOOK,true);
        check.accept(p.getZ()>6&&data.getMana()==65&&data.getPlayerRecasts().hasRecastForSpell(spell)&&!data.getPlayerCooldowns().isOnCooldown(spell),"native reversal charges once and opens return instead of cooldown z="+p.getZ()+" mana="+data.getMana()+" recast="+data.getPlayerRecasts().hasRecastForSpell(spell)+" cooldown="+data.getPlayerCooldowns().isOnCooldown(spell));
        var anchor=(ReversalData)data.getPlayerRecasts().getRecastInstance(spell.getSpellId()).getCastData();
        var saved=anchor.serializeNBT(level.registryAccess());var copy=new ReversalData();copy.deserializeNBT(level.registryAccess(),saved);
        check.accept(copy.safe(p)&&copy.point.equals(Vec3.ZERO.add(0,81,0)),"native return anchor survives serialization");
        data.setMana(0);check.accept(spell.canBeCastedBy(1,CastSource.SPELLBOOK,data,p).isSuccess()&&spell.getEffectiveCastTime(1,p)==0,"return works without mana and without second windup");
        check.accept(spell.attemptInitiateCast(net.minecraft.world.item.ItemStack.EMPTY,1,level,p,CastSource.SPELLBOOK,true,"mainhand"),"return enters through the real native cast request");
        check.accept(p.getZ()==0&&data.getMana()==0&&!data.getPlayerRecasts().hasRecastForSpell(spell)&&data.getPlayerCooldowns().isOnCooldown(spell),"native return is free and starts normal cooldown");
        reset(p);data.getPlayerCooldowns().clearCooldowns();data.setMana(100);spell.castSpell(level,1,p,CastSource.SPELLBOOK,true);
        data.getPlayerRecasts().tick(61);check.accept(!data.getPlayerRecasts().hasRecastForSpell(spell)&&data.getPlayerCooldowns().isOnCooldown(spell)&&p.getZ()>6,"unused return expires and starts cooldown without teleporting");
        data.getPlayerCooldowns().clearCooldowns();data.resetCastingState();
    }
    private static void edgeCases(ServerLevel level,FakePlayer p,BiConsumer<Boolean,String> check){
        reset(p);var target=enemy(level,0,3);var neighbor=enemy(level,3,3);cast(p,Spell.THUNDERBRAND,1,check);
        for(int i=0;i<2;i++){target.invulnerableTime=0;target.hurt(level.damageSources().playerAttack(p),2);}
        target.setHealth(1);target.invulnerableTime=0;target.hurt(level.damageSources().playerAttack(p),2);
        check.accept(!target.isAlive()&&close(neighbor.getHealth(),192),"brand discharge counts a killing blow");target.discard();
        target=enemy(level,0,3);target.hurt(level.damageSources().playerAttack(p),2);
        check.accept(close(target.getHealth(),198)&&close(neighbor.getHealth(),192),"killing discharge resets charges instead of granting permanent empowered hits");target.discard();neighbor.discard();
        reset(p);target=enemy(level,0,3);neighbor=enemy(level,3,3);cast(p,Spell.THUNDERBRAND,1,check);
        for(int i=0;i<2;i++){target.invulnerableTime=0;target.hurt(level.damageSources().indirectMagic(p,p),1);}
        target.invulnerableTime=0;target.hurt(level.damageSources().playerAttack(p),2);
        check.accept(close(target.getHealth(),196)&&neighbor.getHealth()==200,"spell damage cannot charge thunderbrand");target.discard();neighbor.discard();
        reset(p);target=enemy(level,0,3);cast(p,Spell.PHANTOM_FORMATION,1,check);
        for(int i=0;i<5;i++){target.invulnerableTime=0;target.hurt(level.damageSources().playerAttack(p),2);advance(35,target);}
        check.accept(close(target.getHealth(),158),"formation has exactly four level-I follow-up swords");target.discard();
        reset(p);target=enemy(level,0,-3);cast(p,Spell.GLACIAL_COUNTER,1,check);
        p.hurt(level.damageSources().generic(),2);p.invulnerableTime=0;p.hurt(level.damageSources().mobAttack(target),2);
        check.accept(close(p.getHealth(),16)&&target.getHealth()==200&&p.hasEffect(ArcanaEffects.GLACIAL_GUARD),"guard cannot block environmental or rear damage");advance(20);check.accept(!p.hasEffect(ArcanaEffects.GLACIAL_GUARD),"untriggered guard expires");target.discard();
    }
    private static void interruption(ServerLevel level,FakePlayer p,BiConsumer<Boolean,String> check){
        reset(p);var target=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("9b5f2508-dd45-48c5-8735-e2966f52e5b6"),"InterruptTarget"));target.setPos(0,81,4);target.setGameMode(GameType.SURVIVAL);target.setHealth(20);
        var data=MagicData.getPlayerMagicData(target);if(data.getSyncedData()==null)data.setSyncedData(new io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData(target));
        var longSpell=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.SMITE));data.initiateCast(longSpell,1,40,CastSource.SPELLBOOK,"mainhand");
        check.accept(AdvancedSpells.interrupt(p,target)&&!data.isCasting(),"spellbreaker uses real native cancellation");
        var instant=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.SPARK));data.initiateCast(instant,1,40,CastSource.SPELLBOOK,"mainhand");
        check.accept(!AdvancedSpells.interrupt(p,target)&&data.isCasting(),"spellbreaker respects uninterruptible cast types");data.resetCastingState();
        var mage=io.redspace.ironsspellbooks.registries.EntityRegistry.PYROMANCER.get().create(level);mage.setNoAi(true);mage.setPos(0,81,4);mage.setTarget(p);level.addFreshEntity(mage);
        mage.initiateCastSpell(SpellRegistry.FIREBALL_SPELL.get(),1);check.accept(mage.isCasting(),"real Iron's mage begins a native cast");
        check.accept(AdvancedSpells.interrupt(p,mage)&&!mage.isCasting(),"spellbreaker interrupts a real Iron's mage");mage.discard();
    }
    private static void protection(ServerLevel level,FakePlayer p,BiConsumer<Boolean,String> check){
        reset(p);var party=level.getScoreboard().addPlayerTeam("advanced_party");party.setAllowFriendlyFire(false);level.getScoreboard().addPlayerToTeam(p.getScoreboardName(),party);
        try{
            for(var spell:List.of(Spell.RIFT_DRAW,Spell.CRIMSON_REVERSAL,Spell.ASHEN_REBIRTH,Spell.FUNERAL_BELL,Spell.SPELLBREAKER)){
                reset(p);var ally=enemy(level,0,4);level.getScoreboard().addPlayerToTeam(ally.getScoreboardName(),party);
                cast(p,spell,1,check);advance(160,ally);check.accept(ally.getHealth()==200&&ally.getActiveEffects().isEmpty(),"advanced offense protects team companions: "+spell);level.getScoreboard().removePlayerFromTeam(ally.getScoreboardName(),party);ally.discard();
            }
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.STONE.defaultBlockState());
            for(var spell:List.of(Spell.RIFT_DRAW,Spell.CRIMSON_REVERSAL,Spell.ASHEN_REBIRTH,Spell.FUNERAL_BELL,Spell.SPELLBREAKER)){
                reset(p);var hidden=enemy(level,0,5);SpellEngine.cast(p,spell);advance(160,hidden);check.accept(hidden.getHealth()==200,"walls block advanced offense: "+spell);hidden.discard();
            }
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.AIR.defaultBlockState());
        }finally{level.getScoreboard().removePlayerTeam(party);}
    }
    private static void reset(FakePlayer p){SpellEngine.clear();p.setPos(0,81,0);p.setYRot(0);p.setXRot(0);p.setHealth(20);p.invulnerableTime=0;p.removeAllEffects();p.setAbsorptionAmount(0);p.setDeltaMovement(Vec3.ZERO);p.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,net.minecraft.world.item.ItemStack.EMPTY);}
    private static BlockPos findBanner(ServerLevel level,BlockPos origin){
        for(int x=-8;x<=8;x++)for(int y=-2;y<=2;y++)for(int z=-8;z<=8;z++){BlockPos p=origin.offset(x,y,z);if(level.getBlockState(p).is(Blocks.YELLOW_BANNER))return p.immutable();}
        return null;
    }
    private static void cast(FakePlayer p,Spell s,int rank,BiConsumer<Boolean,String> check){check.accept(SpellEngine.castFromSpellbook(p,s,s.power(rank),p.serverLevel().damageSources().indirectMagic(p,p),s.cooldown,true),"advanced cast starts: "+s+" / "+rank);}
    private static void advance(int ticks,Zombie... enemies){for(int i=0;i<ticks;i++){for(var e:enemies)e.invulnerableTime=0;SpellEngine.tick();}}
    private static Zombie enemy(ServerLevel level,double x,double z){var e=new Zombie(EntityType.ZOMBIE,level);e.setNoAi(true);e.getAttribute(Attributes.MAX_HEALTH).setBaseValue(200);e.getAttribute(Attributes.ARMOR).setBaseValue(0);e.setHealth(200);e.setPos(x,81,z);level.addFreshEntity(e);return e;}
    // Forge's fake connection deliberately ignores teleports; record the packet's intended movement.
    private static final class MovementPlayer extends FakePlayer {
        MovementPlayer(ServerLevel level,GameProfile profile){
            super(level,profile);
            // FakePlayer.tick is a no-op, so its initial spawn immunity otherwise never expires.
            try{var immunity=net.minecraft.server.level.ServerPlayer.class.getDeclaredField("spawnInvulnerableTime");immunity.setAccessible(true);immunity.setInt(this,0);}catch(ReflectiveOperationException e){throw new IllegalStateException(e);}
        }
        @Override public void teleportTo(double x,double y,double z){setPos(x,y,z);}
        @Override public boolean isInvulnerableTo(net.minecraft.world.damagesource.DamageSource source){return false;}
        @Override public boolean canHarmPlayer(net.minecraft.world.entity.player.Player other){return getTeam()==null||getTeam()!=other.getTeam()||getTeam().isAllowFriendlyFire();}
    }
    private static boolean close(float a,float b){return Math.abs(a-b)<.02;}
}
