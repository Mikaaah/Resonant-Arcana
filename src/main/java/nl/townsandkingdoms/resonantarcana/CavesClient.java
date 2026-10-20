package nl.townsandkingdoms.resonantarcana;

import com.mojang.blaze3d.vertex.PoseStack;
import com.github.alexmodguy.alexscaves.server.entity.item.DinosaurSpiritEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.SpinningPeppermintEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.SugarStaffHexEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.*;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import io.redspace.ironsspellbooks.render.SpellRenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/** Loaded only on a client with the supported optional mod. Proxies are never added or ticked. */
final class CavesClient {
    static void register(IEventBus bus){bus.addListener(CavesClient::renderers);}
    private static void renderers(EntityRenderersEvent.RegisterRenderers event){event.registerEntityRenderer(CaveVisual.TYPE.get(),Renderer::new);}
    private static ResourceLocation asset(String path){return ResourceLocation.fromNamespaceAndPath(CavesCompat.MOD,path);}
    private static final class Spirit extends DinosaurSpiritEntity {
        Spirit(EntityType<?> type,net.minecraft.world.level.Level level,boolean large){super(type,level);setDinosaurType(large?DinosaurType.TREMORSAURUS:DinosaurType.SUBTERRANODON);}
        @Override public float getFadeIn(float partial){return Math.clamp((tickCount+partial)/5f,0,1);}
        @Override public float getAbilityProgress(float partial){return .75f;}
    }
    private static final class Peppermint extends SpinningPeppermintEntity {
        Peppermint(EntityType<?> type,net.minecraft.world.level.Level level){super(type,level);}
        @Override public float getDespawnTime(float partial){return 40;}
    }
    private static final class Hex extends SugarStaffHexEntity {
        Hex(EntityType<?> type,net.minecraft.world.level.Level level){super(type,level);}
        @Override public float getDespawnTime(float partial){return 40;}
    }
    private static Entity proxy(CaveVisual marker){
        int model=marker.model();if(model<0||model>17||model==15||model==16||model==18)return null;
        String name=model==17?"dinosaur_spirit":CaveVisual.MODELS[model];
        var id=asset(name);if(!BuiltInRegistries.ENTITY_TYPE.containsKey(id))return null;
        var type=BuiltInRegistries.ENTITY_TYPE.get(id);
        Entity entity=switch(name){
            case "dinosaur_spirit" -> new Spirit(type,marker.level(),model==17);
            case "spinning_peppermint" -> new Peppermint(type,marker.level());
            case "sugar_staff_hex" -> new Hex(type,marker.level());
            default -> type.create(marker.level());
        };
        if(entity instanceof DinosaurSpiritEntity spirit){Entity owner=marker.level().getEntity(marker.owner());if(owner!=null)spirit.setPlayerUUID(owner.getUUID());}
        if(entity instanceof IAnimatedEntity animated){
            if(entity instanceof ForsakenEntity)animated.setAnimation(model==5?ForsakenEntity.ANIMATION_SONIC_BLAST:ForsakenEntity.ANIMATION_SONIC_ATTACK);
            else if(entity instanceof LuxtructosaurusEntity)animated.setAnimation(LuxtructosaurusEntity.ANIMATION_SPEW_FLAMES);
            else if(entity instanceof TremorzillaEntity)animated.setAnimation(TremorzillaEntity.ANIMATION_PREPARE_BREATH);
            else if(entity instanceof HullbreakerEntity)animated.setAnimation(HullbreakerEntity.ANIMATION_BITE);
        }
        return entity;
    }
    private static final class Renderer extends EntityRenderer<CaveVisual> {
        Renderer(EntityRendererProvider.Context context){super(context);shadowRadius=0;}
        @Override public boolean shouldRender(CaveVisual marker,net.minecraft.client.renderer.culling.Frustum frustum,double x,double y,double z){
            if(marker.model()==15||marker.model()==16||marker.model()==18){Entity owner=marker.level().getEntity(marker.owner());if(owner==null)return false;
                Vec3 start=owner.getEyePosition(),end=start.add(owner.getLookAngle().scale(marker.model()==16?48:64));return frustum.isVisible(new net.minecraft.world.phys.AABB(start,end).inflate(4));}
            return frustum.isVisible(marker.getBoundingBox().inflate(marker.model()==3||marker.model()==2?12:4));
        }
        @Override public ResourceLocation getTextureLocation(CaveVisual marker){return asset("textures/entity/tremorzilla/tremorzilla_beam_inner.png");}
        @SuppressWarnings({"rawtypes","unchecked"})
        @Override public void render(CaveVisual marker,float yaw,float partial,PoseStack pose,MultiBufferSource buffers,int light){
            if(marker.model()==15||marker.model()==16||marker.model()==18){beam(marker,partial,pose,buffers);return;}
            if(marker.renderProxy==null)marker.renderProxy=proxy(marker);
            Entity entity=marker.renderProxy;if(entity==null)return;
            entity.setPos(marker.position());entity.xo=marker.xo;entity.yo=marker.yo;entity.zo=marker.zo;
            entity.setYRot(marker.getYRot());entity.yRotO=marker.yRotO;entity.tickCount=marker.tickCount;
            if(entity instanceof LivingEntity living){living.yBodyRot=living.yBodyRotO=marker.getYRot();living.yHeadRot=living.yHeadRotO=marker.getYRot();}
            if(entity instanceof IAnimatedEntity animated&&animated.getAnimation()!=null){int duration=animated.getAnimation().getDuration();animated.setAnimationTick(duration<=1?0:marker.tickCount%(duration-1)+1);}
            pose.pushPose();pose.scale(marker.scale(),marker.scale(),marker.scale());
            EntityRenderer renderer=Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
            renderer.render(entity,yaw,partial,pose,buffers,light);pose.popPose();
        }
        private void beam(CaveVisual marker,float partial,PoseStack pose,MultiBufferSource buffers){
            Entity caster=marker.level().getEntity(marker.owner());if(caster==null||!caster.isAlive())return;
            Vec3 eye=BeamRitual.body(caster,partial),dir=caster.getViewVector(partial).normalize();
            Vec3 start=marker.level().clip(new ClipContext(eye,eye.add(dir.scale(BeamRitual.FORWARD)),ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,caster)).getLocation();
            double range=marker.model()==16?Spell.TREMORZILLA_BREATH.range:64;
            Vec3 end=marker.level().clip(new ClipContext(start,start.add(dir.scale(range)),ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,caster)).getLocation();
            float length=(float)start.distanceTo(end);if(length<.05)return;
            Vec3 offset=start.subtract(marker.getPosition(partial));pose.pushPose();pose.translate(offset.x,offset.y,offset.z);
            pose.mulPose(new Quaternionf().rotationTo(new Vector3f(0,0,1),end.subtract(start).normalize().toVector3f()));
            if(marker.model()==16||marker.model()==18){TremorBeamRenderer.render(marker,partial,length,pose,buffers);
                if(marker.lastBeamFrame!=marker.level().getGameTime()){marker.lastBeamFrame=marker.level().getGameTime();CavesClientEffects.tremor((net.minecraft.client.multiplayer.ClientLevel)marker.level(),start,end,marker.variant(),marker.model()==18);}
                pose.popPose();return;}
            float scroll=-(marker.tickCount+partial)*.06f;boolean green=false;
            ResourceLocation texture=asset(green?"textures/entity/tremorzilla/tremorzilla_beam_inner.png":"textures/entity/raygun/raygun_blue_ray.png");
            var core=buffers.getBuffer(RenderType.entityTranslucent(texture));
            SpellRenderingHelper.drawHull(Vec3.ZERO,new Vec3(0,0,length),green?1.0f:.65f,green?1.0f:.65f,pose.last(),core,255,255,255,255,scroll,scroll+length/5);
            var halo=buffers.getBuffer(RenderType.entityTranslucent(green?asset("textures/entity/tremorzilla/tremorzilla_beam_outer.png"):SpellRenderingHelper.BEACON));
            SpellRenderingHelper.drawHull(Vec3.ZERO,new Vec3(0,0,length),green?2.3f:2.8f,green?2.3f:2.8f,pose.last(),halo,100,green?255:170,green?80:255,120,scroll*.6f,scroll*.6f+length/7);
            pose.popPose();
        }
    }
    private CavesClient(){}
}
