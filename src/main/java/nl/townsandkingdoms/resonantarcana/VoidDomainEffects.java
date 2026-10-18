package nl.townsandkingdoms.resonantarcana;

import java.util.ArrayList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import org.joml.Vector3f;

/** Dark outside, translucent inside: the domain remains readable while fighting within it. */
@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
final class VoidDomainEffects {
    private record Sphere(ClientLevel level,Vec3 center,double radius,long expires){}
    private static final ArrayList<Sphere> SPHERES=new ArrayList<>();
    private static final DustParticleOptions EDGE=new DustParticleOptions(new Vector3f(.61f,.32f,.88f),1.1f);
    // Unit mesh is computed once rather than running trigonometry for every frame and domain.
    private static final float[][] MESH=mesh();
    private static float[][] mesh(){
        var points=new ArrayList<float[]>();
        for(int lat=0;lat<12;lat++)for(int lon=0;lon<24;lon++)for(int[] corner:new int[][]{{0,0},{1,0},{1,1},{0,1}}){
            double a=-Math.PI/2+(lat+corner[0])*Math.PI/12,b=(lon+corner[1])*Math.PI/12;
            points.add(new float[]{(float)(Math.cos(a)*Math.cos(b)),(float)Math.sin(a),(float)(Math.cos(a)*Math.sin(b))});
        }return points.toArray(float[][]::new);
    }
    static void visual(ClientLevel level,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double radius){
        if(stage==SpellVisuals.Stage.RITUAL_END){SPHERES.removeIf(s->s.level()!=level||s.center().distanceToSqr(p)<.1);return;}
        if(stage==SpellVisuals.Stage.AREA){
            SPHERES.removeIf(s->s.level()!=level||s.expires()<level.getGameTime()||s.center().distanceToSqr(p)<.1);
            if(SPHERES.size()<16)SPHERES.add(new Sphere(level,p,radius,level.getGameTime()+12));
            for(int i=0;i<128;i++){
                double a=i*2.39996,y=1-2*(i+.5)/128,r=Math.sqrt(1-y*y);Vec3 off=new Vec3(Math.cos(a)*r,y,Math.sin(a)*r).scale(radius);
                NativeSpellEffects.tinted(level,i%5==0?ParticleRegistry.ELECTRICITY_PARTICLE.get():ParticleRegistry.WISP_PARTICLE.get(),p.add(off),off.normalize().scale(-.015),14,i%3==0?0xc5a1f0:0x7143bd,.7f);
            }
            for(int i=0;i<96;i++){double a=i*Math.PI/48;NativeSpellEffects.point(level,EDGE,p.add(Math.cos(a)*radius,-.85,Math.sin(a)*radius),Vec3.ZERO,12,true);}return;
        }
        // The common charge choreography retains its floor seal and a clear view of the target.
        VoidExileEffects.visual(level,stage,p,end,radius);
    }
    @SubscribeEvent public static void render(RenderLevelStageEvent event){
        if(event.getStage()!=RenderLevelStageEvent.Stage.AFTER_PARTICLES)return;
        var mc=Minecraft.getInstance();SPHERES.removeIf(s->s.level()!=mc.level||s.expires()<(mc.level==null?Long.MAX_VALUE:mc.level.getGameTime()));if(SPHERES.isEmpty())return;
        var buffers=mc.renderBuffers().bufferSource();var type=RenderType.debugStructureQuads();var vertex=buffers.getBuffer(type);var camera=event.getCamera().getPosition();var pose=new PoseStack();
        for(var sphere:SPHERES){
            pose.pushPose();pose.translate(sphere.center().x-camera.x,sphere.center().y-camera.y,sphere.center().z-camera.z);var matrix=pose.last().pose();
            boolean inside=camera.distanceToSqr(sphere.center())<sphere.radius()*sphere.radius();int alpha=inside?32:220;
            for(var point:MESH)vertex.addVertex(matrix,(float)(point[0]*sphere.radius()),(float)(point[1]*sphere.radius()),(float)(point[2]*sphere.radius())).setColor(4,1,10,alpha);
            pose.popPose();
        }buffers.endBatch(type);
    }
}
