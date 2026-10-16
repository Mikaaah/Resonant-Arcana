package nl.townsandkingdoms.resonantarcana;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.unusual.block_factorys_bosses.client.renderer.RiftProjectileRenderer;
import net.unusual.block_factorys_bosses.geckolib.IceSpikeRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.worm.SandColumnRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.dragon.guardians.FlamingSkeletonGuardFireballRenderer;
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
        event.registerEntityRenderer(BossesRiseBridge.GUARD.get(),FlamingSkeletonGuardFireballRenderer::new);
        event.registerEntityRenderer(BossesRiseBridge.SWORD.get(),context->new DisplayRenderer.ItemDisplayRenderer(context){
            @Override public void renderInner(Display.ItemDisplay entity,Display.ItemDisplay.ItemRenderState state,
                                              PoseStack pose,MultiBufferSource buffers,int light,float progress){
                pose.pushPose();pose.mulPose(Axis.ZP.rotationDegrees(180));pose.scale(2.5f,2.5f,2.5f);
                super.renderInner(entity,new Display.ItemDisplay.ItemRenderState(state.itemStack(),ItemDisplayContext.GUI),pose,buffers,light,progress);
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
