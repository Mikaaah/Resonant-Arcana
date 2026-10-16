package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;

/** Reactive spell combat. Buffs are bounded casts, not permanent player attachments. */
final class AdvancedSpells {
    private static final Map<UUID,State> GUARDS=new HashMap<>(),BRANDS=new HashMap<>(),FORMATIONS=new HashMap<>();
    private static int procDepth;
    private AdvancedSpells(){}
    static void register(){NeoForge.EVENT_BUS.addListener(AdvancedSpells::incoming);NeoForge.EVENT_BUS.addListener(AdvancedSpells::melee);}
    static boolean handles(Spell.Kind kind){return switch(kind){case COUNTER,DRAW,REVERSAL,BRAND,BANNER,REBIRTH,FORMATION,BELL,BREAKER->true;default->false;};}
    static boolean canStart(ServerPlayer p,Spell s){
        if(s==Spell.CRIMSON_REVERSAL||s==Spell.ASHEN_REBIRTH)return SpellActions.blinkDestination(p,s,s.range)!=null;
        return true;
    }
    static boolean begin(Cast c){
        c.advanced=new State(c);State s=c.advanced;
        switch(c.spell.kind){
            case COUNTER,BRAND,FORMATION -> {
                Map<UUID,State> map=states(c.spell);State old=map.put(c.owner,s);if(old!=null)old.done=true;
                c.duration=c.spell.duration();s.charges=c.spell==Spell.PHANTOM_FORMATION?4+MartialSpells.rank(c):0;
                effect(c.caster,marker(c.spell),c.duration,0);if(c.spell==Spell.PHANTOM_FORMATION)buffVisual(c,s);else areaVisual(c,c.caster.position().add(0,.8,0),1.1);return true;
            }
            case DRAW -> {c.duration=14;c.anchor=clipLoaded(c.level,c.pos,c.dir,c.spell.range,c.caster);s.weapon=(float)Math.max(0,c.caster.getAttributeValue(Attributes.ATTACK_DAMAGE)*.5);line(c,c.pos,c.anchor,.4);return true;}
            case REVERSAL -> {
                Vec3 destination=SpellActions.blinkDestination(c.caster,c.spell,c.spell.range);if(destination==null)return false;
                Vec3 origin=c.caster.position(),end=destination.add(0,c.caster.getEyeHeight(),0);
                c.weaponBonus=(float)Math.max(0,c.caster.getAttributeValue(Attributes.ATTACK_DAMAGE)*.5);
                cut(c,c.pos,end,1);c.caster.teleportTo(destination.x,destination.y,destination.z);c.caster.fallDistance=0;
                effect(c.caster,MobEffects.SLOW_FALLING,40,0);line(c,origin.add(0,1,0),destination.add(0,1,0),1.2);return false;
            }
            case REBIRTH -> {
                Vec3 destination=SpellActions.blinkDestination(c.caster,c.spell,c.spell.range);if(destination==null)return false;
                c.pos=c.caster.position();c.duration=c.spell.duration();
                line(c,c.pos.add(0,1,0),destination.add(0,1,0),1.8);
                c.caster.teleportTo(destination.x,destination.y,destination.z);c.caster.fallDistance=0;effect(c.caster,MobEffects.SLOW_FALLING,60,0);
                return true;
            }
            case BANNER -> {
                c.pos=ground(c,c.anchor);if(c.pos==null)c.pos=c.caster.position();c.duration=c.spell.duration();s.bannerPos=placeBanner(c);rally(c,s);return true;
            }
            case BELL -> {c.pos=c.caster.position().add(0,.7,0);c.duration=c.spell.duration();return true;}
            case BREAKER -> {breaker(c);return false;}
            default -> throw new IllegalArgumentException("Unknown advanced spell: "+c.spell);
        }
    }
    static boolean tick(Cast c){
        State s=c.advanced;if(s==null||s.done)return false;
        switch(c.spell.kind){
            case COUNTER,BRAND,FORMATION -> {
                c.pos=c.caster.position();if(!c.caster.hasEffect(marker(c.spell)))return false;
                if(c.age%8==1)buffVisual(c,s);
                if(c.spell==Spell.PHANTOM_FORMATION){
                    for(var it=s.blades.iterator();it.hasNext();){Blade b=it.next();if(!blade(c,b))it.remove();}
                    if(s.charges==0&&s.blades.isEmpty())return false;
                }
            }
            case DRAW -> {
                if(c.age==1||c.age==7)line(c,c.pos,c.anchor,.45);
                if(c.age==12){c.weaponBonus=s.weapon;cut(c,c.pos,c.anchor,1);line(c,c.pos,c.anchor,1.6);sound(c,c.anchor,"irons_spellbooks:entity.generic.swing_blade",1.2f,.65f);return false;}
            }
            case REBIRTH -> {
                if(c.age<16&&c.age%3==1){
                    for(int i=0;i<6;i++)trail(c.level,c.pos.add(Math.sin(i)*.35,i*.27,Math.cos(i)*.2),c.spell.theme,.03);
                    areaVisual(c,c.pos.add(0,.8,0),.9);
                }
                if(c.age==16){area(c,c.pos.add(0,.5,0),c.spell.radius,true);burst(c,c.pos.add(0,1,0));sound(c,c.pos,"entity.phantom.flap",1.5f,.6f);return false;}
            }
            case BANNER -> {
                if(c.age%20==1){rally(c,s);bannerVisual(c);}
                if(c.age==c.duration){SpellActions.healArea(c,c.pos,c.spell.radius,c.spell.healing(),false);burst(c,c.pos.add(0,2,0));sound(c,c.pos,"block.beacon.activate",1,.8f);return false;}
            }
            case BELL -> {
                int wave=(c.age-1)/22,elapsed=(c.age-1)%22;if(wave>=3)return false;
                if(elapsed==0){c.hit.clear();sound(c,c.pos,"block.bell.use",1.6f,wave==2?.55f:.85f-wave*.1f);sound(c,c.pos,"entity.warden.sonic_boom",.8f,.65f);}
                if(elapsed<=14){
                    double radius=Math.min(c.spell.radius,elapsed*.65+.5);
                    for(var v:near(c.caster,c.pos,radius+1)){
                        double distance=v.getBoundingBox().getCenter().subtract(c.pos).multiply(1,0,1).length();
                        if(Math.abs(distance-radius)<=1&&visible(c,c.pos,v.getBoundingBox().getCenter())){scaledHit(c,v,c.pos,wave==2?3:1,true);effect(v,MobEffects.MOVEMENT_SLOWDOWN,20,1);shove(c,v,c.pos,.45);}
                    }
                    if(elapsed%2==0){
                        for(int i=0;i<12;i++){double a=i*Math.PI/6;Vec3 p=c.pos.add(Math.cos(a)*radius,0,Math.sin(a)*radius);if(visible(c,c.pos,p))trail(c.level,p,c.spell.theme,.04);}
                        areaVisual(c,c.pos,radius*.35+.4);
                    }
                }
            }
            default -> {return false;}
        }
        return true;
    }
    private static void incoming(LivingIncomingDamageEvent event){
        if(event.isCanceled()||event.getAmount()<=0)return;
        if(procDepth==0&&event.getSource().is(DamageTypes.PLAYER_ATTACK)&&event.getSource().getEntity() instanceof ServerPlayer attacker&&allowed(attacker,event.getEntity())){
            State brand=BRANDS.get(attacker.getUUID());
            if(valid(brand)&&brand.charges==2&&attacker.hasEffect(ArcanaEffects.THUNDERBRAND))event.setAmount(event.getAmount()+(float)(brand.c.spell.damage*SpellActions.power(brand.c)*ArcanaConfig.DAMAGE.get()));
        }
        if(!(event.getEntity() instanceof ServerPlayer player))return;
        State s=GUARDS.get(player.getUUID());if(!valid(s)||s.c.age>=s.c.duration||!player.hasEffect(ArcanaEffects.GLACIAL_GUARD))return;
        Entity owner=event.getSource().getEntity(),direct=event.getSource().getDirectEntity();
        if(!(owner instanceof LivingEntity living)||!allowed(player,living)||event.getSource().is(DamageTypeTags.BYPASSES_SHIELD))return;
        Vec3 source=(direct==null?owner:direct).position().subtract(player.position()).multiply(1,0,1).normalize();
        if(source.dot(player.getLookAngle().multiply(1,0,1).normalize())<.25)return;
        s.done=true;GUARDS.remove(player.getUUID(),s);player.removeEffect(ArcanaEffects.GLACIAL_GUARD);
        boolean perfect=s.c.age<=6;if(perfect)event.setCanceled(true);else event.setAmount(event.getAmount()*.3f);
        s.c.pos=player.getEyePosition();
        for(var target:near(player,s.c.pos,s.c.spell.range))if(inCone(s.c,target,s.c.spell.range,.5))scaledHit(s.c,target,s.c.pos,perfect?1.75f:1,true);
        burst(s.c,player.getEyePosition().add(player.getLookAngle().scale(1.5)));
        areaVisual(s.c,player.position().add(player.getLookAngle().scale(2)),2);sound(s.c,player.position(),"block.glass.break",1.2f,perfect?.65f:1.2f);
    }
    private static void melee(LivingDamageEvent.Post event){
        if(procDepth>0||event.getNewDamage()<=0||!(event.getSource().getEntity() instanceof ServerPlayer player)||!event.getSource().is(DamageTypes.PLAYER_ATTACK)||!allegianceAllows(player,event.getEntity()))return;
        State brand=BRANDS.get(player.getUUID());
        if(valid(brand)&&player.hasEffect(ArcanaEffects.THUNDERBRAND)&&++brand.charges>=3){
            brand.charges=0;Cast c=brand.c;c.hit.clear();c.hit.add(event.getEntity().getUUID());Vec3 from=event.getEntity().getBoundingBox().getCenter();
            Vec3 firstOrigin=from;LivingEntity current=near(player,from,c.spell.radius).stream().filter(v->!c.hit.contains(v.getUUID())&&visible(c,firstOrigin,v.getBoundingBox().getCenter())).min(Comparator.comparingDouble(v->v.distanceToSqr(event.getEntity()))).orElse(null);
            for(int i=0;i<2&&current!=null;i++){
                if(current.isAlive()&&visible(c,from,current.getBoundingBox().getCenter())){scaledHit(c,current,from,1,true);line(c,from,current.getBoundingBox().getCenter(),1);}
                Vec3 origin=current.getBoundingBox().getCenter();current=near(player,origin,c.spell.radius).stream().filter(v->!c.hit.contains(v.getUUID())&&visible(c,origin,v.getBoundingBox().getCenter())).min(Comparator.comparingDouble(v->v.distanceToSqr(origin))).orElse(null);from=origin;
            }
            sound(c,event.getEntity().position(),"irons_spellbooks:lightning_lance_cast",.9f,1.3f);
        }
        State formation=FORMATIONS.get(player.getUUID());
        if(valid(formation)&&formation.charges>0&&player.hasEffect(ArcanaEffects.PHANTOM_FORMATION)){
            formation.charges--;Vec3 start=player.getEyePosition();Vec3 direction=event.getEntity().getBoundingBox().getCenter().subtract(start).normalize();
            formation.blades.add(new Blade(start,direction));line(formation.c,start,start.add(direction),.65);
        }
    }
    private static boolean blade(Cast c,Blade b){
        b.age++;if(b.age<=10){b.pos=c.caster.getEyePosition();return true;}
        double step=Math.min(.8,c.spell.range-b.distance);if(step<=0)return false;
        Vec3 end=clipLoaded(c.level,b.pos,b.dir,step,c.caster);var victim=target(c.caster,b.pos,end,c.spell.radius);
        if(victim!=null){c.hit.clear();scaledHit(c,victim,b.pos,1,true);burst(c,victim.getBoundingBox().getCenter());return false;}
        line(c,b.pos,end,.55);b.distance+=b.pos.distanceTo(end);boolean alive=end.distanceTo(b.pos)>=step-.01&&b.distance<c.spell.range;b.pos=end;return alive;
    }
    private static boolean valid(State s){return s!=null&&!s.done&&s.c.caster.isAlive()&&s.c.caster.serverLevel()==s.c.level&&s.c.age<s.c.duration;}
    private static Map<UUID,State> states(Spell spell){return spell==Spell.GLACIAL_COUNTER?GUARDS:spell==Spell.THUNDERBRAND?BRANDS:FORMATIONS;}
    private static net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> marker(Spell spell){return spell==Spell.GLACIAL_COUNTER?ArcanaEffects.GLACIAL_GUARD:spell==Spell.THUNDERBRAND?ArcanaEffects.THUNDERBRAND:ArcanaEffects.PHANTOM_FORMATION;}
    static void clear(){GUARDS.clear();BRANDS.clear();FORMATIONS.clear();procDepth=0;}
    static void cleanup(Cast c){
        if(c.advanced!=null&&c.spell.kind==Spell.Kind.BANNER&&c.advanced.bannerPos!=null){
            BlockPos pos=c.advanced.bannerPos;
            if(c.level.hasChunkAt(pos)&&c.level.getBlockState(pos).is(Blocks.YELLOW_BANNER))c.level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
        }
        if(c.advanced!=null&&(c.spell==Spell.GLACIAL_COUNTER||c.spell==Spell.THUNDERBRAND||c.spell==Spell.PHANTOM_FORMATION)){
            var map=states(c.spell);if(map.remove(c.owner,c.advanced))c.caster.removeEffect(marker(c.spell));
        }
    }
    private static void rally(Cast c,State s){
        for(var ally:SpellActions.allies(c,c.pos,c.spell.radius)){
            effect(ally,MobEffects.DAMAGE_RESISTANCE,30,0);
            if(s.buffed.add(ally.getUUID())){
                float amount=4*Math.clamp(SpellActions.power(c),1,2),old=ally.getAbsorptionAmount();
                effect(ally,MobEffects.ABSORPTION,c.duration-c.age+30,Math.max(0,(int)Math.ceil(amount/4)-1));ally.setAbsorptionAmount(Math.max(old,amount));
            }
        }
    }
    private static void bannerVisual(Cast c){
        c.level.sendParticles(ParticleTypes.END_ROD,c.pos.x,c.pos.y+1.8,c.pos.z,1,.18,.45,.18,.005);
        c.level.sendParticles(ParticleTypes.HAPPY_VILLAGER,c.pos.x,c.pos.y+2.2,c.pos.z,2,.35,.35,.35,.01);
        areaVisual(c,c.pos,1.7);
    }
    private static BlockPos placeBanner(Cast c){
        BlockPos pos=BlockPos.containing(c.pos);
        if(!c.level.hasChunkAt(pos)||!c.level.getBlockState(pos).isAir()||!c.level.getBlockState(pos.below()).isFaceSturdy(c.level,pos.below(),Direction.UP))return null;
        var stack=Items.YELLOW_BANNER.getDefaultInstance();
        if(!c.level.mayInteract(c.caster,pos)||!c.caster.mayUseItemAt(pos,Direction.UP,stack))return null;
        var state=Blocks.YELLOW_BANNER.defaultBlockState();
        return state.canSurvive(c.level,pos)&&c.level.setBlock(pos,state,3)?pos.immutable():null;
    }
    private static void buffVisual(Cast c,State s){
        if(c.spell==Spell.PHANTOM_FORMATION){
            Vec3 behind=c.caster.getEyePosition().subtract(c.caster.getLookAngle().multiply(1,0,1).normalize().scale(.9));
            for(int i=0;i<s.charges;i++){double a=(i-(s.charges-1)/2.0)*.35;Vec3 p=behind.add(Math.sin(a)*1.7,.3+Math.cos(a)*.5,0);
                for(int k=0;k<4;k++)trail(c.level,p.add(0,k*.2,0),c.spell.theme,.005);}
        }else areaVisual(c,c.caster.position().add(0,.9,0),c.spell==Spell.GLACIAL_COUNTER?1.4:.65);
    }
    private static void breaker(Cast c){
        c.weaponBonus=(float)Math.max(0,c.caster.getAttributeValue(Attributes.ATTACK_DAMAGE)*.5);
        for(var target:near(c.caster,c.pos,c.spell.range))if(inCone(c,target,c.spell.range,.5)){
            hit(c,target,c.pos,true);interrupt(c.caster,target);
        }
        var shots=c.level.getEntitiesOfClass(Projectile.class,c.caster.getBoundingBox().inflate(c.spell.range),p->{
            Entity owner=p.getOwner();return !(p instanceof BossSpellVisual)&&owner!=c.caster&&(owner==null||allowed(c.caster,owner))&&inCone(c,p,c.spell.range,.5);
        });
        shots.stream().sorted(Comparator.comparingDouble(p->p.distanceToSqr(c.caster))).limit(32).forEach(p->{burst(c,p.position());p.discard();});
        areaVisual(c,c.pos.add(c.dir.scale(2)),2);line(c,c.pos,clipLoaded(c.level,c.pos,c.dir,c.spell.range,c.caster),1.1);
    }
    static boolean interrupt(ServerPlayer caster,LivingEntity victim){
        if(!allowed(caster,victim))return false;
        boolean casting=victim instanceof ServerPlayer p?MagicData.getPlayerMagicData(p).isCasting():victim instanceof IMagicEntity m&&m.isCasting();
        if(!casting)return false;
        var data=victim instanceof IMagicEntity m?m.getMagicData():MagicData.getPlayerMagicData(victim);
        var active=data.getCastingSpell();if(active==null||!active.getSpell().canBeInterrupted(victim instanceof net.minecraft.world.entity.player.Player p?p:null))return false;
        var event=new io.redspace.ironsspellbooks.api.events.CounterSpellEvent(caster,victim);NeoForge.EVENT_BUS.post(event);if(event.isCanceled())return false;
        if(victim instanceof ServerPlayer p)io.redspace.ironsspellbooks.api.util.Utils.serverSideCancelCast(p);
        else if(victim instanceof IMagicEntity m)m.cancelCast();return true;
    }
    private static boolean inCone(Cast c,Entity target,double range,double cosine){
        Vec3 d=target.getBoundingBox().getCenter().subtract(c.caster.getEyePosition());return d.lengthSqr()<=range*range&&d.normalize().dot(c.caster.getLookAngle())>=cosine&&visible(c,c.caster.getEyePosition(),target.getBoundingBox().getCenter());
    }
    private static void cut(Cast c,Vec3 start,Vec3 end,float multiplier){
        for(var v:c.level.getEntitiesOfClass(LivingEntity.class,new AABB(start,end).inflate(c.spell.radius+1),e->allowed(c.caster,e)))
            if((v.getBoundingBox().inflate(c.spell.radius).clip(start,end).isPresent()||v.getBoundingBox().inflate(c.spell.radius).contains(start))&&visible(c,start,v.getBoundingBox().getCenter()))scaledHit(c,v,start,multiplier,true);
    }
    private static void scaledHit(Cast c,LivingEntity target,Vec3 from,float multiplier,boolean once){
        float bonus=c.weaponBonus;c.weaponBonus+=(c.spell.damage+bonus)*(multiplier-1);procDepth++;
        try{hit(c,target,from,once);}finally{c.weaponBonus=bonus;procDepth--;}
    }
    private static void shove(Cast c,LivingEntity v,Vec3 from,double amount){
        double k=1-Math.clamp(v.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE),0,1);Vec3 d=v.position().subtract(from).multiply(1,0,1).normalize().scale(amount*k);v.push(d.x,.15*k,d.z);v.hurtMarked=true;
    }
    private static Vec3 ground(Cast c,Vec3 wanted){
        Vec3 start=wanted.add(0,1,0),end=wanted.add(0,-5,0);if(!c.level.hasChunkAt(BlockPos.containing(start))||!c.level.hasChunkAt(BlockPos.containing(end)))return null;
        var h=c.level.clip(new ClipContext(start,end,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,c.caster));return h.getType()==HitResult.Type.BLOCK&&h.getDirection()==net.minecraft.core.Direction.UP?h.getLocation().add(0,.02,0):null;
    }
    static boolean beginWrath(Cast c){
        c.pos=BossesRiseCompat.ground(c.level,c.target==null?c.anchor:c.target.position());if(c.pos==null)return false;
        c.duration=150;c.advanced=new State(c);return true;
    }
    static boolean tickWrath(Cast c){
        if(c.age==1||c.age==11||c.age==21||c.age==31){
            int index=(c.age-1)/10;double a=index*Math.PI/2;Vec3 wanted=c.pos.add(Math.cos(a)*3,0,Math.sin(a)*3);Vec3 p=BossesRiseCompat.ground(c.level,wanted);
            if(p!=null&&visible(c,c.caster.getEyePosition(),p.add(0,.7,0))){c.eruptionPoints.add(p);spawnTentacle(c,p);}else c.eruptionPoints.add(null);
        }
        if(c.age>=47&&c.age<=77&&(c.age-47)%10==0){
            int i=(c.age-47)/10;Vec3 p=c.eruptionPoints.get(i);if(p!=null){area(c,p.add(0,.7,0),2.5,true);BossesRiseBridge.tentacleImpact(c.level,p);sound(c,p,BossesRiseCompat.MOD+":item.undying_tentacle.smash_ground",1,.8f);}
        }
        if(c.age==60)spawnTentacle(c,c.pos);
        if(c.age==106){
            c.hit.clear();for(var v:near(c.caster,c.pos,c.spell.radius))if(visible(c,c.pos.add(0,.7,0),v.getBoundingBox().getCenter()))scaledHit(c,v,c.pos,3,true);
            BossesRiseBridge.tentacleImpact(c.level,c.pos);sound(c,c.pos,BossesRiseCompat.MOD+":item.undying_tentacle.smash_ground",1.7f,.55f);
        }
        if(c.age%8==1){c.level.sendParticles(ParticleTypes.SPLASH,c.pos.x,c.pos.y+.2,c.pos.z,12,2,.2,2,.1);areaVisual(c,c.pos,2);}
        return c.age<149;
    }
    private static void spawnTentacle(Cast c,Vec3 point){Vec3 old=c.pos;c.pos=point;try{c.bossVisuals.add(BossesRiseBridge.tentacle(c));}finally{c.pos=old;}}
    private static void areaVisual(Cast c,Vec3 p,double size){SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,p,p,Math.min(4,size));}
    private static void line(Cast c,Vec3 a,Vec3 b,double size){SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,a,b,size);}
    private static void sound(Cast c,Vec3 p,String id,float volume,float pitch){SpellAudio.world(c.level,p,id,volume,pitch);}
    static final class State {final Cast c;boolean done;int charges;float weapon;BlockPos bannerPos;final Set<UUID> buffed=new HashSet<>();final List<Blade> blades=new ArrayList<>();State(Cast c){this.c=c;}}
    private static final class Blade {Vec3 pos;final Vec3 dir;int age;double distance;Blade(Vec3 p,Vec3 d){pos=p;dir=d;}}
}
