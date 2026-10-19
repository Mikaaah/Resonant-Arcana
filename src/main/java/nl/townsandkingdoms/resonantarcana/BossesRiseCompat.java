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
    static boolean handles(Spell.Kind kind){return switch(kind){case BOSS_WRATH,BOSS_TENTACLE,BOSS_ICE,BOSS_RIFT,BOSS_SAND,BOSS_DRAGON,BOSS_SWORD->true;default->false;};}
    static boolean optional(Spell s){return handles(s.kind)||"bossesrise".equals(s.definition.integration());}
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
        if(ground==null||!pathVisible(player.serverLevel(),player,start,ground.add(0,.7,0)))return false;
        if(spell.kind==Spell.Kind.BOSS_SWORD)
            return clipLoaded(player.serverLevel(),ground.add(0,1,0),new Vec3(0,1,0),24,player).y-ground.y
                >BossesRiseBridge.SWORD_TIP_OFFSET+BossesRiseBridge.SWORD_TOP_OFFSET+1;
        if(spell==Spell.DRAGONS_PYRE)
            return clipLoaded(player.serverLevel(),ground.add(0,1,0),new Vec3(0,1,0),8,player).y>ground.y+3.5;
        return true;
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
            c.bossVisuals.add(BossesRiseBridge.rift(c));SpellAudio.world(c.level,c.pos,MOD+":entity.barrage_rift.rift_open",.75f,1);return true;
        }
        c.pos=ground(c.level,c.target==null?c.anchor:c.target.position());
        if(c.pos==null)return false;
        if(c.spell.kind==Spell.Kind.BOSS_SWORD){
            c.bossState=new State();
            double ceiling=clipLoaded(c.level,c.pos.add(0,1,0),new Vec3(0,1,0),24,c.caster).y;
            double height=Math.min(14,ceiling-c.pos.y-BossesRiseBridge.SWORD_TOP_OFFSET-.25);
            if(height<BossesRiseBridge.SWORD_TIP_OFFSET+.5)return false;
            c.bossState.top=c.pos.add(0,height,0);
            c.duration=56;c.bossVisuals.add(BossesRiseBridge.sword(c,c.bossState.top));
            SpellAudio.world(c.level,c.pos,BossesRiseCompat.MOD+":entity.knight.vertical_slash",.85f,.85f);return true;
        }
        if(c.spell.kind==Spell.Kind.BOSS_SAND){
            if(c.spell==Spell.SANDWORMS_QUICKSAND){
                for(int i=0;i<3;i++){
                    double a=i*Math.PI*2/3;Vec3 p=ground(c.level,c.pos.add(Math.cos(a)*1.8,0,Math.sin(a)*1.8));
                    if(p!=null&&visible(c,c.pos.add(0,.7,0),p.add(0,.7,0)))c.bossVisuals.add(BossesRiseBridge.sand(c,p,30));
                }
                c.duration=60;
            }else{
                Vec3 forward=c.dir.multiply(1,0,1);if(forward.lengthSqr()<.01)forward=new Vec3(0,0,1);forward=forward.normalize();
                for(int i=-2;i<=2;i++){
                    Vec3 p=ground(c.level,c.pos.add(forward.scale(i*2)));
                    if(p!=null&&visible(c,c.caster.getEyePosition(),p.add(0,.7,0))){
                        c.eruptionPoints.add(p);c.bossVisuals.add(BossesRiseBridge.sand(c,p,(i+2)*6));
                    }
                }
                if(c.eruptionPoints.isEmpty())return false;
                c.duration=55;
            }
            SpellAudio.world(c.level,c.pos,MOD+":entity.sandworm.emerge",.7f,1);return true;
        }
        if(c.spell.kind==Spell.Kind.BOSS_DRAGON){
            return beginDragon(c);
        }
        if(c.spell.kind==Spell.Kind.BOSS_TENTACLE){
            c.duration=90;c.bossVisuals.add(BossesRiseBridge.tentacle(c));
            water(c,c.pos,12);SpellAudio.world(c.level,c.pos,MOD+":item.undying_tentacle.spawn_tentacles",.75f,1);return true;
        }
        Vec3 forward=c.dir.multiply(1,0,1);if(forward.lengthSqr()<.01)forward=new Vec3(0,0,1);forward=forward.normalize();
        boolean wall=c.spell==Spell.YETIS_GLACIAL_WALL;
        Vec3 side=new Vec3(-forward.z,0,forward.x);
        int count=wall?7:5;
        for(int i=0;i<count;i++){
            Vec3 offset=wall?side.scale((i-3)*1.4).add(forward.scale(Math.abs(i-3)*.4)):forward.scale((i-2)*2);
            Vec3 p=ground(c.level,c.pos.add(offset));
            if(p!=null&&p.distanceToSqr(c.caster.position())<=(c.spell.range+1)*(c.spell.range+1)
                &&visible(c,c.caster.getEyePosition(),p.add(0,.7,0))){
                c.eruptionPoints.add(p);c.bossVisuals.add(BossesRiseBridge.ice(c,p,wall?18:12+c.eruptionPoints.size()*3));
            }
        }
        c.duration=wall?62:48;SpellAudio.world(c.level,c.pos,MOD+":entity.yeti.ice_barrage",.65f,1);return true;
    }
    static boolean tick(Cast c){
        if(c.spell.kind==Spell.Kind.BOSS_WRATH)return AdvancedSpells.tickWrath(c);
        switch(c.spell.kind){
            case BOSS_SWORD -> {
                if(c.age<24&&c.age%3==0)BossSpellEffects.warning(c,c.pos,c.spell.radius*(1-c.age/40.0));
                if(c.age<=24){
                    double progress=c.age/24.0;
                    Vec3 p=c.bossState.top.lerp(c.pos.add(0,BossesRiseBridge.SWORD_TIP_OFFSET,0),progress*progress);
                    for(var visual:c.bossVisuals)if(!visual.isRemoved())visual.setPos(p);
                    if(c.age%2==0)BossSpellEffects.rift(c,p,new Vec3(0,-1,0),false);
                }
                if(c.age>24){double radius=Math.min(c.spell.radius,(c.age-24)*.32);float old=c.weaponBonus;c.weaponBonus=old-c.spell.damage*.6f;
                    try{for(var victim:near(c.caster,c.pos,radius))if(victim.getBoundingBox().getCenter().distanceToSqr(c.pos.add(0,.7,0))<=radius*radius&&visible(c,c.pos.add(0,.7,0),victim.getBoundingBox().getCenter()))hit(c,victim,c.pos,true);}finally{c.weaponBonus=old;}}
                if(c.age==24){
                    area(c,c.pos.add(0,.7,0),3.5,true);
                    SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos,c.spell.radius);
                    BossesRiseBridge.swordImpact(c.level,c.pos);
                    BossSpellEffects.rift(c,c.pos,new Vec3(0,1,0),true);
                    SpellAudio.world(c.level,c.pos,MOD+":entity.knight.heavy_attack",1,.8f);
                }
            }
            case BOSS_TENTACLE -> {
                if(c.age<46&&c.age%3==0){
                    if(c.spell==Spell.KRAKENS_UNDERTOW)BossSpellEffects.water(c,c.age/46.0);
                    else BossSpellEffects.warning(c,c.pos,c.spell.radius);
                }
                if(c.age%8==1&&c.age<48)water(c,c.pos,3);
                if(c.spell==Spell.KRAKENS_UNDERTOW&&c.age>=8&&c.age<42&&c.age%4==0){
                    for(var v:near(c.caster,c.pos,c.spell.radius+2)){
                        Vec3 pull=c.pos.subtract(v.position()).multiply(1,0,1);
                        if(pull.lengthSqr()<.25||!visible(c,c.pos.add(0,.7,0),v.getBoundingBox().getCenter())
                            ||v instanceof net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity
                            ||v instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon
                            ||v instanceof net.minecraft.world.entity.boss.wither.WitherBoss)continue;
                        v.setDeltaMovement(v.getDeltaMovement().add(pull.normalize().scale(Math.min(.2,pull.length()*.035))));v.hurtMarked=true;
                    }
                }
                // 30 ticks rising, then 16 ticks into the attack animation before impact.
                if(c.age==46){
                    for(var v:near(c.caster,c.pos,c.spell.radius))if(v.getBoundingBox().getCenter().distanceToSqr(c.pos.add(0,.7,0))<=c.spell.radius*c.spell.radius&&visible(c,c.pos.add(0,.7,0),v.getBoundingBox().getCenter())){
                        hit(c,v,c.pos,true);effect(v,MobEffects.MOVEMENT_SLOWDOWN,60,2);
                    }
                    water(c,c.pos,16);BossesRiseBridge.tentacleImpact(c.level,c.pos);SpellAudio.world(c.level,c.pos,MOD+":item.undying_tentacle.smash_ground",1.4f,.8f);
                    SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos,c.spell.radius);
                }
            }
            case BOSS_ICE -> {
                boolean wall=c.spell==Spell.YETIS_GLACIAL_WALL;
                if(c.age<18&&c.age%4==0)for(Vec3 p:c.eruptionPoints)BossSpellEffects.ice(c,p,false);
                for(int i=0;i<c.eruptionPoints.size();i++)if(c.age==(wall?18:12+(i+1)*3)){
                    Vec3 p=c.eruptionPoints.get(i);area(c,p.add(0,.7,0),c.spell.radius,true);
                    c.level.sendParticles(ParticleTypes.SNOWFLAKE,p.x,p.y+.7,p.z,16,.5,.7,.5,.08);
                    BossesRiseBridge.iceImpact(c.level,p);
                    BossSpellEffects.ice(c,p,true);
                    SpellAudio.world(c.level,p,MOD+":entity.ice_gauntlet.icicle_spawning",.8f,1+i*.08f);
                    SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,p,p,1.5);
                }
            }
            case BOSS_RIFT -> {
                Vec3 old=c.pos;double step=Math.min(1.2,c.spell.range-c.distance);
                if(step<=.01)return false;
                Vec3 end=clipLoaded(c.level,old,c.dir,step,c.caster);
                var victim=target(c.caster,old,end,.4);
                if(victim!=null){riftImpact(c,victim.getBoundingBox().getCenter(),victim);return false;}
                c.distance+=old.distanceTo(end);c.pos=end;
                for(var e:c.bossVisuals)if(!e.isRemoved()){e.setPos(end);e.setDeltaMovement(c.dir.scale(1.2));}
                if(c.age%2==1){BossesRiseBridge.riftTrail(c.level,end);BossSpellEffects.rift(c,end,c.dir,false);SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.FLIGHT,end,end.add(c.dir),.7);}
                if(old.distanceTo(end)<step-.001||c.distance>=c.spell.range-.01){riftImpact(c,end,null);return false;}
            }
            case BOSS_SAND -> {
                if(c.age%3==0){
                    if(c.spell==Spell.SANDWORMS_QUICKSAND)BossSpellEffects.sand(c,c.age>=42);
                    else if(c.age<12)BossSpellEffects.warning(c,c.pos,c.spell.radius);
                }
                if(c.spell==Spell.SANDWORMS_ERUPTION){
                    for(int i=0;i<c.eruptionPoints.size();i++)if(c.age==12+i*6){
                        Vec3 p=c.eruptionPoints.get(i);area(c,p.add(0,.7,0),c.spell.radius,true);
                        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,p,p,c.spell.radius);
                        BossesRiseBridge.sandImpact(c.level,p);SpellAudio.world(c.level,p,MOD+":entity.sand_column.eruption",.8f,1);
                    }
                }else{
                    if(c.age<42&&c.age%6==0)for(var v:near(c.caster,c.pos,c.spell.radius)){
                        if(!visible(c,c.pos.add(0,.7,0),v.getBoundingBox().getCenter()))continue;
                        effect(v,MobEffects.MOVEMENT_SLOWDOWN,35,1);
                        if(movable(v)){Vec3 d=c.pos.subtract(v.position()).multiply(1,0,1);if(d.lengthSqr()>.25){v.setDeltaMovement(v.getDeltaMovement().add(d.normalize().scale(.16)));v.hurtMarked=true;}}
                    }
                    if(c.age==42){area(c,c.pos.add(0,.7,0),c.spell.radius,true);SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos,c.spell.radius);BossesRiseBridge.sandImpact(c.level,c.pos);SpellAudio.world(c.level,c.pos,MOD+":entity.sandworm.impact",.85f,1);}
                }
            }
            case BOSS_DRAGON -> {
                tickDragon(c);
            }
            default -> {return false;}
        }
        return true;
    }
    private static boolean beginDragon(Cast c){
        c.bossState=new State();
        if(c.spell==Spell.DRAGONS_PYRE){
            Vec3 top=clipLoaded(c.level,c.pos.add(0,1,0),new Vec3(0,1,0),8,c.caster).add(0,-1,0);
            if(top.y<c.pos.y+2)return false;
            c.bossState.top=top;
            Entity visual=BossesRiseBridge.flame(c,top);c.bossVisuals.add(visual);
            c.bossState.flames.add(new FlameShot(visual,top,new Vec3(0,-1,0),20));c.duration=54;
            SpellAudio.world(c.level,c.pos,MOD+":entity.dragon.roar",.8f,1.1f);
        }else{
            Vec3 side=c.dir.cross(new Vec3(0,1,0));if(side.lengthSqr()<.01)side=new Vec3(1,0,0);side=side.normalize();
            for(int i=0;i<3;i++){
                Vec3 eye=c.caster.getEyePosition(),offset=side.scale((i-1)*1.2).add(0,.5,0);
                Vec3 start=clipLoaded(c.level,eye,offset.normalize(),offset.length(),c.caster);
                Entity visual=BossesRiseBridge.flame(c,start);c.bossVisuals.add(visual);
                c.bossState.flames.add(new FlameShot(visual,start,c.pos.add(0,.8,0).subtract(start).normalize(),10+i*10));
            }
            c.duration=80;SpellAudio.world(c.level,c.pos,MOD+":entity.dragon.wing",.8f,.9f);
        }
        return true;
    }
    private static void tickDragon(Cast c){
        if(c.spell==Spell.DRAGONS_PYRE){
            FlameShot shot=c.bossState.flames.getFirst();
            if(c.impactAge==0){
                if(c.age%2==0){BossSpellEffects.warning(c,c.pos,c.spell.radius);BossSpellEffects.fire(c,shot.pos,shot.dir,true);}
                if(c.age>=20){
                    double t=Math.min(1,(c.age-20)/14.0);
                    Vec3 wanted=c.bossState.top.lerp(c.pos.add(0,.8,0),t*t);
                    Vec3 delta=wanted.subtract(shot.pos);
                    Vec3 end=delta.lengthSqr()<.0001?wanted:clipLoaded(c.level,shot.pos,delta.normalize(),delta.length(),c.caster);
                    boolean blocked=end.distanceToSqr(wanted)>.01;shot.pos=end;shot.visual.setPos(end);
                    if(blocked||t>=1){dragonHit(c,shot,end);c.impactAge=c.age;}
                }
            }else if(c.age-c.impactAge<=12&&c.age%2==0)BossSpellEffects.fireImpact(c,shot.pos,c.age-c.impactAge);
            return;
        }
        for(FlameShot shot:c.bossState.flames){
            if(shot.done)continue;
            if(c.age<shot.delay){if(c.age%3==0)BossSpellEffects.fire(c,shot.pos,shot.dir,true);continue;}
            if(c.age==shot.delay)SpellAudio.world(c.level,shot.pos,MOD+":entity.dragon.fireballs",.7f,1+(shot.delay-10)*.01f);
            Vec3 end=clipLoaded(c.level,shot.pos,shot.dir,.9,c.caster);
            var victim=target(c.caster,shot.pos,end,.4);
            boolean blocked=shot.pos.distanceTo(end)<.899;
            shot.distance+=shot.pos.distanceTo(end);shot.pos=end;shot.visual.setPos(end);
            if(c.age%2==0)BossSpellEffects.fire(c,end,shot.dir,false);
            if(victim!=null||blocked||shot.distance>=c.spell.range){
                Vec3 impact=end.subtract(shot.dir.scale(.05));dragonHit(c,shot,impact);
                if(victim!=null&&visible(c,impact,victim.getBoundingBox().getCenter()))hit(c,victim,impact,true);
            }
        }
    }
    private static void dragonHit(Cast c,FlameShot shot,Vec3 pos){
        shot.done=true;shot.visual.discard();c.hit.clear();area(c,pos,c.spell.radius,true);
        BossSpellEffects.fireImpact(c,pos,0);BossesRiseBridge.dragonImpact(c.level,pos,12);
        SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,pos,pos,c.spell.radius);
        SpellAudio.world(c.level,pos,MOD+":entity.dragon.firelayer",.8f,.9f);
    }
    static final class State {
        Vec3 top;
        final java.util.List<FlameShot> flames=new java.util.ArrayList<>();
    }
    private static final class FlameShot {
        final Entity visual;final Vec3 dir;final int delay;Vec3 pos;double distance;boolean done;
        FlameShot(Entity visual,Vec3 pos,Vec3 dir,int delay){this.visual=visual;this.pos=pos;this.dir=dir;this.delay=delay;}
    }
    private static void water(Cast c,Vec3 pos,int count){
        c.level.sendParticles(ParticleTypes.SPLASH,pos.x,pos.y+.2,pos.z,count,1,.3,1,.16);
        c.level.sendParticles(ParticleTypes.BUBBLE_POP,pos.x,pos.y+.5,pos.z,Math.max(2,count/2),.7,.5,.7,.04);
        BossesRiseBridge.waterCloud(c.level,pos,Math.min(6,count));
    }
    private static void riftImpact(Cast c,Vec3 pos,net.minecraft.world.entity.LivingEntity direct){
        BossesRiseBridge.riftClose(c.level,pos);
        BossSpellEffects.rift(c,pos,c.dir,true);
        SpellAudio.world(c.level,pos,MOD+":entity.barrage_rift.closing",.7f,1);
        if(c.spell==Spell.HELVARS_FRACTURE)area(c,pos,c.spell.radius,true);
        else if(direct!=null)hit(c,direct,pos,true);
    }
    private static boolean movable(net.minecraft.world.entity.LivingEntity v){
        return !(v instanceof net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity)
            &&!(v instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon)
            &&!(v instanceof net.minecraft.world.entity.boss.wither.WitherBoss);
    }
    static void cleanup(Cast c){for(var visual:c.bossVisuals)if(!visual.isRemoved())visual.discard();c.bossVisuals.clear();}
}
