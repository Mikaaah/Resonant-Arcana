package nl.townsandkingdoms.kingdomwands;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Collection;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;

/** Client-only optional API bridge. No renderer or native libraries are bundled. */
final class AaaEffects {
    private static boolean checked, available;
    private static int spawned, testTicks;
    private static long received, requested, played, failed;
    private static String lastError="none";
    private static final Set<ResourceLocation> reported=new HashSet<>();
    private static Constructor<?> emitter;
    private static Method rotation, load, get, entries, play, setPosition, setScale, setRotation, getRotation, unsupported;
    private AaaEffects() {}
    static void tick(){
        spawned=0;
        if(testTicks>0){
            var mc=Minecraft.getInstance();
            if(mc.player==null||mc.level==null){testTicks=0;return;}
            if(testTicks%10==0){
                Vec3 eye=mc.player.getEyePosition();
                Vec3 end=eye.add(mc.player.getLookAngle().scale(5));
                var clip=mc.level.clip(new net.minecraft.world.level.ClipContext(eye,end,net.minecraft.world.level.ClipContext.Block.COLLIDER,net.minecraft.world.level.ClipContext.Fluid.NONE,mc.player));
                Vec3 point=clip.getLocation().lerp(eye,0.1);
                visual(new WandNetwork.Visual(Spell.AMETHYST_NOVA.ordinal(),SpellVisuals.Stage.IMPACT.ordinal(),point.x,point.y,point.z,point.x,point.y,point.z,1.8f),false);
            }
            if(--testTicks==0)diagnostics();
        }
    }

    static void visual(WandNetwork.Visual message) { visual(message,true); }
    private static void visual(WandNetwork.Visual message,boolean packet) {
        if(packet)received++;
        if(!message.valid()||!KingdomWands.AAA_PARTICLES.get())return;
        var mc=Minecraft.getInstance();
        if(mc.level==null||mc.player==null||spawned>=KingdomWands.AAA_BUDGET.get())return;
        Vec3 start=new Vec3(message.x(),message.y(),message.z()),end=new Vec3(message.endX(),message.endY(),message.endZ());
        // Use the nearest point, not just the impact: a nearby beam's far end may be distant.
        Vec3 delta=end.subtract(start);double lengthSquared=delta.lengthSqr();
        Vec3 nearest=lengthSquared<0.001?start:start.add(delta.scale(Math.clamp(mc.player.getEyePosition().subtract(start).dot(delta)/lengthSquared,0,1)));
        if(mc.player.distanceToSqr(nearest)>96*96)return;
        if(!checked)initialize();
        if(!available)return;
        var spell=Spell.values()[message.spell()];var stage=SpellVisuals.Stage.values()[message.stage()];
        var shape=SpellVisuals.shape(spell,stage);
        float size=message.size(),sx=size,sy=size,sz=size;
        boolean directional=stage==SpellVisuals.Stage.LINK||stage==SpellVisuals.Stage.STRIKE||stage==SpellVisuals.Stage.CAST||stage==SpellVisuals.Stage.FLIGHT||stage==SpellVisuals.Stage.CHARGE;
        if(stage==SpellVisuals.Stage.LINK||stage==SpellVisuals.Stage.STRIKE){
            if(lengthSquared<0.01)return;
            sx=sy=shape==SpellVisuals.Shape.FLAME?0.8f:shape==SpellVisuals.Shape.LIGHTNING?0.7f:0.45f;
            sz=(float)Math.sqrt(lengthSquared);
        } else if(stage==SpellVisuals.Stage.FLIGHT){
            // Flight messages start at the visible head; their endpoint carries its direction.
            sy=shape==SpellVisuals.Shape.WAVE?0.9f:size;
        } else if(stage==SpellVisuals.Stage.AREA){
            sy=switch(shape){case PULSE -> 0.7f;case CAGE,CURSE -> 1.0f;case VORTEX -> Math.min(4,size);default -> Math.min(2.5f,size);};
        }
        try {
            var id=ResourceLocation.fromNamespaceAndPath(KingdomWands.ID,shape.name().toLowerCase(Locale.ROOT)+"_"+spell.theme.name().toLowerCase(Locale.ROOT));
            var manager=mc.getResourceManager();
            if(manager.getResource(ResourceLocation.fromNamespaceAndPath(KingdomWands.ID,"effeks/"+id.getPath()+".efkefc")).isEmpty()){
                failure(id,new IllegalStateException("Effect file missing from the active client resources"));return;
            }
            if(get.invoke(null,id)==null){
                failure(id,new IllegalStateException("AAAParticles did not register this effect (run /kwvisuals)"));return;
            }
            var info=emitter.newInstance(id);
            double lift=stage==SpellVisuals.Stage.AREA&&(shape==SpellVisuals.Shape.FIELD||shape==SpellVisuals.Shape.VORTEX)?0.08:0;
            if(directional&&lengthSquared>0.001)rotation.invoke(info,delta.normalize());
            Vec3 angles=(Vec3)getRotation.invoke(info);
            final float xScale=sx,yScale=sy,zScale=sz;
            final var level=mc.level;
            long deadline=System.nanoTime()+1_000_000_000L;
            spawned++;requested++;
            // Observe the actual load result, rather than a fire-and-forget spawn call.
            var future=(CompletableFuture<?>)load.invoke(null,id);
            future.whenComplete((definition,error)->mc.execute(()->{
                if(error!=null){failure(id,error);return;}
                if(mc.level!=level||System.nanoTime()>deadline)return;
                try{
                    var handle=play.invoke(definition);
                    setPosition.invoke(handle,(float)start.x,(float)(start.y+lift),(float)start.z);
                    setScale.invoke(handle,xScale,yScale,zScale);
                    if(directional)setRotation.invoke(handle,(float)angles.x,(float)angles.y,(float)angles.z);
                    played++;
                }catch(ReflectiveOperationException|LinkageError|RuntimeException ex){failure(id,ex);}
            }));
        }catch(ReflectiveOperationException|LinkageError|RuntimeException error){
            failure(null,error);
        }
    }

