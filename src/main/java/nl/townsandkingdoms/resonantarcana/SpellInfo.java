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
        if(s.duration()>0&&s!=Spell.WARSCREAM)out.add(Component.literal(String.format(Locale.ROOT,"Base duration: %.1fs",s.duration()/20.0)));
        out.addAll(martial(s,s.power(level)));return out;
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
    private static String format(double n){return n==(int)n?Integer.toString((int)n):Double.toString(n);}
}
