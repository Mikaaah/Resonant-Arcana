package nl.townsandkingdoms.resonantarcana;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/** Optional client-only bridge: floor seals, Explosion seals and Doom. No general AAA spell spam. */
final class AaaEffects {
    private record Key(Spell spell,SpellVisuals.Stage stage,net.minecraft.core.BlockPos point){}
    private static final Map<Key,Long> LAST=new HashMap<>();
    private static boolean checked,available,warned;
    private static Method load,play,position,scale,rotation,speed;
    private static net.minecraft.client.multiplayer.ClientLevel world;
    private static int budget;
    static void tick(){
        budget=16;var level=Minecraft.getInstance().level;
        if(world!=level){LAST.clear();world=level;}
        if(level!=null)LAST.values().removeIf(t->level.getGameTime()-t>40);
    }
    static void visual(WandNetwork.Visual m){
        if(!m.valid()||!ArcanaConfig.AAA_RITUALS.get())return;
        var mc=Minecraft.getInstance();if(mc.level==null||mc.player==null)return;
        Spell s=Spell.values()[m.spell()];var stage=SpellVisuals.Stage.values()[m.stage()];
        Vec3 p=new Vec3(m.x(),m.y(),m.z()),end=new Vec3(m.endX(),m.endY(),m.endZ());
        if(mc.player.distanceToSqr(p)>96*96)return;
        boolean charge=stage==SpellVisuals.Stage.CHARGE&&(s==Spell.DOOM||s.kind==Spell.Kind.CATACLYSM);
        boolean seal=stage==SpellVisuals.Stage.RITUAL_TARGET;
        boolean doom=s==Spell.DOOM&&stage==SpellVisuals.Stage.AREA;
        if(!charge&&!seal&&!doom)return;
        if(!checked)initialize();if(!available)return;
        long now=mc.level.getGameTime();var key=new Key(s,stage,net.minecraft.core.BlockPos.containing(p));
        if(now-LAST.getOrDefault(key,-1000L)<(doom?8:4))return;
        LAST.put(key,now);
        if(doom){double core=Math.clamp(m.size()*.17,.6,2.1);emit("doom_well_dark",p,core,core,core,new Vector3f());return;}
        if(seal&&s==Spell.EXPLOSION){
            double progress=Math.clamp((m.size()-5)/2,0,1);
            Vec3 axis=end.subtract(p).normalize();if(axis.lengthSqr()<.01)return;
            Vector3f angles=new Quaternionf().rotationTo(new Vector3f(0,1,0),axis.toVector3f()).getEulerAnglesXYZ(new Vector3f());
            int count=CataclysmEffects.explosionRings(progress);
            for(int i=0;i<count;i++){
                Vec3 center=p.lerp(end,.12+i*.17);double r=2.4+i*.3;
                emit("explosion_seal",center,r,.015,r,angles);
            }
        }else {if(s==Spell.I_AM_ATOMIC)p=p.add(0,-1,0);emit(s==Spell.EXPLOSION?"explosion_seal":s.theme==Spell.Theme.FIRE?"ward_fire":"ward_magic",p.add(0,.025,0),charge?m.size():m.size()*.7,.015,charge?m.size():m.size()*.7,new Vector3f());}
    }
    private static void emit(String name,Vec3 p,double x,double y,double z,Vector3f angles){
        if(budget--<=0)return;
        var mc=Minecraft.getInstance();var level=mc.level;long deadline=System.nanoTime()+750_000_000L;
        try{
            var id=ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,name);
            ((CompletableFuture<?>)load.invoke(null,id)).whenComplete((effect,error)->mc.execute(()->{
                if(error!=null){fail(error);return;}
                if(mc.level!=level||System.nanoTime()>deadline)return;
                try{var handle=play.invoke(effect);position.invoke(handle,(float)p.x,(float)p.y,(float)p.z);
                    scale.invoke(handle,(float)x,(float)y,(float)z);rotation.invoke(handle,angles.x,angles.y,angles.z);
                    if(speed!=null)speed.invoke(handle,name.equals("doom_well_dark")?.8f:.5f);
                }catch(ReflectiveOperationException|LinkageError|RuntimeException ex){fail(ex);}
            }));
        }catch(ReflectiveOperationException|LinkageError|RuntimeException ex){fail(ex);}
    }
    private static void initialize(){
        checked=true;if(!ModList.get().isLoaded("aaa_particles"))return;
        try{
            var registry=Class.forName("mod.chloeprime.aaaparticles.api.client.EffectRegistry");
            var definition=Class.forName("mod.chloeprime.aaaparticles.api.client.EffectDefinition");
            var handle=Class.forName("mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter");
            var platform=Class.forName("mod.chloeprime.aaaparticles.client.installer.NativePlatform");
            if((Boolean)platform.getMethod("isRunningOnUnsupportedPlatform").invoke(null))return;
            load=registry.getMethod("load",ResourceLocation.class);play=definition.getMethod("play");
            position=handle.getMethod("setPosition",float.class,float.class,float.class);
            scale=handle.getMethod("setScale",float.class,float.class,float.class);
            rotation=handle.getMethod("setRotation",float.class,float.class,float.class);
            try{speed=handle.getMethod("setSpeed",float.class);}catch(NoSuchMethodException ignored){} // Older AAA versions still render finite seals.
            available=true;
        }catch(ReflectiveOperationException|LinkageError|RuntimeException ex){fail(ex);}
    }
    private static void fail(Throwable ex){if(!warned){warned=true;ResonantArcana.LOG.warn("Selective AAA rituals unavailable; native particles remain active",ex);}}
}
