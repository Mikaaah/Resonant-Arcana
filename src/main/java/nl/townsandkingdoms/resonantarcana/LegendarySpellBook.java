package nl.townsandkingdoms.resonantarcana;

import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import java.util.List;

/** Normal, inscribable Iron's books. Perks are native Curios attributes, active only when equipped. */
final class LegendarySpellBook extends SpellBook {
    enum Style { RUNIC,PHOENIX,DEATHCHILL }
    final Style style;
    LegendarySpellBook(Style style){
        super(style==Style.RUNIC?14:12,new Properties().stacksTo(1).rarity(Rarity.EPIC));this.style=style;
        AttributeContainer mana=new AttributeContainer(AttributeRegistry.MAX_MANA,style==Style.RUNIC?450:style==Style.PHOENIX?400:425,Operation.ADD_VALUE);
        switch(style){
            case RUNIC -> withSpellbookAttributes(mana,
                new AttributeContainer(AttributeRegistry.EVOCATION_SPELL_POWER,.15,Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.ENDER_SPELL_POWER,.15,Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.COOLDOWN_REDUCTION,.10,Operation.ADD_MULTIPLIED_BASE));
            case PHOENIX -> withSpellbookAttributes(mana,
                new AttributeContainer(AttributeRegistry.FIRE_SPELL_POWER,.25,Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.FIRE_MAGIC_RESIST,.15,Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.MANA_REGEN,.15,Operation.ADD_MULTIPLIED_BASE));
            case DEATHCHILL -> withSpellbookAttributes(mana,
                new AttributeContainer(AttributeRegistry.ICE_SPELL_POWER,.25,Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.ELDRITCH_SPELL_POWER,.15,Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.CAST_TIME_REDUCTION,.10,Operation.ADD_MULTIPLIED_BASE));
        }
    }
    @Override public void appendHoverText(ItemStack stack,TooltipContext context,List<Component> lines,TooltipFlag flags){
        lines.add(Component.literal("Legendary · "+getMaxSpellSlots()+" spell slots").withStyle(ChatFormatting.GOLD));
        lines.add(Component.literal("Inscribe spells at Iron's inscription table. Equip in the spellbook slot for perks.").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack,context,lines,flags);
    }
}
