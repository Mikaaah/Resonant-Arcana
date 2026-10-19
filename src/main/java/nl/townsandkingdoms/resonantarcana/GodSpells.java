package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.*;

/** Twenty authored rituals. Geometry, native proxies and sound never own combat or terrain. */
final class GodSpells {
    private record Lock(ServerLevel level,Spell spell,Vec3 point){}
    static final class State {Entity focus;Vec3 top;final Set<UUID> fronts=new HashSet<>();float healing;final List<Comet> comets=new ArrayList<>();final List<Vec3> firePools=new ArrayList<>();}
    private record Comet(Entity visual,Vec3 from,Vec3 target,int impact){}
    private static final Map<UUID,Lock> LOCKED=new HashMap<>();
    static boolean handles(Spell s){return s.kind==Spell.Kind.GOD_ART;}
    static void clear(){LOCKED.clear();}
    private static Vec3 mark(ServerPlayer p,Spell s){if(s==Spell.TREMORZILLA_CATACLYSM)return p.position();Vec3 eye=p.getEyePosition(),end=clipLoaded(p.serverLevel(),eye,p.getLookAngle(),s.range,p);var victim=target(p,eye,end,.5);return BossesRiseCompat.ground(p.serverLevel(),victim==null?end:victim.position());}
    static boolean canStart(ServerPlayer p,Spell s){if(s==Spell.TREMORZILLA_CATACLYSM)return true;var locked=LOCKED.get(p.getUUID());var point=locked!=null&&locked.level()==p.serverLevel()&&locked.spell()==s?locked.point():mark(p,s);if(point==null||p.position().distanceToSqr(point)>s.range*s.range||!visiblePoint(p,point.add(0,.5,0)))return false;if(s==Spell.HELVAR_FINAL_EDICT)return clipLoaded(p.serverLevel(),point.add(0,1,0),new Vec3(0,1,0),28,p).y-point.y>BossesRiseBridge.SWORD_TIP_OFFSET+BossesRiseBridge.SWORD_TOP_OFFSET+1;return true;}
    private static boolean visiblePoint(ServerPlayer p,Vec3 point){Vec3 eye=p.getEyePosition();return clipLoaded(p.serverLevel(),eye,point.subtract(eye).normalize(),eye.distanceTo(point),p).distanceToSqr(point)<.02;}
    static void lock(ServerPlayer p,Spell s){Vec3 point=mark(p,s);if(LOCKED.size()>=128)LOCKED.clear();if(point!=null)LOCKED.put(p.getUUID(),new Lock(p.serverLevel(),s,point));}
    static void unlock(ServerPlayer p){LOCKED.remove(p.getUUID());}
    static String chargeSound(Spell s){return switch(s){
        case THUNDER_OF_THE_HEAVENS,PLANETARY_POLARITY->"block.conduit.ambient";
        case SOLAR_SUPERNOVA,DRAGON_PRIMORDIAL_INFERNO->"block.fire.ambient";
        case EVENTIDE_HORIZON,HELVAR_FINAL_EDICT->"block.respawn_anchor.ambient";
        case CRIMSON_APOTHEOSIS->"entity.warden.heartbeat";
        case ABSOLUTE_GLACIATION,YETI_WORLD_HEART->"block.amethyst_cluster.hit";
        case VERDANT_AWAKENING,PRIMORDIAL_SOVEREIGN->"irons_spellbooks:spell.firefly_swarm.prepare";
        case HEAVENS_COVENANT->"block.beacon.ambient";
        case TREMORZILLA_CATACLYSM->"alexscaves:tremorzilla_charge_normal";
        case ABYSSAL_WORLD_TIDE,KRAKEN_WORLD_DROWN->"block.bubble_column.whirlpool_inside";
        case FORSAKEN_ECLIPSE->"alexscaves:forsaken_spawn";
        case CONFECTION_APOCALYPSE->"alexscaves:sugar_staff_cast_hex";
        case SANDWORM_WORLD_DEVOURER->"item.elytra.flying";
        case SOUL_ASCENDANCY->"block.sculk_catalyst.bloom";
        default->"block.amethyst_block.resonate";
    };}
    static String castSound(Spell s){return switch(s){
        case TREMORZILLA_CATACLYSM->"alexscaves:tremorzilla_beam_start";case PLANETARY_POLARITY->"alexscaves:magnetron_attack";
        case PRIMORDIAL_SOVEREIGN->"alexscaves:tremorsaurus_roar";case ABYSSAL_WORLD_TIDE->"alexscaves:hullbreaker_attack";
        case FORSAKEN_ECLIPSE->"alexscaves:forsaken_aoe";case CONFECTION_APOCALYPSE->"alexscaves:gum_worm_attack";
        case HELVAR_FINAL_EDICT->"block_factorys_bosses:entity.knight.heavy_attack";case YETI_WORLD_HEART->"block_factorys_bosses:entity.yeti.ice_barrage";
        case KRAKEN_WORLD_DROWN->"block_factorys_bosses:item.undying_tentacle.spawn_tentacles";case DRAGON_PRIMORDIAL_INFERNO->"block_factorys_bosses:entity.dragon.firelayer";
        case SANDWORM_WORLD_DEVOURER->"entity.ravager.roar";case THUNDER_OF_THE_HEAVENS->"entity.lightning_bolt.thunder";
        case SOLAR_SUPERNOVA->"entity.ender_dragon.growl";case EVENTIDE_HORIZON->"block.respawn_anchor.deplete";
        case HEAVENS_COVENANT->"block.beacon.activate";case SOUL_ASCENDANCY->"entity.warden.roar";
        case ABSOLUTE_GLACIATION->"block.glass.break";case VERDANT_AWAKENING->"irons_spellbooks:entity.root.emerge";
        case CRIMSON_APOTHEOSIS->"irons_spellbooks:cast.generic.blood";default->"block.amethyst_block.resonate";
    };}
    static void charge(ServerPlayer p,Spell s,double progress,int elapsed){
        if(elapsed%8!=0)return;var lock=LOCKED.get(p.getUUID());if(lock==null||lock.level()!=p.serverLevel())return;
        SpellLoops.sync(p,s,true,false,0);
        SpellVisuals.send(p.serverLevel(),s,SpellVisuals.Stage.CHARGE,p.position().add(0,.05,0),lock.point(),10+progress*6);
        SpellVisuals.send(p.serverLevel(),s,SpellVisuals.Stage.RITUAL_TARGET,lock.point().add(0,.05,0),lock.point().add(0,1,0),s.radius);
    }
    static boolean begin(Cast c){
        c.god=new State();c.duration=c.spell.duration();var locked=LOCKED.remove(c.owner);
        c.pos=locked!=null&&locked.level()==c.level&&locked.spell()==c.spell?locked.point():mark(c.caster,c.spell);if(c.pos==null)return false;c.pos=c.pos.add(0,.1,0);
        if(c.spell==Spell.TREMORZILLA_CATACLYSM){CaveVisual.spawn(c,18,c.caster.position(),1,c.duration);return true;}
        if(c.spell==Spell.HELVAR_FINAL_EDICT){double ceiling=clipLoaded(c.level,c.pos.add(0,.2,0),new Vec3(0,1,0),28,c.caster).y;c.god.top=c.pos.add(0,Math.max(BossesRiseBridge.SWORD_TIP_OFFSET,Math.min(16,ceiling-c.pos.y-BossesRiseBridge.SWORD_TOP_OFFSET-.3)),0);c.god.focus=BossesRiseBridge.sword(c,c.god.top);c.bossVisuals.add(c.god.focus);}
        if(c.spell==Spell.FORSAKEN_ECLIPSE)for(int i=0;i<4;i++){double a=i*Math.PI/2;var visual=CaveVisual.spawn(c,5,c.pos.add(Math.cos(a)*18,0,Math.sin(a)*18),1.1f,c.duration);visual.setYRot((float)Math.toDegrees(Math.atan2(Math.cos(a),-Math.sin(a))));}
        if(c.spell==Spell.PRIMORDIAL_SOVEREIGN)for(int i=0;i<3;i++)CaveVisual.spawn(c,i==0?1:17,c.pos.add((i-1)*5,0,0),1,c.duration);
        if(c.spell==Spell.CONFECTION_APOCALYPSE){CaveVisual.spawn(c,10,c.pos,5,c.duration);for(int i=0;i<6;i++){double a=i*Math.PI/3;CaveVisual.spawn(c,9,c.pos.add(Math.cos(a)*12,1,Math.sin(a)*12),2,c.duration);}}
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(0,1,0),c.spell.radius);return true;
    }
    private static List<LivingEntity> enemies(Cast c,Vec3 center,double radius){return near(c.caster,center,radius).stream().filter(e->e.getBoundingBox().getCenter().distanceToSqr(center)<=radius*radius&&visible(c,center.add(0,.6,0),e.getBoundingBox().getCenter())).limit(64).toList();}
    private static void hitAt(Cast c,LivingEntity enemy,Vec3 from,float multiplier){float old=c.weaponBonus;c.weaponBonus=old+c.spell.damage*(multiplier-1);try{hit(c,enemy,from,true);}finally{c.weaponBonus=old;}}
    private static void pulse(Cast c,Vec3 point,double radius,float multiplier){c.hit.clear();for(var enemy:enemies(c,point,radius))if(enemy.getBoundingBox().getCenter().distanceToSqr(c.pos)<=c.spell.radius*c.spell.radius)hitAt(c,enemy,point,multiplier);SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,point,point.add(0,1,0),Math.min(32,radius));}
    private static void pull(Cast c,Vec3 point,double speed){for(var enemy:enemies(c,c.pos,c.spell.radius))if(!enemy.getType().is(net.neoforged.neoforge.common.Tags.EntityTypes.BOSSES)){Vec3 d=point.subtract(enemy.position()).normalize().scale(speed*(1-enemy.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE)));enemy.setDeltaMovement(enemy.getDeltaMovement().scale(.5).add(d));enemy.hurtMarked=true;}}
    private static void support(Cast c){for(var ally:SpellActions.allies(c,c.pos,c.spell.radius))switch(c.spell){
        case HEAVENS_COVENANT->{SpellActions.heal(c,ally,10);ally.removeEffect(MobEffects.POISON);ally.removeEffect(MobEffects.WITHER);effect(ally,ArcanaEffects.COVENANT_GRACE,45,0);effect(ally,MobEffects.DAMAGE_RESISTANCE,45,1);}
        case VERDANT_AWAKENING->{SpellActions.heal(c,ally,10);effect(ally,MobEffects.DAMAGE_RESISTANCE,45,0);effect(ally,MobEffects.REGENERATION,45,1);}
        case PRIMORDIAL_SOVEREIGN->{effect(ally,ArcanaEffects.PRIMAL_INSTINCT,60,0);effect(ally,MobEffects.DAMAGE_RESISTANCE,60,1);effect(ally,MobEffects.REGENERATION,60,1);}
        case ABYSSAL_WORLD_TIDE->{effect(ally,MobEffects.WATER_BREATHING,60,0);effect(ally,MobEffects.DAMAGE_RESISTANCE,60,1);}
        case CONFECTION_APOCALYPSE->CavesSpells.nativeEffect(ally,"sugar_rush",45);
        default->{}
    }}
    private static Vec3 sector(Cast c,int index,int count,double radius){double a=index*Math.PI*2/count;return c.pos.add(Math.cos(a)*radius,0,Math.sin(a)*radius);}
    private static void bolt(Cast c,Vec3 point,float multiplier){if(!c.level.hasChunkAt(BlockPos.containing(point)))return;Vec3 ground=BossesRiseCompat.ground(c.level,point);if(ground==null)return;pulse(c,ground.add(0,.5,0),3,multiplier);SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.STRIKE,ground,ground.add(0,24,0),2);}
    static boolean tick(Cast c){
        int age=c.age;
        if(c.spell==Spell.TREMORZILLA_CATACLYSM){
            c.pos=BeamRitual.origin(c.caster);Vec3 end=clipLoaded(c.level,c.pos,c.caster.getLookAngle(),64,c.caster);for(var enemy:lineTargets(c,c.pos,end,4))if(visible(c,c.pos,enemy.getBoundingBox().getCenter()))hit(c,enemy,c.pos,true);
            if(age%4==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,c.pos,end,4);if(age%6==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,end,end.add(0,1,0),4);if(age%8==1)SpellLoops.sync(c.caster,c.spell,true,true,1);return true;
        }
        if(age%4==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(0,age/(double)c.duration,0),c.spell.radius);
        switch(c.spell){
            case THUNDER_OF_THE_HEAVENS->{if(age%4==1){int wave=(age-1)/4;var victims=enemies(c,c.pos,c.spell.radius);for(int i=0;i<4;i++){double a=(wave*4+i)*2.399963,r=c.spell.radius*Math.sqrt(((wave*17+i*23)%97+1)/98.0);Vec3 point=i==0&&!victims.isEmpty()?victims.get(wave%victims.size()).position():c.pos.add(Math.cos(a)*r,0,Math.sin(a)*r);bolt(c,point,1);}if(age%20==1)SpellAudio.world(c.level,c.pos,"entity.lightning_bolt.thunder",.65f,.8f);}}
            case DRAGON_PRIMORDIAL_INFERNO->{
                if(age>=8&&age<=98&&(age-8)%18==0){int index=(age-8)/18;Vec3 point=sector(c,index,6,12);Vec3 target=BossesRiseCompat.ground(c.level,point);
                    if(target!=null){Vec3 from=clipLoaded(c.level,target.add(0,.5,0),new Vec3(0,1,0),10,c.caster);var orb=BossesRiseBridge.flame(c,from);c.bossVisuals.add(orb);c.god.comets.add(new Comet(orb,from,target,age+14));}}
                for(var it=c.god.comets.iterator();it.hasNext();){var comet=it.next();double t=Math.clamp(1-(comet.impact()-age)/14.0,0,1);comet.visual().setPos(comet.from().lerp(comet.target(),t));
                    if(age>=comet.impact()){comet.visual().discard();pulse(c,comet.target(),10,1);c.god.firePools.add(comet.target());for(var enemy:enemies(c,comet.target(),10))enemy.igniteForSeconds(6);it.remove();}}
                if(age%20==1)for(var pool:c.god.firePools){SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.STRIKE,pool,pool.add(0,2,0),4);for(var enemy:enemies(c,pool,4))enemy.igniteForSeconds(2);}
                if(age==165){pulse(c,c.pos,c.spell.radius,3);for(var enemy:enemies(c,c.pos,c.spell.radius))enemy.igniteForSeconds(8);}
            }
            case ARCANE_CREATION,CONFECTION_APOCALYPSE->{
                if(age>=20&&(age-20)%20==0&&age<=120){int index=(age-20)/20;Vec3 point=sector(c,index,6,12);if(c.spell==Spell.CONFECTION_APOCALYPSE){var ball=CaveVisual.spawn(c,8,point.add(0,8,0),4,20);ball.setDeltaMovement(0,-.4,0);}if(c.spell==Spell.DRAGON_PRIMORDIAL_INFERNO)c.bossVisuals.add(BossesRiseBridge.flame(c,point.add(0,4,0)));pulse(c,point,14,1);}
                if(age==150)pulse(c,c.pos,c.spell.radius,c.spell==Spell.ARCANE_CREATION?2:3);if(c.spell==Spell.CONFECTION_APOCALYPSE&&age%20==1)support(c);
            }
            case CRIMSON_APOTHEOSIS->{if(age>=20&&age<=80&&age%20==0){var victims=enemies(c,c.pos,c.spell.radius);pulse(c,c.pos,c.spell.radius,1);if(!victims.isEmpty()&&c.god.healing<20){float heal=Math.min(4,20-c.god.healing);SpellActions.heal(c,c.caster,heal);c.god.healing+=heal;}for(var enemy:victims.stream().limit(8).toList())SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,enemy.getBoundingBox().getCenter(),BeamRitual.origin(c.caster),1);}if(age==130)pulse(c,c.pos,c.spell.radius,2);}
            case SOLAR_SUPERNOVA->{if(age>=40&&age<=100){double radius=c.spell.radius*(age-39)/61.0;for(var enemy:enemies(c,c.pos,radius))if(c.god.fronts.add(enemy.getUUID())){hitAt(c,enemy,c.pos,1);enemy.igniteForSeconds(8);}if(age%3==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,c.pos,c.pos.add(0,3,0),Math.max(.2,radius));}}
            case EVENTIDE_HORIZON->{if(age==25||age==50||age==75||age==100){double a=(age/25-1)*Math.PI/4;Vec3 axis=new Vec3(Math.cos(a),0,Math.sin(a));Vec3 start=c.pos.subtract(axis.scale(24)).add(0,1,0),end=c.pos.add(axis.scale(24)).add(0,1,0);c.hit.clear();for(var enemy:lineTargets(c,start,end,4))if(enemy.getBoundingBox().getCenter().distanceToSqr(c.pos)<=c.spell.radius*c.spell.radius&&visible(c,c.pos,enemy.getBoundingBox().getCenter())){hitAt(c,enemy,c.pos,1);effect(enemy,MobEffects.WEAKNESS,60,1);}SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.STRIKE,start,end,4);}if(age==145)pulse(c,c.pos,c.spell.radius,2);}
            case ABSOLUTE_GLACIATION->{if(age<=40){double radius=c.spell.radius*age/40.0;for(var enemy:enemies(c,c.pos,radius))if(c.god.fronts.add(enemy.getUUID())){hitAt(c,enemy,c.pos,1);enemy.setTicksFrozen(240);effect(enemy,MobEffects.MOVEMENT_SLOWDOWN,80,3);}}if(age==60||age==80||age==100||age==120)pulse(c,c.pos,c.spell.radius,1);if(age==155)pulse(c,c.pos,c.spell.radius,2);}
            case VERDANT_AWAKENING->{if(age%30==1&&age<=151){pulse(c,c.pos,c.spell.radius,1);pull(c,c.pos,.3);for(var enemy:enemies(c,c.pos,c.spell.radius))effect(enemy,MobEffects.POISON,60,1);support(c);}if(age==195)pulse(c,c.pos,c.spell.radius,3);}
            case HEAVENS_COVENANT->{if(age%30==1&&age<=121){pulse(c,c.pos,c.spell.radius,1);support(c);}if(age%20==1)for(var ally:SpellActions.allies(c,c.pos,c.spell.radius)){effect(ally,ArcanaEffects.COVENANT_GRACE,30,0);effect(ally,MobEffects.DAMAGE_RESISTANCE,30,1);}if(age==170)pulse(c,c.pos,c.spell.radius,2);}
            case SOUL_ASCENDANCY->{if(age%25==1&&age<=101){pulse(c,c.pos,c.spell.radius,1);effect(c.caster,MobEffects.ABSORPTION,100,3);for(var enemy:enemies(c,c.pos,c.spell.radius))effect(enemy,MobEffects.WEAKNESS,60,1);}if(age==150)pulse(c,c.pos,c.spell.radius,2);}
            case PLANETARY_POLARITY->{if(age==1){int n=0;for(var shot:c.level.getEntitiesOfClass(Projectile.class,c.caster.getBoundingBox().inflate(24)))if(n<32&&shot.getOwner()!=c.caster&&(shot.getOwner()==null||allowed(c.caster,shot.getOwner()))&&visible(c,c.pos,shot.position())){shot.discard();n++;}}if(age%24==1&&age<=121){pulse(c,c.pos,c.spell.radius,1);pull(c,sector(c,age/24,6,10),age%48==1?.5:-.35);}if(age==155)pulse(c,c.pos,c.spell.radius,2);}
            case PRIMORDIAL_SOVEREIGN->{if(age%30==1&&age<=121){pulse(c,c.pos,c.spell.radius,1);support(c);CaveVisual.spawn(c,17,sector(c,age/30,5,8),1.5f,30);}if(age==170)pulse(c,c.pos,c.spell.radius,2);}
            case ABYSSAL_WORLD_TIDE->{if(age%28==1&&age<=141){pulse(c,c.pos,c.spell.radius,1);pull(c,c.pos,.45);support(c);if(age==141)for(var enemy:enemies(c,c.pos,c.spell.radius))if(!enemy.getType().is(net.neoforged.neoforge.common.Tags.EntityTypes.BOSSES))CavesSpells.nativeEffect(enemy,"bubbled",40);}}
            case FORSAKEN_ECLIPSE->{if(age==25||age==55||age==85){pulse(c,c.pos,c.spell.radius,1);for(var enemy:enemies(c,c.pos,c.spell.radius)){effect(enemy,MobEffects.DARKNESS,80,0);effect(enemy,MobEffects.GLOWING,80,0);}}if(age==145)pulse(c,c.pos,c.spell.radius,3);}
            case KRAKEN_WORLD_DROWN->{if(age>=20&&age<=80&&age%20==0){int index=(age-20)/20;for(int i=0;i<2;i++){Vec3 point=sector(c,index*2+i,8,Math.max(4,20-index*3));Vec3 floor=BossesRiseCompat.ground(c.level,point);if(floor!=null)c.bossVisuals.add(BossesRiseBridge.tentacle(c,floor,c.pos.subtract(floor).normalize()));}pulse(c,c.pos,Math.max(8,c.spell.radius-index*3),1);pull(c,c.pos,.4);}if(age==150)pulse(c,c.pos,c.spell.radius,3);}
            case YETI_WORLD_HEART->{if(age==20||age==50||age==80){double radius=(age/30+1)*7;for(int i=0;i<12;i++){Vec3 point=BossesRiseCompat.ground(c.level,sector(c,i,12,radius));if(point!=null)c.bossVisuals.add(BossesRiseBridge.ice(c,point,0));}pulse(c,c.pos,Math.min(radius+2,c.spell.radius),1);for(var enemy:enemies(c,c.pos,radius+2)){enemy.setTicksFrozen(240);effect(enemy,MobEffects.MOVEMENT_SLOWDOWN,70,3);}}if(age==145)pulse(c,c.pos,c.spell.radius,3);}
            case HELVAR_FINAL_EDICT->{if(age==24||age==48||age==72)pulse(c,c.pos,c.spell.radius*age/72.0,1);if(age>=88&&age<=112&&c.god.focus!=null)c.god.focus.setPos(c.god.top.lerp(c.pos.add(0,BossesRiseBridge.SWORD_TIP_OFFSET,0),Math.pow((age-88)/24.0,2)));if(age==112){pulse(c,c.pos,c.spell.radius,3);BossesRiseBridge.swordImpact(c.level,c.pos);}}
            case SANDWORM_WORLD_DEVOURER->{if(age%24==1&&age<=97){for(int i=0;i<4;i++){Vec3 point=BossesRiseCompat.ground(c.level,sector(c,i,4,20-age*.15));if(point!=null)c.bossVisuals.add(BossesRiseBridge.sand(c,point,0));}pulse(c,c.pos,c.spell.radius,1);pull(c,c.pos,.4);}if(age==145)pulse(c,c.pos,c.spell.radius,3);}
            default->{}
        }
        return true;
    }
    static void cleanup(Cast c){if(handles(c.spell))unlock(c.caster);}
    private GodSpells(){}
}
