package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import java.util.*;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.ICastData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;

/** An anchored containment ritual, followed by one protected, terrain-safe detonation. */
final class AtomicRitual {
    static final double RADIUS=16;
    static final class State implements ICastData {
        final Cast context;
        final Map<UUID,Vec3> prisoners=new HashMap<>();
        State(ServerPlayer p){context=new Cast(p,Spell.I_AM_ATOMIC,p.position().add(0,1,0),p.getLookAngle(),p.position(),null);}
        @Override public void reset(){SpellCentrepieces.cleanup(context);} // Per-native-cast state is discarded by MagicData.
    }
    static State state(ServerPlayer p){return MagicData.getPlayerMagicData(p).getAdditionalCastData() instanceof State s?s:null;}
    static void lock(ServerPlayer p,MagicData data){
        var s=new State(p);data.setAdditionalCastData(s);SpellCentrepieces.atomicFocus(s.context);
        for(var v:near(p,s.context.pos,RADIUS-.75))if(visible(s.context,s.context.pos,v.getBoundingBox().getCenter()))s.prisoners.put(v.getUUID(),v.position());
    }
    static void charge(ServerPlayer p,double progress,int elapsed){
        var s=state(p);if(s==null)return;
        if(p.position().add(0,1,0).distanceToSqr(s.context.pos)>9){IronsCompat.cancel(p);return;}
        contain(p,s);SpellCentrepieces.moveAtomicFocus(s.context);
        if(elapsed%4==0){
            Vec3 tip=SpellCentrepieces.atomicTip(p);
            SpellVisuals.send(p.serverLevel(),Spell.I_AM_ATOMIC,SpellVisuals.Stage.CHARGE,s.context.pos,tip,5+2*progress);
        }
        if(elapsed%4==0)for(var ally:SpellActions.allies(s.context,s.context.pos,RADIUS))effect(ally,MobEffects.DAMAGE_RESISTANCE,8,0);
    }
    static void contain(ServerPlayer p,State s){
        var c=s.context;
        for(var it=s.prisoners.entrySet().iterator();it.hasNext();){
            var entry=it.next();var entity=p.serverLevel().getEntity(entry.getKey());
            if(!(entity instanceof LivingEntity v)||!v.isAlive()||!allowed(p,v)){it.remove();continue;}
            if(v.getType().is(net.neoforged.neoforge.common.Tags.EntityTypes.BOSSES)||v.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)>=1)continue;
            double distance=v.getBoundingBox().getCenter().distanceTo(c.pos);
            if(distance<RADIUS-1){if(p.serverLevel().noCollision(v))entry.setValue(v.position());continue;}
            Vec3 safe=entry.getValue(),delta=safe.subtract(v.position());
            if(p.serverLevel().hasChunkAt(BlockPos.containing(safe))&&p.serverLevel().noCollision(v,v.getBoundingBox().move(delta)))v.teleportTo(safe.x,safe.y,safe.z);
            Vec3 inward=c.pos.subtract(v.getBoundingBox().getCenter()).normalize().scale(.25);
            v.setDeltaMovement(v.getDeltaMovement().scale(.2).add(inward));v.hurtMarked=true;
        }
        for(var shot:p.serverLevel().getEntitiesOfClass(Projectile.class,new AABB(c.pos,c.pos).inflate(RADIUS+2))){
            if(!(shot.getOwner() instanceof LivingEntity owner)||!allowed(p,owner))continue;
            double a=shot.position().distanceTo(c.pos),b=shot.position().add(shot.getDeltaMovement()).distanceTo(c.pos);
            double previous=new Vec3(shot.xo,shot.yo,shot.zo).distanceTo(c.pos);
            if((a<RADIUS)!=(b<RADIUS)||(shot.tickCount>0&&(a<RADIUS)!=(previous<RADIUS)))shot.discard();
        }
    }
    static boolean begin(Cast c){
        var s=state(c.caster);c.pos=s==null?c.caster.position().add(0,1,0):s.context.pos;c.duration=c.spell.duration();if(s!=null)SpellCentrepieces.cleanup(s.context);
        return true;
    }
    static boolean tick(Cast c){
        if(c.age==1){
            RecordedSpells.release(c);c.hit.clear();
            for(var victim:near(c.caster,c.pos,RADIUS))if(victim.getBoundingBox().getCenter().distanceToSqr(c.pos)<=RADIUS*RADIUS&&visible(c,c.pos,victim.getBoundingBox().getCenter()))hit(c,victim,c.pos,true);
            SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,c.pos,c.pos,16);
        }
        if(c.age<=32&&c.age%2==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(0,c.age/32.0,0),Math.min(16,.5+c.age*.7));
        return c.age<c.duration;
    }
    static void end(ServerPlayer p,MagicData data){
        if(data.getAdditionalCastData() instanceof State s){SpellCentrepieces.cleanup(s.context);SpellVisuals.send(p.serverLevel(),Spell.I_AM_ATOMIC,SpellVisuals.Stage.RITUAL_END,s.context.pos,s.context.pos,1);}
    }
}
