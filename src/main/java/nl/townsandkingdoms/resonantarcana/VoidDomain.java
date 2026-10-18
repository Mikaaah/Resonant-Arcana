package nl.townsandkingdoms.resonantarcana;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

/** One fixed sphere, one bounded entity query per pulse, short-lived non-stacking effects. */
final class VoidDomain {
    static boolean begin(SpellEngine.Cast cast){
        cast.pos=cast.caster.position().add(0,1,0);cast.duration=SpellActions.supportDuration(cast,cast.spell.duration());
        SpellVisuals.send(cast.level,cast.spell,SpellVisuals.Stage.AREA,cast.pos,cast.pos,cast.spell.radius);return true;
    }
    static boolean tick(SpellEngine.Cast cast){
        if(cast.age%4==1)SpellVisuals.send(cast.level,cast.spell,SpellVisuals.Stage.AREA,cast.pos,cast.pos,cast.spell.radius);
        if((cast.age-1)%cast.spell.interval()!=0)return true;
        var targets=cast.level.getEntitiesOfClass(LivingEntity.class,new AABB(cast.pos,cast.pos).inflate(cast.spell.radius),v->v.isAlive()&&!v.isSpectator()&&v.getBoundingBox().getCenter().distanceToSqr(cast.pos)<=cast.spell.radius*cast.spell.radius);
        if(!targets.contains(cast.caster)&&cast.caster.getBoundingBox().getCenter().distanceToSqr(cast.pos)<=cast.spell.radius*cast.spell.radius)targets.add(cast.caster);
        for(var target:targets){
            if(!SpellEngine.visible(cast,cast.pos,target.getBoundingBox().getCenter()))continue;
            if(SpellEngine.healingAlly(cast.caster,target)){
                SpellEngine.effect(target,MobEffects.DAMAGE_RESISTANCE,30,1);SpellEngine.effect(target,MobEffects.REGENERATION,30,1);
                SpellEngine.effect(target,MobEffects.MOVEMENT_SPEED,30,1);SpellEngine.effect(target,ArcanaEffects.VOID_EMPOWERMENT,30,0);
            }else if(SpellEngine.allowed(cast.caster,target)){
                SpellEngine.effect(target,MobEffects.MOVEMENT_SLOWDOWN,30,2);SpellEngine.effect(target,MobEffects.WEAKNESS,30,1);SpellEngine.effect(target,MobEffects.DARKNESS,30,0);
            }
        }
        if(cast.age%40==1)SpellAudio.world(cast.level,cast.pos,"block.portal.ambient",.65f,.65f);
        return true;
    }
    static void cleanup(SpellEngine.Cast cast){if(cast.spell==Spell.VOID_DOMAIN)SpellVisuals.send(cast.level,cast.spell,SpellVisuals.Stage.RITUAL_END,cast.pos,cast.pos,cast.spell.radius);}
}
