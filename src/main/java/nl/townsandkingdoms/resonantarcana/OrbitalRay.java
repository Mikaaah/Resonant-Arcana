package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** A locked orbital strike: no TNT, nuclear entity, destructive explosion or terrain edits. */
final class OrbitalRay {
    static final int ASCENT=40,BEAM_END=80,DETONATE=60;
    private record Lock(Vec3 point,Vec3 top,ResourceKey<Level> dimension,long expires){}
    private static final Map<UUID,Lock> LOCKS=new HashMap<>();
    static Vec3 focus(ServerPlayer p){Vec3 start=BeamRitual.body(p,1);return clipLoaded(p.serverLevel(),start,p.getLookAngle(),1.3,p).add(0,.6,0);}
    private static Lock find(ServerPlayer p){
        Vec3 eye=p.getEyePosition(),end=clipLoaded(p.serverLevel(),eye,p.getLookAngle(),Spell.ORBITAL_RAY.range,p);var victim=target(p,eye,end,.5);
        Vec3 point=BossesRiseCompat.ground(p.serverLevel(),victim==null?end:victim.position());if(point==null)return null;point=point.add(0,.15,0);
        if(eye.distanceToSqr(point)>64*64||!clear(p,eye,point.add(0,.4,0)))return null;
        Vec3 from=point.add(0,.3,0),top=clipLoaded(p.serverLevel(),from,new Vec3(0,1,0),32,p).add(0,-.4,0);
        if(top.y-point.y<12)return null;
        return new Lock(point,top,p.serverLevel().dimension(),p.serverLevel().getGameTime()+400);
    }
    static boolean canStart(ServerPlayer p){
        Lock mark=LOCKS.get(p.getUUID());if(mark!=null&&(mark.expires()<p.serverLevel().getGameTime()||!mark.dimension().equals(p.serverLevel().dimension()))){LOCKS.remove(p.getUUID());mark=null;}
        if(mark==null)mark=find(p);return valid(p,mark)&&routeClear(p,mark);
    }
    private static boolean routeClear(ServerPlayer p,Lock mark){Vec3 start=focus(p),previous=start;for(int i=1;i<=ASCENT;i++){Vec3 next=flight(start,mark.top(),i/(double)ASCENT);if(!clear(p,previous,next))return false;previous=next;}return true;}
    private static boolean valid(ServerPlayer p,Lock mark){return mark!=null&&mark.dimension().equals(p.serverLevel().dimension())&&mark.expires()>=p.serverLevel().getGameTime()&&p.getEyePosition().distanceToSqr(mark.point())<=64*64&&clear(p,p.getEyePosition(),mark.point().add(0,.4,0))&&clear(p,mark.point().add(0,.3,0),mark.top());}
    private static boolean clear(ServerPlayer p,Vec3 a,Vec3 b){return p.serverLevel().hasChunkAt(BlockPos.containing(a))&&p.serverLevel().hasChunkAt(BlockPos.containing(b))&&clipLoaded(p.serverLevel(),a,b.subtract(a).normalize(),a.distanceTo(b),p).distanceToSqr(b)<.02;}
    static void lock(ServerPlayer p){LOCKS.entrySet().removeIf(e->e.getValue().expires()<p.serverLevel().getGameTime());if(LOCKS.size()>=128)LOCKS.remove(LOCKS.keySet().iterator().next());Lock mark=find(p);if(mark!=null)LOCKS.put(p.getUUID(),mark);}
    static void charge(ServerPlayer p,double progress,int elapsed){
        Lock mark=LOCKS.get(p.getUUID());if(mark==null)return;if(!valid(p,mark)||!routeClear(p,mark)){IronsCompat.cancel(p);return;}
        if(elapsed%3==0)SpellVisuals.orbital(p,SpellVisuals.Stage.CHARGE,focus(p),mark.point(),.35+progress*2.15);
        if(elapsed%8==0){SpellVisuals.orbital(p,SpellVisuals.Stage.RITUAL_TARGET,mark.point(),mark.top(),16);SpellLoops.sync(p,Spell.ORBITAL_RAY,true,false,0,progress);}
    }
    static void cancel(ServerPlayer p){Lock mark=LOCKS.remove(p.getUUID());if(mark!=null)SpellVisuals.orbital(p,SpellVisuals.Stage.RITUAL_END,focus(p),mark.point(),1);}
    static void clear(){LOCKS.clear();}
    static Vec3 flight(Vec3 start,Vec3 top,double t){Vec3 control=start.add(0,Math.max(18,top.y-start.y+8),0);double u=1-t;return start.scale(u*u).add(control.scale(2*u*t)).add(top.scale(t*t));}
    static boolean begin(Cast c){
        Lock mark=LOCKS.remove(c.owner);if(mark==null)mark=find(c.caster);if(!valid(c.caster,mark))return false;
        c.pos=mark.point();c.travel=mark.top();c.anchor=focus(c.caster);c.duration=c.spell.duration();
        Vec3 previous=c.anchor;
        for(int i=1;i<=ASCENT;i++){Vec3 next=flight(c.anchor,c.travel,i/(double)ASCENT);if(!clear(c.caster,previous,next))return false;previous=next;}
        SpellAudio.world(c.level,c.anchor,"irons_spellbooks:spell.soulfire_ray.cast",2.4f,.7f);return true;
    }
    static boolean tick(Cast c){
        if(c.age<=ASCENT){
            Vec3 previous=flight(c.anchor,c.travel,(c.age-1)/(double)ASCENT),next=flight(c.anchor,c.travel,c.age/(double)ASCENT);
            if(!clear(c.caster,previous,next))return false;
            if(c.age%2==1)SpellVisuals.orbital(c.caster,SpellVisuals.Stage.FLIGHT,next,c.pos,2.5);
        }else if(c.age<=BEAM_END){
            if(!clear(c.caster,c.pos.add(0,.3,0),c.travel))return false;
            if(c.age%3==0)SpellVisuals.orbital(c.caster,SpellVisuals.Stage.STRIKE,c.travel,c.pos,3);
            if(c.age==ASCENT+1)SpellAudio.world(c.level,c.pos,"irons_spellbooks:spell.soulfire_ray.cast",2.6f,.75f);
            if(c.age==DETONATE){
                for(var victim:near(c.caster,c.pos,c.spell.radius))if(victim.getBoundingBox().getCenter().distanceToSqr(c.pos)<=c.spell.radius*c.spell.radius&&visible(c,c.pos,victim.getBoundingBox().getCenter()))hit(c,victim,c.pos,true);
                SpellVisuals.orbital(c.caster,SpellVisuals.Stage.IMPACT,c.pos,c.travel,16);
                RitualAudio.blast(c);
            }
        }
        return c.age<c.duration;
    }
    static void cleanup(Cast c){if(c.spell==Spell.ORBITAL_RAY)SpellVisuals.orbital(c.caster,SpellVisuals.Stage.RITUAL_END,c.pos,c.travel,1);}
    private OrbitalRay(){}
}
