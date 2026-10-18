package nl.townsandkingdoms.resonantarcana;

import java.util.ArrayList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

/** Small unlit cores; short refresh leases also clean up interrupted rituals. */
@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
final class SingularityEffects {
    private record Core(ClientLevel level,Vec3 center,double radius,long expires){}
    private static final ArrayList<Core> CORES=new ArrayList<>();
    static void refresh(ClientLevel level,Vec3 center,double radius){
        CORES.removeIf(c->c.level()!=level||c.expires()<level.getGameTime()||c.center().distanceToSqr(center)<1);
        if(CORES.size()<16)CORES.add(new Core(level,center,radius,level.getGameTime()+6));
    }
    @SubscribeEvent public static void render(RenderLevelStageEvent e){
        if(e.getStage()!=RenderLevelStageEvent.Stage.AFTER_PARTICLES)return;
        var mc=Minecraft.getInstance();CORES.removeIf(c->c.level()!=mc.level||c.expires()<(mc.level==null?Long.MAX_VALUE:mc.level.getGameTime()));
        if(CORES.isEmpty())return;
        var buffers=mc.renderBuffers().bufferSource();var type=RenderType.debugStructureQuads();var vertex=buffers.getBuffer(type);
        var camera=e.getCamera().getPosition();var pose=new PoseStack(); // LevelRenderer already applied the camera rotation.
        for(var c:CORES){
            pose.pushPose();pose.translate(c.center().x-camera.x,c.center().y-camera.y,c.center().z-camera.z);var m=pose.last().pose();
            for(int lat=0;lat<16;lat++)for(int lon=0;lon<32;lon++){
                double a=-Math.PI/2+lat*Math.PI/16,b=lon*Math.PI/16;
                vertex(vertex,m,a,b,c.radius());vertex(vertex,m,a+Math.PI/16,b,c.radius());
                vertex(vertex,m,a+Math.PI/16,b+Math.PI/16,c.radius());vertex(vertex,m,a,b+Math.PI/16,c.radius());
            }
            pose.popPose();
        }
        buffers.endBatch(type);
    }
    private static void vertex(com.mojang.blaze3d.vertex.VertexConsumer v,org.joml.Matrix4f m,double lat,double lon,double size){
        double r=Math.cos(lat)*size;v.addVertex(m,(float)(Math.cos(lon)*r),(float)(Math.sin(lat)*size),(float)(Math.sin(lon)*r)).setColor(2,1,1,255);
    }
}
