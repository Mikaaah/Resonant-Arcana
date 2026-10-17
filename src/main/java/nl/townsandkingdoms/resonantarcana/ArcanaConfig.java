package nl.townsandkingdoms.resonantarcana;

import net.neoforged.neoforge.common.ModConfigSpec;

/** Server rules synchronize on join; client presentation remains local. */
final class ArcanaConfig {
    static final ModConfigSpec SERVER,CLIENT;
    static final ModConfigSpec.BooleanValue WAND_CASTING,PVP,CASTER_IMPACTS,TEAMLESS_HEALING;
    static final ModConfigSpec.BooleanValue MOUSE_CONTROLS,WAND_HUD,AAA_RITUALS;
    static final ModConfigSpec.DoubleValue DAMAGE,HEALING,COOLDOWNS,SOUND_VOLUME,IMPACT_VOLUME;
    static final ModConfigSpec.IntValue PARTICLES,MAX_ACTIVE,PER_PLAYER_LIMIT,VISUAL_BUDGET,DETAIL_BUDGET,ACCENT_BUDGET,NATIVE_BUDGET,CLIENT_NATIVE_BUDGET;
    static {
        var s=new ModConfigSpec.Builder();
        s.comment("Authoritative casting rules. These values synchronize to joining clients.").push("casting");
        WAND_CASTING=s.comment("Allow direct wand casting. Disable to require Iron's spellbooks.").define("enableWands",true);
        MAX_ACTIVE=s.comment("Maximum timed spell simulations across the server.").defineInRange("maxActiveSpells",128,8,512);
        PER_PLAYER_LIMIT=s.comment("Maximum simultaneous timed spells owned by one player.").defineInRange("maxActivePerPlayer",8,1,16);s.pop();
        s.push("combat");
        PVP=s.comment("Allow offensive spells against players. Server PvP, teams and owned-pet protections still apply.").define("allowPvp",true);
        TEAMLESS_HEALING=s.comment("Allow healing between players without scoreboard teams. Disable on competitive PvP servers.").define("healTeamlessPlayers",true);s.pop();
        s.comment("Multipliers apply after spell level and equipment. Individual spell costs, levels and cooldowns also use Iron's native spell configuration.").push("balance");
        DAMAGE=s.comment("Direct damage multiplier. Does not change vanilla burning, poison or wither damage.").defineInRange("damageMultiplier",1.0,0.0,10.0);
        HEALING=s.comment("Direct healing multiplier. Healing never exceeds maximum health.").defineInRange("healingMultiplier",1.0,0.0,10.0);
        COOLDOWNS=s.comment("Cooldown multiplier before Iron's equipment modifiers.").defineInRange("cooldownMultiplier",1.0,.1,10.0);s.pop();
        s.push("audio");
        SOUND_VOLUME=s.comment("Server spell audio gain. The player's Players sound slider still applies.").defineInRange("volume",1.0,0.0,2.0);
        IMPACT_VOLUME=s.comment("Additional impact gain.").defineInRange("impactVolume",1.0,0.0,3.0);
        CASTER_IMPACTS=s.comment("Send personal ranged impact feedback without distance attenuation.").define("personalImpactFeedback",true);s.pop();
        s.comment("Budgets limit presentation, never damage, target selection or collision.").push("visuals");
        PARTICLES=s.comment("Vanilla trail density, from one to three particles per point.").defineInRange("trailDensity",2,1,3);
        VISUAL_BUDGET=s.comment("Maximum shared vanilla trail points per server tick.").defineInRange("trailPointsPerTick",256,64,2048);
        DETAIL_BUDGET=s.comment("Maximum detail effect messages per server tick.").defineInRange("detailMessagesPerTick",64,16,256);
        ACCENT_BUDGET=s.comment("Reserved impact and cast effect messages per server tick.").defineInRange("accentMessagesPerTick",32,8,128);
        NATIVE_BUDGET=s.comment("Maximum extra Iron's particles per server tick.").defineInRange("ironsParticlesPerTick",128,32,512);s.pop();SERVER=s.build();
        var c=new ModConfigSpec.Builder();c.push("controls");
        MOUSE_CONTROLS=c.comment("Enable classic left-click casting. Disable if a combat mod owns attack inputs; the cast key still works.").define("leftClickCasting",true);c.pop();
        c.push("display");WAND_HUD=c.comment("Show the selected wand spell above the hotbar.").define("showWandHud",true);c.pop();
        c.push("particles");
        AAA_RITUALS=c.comment("Optional AAAParticles: caster seals, five Explosion seals and Doom only. Native fallback remains available.").define("aaaRituals",true);
        CLIENT_NATIVE_BUDGET=c.comment("Native particle detail per client tick; range outlines have an additional reserved allowance.").defineInRange("nativeParticlesPerTick",1800,300,6000);
        c.pop();CLIENT=c.build();
    }
    private ArcanaConfig(){}
}
