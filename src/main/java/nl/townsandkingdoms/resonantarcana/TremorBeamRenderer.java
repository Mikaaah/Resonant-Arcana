package nl.townsandkingdoms.resonantarcana;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.github.alexmodguy.alexscaves.client.model.TremorzillaBeamModel;
import com.github.alexmodguy.alexscaves.client.render.ACRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/** Native material, animated end model and all three skin textures; no boss entity is spawned. */
final class TremorBeamRenderer {
    private static final TremorzillaBeamModel END=new TremorzillaBeamModel();
    private static ResourceLocation texture(int variant,String part){String name=variant==1?"tremorzilla_retro":variant==2?"tremorzilla_tectonic":"tremorzilla";return ResourceLocation.fromNamespaceAndPath("alexscaves","textures/entity/tremorzilla/"+name+"_beam_"+part+".png");}
    static void render(CaveVisual marker,float partial,float length,PoseStack pose,MultiBufferSource buffers){
        float time=marker.tickCount+partial,fade=Math.clamp(time/5,0,1),width=(marker.model()==18?2.1f:.8f)*fade*(1+.035f*(float)Math.sin(time*.7));
        DoomBeamEffects.cap(pose,buffers,.19,marker.variant()==1);
        tube(pose,buffers.getBuffer(ACRenderTypes.getTremorzillaBeam(texture(marker.variant(),"inner"),false)),length,.18f,width,4,-time*.125f,1);
        tube(pose,buffers.getBuffer(ACRenderTypes.getTremorzillaBeam(texture(marker.variant(),"outer"),false)),length,.24f,width*1.5f,8,-time*.25f,.5f);
        pose.pushPose();pose.translate(0,0,length-.3);pose.mulPose(Axis.XP.rotationDegrees(90));pose.scale(width,width,width);
        END.resetToDefaultPose();END.renderToBuffer(pose,buffers.getBuffer(ACRenderTypes.getTremorzillaBeam(texture(marker.variant(),"end_"+(marker.tickCount/2%3)),false)),240,OverlayTexture.NO_OVERLAY,-1);pose.popPose();
    }
    private static void tube(PoseStack pose,VertexConsumer out,float length,float start,float end,int sides,float scroll,float alpha){
        for(int i=0;i<sides;i++){double a=i*Math.PI*2/sides,b=(i+1)*Math.PI*2/sides;vertex(pose,out,(float)Math.cos(a)*start,(float)Math.sin(a)*start,0,i/(float)sides,scroll,alpha);vertex(pose,out,(float)Math.cos(a)*end,(float)Math.sin(a)*end,length,i/(float)sides,scroll+length*.3f,alpha);vertex(pose,out,(float)Math.cos(b)*end,(float)Math.sin(b)*end,length,(i+1)/(float)sides,scroll+length*.3f,alpha);vertex(pose,out,(float)Math.cos(b)*start,(float)Math.sin(b)*start,0,(i+1)/(float)sides,scroll,alpha);}
    }
    private static void vertex(PoseStack pose,VertexConsumer out,float x,float y,float z,float u,float v,float alpha){out.addVertex(pose.last().pose(),x,y,z).setColor(1f,1f,1f,alpha).setUv(u,v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(240).setNormal(pose.last(),0,1,0);}
    private TremorBeamRenderer(){}
}
