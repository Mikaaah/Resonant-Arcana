package nl.townsandkingdoms.resonantarcana;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Display;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.unusual.block_factorys_bosses.client.model.Modelknight_sword;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.unusual.block_factorys_bosses.client.renderer.RiftProjectileRenderer;
import net.unusual.block_factorys_bosses.geckolib.IceSpikeRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.worm.SandColumnRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/** Client-only adapter; models, animations and textures remain in Bosses'Rise. */
final class BossesRiseClient {
    private BossesRiseClient(){}
    static void register(IEventBus bus){bus.addListener(BossesRiseClient::renderers);}
    private static void renderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(BossesRiseBridge.ICE.get(),IceSpikeRenderer::new);
        event.registerEntityRenderer(BossesRiseBridge.RIFT.get(),RiftProjectileRenderer::new);
        event.registerEntityRenderer(BossesRiseBridge.SAND.get(),SandColumnRenderer::new);
        event.registerEntityRenderer(BossesRiseBridge.FLAME.get(),context->new ThrownItemRenderer<>(context,1.8f,true));
        event.registerEntityRenderer(BossesRiseBridge.SWORD.get(),context->new DisplayRenderer.ItemDisplayRenderer(context){
            private final Modelknight_sword<Display.ItemDisplay> sword=new Modelknight_sword<>(context.bakeLayer(Modelknight_sword.LAYER_LOCATION));
            @Override public void renderInner(Display.ItemDisplay entity,Display.ItemDisplay.ItemRenderState state,
                                              PoseStack pose,MultiBufferSource buffers,int light,float progress){
                // Reuse the actual held geometry. Its unflipped model axis points the blade down;
                // neither GUI perspective substitutions nor hand animations can flatten/tilt it.
                int frame=(int)(entity.level().getGameTime()/3%10);
                ResourceLocation texture=ResourceLocation.fromNamespaceAndPath(BossesRiseCompat.MOD,"textures/entities/knight_sword/knight_sword_"+frame+".png");
                pose.pushPose();pose.scale(BossesRiseBridge.SWORD_SCALE,BossesRiseBridge.SWORD_SCALE,BossesRiseBridge.SWORD_SCALE);
                sword.renderToBuffer(pose,buffers.getBuffer(RenderType.entityCutoutNoCull(texture)),light,OverlayTexture.NO_OVERLAY,-1);
                pose.popPose();
            }
        });
        event.registerEntityRenderer(BossesRiseBridge.TENTACLE.get(),context->new GeoEntityRenderer<>(context,
            new CustomEntityGeoModel<BossesRiseBridge.Tentacle>(ResourceLocation.fromNamespaceAndPath(BossesRiseCompat.MOD,"kraken_tentacle"),
                (texture,outline)->RenderType.entityCutoutNoCull(texture)) {
                @Override public ResourceLocation getTextureResource(BossesRiseBridge.Tentacle entity){
                    return ResourceLocation.fromNamespaceAndPath(BossesRiseCompat.MOD,"textures/entity/kraken.png");
                }
                @Override public ResourceLocation getAnimationResource(BossesRiseBridge.Tentacle entity){
                    return ResourceLocation.fromNamespaceAndPath(BossesRiseCompat.MOD,"anim_bosses_rise/entity/kraken_tentacle.animation.json");
                }
            }).withScale(.65f));
    }
}
