package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/** Public information uses the same values as the native spell definitions. */
final class SpellInfo {
    private SpellInfo(){}
    static String school(Spell.Theme t){return switch(t){
        case MAGIC->"Evocation";case BLOOD->"Blood";case FIRE->"Fire";case DARK,GRAVITY->"Ender";
        case THUNDER->"Lightning";case SOUL->"Eldritch";case POISON->"Nature";case HOLY->"Holy";case ICE->"Ice";
    };}
    static List<String> wrap(String text,int width){
        var lines=new ArrayList<String>();StringBuilder line=new StringBuilder();
        for(String word:text.trim().split("\\s+")){
            if(!line.isEmpty()&&line.length()+word.length()+1>width){lines.add(line.toString());line.setLength(0);}
            if(!line.isEmpty())line.append(' ');line.append(word);
        }
        if(!line.isEmpty())lines.add(line.toString());return lines;
    }
    static List<MutableComponent> overview(Spell s,float power,double castSeconds){
        var out=new ArrayList<MutableComponent>();
        out.add(Component.literal(SpellInfo.school(s.theme)+" • "+s.category().title+" • "+SpellIndex.tier(s)).withStyle(net.minecraft.ChatFormatting.DARK_AQUA));
        for(String line:wrap(s.description(),52))out.add(Component.literal(line).withStyle(net.minecraft.ChatFormatting.GRAY));
        if(s.damage>0)out.add(Component.literal("Damage / hit: "+format(s.damage*power)).withStyle(net.minecraft.ChatFormatting.GOLD));
        String sequence=sequence(s);if(!sequence.isEmpty())for(String line:wrap(sequence,52))out.add(Component.literal(line).withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
        if(s.healing()>0)out.add(Component.literal("Healing / pulse: "+format(s.healing()*power)+" health").withStyle(net.minecraft.ChatFormatting.GREEN));
        if(s.range>0)out.add(Component.literal("Range: "+format(s.range)+" blocks").withStyle(net.minecraft.ChatFormatting.GRAY));
        String extent=switch(s.kind){
            case TARGET,CONFUSE,STUN,CAPTURE,CHOKE,SUSPEND,LAUNCH,PUSH,PULL,SIPHON,TETHER,EXECUTE,EXILE -> "";
            case CANNON -> "Blast radius: ";
            case CHANNEL,LANCE,RAIL,DEVASTATOR,BLADE_THRUST -> "Beam half-width: ";
            default -> "Area radius: ";
        };
        if(s==Spell.TREMORZILLA_BREATH||s==Spell.MAGNETAR_ANNIHILATION)extent="Beam half-width: ";
        if(CavesCompat.optional(s))out.add(Component.literal("Alex’s Caves • "+s.definition.area().replace('_',' ')).withStyle(net.minecraft.ChatFormatting.DARK_AQUA));
        if(s.radius>0&&!extent.isEmpty())out.add(Component.literal(extent+format(s.radius)+" blocks").withStyle(net.minecraft.ChatFormatting.GRAY));
        if(s==Spell.TECTONIC_STAMPEDE)out.add(Component.literal("Buff duration: "+format(20*Math.clamp(power,1,2))+"s").withStyle(net.minecraft.ChatFormatting.GRAY));
        if(s.duration()>0&&s!=Spell.TECTONIC_STAMPEDE&&s!=Spell.WARSCREAM&&s!=Spell.EXPLOSION&&s!=Spell.I_AM_ATOMIC)out.add(Component.literal("Base duration: "+format(s.duration()/20.0)+"s").withStyle(net.minecraft.ChatFormatting.GRAY));
        if(castSeconds>0)out.add(Component.literal("Cast time: "+format(castSeconds)+"s"+((RecordedSpells.handles(s)||s==Spell.ORBITAL_RAY)?" (fixed)":"")).withStyle(net.minecraft.ChatFormatting.GRAY));
        for(var line:martial(s,power))for(String wrapped:wrap(line.getString(),52))out.add(Component.literal(wrapped).withStyle(net.minecraft.ChatFormatting.GRAY));
        return out;
    }
    static String sequence(Spell s){if(GodSpells.handles(s))return switch(s){
        case THUNDER_OF_THE_HEAVENS -> "200 local lightning strikes; each has a 3-block radius.";
        case SOLAR_SUPERNOVA,TREMORZILLA_CATACLYSM -> "One damage hit per enemy per cast.";
        case ARCANE_CREATION -> "Six sector impacts; final area hit ×2.";
        case CONFECTION_APOCALYPSE,DRAGON_PRIMORDIAL_INFERNO -> "Six sector impacts; final area hit ×3.";
        case EVENTIDE_HORIZON -> "Four crossing cuts; final area hit ×2.";
        case ABSOLUTE_GLACIATION -> "Advancing front, four pulses; final shatter ×2.";
        case VERDANT_AWAKENING -> "Six pulses; final seedburst ×3.";
        case FORSAKEN_ECLIPSE,YETI_WORLD_HEART -> "Three fronts; final area hit ×3.";
        case HELVAR_FINAL_EDICT -> "Three fracture waves; final sword hit ×3.";
        case KRAKEN_WORLD_DROWN -> "Four paired slams; final area hit ×3.";
        case SANDWORM_WORLD_DEVOURER -> "Five travelling fronts; final area hit ×3.";
        case ABYSSAL_WORLD_TIDE -> "Six tidal collapses.";
        case PLANETARY_POLARITY -> "Six polarity pulses; final collision ×2.";
        default -> "Five normal pulses; final area hit ×2.";
    };return switch(s){
        case HELVARS_SWORDFALL -> "Blade ×1 within 3.5 blocks; outer wave ×0.4. One hit each.";
        case TECTONIC_STAMPEDE -> "Twenty-second ally blessing; no damage.";
        case HULLBREAKER_SURGE -> "1.2-second warning; one breach hit per enemy.";
        case LUX_EXTINCTION -> "Four dodgeable vent rounds; final core impact ×2.";
        case ABYSSAL_DELUGE,CANDY_CATASTROPHE -> "Four pulses; final area hit ×2.";
        case FORLORN_RECKONING -> "Three pulses; final area hit ×2.";
        case NUCLEAR_STRIKE -> "Two-second warning; one blast per enemy.";
        case TREMORZILLA_BREATH,MAGNETAR_ANNIHILATION -> "Follows your aim; one damage hit per enemy.";
        case GUMBALL_VOLLEY -> "Three projectiles; each can bounce once.";
        case DESOLATE_DAGGER -> "Pierces up to three enemies; one hit each.";
        case ASTRAL_APOCALYPSE -> "Nine local star impacts; final area hit ×2.";
        case WORLD_PYRE -> "Five local meteor impacts; final area hit ×2.5.";
        case HEAVENS_THUNDER -> "Six local lightning impacts; final area hit ×2.";
        case RIFT_COLLISION -> "Two crossing cuts; final area hit ×3.";
        case ETERNAL_WINTER -> "Eight pulses; final area hit ×3.";
        case CRIMSON_ECLIPSE,VOID_CORONATION,WORLDROOT_CATASTROPHE,FINAL_JUDGEMENT -> "Three pulses; final area hit ×2.";
        case SOUL_REQUIEM -> "Three expanding rings; final area hit ×2.";
        case KRAKENS_WRATH -> "One outer hit and one central hit ×3 per enemy.";
        case FUNERAL_BELL -> "Two normal tolls; final toll ×3.";
        case ORBITAL_RAY,I_AM_ATOMIC,EXPLOSION,DOOM,DOOM_BEAM,ARCANE_DEVASTATOR -> "One damage hit per enemy per cast.";
        default -> s.pulses()>1?(s.kind==Spell.Kind.FAN?"Projectiles: ":"Damage pulses: ")+s.pulses():"";
    };}
    static List<MutableComponent> details(Spell s,int level){
        var out=new ArrayList<MutableComponent>();
        out.add(Component.literal("Rank "+WandItem.roman(level)+" • Mana: "+s.manaAtLevel(level)+" • Cooldown: "+format(s.cooldown/20.0)+"s").withStyle(net.minecraft.ChatFormatting.AQUA));
        out.addAll(overview(s,s.power(level),s.castTicks()/20.0));return out;
    }
    static List<MutableComponent> martial(Spell s,float power){
        var out=new ArrayList<MutableComponent>();int rank=Math.clamp(Math.round((power-1)*4),0,4);
        if(s.kind==Spell.Kind.BLADE_ARC||s.kind==Spell.Kind.BLADE_THRUST)out.add(Component.literal(String.format(Locale.ROOT,"Plus %.0f%% of melee attack damage at release",50*power)));
        if(s==Spell.WARSCREAM)out.add(Component.literal("Resistance I • Taunt and defense: "+(3+rank)+"s • Three damage pulses"));
        if(s==Spell.BULWARK_CHANT)out.add(Component.literal(String.format(Locale.ROOT,"Temporary health: %.1f",4*Math.clamp(power,1,2))));
        if(s==Spell.ARCANE_EDGE)out.add(Component.literal(String.format(Locale.ROOT,"Melee damage: +%.1f • Attack speed: +%.1f%%",2+rank*.5,10+rank*2.5)));
        if(s==Spell.BATTLE_TEMPO)out.add(Component.literal(String.format(Locale.ROOT,"Attack and movement speed: +%.1f%%",10+rank*2.5)));
        if(s==Spell.BLOOD_REND)out.add(Component.literal(String.format(Locale.ROOT,"Healing cap per cast: %.1f health",3*power)));
        if(s.kind==Spell.Kind.MARTIAL_BUFF)out.add(Component.literal(String.format(Locale.ROOT,"Buff duration: %.1fs",s.duration()/20f*Math.clamp(power,1,2))));
        if(s==Spell.RIFT_DRAW||s==Spell.CRIMSON_REVERSAL||s==Spell.SPELLBREAKER)out.add(Component.literal(String.format(Locale.ROOT,"Plus %.0f%% of melee attack damage at release",50*power)));
        if(s==Spell.GLACIAL_COUNTER)out.add(Component.literal(String.format(Locale.ROOT,"Perfect counter: %.1f damage • Guard 0.9s • Perfect window 0.3s",s.damage*power*1.75)));
        if(s==Spell.LAST_STAND)out.add(Component.literal(String.format(Locale.ROOT,"Temporary health once per ally: %.1f • Resistance I",4*Math.clamp(power,1,2))));
        if(s==Spell.PHANTOM_FORMATION)out.add(Component.literal("Swords: "+(4+rank)+" • One per successful player melee hit"));
        if(s==Spell.THUNDERBRAND)out.add(Component.literal("Every third successful player melee hit • Up to 3 targets"));
        if(s==Spell.FUNERAL_BELL||s==Spell.KRAKENS_WRATH)out.add(Component.literal(String.format(Locale.ROOT,"Final hit: %.1f damage",s.damage*power*3)));
        if(s==Spell.CRIMSON_REVERSAL)out.add(Component.literal("Free native recast: 3s • Cooldown after return or expiry"));
        return out;
    }
    private static String format(double n){return n==(int)n?Integer.toString((int)n):String.format(Locale.ROOT,"%.2f",n).replaceAll("0+$", "");}
}
