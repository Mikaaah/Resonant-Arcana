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
    static List<MutableComponent> details(Spell s,int level){
        var out=new ArrayList<MutableComponent>();out.add(Component.literal(s.description()));
        out.add(Component.literal(s.role()+" • "+school(s.theme)+" • Level "+WandItem.roman(level)));
        if(s.damage>0)out.add(Component.literal(String.format(Locale.ROOT,"Base damage: %.1f%s",s.damageAtLevel(level),s.pulses()>1?" per hit / pulse":"")));
        if(s.healing()>0)out.add(Component.literal(String.format(Locale.ROOT,"Base healing: %.1f health%s",s.healingAtLevel(level),s.pulses()>1?" per pulse":"")));
        out.add(Component.literal(String.format(Locale.ROOT,"Mana %d • Windup %.2fs • Cooldown %.1fs",s.manaAtLevel(level),s.castTicks()/20.0,s.cooldown/20.0)));
        if(s.range>0)out.add(Component.literal("Range: "+format(s.range)+" blocks"));
        if(s.radius>0)out.add(Component.literal("Radius / half-width: "+format(s.radius)+" blocks"));
        if(s.duration()>0)out.add(Component.literal(String.format(Locale.ROOT,"Base duration: %.1fs",s.duration()/20.0)));
        return out;
    }
    private static String format(double n){return n==(int)n?Integer.toString((int)n):Double.toString(n);}
}
