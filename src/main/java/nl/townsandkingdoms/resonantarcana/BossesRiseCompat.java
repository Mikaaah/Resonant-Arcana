package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;

/** Optional spell orchestration. All damage goes through the shared allegiance/LOS engine. */
final class BossesRiseCompat {
    static final String MOD="block_factorys_bosses";
    private static final boolean AVAILABLE=ModList.get().getModContainerById(MOD)
        .map(c->c.getModInfo().getVersion().toString().equals("2.1.2")).orElse(false);
    private BossesRiseCompat(){}
    static boolean available(){return AVAILABLE;}
    static boolean handles(Spell.Kind kind){return switch(kind){case BOSS_WRATH,BOSS_TENTACLE,BOSS_ICE,BOSS_RIFT->true;default->false;};}
    static boolean optional(Spell s){return handles(s.kind);}
    static void register(IEventBus bus){
        if(AVAILABLE)BossesRiseBridge.register(bus);
        else if(ModList.get().isLoaded(MOD))ResonantArcana.LOG.warn("Bosses'Rise spell integration supports 2.1.2; optional spells are disabled for this version.");
    }
    static boolean canStart(ServerPlayer player,Spell spell){
        if(!AVAILABLE)return false;
        if(spell.kind==Spell.Kind.BOSS_RIFT)return true;
        Vec3 start=player.getEyePosition(),aim=clipLoaded(player.serverLevel(),start,player.getLookAngle(),spell.range,player);
        var victim=target(player,start,aim,.5);
        Vec3 ground=ground(player.serverLevel(),victim==null?aim:victim.position());
        return ground!=null&&pathVisible(player.serverLevel(),player,start,ground.add(0,.7,0));
    }
    private static boolean pathVisible(ServerLevel level,Entity caster,Vec3 from,Vec3 to){
        Vec3 delta=to.subtract(from);return clipLoaded(level,from,delta.normalize(),delta.length(),caster).distanceToSqr(to)<.01;
    }
    static Vec3 ground(ServerLevel level,Vec3 point){
        BlockPos top=BlockPos.containing(point).above(2);
        for(int i=0;i<=10;i++){
            BlockPos floor=top.below(i);if(!level.hasChunkAt(floor))return null;
            var state=level.getBlockState(floor);
            if(state.isFaceSturdy(level,floor,Direction.UP)&&level.getBlockState(floor.above()).getCollisionShape(level,floor.above()).isEmpty())
                return new Vec3(point.x,floor.getY()+1,point.z);
        }
        return null;
    }
    static boolean begin(Cast c){
        if(c.spell.kind==Spell.Kind.BOSS_WRATH)return AdvancedSpells.beginWrath(c);
        if(!AVAILABLE)return false;
        if(c.spell.kind==Spell.Kind.BOSS_RIFT){
            c.duration=30;c.pos=clipLoaded(c.level,c.pos,c.dir,.8,c.caster);
            c.bossVisuals.add(BossesRiseBridge.rift(c));return true;
        }
        c.pos=ground(c.level,c.target==null?c.anchor:c.target.position());
        if(c.pos==null)return false;
        if(c.spell.kind==Spell.Kind.BOSS_TENTACLE){
            c.duration=90;c.bossVisuals.add(BossesRiseBridge.tentacle(c));
            water(c,c.pos,24);return true;
        }
        Vec3 forward=c.dir.multiply(1,0,1);if(forward.lengthSqr()<.01)forward=new Vec3(0,0,1);forward=forward.normalize();
        for(int i=-2;i<=2;i++){
            Vec3 p=ground(c.level,c.pos.add(forward.scale(i*2)));
            if(p!=null&&p.distanceToSqr(c.caster.position())<=(c.spell.range+1)*(c.spell.range+1)
                &&visible(c,c.caster.getEyePosition(),p.add(0,.7,0))){
                c.eruptionPoints.add(p);c.bossVisuals.add(BossesRiseBridge.ice(c,p,12+c.eruptionPoints.size()*3));
            }
        }
        c.duration=48;return true;
    }
    static boolean tick(Cast c){
        if(c.spell.kind==Spell.Kind.BOSS_WRATH)return AdvancedSpells.tickWrath(c);
        switch(c.spell.kind){
            case BOSS_TENTACLE -> {
                if(c.age%5==1&&c.age<48)water(c,c.pos,6);
                // 30 ticks rising, then 16 ticks into the attack animation before impact.
                if(c.age==46){
                    for(var v:near(c.caster,c.pos,c.spell.radius))if(v.getBoundingBox().getCenter().distanceToSqr(c.pos.add(0,.7,0))<=c.spell.radius*c.spell.radius&&visible(c,c.pos.add(0,.7,0),v.getBoundingBox().getCenter())){
                        hit(c,v,c.pos,true);effect(v,MobEffects.MOVEMENT_SLOWDOWN,60,2);
                    }
                    water(c,c.pos,60);SpellAudio.world(c.level,c.pos,MOD+":item.undying_tentacle.smash_ground",1.4f,.8f);
                    SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos,c.spell.radius);
                }
            }
            case BOSS_ICE -> {
                for(int i=0;i<c.eruptionPoints.size();i++)if(c.age==12+(i+1)*3){
                    Vec3 p=c.eruptionPoints.get(i);area(c,p.add(0,.7,0),c.spell.radius,true);
                    c.level.sendParticles(ParticleTypes.SNOWFLAKE,p.x,p.y+.7,p.z,16,.5,.7,.5,.08);
                    SpellAudio.world(c.level,p,MOD+":entity.ice_gauntlet.icicle_spawning",.8f,1+i*.08f);
                    SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,p,p,1.5);
                }
            }
            case BOSS_RIFT -> {
                Vec3 old=c.pos;double step=Math.min(1.2,c.spell.range-c.distance);
                if(step<=.01)return false;
                Vec3 end=clipLoaded(c.level,old,c.dir,step,c.caster);
                var victim=target(c.caster,old,end,.4);
                if(victim!=null){hit(c,victim,old,true);burst(c,victim.getBoundingBox().getCenter());return false;}
                c.distance+=old.distanceTo(end);c.pos=end;
                for(var e:c.bossVisuals)if(!e.isRemoved()){e.setPos(end);e.setDeltaMovement(c.dir.scale(1.2));}
                if(c.age%2==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.FLIGHT,end,end.add(c.dir),.7);
                if(old.distanceTo(end)<step-.001||c.distance>=c.spell.range-.01){burst(c,end);return false;}
            }
            default -> {return false;}
        }
        return true;
    }
    private static void water(Cast c,Vec3 pos,int count){
        c.level.sendParticles(ParticleTypes.SPLASH,pos.x,pos.y+.2,pos.z,count,1,.3,1,.16);
        c.level.sendParticles(ParticleTypes.BUBBLE_POP,pos.x,pos.y+.5,pos.z,Math.max(2,count/2),.7,.5,.7,.04);
    }
    static void cleanup(Cast c){for(var visual:c.bossVisuals)if(!visual.isRemoved())visual.discard();c.bossVisuals.clear();}
}
