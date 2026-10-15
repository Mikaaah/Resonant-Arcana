package nl.townsandkingdoms.kingdomwands;

import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

/** Server-authoritative, bounded spell simulation. No block edits and no Bukkit code. */
public final class SpellEngine {
    private static final List<Cast> ACTIVE=new ArrayList<>();
    private static final Map<UUID,EnumMap<Spell,Long>> COOLDOWNS=new HashMap<>();
    private static final Map<UUID,Long> LAST_ACTION=new HashMap<>();
    private static long ticks;
    private static int visualPoints;
    private SpellEngine() {}
    public static int activeCount(){return ACTIVE.size();}
    public static void clear(){ACTIVE.clear();COOLDOWNS.clear();LAST_ACTION.clear();ticks=0;visualPoints=0;}
    public static void action(ServerPlayer player,int action) {
        if(action<0||action>2||!player.isAlive()||player.isSpectator())return;
        ItemStack stack=player.getMainHandItem();
        if(!(stack.getItem() instanceof WandItem wand))return;
        if(LAST_ACTION.getOrDefault(player.getUUID(),Long.MIN_VALUE)==ticks)return;
        LAST_ACTION.put(player.getUUID(),ticks);
        if(action==0)cast(player,wand.selected(stack));else wand.cycle(player,stack,action==1?1:-1);
    }
    public static boolean cast(ServerPlayer player,Spell spell) {
        if(!player.isAlive()||player.isSpectator())return false;
        var cooldowns=COOLDOWNS.computeIfAbsent(player.getUUID(),id->new EnumMap<>(Spell.class));
        long left=cooldowns.getOrDefault(spell,0L)-ticks;
        if(left>0){message(player,spell.title+": "+String.format(Locale.ROOT,"%.1f",left/20.0)+"s");return false;}
        if(ACTIVE.size()>=KingdomWands.MAX_ACTIVE.get()||ACTIVE.stream().filter(c->c.owner.equals(player.getUUID())).count()>=8){message(player,"Too many active spells");return false;}
        ServerLevel level=player.serverLevel();
        Vec3 start=player.getEyePosition(), direction=player.getLookAngle().normalize();
        Vec3 aim=clipLoaded(level,start,direction,spell.range,player);
        LivingEntity target=target(player,start,aim,0.5);
        if(requiresTarget(spell)&&target==null){message(player,"Aim at a living target within range");return false;}
        Cast cast=new Cast(player,spell,start,direction,aim,target);
        boolean timed=true;
        switch(spell.kind){
            case TARGET, CONFUSE, STUN, LAUNCH, PUSH, PULL -> {
                timed=false;
                if(target==null){message(player,"Aim at a living target within range");return false;}
                hit(cast,target,target.position(),true);
                burst(level,target.getBoundingBox().getCenter(),spell.theme);
            }
            case BURST -> {timed=false;Vec3 loc=target!=null?target.getBoundingBox().getCenter():aim;area(cast,loc,spell.radius,true);burst(level,loc,spell.theme);}
            case LIGHTNING,SMITE -> {
                timed=false;Vec3 loc=target!=null?target.position():aim;
                lightning(level,loc,spell.theme);area(cast,loc,spell.radius,true);
            }
            case CHAIN -> {timed=false;chain(cast,target);}
            case STORM, WELL -> {cast.pos=target!=null?target.position():aim;cast.duration=60;}
            case AURA -> {cast.pos=player.position();cast.duration=60;}
            case LEAP -> {timed=false;player.setDeltaMovement(direction.scale(1.15).add(0,0.4,0));player.hurtMarked=true;trail(level,start,spell.theme,0.25);}
            case CAPTURE, CHOKE -> {cast.anchor=target.position();cast.duration=60;}
            case PULSE -> {cast.pos=player.position().add(0,0.5,0);cast.duration=14;}
            case WARD -> cast.duration=80;
            case TWISTER -> {cast.pos=target!=null?target.position():aim;cast.duration=40;}
            case CHANNEL -> cast.duration=30;
            default -> cast.duration=(int)Math.ceil(spell.range/1.1)+2;
        }
        if(timed)ACTIVE.add(cast);
        int cooldown=Math.max(1,(int)Math.ceil(spell.cooldown*KingdomWands.COOLDOWNS.get()));
        cooldowns.put(spell,ticks+cooldown);
        ring(level,player.position().add(0,0.12,0),0.7,spell.theme,0);
        SpellAudio.cast(player,spell.theme);
        message(player,spell.title);return true;
    }
    private static boolean requiresTarget(Spell spell){return switch(spell.kind){case TARGET,CONFUSE,STUN,CAPTURE,LAUNCH,PUSH,PULL,CHOKE,CHAIN,SEEKER->true;default->false;};}
    public static void tick(){
        ticks++;visualPoints=0;
        for(Iterator<Cast> it=ACTIVE.iterator();it.hasNext();){Cast c=it.next();
            ServerPlayer owner=c.level.getServer().getPlayerList().getPlayer(c.owner);
            // Fake players are only allowed through the explicitly gated integration fixture.
            if(owner==null&&IntegrationChecks.enabled())owner=c.testOwner;
            if(owner==null||!owner.isAlive()||owner.isSpectator()||owner.serverLevel()!=c.level||c.age++>=c.duration||!c.level.hasChunkAt(BlockPos.containing(c.pos))){it.remove();continue;}
            c.caster=owner;
            if(!advance(c))it.remove();
        }
        if(ticks%200==0){
            COOLDOWNS.values().forEach(map->map.entrySet().removeIf(e->e.getValue()<=ticks));
            COOLDOWNS.entrySet().removeIf(e->e.getValue().isEmpty());
            LAST_ACTION.entrySet().removeIf(e->ticks-e.getValue()>200);
        }
    }
    private static boolean advance(Cast c){
        // Sustained cues are bounded to one per cast per second.
        if(c.age%20==1)SpellAudio.world(c.level,c.pos,SpellAudio.sustainId(c.spell.theme),0.55f,1.0f);
        switch(c.spell.kind){
            case BOLT,COMET,WAVE,SEEKER -> {
                if(c.spell.kind==Spell.Kind.SEEKER){
                    if(c.target==null||!c.target.isAlive()||c.target.level()!=c.level||!allowed(c.caster,c.target))return false;
                    Vec3 desired=c.target.getBoundingBox().getCenter().subtract(c.pos).normalize();
                    c.travel=c.travel.scale(0.65).add(desired.scale(0.35)).normalize();
                }
                Vec3 next=c.pos.add(c.travel.scale(c.spell.kind==Spell.Kind.COMET?0.9:1.1));
                if(!c.level.hasChunkAt(BlockPos.containing(next)))return false;
                var block=c.level.clip(new ClipContext(c.pos,next,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,c.caster));
                Vec3 end=block.getLocation();
                if(c.spell.kind==Spell.Kind.WAVE){
                    for(var entity:near(c.caster,c.pos.add(end).scale(0.5),c.spell.radius+1))
                        if(entity.getBoundingBox().inflate(c.spell.radius).clip(c.pos,end).isPresent()||entity.getBoundingBox().inflate(c.spell.radius).contains(c.pos)) { if(visible(c,c.pos,entity.getBoundingBox().getCenter()))hit(c,entity,end,true); }
                    waveVisual(c,end);
                } else {
                    LivingEntity victim=target(c.caster,c.pos,end,c.spell.kind==Spell.Kind.COMET?0.65:0.3);
                    if(victim!=null){Vec3 impact=victim.getBoundingBox().getCenter();area(c,impact,c.spell.radius,true);burst(c.level,impact,c.spell.theme);return false;}
                    for(int s=0;s<3;s++){
                        Vec3 point=c.pos.lerp(end,s/3.0);trail(c.level,point,c.spell.theme,0.06);
                        if(c.spell.kind==Spell.Kind.COMET){
                            Vec3 side=c.travel.cross(new Vec3(0,1,0));if(side.lengthSqr()<0.01)side=new Vec3(1,0,0);side=side.normalize();
                            Vec3 up=side.cross(c.travel).normalize();double angle=c.age*0.7+s*0.25;
                            Vec3 spiral=side.scale(Math.cos(angle)*0.35).add(up.scale(Math.sin(angle)*0.35));
                            trail(c.level,point.add(spiral),c.spell.theme,0.015);trail(c.level,point.subtract(spiral),c.spell.theme,0.015);
                        }
                    }
                }
                c.pos=end;
                if(block.getType()!=HitResult.Type.MISS){if(c.spell.kind!=Spell.Kind.WAVE){area(c,end,c.spell.radius,true);burst(c.level,end,c.spell.theme);}return false;}
            }
            case PULSE -> {
                double radius=c.spell.radius*c.age/c.duration;
                ring(c.level,c.pos,radius,c.spell.theme,c.age*0.1);
                for(var victim:near(c.caster,c.pos,radius+1)){
                    double d=victim.position().distanceTo(c.pos);
                    if(Math.abs(d-radius)<1.5&&visible(c,c.pos,victim.getBoundingBox().getCenter()))hit(c,victim,c.pos,true);
                }
            }
            case CAPTURE, CHOKE -> {
                LivingEntity victim=c.target;
                if(victim==null||!victim.isAlive()||victim.level()!=c.level||!allowed(c.caster,victim)||victim.position().distanceToSqr(c.anchor)>16)return false;
                // Do not teleport a target back across a wall or a large external movement.
                victim.setDeltaMovement(Vec3.ZERO);victim.hurtMarked=true;
                effect(victim,MobEffects.MOVEMENT_SLOWDOWN,5,10);effect(victim,MobEffects.WEAKNESS,5,2);
                if(c.spell.kind==Spell.Kind.CHOKE&&c.age%20==1){c.hit.clear();hit(c,victim,c.anchor,true);}
                if(c.age%4==0)ring(c.level,victim.position().add(0,0.2+c.age%12*0.12,0),0.8,c.spell.theme,c.age*0.2);
            }
            case WELL, STORM, AURA -> {
                if(c.spell.kind==Spell.Kind.AURA)c.pos=c.caster.position().add(0,0.3,0);
                if(c.age%4==1){
                    ring(c.level,c.pos.add(0,0.15,0),c.spell.radius,c.spell.theme,c.age*0.15);
                    ring(c.level,c.pos.add(0,1.0,0),c.spell.radius*0.45,c.spell.theme,-c.age*0.2);
                }
                if(c.age%20==1){
                    c.hit.clear();area(c,c.pos,c.spell.radius,true);
                    if(c.spell.kind==Spell.Kind.STORM){
                        var victims=near(c.caster,c.pos,c.spell.radius);
                        int bolts=0;for(var victim:victims)if(visible(c,c.pos,victim.getBoundingBox().getCenter())&&bolts++<3)lightning(c.level,victim.position(),c.spell.theme);
                    }
                }
                if(c.spell.kind==Spell.Kind.WELL)for(var victim:near(c.caster,c.pos,c.spell.radius))if(visible(c,c.pos,victim.getBoundingBox().getCenter())){
                    Vec3 delta=c.pos.subtract(victim.position()).multiply(1,0,1);
                    Vec3 pull=delta.normalize().scale(Math.min(0.18,delta.length()));
                    victim.setDeltaMovement(pull.add(0,victim.getDeltaMovement().y*0.7,0));victim.hurtMarked=true;
                }
            }
            case TWISTER -> {
                if(c.age%2==0)for(int i=0;i<3;i++)ring(c.level,c.pos.add(0,i*0.65,0),c.spell.radius*(1-i*0.22),c.spell.theme,c.age*0.35+i);
                if(c.age%10==1){c.hit.clear();area(c,c.pos,c.spell.radius,true);}
            }
            case CHANNEL -> {
                if(c.age%5!=1)return true;
                c.hit.clear();Vec3 eye=c.caster.getEyePosition(),dir=c.caster.getLookAngle();
                Vec3 end=clipLoaded(c.level,eye,dir,c.spell.range,c.caster);
                double length=eye.distanceTo(end);
                for(double d=0;d<=length;d+=0.6){Vec3 p=eye.add(dir.scale(d));trail(c.level,p,c.spell.theme,0.08+d*0.015);}
                for(var victim:near(c.caster,eye.add(end).scale(0.5),length*0.5+2))
                    if(victim.getBoundingBox().inflate(c.spell.radius).clip(eye,end).isPresent())hit(c,victim,eye,true);
            }
            case WARD -> {
                c.pos=c.caster.position().add(0,1,0);
                if(c.age%4==1)ring(c.level,c.pos,c.spell.radius,c.spell.theme,c.age*0.15);
                for(var p:c.level.getEntitiesOfClass(Projectile.class,new AABB(c.pos,c.pos).inflate(c.spell.radius),p->p.getOwner()!=c.caster)) {
                    if(p.getOwner()!=null&&!allowed(c.caster,p.getOwner()))continue;
                    if(p.getDeltaMovement().dot(p.position().subtract(c.pos))<0){
                        p.setDeltaMovement(p.position().subtract(c.pos).normalize().scale(Math.max(0.5,p.getDeltaMovement().length())));p.hurtMarked=true;
                    }
                }
            }
            default -> {return false;}
        }
        return true;
    }
    private static void area(Cast c,Vec3 pos,double radius,boolean once){
        for(LivingEntity entity:near(c.caster,pos,radius))if(visible(c,pos,entity.getBoundingBox().getCenter()))hit(c,entity,pos,once);
    }
    public static boolean allowed(ServerPlayer caster,Entity entity){
        if(entity==caster||!entity.isAlive()||entity.isSpectator()||entity instanceof ArmorStand)return false;
        if(entity instanceof net.minecraft.world.entity.player.Player other&&(!KingdomWands.PVP.get()||!caster.server.isPvpAllowed()||!caster.canHarmPlayer(other)))return false;
        if(entity instanceof TamableAnimal tame&&caster.getUUID().equals(tame.getOwnerUUID()))return false;
        var team=caster.getTeam();
        return team==null||team.isAllowFriendlyFire()||entity.getTeam()!=team;
    }
    private static List<LivingEntity> near(ServerPlayer player,Vec3 pos,double radius){
        return player.serverLevel().getEntitiesOfClass(LivingEntity.class,new AABB(pos,pos).inflate(radius),e->allowed(player,e)&&e.getBoundingBox().getCenter().distanceToSqr(pos)<(radius+1)*(radius+1));
    }
    private static LivingEntity target(ServerPlayer caster,Vec3 start,Vec3 end,double margin){
        LivingEntity result=null;double distance=start.distanceToSqr(end)+1;
        for(var entity:caster.serverLevel().getEntitiesOfClass(LivingEntity.class,new AABB(start,end).inflate(margin+1),e->allowed(caster,e))){
            AABB box=entity.getBoundingBox().inflate(margin);var point=box.clip(start,end);
            double d=box.contains(start)?0:point.map(start::distanceToSqr).orElse(Double.MAX_VALUE);
            if(d<distance&&caster.serverLevel().clip(new ClipContext(start,entity.getBoundingBox().getCenter(),ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,caster)).getType()==HitResult.Type.MISS){distance=d;result=entity;}
        }
        return result;
    }
    private static Vec3 clipLoaded(ServerLevel level,Vec3 start,Vec3 dir,double range,Entity caster){
        Vec3 end=start;
        for(double d=0.5;d<=range;d+=0.5){Vec3 p=start.add(dir.scale(d));if(!level.hasChunkAt(BlockPos.containing(p)))break;end=p;}
        return level.clip(new ClipContext(start,end,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,caster)).getLocation();
    }
    private static boolean visible(Cast c,Vec3 start,Vec3 end){return c.level.clip(new ClipContext(start,end,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,c.caster)).getType()==HitResult.Type.MISS;}
    private static void hit(Cast c,LivingEntity victim,Vec3 from,boolean once){
        if(!allowed(c.caster,victim)||(once&&!c.hit.add(victim.getUUID())))return;
        if(c.spell.kind==Spell.Kind.WAVE&&c.age-c.lastImpact>=5){
            c.lastImpact=c.age;SpellAudio.world(c.level,victim.position(),SpellAudio.impactId(c.spell.theme),0.55f,1.1f);
        }
        if(c.spell.damage>0)victim.hurt(c.level.damageSources().indirectMagic(c.caster,c.caster),(float)(c.spell.damage*KingdomWands.DAMAGE.get()));
        if(c.spell.theme==Spell.Theme.FIRE)victim.igniteForSeconds(3);
        if(c.spell.theme==Spell.Theme.DARK){effect(victim,MobEffects.WEAKNESS,60,0);}
        if(c.spell.theme==Spell.Theme.POISON)effect(victim,MobEffects.POISON,80,0);
        switch(c.spell.kind){
            case CONFUSE->{effect(victim,MobEffects.CONFUSION,100,0);effect(victim,MobEffects.BLINDNESS,40,0);}
            case STUN->{effect(victim,MobEffects.MOVEMENT_SLOWDOWN,60,5);effect(victim,MobEffects.WEAKNESS,60,1);}
            case PUSH,PULL->{Vec3 d=victim.position().subtract(c.caster.position()).multiply(1,0,1).normalize().scale(c.spell.kind==Spell.Kind.PULL?-0.9:1.3);victim.setDeltaMovement(d.add(0,0.25,0));victim.hurtMarked=true;}
            case LAUNCH->{victim.setDeltaMovement(victim.getDeltaMovement().add(0,1.1,0));victim.hurtMarked=true;}
            case COMET,PULSE,BURST,TWISTER,SMITE->{Vec3 delta=victim.position().subtract(from).multiply(1,0,1).normalize().scale(0.65);victim.push(delta.x,0.3,delta.z);victim.hurtMarked=true;}
            default->{}
        }
    }
    private static void effect(LivingEntity e,net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> effect,int ticks,int amp){e.addEffect(new MobEffectInstance(effect,ticks,amp));}
    public static void switchEffect(ServerPlayer player,Spell.Theme theme){
        trail(player.serverLevel(),player.getEyePosition().add(player.getLookAngle().scale(0.6)),theme,0.15);
        player.serverLevel().playSound(null,player.blockPosition(),SoundEvents.UI_BUTTON_CLICK.value(),SoundSource.PLAYERS,0.35F,1.4F);
    }
    private static void trail(ServerLevel level,Vec3 pos,Spell.Theme theme,double spread){
        if(visualPoints++>=KingdomWands.VISUAL_BUDGET.get())return;
        int color=theme.color;Vector3f rgb=new Vector3f(((color>>16)&255)/255f,((color>>8)&255)/255f,(color&255)/255f);
        if(theme==Spell.Theme.BLOOD){
            level.sendParticles(new DustColorTransitionOptions(rgb,new Vector3f(0.19f,0.005f,0.025f),1.25f),pos.x,pos.y,pos.z,KingdomWands.PARTICLES.get(),spread,spread,spread,0);
            // Falling red fragments instead of bright white sparks; vanilla assets only.
            level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK,Blocks.REDSTONE_BLOCK.defaultBlockState()),pos.x,pos.y,pos.z,1,spread,spread,spread,0.015);
        } else {
            level.sendParticles(new DustParticleOptions(rgb,1.1f),pos.x,pos.y,pos.z,KingdomWands.PARTICLES.get(),spread,spread,spread,0);
            var accent=switch(theme){case FIRE->ParticleTypes.FLAME;case DARK->ParticleTypes.SOUL;case GRAVITY->ParticleTypes.REVERSE_PORTAL;case THUNDER->ParticleTypes.ELECTRIC_SPARK;default->ParticleTypes.ENCHANT;};
            level.sendParticles(accent,pos.x,pos.y,pos.z,1,spread,spread,spread,0.005);
        }
    }

    private static void waveVisual(Cast c,Vec3 center){
        Vec3 side=c.dir.cross(new Vec3(0,1,0));if(side.lengthSqr()<0.01)side=new Vec3(1,0,0);side=side.normalize();
        for(int i=-4;i<=4;i++){double t=i/4.0;Vec3 p=center.add(side.scale(t*c.spell.radius)).add(0,(1-t*t)*0.3,0);trail(c.level,p,c.spell.theme,0.05);}
    }
    private static void ring(ServerLevel level,Vec3 center,double radius,Spell.Theme theme,double offset){
        int count=Math.min(48,Math.max(12,(int)(radius*10)));
        for(int i=0;i<count;i++){double a=i*Math.PI*2/count+offset;trail(level,center.add(Math.cos(a)*radius,0,Math.sin(a)*radius),theme,0.015);}
    }
    private static void burst(ServerLevel level,Vec3 pos,Spell.Theme theme){
        if(theme!=Spell.Theme.BLOOD)PacketDistributor.sendToPlayersNear(level,null,pos.x,pos.y,pos.z,64,new WandNetwork.Burst(pos.x,pos.y,pos.z,theme.color));
        for(int i=0;i<12;i++){double a=i*Math.PI/6;trail(level,pos.add(Math.cos(a)*0.4,0.25,Math.sin(a)*0.4),theme,0.12);}
        ring(level,pos,1.25,theme,0);
        if(theme==Spell.Theme.BLOOD)level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK,Blocks.REDSTONE_BLOCK.defaultBlockState()),pos.x,pos.y,pos.z,24,0.3,0.3,0.3,0.18);
        SpellAudio.world(level,pos,SpellAudio.impactId(theme),0.9f,theme==Spell.Theme.BLOOD?0.65f:0.9f);
    }

    private static void lightning(ServerLevel level,Vec3 pos,Spell.Theme theme){
        Vec3 last=pos.add(0,16,0);
        for(int i=1;i<=32;i++){Vec3 next=pos.add(Math.sin(i*2.31)*0.55,16-i*0.5,Math.cos(i*1.7)*0.55);for(int j=0;j<3;j++)trail(level,last.lerp(next,j/3.0),theme,0.02);last=next;}
        burst(level,pos,theme);
    }
    private static void chain(Cast c,LivingEntity first){
        LivingEntity victim=first;Vec3 from=c.caster.getEyePosition();
        for(int jump=0;jump<5&&victim!=null;jump++){
            Vec3 end=victim.getBoundingBox().getCenter();
            double length=from.distanceTo(end);int steps=Math.min(96,Math.max(2,(int)(length*4)));
            for(int i=0;i<=steps;i++){double t=i/(double)steps;trail(c.level,from.lerp(end,t).add(Math.sin(i*2.4)*0.12,Math.cos(i*1.7)*0.12,0),c.spell.theme,0.015);}
            hit(c,victim,end,true);burst(c.level,end,c.spell.theme);from=end;
            LivingEntity next=null;double closest=c.spell.radius*c.spell.radius;
            for(var candidate:near(c.caster,end,c.spell.radius)){
                double d=candidate.getBoundingBox().getCenter().distanceToSqr(end);
                if(!c.hit.contains(candidate.getUUID())&&d<closest&&visible(c,end,candidate.getBoundingBox().getCenter())){next=candidate;closest=d;}
            }
            victim=next;
        }
    }
    private static void message(ServerPlayer player,String text){player.displayClientMessage(Component.literal(text),true);}
    private static final class Cast {
        final UUID owner;final ServerLevel level;final Spell spell;final Vec3 dir;final LivingEntity target;final Set<UUID> hit=new HashSet<>();
        final ServerPlayer testOwner;ServerPlayer caster;Vec3 pos,anchor,travel;int age,duration,lastImpact=-20;
        Cast(ServerPlayer player,Spell spell,Vec3 start,Vec3 dir,Vec3 aim,LivingEntity target){
            owner=player.getUUID();level=player.serverLevel();caster=player;testOwner=player;this.spell=spell;pos=start;this.dir=dir;travel=dir;anchor=aim;this.target=target;
        }
    }
}
