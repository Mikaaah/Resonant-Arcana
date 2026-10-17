package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

/** Short-range weapon magic, bounded party support, and directional mobility. */
final class MartialSpells {
    private MartialSpells(){}
    static boolean handles(Spell.Kind kind){return switch(kind){case WARCRY,SOULBLAST,MARTIAL_BUFF,BLADE_ARC,BLADE_THRUST->true;default->false;};}
    static int rank(Cast c){return Math.clamp(Math.round((SpellActions.power(c)-1)*4),0,4);}
    static boolean begin(Cast c){
        switch(c.spell.kind){
            case WARCRY -> {
                c.duration=60+20*rank(c);
                support(c);
                Vec3 center=c.caster.getBoundingBox().getCenter();
                for(var v:near(c.caster,center,c.spell.radius))if(inSight(c,center,v,c.spell.radius)){
                    if(v instanceof Mob mob&&v instanceof Enemy&&c.taunted.size()<32){mob.setTarget(c.caster);c.taunted.add(v.getUUID());}
                }
                SpellAudio.world(c.level,center,"entity.ravager.roar",1.05f,.8f);
                return true;
            }
            case MARTIAL_BUFF -> support(c);
            case SOULBLAST -> soulblast(c);
            case BLADE_ARC,BLADE_THRUST -> blade(c);
            default -> throw new IllegalArgumentException("Unknown martial spell");
        }
        return false;
    }
    static boolean tick(Cast c){
        if(c.spell.kind!=Spell.Kind.WARCRY||c.age>=c.duration)return false;
        Vec3 center=c.caster.getBoundingBox().getCenter();
        if(c.age==1||c.age==13||c.age==25){
            c.hit.clear();area(c,center,c.spell.radius,true);
            SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.caster.position().add(0,.08,0),center,c.spell.radius);
            if(c.age>1)SpellAudio.world(c.level,center,"entity.warden.step",.8f,.65f+c.age*.005f);
        }
        if(c.age%5==1)for(var victim:near(c.caster,center,c.spell.radius)){
            if(!inSight(c,center,victim,c.spell.radius)||victim instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon||victim instanceof net.minecraft.world.entity.boss.wither.WitherBoss||victim.getType().is(net.neoforged.neoforge.common.Tags.EntityTypes.BOSSES))continue;
            double resistance=1-Math.clamp(victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE),0,1);
            if(resistance<=0)continue;
            Vec3 toward=center.subtract(victim.getBoundingBox().getCenter()).multiply(1,0,1);
            if(toward.lengthSqr()>2.25){victim.setDeltaMovement(victim.getDeltaMovement().multiply(.35,1,.35).add(toward.normalize().scale(.24*resistance)));victim.hurtMarked=true;}
        }
        if(c.age%10==0)c.taunted.removeIf(id->{
            var entity=c.level.getEntity(id);
            if(!(entity instanceof Mob mob)||!(entity instanceof Enemy)||!allowed(c.caster,mob)
                ||mob.distanceToSqr(c.caster)>c.spell.radius*c.spell.radius||!visible(c,c.caster.getEyePosition(),mob.getBoundingBox().getCenter()))return true;
            mob.setTarget(c.caster);return false;
        });
        return true;
    }
    private static void support(Cast c){
        int duration=SpellActions.supportDuration(c,c.spell.duration()),rank=rank(c);
        var allies=c.spell==Spell.ARCANE_EDGE?java.util.List.<LivingEntity>of(c.caster):SpellActions.allies(c,c.caster.position(),c.spell.radius);
        for(var ally:allies){
            switch(c.spell){
                case WARSCREAM -> effect(ally,MobEffects.DAMAGE_RESISTANCE,c.duration,0);
                case ARCANE_EDGE -> effect(ally,ArcanaEffects.ARCANE_EDGE,duration,rank+3);
                case BULWARK_CHANT -> {effect(ally,MobEffects.DAMAGE_RESISTANCE,duration,0);shield(c,ally,duration);}
                case BATTLE_TEMPO -> effect(ally,ArcanaEffects.TEMPO,duration,rank+3);
                default -> throw new IllegalArgumentException("Unknown rally");
            }
        }
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.caster.position().add(0,.3,0),c.caster.position(),Math.max(1.2,c.spell.radius));
    }
    private static void shield(Cast c,LivingEntity ally,int duration){
        float amount=4*Math.clamp(SpellActions.power(c),1,2),before=ally.getAbsorptionAmount();
        effect(ally,MobEffects.ABSORPTION,duration,Math.max(0,(int)Math.ceil(amount/4)-1));
        ally.setAbsorptionAmount(Math.max(before,amount));
    }
    private static void soulblast(Cast c){
        Vec3 center=c.caster.getEyePosition(),visualDir=c.dir;
        if(c.caster.isFallFlying()){
            Vec3 current=c.caster.getDeltaMovement();double speed=1.7+rank(c)*.1;
            Vec3 motion=current.scale(.3).add(c.dir.scale(speed));if(motion.length()>3)motion=motion.normalize().scale(3);
            c.caster.setDeltaMovement(motion);
            visualDir=c.dir.scale(-1);
        }else if(c.dir.y<=-.65){
            Vec3 old=c.caster.getDeltaMovement();c.caster.setDeltaMovement(old.x,1.05+rank(c)*.1,old.z);
            c.caster.fallDistance=0;effect(c.caster,MobEffects.SLOW_FALLING,60,0);
            center=c.caster.position();
        }else{
            for(var v:near(c.caster,center,c.spell.range)){
                Vec3 d=v.getBoundingBox().getCenter().subtract(center);
                if(d.normalize().dot(c.dir)>=.5&&inSight(c,center,v,c.spell.range)){hit(c,v,center,true);shove(c,v,center,1.5+rank(c)*.1);}
            }
        }
        c.caster.hurtMarked=true;
        Vec3 start=clipLoaded(c.level,center,visualDir,.8,c.caster);
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.FLIGHT,start,start.add(visualDir),2.8);
        c.level.sendParticles(ParticleTypes.SONIC_BOOM,start.x,start.y,start.z,1,0,0,0,0);
        c.level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,start.x,start.y,start.z,24,.6,.4,.6,.12);
    }
    private static void blade(Cast c){
        c.weaponBonus=(float)Math.max(0,c.caster.getAttributeValue(Attributes.ATTACK_DAMAGE)*.5);
        Vec3 end=clipLoaded(c.level,c.pos,c.dir,c.spell.range,c.caster);
        for(var v:near(c.caster,c.pos,c.spell.range)){
            Vec3 d=v.getBoundingBox().getCenter().subtract(c.pos);
            boolean shape=c.spell.kind==Spell.Kind.BLADE_THRUST?v.getBoundingBox().inflate(.45).clip(c.pos,end).isPresent():d.normalize().dot(c.dir)>=(c.spell==Spell.BLOOD_REND?Math.sqrt(.5):.5);
            if(shape&&inSight(c,c.pos,v,c.spell.range)){hit(c,v,c.pos,true);if(c.spell==Spell.RIME_THRUST)effect(v,MobEffects.MOVEMENT_SLOWDOWN,40,2);}
        }
        if(c.spell.kind==Spell.Kind.BLADE_THRUST)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,c.pos.add(c.dir.scale(.6)),end,1);
        else SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos.add(c.dir.scale(2)),c.pos.add(c.dir.scale(3)),2.5);
    }
    static void drain(Cast c,float removed){
        if(c.spell!=Spell.BLOOD_REND||removed<=0)return;
        float heal=Math.min(Math.max(0,3*SpellActions.power(c)-c.drained),removed*.4f);
        c.drained+=heal;c.caster.heal((float)(heal*ArcanaConfig.HEALING.get()));
    }
    private static boolean inSight(Cast c,Vec3 center,LivingEntity v,double radius){return center.distanceToSqr(v.getBoundingBox().getCenter())<=radius*radius&&visible(c,center,v.getBoundingBox().getCenter());}
    private static void shove(Cast c,LivingEntity v,Vec3 center,double strength){
        double resistance=1-Math.clamp(v.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE),0,1);
        Vec3 away=v.position().subtract(center).multiply(1,0,1).normalize();
        v.setDeltaMovement(v.getDeltaMovement().add(away.scale(strength*resistance)).add(0,.25*resistance,0));v.hurtMarked=true;
    }
}