    private static void failure(ResourceLocation id,Throwable error){
        failed++;
        while(error.getCause()!=null&&error.getCause()!=error)error=error.getCause();
        lastError=error.getClass().getSimpleName()+": "+error.getMessage();
        if(reported.add(id)){
            KingdomWands.LOG.error("AAAParticles effect {} failed: {}",id,lastError,error);
            if(reported.size()<=3)say("AAAParticles effect failed: "+lastError+". Run /kwvisuals for details.");
        }
    }
    private static void say(String message){
        var player=Minecraft.getInstance().player;
        if(player!=null)player.displayClientMessage(Component.literal("[Kingdom Wands] "+message),false);
    }
    static void diagnostics(){
        var mods=ModList.get();
        String aaa=mods.getModContainerById("aaa_particles").map(m->m.getModInfo().getVersion().toString()).orElse("not installed");
        String kw=mods.getModContainerById(KingdomWands.ID).map(m->m.getModInfo().getVersion().toString()).orElse("unknown");
        if(!checked)initialize();
        say("Client "+kw+" | AAAParticles "+aaa+" | enabled="+KingdomWands.AAA_PARTICLES.get()+" | API="+available);
        int assets=0,registered=0;
        var manager=Minecraft.getInstance().getResourceManager();
        for(var shape:SpellVisuals.Shape.values())for(var theme:Spell.Theme.values()){
            String path=shape.name().toLowerCase(Locale.ROOT)+"_"+theme.name().toLowerCase(Locale.ROOT);
            if(manager.getResource(ResourceLocation.fromNamespaceAndPath(KingdomWands.ID,"effeks/"+path+".efkefc")).isPresent())assets++;
        }
        if(available)try{
            var collection=(Collection<?>)entries.invoke(null);
            for(var entry:collection)if(entry instanceof Map.Entry<?,?> e&&e.getKey() instanceof ResourceLocation id&&id.getNamespace().equals(KingdomWands.ID))registered++;
            say("Native renderer supported="+!(Boolean)unsupported.invoke(null));
        }catch(ReflectiveOperationException|LinkageError|RuntimeException error){lastError=error.toString();}
        say("Effect files="+assets+"/136 | registered="+registered+"/136 | packets="+received+" | requested="+requested+" | created="+played+" | failures="+failed);
        say("Last error: "+lastError);
        KingdomWands.LOG.info("Wand visual diagnostics: client={}, AAA={}, enabled={}, API={}, files={}, registered={}, packets={}, requested={}, created={}, failures={}, lastError={}",kw,aaa,KingdomWands.AAA_PARTICLES.get(),available,assets,registered,received,requested,played,failed,lastError);
    }
    static void test(){
        diagnostics();
        if(!available||!KingdomWands.AAA_PARTICLES.get()){
            say("Test unavailable: AAAParticles must be installed and aaaParticles must be true.");return;
        }
        testTicks=60;
        say("Testing cyan/amethyst bursts in front of you for three seconds; no server packets required.");
    }

    private static void initialize() {
        checked=true;
        if(!ModList.get().isLoaded("aaa_particles"))return;
        try {
            var info=Class.forName("mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo");
            var registry=Class.forName("mod.chloeprime.aaaparticles.api.client.EffectRegistry");
            var definition=Class.forName("mod.chloeprime.aaaparticles.api.client.EffectDefinition");
            var handle=Class.forName("mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter");
            var platform=Class.forName("mod.chloeprime.aaaparticles.client.installer.NativePlatform");
            emitter=info.getConstructor(ResourceLocation.class);
            rotation=info.getMethod("rotationFromForward",Vec3.class);
            getRotation=info.getMethod("rotation");
            load=registry.getMethod("load",ResourceLocation.class);
            get=registry.getMethod("get",ResourceLocation.class);
            entries=registry.getMethod("entries");
            play=definition.getMethod("play");
            setPosition=handle.getMethod("setPosition",float.class,float.class,float.class);
            setScale=handle.getMethod("setScale",float.class,float.class,float.class);
            setRotation=handle.getMethod("setRotation",float.class,float.class,float.class);
            unsupported=platform.getMethod("isRunningOnUnsupportedPlatform");
            if((Boolean)unsupported.invoke(null)){
                lastError="AAAParticles native renderer is unavailable; check latest.log";
                say(lastError);return;
            }
            available=true;
            KingdomWands.LOG.info("AAAParticles full spell visuals enabled");
        }catch(ReflectiveOperationException|LinkageError error){
            lastError=error.toString();
            say("AAAParticles API unavailable: "+lastError);
            KingdomWands.LOG.warn("AAAParticles API unavailable; using vanilla spell effects",error);
        }
    }
}
