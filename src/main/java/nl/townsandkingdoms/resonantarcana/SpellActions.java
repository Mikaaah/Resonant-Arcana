package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

/** Additional delivery patterns. Damage, allegiance and sight checks stay in the shared engine. */
final class SpellActions {
    private static final EnumSet<Spell.Kind> KINDS=EnumSet.of(Spell.Kind.BLINK,Spell.Kind.DASH,Spell.Kind.SWEEP,
        Spell.Kind.FAN,Spell.Kind.TETHER,Spell.Kind.EXECUTE,Spell.Kind.BUFF,Spell.Kind.TRAP,Spell.Kind.RICOCHET,
        Spell.Kind.PIERCING_ORB,Spell.Kind.ORBIT,Spell.Kind.TOTEM,Spell.Kind.RAIN,Spell.Kind.GAZE,
        Spell.Kind.REMEDY,Spell.Kind.CLEANSE,Spell.Kind.PILLAR,Spell.Kind.HYMN,Spell.Kind.SERPENT,Spell.Kind.DOMAIN,Spell.Kind.CONE_CHANNEL);
    private SpellActions(){}
    static boolean handles(Spell.Kind kind){return kind==Spell.Kind.EXILE||kind==Spell.Kind.ASCENSION||kind==Spell.Kind.DEVASTATOR||kind==Spell.Kind.CATACLYSM||AdvancedSpells.handles(kind)||BossesRiseCompat.handles(kind)||KINDS.contains(kind)||MartialSpells.handles(kind);}
    static float power(Cast c){return c.book==null?1:c.book.power();}
    static int supportDuration(Cast c,int base){return Math.max(1,(int)(base*Math.clamp(power(c),1,2)));}
    static Vec3 blinkDestination(ServerPlayer player,Spell spell,double range){
        Vec3 start=player.getEyePosition(),dir=player.getLookAngle().normalize();
        Vec3 clipped=clipLoaded(player.serverLevel(),start,dir,range,player);double length=start.distanceTo(clipped);
        for(double d=length-.25;d>=.75;d-=.25){
            Vec3 delta=dir.scale(d),feet=player.position().add(delta);
            if(!player.serverLevel().hasChunkAt(BlockPos.containing(feet))||player.serverLevel().getFluidState(BlockPos.containing(feet)).is(FluidTags.LAVA))continue;
            if(player.serverLevel().noCollision(player,player.getBoundingBox().move(delta)))return feet;
        }
        return null;
    }
    static boolean canStart(ServerPlayer p,Spell s){
        if(s==Spell.VOID_EXILE)return VoidExile.canStart(p);
        if(s==Spell.SERAPH_ASCENDANT&&p.hasEffect(ArcanaEffects.SERAPH_FLIGHT))return false;
        if(!AdvancedSpells.canStart(p,s))return false;
        if(BossesRiseCompat.optional(s)&&!BossesRiseCompat.canStart(p,s))return false;
        if(s.kind==Spell.Kind.TOTEM){
            Vec3 eye=p.getEyePosition(),dir=p.getLookAngle().normalize();
            Vec3 aim=clipLoaded(p.serverLevel(),eye,dir,s.range,p);var target=target(p,eye,aim,.5);
            Vec3 ground=BossesRiseCompat.ground(p.serverLevel(),target==null?aim:target.position());
            if(ground==null||p.serverLevel().clip(new net.minecraft.world.level.ClipContext(eye,ground.add(0,.2,0),net.minecraft.world.level.ClipContext.Block.COLLIDER,net.minecraft.world.level.ClipContext.Fluid.NONE,p)).getType()!=net.minecraft.world.phys.HitResult.Type.MISS)return false;
        }
        if(s==Spell.SANGUINE_PACT&&p.getHealth()<=2)return false;
        return s.kind!=Spell.Kind.BLINK||blinkDestination(p,s,s.range)!=null;
    }
    static boolean begin(Cast c){
        if(c.spell==Spell.VOID_EXILE){VoidExile.begin(c);return false;}
        if(SpecialSpells.handles(c.spell))return SpecialSpells.begin(c);
        if(CataclysmSpells.handles(c.spell))return CataclysmSpells.begin(c);
        if(AdvancedSpells.handles(c.spell.kind))return AdvancedSpells.begin(c);
        if(BossesRiseCompat.handles(c.spell.kind))return BossesRiseCompat.begin(c);
        if(MartialSpells.handles(c.spell.kind))return MartialSpells.begin(c);
        switch(c.spell.kind){
            case BLINK -> {
                Vec3 dest=blinkDestination(c.caster,c.spell,c.spell.range+Math.min(4,Math.max(0,(power(c)-1)*4)));
                if(dest!=null){Vec3 old=c.caster.position();c.caster.teleportTo(dest.x,dest.y,dest.z);c.caster.fallDistance=0;
                    effect(c.caster,MobEffects.SLOW_FALLING,40,0);
                    if(c.spell==Spell.UMBRAL_STEP)effect(c.caster,MobEffects.INVISIBILITY,20,0);
                    SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,old.add(0,1,0),dest.add(0,1,0),1);burst(c,dest.add(0,1,0));}
                return false;
            }
            case DASH -> {
                c.caster.setDeltaMovement(c.dir.scale(1.15).add(0,.32,0));c.caster.hurtMarked=true;
                effect(c.caster,MobEffects.SLOW_FALLING,60,0);c.caster.fallDistance=0;
                if(c.spell.damage>0)area(c,c.caster.position().add(0,.5,0),c.spell.radius,true);
                SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,c.pos,c.pos.add(c.dir.scale(4)),1);return false;
            }
            case SWEEP,GAZE -> {cone(c,c.pos,c.dir,c.spell.range,.5);SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos.add(c.dir.scale(2)),c.pos.add(c.dir.scale(3)),Math.max(2,c.spell.radius*.65));return false;}
            case EXECUTE -> {if(c.target!=null){hit(c,c.target,c.target.position(),true);burst(c,c.target.getBoundingBox().getCenter());}return false;}
            case BUFF -> {buff(c);return false;}
            case CLEANSE -> {cleanse(c);return false;}
            case REMEDY -> {remedy(c);return false;}
            case TETHER -> {c.anchor=c.target.position();c.duration=supportDuration(c,c.spell.duration());}
            case ORBIT -> c.duration=c.spell.duration();
            case HYMN,CONE_CHANNEL -> c.duration=c.spell.duration();
            case TOTEM,RAIN,DOMAIN,TRAP,PILLAR -> {c.pos=c.target!=null?c.target.getBoundingBox().getCenter():c.anchor.subtract(c.dir.scale(.08));c.duration=c.spell.duration();if(c.spell.kind==Spell.Kind.TOTEM)SpellCentrepieces.place(c);}
            case FAN -> {
                c.duration=40;Vec3 side=c.dir.cross(new Vec3(0,1,0));if(side.lengthSqr()<.01)side=new Vec3(1,0,0);side=side.normalize();
                for(int i=-2;i<=2;i++)c.shots.add(new Shot(c.pos,c.dir.add(side.scale(i*.12)).normalize()));
            }
            case SERPENT -> {c.duration=(int)Math.ceil(c.spell.range/1.2)+6;
                Vec3 flat=c.dir.multiply(1,0,1);if(flat.lengthSqr()<.01)flat=new Vec3(0,0,1);
                c.travel=flat.normalize();c.pos=c.caster.position().add(0,.35,0).add(c.travel.scale(.7));}
            case PIERCING_ORB -> c.duration=(int)Math.ceil(c.spell.range/.7)+6;
            case RICOCHET -> c.duration=(int)Math.ceil(c.spell.range/1.2)+6;
            default -> throw new IllegalArgumentException("Unsupported pattern: "+c.spell.kind);
        }
        return true;
    }
    static boolean tick(Cast c){
        if(SpecialSpells.handles(c.spell))return SpecialSpells.tick(c);
        if(CataclysmSpells.handles(c.spell))return CataclysmSpells.tick(c);
        if(AdvancedSpells.handles(c.spell.kind))return AdvancedSpells.tick(c);
        if(BossesRiseCompat.handles(c.spell.kind))return BossesRiseCompat.tick(c);
        if(MartialSpells.handles(c.spell.kind))return MartialSpells.tick(c);
        switch(c.spell.kind){
            case FAN -> {return fan(c);}
            case PIERCING_ORB,RICOCHET,SERPENT -> {return projectile(c);}
            case TETHER -> {
                var v=c.target;
                if(v==null||!v.isAlive()||!allowed(c.caster,v)||v.level()!=c.level||v.distanceToSqr(c.caster)>c.spell.range*c.spell.range||!visible(c,c.caster.getEyePosition(),v.getBoundingBox().getCenter()))return false;
                c.pos=v.getBoundingBox().getCenter();
                Vec3 pull=c.caster.position().subtract(v.position()).multiply(1,0,1).normalize().scale(.08);
                v.setDeltaMovement(v.getDeltaMovement().add(pull));v.hurtMarked=true;
                if(c.age%4==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,c.pos,c.caster.getEyePosition(),1);
                if((c.age-1)%20==0&&c.age<=c.spell.pulses()*20){c.hit.clear();hit(c,v,c.pos,true);}return true;
            }
            case ORBIT -> {
                c.pos=c.caster.position().add(0,1,0);if((c.age-1)%20==0)c.hit.clear();
                for(int i=0;i<3;i++){
                    double a=c.age*.22+i*Math.PI*2/3;Vec3 raw=c.pos.add(Math.cos(a)*c.spell.radius,0,Math.sin(a)*c.spell.radius);
                    Vec3 delta=raw.subtract(c.pos);Vec3 p=clipLoaded(c.level,c.pos,delta.normalize(),delta.length(),c.caster);
                    if(c.age%3==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,p,p.add(-Math.sin(a),0,Math.cos(a)),.8);
                    area(c,p,1.3,true);
                }return true;
            }
            case TOTEM,RAIN,DOMAIN -> {
                if(c.age%4==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos,c.spell.radius);
                if((c.age-1)%c.spell.interval()==0){
                    c.hit.clear();area(c,c.pos,c.spell.radius,true);
                    if(c.spell==Spell.WISP_LANTERN||c.spell==Spell.DREAD_EFFIGY)for(var victim:near(c.caster,c.pos,c.spell.radius))if(visible(c,c.pos,victim.getBoundingBox().getCenter())){
                        if(c.spell==Spell.WISP_LANTERN)effect(victim,MobEffects.GLOWING,30,0);else effect(victim,MobEffects.WEAKNESS,30,1);
                    }
                    if(c.spell==Spell.CONSECRATION)healArea(c,c.pos,c.spell.radius,c.spell.healing(),false);
                }return true;
            }
            case HYMN -> {
                c.pos=c.caster.position().add(0,.5,0);
                if(c.age%5==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos,c.spell.radius);
                if((c.age-1)%c.spell.interval()==0){healArea(c,c.pos,c.spell.radius,c.spell.healing(),false);SpellAudio.world(c.level,c.pos,"block.amethyst_block.chime",.7f,1.1f);}return true;
            }
            case CONE_CHANNEL -> {
                if((c.age-1)%c.spell.interval()!=0)return true;
                SpellAudio.world(c.level,c.caster.position(),"irons_spellbooks:loop.fire_breath",.75f,1.05f);
                c.hit.clear();Vec3 eye=c.caster.getEyePosition(),dir=c.caster.getLookAngle();cone(c,eye,dir,c.spell.range,.65);
                SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,eye.add(dir.scale(.8)),clipLoaded(c.level,eye,dir,c.spell.range,c.caster),1.5);return true;
            }
            case TRAP -> {
                if(c.age%5==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos,c.spell.radius);
                if(c.age>=10&&near(c.caster,c.pos,2).stream().anyMatch(v->visible(c,c.pos,v.getBoundingBox().getCenter()))){area(c,c.pos,c.spell.radius,true);burst(c,c.pos);return false;}return true;
            }
            case PILLAR -> {
                if(c.age<c.duration){if(c.age%5==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos,c.spell.radius);return true;}
                Vec3 top=clipLoaded(c.level,c.pos,new Vec3(0,1,0),12,c.caster);
                SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.STRIKE,top,c.pos,1.5);area(c,c.pos,c.spell.radius,true);
                if(c.spell.healing()>0)healArea(c,c.pos,c.spell.radius,c.spell.healing(),false);burst(c,c.pos);return false;
            }
            default -> {return false;}
        }
    }
    private static void cone(Cast c,Vec3 start,Vec3 dir,double length,double cosine){
        for(var v:near(c.caster,start,length)){
            Vec3 delta=v.getBoundingBox().getCenter().subtract(start);
            if(delta.length()<=length&&delta.normalize().dot(dir)>=cosine&&visible(c,start,v.getBoundingBox().getCenter())){
                hit(c,v,start,true);
                if(c.spell.kind==Spell.Kind.GAZE){effect(v,MobEffects.MOVEMENT_SLOWDOWN,supportDuration(c,40),4);effect(v,MobEffects.WEAKNESS,supportDuration(c,40),1);}
            }
        }
    }
    private static boolean fan(Cast c){
        for(var it=c.shots.iterator();it.hasNext();){
            Shot shot=it.next();Vec3 next=clipLoaded(c.level,shot.pos,shot.dir,1.5,c.caster);
            var victim=target(c.caster,shot.pos,next,.25);
            if(victim!=null){c.hit.clear();hit(c,victim,next,true);burst(c,next);it.remove();continue;}
            if(c.age%2==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.FLIGHT,next,next.add(shot.dir),.55);
            shot.distance+=shot.pos.distanceTo(next);boolean stop=next.distanceTo(shot.pos)<1.49||shot.distance>=c.spell.range;
            shot.pos=next;if(stop)it.remove();
        }
        return !c.shots.isEmpty();
    }
    private static boolean projectile(Cast c){
        Vec3 old=c.pos,dir=c.travel;
        if(c.spell.kind==Spell.Kind.SERPENT){
            Vec3 side=c.travel.cross(new Vec3(0,1,0)).normalize();
            double weave=.55*(Math.sin(c.age*.65)-Math.sin((c.age-1)*.65));
            dir=c.travel.add(side.scale(weave/1.5)).normalize();
        }
        double step=Math.min(c.spell.kind==Spell.Kind.PIERCING_ORB?.7:1.5,c.spell.range-c.distance);if(step<=.01){burst(c,c.pos);return false;}
        Vec3 end=clipLoaded(c.level,old,dir,step,c.caster);double moved=old.distanceTo(end);
        for(var v:near(c.caster,old.lerp(end,.5),c.spell.kind==Spell.Kind.PIERCING_ORB?c.spell.radius+1:2)){
            if(v.getBoundingBox().inflate(c.spell.kind==Spell.Kind.PIERCING_ORB?c.spell.radius:.45).clip(old,end).isPresent()&&visible(c,old,v.getBoundingBox().getCenter())&&!c.hit.contains(v.getUUID())){
                hit(c,v,old,true);
                if(c.spell.kind==Spell.Kind.RICOCHET){
                    LivingEntity next=null;double best=c.spell.radius*c.spell.radius;
                    for(var candidate:near(c.caster,v.position(),c.spell.radius)){
                        double d=candidate.distanceToSqr(v);
                        if(!c.hit.contains(candidate.getUUID())&&d<best&&visible(c,v.getBoundingBox().getCenter(),candidate.getBoundingBox().getCenter())){next=candidate;best=d;}
                    }
                    if(next==null||c.hit.size()>=3){burst(c,v.getBoundingBox().getCenter());return false;}
                    c.travel=next.getBoundingBox().getCenter().subtract(end).normalize();break;
                }
            }
        }
        c.distance+=moved;c.pos=end;
        if(c.age%2==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.FLIGHT,end,end.add(dir),c.spell.kind==Spell.Kind.PIERCING_ORB?1.8:.85);
        if(moved+1e-3<step||c.distance>=c.spell.range-.01){if(c.spell.kind==Spell.Kind.PIERCING_ORB)area(c,end,c.spell.radius,true);burst(c,end);return false;}
        return true;
    }
    private static void buff(Cast c){
        var p=c.caster;int duration=supportDuration(c,c.spell.duration());float shield=0;
        switch(c.spell){
            case SANGUINE_PACT -> {p.setHealth(Math.max(1,p.getHealth()-2));shield=4;effect(p,MobEffects.DAMAGE_BOOST,duration,0);}
            case VEIL_OF_NIGHT -> {effect(p,MobEffects.INVISIBILITY,duration,0);effect(p,MobEffects.MOVEMENT_SPEED,duration,0);}
            case RESONANT_GUARD,FROZEN_AEGIS -> {shield=6;effect(p,MobEffects.DAMAGE_RESISTANCE,duration,0);}
            case BLESSING_OF_VIGOR -> {shield=4;effect(p,MobEffects.MOVEMENT_SPEED,duration,0);}
            default -> throw new IllegalArgumentException("Unknown blessing");
        }
        if(shield>0){float before=p.getAbsorptionAmount(),amount=Math.min(16,shield*power(c));effect(p,MobEffects.ABSORPTION,duration,Math.max(0,(int)Math.ceil(amount/4)-1));p.setAbsorptionAmount(Math.max(before,amount));}
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,p.position().add(0,1,0),p.position(),1.3);
    }
    private static void cleanse(Cast c){
        if(c.spell==Spell.TOXIC_MOLT){
            c.caster.removeEffect(MobEffects.POISON);c.caster.removeEffect(MobEffects.WITHER);heal(c,c.caster,c.spell.healing());
            effect(c.caster,MobEffects.MOVEMENT_SPEED,supportDuration(c,c.spell.duration()),0);
        }else{
            for(var ally:allies(c,c.caster.position(),c.spell.radius)){
                removeAfflictions(ally);effect(ally,MobEffects.DAMAGE_RESISTANCE,supportDuration(c,c.spell.duration()),0);
            }
        }
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.caster.position().add(0,.5,0),c.caster.position(),Math.max(1.3,c.spell.radius));
    }
    private static void remedy(Cast c){
        var candidates=allies(c,c.caster.position(),c.spell.radius);
        var chosen=candidates.stream().min(Comparator.comparingDouble(e->e.getHealth()/e.getMaxHealth())).orElse(c.caster);
        heal(c,chosen,c.spell.healing());SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,chosen.getBoundingBox().getCenter(),chosen.position(),1.2);
    }
    static List<LivingEntity> allies(Cast c,Vec3 center,double radius){
        var result=c.level.getEntitiesOfClass(LivingEntity.class,new net.minecraft.world.phys.AABB(center,center).inflate(radius),e->healingAlly(c.caster,e)&&e.getBoundingBox().getCenter().distanceToSqr(center)<radius*radius&&visible(c,center.add(0,.3,0),e.getBoundingBox().getCenter()));
        if(c.caster.position().distanceToSqr(center)<radius*radius&&!result.contains(c.caster)&&visible(c,center,c.caster.getEyePosition()))result.add(c.caster);return result;
    }
    static void heal(Cast c,LivingEntity entity,float amount){entity.heal((float)(amount*power(c)*ArcanaConfig.HEALING.get()));}
    static void healArea(Cast c,Vec3 center,double radius,float amount,boolean cleanse){
        for(var ally:allies(c,center,radius)){heal(c,ally,amount);if(cleanse)removeAfflictions(ally);}
    }
    static void removeAfflictions(LivingEntity ally){for(var e:List.of(MobEffects.POISON,MobEffects.WITHER,MobEffects.BLINDNESS,MobEffects.DARKNESS))ally.removeEffect(e);}
    static final class Shot {
        Vec3 pos;final Vec3 dir;double distance;
        Shot(Vec3 pos,Vec3 dir){this.pos=pos;this.dir=dir;}
    }
}
