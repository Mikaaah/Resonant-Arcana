package nl.townsandkingdoms.resonantarcana;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import com.mojang.authlib.GameProfile;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

/** Real transfers, persisted returns, damage ordering, native windup and PvP protection. */
final class VoidExileChecks {
    static void run(ServerLevel home,BiConsumer<Boolean,String> check){
        var pocket=home.getServer().getLevel(VoidExile.DIMENSION);check.accept(pocket!=null,"Void Exile dimension exists");
        var caster=new CombatPlayer(home,new GameProfile(UUID.fromString("c90e46f4-6c73-4e7b-8eb7-83401a8ab052"),"ExileTest"));
        caster.setGameMode(GameType.SURVIVAL);caster.setPos(0,81,0);caster.setYRot(0);caster.setXRot(0);SpellEngine.clear();
        Zombie victim=target(home);victim.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,new ItemStack(Items.STICK,3));UUID id=victim.getUUID();
        boolean[] sawDamage={false},ordered={false};Consumer<LivingIncomingDamageEvent> listener=e->{if(e.getEntity().getUUID().equals(id)){sawDamage[0]=true;ordered[0]=!e.getEntity().hasEffect(MobEffects.WEAKNESS);}};
        NeoForge.EVENT_BUS.addListener(listener);
        try{
            check.accept(VoidExile.eligible(caster,victim)&&SpellEngine.cast(caster,Spell.VOID_EXILE),"Void Exile accepts and transfers hostile target");
            var held=VoidExile.state(home.getServer()).live.get(id);
            check.accept((home.getEntity(id)==null||home.getEntity(id).isRemoved())&&held instanceof Zombie&&held.level()==pocket,"exile removes target from battlefield into real void dimension");
            ((Zombie)held).checkDespawn();check.accept(held.isAlive(),"exiled mob cannot despawn without nearby players");
            check.accept(held.getHealth()==1024&&!held.hasEffect(MobEffects.WEAKNESS)&&held.isInvulnerable()&&held.isNoGravity()&&((Zombie)held).isNoAi(),"exile pauses combat without early damage or weakness");
            var data=VoidExile.state(home.getServer());CompoundTag saved=data.save(new CompoundTag(),home.registryAccess());var restored=VoidExile.State.load(saved,home.registryAccess());
            check.accept(restored.entries.size()==1&&restored.clock==data.clock,"return record and timer survive saved-data serialization");
            CompoundTag mob=new CompoundTag();held.save(mob);held.discard();home.getDataStorage().set(VoidExile.STORAGE,restored);
            var reloaded=EntityType.loadEntityRecursive(mob,pocket,java.util.function.Function.identity());pocket.addFreshEntity(reloaded);
            check.accept(restored.live.containsKey(id),"entity reload reconnects durable return before chunk entity lookup");
            SpellEngine.clear();for(int i=0;i<199;i++)SpellEngine.tick();
            check.accept((home.getEntity(id)==null||home.getEntity(id).isRemoved())&&restored.live.containsKey(id)&&!sawDamage[0],"target stays exiled for full ten seconds independently of active casts");
            home.setBlock(new BlockPos(0,81,6),Blocks.STONE.defaultBlockState(),3);home.setBlock(new BlockPos(0,82,6),Blocks.STONE.defaultBlockState(),3);SpellEngine.tick();
            Zombie returned=(Zombie)home.getEntity(id);
            check.accept(returned!=null&&(pocket.getEntity(id)==null||pocket.getEntity(id).isRemoved())&&restored.entries.isEmpty(),"target returns once at ten seconds and clears durable record");
            check.accept(returned.getUUID().equals(id)&&returned.getMainHandItem().is(Items.STICK)&&returned.getMainHandItem().getCount()==3,"exile preserves entity identity and equipment");
            check.accept(returned.getHealth()==1004&&sawDamage[0]&&ordered[0],"20 damage is applied after return and before weakness");
            check.accept(returned.hasEffect(MobEffects.WEAKNESS)&&returned.getEffect(MobEffects.WEAKNESS).getAmplifier()==1&&returned.getEffect(MobEffects.WEAKNESS).getDuration()==100,"return applies Weakness II for five seconds");
            check.accept(!returned.isInvulnerable()&&!returned.isNoGravity()&&!returned.isNoAi(),"original gravity AI and vulnerability are restored");
            check.accept(home.noCollision(returned)&&returned.getY()>=83,"blocked return point uses nearby clear space without changing blocks");
            for(int i=0;i<40;i++)SpellEngine.tick();check.accept(returned.getHealth()==1004,"return damage cannot repeat");returned.discard();
        }finally{
            NeoForge.EVENT_BUS.unregister(listener);for(int i=0;i<205&&!VoidExile.state(home.getServer()).entries.isEmpty();i++)SpellEngine.tick();var remaining=home.getEntity(id);if(remaining!=null)remaining.discard();
            home.setBlock(new BlockPos(0,81,6),Blocks.AIR.defaultBlockState(),3);home.setBlock(new BlockPos(0,82,6),Blocks.AIR.defaultBlockState(),3);SpellEngine.clear();
        }
        var wither=EntityType.WITHER.create(home);check.accept(!VoidExile.eligible(caster,wither),"bosses resist exile");wither.discard();
        nativeCasting(home,caster,check);pvp(home,caster,check);
    }
    private static Zombie target(ServerLevel level){Zombie z=new Zombie(EntityType.ZOMBIE,level);z.setPos(0,81,6);z.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);z.getAttribute(Attributes.ARMOR).setBaseValue(0);z.setHealth(1024);level.addFreshEntity(z);return z;}
    private static void nativeCasting(ServerLevel home,FakePlayer caster,BiConsumer<Boolean,String> check){
        home.addNewPlayer(caster);caster.setPos(0,81,0);caster.setYRot(0);caster.setXRot(0);
        caster.getAttribute(AttributeRegistry.MAX_MANA).setBaseValue(1000);caster.getAttribute(AttributeRegistry.MANA_REGEN).setBaseValue(0);
        var spell=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.VOID_EXILE));var data=MagicData.getPlayerMagicData(caster);
        if(data.getSyncedData()==null)data.setSyncedData(new io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData(caster));
        var manager=(io.redspace.ironsspellbooks.capabilities.magic.MagicManager)io.redspace.ironsspellbooks.api.magic.MagicHelper.MAGIC_MANAGER;
        var victim=target(home);UUID id=victim.getUUID();
        try{
            data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();data.setMana(1000);
            check.accept(spell.getEffectiveCastTime(1,caster)==80,"native Void Exile cast time is four seconds");
            check.accept(spell.attemptInitiateCast(ItemStack.EMPTY,1,home,caster,CastSource.SPELLBOOK,false,"test"),"native Void Exile charge starts");
            for(int i=0;i<40;i++){manager.tick(home);SpellEngine.tick();}IronsCompat.cancel(caster);for(int i=0;i<100;i++){manager.tick(home);SpellEngine.tick();}
            check.accept(victim.getHealth()==1024&&data.getMana()==1000&&VoidExile.state(home.getServer()).entries.isEmpty(),"cancelled exile spends no mana and leaves target present");
            data.getPlayerCooldowns().clearCooldowns();check.accept(spell.attemptInitiateCast(ItemStack.EMPTY,1,home,caster,CastSource.SPELLBOOK,false,"test"),"exile can be recast after interruption");
            for(int i=0;i<80;i++){manager.tick(home);SpellEngine.tick();}
            check.accept(home.getEntity(id)==victim&&data.getMana()==1000,"target and mana remain untouched throughout windup");manager.tick(home);SpellEngine.tick();
            check.accept((home.getEntity(id)==null||home.getEntity(id).isRemoved())&&data.getMana()==1000-spell.getManaCost(1),"native cast exiles on release and spends mana once");
            for(int i=0;i<201;i++)SpellEngine.tick();check.accept(home.getEntity(id) instanceof Zombie returned&&returned.getHealth()<1024,"native spellbook cast completes exile and return damage");
        }finally{
            for(int i=0;i<205&&!VoidExile.state(home.getServer()).entries.isEmpty();i++)SpellEngine.tick();var remaining=home.getEntity(id);if(remaining!=null)remaining.discard();data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();SpellEngine.clear();
        }
    }
    private static void pvp(ServerLevel home,FakePlayer caster,BiConsumer<Boolean,String> check){
        var target=new CombatPlayer(home,new GameProfile(UUID.fromString("e1dfa0a0-ab6e-4f6a-8a8d-1f00f26be265"),"ExilePvpTest"));home.addNewPlayer(target);target.setGameMode(GameType.SURVIVAL);target.setPos(0,81,6);target.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100);target.setHealth(100);
        boolean old=ArcanaConfig.PVP.get();
        try{
            ArcanaConfig.PVP.set(false);check.accept(!VoidExile.eligible(caster,target),"PvP-disabled players resist exile");ArcanaConfig.PVP.set(true);SpellEngine.clear();
            check.accept(SpellEngine.cast(caster,Spell.VOID_EXILE)&&target.serverLevel().dimension().equals(VoidExile.DIMENSION),"PvP-enabled player transfers to isolated void");
            for(int i=0;i<200;i++)SpellEngine.tick();
            check.accept(target.serverLevel()==home&&target.position().distanceToSqr(new net.minecraft.world.phys.Vec3(0,81,6))<.01&&target.getHealth()<100&&target.hasEffect(MobEffects.WEAKNESS)&&!target.isInvulnerable()&&!target.isNoGravity(),"player returns to origin with damage then weakness and original flags");
        }finally{
            for(int i=0;i<205&&!VoidExile.state(home.getServer()).entries.isEmpty();i++)SpellEngine.tick();ArcanaConfig.PVP.set(old);target.discard();caster.discard();SpellEngine.clear();
        }
    }
    // Ordinary fake players are permanently immune and cannot harm players. Use combat semantics
    // while retaining FakePlayer's socket-free connection; its no-op tick cannot age spawn immunity.
    private static final class CombatPlayer extends FakePlayer {
        CombatPlayer(ServerLevel level,GameProfile profile){
            super(level,profile);
            try{
                var field=net.minecraft.server.level.ServerPlayer.class.getDeclaredField("spawnInvulnerableTime");field.setAccessible(true);field.setInt(this,0);
                // Dimension transfer asks NeoForge which payload channels were negotiated. Its
                // stock fake connection has no channel; an in-memory channel answers that check.
                var channel=net.minecraft.network.Connection.class.getDeclaredField("channel");channel.setAccessible(true);
                if(channel.get(connection.getConnection())==null)channel.set(connection.getConnection(),new io.netty.channel.embedded.EmbeddedChannel());
            }catch(ReflectiveOperationException e){throw new IllegalStateException(e);}
        }
        @Override public boolean isInvulnerableTo(net.minecraft.world.damagesource.DamageSource source){return isInvulnerable()&&!source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY);}
        @Override public boolean canHarmPlayer(net.minecraft.world.entity.player.Player other){return getTeam()==null||getTeam()!=other.getTeam()||getTeam().isAllowFriendlyFire();}
        @Override public void teleportTo(double x,double y,double z){setPos(x,y,z);}
        @Override public net.minecraft.world.entity.Entity changeDimension(net.minecraft.world.level.portal.DimensionTransition transition){
            var changed=super.changeDimension(transition);
            // FakePlayer's connection drops teleport packets, including their server-side movement.
            if(changed==this){setPos(transition.pos());setYRot(transition.yRot());setXRot(transition.xRot());}
            return changed;
        }
    }
}
