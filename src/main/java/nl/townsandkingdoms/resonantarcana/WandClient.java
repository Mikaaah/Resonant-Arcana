package nl.townsandkingdoms.resonantarcana;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
public final class WandClient {
    public static final KeyMapping CAST=new KeyMapping("key.resonant_arcana.cast",InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_G,"key.categories.resonant_arcana");
    public static final KeyMapping NEXT=new KeyMapping("key.resonant_arcana.next",InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_V,"key.categories.resonant_arcana");
    public static final KeyMapping SELECT=new KeyMapping("key.resonant_arcana.select",InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_X,"key.categories.resonant_arcana");
    private static int visualBursts;
    private static boolean reversalAttackHeld,reversalCastHeld;
    private WandClient() {}
    @SubscribeEvent public static void input(InputEvent.InteractionKeyMappingTriggered e) {
        var mc=Minecraft.getInstance();
        if(mc.player==null||mc.screen!=null||!(mc.player.getMainHandItem().getItem() instanceof WandItem))return;
        if(e.isAttack()&&ArcanaConfig.MOUSE_CONTROLS.get()) {
            e.setCanceled(true);e.setSwingHand(false);
            if(mc.player.getMainHandItem().getItem() instanceof WandItem wand&&wand.selected(mc.player.getMainHandItem())==Spell.CRIMSON_REVERSAL){
                if(reversalAttackHeld)return;reversalAttackHeld=true;
            }
            PacketDistributor.sendToServer(new WandNetwork.Action(0));
        } else if(e.isUseItem()) {
            // Cancel both hands so block/offhand interaction cannot leak through the wand action.
            e.setCanceled(true);e.setSwingHand(false);
            if(e.getHand()==InteractionHand.MAIN_HAND)PacketDistributor.sendToServer(new WandNetwork.Action(mc.player.isShiftKeyDown()?2:1));
        }
    }
    @SubscribeEvent public static void tick(ClientTickEvent.Post e) {
        visualBursts=0;
        NativeSpellEffects.tick();AaaEffects.tick();ExplosionAudio.tick();
        var mc=Minecraft.getInstance();
        if(!mc.options.keyAttack.isDown())reversalAttackHeld=false;
        if(!CAST.isDown())reversalCastHeld=false;
        while(SELECT.consumeClick())if(mc.screen==null&&mc.player!=null&&mc.player.getMainHandItem().getItem() instanceof WandItem wand)mc.setScreen(new WandSelectScreen(wand,mc.player.getMainHandItem()));
        while(CAST.consumeClick())if(mc.screen==null&&mc.player!=null&&mc.player.getMainHandItem().getItem() instanceof WandItem wand){
            if(wand.selected(mc.player.getMainHandItem())==Spell.CRIMSON_REVERSAL){if(reversalCastHeld)continue;reversalCastHeld=true;}
            PacketDistributor.sendToServer(new WandNetwork.Action(0));
        }
        while(NEXT.consumeClick())if(mc.screen==null&&mc.player!=null&&mc.player.getMainHandItem().getItem() instanceof WandItem)PacketDistributor.sendToServer(new WandNetwork.Action(mc.player.isShiftKeyDown()?2:1));
    }
    @SubscribeEvent public static void hud(net.neoforged.neoforge.client.event.RenderGuiEvent.Post event){
        var mc=Minecraft.getInstance();
        if(!ArcanaConfig.WAND_HUD.get()||mc.player==null||mc.screen!=null||mc.options.hideGui||!(mc.player.getMainHandItem().getItem() instanceof WandItem wand))return;
        if(io.redspace.ironsspellbooks.api.magic.MagicData.getPlayerMagicData(mc.player).isCasting())return;
        var stack=mc.player.getMainHandItem();Spell spell=wand.selected(stack);int level=wand.level(stack);
        var g=event.getGuiGraphics();int x=g.guiWidth()/2-110,y=g.guiHeight()-84;
        g.fill(x,y,x+220,y+29,0xBB0D1421);g.fill(x,y+27,x+220,y+29,0xFF000000|spell.theme.color);
        var icon=net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"textures/gui/spell_icons/"+spell.bookId()+".png");
        g.blit(icon,x+5,y+5,0,0,19,19,19,19);
        g.drawString(mc.font,spell.title+" "+WandItem.roman(level),x+29,y+4,0xFF000000|spell.theme.color,false);
        g.drawString(mc.font,mc.font.plainSubstrByWidth(spell.manaAtLevel(level)+" base mana · "+SELECT.getTranslatedKeyMessage().getString()+" to choose",185),x+29,y+16,0xFFB4C2D6,false);
    }
    public static void burst(WandNetwork.Burst b) {
        var mc=Minecraft.getInstance();
        if(!Float.isFinite(b.scale())||b.scale()<0.1f||b.scale()>4||mc.level==null||mc.player==null||visualBursts>=8||mc.player.distanceToSqr(b.x(),b.y(),b.z())>96*96)return;
        visualBursts++;
        if(b.color()==Spell.Theme.BLOOD.color)return;
        // Use coloured sparks directly: no automatic white fade or duplicate vanilla blast audio.
        float r=((b.color()>>16)&255)/255f,g=((b.color()>>8)&255)/255f,blue=(b.color()&255)/255f;
        int count=Math.min(144,(int)(24*ArcanaConfig.PARTICLES.get()*Math.max(1,b.scale())));
        for(int i=0;i<count;i++){
            double y=1-2*(i+0.5)/count,radial=Math.sqrt(1-y*y),a=i*2.399963229728653;
            double speed=(0.12+(i%3)*0.035)*b.scale();
            var spark=mc.particleEngine.createParticle(ParticleTypes.FIREWORK,b.x(),b.y(),b.z(),Math.cos(a)*radial*speed,y*speed,Math.sin(a)*radial*speed);
            if(spark!=null){
                if(b.color()==Spell.Theme.SOUL.color&&i%3==0)spark.setColor(0.66f,0.43f,0.94f);else spark.setColor(r,g,blue);
                spark.setLifetime(20+i%12);
            }
        }
    }
    @EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT,bus=EventBusSubscriber.Bus.MOD)
    public static final class Registration {
        @SubscribeEvent public static void renderers(net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers e){
            e.registerEntityRenderer(SpellCentrepieces.SWORD.get(),ctx->new net.minecraft.client.renderer.entity.DisplayRenderer.ItemDisplayRenderer(ctx){});
            e.registerEntityRenderer(SpellCentrepieces.PROP.get(),ctx->new net.minecraft.client.renderer.entity.DisplayRenderer.BlockDisplayRenderer(ctx){});
        }
        @SubscribeEvent public static void keys(RegisterKeyMappingsEvent e){e.register(CAST);e.register(NEXT);e.register(SELECT);}
    }
}
