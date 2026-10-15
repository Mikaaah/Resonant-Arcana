package nl.townsandkingdoms.kingdomwands;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayer;

public final class WandItem extends Item {
    public final List<Spell> spells;
    public WandItem(Spell... spells) { super(new Properties().stacksTo(1)); this.spells=List.of(spells); }
    public int selection(ItemStack stack) {
        return Math.floorMod(stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getInt("kingdomwands_spell"),spells.size());
    }
    public Spell selected(ItemStack stack) { return spells.get(selection(stack)); }
    public void select(ItemStack stack,int index) {
        CompoundTag tag=stack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag();
        tag.putInt("kingdomwands_spell",Math.floorMod(index,spells.size())); stack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));
    }
    public void cycle(ServerPlayer player, ItemStack stack,int step) {
        select(stack,selection(stack)+step);
        player.displayClientMessage(Component.literal(selected(stack).title+"  ["+(selection(stack)+1)+"/"+spells.size()+"]").withStyle(ChatFormatting.LIGHT_PURPLE),true);
        SpellEngine.switchEffect(player,selected(stack).theme);
    }
    @Override public InteractionResultHolder<ItemStack> use(Level level,Player player,InteractionHand hand) {
        ItemStack stack=player.getItemInHand(hand);
        if(hand!=InteractionHand.MAIN_HAND) return InteractionResultHolder.pass(stack);
        if(player instanceof ServerPlayer server) SpellEngine.action(server, player.isShiftKeyDown()?2:1);
        return InteractionResultHolder.sidedSuccess(stack,level.isClientSide());
    }
    @Override public boolean canAttackBlock(net.minecraft.world.level.block.state.BlockState state, Level level, net.minecraft.core.BlockPos pos, Player player) { return false; }
    @Override public boolean isFoil(ItemStack stack) {return true;}
    @Override public void appendHoverText(ItemStack stack,TooltipContext context,List<Component> lines,TooltipFlag flags) {
        Spell spell=selected(stack);
        lines.add(Component.literal(spell.title+"  ["+(selection(stack)+1)+"/"+spells.size()+"]").withStyle(ChatFormatting.LIGHT_PURPLE));
        lines.add(Component.literal("Left-click: cast | Right-click: next spell").withStyle(ChatFormatting.GRAY));
        lines.add(Component.literal("Sneak + right-click: previous spell").withStyle(ChatFormatting.GRAY));
        lines.add(Component.literal("Alternate cast key: set in Controls").withStyle(ChatFormatting.DARK_GRAY));
    }
}
