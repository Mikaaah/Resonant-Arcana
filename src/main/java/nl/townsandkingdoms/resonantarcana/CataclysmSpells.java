package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

/** Legendary release sequences. Native Iron's owns the long, interruptible cast bar. */
final class CataclysmSpells {
    private CataclysmSpells(){}
    static boolean handles(Spell s){return s.kind==Spell.Kind.CATACLYSM;}
    static boolean begin(Cast c){
        c.pos=c.target==null?c.anchor.subtract(c.dir.scale(.08)):c.target.getBoundingBox().getCenter();
        c.duration=c.spell.duration();
        if(c.spell==Spell.EXPLOSION){
            var lock=c.book!=null&&c.book.prepared()?ExplosionRitual.locked(c.caster):null;
            if(lock!=null){c.pos=lock.point();c.travel=lock.top();}
            else {c.pos=c.anchor;c.travel=ExplosionRitual.beamTop(c.caster,c.pos);}
            SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.RITUAL_TARGET,c.pos,c.travel,7);
        }
        if(c.spell==Spell.FINAL_JUDGEMENT)SpellCentrepieces.holySword(c);return true;
    }
    static boolean tick(Cast c){
        double t=c.age/(double)c.duration;
        if(c.spell!=Spell.EXPLOSION&&c.age%3==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(0,t,0),c.spell.radius);
        if(c.spell==Spell.FINAL_JUDGEMENT)SpellCentrepieces.moveHolySword(c);
        switch(c.spell){
            case EXPLOSION -> {
                if(c.age==1){
                    pulse(c,c.pos,c.spell.radius,1);effect(c.caster,MobEffects.MOVEMENT_SLOWDOWN,80,3);effect(c.caster,MobEffects.WEAKNESS,100,0);
                    ExplosionRitual.blast(c);
                }
            }
            case ASTRAL_APOCALYPSE -> {
                if(c.age==16||c.age==40||c.age==64){
                    for(int i=0;i<3;i++){
                        double a=c.age*.06+i*Math.PI*2/3;Vec3 p=c.pos.add(Math.cos(a)*5,0,Math.sin(a)*5);
                        if(!reachable(c,p))continue;
                        strike(c,p.add(0,12,0),p);pulse(c,p,4,1);
                    }
                }
                if(c.age==88){pulse(c,c.pos,c.spell.radius,2);finish(c);}
            }
            case CRIMSON_ECLIPSE -> {
                if(c.age==18||c.age==42||c.age==66)pulse(c,c.pos,c.spell.radius,1);
                if(c.age==90){pulse(c,c.pos,c.spell.radius,2);finish(c);}
            }
            case WORLD_PYRE -> {
                if(c.age>=12&&c.age<=76&&(c.age-12)%16==0){
                    int index=(c.age-12)/16;double a=index*2.39996;
                    Vec3 p=c.pos.add(Math.cos(a)*4,0,Math.sin(a)*4);
                    if(reachable(c,p)){strike(c,p.add(0,15,0),p);pulse(c,p,5,1);}
                }
                if(c.age==100){pulse(c,c.pos,c.spell.radius,2.5);finish(c);}
            }
            case VOID_CORONATION -> {
                if(c.age%5==1)for(var v:near(c.caster,c.pos,c.spell.radius))if(seen(c,v)){
                    effect(v,MobEffects.DARKNESS,40,0);effect(v,MobEffects.WEAKNESS,40,1);
                }
                if(c.age==24||c.age==48||c.age==72){
                    double radius=4+c.age/12.0;pulse(c,c.pos,radius,1);
                }
                if(c.age==96){pulse(c,c.pos,c.spell.radius,2);finish(c);}
            }
            case RIFT_COLLISION -> {
                if(c.age<88&&c.age%3==1)pull(c,.32);
                if(c.age==64||c.age==84){
                    Vec3 axis=c.age==64?new Vec3(1,0,0):new Vec3(0,0,1);
                    Vec3 a=clipLoaded(c.level,c.pos,axis,c.spell.radius,c.caster),b=clipLoaded(c.level,c.pos,axis.scale(-1),c.spell.radius,c.caster);
                    strike(c,a,b);c.hit.clear();
                    for(var v:near(c.caster,c.pos,c.spell.radius))if(seen(c,v)&&v.getBoundingBox().inflate(2).clip(a,b).isPresent())hit(c,v,c.pos,true);
                }
                if(c.age==104){pulse(c,c.pos,c.spell.radius,3);finish(c);}
            }
            case HEAVENS_THUNDER -> {
                if(c.age>=12&&c.age<=92&&(c.age-12)%16==0){
                    int i=(c.age-12)/16;double a=i*Math.PI/3;Vec3 p=c.pos.add(Math.cos(a)*4,0,Math.sin(a)*4);
                    if(reachable(c,p)){strike(c,p.add(0,20,0),p);pulse(c,p,6,1);}
                }
                if(c.age==112){pulse(c,c.pos,c.spell.radius,2);finish(c);}
            }
            case SOUL_REQUIEM -> {
                if(c.age==20||c.age==44||c.age==68){
                    double radius=c.age==20?4:c.age==44?8:12;c.hit.clear();
                    for(var v:near(c.caster,c.pos,radius+2))if(seen(c,v)&&Math.abs(v.getBoundingBox().getCenter().distanceTo(c.pos)-radius)<=2){
                        hit(c,v,c.pos,true);effect(v,MobEffects.GLOWING,80,0);effect(v,MobEffects.DARKNESS,60,0);
                    }
                    SpellAudio.world(c.level,c.pos,"entity.warden.sonic_boom",1.4f,.5f);
                }
                if(c.age==92){pulse(c,c.pos,c.spell.radius,2);finish(c);}
            }
            case WORLDROOT_CATASTROPHE -> {
                if(c.age%5==1)for(var v:near(c.caster,c.pos,c.spell.radius))if(seen(c,v))effect(v,MobEffects.MOVEMENT_SLOWDOWN,30,2);
                if(c.age==20||c.age==44||c.age==68)pulse(c,c.pos,c.spell.radius,1);
                if(c.age==96){pulse(c,c.pos,c.spell.radius,2);SpellActions.healArea(c,c.pos,c.spell.radius,8,false);finish(c);}
            }
            case FINAL_JUDGEMENT -> {
                if(c.age==16||c.age==40||c.age==64){pulse(c,c.pos,c.spell.radius,1);SpellActions.healArea(c,c.pos,c.spell.radius,4,false);}
                if(c.age==104){
                    pulse(c,c.pos,c.spell.radius,2);SpellActions.healArea(c,c.pos,c.spell.radius,12,true);
                    for(var ally:SpellActions.allies(c,c.pos,c.spell.radius))effect(ally,MobEffects.DAMAGE_RESISTANCE,100,0);
                    finish(c);
                }
            }
            case ETERNAL_WINTER -> {
                if(c.age%5==1)for(var v:near(c.caster,c.pos,c.spell.radius))if(seen(c,v)){
                    effect(v,MobEffects.MOVEMENT_SLOWDOWN,30,3);v.setTicksFrozen(Math.max(180,v.getTicksFrozen()));
                }
                if(c.age<=176&&(c.age-1)%25==0)pulse(c,c.pos,c.spell.radius,1);
                if(c.age==200){pulse(c,c.pos,c.spell.radius,3);finish(c);}
            }
            default -> throw new IllegalArgumentException("Missing cataclysm: "+c.spell);
        }
        return c.age<c.duration;
    }
    private static void pulse(Cast c,Vec3 center,double radius,double multiplier){
        c.hit.clear();float old=c.weaponBonus;c.weaponBonus=c.spell.damage*(float)(multiplier-1);
        try{for(var v:near(c.caster,center,radius))if(v.getBoundingBox().getCenter().distanceToSqr(center)<=radius*radius&&visible(c,center,v.getBoundingBox().getCenter())){
            float before=v.getHealth();hit(c,v,center,true);
            if(c.spell==Spell.CRIMSON_ECLIPSE){
                float healing=Math.min(20-c.drained,Math.max(0,before-v.getHealth())*.08f);
                if(healing>0){c.drained+=healing;c.caster.heal((float)(healing*ArcanaConfig.HEALING.get()));}
            }
        }}finally{c.weaponBonus=old;}
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,center,center,radius);
        if(c.spell!=Spell.EXPLOSION)SpellAudio.world(c.level,center,SpellAudio.impactId(c.spell.theme),1.4f,.65f);
    }
    static void pull(Cast c,double speed){
        for(var v:near(c.caster,c.pos,c.spell.radius))if(seen(c,v)&&!v.getType().is(net.neoforged.neoforge.common.Tags.EntityTypes.BOSSES)){
            double resistance=1-Math.clamp(v.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE),0,1);
            if(resistance<=0)continue;
            Vec3 d=c.pos.subtract(v.getBoundingBox().getCenter());
            v.setDeltaMovement(v.getDeltaMovement().scale(.3).add(d.normalize().scale(Math.min(speed,d.length()*.1)*resistance)));v.hurtMarked=true;
        }
    }
    private static boolean seen(Cast c,LivingEntity v){return v.getBoundingBox().getCenter().distanceToSqr(c.pos)<=c.spell.radius*c.spell.radius&&visible(c,c.pos,v.getBoundingBox().getCenter());}
    private static boolean reachable(Cast c,Vec3 p){return c.level.hasChunkAt(net.minecraft.core.BlockPos.containing(p))&&visible(c,c.pos,p);}
    private static void strike(Cast c,Vec3 from,Vec3 to){
        // Clip downward beams to loaded, visible space rather than drawing through ceilings.
        Vec3 delta=from.subtract(to);Vec3 start=clipLoaded(c.level,to,delta.normalize(),delta.length(),c.caster);
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.STRIKE,start,to,2.5);
    }
    private static void finish(Cast c){SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,c.pos,c.pos,c.spell.radius);SpellAudio.world(c.level,c.pos,c.spell==Spell.SOUL_REQUIEM?"entity.warden.sonic_boom":SpellAudio.spellCastId(c.spell),1.8f,.55f);}
}
