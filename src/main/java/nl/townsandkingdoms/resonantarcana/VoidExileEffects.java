package nl.townsandkingdoms.resonantarcana;

import java.util.ArrayList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import org.joml.Vector3f;

/** A narrow vertical tear, violet sparks and a readable native-particle charge circle. */
@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
final class VoidExileEffects {
    private record Rift(ClientLevel level,Vec3 center,Vec3 side,double size,long until){}
    private static final ArrayList<Rift> RIFTS=new ArrayList<>();
    private static final DustParticleOptions EDGE=new DustParticleOptions(new Vector3f(.67f,.35f,1),.9f);
    static void visual(ClientLevel level,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        double time=level.getGameTime()*.12;
        if(stage==SpellVisuals.Stage.RITUAL_END||stage==SpellVisuals.Stage.IMPACT){RIFTS.removeIf(r->r.level()!=level||r.center().distanceToSqr(p)<.25);if(stage==SpellVisuals.Stage.RITUAL_END)return;}
        if(stage==SpellVisuals.Stage.RITUAL_TARGET){
            for(int i=0;i<72;i++){double a=i*Math.PI/36;NativeSpellEffects.point(level,EDGE,p.add(Math.cos(a)*size*.7,.03,Math.sin(a)*size*.7),Vec3.ZERO,12,true);}return;
        }
        if(stage==SpellVisuals.Stage.AREA||stage==SpellVisuals.Stage.STRIKE){
            Vec3 axis=end.subtract(p).multiply(1,0,1).normalize();if(axis.lengthSqr()<.01)axis=new Vec3(0,0,1);Vec3 side=new Vec3(-axis.z,0,axis.x);
            RIFTS.removeIf(r->r.level()!=level||r.until()<level.getGameTime()||r.center().distanceToSqr(p)<.25);
            if(RIFTS.size()<128)RIFTS.add(new Rift(level,p,side,size,level.getGameTime()+12));
            for(int i=0;i<96;i++){
                double a=i*Math.PI/48;Vec3 off=side.scale(Math.cos(a)*size*.36).add(0,Math.sin(a)*size*1.3,0);
                NativeSpellEffects.tinted(level,i%3==0?ParticleRegistry.ELECTRICITY_PARTICLE.get():ParticleRegistry.WISP_PARTICLE.get(),p.add(off),off.scale(-.045),12,i%2==0?0xe6c8ff:0x9741ff,.65f);
            }
            for(int arm=0;arm<3;arm++)for(int i=0;i<16;i++){
                double t=i/16.0,a=time+t*8+arm*Math.PI*2/3;Vec3 off=side.scale(Math.cos(a)*(1-t)*size*1.4).add(axis.scale(Math.sin(a)*(1-t)*size)).add(0,(t-.5)*size*2,0);
                NativeSpellEffects.point(level,ParticleTypes.REVERSE_PORTAL,p.add(off),off.scale(-.09),14,false);
            }return;
        }
        if(stage==SpellVisuals.Stage.IMPACT){
            for(int i=0;i<100;i++){double y=1-2*(i+.5)/100,a=i*2.39996,r=Math.sqrt(1-y*y);Vec3 off=new Vec3(Math.cos(a)*r,y,Math.sin(a)*r);NativeSpellEffects.tinted(level,ParticleRegistry.WISP_PARTICLE.get(),p.add(off.scale(.3)),off.scale(.18),20,i%3==0?0xe4c7ff:0x8439ec,.8f);}return;
        }
        if(stage==SpellVisuals.Stage.LINK){
            int count=(int)Math.clamp(p.distanceTo(end)*6,12,96);for(int i=0;i<count;i++)NativeSpellEffects.tinted(level,ParticleRegistry.WISP_PARTICLE.get(),p.lerp(end,i/(double)count),Vec3.ZERO,12,0xb278ff,.6f);return;
        }
        if(stage==SpellVisuals.Stage.CHARGE||stage==SpellVisuals.Stage.CAST){
            for(int i=0;i<24;i++){double a=i*Math.PI/12+time;Vec3 off=new Vec3(Math.cos(a)*.65,Math.sin(a*2)*.3,Math.sin(a)*.65);NativeSpellEffects.tinted(level,ParticleRegistry.WISP_PARTICLE.get(),p.add(off),off.scale(-.06),12,0xb278ff,.6f);}
        }
    }
    @SubscribeEvent public static void render(RenderLevelStageEvent event){
        if(event.getStage()!=RenderLevelStageEvent.Stage.AFTER_PARTICLES)return;
        var mc=Minecraft.getInstance();RIFTS.removeIf(r->r.level()!=mc.level||r.until()<(mc.level==null?Long.MAX_VALUE:mc.level.getGameTime()));if(RIFTS.isEmpty())return;
        var buffers=mc.renderBuffers().bufferSource();var type=RenderType.debugStructureQuads();var vertex=buffers.getBuffer(type);var camera=event.getCamera().getPosition();var pose=new PoseStack();
        for(var r:RIFTS){
            pose.pushPose();pose.translate(r.center().x-camera.x,r.center().y-camera.y,r.center().z-camera.z);var matrix=pose.last().pose();
            for(int band=0;band<16;band++){
                double low=-1.3+band*2.6/16,high=low+2.6/16;
                double w1=(.05+.24*(1-Math.abs(low)/1.3))*(1+.16*Math.sin(band*2.7+r.level().getGameTime()*.1));
                double w2=(.05+.24*(1-Math.abs(high)/1.3))*(1+.16*Math.sin((band+1)*2.7+r.level().getGameTime()*.1));
                double[][] points={{-w1,low},{w1,low},{w2,high},{-w2,high}};
                for(var point:points)vertex.addVertex(matrix,(float)(r.side().x*point[0]*r.size()),(float)(point[1]*r.size()),(float)(r.side().z*point[0]*r.size())).setColor(4,0,12,245);
            }pose.popPose();
        }buffers.endBatch(type);
    }
}
