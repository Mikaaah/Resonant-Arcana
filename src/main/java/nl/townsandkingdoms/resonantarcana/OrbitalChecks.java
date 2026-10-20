package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

/** Orbital timing, lock, single-hit damage, allies, ceilings and payload contracts. */
final class OrbitalChecks {
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        var p=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("c29f9568-35e2-42e0-b79e-6a67900dc030"),"OrbitalTest"));p.setPos(0,81,0);p.setYRot(0);p.setXRot(0);
        var team=level.getScoreboard().addPlayerTeam("orbital_allies");team.setAllowFriendlyFire(false);level.getScoreboard().addPlayerToTeam(p.getScoreboardName(),team);
        Zombie enemy=enemy(level,0,6),ally=enemy(level,2,6),outside=enemy(level,20,6);level.getScoreboard().addPlayerToTeam(ally.getScoreboardName(),team);
        try{
            for(Spell s:Spell.values()){
                if(GodSpells.handles(s))check.accept(s.castTicks()==200,"God charge is ten seconds: "+s);
                else if(s.category()==SpellCategory.CATACLYSM&&!RecordedSpells.handles(s)&&s!=Spell.ORBITAL_RAY)check.accept(s.castTicks()==120,"non-recorded cataclysm charge is six seconds: "+s);
            }
            check.accept(Spell.EXPLOSION.castTicks()==154&&Spell.I_AM_ATOMIC.castTicks()==270&&Spell.ARCANE_DEVASTATOR.castTicks()==130&&Spell.DOOM_BEAM.castTicks()==260,"all four supplied recordings retain their exact charge boundaries");
            check.accept(Spell.ORBITAL_RAY.castTicks()==300&&io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.ORBITAL_RAY)).getEffectiveCastTime(1,p)==300,"Orbital Ray has a fixed fifteen-second native cast");
            for(String id:RitualAudio.IDS)check.accept(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.containsKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,id)),"authored ritual sound registered: "+id);
            var nativeSpell=io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.ORBITAL_RAY));var data=io.redspace.ironsspellbooks.api.magic.MagicData.getPlayerMagicData(p);
            data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();data.setMana(1000);data.initiateCast(nativeSpell,1,300,io.redspace.ironsspellbooks.api.spells.CastSource.SPELLBOOK,"mainhand");nativeSpell.onServerPreCast(level,1,p,data);OrbitalRay.charge(p,.5,150);float beforeMana=data.getMana();nativeSpell.onServerCastComplete(level,1,p,data,true);data.resetCastingState();
            check.accept(data.getMana()==beforeMana&&SpellEngine.activeCount()==0&&enemy.getHealth()==1024,"interrupting native orbital charge spends no mana and releases no attack (mana="+beforeMana+" -> "+data.getMana()+", active="+SpellEngine.activeCount()+", health="+enemy.getHealth()+")");
            Vec3 muzzle=BeamRitual.origin(p);check.accept(muzzle.distanceTo(BeamRitual.body(p,1))<=.181,"beam source is close to hands, not almost a block forward");
            SpellEngine.clear();OrbitalRay.lock(p);p.setYRot(45);check.accept(SpellEngine.cast(p,Spell.ORBITAL_RAY),"orbital release uses initial locked mark after turning");
            for(int t=0;t<OrbitalRay.DETONATE-1;t++)SpellEngine.tick();check.accept(enemy.getHealth()==1024,"ascent and pre-impact beam deal no premature damage");SpellEngine.tick();
            check.accept(Math.abs(enemy.getHealth()-(1024-Spell.ORBITAL_RAY.damage))<.01,"orbital explosion deals one spell-owned hit only after the slower descent");
            check.accept(ally.getHealth()==1024&&outside.getHealth()==1024,"orbital blast protects teammates and excludes targets outside sixteen blocks");
            for(int t=0;t<160;t++){enemy.invulnerableTime=0;SpellEngine.tick();}
            check.accept(Math.abs(enemy.getHealth()-(1024-Spell.ORBITAL_RAY.damage))<.01&&SpellEngine.activeCount()==0,"orbital beam and cloud cannot repeat damage and clean up");
            check.accept(level.getBlockState(new BlockPos(0,80,6)).is(Blocks.STONE),"orbital nuclear spectacle preserves ground");
            SpellEngine.clear();p.setYRot(0);OrbitalRay.lock(p);OrbitalRay.cancel(p);p.setXRot(-90);check.accept(!OrbitalRay.canStart(p),"cancelled orbital target does not leak into next aim");p.setXRot(0);
            for(int x=-2;x<=2;x++)for(int z=4;z<=8;z++)level.setBlockAndUpdate(new BlockPos(x,86,z),Blocks.STONE.defaultBlockState());
            check.accept(!OrbitalRay.canStart(p),"orbital cast refuses insufficient overhead clearance");
            for(int x=-2;x<=2;x++)for(int z=4;z<=8;z++)level.setBlockAndUpdate(new BlockPos(x,86,z),Blocks.AIR.defaultBlockState());
            SpellEngine.clear();check.accept(SpellEngine.cast(p,Spell.ORBITAL_RAY),"orbital obstruction fixture starts");for(int t=0;t<OrbitalRay.ASCENT;t++)SpellEngine.tick();level.setBlockAndUpdate(new BlockPos(0,90,6),Blocks.STONE.defaultBlockState());SpellEngine.tick();check.accept(SpellEngine.activeCount()==0,"a newly blocked vertical ray aborts before detonation");level.setBlockAndUpdate(new BlockPos(0,90,6),Blocks.AIR.defaultBlockState());
            var buf=new RegistryFriendlyByteBuf(Unpooled.buffer(),level.registryAccess());try{
                var v=new WandNetwork.Visual(Spell.ORBITAL_RAY.ordinal(),SpellVisuals.Stage.STRIKE.ordinal(),0,110,6,0,81,6,3);var packet=new WandNetwork.OrbitalFocus(p.getId(),v);WandNetwork.OrbitalFocus.CODEC.encode(buf,packet);check.accept(packet.equals(WandNetwork.OrbitalFocus.CODEC.decode(buf))&&packet.valid(),"caster-owned orbital payload round trips");
                check.accept(!new WandNetwork.OrbitalFocus(-1,v).valid(),"orbital payload rejects invalid caster");buf.clear();var sound=new WandNetwork.Loop(p.getId(),Spell.ARCANE_CREATION.ordinal(),true,false,0,.7f);WandNetwork.Loop.CODEC.encode(buf,sound);check.accept(sound.equals(WandNetwork.Loop.CODEC.decode(buf))&&sound.valid(),"audible ritual progress round trips");check.accept(!new WandNetwork.Loop(p.getId(),Spell.DOOM.ordinal(),true,false,0,Float.NaN).valid(),"ritual envelope rejects nonfinite progress");
            }finally{buf.release();}
            check.accept(level.getEntitiesOfClass(net.minecraft.world.entity.item.PrimedTnt.class,p.getBoundingBox().inflate(80)).isEmpty(),"orbital art never spawns visible or explosive TNT");
        }finally{
            SpellEngine.clear();for(var z:List.of(enemy,ally,outside))z.discard();for(int x=-2;x<=2;x++)for(int z=4;z<=8;z++)level.setBlockAndUpdate(new BlockPos(x,86,z),Blocks.AIR.defaultBlockState());level.setBlockAndUpdate(new BlockPos(0,90,6),Blocks.AIR.defaultBlockState());level.getScoreboard().removePlayerTeam(team);
        }
    }
    private static Zombie enemy(ServerLevel level,double x,double z){var e=new Zombie(EntityType.ZOMBIE,level);e.setPos(x,81,z);e.setNoAi(true);e.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);e.setHealth(1024);level.addFreshEntity(e);return e;}
    private OrbitalChecks(){}
}
