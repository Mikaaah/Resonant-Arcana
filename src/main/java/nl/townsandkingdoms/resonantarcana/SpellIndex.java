package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;

/** Searchable spell metadata. Scrolls share one item ID, so their keywords belong to the spell. */
final class SpellIndex {
    private static final Map<ResourceLocation,Spell> IDS=new HashMap<>();
    private static final Map<Spell,List<String>> TAGS=new EnumMap<>(Spell.class);
    static {
        for(Spell spell:Spell.values()){
            IDS.put(IronsCompat.id(spell),spell);var tags=new LinkedHashSet<String>();
            tags.add(SpellInfo.school(spell.theme).toLowerCase(Locale.ROOT));
            tags.add(spell.category().title.toLowerCase(Locale.ROOT));tags.add(tier(spell).toLowerCase(Locale.ROOT));
            if(CavesCompat.optional(spell)){tags.add("alexscaves");tags.add(spell.definition.area().replace('_',' '));}
            if(spell.damage>0)tags.add("damage");if(spell.healing()>0)tags.add("healing");
            if(spell.radius>=3&&spell.category()!=SpellCategory.MOBILITY)tags.add("aoe");
            switch(spell.kind){
                case BUFF,MARTIAL_BUFF,BANNER,ASCENSION,VOID_DOMAIN -> tags.add("buff");
                case CAPTURE,CHOKE,SUSPEND,STUN,GAZE,EXILE -> tags.add("debuff");
                case BLINK,DASH,LEAP,REVERSAL -> tags.add("mobility");
                case TOTEM,TRAP,DOMAIN -> tags.add("ground");
                default -> {}
            }
            if(spell==Spell.VOID_DOMAIN){tags.add("debuff");tags.add("void");tags.add("domain");}
            if(!CavesCompat.optional(spell)&&spell.theme==Spell.Theme.FIRE&&spell.damage>0)tags.add("burning");
            if(!CavesCompat.optional(spell)&&spell.theme==Spell.Theme.ICE&&spell.damage>0)tags.add("slow");
            if(spell.theme==Spell.Theme.POISON&&spell.damage>0&&!BossesRiseCompat.optional(spell)&&!CavesCompat.optional(spell))tags.add("poison");
            if(!CavesCompat.optional(spell)&&spell.theme==Spell.Theme.DARK&&spell.damage>0)tags.add("weakness");
            if(spell.pulses()>1)tags.add("multi hit");if(BossesRiseCompat.optional(spell))tags.add("bossesrise");
            if(CavesCompat.optional(spell)){
                switch(spell){
                    case TECTONIC_STAMPEDE -> tags.add("buff melee dinosaur resistance");case POLARITY_BOLT -> tags.add("magnet pull");case EMP_SURGE -> tags.add("stun interrupt emp");case ACID_GLOB -> tags.add("poison acid");
                    case FROSTMINT_HEX -> tags.add("slow freeze buff sugar");case DESOLATE_DAGGER -> tags.add("piercing weakness");case BRINE_BUBBLE -> tags.add("bubble debuff");
                    case ABYSSAL_DELUGE -> tags.add("vortex pull");case HULLBREAKER_SURGE -> tags.add("wave slow");case FORSAKEN_SONAR -> tags.add("sonar slow reveal");
                    case FORLORN_RECKONING -> tags.add("darkness reveal");case LUX_EXTINCTION -> tags.add("burning tephra lux");case NUCLEAR_STRIKE -> tags.add("nuke weakness");default -> {}
                }
            }
            TAGS.put(spell,List.copyOf(tags));
        }
    }
    static String tier(Spell s){if(GodSpells.handles(s))return "God";if(CavesCompat.optional(s))return s.definition.tier().substring(0,1)+s.definition.tier().substring(1).toLowerCase(Locale.ROOT);return s.category()==SpellCategory.CATACLYSM||s==Spell.DOOM||s==Spell.DOOM_BEAM?"Cataclysm":s.manaCost()>=100||s.castTicks()>=60?"Master":s.manaCost()>=55||s.castTicks()>=30?"Expert":s.manaCost()>=30||s.castTicks()>=15?"Adept":"Novice";}
    static List<String> tags(Spell s){return TAGS.get(s);}
    static boolean matches(Spell s,String query){
        String haystack=(s.title+" "+s.bookId()+" "+String.join(" ",tags(s))).toLowerCase(Locale.ROOT);
        for(String word:query.toLowerCase(Locale.ROOT).trim().split("\\s+"))if(!haystack.contains(word))return false;return true;
    }
    static List<Spell> ordered(){return Arrays.stream(Spell.enabledValues()).sorted(Comparator.comparing((Spell s)->SpellInfo.school(s.theme)).thenComparing(s->s.category().ordinal()).thenComparing(s->s.title)).toList();}
    static void tooltip(ItemTooltipEvent event){
        if(!event.getItemStack().is(io.redspace.ironsspellbooks.registries.ItemRegistry.SCROLL.get()))return;
        var container=ISpellContainer.get(event.getItemStack());if(container==null||container.isEmpty())return;
        Spell spell=IDS.get(container.getSpellAtIndex(0).getSpell().getSpellResource());if(spell==null)return;
        for(var line:SpellInfo.wrap("Keywords: "+String.join(" · ",tags(spell)),52))event.getToolTip().add(Component.literal(line).withStyle(ChatFormatting.DARK_GRAY));
    }
    private SpellIndex(){}
}
