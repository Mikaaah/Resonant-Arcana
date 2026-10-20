package nl.townsandkingdoms.resonantarcana;

import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Original, midrange-audible ritual beds; no borrowed voice recordings. */
final class RitualAudio {
    static final List<String> IDS=List.of("ritual_arcane","ritual_fire","ritual_ice","ritual_nature","ritual_abyss","ritual_shadow","doom_charge_bed","doom_gravity_bed","doom_collapse","orbital_detonation");
    static final DeferredRegister<SoundEvent> SOUNDS=DeferredRegister.create(Registries.SOUND_EVENT,ResonantArcana.ID);
    static {for(String id:IDS)SOUNDS.register(id,()->SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,id)));}
    static String charge(Spell s){if(s==Spell.ABYSSAL_WORLD_TIDE||s==Spell.KRAKEN_WORLD_DROWN)return ResonantArcana.ID+":ritual_abyss";return ResonantArcana.ID+":"+switch(s.theme){case FIRE->"ritual_fire";case ICE->"ritual_ice";case POISON->"ritual_nature";case SOUL,GRAVITY,DARK,BLOOD->"ritual_shadow";default->s==Spell.ABYSSAL_WORLD_TIDE||s==Spell.KRAKEN_WORLD_DROWN?"ritual_abyss":"ritual_arcane";};}
    static void blast(SpellEngine.Cast c){
        String id=c.spell==Spell.DOOM?"doom_collapse":"orbital_detonation";var sound=SpellAudio.event(ResonantArcana.ID+":"+id);
        float gain=(float)(2.4*ArcanaConfig.SOUND_VOLUME.get()*ArcanaConfig.IMPACT_VOLUME.get());boolean direct=ArcanaConfig.CASTER_IMPACTS.get();
        if(direct)c.caster.playNotifySound(sound,SoundSource.PLAYERS,gain,1);
        c.level.playSound(direct?c.caster:null,c.pos.x,c.pos.y,c.pos.z,sound,SoundSource.PLAYERS,gain,1);
    }
    private RitualAudio(){}
}
