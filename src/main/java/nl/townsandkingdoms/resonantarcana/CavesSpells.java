package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import java.util.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.*;

/** Eighteen authored release patterns. Native presentation never runs uncontrolled mob combat. */
final class CavesSpells {
    static final class State {final List<Bolt> bolts=new ArrayList<>();final List<CaveVisual> runners=new ArrayList<>();final List<Vent> vents=new ArrayList<>();CaveVisual focus;Vec3 origin;double dropHeight;int variant;}
    private record Vent(Vec3 point,int due){}
    static final class Bolt {Vec3 pos,dir;CaveVisual visual;double distance;int bounces,hits;Bolt(Vec3 p,Vec3 d){pos=p;dir=d;}}
    private static boolean projectile(Spell s){return switch(s){case POLARITY_BOLT,AMBER_TALON,ACID_GLOB,BRINE_BUBBLE,DESOLATE_DAGGER,GUMBALL_VOLLEY->true;default->false;};}
    private static boolean beam(Spell s){return s==Spell.TREMORZILLA_BREATH||s==Spell.MAGNETAR_ANNIHILATION;}
    private static boolean self(Spell s){return s==Spell.EMP_SURGE||s==Spell.FORSAKEN_SONAR||beam(s);}
    static boolean canStart(ServerPlayer player,Spell spell){
        if(!CavesCompat.available())return false;if(spell==Spell.TECTONIC_STAMPEDE||projectile(spell)||self(spell))return true;
        Vec3 eye=player.getEyePosition(),end=clipLoaded(player.serverLevel(),eye,player.getLookAngle(),spell.range,player);var victim=target(player,eye,end,.5);
        Vec3 ground=BossesRiseCompat.ground(player.serverLevel(),victim==null?end:victim.position());
        if(ground==null||player.serverLevel().clip(new net.minecraft.world.level.ClipContext(eye,ground.add(0,.4,0),net.minecraft.world.level.ClipContext.Block.COLLIDER,net.minecraft.world.level.ClipContext.Fluid.NONE,player)).getType()!=HitResult.Type.MISS)return false;
        return spell!=Spell.NUCLEAR_STRIKE||clipLoaded(player.serverLevel(),ground.add(0,.1,0),new Vec3(0,1,0),14,player).y>ground.y+5;
    }
    static boolean begin(Cast c){
        c.cave=new State();c.cave.variant=CavesCompat.beamVariant(c.caster);c.cave.origin=c.caster.position();c.duration=c.spell.duration();
        if(projectile(c.spell)){
            c.pos=BeamRitual.origin(c.caster);if(c.spell!=Spell.GUMBALL_VOLLEY)launch(c);return true;
        }
        if(beam(c.spell))CaveVisual.spawn(c,c.spell==Spell.TREMORZILLA_BREATH?16:15,c.caster.position(),1,c.duration);
        if(c.spell==Spell.TECTONIC_STAMPEDE){c.pos=c.caster.position();for(var ally:SpellActions.allies(c,c.pos,c.spell.radius)){int blessing=SpellActions.supportDuration(c,400);effect(ally,ArcanaEffects.PRIMAL_INSTINCT,blessing,0);effect(ally,net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE,blessing,0);}CaveVisual.spawn(c,1,c.pos.add(0,2,0),.8f,60);return true;}
        if(self(c.spell))c.pos=c.caster.position().add(0,1,0);
        else {
            Vec3 ground=BossesRiseCompat.ground(c.level,c.target==null?c.anchor:c.target.position());if(ground==null)return false;c.pos=ground.add(0,.15,0);
        }
        switch(c.spell){
            case TREMORZILLA_BREATH -> {}
            case NUCLEAR_STRIKE -> {c.cave.dropHeight=Math.max(1,clipLoaded(c.level,c.pos.add(0,.1,0),new Vec3(0,1,0),14,c.caster).y-c.pos.y-.5);c.cave.focus=CaveVisual.spawn(c,6,c.pos.add(0,c.cave.dropHeight,0),1,c.duration);}
            case LUX_EXTINCTION -> {c.cave.dropHeight=Math.max(1,clipLoaded(c.level,c.pos.add(0,.1,0),new Vec3(0,1,0),10,c.caster).y-c.pos.y-.6);c.cave.focus=CaveVisual.spawn(c,12,c.pos.add(0,c.cave.dropHeight,0),3,c.duration);}
            case HULLBREAKER_SURGE -> c.cave.focus=CaveVisual.spawn(c,4,c.pos.add(0,-6,0),.8f,c.duration);
            case FORSAKEN_SONAR,FORLORN_RECKONING -> c.cave.focus=CaveVisual.spawn(c,5,c.spell==Spell.FORSAKEN_SONAR?c.caster.position():c.pos,c.spell==Spell.FORLORN_RECKONING?1.3f:.65f,c.duration);
            case FROSTMINT_HEX -> {c.cave.focus=CaveVisual.spawn(c,10,c.pos,1.8f,c.duration);for(int i=0;i<3;i++)CaveVisual.spawn(c,9,c.pos.add(Math.cos(i*Math.PI*2/3)*4,.5,Math.sin(i*Math.PI*2/3)*4),1,c.duration);}
            case CANDY_CATASTROPHE -> {c.cave.focus=CaveVisual.spawn(c,11,c.pos.add(0,-2,0),.65f,c.duration);for(int i=0;i<5;i++)CaveVisual.spawn(c,9,c.pos.add(Math.cos(i*Math.PI*2/5)*6,1,Math.sin(i*Math.PI*2/5)*6),1.8f,c.duration);}
            default -> {}
        }
        if(!beam(c.spell))SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(c.dir),c.spell.radius);return true;
    }
    private static void launch(Cast c){
        var bolt=new Bolt(BeamRitual.origin(c.caster),c.caster.getLookAngle().normalize());
        int model=switch(c.spell){case AMBER_TALON->1;case BRINE_BUBBLE->14;case DESOLATE_DAGGER->7;case GUMBALL_VOLLEY->8;default->-1;};
        if(model>=0)bolt.visual=CaveVisual.spawn(c,model,bolt.pos,c.spell==Spell.AMBER_TALON?.65f:.8f,70);
        c.cave.bolts.add(bolt);
    }
    static boolean tick(Cast c){
        if(projectile(c.spell))return projectiles(c);
        if(beam(c.spell))return beamTick(c);
        switch(c.spell){
            case EMP_SURGE -> {
                if(c.age==1){
                    pulse(c,c.pos,c.spell.radius,1);
                    for(var victim:near(c.caster,c.pos,c.spell.radius))if(inside(c,victim,c.pos,c.spell.radius)){
                        if(!victim.getType().is(net.neoforged.neoforge.common.Tags.EntityTypes.BOSSES))nativeEffect(victim,"stunned",60);
                        AdvancedSpells.interrupt(c.caster,victim);
                    }
                    int removed=0;for(var shot:c.level.getEntitiesOfClass(Projectile.class,new AABB(c.pos,c.pos).inflate(c.spell.radius),p->p.getOwner()==null||allowed(c.caster,p.getOwner())))
                        if(removed<32&&shot.position().distanceToSqr(c.pos)<=c.spell.radius*c.spell.radius&&visible(c,c.pos,shot.position())){shot.discard();removed++;}
                }
            }
            case TECTONIC_STAMPEDE -> {if(c.age%4==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(0,1,0),c.spell.radius);return true;}
            case HULLBREAKER_SURGE -> {
                double rise=c.age<=24?Math.sin(c.age/24.0*Math.PI/2):Math.max(0,1-(c.age-24)/30.0);
                if(c.cave.focus!=null)c.cave.focus.setPos(c.pos.add(0,-6+rise*6.4,0));
                if(c.age==24){pulse(c,c.pos.add(0,1,0),c.spell.radius,1);for(var victim:near(c.caster,c.pos,c.spell.radius))if(inside(c,victim,c.pos.add(0,1,0),c.spell.radius))victim.push(0,.55,0);}
                if(c.age%4==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(0,rise,0),c.spell.radius);
            }
            case LUX_EXTINCTION -> {
                if(c.age>=15&&(c.age-15)%24==0&&c.age<=87){int selected=0;
                    for(var victim:near(c.caster,c.pos,c.spell.radius))if(inside(c,victim,c.pos,c.spell.radius)&&selected++<6){Vec3 point=BossesRiseCompat.ground(c.level,victim.position());if(point!=null){c.cave.vents.add(new Vent(point,c.age+8));SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.STRIKE,point,point.add(0,5,0),2.5);}}
                }
                boolean batch=false;for(var it=c.cave.vents.iterator();it.hasNext();){var vent=it.next();if(vent.due()<=c.age){if(!batch){c.hit.clear();batch=true;}for(var victim:near(c.caster,vent.point(),3))if(inside(c,victim,vent.point().add(0,1,0),3))strike(c,victim,vent.point());SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,vent.point(),vent.point().add(0,5,0),3);it.remove();}}
                if(c.age>=88&&c.age<=112&&c.cave.focus!=null)c.cave.focus.setPos(c.pos.add(0,c.cave.dropHeight*(1-(c.age-88)/24.0),0));
                if(c.age==112){if(c.cave.focus!=null)c.cave.focus.discard();pulse(c,c.pos,c.spell.radius,2);}
            }
            case FORSAKEN_SONAR -> {
                if(c.cave.focus!=null)c.cave.focus.setYRot(c.caster.getYRot());
                if(c.age==8||c.age==28||c.age==48){
                    c.hit.clear();Vec3 start=BeamRitual.origin(c.caster),dir=c.caster.getLookAngle();Vec3 end=clipLoaded(c.level,start,dir,c.spell.range,c.caster);
                    for(var victim:near(c.caster,start,c.spell.range)){
                        Vec3 d=victim.getBoundingBox().getCenter().subtract(start);
                        if(d.length()<=start.distanceTo(end)&&d.normalize().dot(dir)>.55&&visible(c,start,victim.getBoundingBox().getCenter())){strike(c,victim,start);effect(victim,MobEffects.GLOWING,60,0);effect(victim,MobEffects.MOVEMENT_SLOWDOWN,30,1);}
                    }
                    SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.STRIKE,start,end,4);
                }
            }
            case NUCLEAR_STRIKE -> {
                if(c.age<40&&c.cave.focus!=null)c.cave.focus.setPos(c.pos.add(0,c.cave.dropHeight*(1-c.age/40.0),0));
                if(c.age==40){if(c.cave.focus!=null)c.cave.focus.discard();pulse(c,c.pos,c.spell.radius,1);for(var victim:near(c.caster,c.pos,c.spell.radius))if(inside(c,victim,c.pos,c.spell.radius))effect(victim,MobEffects.WEAKNESS,100,1);}
            }
            case ABYSSAL_DELUGE,FORLORN_RECKONING,CANDY_CATASTROPHE -> {
                int pulses=c.spell==Spell.FORLORN_RECKONING?3:4;
                if(c.age>=15&&(c.age-15)%24==0&&(c.age-15)/24<pulses){
                    if(c.spell==Spell.ABYSSAL_DELUGE)pull(c,c.pos,.3);
                    pulse(c,c.pos,c.spell.radius,1);
                    if(c.spell==Spell.LUX_EXTINCTION){int i=(c.age-15)/24;double a=i*Math.PI/2;var tephra=CaveVisual.spawn(c,12,c.pos.add(Math.cos(a)*5,7,Math.sin(a)*5),2.5f,20);tephra.setDeltaMovement(0,-.4,0);}
                    if(c.spell==Spell.FORLORN_RECKONING)for(var victim:near(c.caster,c.pos,c.spell.radius))if(inside(c,victim,c.pos,c.spell.radius)){effect(victim,MobEffects.DARKNESS,60,0);effect(victim,MobEffects.GLOWING,80,0);}
                }
                if(c.spell==Spell.ABYSSAL_DELUGE&&c.age%4==1)pull(c,c.pos,.16);
                if(c.age==112)pulse(c,c.pos,c.spell.radius,2);
            }
            case FROSTMINT_HEX -> {
                if(c.age%20==1){pulse(c,c.pos,c.spell.radius,1);for(var ally:SpellActions.allies(c,c.pos,c.spell.radius))nativeEffect(ally,"sugar_rush",30);}
                int i=0;for(Entity prop:c.centrepieces)if(prop instanceof CaveVisual visual&&visual.model()==9){double a=c.age*.12+i++*Math.PI*2/3;visual.setPos(c.pos.add(Math.cos(a)*4,.6,Math.sin(a)*4));}
            }
            default -> {}
        }
        if(c.spell==Spell.CANDY_CATASTROPHE){int i=0;for(Entity prop:c.centrepieces)if(prop instanceof CaveVisual visual&&visual.model()==9){double a=c.age*.09+i++*Math.PI*2/5,r=6+Math.sin(c.age*.04)*3;visual.setPos(c.pos.add(Math.cos(a)*r,1.5+Math.sin(a*2+c.age*.05),Math.sin(a)*r));}}
        if(c.age%4==1&&c.spell!=Spell.HULLBREAKER_SURGE&&(c.spell!=Spell.NUCLEAR_STRIKE||c.age<40))SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(c.dir),c.spell.radius);
        return true;
    }
    private static boolean beamTick(Cast c){
        c.pos=BeamRitual.origin(c.caster);Vec3 dir=c.caster.getLookAngle().normalize();c.anchor=clipLoaded(c.level,c.pos,dir,c.spell.range,c.caster);
        if(c.cave.focus!=null){c.cave.focus.setPos(c.caster.position().subtract(dir.scale(2)));c.cave.focus.setYRot((float)Math.toDegrees(Math.atan2(-dir.x,dir.z)));}
        for(var target:lineTargets(c,c.pos,c.anchor,c.spell.radius))if(visible(c,c.pos,target.getBoundingBox().getCenter()))strike(c,target,c.pos);
        if(c.age%3==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,c.pos,c.anchor,c.spell.radius);
        if(c.spell==Spell.TREMORZILLA_BREATH&&c.age%8==1)SpellLoops.sync(c.caster,c.spell,true,true,c.cave.variant);
        else if(c.spell!=Spell.TREMORZILLA_BREATH&&c.age%24==1)SpellAudio.world(c.level,c.pos,"alexscaves:raygun_loop",.8f,1);
        return true;
    }
    private static boolean projectiles(Cast c){
        if(c.spell==Spell.GUMBALL_VOLLEY&&(c.age==1||c.age==11||c.age==21))launch(c);
        for(var iterator=c.cave.bolts.iterator();iterator.hasNext();){
            Bolt bolt=iterator.next();if(c.spell==Spell.GUMBALL_VOLLEY)c.hit.clear();
            if(c.spell==Spell.AMBER_TALON&&c.target!=null&&c.target.isAlive()&&visible(c,bolt.pos,c.target.getBoundingBox().getCenter()))bolt.dir=bolt.dir.lerp(c.target.getBoundingBox().getCenter().subtract(bolt.pos).normalize(),.15).normalize();
            Vec3 next=clipLoaded(c.level,bolt.pos,bolt.dir,1.1,c.caster);boolean blocked=next.distanceToSqr(bolt.pos)<1.19;
            boolean done=false;
            for(var victim:lineTargets(c,bolt.pos,next,.25))if(!c.hit.contains(victim.getUUID())&&visible(c,bolt.pos,victim.getBoundingBox().getCenter())){
                if(c.spell==Spell.ACID_GLOB)pulse(c,victim.getBoundingBox().getCenter(),c.spell.radius,1);else strike(c,victim,bolt.pos);
                if(c.spell==Spell.POLARITY_BOLT){double mass=Math.clamp(1-victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE),0,1);Vec3 pull=c.caster.position().subtract(victim.position()).normalize().scale(.55*mass);victim.push(pull.x,.1*mass,pull.z);}
                if(c.spell==Spell.AMBER_TALON)victim.push(0,.3,0);
                if(c.spell==Spell.BRINE_BUBBLE&&!victim.getType().is(net.neoforged.neoforge.common.Tags.EntityTypes.BOSSES))nativeEffect(victim,"bubbled",40);
                bolt.hits++;if(c.spell!=Spell.DESOLATE_DAGGER||bolt.hits>=3){done=true;break;}
            }
            if(blocked&&c.spell==Spell.GUMBALL_VOLLEY&&bolt.bounces++==0){
                var hit=c.level.clip(new net.minecraft.world.level.ClipContext(bolt.pos,bolt.pos.add(bolt.dir.scale(1.1)),net.minecraft.world.level.ClipContext.Block.COLLIDER,net.minecraft.world.level.ClipContext.Fluid.NONE,c.caster));
                Vec3 normal=Vec3.atLowerCornerOf(hit.getDirection().getNormal());bolt.dir=bolt.dir.subtract(normal.scale(2*bolt.dir.dot(normal)));next=hit.getLocation().add(normal.scale(.08));blocked=false;
            }
            if(blocked&&!done&&c.spell==Spell.ACID_GLOB)pulse(c,next.add(bolt.dir.scale(-.05)),c.spell.radius,1);
            bolt.distance+=bolt.pos.distanceTo(next);bolt.pos=next;
            if(bolt.visual!=null){bolt.visual.setPos(next);bolt.visual.setYRot((float)Math.toDegrees(Math.atan2(-bolt.dir.x,bolt.dir.z)));}
            if(c.age%2==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.FLIGHT,next,next.add(bolt.dir),.8);
            if(done||blocked||bolt.distance>=c.spell.range){if(bolt.visual!=null)bolt.visual.discard();iterator.remove();}
        }
        return !c.cave.bolts.isEmpty()||c.spell==Spell.GUMBALL_VOLLEY&&c.age<21;
    }
    private static boolean inside(Cast c,LivingEntity entity,Vec3 pos,double radius){return entity.getBoundingBox().getCenter().distanceToSqr(pos)<=radius*radius&&visible(c,pos,entity.getBoundingBox().getCenter());}
    private static void strike(Cast c,LivingEntity victim,Vec3 from){
        hit(c,victim,from,true);
        if(c.spell==Spell.ACID_GLOB)effect(victim,MobEffects.POISON,60,0);
        if(c.spell==Spell.DESOLATE_DAGGER)effect(victim,MobEffects.WEAKNESS,60,0);
        if(c.spell==Spell.LUX_EXTINCTION)victim.igniteForSeconds(3);
        if(c.spell==Spell.FROSTMINT_HEX){effect(victim,MobEffects.MOVEMENT_SLOWDOWN,30,2);victim.setTicksFrozen(Math.max(160,victim.getTicksFrozen()));}
        if(c.spell==Spell.HULLBREAKER_SURGE)effect(victim,MobEffects.MOVEMENT_SLOWDOWN,40,1);
    }
    private static void pulse(Cast c,Vec3 center,double radius,float multiplier){
        c.hit.clear();float old=c.weaponBonus;c.weaponBonus=c.spell.damage*(multiplier-1);
        try{for(var victim:near(c.caster,center,radius))if(inside(c,victim,center,radius))strike(c,victim,center);}finally{c.weaponBonus=old;}
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,center,center.add(c.dir),radius);
    }
    private static void push(Cast c,Vec3 center,double speed){for(var victim:near(c.caster,center,c.spell.radius))if(inside(c,victim,center,c.spell.radius)){Vec3 d=victim.position().subtract(center).normalize().scale(speed*(1-victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));victim.push(d.x,.15,d.z);}}
    private static void pull(Cast c,Vec3 center,double speed){for(var victim:near(c.caster,center,c.spell.radius))if(inside(c,victim,center,c.spell.radius)&&!victim.getType().is(net.neoforged.neoforge.common.Tags.EntityTypes.BOSSES)){Vec3 d=center.subtract(victim.position()).normalize().scale(speed*(1-victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));victim.setDeltaMovement(victim.getDeltaMovement().scale(.5).add(d));victim.hurtMarked=true;}}
    static void nativeEffect(LivingEntity target,String name,int duration){var key=ResourceLocation.fromNamespaceAndPath(CavesCompat.MOD,name);if(BuiltInRegistries.MOB_EFFECT.containsKey(key))effect(target,BuiltInRegistries.MOB_EFFECT.wrapAsHolder(BuiltInRegistries.MOB_EFFECT.get(key)),duration,0);}
}
