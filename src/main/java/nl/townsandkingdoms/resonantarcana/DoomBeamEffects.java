package nl.townsandkingdoms.resonantarcana;

import java.util.HashMap;
import java.util.Map;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import io.redspace.ironsspellbooks.render.SpellRenderingHelper;
import io.redspace.ironsspellbooks.render.RenderHelper;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import org.joml.Quaternionf;
import net.minecraft.core.particles.*;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** Native emerald plasma: a singularity ignition, white core and branching corona. */
@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
final class DoomBeamEffects {
    private record Key(int caster,int spell){}
    private record Lease(Spell spell,int phase,float progress,float length,long expires){}
    private record Endpoints(Vec3 start,Vec3 end){}
    private static final Map<Key,Lease> CASTERS=new HashMap<>();
    private static ClientLevel world;
    static void track(WandNetwork.BeamFocus cue){
        var level=Minecraft.getInstance().level;if(level==null||!cue.valid())return;
        if(level!=world){CASTERS.clear();world=level;}
        Key key=new Key(cue.caster(),cue.spell());
        if(cue.phase()==0){CASTERS.remove(key);return;}
        if(CASTERS.size()<16||CASTERS.containsKey(key))CASTERS.put(key,new Lease(Spell.values()[cue.spell()],cue.phase(),cue.progress(),cue.length(),level.getGameTime()+8));
    }
    private static void prune(){
        var level=Minecraft.getInstance().level;
        if(level!=world){CASTERS.clear();world=level;}
        CASTERS.entrySet().removeIf(e->level==null||e.getValue().expires()<level.getGameTime()||level.getEntity(e.getKey().caster())==null||!level.getEntity(e.getKey().caster()).isAlive());
    }
    private static Endpoints endpoints(Entity caster,Lease lease,float partial){
        Vec3 eye=caster.getEyePosition(partial),dir=caster.getViewVector(partial).normalize();
        Vec3 start=lease.phase()==1?eye:world.clip(new ClipContext(BeamRitual.body(caster,partial),BeamRitual.body(caster,partial).add(dir.scale(BeamRitual.FORWARD)),ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,caster)).getLocation();
        Vec3 end=world.clip(new ClipContext(start,start.add(dir.scale(lease.length())),ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,caster)).getLocation();
        return new Endpoints(lease.phase()==1?end:start,lease.phase()==1?end.add(dir):end);
    }
    static void tick(){
        prune();if(world==null||world.getGameTime()%2!=0)return;
        for(var e:CASTERS.entrySet()){
            var lease=e.getValue();var points=endpoints(world.getEntity(e.getKey().caster()),lease,1);
            particles(world,lease.spell(),lease.phase()==1?SpellVisuals.Stage.CHARGE:SpellVisuals.Stage.LINK,points.start(),points.end(),lease.phase()==1?5+lease.progress()*2:6);
        }
    }
    // Old unowned world-space beam packets are deliberately not used to draw a second frozen beam.
    static void visual(ClientLevel l,Spell spell,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        if(stage==SpellVisuals.Stage.RITUAL_TARGET||stage==SpellVisuals.Stage.IMPACT||stage==SpellVisuals.Stage.HIT)particles(l,spell,stage,p,end,size);
    }
    @SubscribeEvent public static void render(RenderLevelStageEvent event){
        if(event.getStage()!=RenderLevelStageEvent.Stage.AFTER_PARTICLES)return;
        prune();if(world==null||CASTERS.isEmpty())return;
        var mc=Minecraft.getInstance();var buffers=mc.renderBuffers().bufferSource();Vec3 camera=event.getCamera().getPosition();
        float partial=event.getPartialTick().getGameTimeDeltaPartialTick(false);
        for(var e:CASTERS.entrySet()){
            var lease=e.getValue();var points=endpoints(world.getEntity(e.getKey().caster()),lease,partial);
            var pose=new PoseStack(); // World camera rotation already exists on RenderSystem's stack.
            Vec3 start=points.start();pose.translate(start.x-camera.x,start.y-camera.y,start.z-camera.z);
            if(lease.phase()==1){
                var type=RenderType.debugStructureQuads();var v=buffers.getBuffer(type);var matrix=pose.last().pose();double size=(.45+lease.progress()*.8)*.8;
                for(int lat=0;lat<16;lat++)for(int lon=0;lon<32;lon++){
                    double a=-Math.PI/2+lat*Math.PI/16,b=lon*Math.PI/16;
                    core(v,matrix,a,b,size);core(v,matrix,a+Math.PI/16,b,size);core(v,matrix,a+Math.PI/16,b+Math.PI/16,size);core(v,matrix,a,b+Math.PI/16,size);
                }
                buffers.endBatch(type);continue;
            }
            Vec3 direction=points.end().subtract(start);float length=(float)direction.length();if(length<.05f)continue;
            pose.mulPose(new Quaternionf().rotationTo(new Vector3f(0,0,1),direction.normalize().toVector3f()));
            boolean arcane=lease.spell()==Spell.ARCANE_DEVASTATOR;
            cap(pose,buffers,arcane?.5:.65,arcane);
            float scroll=-(world.getGameTime()+partial)*.06f;
            var core=RenderType.entityTranslucent(SpellRenderingHelper.BEACON);var v=buffers.getBuffer(core);
            SpellRenderingHelper.drawHull(Vec3.ZERO,new Vec3(0,0,length),.15f,.42f,pose.last(),v,arcane?240:210,arcane?200:255,arcane?255:175,255,scroll,scroll+length/6);
            SpellRenderingHelper.drawHull(Vec3.ZERO,new Vec3(0,0,length),.2f,1.1f,pose.last(),v,arcane?170:55,arcane?70:255,arcane?255:8,165,scroll*.7f,scroll*.7f+length/8);
            buffers.endBatch(core);
            var corona=RenderHelper.CustomerRenderType.magicNoCull(PlasmaAssets.PLASMA);var glow=buffers.getBuffer(corona);
            SpellRenderingHelper.drawHull(new Vec3(0,0,Math.min(.6,length)),new Vec3(0,0,length),.3f,arcane?1.8f:2.9f,pose.last(),glow,arcane?150:40,arcane?65:255,arcane?255:3,95,scroll,scroll+length/5);
            buffers.endBatch(corona);
        }
    }
    static void cap(PoseStack pose,net.minecraft.client.renderer.MultiBufferSource buffers,double radius,boolean arcane){
        var type=RenderType.lightning();var v=buffers.getBuffer(type);var m=pose.last().pose();
        for(int i=0;i<32;i++){double a=i*Math.PI/16,b=(i+1)*Math.PI/16;
            for(Vec3 p:new Vec3[]{Vec3.ZERO,new Vec3(Math.cos(a)*radius,Math.sin(a)*radius,0),new Vec3(Math.cos(b)*radius,Math.sin(b)*radius,0),Vec3.ZERO})v.addVertex(m,(float)p.x,(float)p.y,(float)p.z).setColor(arcane?255:210,arcane?225:255,arcane?155:170,255);
        }if(buffers instanceof net.minecraft.client.renderer.MultiBufferSource.BufferSource source)source.endBatch(type);
    }
    private static void core(com.mojang.blaze3d.vertex.VertexConsumer v,org.joml.Matrix4f m,double lat,double lon,double size){
        double r=Math.cos(lat)*size;v.addVertex(m,(float)(Math.cos(lon)*r),(float)(Math.sin(lat)*size),(float)(Math.sin(lon)*r)).setColor(2,1,1,255);
    }
    private static DustParticleOptions dust(float r,float g,float b,float size){return new DustParticleOptions(new Vector3f(r,g,b),size);}
    private static final ParticleOptions PURPLE=dust(.65f,.18f,1,1.25f),YELLOW=dust(1,.82f,.15f,1.2f),ARCANE_WHITE=dust(1,.9f,.72f,1.1f);
    private static final ParticleOptions GREEN=dust(.12f,1,.015f,1.5f),WHITE=dust(.8f,1,.65f,1.4f),GOLD=dust(1,.65f,.18f,1);
    static boolean green(double progress){return progress>=.5;}
    private static void particles(ClientLevel l,Spell spell,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        boolean arcane=spell==Spell.ARCANE_DEVASTATOR;
        ParticleOptions green=arcane?PURPLE:GREEN,white=arcane?ARCANE_WHITE:WHITE;
        double time=l.getGameTime()*.12;
        Vec3 axis=end.subtract(p).normalize();if(axis.lengthSqr()<.01)axis=new Vec3(0,0,1);
        Vec3 side=axis.cross(new Vec3(0,1,0)).normalize();if(side.lengthSqr()<.01)side=new Vec3(1,0,0);
        Vec3 up=side.cross(axis).normalize();
        if(stage==SpellVisuals.Stage.RITUAL_TARGET){
            for(int ring=0;ring<2;ring++)for(int i=0;i<100;i++){
                double a=i*Math.PI/50,r=size-ring*.25;
                NativeSpellEffects.point(l,green,p.add(Math.cos(a)*r,.03,Math.sin(a)*r),Vec3.ZERO,12,true);
            }
            return;
        }
        if(stage==SpellVisuals.Stage.CHARGE){
            double progress=Math.clamp((size-5)/2,0,1),radius=.45+progress*.8;
            boolean charged=green(progress);ParticleOptions rim=charged?green:GOLD;

            for(int i=0;i<144;i++){
                double a=i*Math.PI/72+time;
                Vec3 off=side.scale(Math.cos(a)*radius).add(up.scale(Math.sin(a)*radius));
                point(l,rim,p.add(off),Vec3.ZERO);
            }
            for(int arm=0;arm<5;arm++)for(int i=0;i<36;i++){
                double t=i/36.0,a=arm*Math.PI*2/5+time+t*9,r=radius+(1-t)*4;
                Vec3 off=side.scale(Math.cos(a)*r).add(up.scale(Math.sin(a)*r)).add(axis.scale((1-t)*2));
                if(!arcane&&charged&&i%3==0)NativeSpellEffects.tinted(l,i%2==0?ParticleRegistry.WISP_PARTICLE.get():ParticleRegistry.ELECTRICITY_PARTICLE.get(),p.add(off),off.scale(-.08),14,0x49ff0a,.65f);
                else point(l,arcane&&i%3==0?YELLOW:charged&&i%5==0?white:rim,p.add(off),off.scale(-.08));
            }
            return;
        }
        if(stage==SpellVisuals.Stage.LINK){
            // Spacing is independent of range, with three luminous concentric layers.
            int n=(int)Math.clamp(p.distanceTo(end)*2,24,128);
            for(int i=0;i<=n;i++){
                double t=i/(double)n,a=t*48-time*2;
                Vec3 q=p.lerp(end,t);
                if(i%4==0)NativeSpellEffects.tinted(l,ParticleRegistry.WISP_PARTICLE.get(),q,axis.scale(.12),8,arcane?(i%8==0?0xffdb36:0xbf8aff):0x9aff52,.7f);
                for(int arm=0;arm<2;arm++){
                    double angle=a+arm*Math.PI,r=.65+.25*Math.sin(t*16+time);
                    Vec3 off=side.scale(Math.cos(angle)*r).add(up.scale(Math.sin(angle)*r));
                    NativeSpellEffects.tinted(l,i%3==0?ParticleRegistry.ELECTRICITY_PARTICLE.get():ParticleRegistry.WISP_PARTICLE.get(),q.add(off),off.scale(.015),6,arcane?(arm==0?0xffd12e:0x9c4dff):0x35ff05,.65f);
                }
            }
            // Jagged branching lightning hugs the outer plasma envelope.
            for(int branch=0;branch<4;branch++){
                Vec3 previous=p;
                for(int i=1;i<=36;i++){
                    double t=i/36.0,a=branch*Math.PI/2+t*17+time;
                    double r=1.3+.65*Math.sin(i*2.7+time*3+branch);
                    Vec3 q=p.lerp(end,t).add(side.scale(Math.cos(a)*r)).add(up.scale(Math.sin(a)*r));
                    for(int j=0;j<2;j++)point(l,arcane&&branch%2==0?YELLOW:j==0?white:green,previous.lerp(q,j/2.0),Vec3.ZERO);
                    previous=q;
                }
            }
            // Expanding muzzle rings give the beam a dense, turbulent source.
            for(int ring=0;ring<3;ring++)for(int i=0;i<72;i++){
                double a=i*Math.PI/36+time,r=1.4+ring*.65;
                Vec3 off=side.scale(Math.cos(a)*r).add(up.scale(Math.sin(a)*r));
                point(l,arcane&&ring%2==0?YELLOW:green,p.add(axis.scale(.15+ring*.25)).add(off),off.scale(.025));
            }
        }else if(stage==SpellVisuals.Stage.IMPACT||stage==SpellVisuals.Stage.HIT){
            for(int i=0;i<160;i++){
                double a=i*2.39996,y=1-2*(i+.5)/160,r=Math.sqrt(1-y*y);
                Vec3 off=new Vec3(Math.cos(a)*r,y,Math.sin(a)*r);
                point(l,arcane&&i%3==0?YELLOW:i%4==0?white:green,p.add(off.scale(1.5)),off.scale(.25));
            }
            point(l,ParticleTypes.FLASH,p,Vec3.ZERO);
        }
    }
    private static void point(ClientLevel l,ParticleOptions o,Vec3 p,Vec3 v){NativeSpellEffects.point(l,o,p,v,5,false);}
}
