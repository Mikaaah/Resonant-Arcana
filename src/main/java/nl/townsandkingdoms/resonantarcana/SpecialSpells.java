package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.SpellEngine.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;

/** A mobile flight blessing and a focused cannon, with no shared placed-field behavior. */
final class SpecialSpells {
    static boolean handles(Spell s){return s.kind==Spell.Kind.ASCENSION||s.kind==Spell.Kind.DEVASTATOR;}
    static boolean begin(Cast c){
        c.duration=c.spell.duration();
        if(c.spell.kind==Spell.Kind.ASCENSION){effect(c.caster,ArcanaEffects.SERAPH_FLIGHT,c.duration+1,0);return true;}
        c.pos=BeamRitual.origin(c.caster);
        if(c.spell==Spell.DOOM_BEAM)c.pos=BeamRitual.origin(c.caster);
        c.anchor=clipLoaded(c.level,c.pos,c.dir,beamRange(c),c.caster);return true;
    }
    static boolean tick(Cast c){
        if(c.spell.kind==Spell.Kind.ASCENSION){
            c.pos=c.caster.position();
            if(c.age%4==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.AREA,c.pos,c.pos.add(c.caster.getLookAngle()),c.spell.radius);
            if(c.age%10==1)for(var ally:SpellActions.allies(c,c.pos,c.spell.radius))effect(ally,ArcanaEffects.SERAPH_GRACE,30,0);
            if(c.age%20==1)SpellActions.healArea(c,c.pos,c.spell.radius,c.spell.healing(),false);
            return true;
        }
        Vec3 direction=c.caster.getLookAngle().normalize();c.pos=BeamRitual.origin(c.caster);
        c.anchor=clipLoaded(c.level,c.pos,direction,beamRange(c),c.caster);
        if(c.age%2==1)BeamRitual.sync(c.caster,c.spell,2,c.age/(double)c.duration,c.pos.distanceTo(c.anchor));
        if(c.age%2==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.LINK,c.pos,c.anchor,c.spell==Spell.DOOM_BEAM?6:3.2);
        if(c.age==1)RecordedSpells.release(c);
        {
            for(var victim:lineTargets(c,c.pos,c.anchor,c.spell.radius))
                if(visible(c,c.pos,victim.getBoundingBox().getCenter()))hit(c,victim,c.pos,true);
            if(c.age==1||c.spell==Spell.DOOM_BEAM&&c.age%6==1)SpellVisuals.send(c.level,c.spell,SpellVisuals.Stage.IMPACT,c.anchor,c.anchor,c.age==1?5:2.5);
        }
        return c.age<c.duration;
    }
    private static double beamRange(Cast c){return c.spell.range;}
    static void cleanup(Cast c){
        if(c.spell.kind==Spell.Kind.DEVASTATOR)BeamRitual.sync(c.caster,c.spell,0,0,0);
        if(c.spell.kind==Spell.Kind.ASCENSION){c.caster.removeEffect(ArcanaEffects.SERAPH_FLIGHT);effect(c.caster,MobEffects.SLOW_FALLING,80,0);}
    }
}
