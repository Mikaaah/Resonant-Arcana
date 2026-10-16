package nl.townsandkingdoms.resonantarcana;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.unusual.block_factorys_bosses.client.renderer.RiftProjectileRenderer;
import net.unusual.block_factorys_bosses.geckolib.IceSpikeRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/** Client-only adapter; models, animations and textures remain in Bosses'Rise. */
final class BossesRiseClient {
    private BossesRiseClient(){}
    static void register(IEventBus bus){bus.addListener(BossesRiseClient::renderers);}
    private static void renderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(BossesRiseBridge.ICE.get(),IceSpikeRenderer::new);
        event.registerEntityRenderer(BossesRiseBridge.RIFT.get(),RiftProjectileRenderer::new);
        event.registerEntityRenderer(BossesRiseBridge.TENTACLE.get(),context->new GeoEntityRenderer<>(context,
            new CustomEntityGeoModel<BossesRiseBridge.Tentacle>(ResourceLocation.fromNamespaceAndPath(BossesRiseCompat.MOD,"ghost_tentacle"),
                (texture,outline)->RenderType.entityTranslucentEmissive(texture))));
    }
}
