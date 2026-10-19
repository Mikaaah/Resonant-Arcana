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
        event.registerEntityRenderer(BossesRiseBridge.RIFT.get(),context->new net.minecraft.client.renderer.entity.EntityRenderer<BossesRiseBridge.Rift>(context){
            @Override public ResourceLocation getTextureLocation(BossesRiseBridge.Rift entity){return PlasmaAssets.PLASMA;}
            @Override public void render(BossesRiseBridge.Rift entity,float yaw,float partial,PoseStack pose,MultiBufferSource source,int light){
                pose.pushPose();pose.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-yaw));float phase=(entity.tickCount+partial)*-.12f;
                var vertex=source.getBuffer(RenderType.entityTranslucent(PlasmaAssets.PLASMA));
                io.redspace.ironsspellbooks.render.SpellRenderingHelper.drawHull(new net.minecraft.world.phys.Vec3(0,-.8,0),new net.minecraft.world.phys.Vec3(0,.8,0),.08f,.18f,pose.last(),vertex,255,65,12,230,phase,phase+2);
                io.redspace.ironsspellbooks.render.SpellRenderingHelper.drawHull(new net.minecraft.world.phys.Vec3(-.55,-.55,0),new net.minecraft.world.phys.Vec3(.55,.55,0),.025f,.08f,pose.last(),vertex,255,155,35,210,phase,phase+2);
                pose.popPose();
            }
        });
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
        event.registerEntityRenderer(BossesRiseBridge.FULL_KRAKEN.get(),context->new GeoEntityRenderer<>(context,
            new CustomEntityGeoModel<BossesRiseBridge.FullKraken>(ResourceLocation.fromNamespaceAndPath(BossesRiseCompat.MOD,"kraken_cinematic"),
                (texture,outline)->RenderType.entityCutoutNoCull(texture)) {
                @Override public ResourceLocation getTextureResource(BossesRiseBridge.FullKraken entity){return ResourceLocation.fromNamespaceAndPath(BossesRiseCompat.MOD,"textures/entity/kraken.png");}
                @Override public ResourceLocation getAnimationResource(BossesRiseBridge.FullKraken entity){return ResourceLocation.fromNamespaceAndPath(BossesRiseCompat.MOD,"anim_bosses_rise/entity/kraken_cinematic.animation.json");}
                @Override public void setCustomAnimations(BossesRiseBridge.FullKraken entity,long instance,software.bernie.geckolib.animation.AnimationState<BossesRiseBridge.FullKraken> state){
                    double age=entity.tickCount+state.getPartialTick();
                    float curl=(float)Math.sin(Math.clamp((age-26)/40.0,0,1)*Math.PI);
                    for(int root:new int[]{1,37,61,73,97,109,121,133})for(int segment=0;segment<6;segment++){
                        int index=root+segment;String name="left_front_tentacle"+(index==1?"":index);
                        getBone(name).ifPresent(bone->bone.setRotX(bone.getInitialSnapshot().getRotX()-curl*.12f));
                    }
                }
            }).withScale(.27f));
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
