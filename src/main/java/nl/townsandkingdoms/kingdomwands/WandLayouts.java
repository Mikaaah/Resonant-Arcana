package nl.townsandkingdoms.kingdomwands;

import java.util.List;
import java.util.Map;

/** Numeric item selections used by versions 0.1–0.3. Keep this snapshot when editing lineups. */
final class WandLayouts {
    static final Map<String,List<String>> LEGACY=Map.ofEntries(
        Map.entry("empire_wand", List.of("SPARK","EMPIRE_SPARK","BLOOD_SPARK","POISON_SPARK","BLOOD_WAVE","POISON_WAVE","FLAME_WAVE","CONFUSE","STUN","CAPTURE","EMPIRE_COMET","FIRE_COMET","EMPIRE_PULSE","FIRE_PULSE","FIREBALL","IGNITE","LAUNCH","LEAP","LIGHTNING","SMITE")),
        Map.entry("blood_wand", List.of("BLOOD_WAVE","BLOOD_SPARK","BLOOD_EXPLODE","BLOOD_STUN","BLOOD_BLOCK")),
        Map.entry("celcrius_wand", List.of("FIRE_COMET","FIRE_TWISTER","FLAME_WAVE","FLAME_SHOCK_WAVE","FLAME_THROWER","FIREBALL")),
        Map.entry("dark_wand", List.of("SHADOW_BOLT","SOUL_SEEKER","DARK_WAVE","NIGHTFALL","SHADOW_CAGE","SOUL_AURA")),
        Map.entry("thunder_wand", List.of("THUNDER_BOLT","CHAIN_LIGHTNING","THUNDER_WAVE","STORM_CALL","THUNDER_PULSE","STATIC_SHOCK")),
        Map.entry("mephi_wand", List.of("GRAVITY_BOLT","GRAVITY_WELL","FORCE_PUSH","FORCE_PULL","GRAVITY_CHOKE","SINGULARITY")),
        Map.entry("soul_wand", List.of("ECHO_BOLT","SCULK_WAVE","SONIC_BOOM","SOUL_SIPHON","SOUL_PRISON","SHRIEKING_PULSE","AMETHYST_NOVA","ECHO_WARD")));
    private WandLayouts() {}
}
