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
    private final List<String> legacy;
    public WandItem(String id,Spell... spells) {
        super(new Properties().stacksTo(1));this.spells=List.of(spells);
        this.legacy=WandLayouts.LEGACY.getOrDefault(id,this.spells.stream().map(Enum::name).toList());
    }
    public int selection(ItemStack stack) {
        CompoundTag tag=stack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag();
        String id=tag.getString("kingdomwands_spell_id");
        if(id.isEmpty())id=legacy.get(Math.floorMod(tag.getInt("kingdomwands_spell"),legacy.size()));
        for(int i=0;i<spells.size();i++)if(spells.get(i).name().equals(id))return i;
        // A spell moved off this wand starts at its first valid spell, never an unrelated numeric slot.
        return 0;
    }

    public Spell selected(ItemStack stack) { return spells.get(selection(stack)); }
    public void select(ItemStack stack,int index) {
        CompoundTag tag=stack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag();
        int selected=Math.floorMod(index,spells.size());
        tag.putInt("kingdomwands_spell",selected);tag.putString("kingdomwands_spell_id",spells.get(selected).name());
        stack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));
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
        lines.add(Component.literal(spell.description()).withStyle(style->style.withColor(spell.theme.color)));
        lines.add(Component.literal("Left-click: cast | Right-click: next spell").withStyle(ChatFormatting.GRAY));
        lines.add(Component.literal("Sneak + right-click: previous spell").withStyle(ChatFormatting.GRAY));
        lines.add(Component.literal("Alternate cast key: set in Controls").withStyle(ChatFormatting.DARK_GRAY));
    }
}
