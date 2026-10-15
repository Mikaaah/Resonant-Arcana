package nl.townsandkingdoms.kingdomwands;

import com.mojang.blaze3d.platform.InputConstants;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.component.FireworkExplosion;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid=KingdomWands.ID,value=Dist.CLIENT)
public final class WandClient {
    public static final KeyMapping CAST=new KeyMapping("key.kingdomwands.cast",InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_G,"key.categories.kingdomwands");
    public static final KeyMapping NEXT=new KeyMapping("key.kingdomwands.next",InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_V,"key.categories.kingdomwands");
    private static int visualBursts;
    private WandClient() {}
    @SubscribeEvent public static void input(InputEvent.InteractionKeyMappingTriggered e) {
        var mc=Minecraft.getInstance();
        if(mc.player==null||mc.screen!=null||!(mc.player.getMainHandItem().getItem() instanceof WandItem))return;
        if(e.isAttack()&&KingdomWands.MOUSE_CONTROLS.get()) {
            e.setCanceled(true);e.setSwingHand(false);PacketDistributor.sendToServer(new WandNetwork.Action(0));
        } else if(e.isUseItem()) {
            // Cancel both hands so block/offhand interaction cannot leak through the wand action.
            e.setCanceled(true);e.setSwingHand(false);
            if(e.getHand()==InteractionHand.MAIN_HAND)PacketDistributor.sendToServer(new WandNetwork.Action(mc.player.isShiftKeyDown()?2:1));
        }
    }
    @SubscribeEvent public static void tick(ClientTickEvent.Post e) {
        visualBursts=0;
        var mc=Minecraft.getInstance();
        while(CAST.consumeClick())if(mc.screen==null&&mc.player!=null&&mc.player.getMainHandItem().getItem() instanceof WandItem)PacketDistributor.sendToServer(new WandNetwork.Action(0));
        while(NEXT.consumeClick())if(mc.screen==null&&mc.player!=null&&mc.player.getMainHandItem().getItem() instanceof WandItem)PacketDistributor.sendToServer(new WandNetwork.Action(mc.player.isShiftKeyDown()?2:1));
    }
    public static void burst(WandNetwork.Burst b) {
        var mc=Minecraft.getInstance();
        if(mc.level==null||mc.player==null||visualBursts>=8||mc.player.distanceToSqr(b.x(),b.y(),b.z())>96*96)return;
        visualBursts++;
        mc.level.createFireworks(b.x(),b.y(),b.z(),0,0,0,List.of(new FireworkExplosion(
            FireworkExplosion.Shape.SMALL_BALL,IntList.of(b.color()),IntList.of(0xFFFFFF),true,true)));
    }
    @EventBusSubscriber(modid=KingdomWands.ID,value=Dist.CLIENT,bus=EventBusSubscriber.Bus.MOD)
    public static final class Registration {
        @SubscribeEvent public static void keys(RegisterKeyMappingsEvent e){e.register(CAST);e.register(NEXT);}
    }
}
