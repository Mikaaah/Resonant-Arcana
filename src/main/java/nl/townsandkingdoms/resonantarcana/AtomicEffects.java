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
import org.joml.Vector3f;

/** One translucent containment membrane, converging energy, then a violet shock front. */
@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
final class AtomicEffects {
    private record Barrier(ClientLevel level,Vec3 center,long expires){}
    private static final ArrayList<Barrier> BARRIERS=new ArrayList<>();
    private static final DustColorTransitionOptions VIOLET=new DustColorTransitionOptions(new Vector3f(.52f,.035f,1),new Vector3f(.12f,0,.3f),1.3f);
    private static final DustParticleOptions HOT=new DustParticleOptions(new Vector3f(.93f,.72f,1),1.1f);
    static void visual(ClientLevel l,SpellVisuals.Stage stage,Vec3 p,Vec3 tip,double size){
        double time=l.getGameTime()*.08;
        BARRIERS.removeIf(b->b.level()!=l||b.expires()<l.getGameTime()||b.center().distanceToSqr(p)<.5);
        if(stage==SpellVisuals.Stage.RITUAL_END)return;
        if(stage==SpellVisuals.Stage.CHARGE){
            if(BARRIERS.size()<16)BARRIERS.add(new Barrier(l,p,l.getGameTime()+8));
            double progress=Math.clamp((size-5)/2,0,1);
            // Wisps on the dome make its extent visible without filling the caster's eye line.
            for(int i=0;i<180;i++){
                double y=(i+.5)/180,a=i*2.39996+time*.15,r=Math.sqrt(1-y*y)*16;
                Vec3 q=p.add(Math.cos(a)*r,y*16,Math.sin(a)*r);
                point(l,VIOLET,q,new Vec3(0,.006,0),14);
            }
            for(int arm=0;arm<5;arm++)for(int i=0;i<32;i++){
                double t=(i+.5)/32,a=arm*Math.PI*2/5+time*1.4+t*10,r=(1-t)*(9+progress*6)+.1;
                Vec3 q=tip.add(Math.cos(a)*r,(1-t)*6,Math.sin(a)*r);
                Vec3 v=new Vec3(-Math.sin(a)*.12,-.08,Math.cos(a)*.12).add(tip.subtract(q).scale(.055));
                point(l,i%6==0?HOT:VIOLET,q,v,22);
            }
            for(int i=0;i<24;i++){double a=i*2.39996+time*3,r=.16+(1-progress)*.25;point(l,HOT,tip.add(Math.cos(a)*r,Math.sin(a*2)*r,Math.sin(a)*r),Vec3.ZERO,10);}
            return;
        }
        if(stage==SpellVisuals.Stage.IMPACT){
            point(l,ParticleTypes.FLASH,p.add(0,2,0),Vec3.ZERO,4);
            for(int i=0;i<260;i++){
                double y=1-2*(i+.5)/260,a=i*2.39996,r=Math.sqrt(1-y*y);Vec3 dir=new Vec3(Math.cos(a)*r,y,Math.sin(a)*r);
                point(l,i%5==0?HOT:VIOLET,p.add(dir.scale(.6)),dir.scale(.9),30);
            }
            return;
        }
        if(stage==SpellVisuals.Stage.AREA){
            for(int ring=0;ring<3;ring++)for(int i=0;i<150;i++){
                double a=i*Math.PI/75,r=size*(1-ring*.07),tilt=ring*.7;
                Vec3 off=new Vec3(Math.cos(a)*r,Math.sin(a)*Math.sin(tilt)*r,Math.sin(a)*Math.cos(tilt)*r);
                point(l,i%6==0?HOT:VIOLET,p.add(off),off.normalize().scale(.09),14);
            }
            for(int i=0;i<50;i++){double a=i*2.39996,r=size*Math.sqrt((i+.5)/50);point(l,ParticleTypes.REVERSE_PORTAL,p.add(Math.cos(a)*r,i%7*.6,Math.sin(a)*r),new Vec3(0,.06,0),24);}
        }
    }
    private static void point(ClientLevel l,ParticleOptions o,Vec3 p,Vec3 v,int life){NativeSpellEffects.point(l,o,p,v,life,false);}
    @SubscribeEvent public static void render(RenderLevelStageEvent e){
        if(e.getStage()!=RenderLevelStageEvent.Stage.AFTER_PARTICLES)return;
        var mc=Minecraft.getInstance();BARRIERS.removeIf(b->b.level()!=mc.level||b.expires()<(mc.level==null?Long.MAX_VALUE:mc.level.getGameTime()));if(BARRIERS.isEmpty())return;
        var buffers=mc.renderBuffers().bufferSource();var type=RenderType.debugStructureQuads();var vertex=buffers.getBuffer(type);
        var camera=e.getCamera().getPosition();var pose=new PoseStack();pose.mulPose(e.getModelViewMatrix());
        for(var b:BARRIERS){
            pose.pushPose();pose.translate(b.center().x-camera.x,b.center().y-camera.y,b.center().z-camera.z);var m=pose.last().pose();
            for(int lat=0;lat<16;lat++)for(int lon=0;lon<32;lon++){
                double a=-Math.PI/2+lat*Math.PI/16,c=lon*Math.PI/16;
                vertex(vertex,m,a,c);vertex(vertex,m,a+Math.PI/16,c);vertex(vertex,m,a+Math.PI/16,c+Math.PI/16);vertex(vertex,m,a,c+Math.PI/16);
            }
            pose.popPose();
        }
        buffers.endBatch(type);
    }
    private static void vertex(com.mojang.blaze3d.vertex.VertexConsumer v,org.joml.Matrix4f m,double lat,double lon){
        double r=Math.cos(lat)*16;v.addVertex(m,(float)(Math.cos(lon)*r),(float)(Math.sin(lat)*16),(float)(Math.sin(lon)*r)).setColor(112,24,230,22);
    }
}
