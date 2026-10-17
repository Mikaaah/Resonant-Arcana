# Spell Mechanics

**137 core spells + 12 optional Bosses’Rise spells · 10 wands · 12–16 spells per wand.**

Values below are defaults before armour, resistance, school power, equipment and configuration. Damage and healing use **health points**: 2 points equal one heart. A listed damage value is per successful hit or pulse, rather than guaranteed total damage. Normal Minecraft damage immunity and status-effect immunities still apply.

All spells have five levels except **DOOM**, which has one Legendary level. Base power is 100%, 125%, 150%, 175%, and 200%. Mana increases by the spell’s listed step for each level above I. Higher levels do not change the base cast time or cooldown. Iron’s cast-speed, cooldown and school modifiers still apply.

Craft a wand to access its level-I kit. To upgrade an individual spell, hold a matching higher-level Iron’s scroll in your offhand and sneak-use the wand. The scroll is consumed. Native Iron’s spellbooks also accept all spells through normal inscription.

Healing affects the caster, owned pets and scoreboard teammates. By default it also affects players who, like the caster, have no scoreboard team; servers can disable this. Healing requires line of sight and cannot exceed maximum health. Offensive spells respect server PvP and scoreboard friendly-fire settings, and protect the caster’s own pets. Spells do not intentionally load new chunks or damage terrain. Last Stand temporarily places and later removes its own banner; lantern and totem centrepieces use temporary display entities without replacing world blocks.

## Visual presentation

Native vanilla and Iron’s particles layer distinct projectile wakes, beams, rain, restraints, vortex interiors and impacts. Persistent areas use ground-following boundary rings; decorative particles have a separate allowance. Last Stand shows its full six-block defense radius. Wisp Lantern, Dread Effigy, Spore Totem and Consecration place temporary physical centrepieces on visible ground. Spell effects and centrepieces expire with the cast.

The readability and bounded-detail approach is inspired by [Wynncraft’s official Spellbound combat notes](https://wynncraft.com/updates/2.0). Geometry and implementation are original; no Wynncraft assets are included.

## Shared recovery

Healing and cleansing spells share a recovery group. Blood Ward, Echo Ward and Dawn Ward share another; stuns, restraints and life drains each share their own group. Swapping wands or switching to a spellbook does not bypass the recovery already applied. Other cooldowns are per spell and player, rather than per item.

## Secondary effects and level scaling

- Fire hits ignite for three seconds. Nature hits poison for four seconds. Dark shadow hits apply Weakness I for three seconds; Nightfall also applies Darkness.
- Frost hits apply Slowness I for two seconds and add 40 freezing ticks, capped at 240. Freeze buildup is not a guaranteed stun and can decay or be resisted normally. Absolute Zero additionally imposes Slowness IV and at least 160 freezing ticks.
- Ward, restraint, tether, blessing, cleansing-speed and gaze durations scale with spell power, capped at twice their base duration. Resistance and Strength amplifiers remain fixed. Absorption scales with spell power, capped at 16 health, and refreshes rather than adding unlimited shields.
- Blink range gains up to four blocks from spell power, reaching the extra four at level V without gear. The destination must be clear, loaded, and outside lava. Blinks follow the caster’s line of sight; they do not search for a safe floor, so aiming into open air still requires care.
- Siphons restore 40% of health actually removed, capped at 2 health times spell power, before the healing multiplier. They provide no healing when damage is blocked. Projectile fans and volleys list damage per projectile; normal hit immunity prevents treating all overlapping projectiles as guaranteed combined damage.

## Heavy cast timing

Windup is the native Iron’s cast bar. Some spells also have a release phase: Solar Cataclysm warns for 0.6 seconds before its meteor falls; Event Horizon pulls for 1.5 seconds; DOOM grows and devours its core before collapsing at 4.5 seconds. Gravity Stamp and Seraphic Descent mark their impact for one second after the cast bar. Other attacks may take additional time to travel. Interrupting the cast bar cancels the release; already-released fields continue for their stated lifetime.



[← Wiki Home](Home.md)
