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

/** A casting implement with persistent per-spell ranks learned from native Iron's scrolls. */
public final class WandItem extends Item {
    public final List<Spell> spells;
    public final String id;
    private final List<String> legacy;
    public WandItem(String id,Spell... spells){
        super(new Properties().stacksTo(1));this.id=id;this.spells=List.of(spells);
        legacy=WandLayouts.LEGACY.getOrDefault(id,this.spells.stream().map(Enum::name).toList());
    }
    private static CompoundTag data(ItemStack stack){return stack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag();}
    public int selection(ItemStack stack){
        var tag=data(stack);String selected=tag.getString("kingdomwands_spell_id");
        if(selected.isEmpty())selected=legacy.get(Math.floorMod(tag.getInt("kingdomwands_spell"),legacy.size()));
        for(int i=0;i<spells.size();i++)if(spells.get(i).name().equals(selected))return i;return 0;
    }
    public Spell selected(ItemStack stack){return spells.get(selection(stack));}
    public int level(ItemStack stack,Spell spell){return Math.clamp(data(stack).getCompound("resonant_arcana_levels").getInt(spell.name()),1,spell.levelCap());}
    public int level(ItemStack stack){return level(stack,selected(stack));}
    public void learn(ItemStack stack,Spell spell,int rank){
        if(!spells.contains(spell))throw new IllegalArgumentException("Spell does not belong to this wand");
        var tag=data(stack);var levels=tag.getCompound("resonant_arcana_levels");levels.putInt(spell.name(),Math.clamp(rank,1,spell.levelCap()));
        tag.put("resonant_arcana_levels",levels);stack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));
    }
    public void select(ItemStack stack,int index){
        var tag=data(stack);int selected=Math.floorMod(index,spells.size());
        tag.putInt("kingdomwands_spell",selected);tag.putString("kingdomwands_spell_id",spells.get(selected).name());stack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));
    }
    public void cycle(ServerPlayer player,ItemStack stack,int step){select(stack,selection(stack)+step);announce(player,stack);}
    public void announce(ServerPlayer player,ItemStack stack){
        player.displayClientMessage(Component.literal(selected(stack).title+" "+roman(level(stack))+"  ["+(selection(stack)+1)+"/"+spells.size()+"]").withStyle(style->style.withColor(selected(stack).theme.color)),true);
    }
    static String roman(int level){return switch(level){case 1->"I";case 2->"II";case 3->"III";case 4->"IV";default->"V";};}
    @Override public InteractionResultHolder<ItemStack> use(Level level,Player player,InteractionHand hand){
        var stack=player.getItemInHand(hand);if(hand!=InteractionHand.MAIN_HAND)return InteractionResultHolder.pass(stack);
        if(player instanceof ServerPlayer server)SpellEngine.action(server,player.isShiftKeyDown()?2:1);
        return InteractionResultHolder.sidedSuccess(stack,level.isClientSide());
    }
    @Override public boolean canAttackBlock(net.minecraft.world.level.block.state.BlockState state,Level level,net.minecraft.core.BlockPos pos,Player player){return false;}
    @Override public boolean isFoil(ItemStack stack){return level(stack)>=5;}
    @Override public void appendHoverText(ItemStack stack,TooltipContext context,List<Component> lines,TooltipFlag flags){
        Spell s=selected(stack);int rank=level(stack);
        lines.add(Component.literal(s.title+" "+roman(rank)+"  ["+(selection(stack)+1)+"/"+spells.size()+"]").withStyle(style->style.withColor(s.theme.color)));
        lines.add(Component.literal(s.role()+" • "+SpellInfo.school(s.theme)).withStyle(ChatFormatting.DARK_AQUA));
        lines.add(Component.literal(String.format(java.util.Locale.ROOT,"Mana %d  •  Cast %.2fs  •  Cooldown %.1fs",s.manaAtLevel(rank),s.castTicks()/20.0,s.cooldown/20.0)).withStyle(ChatFormatting.GRAY));
        if(s.damage>0)lines.add(Component.literal(String.format(java.util.Locale.ROOT,"Base damage: %.1f%s",s.damageAtLevel(rank),s.pulses()>1?" per hit / pulse":"")).withStyle(ChatFormatting.GOLD));
        if(s.healing()>0)lines.add(Component.literal(String.format(java.util.Locale.ROOT,"Base healing: %.1f health%s",s.healingAtLevel(rank),s.pulses()>1?" per pulse":"")).withStyle(ChatFormatting.GREEN));
        lines.add(Component.literal("Cast and selection keys: Controls → Resonant Arcana").withStyle(ChatFormatting.GRAY));
        lines.add(Component.literal("Sneak-use with a matching scroll in your offhand to learn its level.").withStyle(ChatFormatting.DARK_GRAY));
    }
}
