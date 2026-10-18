package nl.townsandkingdoms.resonantarcana;

import java.util.UUID;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

/** Containment, interruption, projectile filtering and exact protected detonation. */
final class AtomicChecks {
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        var p=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("dbf70f0c-13f7-4c70-a0f7-27096bde0827"),"AtomicTest"));
        p.setPos(0,81,0);p.setYRot(0);p.setXRot(0);p.removeAllEffects();
        var data=MagicData.getPlayerMagicData(p);var spell=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.I_AM_ATOMIC));
        Zombie enemy=enemy(level,0,8),friend=enemy(level,1,8),hidden=enemy(level,0,12),resistant=enemy(level,8,6);
        resistant.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        var party=level.getScoreboard().addPlayerTeam("atomic_party");party.setAllowFriendlyFire(false);level.getScoreboard().addPlayerToTeam(p.getScoreboardName(),party);level.getScoreboard().addPlayerToTeam(friend.getScoreboardName(),party);
        var fastShot=new Arrow(EntityType.ARROW,level);var hostileShot=new Arrow(EntityType.ARROW,level);var friendlyShot=new Arrow(EntityType.ARROW,level);
        try{
            SpellEngine.clear();data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();data.setMana(1000);
            data.initiateCast(spell,1,270,CastSource.SPELLBOOK,"mainhand");spell.onServerPreCast(level,1,p,data);
            var state=AtomicRitual.state(p);check.accept(state!=null&&state.prisoners.containsKey(enemy.getUUID())&&!state.prisoners.containsKey(friend.getUUID()),"Atomic captures enemies and excludes allies");
            var focus=state.context.centrepieces.getFirst();check.accept(focus instanceof SpellCentrepieces.HolySword&&!focus.isPickable(),"Atomic summons a real untargetable sword focus");
            enemy.setPos(0,81,17);resistant.setPos(17,81,0);
            hostileShot.setOwner(enemy);hostileShot.setPos(0,82,15.8);hostileShot.setDeltaMovement(0,0,1);level.addFreshEntity(hostileShot);
            friendlyShot.setOwner(p);friendlyShot.setPos(0,82,15.8);friendlyShot.setDeltaMovement(0,0,1);level.addFreshEntity(friendlyShot);
            fastShot.setOwner(enemy);fastShot.setPos(0,82,15.7);level.addFreshEntity(fastShot);fastShot.xo=0;fastShot.yo=82;fastShot.zo=19.5;fastShot.tickCount=1;
            AtomicRitual.charge(p,.4,108);
            check.accept(enemy.getZ()<16&&enemy.getDeltaMovement().z<0,"Atomic returns a captured enemy across its boundary");
            check.accept(resistant.getX()==17,"Atomic does not force fully knockback-resistant enemies");
            check.accept(fastShot.isRemoved(),"Atomic catches a fast projectile that crossed the boundary since its previous position");
            check.accept(hostileShot.isRemoved()&&!friendlyShot.isRemoved(),"Atomic absorbs hostile boundary-crossing projectiles and preserves friendly shots");
            check.accept(p.hasEffect(MobEffects.DAMAGE_RESISTANCE)&&friend.hasEffect(MobEffects.DAMAGE_RESISTANCE),"Atomic shelters the caster and allies during compression");
            p.setPos(4,81,0);AtomicRitual.charge(p,.5,135);
            check.accept(!data.isCasting()&&focus.isRemoved()&&SpellEngine.activeCount()==0,"leaving Atomic's anchor interrupts the ritual and removes its focus");
            enemy.setPos(0,81,17);AtomicRitual.charge(p,.8,200);check.accept(enemy.getZ()==17&&enemy.getHealth()==1024,"interrupted Atomic releases prisoners and cannot deal damage");
            p.setPos(0,81,0);enemy.setPos(0,81,8);resistant.setPos(8,81,6);p.removeAllEffects();data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();data.setMana(1000);
            data.initiateCast(spell,1,270,CastSource.SPELLBOOK,"mainhand");spell.onServerPreCast(level,1,p,data);
            for(int x=-8;x<=8;x++)for(int y=81;y<=90;y++)level.setBlockAndUpdate(new BlockPos(x,y,10),Blocks.STONE.defaultBlockState());
            check.accept(spell.getEffectiveCastTime(1,p)==270&&RecordedSpells.chargeEvent(Spell.I_AM_ATOMIC)!=null,"Atomic has a 13.5-second original-pitch windup");
            spell.castSpell(level,1,p,CastSource.SPELLBOOK,true);SpellEngine.tick();
            check.accept(enemy.getHealth()==512&&resistant.getHealth()==512,"Atomic releases exactly one 512-damage hit on its first release tick");
            check.accept(friend.getHealth()==1024&&hidden.getHealth()==1024,"Atomic protects allies and enemies behind solid cover");
            for(int i=1;i<90;i++){enemy.invulnerableTime=0;resistant.invulnerableTime=0;SpellEngine.tick();}
            check.accept(enemy.getHealth()==512&&SpellEngine.activeCount()==0,"Atomic shock fronts do not repeat damage and clean up");
            check.accept(level.getBlockState(new BlockPos(0,81,10)).is(Blocks.STONE),"Atomic does not destroy terrain");
        }finally{
            AtomicRitual.end(p,data);data.resetCastingState();SpellEngine.clear();p.removeAllEffects();
            for(int x=-8;x<=8;x++)for(int y=81;y<=90;y++)level.setBlockAndUpdate(new BlockPos(x,y,10),Blocks.AIR.defaultBlockState());
            for(var v:java.util.List.of(enemy,friend,hidden,resistant))v.discard();hostileShot.discard();friendlyShot.discard();fastShot.discard();level.getScoreboard().removePlayerTeam(party);
        }
        cosmicBeam(level,p,check);
        check.accept(io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.ARCANE_DEVASTATOR)).getEffectiveCastTime(1,p)==130,"cosmic beam waits for the later 6.5-second audio attack");
        check.accept(!RecordedSpells.handles(Spell.DOOM)&&RecordedSpells.handles(Spell.DOOM_BEAM),"laser recording belongs to Doom Beam, not black-hole Doom");
        check.accept(io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.DOOM)).getCastStartSound().isEmpty(),"Doom uses only its staggered native ambience, without an automatic start loop");
        doomBeam(level,p,check);
    }
    private static void cosmicBeam(ServerLevel level,net.minecraft.server.level.ServerPlayer p,BiConsumer<Boolean,String> check){
        p.setPos(0,81,0);p.setYRot(0);p.setXRot(0);SpellEngine.clear();
        Zombie first=enemy(level,0,6),late=enemy(level,8,8),covered=enemy(level,8,12);
        try{
            check.accept(SpellEngine.cast(p,Spell.ARCANE_DEVASTATOR),"cosmic beam releases");SpellEngine.tick();
            check.accept(first.getHealth()==904&&late.getHealth()==1024,"cosmic beam initially hits only its path");
            late.setPos(0,81,8);for(int i=0;i<10;i++){first.invulnerableTime=0;late.invulnerableTime=0;SpellEngine.tick();}
            check.accept(first.getHealth()==904&&late.getHealth()==904,"sustained cosmic beam catches a new target without repeating a hit");
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,4),Blocks.STONE.defaultBlockState());covered.setPos(0,81,12);
            for(int i=0;i<200;i++)SpellEngine.tick();
            check.accept(covered.getHealth()==1024&&SpellEngine.activeCount()==0,"new cover clips the sustained beam and its release expires");
        }finally{
            first.discard();late.discard();covered.discard();SpellEngine.clear();
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,4),Blocks.AIR.defaultBlockState());
        }
    }
    private static void doomBeam(ServerLevel level,net.minecraft.server.level.ServerPlayer p,BiConsumer<Boolean,String> check){
        p.setPos(0,81,0);p.setYRot(0);p.setXRot(0);SpellEngine.clear();
        Zombie first=enemy(level,0,6),late=enemy(level,8,8);
        try{
            check.accept(Spell.DOOM_BEAM.castTicks()==260,"Doom Beam has a thirteen-second native charge");
            check.accept(SpellEngine.cast(p,Spell.DOOM_BEAM),"Doom Beam releases");SpellEngine.tick();
            check.accept(first.getHealth()==704&&late.getHealth()==1024,"Doom Beam deals 320 damage only along its path");
            late.setPos(0,81,8);
            for(int i=0;i<12;i++){first.invulnerableTime=0;late.invulnerableTime=0;SpellEngine.tick();}
            check.accept(first.getHealth()==704&&late.getHealth()==704,"Doom Beam catches late entrants without repeating hits");
            var followed=enemy(level,16,0);var abandoned=enemy(level,0,14);
            try{
                p.setPos(10,81,0);p.setYRot(-90);SpellEngine.tick();
                check.accept(followed.getHealth()==704&&abandoned.getHealth()==1024,"Doom Beam moves with its caster and turns its damage path away from the old ray");
                check.accept(BeamRitual.origin(p).distanceToSqr(p.getEyePosition().add(0,-.25,0).add(p.getLookAngle().scale(.9)))<.0001,"Doom Beam remains connected to the caster's hands");
            }finally{followed.discard();abandoned.discard();p.setPos(0,81,0);p.setYRot(0);}
            for(int x=-3;x<=3;x++)for(int y=81;y<=86;y++)level.setBlockAndUpdate(new BlockPos(x,y,4),Blocks.STONE.defaultBlockState());
            var hidden=enemy(level,0,14);
            try{for(int i=0;i<200;i++)SpellEngine.tick();check.accept(hidden.getHealth()==1024&&SpellEngine.activeCount()==0,"Doom Beam respects new cover and expires");}finally{hidden.discard();}
        }finally{
            first.discard();late.discard();SpellEngine.clear();
            for(int x=-3;x<=3;x++)for(int y=81;y<=86;y++)level.setBlockAndUpdate(new BlockPos(x,y,4),Blocks.AIR.defaultBlockState());
        }
    }
    private static Zombie enemy(ServerLevel level,double x,double z){var v=new Zombie(EntityType.ZOMBIE,level);v.setPos(x,81,z);v.setNoAi(true);v.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);v.getAttribute(Attributes.ARMOR).setBaseValue(0);v.setHealth(1024);level.addFreshEntity(v);return v;}
}
