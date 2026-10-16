# Spell reference

**128 core spells + 3 optional Bosses’Rise spells · 10 wands · 12–14 spells per wand.**

Values below are defaults before armour, resistance, school power, equipment and configuration. Damage and healing use **health points**: 2 points equal one heart. A listed damage value is per successful hit or pulse, rather than guaranteed total damage. Normal Minecraft damage immunity and status-effect immunities still apply.

All spells have five levels except **DOOM**, which has one Legendary level. Base power is 100%, 125%, 150%, 175%, and 200%. Mana increases by the spell’s listed step for each level above I. Higher levels do not change the base cast time or cooldown. Iron’s cast-speed, cooldown and school modifiers still apply.

Craft a wand to access its level-I kit. To upgrade an individual spell, hold a matching higher-level Iron’s scroll in your offhand and sneak-use the wand. The scroll is consumed. Native Iron’s spellbooks also accept all spells through normal inscription.

Healing affects the caster, owned pets and scoreboard teammates. By default it also affects players who, like the caster, have no scoreboard team; servers can disable this. Healing requires line of sight and cannot exceed maximum health. Offensive spells respect server PvP and scoreboard friendly-fire settings, and protect the caster’s own pets. No spell edits terrain or intentionally loads new chunks.

## Optional Bosses’Rise spells

Bosses’Rise **2.1.2** adds Kraken’s Grasp (Venom / Nature), Ice Spike Eruption (Frost / Ice), and Helvar’s Rift (Dark / Ender). Without that mod, the three spells are absent from registries, scrolls, commands and wand selections. Existing spell IDs remain unchanged. Other Bosses’Rise versions leave this adapter disabled until validated.

Visual-only entities reference the installed mod’s models, animations and textures. They do not run original boss attack ticks, persist in saves, break terrain, drop loot or qualify as spell targets. Damage and status effects use Resonant Arcana’s targeting, line-of-sight, PvP and friendly-fire rules. Sounds also come from the installed mod; no third-party assets are bundled.

Kraken’s Grasp has a 1.2-second cast bar, followed by approximately 2.3 seconds of rise/attack animation before one slam; its visual fades afterward. Ice Spike Eruption has a 0.8-second cast bar, then five eruptions over 0.75–1.35 seconds; each enemy can take one hit per cast. Helvar’s Rift has a 0.9-second cast bar, then travels at 1.2 blocks per tick up to 28 blocks, stopping at walls or its first enemy. Ground spells need a visible solid surface within a bounded ground search; they cannot place attacks through ceilings or walls.

## Wand index

| Wand | School | Spell kit |
| --- | --- | --- |
| [Arcane Wand](#arcane-wand) | Evocation | Spark, Arcane Strike, Mind Haze, Stun, Arcane Bind, Astral Comet, Arcane Repulse, Launch, Leap, Arcane Volley, Evoker's Edict, Arcane Step, Arcane Edge |
| [Blood Wand](#blood-wand) | Blood | Blood Wave, Crimson Strike, Crimson Rupture, Hemorrhagic Shock, Blood Ward, Sanguine Drain, Blood Lance, Blood Needles, Blood Reap, Crimson Tether, Sanguine Pact, Crimson Harvest, Blood Rend |
| [Cinder Wand](#cinder-wand) | Fire | Fire Comet, Cinder Vortex, Flame Wave, Cinder Surge, Flamethrower, Fireball, Ignite, Ember Veil, Solar Cataclysm, Cinder Step, Phoenix Rush, Dragon Breath, Cinder Cleave |
| [Dark Wand](#dark-wand) | Ender | Shadow Bolt, Umbral Seeker, Umbral Wave, Nightfall, Dread Mark, Umbral Veil, DOOM, Umbral Step, Dusk Cleave, Dread Effigy, Veil of Night, Night Bloom, Helvar’s Rift |
| [Thunder Wand](#thunder-wand) | Lightning | Thunder Bolt, Chain Lightning, Thunder Wave, Storm Call, Thunder Pulse, Static Shock, Lightning, Smite, Tempest Spear, Storm Step, Thunderclap, Arc Ricochet, Battle Tempo |
| [Rift Wand](#rift-wand) | Ender | Rift Bolt, Rift Well, Force Push, Force Pull, Suspension, Singularity, Event Horizon, Rift Blink, Orbital Blades, Prismatic Ruin, Gravity Stamp, Spatial Shear |
| [Soul Wand](#soul-wand) | Eldritch | Echo Bolt, Sculk Wave, Sonic Scream, Soul Siphon, Soul Prison, Shrieking Pulse, Amethyst Nova, Echo Ward, Soul Cannon, Wisp Lantern, Resonant Guard, Memory Fracture, Soulblast |
| [Venom Wand](#venom-wand) | Nature | Venom Sting, Poison Wave, Venom Bolt, Miasma Field, Withering Hex, Thorn Lash, Viper Fangs, Bramble Ring, Toxic Molt, Spore Totem, Corrosive Rain, Gorgon Gaze, Kraken’s Grasp |
| [Holy Wand](#holy-wand) | Holy | Sanctuary, Luminous Dart, Mending Light, Blessing of Vigor, Consecration, Radiant Sweep, Pilgrim Step, Absolution, Dawn Ward, Judgement Ray, Seraphic Descent, Renewal Hymn, Warscream, Bulwark Chant |
| [Frost Wand](#frost-wand) | Ice | Glacial Rupture, Frost Needle, Ice Serpent, Rime Nova, Cryo Lance, Frozen Aegis, Glacier Step, Shatter, Hailstorm, Glacial Prison, Crystal Barrage, Absolute Zero, Rime Thrust, Ice Spike Eruption |

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

<a id="arcane-wand"></a>
## Arcane Wand

**Evocation school.** Saved item ID: `resonant_arcana:arcane_wand`.

### Spark

A travelling projectile that stops at walls.

**Role:** Attack · **Cast:** instant · **Cooldown:** 0.6s · **Range:** 32 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 15 |
| II | 3.75 | — | 18 |
| III | 4.5 | — | 21 |
| IV | 5.25 | — | 24 |
| V | 6 | — | 27 |

Spell ID: `resonant_arcana:spark`. Inspect any rank in game with `/resonantarcana spell spark <level>`.

### Arcane Strike

Strike the living target you aim at.

**Role:** Attack · **Cast:** 0.25s · **Cooldown:** 1.75s · **Range:** 24 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 21 |
| II | 6.25 | — | 25 |
| III | 7.5 | — | 29 |
| IV | 8.75 | — | 33 |
| V | 10 | — | 37 |

Spell ID: `resonant_arcana:arcane_strike`. Inspect any rank in game with `/resonantarcana spell arcane_strike <level>`.

### Mind Haze

Briefly blind and disorient your target.

**Role:** Control · **Cast:** 0.4s · **Cooldown:** 5s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 18 |
| II | — | — | 21 |
| III | — | — | 24 |
| IV | — | — | 27 |
| V | — | — | 30 |

Spell ID: `resonant_arcana:confuse`. Inspect any rank in game with `/resonantarcana spell confuse <level>`.

### Stun

Slow and weaken your target.

**Role:** Control · **Cast:** 0.4s · **Cooldown:** 5s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 1 | — | 20 |
| II | 1.25 | — | 24 |
| III | 1.5 | — | 28 |
| IV | 1.75 | — | 32 |
| V | 2 | — | 36 |

Spell ID: `resonant_arcana:stun`. Inspect any rank in game with `/resonantarcana spell stun <level>`.

### Arcane Bind

Hold a target briefly; cover breaks the tether.

**Role:** Control · **Cast:** 0.6s · **Cooldown:** 8s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 24 |
| II | — | — | 28 |
| III | — | — | 32 |
| IV | — | — | 36 |
| V | — | — | 40 |

**Base active phase:** 3s. Duration at levels I–V without gear: 3s, 3.75s, 4.5s, 5.25s, 6s.

Spell ID: `resonant_arcana:capture`. Inspect any rank in game with `/resonantarcana spell capture <level>`.

### Astral Comet

A slow projectile with an area impact.

**Role:** Attack · **Cast:** 1s · **Cooldown:** 6s · **Range:** 48 blocks · **Radius / width:** 4.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 45 |
| II | 17.5 | — | 54 |
| III | 21 | — | 63 |
| IV | 24.5 | — | 72 |
| V | 28 | — | 81 |

Spell ID: `resonant_arcana:astral_comet`. Inspect any rank in game with `/resonantarcana spell astral_comet <level>`.

### Arcane Repulse

An expanding shock ring around you.

**Role:** Attack · **Cast:** 0.5s · **Cooldown:** 6s · **Range:** 0 blocks · **Radius / width:** 7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 36 |
| II | 10 | — | 43 |
| III | 12 | — | 50 |
| IV | 14 | — | 57 |
| V | 16 | — | 64 |

Spell ID: `resonant_arcana:arcane_repulse`. Inspect any rank in game with `/resonantarcana spell arcane_repulse <level>`.

### Launch

Launch your target upwards.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 3.5s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 19 |
| II | 2.5 | — | 22 |
| III | 3 | — | 25 |
| IV | 3.5 | — | 28 |
| V | 4 | — | 31 |

Spell ID: `resonant_arcana:launch`. Inspect any rank in game with `/resonantarcana spell launch <level>`.

### Leap

Propel yourself forwards and upwards.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 4s · **Range:** 0 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 16 |
| II | — | — | 19 |
| III | — | — | 22 |
| IV | — | — | 25 |
| V | — | — | 28 |

Spell ID: `resonant_arcana:leap`. Inspect any rank in game with `/resonantarcana spell leap <level>`.

### Arcane Volley

Fire three staggered arcane projectiles.

**Role:** Attack · **Cast:** 0.5s · **Cooldown:** 4.5s · **Range:** 32 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 23 |
| II | 3.75 | — | 27 |
| III | 4.5 | — | 31 |
| IV | 5.25 | — | 35 |
| V | 6 | — | 39 |

**Delivery:** up to 3 projectiles; the table gives the value for each one.

**Base active phase:** 2.2s.

Spell ID: `resonant_arcana:arcane_volley`. Inspect any rank in game with `/resonantarcana spell arcane_volley <level>`.

### Evoker's Edict

Send three staggered rows of spectral fangs along your aim. Each enemy takes one full hit.

**Role:** Attack · **Cast:** 2.4s · **Cooldown:** 16s · **Range:** 24 blocks · **Radius / width:** 1.6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 26 | — | 80 |
| II | 32.5 | — | 90 |
| III | 39 | — | 100 |
| IV | 45.5 | — | 110 |
| V | 52 | — | 120 |

Spell ID: `resonant_arcana:evokers_edict`. Inspect any rank in game with `/resonantarcana spell evokers_edict <level>`.

### Arcane Step

Blink along your aim to a clear, loaded position. Range grows by 1 block per additional spell level, up to 12 blocks.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 5s · **Range:** 8 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 24 |
| II | — | — | 28 |
| III | — | — | 32 |
| IV | — | — | 36 |
| V | — | — | 40 |

Spell ID: `resonant_arcana:arcane_step`. Inspect any rank in game with `/resonantarcana spell arcane_step <level>`.

### Arcane Edge

Empower yourself with flat melee attack damage and faster attacks for 8–16 seconds. Refreshes the existing enchantment.

**Role:** Weapon enchantment · **Cast:** 0.4s · **Cooldown:** 20s · **Range:** 0 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 35 |
| II | — | — | 40 |
| III | — | — | 45 |
| IV | — | — | 50 |
| V | — | — | 55 |

**Base active phase:** 8s. Duration at levels I–V without gear: 8s, 10s, 12s, 14s, 16s.

**Attack damage:** +2, +2.5, +3, +3.5, +4. **Attack speed:** +10%, +12.5%, +15%, +17.5%, +20%.

Spell ID: `resonant_arcana:arcane_edge`. Inspect any rank in game with `/resonantarcana spell arcane_edge <level>`.

<a id="blood-wand"></a>
## Blood Wand

**Blood school.** Saved item ID: `resonant_arcana:blood_wand`.

### Blood Wave

A wide travelling arc, hitting each enemy once.

**Role:** Attack · **Cast:** 0.35s · **Cooldown:** 3s · **Range:** 28 blocks · **Radius / width:** 1.8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:blood_wave`. Inspect any rank in game with `/resonantarcana spell blood_wave <level>`.

### Crimson Strike

Strike the living target you aim at.

**Role:** Attack · **Cast:** 0.25s · **Cooldown:** 1.75s · **Range:** 24 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 21 |
| II | 6.25 | — | 25 |
| III | 7.5 | — | 29 |
| IV | 8.75 | — | 33 |
| V | 10 | — | 37 |

Spell ID: `resonant_arcana:blood_spark`. Inspect any rank in game with `/resonantarcana spell blood_spark <level>`.

### Crimson Rupture

An area burst centred on your aim.

**Role:** Attack · **Cast:** 1s · **Cooldown:** 7s · **Range:** 24 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 46 |
| II | 15 | — | 55 |
| III | 18 | — | 64 |
| IV | 21 | — | 73 |
| V | 24 | — | 82 |

Spell ID: `resonant_arcana:blood_explode`. Inspect any rank in game with `/resonantarcana spell blood_explode <level>`.

### Hemorrhagic Shock

Slow and weaken your target.

**Role:** Control · **Cast:** 0.4s · **Cooldown:** 5s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 22 |
| II | 2.5 | — | 26 |
| III | 3 | — | 30 |
| IV | 3.5 | — | 34 |
| V | 4 | — | 38 |

Spell ID: `resonant_arcana:blood_stun`. Inspect any rank in game with `/resonantarcana spell blood_stun <level>`.

### Blood Ward

Deflect incoming hostile projectiles for 4 seconds at level I. Higher spell power extends the duration, up to 8 seconds.

**Role:** Defense · **Cast:** 0.5s · **Cooldown:** 9s · **Range:** 0 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

**Base active phase:** 4s. Duration at levels I–V without gear: 4s, 5s, 6s, 7s, 8s.

Spell ID: `resonant_arcana:blood_block`. Inspect any rank in game with `/resonantarcana spell blood_block <level>`.

### Sanguine Drain

Damage a visible aimed target and restore 40% of health actually removed, capped at 2 health at level I. Healing scales with spell power.

**Role:** Attack · **Cast:** 0.7s · **Cooldown:** 5s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 28 |
| II | 6.25 | — | 33 |
| III | 7.5 | — | 38 |
| IV | 8.75 | — | 43 |
| V | 10 | — | 48 |

Spell ID: `resonant_arcana:sanguine_drain`. Inspect any rank in game with `/resonantarcana spell sanguine_drain <level>`.

### Blood Lance

An instant piercing line through visible enemies.

**Role:** Attack · **Cast:** 0.5s · **Cooldown:** 4.5s · **Range:** 24 blocks · **Radius / width:** 0.6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 35 |
| II | 11.25 | — | 42 |
| III | 13.5 | — | 49 |
| IV | 15.75 | — | 56 |
| V | 18 | — | 63 |

Spell ID: `resonant_arcana:blood_lance`. Inspect any rank in game with `/resonantarcana spell blood_lance <level>`.

### Blood Needles

Release five divergent blood needles. Damage is per projectile; projectiles stop at solid cover.

**Role:** Attack · **Cast:** 0.4s · **Cooldown:** 4s · **Range:** 28 blocks · **Radius / width:** 0.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 28 |
| II | 5 | — | 33 |
| III | 6 | — | 38 |
| IV | 7 | — | 43 |
| V | 8 | — | 48 |

**Delivery:** up to 5 projectiles; the table gives the value for each one.

Spell ID: `resonant_arcana:blood_needles`. Inspect any rank in game with `/resonantarcana spell blood_needles <level>`.

### Blood Reap

Sweep a crimson crescent across a 120-degree forward arc. Each visible enemy is hit once.

**Role:** Attack · **Cast:** 0.7s · **Cooldown:** 5.5s · **Range:** 6 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 34 |
| II | 11.25 | — | 40 |
| III | 13.5 | — | 46 |
| IV | 15.75 | — | 52 |
| V | 18 | — | 58 |

Spell ID: `resonant_arcana:blood_reap`. Inspect any rank in game with `/resonantarcana spell blood_reap <level>`.

### Crimson Tether

Tether an aimed enemy for 3 seconds, gently pulling it and dealing three damage pulses. Cover or moving out of range breaks the link.

**Role:** Control · **Cast:** 0.8s · **Cooldown:** 8s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 42 |
| II | 2.5 | — | 50 |
| III | 3 | — | 58 |
| IV | 3.5 | — | 66 |
| V | 4 | — | 74 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Base active phase:** 3s. Duration at levels I–V without gear: 3s, 3.75s, 4.5s, 5.25s, 6s.

Spell ID: `resonant_arcana:crimson_tether`. Inspect any rank in game with `/resonantarcana spell crimson_tether <level>`.

### Sanguine Pact

Spend 2 health to gain 4 absorption health and Strength I for 6 seconds at level I. Requires more than 2 current health. Absorption and duration grow with spell power.

**Role:** Defense · **Cast:** 0.7s · **Cooldown:** 12s · **Range:** 0 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 40 |
| II | — | — | 48 |
| III | — | — | 56 |
| IV | — | — | 64 |
| V | — | — | 72 |

**Base active phase:** 6s. Duration at levels I–V without gear: 6s, 7.5s, 9s, 10.5s, 12s.

**Absorption at levels I–V:** 4, 5, 6, 7, 8 health.

Spell ID: `resonant_arcana:sanguine_pact`. Inspect any rank in game with `/resonantarcana spell sanguine_pact <level>`.

### Crimson Harvest

Strike an aimed enemy. Deal 50% more direct damage below 35% maximum health; a killing hit restores up to 2 health at level I.

**Role:** Attack · **Cast:** 1.1s · **Cooldown:** 9s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 48 |
| II | 15 | — | 57 |
| III | 18 | — | 66 |
| IV | 21 | — | 75 |
| V | 24 | — | 84 |

**Conditional direct damage at levels I–V:** 18, 22.5, 27, 31.5, 36.

Spell ID: `resonant_arcana:crimson_harvest`. Inspect any rank in game with `/resonantarcana spell crimson_harvest <level>`.

### Blood Rend

Rend a 90-degree crescent. Damage includes half your melee attack attribute. Restore 40% of health actually removed, capped at 3–6 health for the entire cast.

**Role:** Spellsword sustain · **Cast:** 0.5s · **Cooldown:** 7s · **Range:** 5 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 30 |
| II | 7.5 | — | 35 |
| III | 9 | — | 40 |
| IV | 10.5 | — | 45 |
| V | 12 | — | 50 |

**Weapon contribution:** add 50%, 62.5%, 75%, 87.5%, or 100% of the caster’s melee attack attribute at release to the listed base damage, at levels I–V respectively. This uses the equipped weapon’s attributes; the spell is not an automatic melee swing. Cast from an Iron’s spellbook while holding your weapon to use its attack damage.

**Drain:** 40% of health actually removed, capped at 3, 3.75, 4.5, 5.25, 6 health per entire cast, before the healing configuration multiplier. Blocked damage gives no healing.

Spell ID: `resonant_arcana:blood_rend`. Inspect any rank in game with `/resonantarcana spell blood_rend <level>`.

<a id="cinder-wand"></a>
## Cinder Wand

**Fire school.** Saved item ID: `resonant_arcana:cinder_wand`.

### Fire Comet

A slow projectile with an area impact. Fire hits ignite for three seconds.

**Role:** Attack · **Cast:** 1s · **Cooldown:** 6s · **Range:** 44 blocks · **Radius / width:** 4.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 45 |
| II | 15 | — | 54 |
| III | 18 | — | 63 |
| IV | 21 | — | 72 |
| V | 24 | — | 81 |

Spell ID: `resonant_arcana:fire_comet`. Inspect any rank in game with `/resonantarcana spell fire_comet <level>`.

### Cinder Vortex

A stationary vortex dealing repeated area damage. Fire hits ignite for three seconds.

**Role:** Sustain · **Cast:** 1.25s · **Cooldown:** 6s · **Range:** 30 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 34 |
| II | 8.75 | — | 40 |
| III | 10.5 | — | 46 |
| IV | 12.25 | — | 52 |
| V | 14 | — | 58 |

**Delivery:** up to 4 hits / pulses; the table gives the value for each one.

**Base active phase:** 2s.

Spell ID: `resonant_arcana:fire_twister`. Inspect any rank in game with `/resonantarcana spell fire_twister <level>`.

### Flame Wave

A wide travelling arc, hitting each enemy once. Fire hits ignite for three seconds.

**Role:** Attack · **Cast:** 0.35s · **Cooldown:** 3s · **Range:** 28 blocks · **Radius / width:** 1.8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:flame_wave`. Inspect any rank in game with `/resonantarcana spell flame_wave <level>`.

### Cinder Surge

An expanding shock ring around you. Fire hits ignite for three seconds.

**Role:** Attack · **Cast:** 0.8s · **Cooldown:** 7.5s · **Range:** 0 blocks · **Radius / width:** 9 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 41 |
| II | 11.25 | — | 49 |
| III | 13.5 | — | 57 |
| IV | 15.75 | — | 65 |
| V | 18 | — | 73 |

Spell ID: `resonant_arcana:flame_shock_wave`. Inspect any rank in game with `/resonantarcana spell flame_shock_wave <level>`.

### Flamethrower

A short stream following your current aim. Fire hits ignite for three seconds.

**Role:** Sustain · **Cast:** 0.4s · **Cooldown:** 5s · **Range:** 12 blocks · **Radius / width:** 1.4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 22 |
| II | 2.5 | — | 26 |
| III | 3 | — | 30 |
| IV | 3.5 | — | 34 |
| V | 4 | — | 38 |

**Delivery:** up to 6 hits / pulses; the table gives the value for each one.

**Base active phase:** 1.5s.

Spell ID: `resonant_arcana:flame_thrower`. Inspect any rank in game with `/resonantarcana spell flame_thrower <level>`.

### Fireball

A travelling projectile that stops at walls. Fire hits ignite for three seconds.

**Role:** Attack · **Cast:** instant · **Cooldown:** 2.5s · **Range:** 40 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 25 |
| II | 7.5 | — | 30 |
| III | 9 | — | 35 |
| IV | 10.5 | — | 40 |
| V | 12 | — | 45 |

Spell ID: `resonant_arcana:fireball`. Inspect any rank in game with `/resonantarcana spell fireball <level>`.

### Ignite

Strike the living target you aim at. Fire hits ignite for three seconds.

**Role:** Attack · **Cast:** 0.25s · **Cooldown:** 2.5s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 17 |
| II | 2.5 | — | 20 |
| III | 3 | — | 23 |
| IV | 3.5 | — | 26 |
| V | 4 | — | 29 |

Spell ID: `resonant_arcana:ignite`. Inspect any rank in game with `/resonantarcana spell ignite <level>`.

### Ember Veil

A damaging aura that moves with you. Fire hits ignite for three seconds.

**Role:** Sustain · **Cast:** 0.5s · **Cooldown:** 7.5s · **Range:** 0 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 27 |
| II | 2.5 | — | 32 |
| III | 3 | — | 37 |
| IV | 3.5 | — | 42 |
| V | 4 | — | 47 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Base active phase:** 3s.

Spell ID: `resonant_arcana:ember_veil`. Inspect any rank in game with `/resonantarcana spell ember_veil <level>`.

### Solar Cataclysm

Charge for three seconds, mark your aim, then drop a blazing meteor. Ceilings limit its drop. Fire hits ignite for three seconds.

**Role:** Attack · **Cast:** 3s · **Cooldown:** 20s · **Range:** 40 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 32 | — | 90 |
| II | 40 | — | 100 |
| III | 48 | — | 110 |
| IV | 56 | — | 120 |
| V | 64 | — | 130 |

Spell ID: `resonant_arcana:solar_cataclysm`. Inspect any rank in game with `/resonantarcana spell solar_cataclysm <level>`.

### Cinder Step

Blink through a safe line of sight in a burst of embers. Range grows by 1 block per additional spell level.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 6s · **Range:** 8 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 28 |
| II | — | — | 33 |
| III | — | — | 38 |
| IV | — | — | 43 |
| V | — | — | 48 |

Spell ID: `resonant_arcana:cinder_step`. Inspect any rank in game with `/resonantarcana spell cinder_step <level>`.

### Phoenix Rush

Launch forwards in a rising rush and hit nearby enemies in a four-block launch burst. Slow Falling protects the short descent. Fire hits ignite for three seconds.

**Role:** Mobility · **Cast:** 0.8s · **Cooldown:** 9s · **Range:** 8 blocks · **Radius / width:** 4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 45 |
| II | 15 | — | 54 |
| III | 18 | — | 63 |
| IV | 21 | — | 72 |
| V | 24 | — | 81 |

Spell ID: `resonant_arcana:phoenix_rush`. Inspect any rank in game with `/resonantarcana spell phoenix_rush <level>`.

### Dragon Breath

Exhale a broad cone of flame following your aim for 1.5 seconds. Three pulses can hit enemies that remain in the cone. Fire hits ignite for three seconds.

**Role:** Sustain · **Cast:** 1s · **Cooldown:** 8s · **Range:** 10 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 50 |
| II | 3.75 | — | 60 |
| III | 4.5 | — | 70 |
| IV | 5.25 | — | 80 |
| V | 6 | — | 90 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Base active phase:** 1.5s.

Spell ID: `resonant_arcana:dragon_breath`. Inspect any rank in game with `/resonantarcana spell dragon_breath <level>`.

### Cinder Cleave

Sweep a burning crescent through a 120-degree cone. Damage includes half your melee attack attribute at release; ignites enemies.

**Role:** Spellsword cleave · **Cast:** 0.4s · **Cooldown:** 5s · **Range:** 5 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 25 |
| II | 7.5 | — | 30 |
| III | 9 | — | 35 |
| IV | 10.5 | — | 40 |
| V | 12 | — | 45 |

**Weapon contribution:** add 50%, 62.5%, 75%, 87.5%, or 100% of the caster’s melee attack attribute at release to the listed base damage, at levels I–V respectively. This uses the equipped weapon’s attributes; the spell is not an automatic melee swing. Cast from an Iron’s spellbook while holding your weapon to use its attack damage.

Spell ID: `resonant_arcana:cinder_cleave`. Inspect any rank in game with `/resonantarcana spell cinder_cleave <level>`.

<a id="dark-wand"></a>
## Dark Wand

**Ender school.** Saved item ID: `resonant_arcana:dark_wand`.

### Shadow Bolt

A travelling projectile that stops at walls. Ender shadow hits weaken for three seconds.

**Role:** Attack · **Cast:** instant · **Cooldown:** 1.25s · **Range:** 36 blocks · **Radius / width:** 1.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 20 |
| II | 6.25 | — | 24 |
| III | 7.5 | — | 28 |
| IV | 8.75 | — | 32 |
| V | 10 | — | 36 |

Spell ID: `resonant_arcana:shadow_bolt`. Inspect any rank in game with `/resonantarcana spell shadow_bolt <level>`.

### Umbral Seeker

A projectile that follows your selected target. Ender shadow hits weaken for three seconds.

**Role:** Attack · **Cast:** 0.6s · **Cooldown:** 3.5s · **Range:** 36 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 27 |
| II | 7.5 | — | 32 |
| III | 9 | — | 37 |
| IV | 10.5 | — | 42 |
| V | 12 | — | 47 |

Spell ID: `resonant_arcana:soul_seeker`. Inspect any rank in game with `/resonantarcana spell soul_seeker <level>`.

### Umbral Wave

A wide travelling arc, hitting each enemy once. Ender shadow hits weaken for three seconds.

**Role:** Attack · **Cast:** 0.35s · **Cooldown:** 3.25s · **Range:** 28 blocks · **Radius / width:** 2.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:dark_wave`. Inspect any rank in game with `/resonantarcana spell dark_wave <level>`.

### Nightfall

An area burst centred on your aim. Ender shadow hits weaken for three seconds.

**Role:** Attack · **Cast:** 1s · **Cooldown:** 7s · **Range:** 28 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 46 |
| II | 15 | — | 55 |
| III | 18 | — | 64 |
| IV | 21 | — | 73 |
| V | 24 | — | 82 |

Spell ID: `resonant_arcana:nightfall`. Inspect any rank in game with `/resonantarcana spell nightfall <level>`.

### Dread Mark

Curse a target; detonate after two seconds. Cover breaks the curse. Ender shadow hits weaken for three seconds.

**Role:** Control · **Cast:** 0.5s · **Cooldown:** 8s · **Range:** 24 blocks · **Radius / width:** 3.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 52 |
| II | 17.5 | — | 62 |
| III | 21 | — | 72 |
| IV | 24.5 | — | 82 |
| V | 28 | — | 92 |

**Base active phase:** 2s.

Spell ID: `resonant_arcana:shadow_cage`. Inspect any rank in game with `/resonantarcana spell shadow_cage <level>`.

### Umbral Veil

A damaging aura that moves with you. Ender shadow hits weaken for three seconds.

**Role:** Sustain · **Cast:** 0.5s · **Cooldown:** 8s · **Range:** 0 blocks · **Radius / width:** 8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 30 |
| II | 3.75 | — | 36 |
| III | 4.5 | — | 42 |
| IV | 5.25 | — | 48 |
| V | 6 | — | 54 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Base active phase:** 3s.

Spell ID: `resonant_arcana:soul_aura`. Inspect any rank in game with `/resonantarcana spell soul_aura <level>`.

### DOOM

Grow a vast black hole. It drags visible enemies inward, devours its core, then collapses. One damaging hit per enemy. Ender shadow hits weaken for three seconds.

**Role:** Attack · **Cast:** 4s · **Cooldown:** 60s · **Range:** 32 blocks · **Radius / width:** 12 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 72 | — | 100 |

**Base active phase:** 4.5s.

Spell ID: `resonant_arcana:doom`. Inspect any rank in game with `/resonantarcana spell doom <level>`.

### Umbral Step

Blink to a clear position and become invisible for one second. Higher levels extend blink range.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 7s · **Range:** 10 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 30 |
| II | — | — | 36 |
| III | — | — | 42 |
| IV | — | — | 48 |
| V | — | — | 54 |

Spell ID: `resonant_arcana:umbral_step`. Inspect any rank in game with `/resonantarcana spell umbral_step <level>`.

### Dusk Cleave

Carve a shadow crescent across the space in front of you, weakening enemies hit by the arc. Ender shadow hits weaken for three seconds.

**Role:** Attack · **Cast:** 0.7s · **Cooldown:** 6s · **Range:** 6 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 36 |
| II | 12.5 | — | 43 |
| III | 15 | — | 50 |
| IV | 17.5 | — | 57 |
| V | 20 | — | 64 |

Spell ID: `resonant_arcana:dusk_cleave`. Inspect any rank in game with `/resonantarcana spell dusk_cleave <level>`.

### Dread Effigy

Place a spectral effigy at your aim for four seconds. Four pulses damage and weaken visible enemies near it. Ender shadow hits weaken for three seconds.

**Role:** Sustain · **Cast:** 1.2s · **Cooldown:** 11s · **Range:** 24 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 55 |
| II | 2.5 | — | 65 |
| III | 3 | — | 75 |
| IV | 3.5 | — | 85 |
| V | 4 | — | 95 |

**Delivery:** up to 4 hits / pulses; the table gives the value for each one.

**Base active phase:** 4s.

Spell ID: `resonant_arcana:dread_effigy`. Inspect any rank in game with `/resonantarcana spell dread_effigy <level>`.

### Veil of Night

Gain invisibility and Speed I for six seconds at level I. Spell power extends the duration, up to twelve seconds.

**Role:** Defense · **Cast:** 0.9s · **Cooldown:** 13s · **Range:** 0 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 45 |
| II | — | — | 54 |
| III | — | — | 63 |
| IV | — | — | 72 |
| V | — | — | 81 |

**Base active phase:** 6s. Duration at levels I–V without gear: 6s, 7.5s, 9s, 10.5s, 12s.

Spell ID: `resonant_arcana:veil_of_night`. Inspect any rank in game with `/resonantarcana spell veil_of_night <level>`.

### Night Bloom

Place a shadow trap. After a half-second arming period, an enemy within two blocks triggers one area explosion. Untriggered traps expire after four seconds. Ender shadow hits weaken for three seconds.

**Role:** Control · **Cast:** 1.1s · **Cooldown:** 11s · **Range:** 24 blocks · **Radius / width:** 4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 52 |
| II | 20 | — | 62 |
| III | 24 | — | 72 |
| IV | 28 | — | 82 |
| V | 32 | — | 92 |

**Base active phase:** 4s.

Spell ID: `resonant_arcana:night_bloom`. Inspect any rank in game with `/resonantarcana spell night_bloom <level>`.

### Helvar’s Rift

Requires Bosses’Rise 2.1.2. Launch a rift projectile with Helvar’s own model and sounds. It stops at walls and the first enemy hit, applies brief Weakness, and uses your spell power rather than fixed boss damage.

**Role:** Boss magic / projectile · **Cast:** 0.9s · **Cooldown:** 8s · **Range:** 28 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 40 |
| II | 17.5 | — | 45 |
| III | 21 | — | 50 |
| IV | 24.5 | — | 55 |
| V | 28 | — | 60 |

Spell ID: `resonant_arcana:helvars_rift`. Inspect any rank in game with `/resonantarcana spell helvars_rift <level>`.

<a id="thunder-wand"></a>
## Thunder Wand

**Lightning school.** Saved item ID: `resonant_arcana:thunder_wand`.

### Thunder Bolt

A travelling projectile that stops at walls.

**Role:** Attack · **Cast:** instant · **Cooldown:** 1.5s · **Range:** 40 blocks · **Radius / width:** 1.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 23 |
| II | 7.5 | — | 27 |
| III | 9 | — | 31 |
| IV | 10.5 | — | 35 |
| V | 12 | — | 39 |

Spell ID: `resonant_arcana:thunder_bolt`. Inspect any rank in game with `/resonantarcana spell thunder_bolt <level>`.

### Chain Lightning

Jump between up to five visible targets.

**Role:** Attack · **Cast:** 0.6s · **Cooldown:** 6s · **Range:** 28 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 32 |
| II | 7.5 | — | 38 |
| III | 9 | — | 44 |
| IV | 10.5 | — | 50 |
| V | 12 | — | 56 |

Spell ID: `resonant_arcana:chain_lightning`. Inspect any rank in game with `/resonantarcana spell chain_lightning <level>`.

### Thunder Wave

A wide travelling arc, hitting each enemy once.

**Role:** Attack · **Cast:** 0.35s · **Cooldown:** 3.25s · **Range:** 30 blocks · **Radius / width:** 2.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:thunder_wave`. Inspect any rank in game with `/resonantarcana spell thunder_wave <level>`.

### Storm Call

Call repeated lightning strikes over an area.

**Role:** Sustain · **Cast:** 1.6s · **Cooldown:** 12s · **Range:** 28 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 70 |
| II | 6.25 | — | 80 |
| III | 7.5 | — | 90 |
| IV | 8.75 | — | 100 |
| V | 10 | — | 110 |

**Delivery:** up to 5 hits / pulses; the table gives the value for each one.

**Base active phase:** 4.8s.

Spell ID: `resonant_arcana:storm_call`. Inspect any rank in game with `/resonantarcana spell storm_call <level>`.

### Thunder Pulse

An expanding shock ring around you.

**Role:** Attack · **Cast:** 0.7s · **Cooldown:** 7.5s · **Range:** 0 blocks · **Radius / width:** 9 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 43 |
| II | 12.5 | — | 51 |
| III | 15 | — | 59 |
| IV | 17.5 | — | 67 |
| V | 20 | — | 75 |

Spell ID: `resonant_arcana:thunder_pulse`. Inspect any rank in game with `/resonantarcana spell thunder_pulse <level>`.

### Static Shock

Slow and weaken your target.

**Role:** Control · **Cast:** 0.4s · **Cooldown:** 4s · **Range:** 28 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 22 |
| II | 3.75 | — | 26 |
| III | 4.5 | — | 30 |
| IV | 5.25 | — | 34 |
| V | 6 | — | 38 |

Spell ID: `resonant_arcana:static_shock`. Inspect any rank in game with `/resonantarcana spell static_shock <level>`.

### Lightning

Call a lightning strike at your aim.

**Role:** Attack · **Cast:** 0.4s · **Cooldown:** 5s · **Range:** 32 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 32 |
| II | 8.75 | — | 38 |
| III | 10.5 | — | 44 |
| IV | 12.25 | — | 50 |
| V | 14 | — | 56 |

Spell ID: `resonant_arcana:lightning`. Inspect any rank in game with `/resonantarcana spell lightning <level>`.

### Smite

Call a lightning strike at your aim.

**Role:** Attack · **Cast:** 1.4s · **Cooldown:** 10s · **Range:** 32 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 65 |
| II | 22.5 | — | 75 |
| III | 27 | — | 85 |
| IV | 31.5 | — | 95 |
| V | 36 | — | 105 |

Spell ID: `resonant_arcana:smite`. Inspect any rank in game with `/resonantarcana spell smite <level>`.

### Tempest Spear

Charge for 2.5 seconds, then fire a piercing storm lance. Solid cover stops the beam.

**Role:** Attack · **Cast:** 2.5s · **Cooldown:** 18s · **Range:** 56 blocks · **Radius / width:** 1.1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 30 | — | 85 |
| II | 37.5 | — | 95 |
| III | 45 | — | 105 |
| IV | 52.5 | — | 115 |
| V | 60 | — | 125 |

Spell ID: `resonant_arcana:tempest_spear`. Inspect any rank in game with `/resonantarcana spell tempest_spear <level>`.

### Storm Step

Surge forwards and upwards with lightning afterimages. Slow Falling protects the short descent.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 5s · **Range:** 9 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

Spell ID: `resonant_arcana:storm_step`. Inspect any rank in game with `/resonantarcana spell storm_step <level>`.

### Thunderclap

Release a broad thunder crescent and knock visible enemies away. Each target is hit once.

**Role:** Attack · **Cast:** 0.9s · **Cooldown:** 7s · **Range:** 8 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 11 | — | 42 |
| II | 13.75 | — | 50 |
| III | 16.5 | — | 58 |
| IV | 19.25 | — | 66 |
| V | 22 | — | 74 |

Spell ID: `resonant_arcana:thunderclap`. Inspect any rank in game with `/resonantarcana spell thunderclap <level>`.

### Arc Ricochet

Throw a travelling lightning orb that can ricochet between three visible enemies, hitting each once.

**Role:** Attack · **Cast:** 0.7s · **Cooldown:** 7s · **Range:** 32 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 40 |
| II | 7.5 | — | 48 |
| III | 9 | — | 56 |
| IV | 10.5 | — | 64 |
| V | 12 | — | 72 |

Spell ID: `resonant_arcana:arc_ricochet`. Inspect any rank in game with `/resonantarcana spell arc_ricochet <level>`.

### Battle Tempo

A rallying rhythm grants visible nearby allies faster attacks and movement for 8–16 seconds.

**Role:** Party haste · **Cast:** 0.4s · **Cooldown:** 20s · **Range:** 8 blocks · **Radius / width:** 8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 35 |
| II | — | — | 40 |
| III | — | — | 45 |
| IV | — | — | 50 |
| V | — | — | 55 |

**Base active phase:** 8s. Duration at levels I–V without gear: 8s, 10s, 12s, 14s, 16s.

**Attack and movement speed:** +10%, +12.5%, +15%, +17.5%, +20%.

Spell ID: `resonant_arcana:battle_tempo`. Inspect any rank in game with `/resonantarcana spell battle_tempo <level>`.

<a id="rift-wand"></a>
## Rift Wand

**Ender school.** Saved item ID: `resonant_arcana:rift_wand`.

### Rift Bolt

A travelling projectile that stops at walls.

**Role:** Attack · **Cast:** instant · **Cooldown:** 1.5s · **Range:** 32 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 21 |
| II | 6.25 | — | 25 |
| III | 7.5 | — | 29 |
| IV | 8.75 | — | 33 |
| V | 10 | — | 37 |

Spell ID: `resonant_arcana:gravity_bolt`. Inspect any rank in game with `/resonantarcana spell gravity_bolt <level>`.

### Rift Well

Draw nearby enemies into a gravity well.

**Role:** Sustain · **Cast:** 1.25s · **Cooldown:** 9s · **Range:** 28 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 55 |
| II | 3.75 | — | 65 |
| III | 4.5 | — | 75 |
| IV | 5.25 | — | 85 |
| V | 6 | — | 95 |

**Delivery:** up to 5 hits / pulses; the table gives the value for each one.

**Base active phase:** 4.8s.

Spell ID: `resonant_arcana:gravity_well`. Inspect any rank in game with `/resonantarcana spell gravity_well <level>`.

### Force Push

Push the target away from you.

**Role:** Control · **Cast:** instant · **Cooldown:** 3.25s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 18 |
| II | 2.5 | — | 21 |
| III | 3 | — | 24 |
| IV | 3.5 | — | 27 |
| V | 4 | — | 30 |

Spell ID: `resonant_arcana:force_push`. Inspect any rank in game with `/resonantarcana spell force_push <level>`.

### Force Pull

Pull the target towards you.

**Role:** Control · **Cast:** instant · **Cooldown:** 3.25s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 18 |
| II | 2.5 | — | 21 |
| III | 3 | — | 24 |
| IV | 3.5 | — | 27 |
| V | 4 | — | 30 |

Spell ID: `resonant_arcana:force_pull`. Inspect any rank in game with `/resonantarcana spell force_pull <level>`.

### Suspension

Lift a target briefly, followed by a gentle descent.

**Role:** Control · **Cast:** 0.6s · **Cooldown:** 7s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 26 |
| II | 2.5 | — | 31 |
| III | 3 | — | 36 |
| IV | 3.5 | — | 41 |
| V | 4 | — | 46 |

**Base active phase:** 2s.

Spell ID: `resonant_arcana:gravity_choke`. Inspect any rank in game with `/resonantarcana spell gravity_choke <level>`.

### Singularity

An area burst centred on your aim.

**Role:** Attack · **Cast:** 1.6s · **Cooldown:** 9s · **Range:** 28 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 70 |
| II | 22.5 | — | 80 |
| III | 27 | — | 90 |
| IV | 31.5 | — | 100 |
| V | 36 | — | 110 |

Spell ID: `resonant_arcana:singularity`. Inspect any rank in game with `/resonantarcana spell singularity <level>`.

### Event Horizon

Charge for 3.2 seconds, then pull visible enemies inward for 1.5 seconds before a crushing detonation.

**Role:** Attack · **Cast:** 3.2s · **Cooldown:** 24s · **Range:** 32 blocks · **Radius / width:** 7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 36 | — | 100 |
| II | 45 | — | 110 |
| III | 54 | — | 120 |
| IV | 63 | — | 130 |
| V | 72 | — | 140 |

**Base active phase:** 1.5s.

Spell ID: `resonant_arcana:event_horizon`. Inspect any rank in game with `/resonantarcana spell event_horizon <level>`.

### Rift Blink

Blink along a clear path without crossing solid cover or loading terrain. Higher levels increase its range.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 6s · **Range:** 10 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 28 |
| II | — | — | 33 |
| III | — | — | 38 |
| IV | — | — | 43 |
| V | — | — | 48 |

Spell ID: `resonant_arcana:rift_blink`. Inspect any rank in game with `/resonantarcana spell rift_blink <level>`.

### Orbital Blades

Orbit three spatial blades around yourself for three seconds. Enemies can take one hit per second, up to three hits.

**Role:** Defense · **Cast:** 1s · **Cooldown:** 10s · **Range:** 0 blocks · **Radius / width:** 3.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 48 |
| II | 5 | — | 57 |
| III | 6 | — | 66 |
| IV | 7 | — | 75 |
| V | 8 | — | 84 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Base active phase:** 3s.

Spell ID: `resonant_arcana:orbital_blades`. Inspect any rank in game with `/resonantarcana spell orbital_blades <level>`.

### Prismatic Ruin

Merge opposing energies into a slow, broad spatial orb. It passes through enemies, hitting each once, and stops at solid cover or maximum range.

**Role:** Attack · **Cast:** 3.5s · **Cooldown:** 30s · **Range:** 48 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 40 | — | 100 |
| II | 50 | — | 110 |
| III | 60 | — | 120 |
| IV | 70 | — | 130 |
| V | 80 | — | 140 |

Spell ID: `resonant_arcana:prismatic_ruin`. Inspect any rank in game with `/resonantarcana spell prismatic_ruin <level>`.

### Gravity Stamp

Mark your aim for one second, then crush visible enemies with a vertical spatial impact.

**Role:** Control · **Cast:** 1.4s · **Cooldown:** 11s · **Range:** 28 blocks · **Radius / width:** 4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 55 |
| II | 22.5 | — | 65 |
| III | 27 | — | 75 |
| IV | 31.5 | — | 85 |
| V | 36 | — | 95 |

**Base active phase:** 1s.

Spell ID: `resonant_arcana:gravity_stamp`. Inspect any rank in game with `/resonantarcana spell gravity_stamp <level>`.

### Spatial Shear

Cut a wide crescent through nearby space, striking each visible enemy in front of you once.

**Role:** Attack · **Cast:** 0.9s · **Cooldown:** 7.5s · **Range:** 8 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 42 |
| II | 15 | — | 50 |
| III | 18 | — | 58 |
| IV | 21 | — | 66 |
| V | 24 | — | 74 |

Spell ID: `resonant_arcana:spatial_shear`. Inspect any rank in game with `/resonantarcana spell spatial_shear <level>`.

<a id="soul-wand"></a>
## Soul Wand

**Eldritch school.** Saved item ID: `resonant_arcana:soul_wand`.

### Echo Bolt

A travelling projectile that stops at walls.

**Role:** Attack · **Cast:** instant · **Cooldown:** 1.25s · **Range:** 36 blocks · **Radius / width:** 1.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 20 |
| II | 6.25 | — | 24 |
| III | 7.5 | — | 28 |
| IV | 8.75 | — | 32 |
| V | 10 | — | 36 |

Spell ID: `resonant_arcana:echo_bolt`. Inspect any rank in game with `/resonantarcana spell echo_bolt <level>`.

### Sculk Wave

A wide travelling arc, hitting each enemy once.

**Role:** Attack · **Cast:** 0.35s · **Cooldown:** 3.25s · **Range:** 28 blocks · **Radius / width:** 2.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:sculk_wave`. Inspect any rank in game with `/resonantarcana spell sculk_wave <level>`.

### Sonic Scream

Charge for one second, then unleash a deafening, wide sonic scream over 12 blocks. Cover blocks the blast.

**Role:** Attack · **Cast:** 1s · **Cooldown:** 8s · **Range:** 12 blocks · **Radius / width:** 12 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 55 |
| II | 17.5 | — | 65 |
| III | 21 | — | 75 |
| IV | 24.5 | — | 85 |
| V | 28 | — | 95 |

Spell ID: `resonant_arcana:sonic_scream`. Inspect any rank in game with `/resonantarcana spell sonic_scream <level>`.

### Soul Siphon

Damage a visible aimed target and restore 40% of health actually removed, capped at 2 health at level I. Healing scales with spell power.

**Role:** Attack · **Cast:** 0.7s · **Cooldown:** 5s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 28 |
| II | 6.25 | — | 33 |
| III | 7.5 | — | 38 |
| IV | 8.75 | — | 43 |
| V | 10 | — | 48 |

Spell ID: `resonant_arcana:soul_siphon`. Inspect any rank in game with `/resonantarcana spell soul_siphon <level>`.

### Soul Prison

Restrain and damage your target briefly.

**Role:** Control · **Cast:** 0.6s · **Cooldown:** 7s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 26 |
| II | 2.5 | — | 31 |
| III | 3 | — | 36 |
| IV | 3.5 | — | 41 |
| V | 4 | — | 46 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Base active phase:** 3s. Duration at levels I–V without gear: 3s, 3.75s, 4.5s, 5.25s, 6s.

Spell ID: `resonant_arcana:soul_prison`. Inspect any rank in game with `/resonantarcana spell soul_prison <level>`.

### Shrieking Pulse

An expanding shock ring around you.

**Role:** Attack · **Cast:** 0.8s · **Cooldown:** 7.5s · **Range:** 0 blocks · **Radius / width:** 9 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 41 |
| II | 11.25 | — | 49 |
| III | 13.5 | — | 57 |
| IV | 15.75 | — | 65 |
| V | 18 | — | 73 |

Spell ID: `resonant_arcana:shrieking_pulse`. Inspect any rank in game with `/resonantarcana spell shrieking_pulse <level>`.

### Amethyst Nova

An area burst centred on your aim.

**Role:** Attack · **Cast:** 1.2s · **Cooldown:** 8s · **Range:** 28 blocks · **Radius / width:** 5.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 55 |
| II | 17.5 | — | 65 |
| III | 21 | — | 75 |
| IV | 24.5 | — | 85 |
| V | 28 | — | 95 |

Spell ID: `resonant_arcana:amethyst_nova`. Inspect any rank in game with `/resonantarcana spell amethyst_nova <level>`.

### Echo Ward

Deflect incoming hostile projectiles for 4 seconds at level I. Higher spell power extends the duration, up to 8 seconds.

**Role:** Defense · **Cast:** 0.5s · **Cooldown:** 9s · **Range:** 0 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

**Base active phase:** 4s. Duration at levels I–V without gear: 4s, 5s, 6s, 7s, 8s.

Spell ID: `resonant_arcana:echo_ward`. Inspect any rank in game with `/resonantarcana spell echo_ward <level>`.

### Soul Cannon

Charge for two seconds, then launch a massive echo blast. Switch away to cancel charging.

**Role:** Attack · **Cast:** 2s · **Cooldown:** 14s · **Range:** 48 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 90 |
| II | 20 | — | 100 |
| III | 24 | — | 110 |
| IV | 28 | — | 120 |
| V | 32 | — | 130 |

Spell ID: `resonant_arcana:soul_cannon`. Inspect any rank in game with `/resonantarcana spell soul_cannon <level>`.

### Wisp Lantern

Place a soul lantern for four seconds. Four echo pulses damage visible enemies near it.

**Role:** Sustain · **Cast:** 1.2s · **Cooldown:** 11s · **Range:** 24 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 52 |
| II | 2.5 | — | 62 |
| III | 3 | — | 72 |
| IV | 3.5 | — | 82 |
| V | 4 | — | 92 |

**Delivery:** up to 4 hits / pulses; the table gives the value for each one.

**Base active phase:** 4s.

Spell ID: `resonant_arcana:wisp_lantern`. Inspect any rank in game with `/resonantarcana spell wisp_lantern <level>`.

### Resonant Guard

Gain 6 absorption health and Resistance I for five seconds at level I. Spell power improves absorption and duration.

**Role:** Defense · **Cast:** 0.9s · **Cooldown:** 12s · **Range:** 0 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 44 |
| II | — | — | 52 |
| III | — | — | 60 |
| IV | — | — | 68 |
| V | — | — | 76 |

**Base active phase:** 5s. Duration at levels I–V without gear: 5s, 6.25s, 7.5s, 8.75s, 10s.

**Absorption at levels I–V:** 6, 7.5, 9, 10.5, 12 health.

Spell ID: `resonant_arcana:resonant_guard`. Inspect any rank in game with `/resonantarcana spell resonant_guard <level>`.

### Memory Fracture

Strike an aimed enemy. If it is affected by Darkness, direct damage increases by 50%; combine with Soul Prison.

**Role:** Attack · **Cast:** 0.9s · **Cooldown:** 8s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 42 |
| II | 12.5 | — | 50 |
| III | 15 | — | 58 |
| IV | 17.5 | — | 66 |
| V | 20 | — | 74 |

**Conditional direct damage at levels I–V:** 15, 18.75, 22.5, 26.25, 30.

Spell ID: `resonant_arcana:memory_fracture`. Inspect any rank in game with `/resonantarcana spell memory_fracture <level>`.

### Soulblast

Blast a forward cone of enemies away. Aim sharply downward to launch yourself with brief Slow Falling. While gliding, boost your Elytra along your aim instead.

**Role:** Mobility / repulsion · **Cast:** instant · **Cooldown:** 5s · **Range:** 8 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 25 |
| II | 7.5 | — | 30 |
| III | 9 | — | 35 |
| IV | 10.5 | — | 40 |
| V | 12 | — | 45 |

**Aim modes:** while gliding, boost along your look direction (initial contribution 1.7–2.1 blocks/tick plus 30% of existing motion, final speed capped at 3). Otherwise, look down at least 40.6 degrees to launch upward at 1.05–1.45 blocks/tick and gain three seconds of Slow Falling. Forward casts hit a 120-degree cone and apply horizontal knockback, respecting knockback resistance. Mobility modes deal no damage and do not force Elytra flight. Slow Falling is brief, not permanent fall immunity.

Spell ID: `resonant_arcana:soulblast`. Inspect any rank in game with `/resonantarcana spell soulblast <level>`.

<a id="venom-wand"></a>
## Venom Wand

**Nature school.** Saved item ID: `resonant_arcana:venom_wand`.

### Venom Sting

Strike the living target you aim at. Nature hits poison for four seconds.

**Role:** Attack · **Cast:** 0.25s · **Cooldown:** 2.5s · **Range:** 24 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 17 |
| II | 2.5 | — | 20 |
| III | 3 | — | 23 |
| IV | 3.5 | — | 26 |
| V | 4 | — | 29 |

Spell ID: `resonant_arcana:poison_spark`. Inspect any rank in game with `/resonantarcana spell poison_spark <level>`.

### Poison Wave

A wide travelling arc, hitting each enemy once. Nature hits poison for four seconds.

**Role:** Attack · **Cast:** 0.35s · **Cooldown:** 3.5s · **Range:** 28 blocks · **Radius / width:** 1.8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 19 |
| II | 2.5 | — | 22 |
| III | 3 | — | 25 |
| IV | 3.5 | — | 28 |
| V | 4 | — | 31 |

Spell ID: `resonant_arcana:poison_wave`. Inspect any rank in game with `/resonantarcana spell poison_wave <level>`.

### Venom Bolt

A travelling projectile that stops at walls. Nature hits poison for four seconds.

**Role:** Attack · **Cast:** instant · **Cooldown:** 1.75s · **Range:** 32 blocks · **Radius / width:** 1.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 17 |
| II | 3.75 | — | 20 |
| III | 4.5 | — | 23 |
| IV | 5.25 | — | 26 |
| V | 6 | — | 29 |

Spell ID: `resonant_arcana:venom_bolt`. Inspect any rank in game with `/resonantarcana spell venom_bolt <level>`.

### Miasma Field

Leave a poisonous cloud for nearly five seconds. Nature hits poison for four seconds.

**Role:** Sustain · **Cast:** 0.75s · **Cooldown:** 9s · **Range:** 24 blocks · **Radius / width:** 4.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 50 |
| II | 3.75 | — | 60 |
| III | 4.5 | — | 70 |
| IV | 5.25 | — | 80 |
| V | 6 | — | 90 |

**Delivery:** up to 5 hits / pulses; the table gives the value for each one.

**Base active phase:** 4.8s.

Spell ID: `resonant_arcana:miasma_field`. Inspect any rank in game with `/resonantarcana spell miasma_field <level>`.

### Withering Hex

Inflict poison and wither on an aimed target. Nature hits poison for four seconds.

**Role:** Attack · **Cast:** 0.25s · **Cooldown:** 5.5s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 25 |
| II | 3.75 | — | 30 |
| III | 4.5 | — | 35 |
| IV | 5.25 | — | 40 |
| V | 6 | — | 45 |

Spell ID: `resonant_arcana:withering_hex`. Inspect any rank in game with `/resonantarcana spell withering_hex <level>`.

### Thorn Lash

Catch an aimed enemy with a thorn tether, pull it towards you and poison it. Nature hits poison for four seconds.

**Role:** Control · **Cast:** 0.5s · **Cooldown:** 5.5s · **Range:** 20 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 30 |
| II | 5 | — | 36 |
| III | 6 | — | 42 |
| IV | 7 | — | 48 |
| V | 8 | — | 54 |

Spell ID: `resonant_arcana:thorn_lash`. Inspect any rank in game with `/resonantarcana spell thorn_lash <level>`.

### Viper Fangs

Release five venom fangs in a spreading fan. Each projectile can poison the enemy it hits. Nature hits poison for four seconds.

**Role:** Attack · **Cast:** 0.5s · **Cooldown:** 4.5s · **Range:** 24 blocks · **Radius / width:** 0.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 28 |
| II | 3.75 | — | 33 |
| III | 4.5 | — | 38 |
| IV | 5.25 | — | 43 |
| V | 6 | — | 48 |

**Delivery:** up to 5 projectiles; the table gives the value for each one.

Spell ID: `resonant_arcana:viper_fangs`. Inspect any rank in game with `/resonantarcana spell viper_fangs <level>`.

### Bramble Ring

Expand a ring of thorns around yourself. Each visible enemy is damaged once, poisoned and briefly slowed. Nature hits poison for four seconds.

**Role:** Control · **Cast:** 0.7s · **Cooldown:** 7.5s · **Range:** 0 blocks · **Radius / width:** 7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 40 |
| II | 6.25 | — | 48 |
| III | 7.5 | — | 56 |
| IV | 8.75 | — | 64 |
| V | 10 | — | 72 |

Spell ID: `resonant_arcana:bramble_ring`. Inspect any rank in game with `/resonantarcana spell bramble_ring <level>`.

### Toxic Molt

Shed poison and wither, restore 4 health at level I and gain Speed I for four seconds. Healing and duration scale with spell power.

**Role:** Healing · **Cast:** 0.8s · **Cooldown:** 11s · **Range:** 0 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 4 | 38 |
| II | — | 5 | 45 |
| III | — | 6 | 52 |
| IV | — | 7 | 59 |
| V | — | 8 | 66 |

**Base active phase:** 4s. Duration at levels I–V without gear: 4s, 5s, 6s, 7s, 8s.

Spell ID: `resonant_arcana:toxic_molt`. Inspect any rank in game with `/resonantarcana spell toxic_molt <level>`.

### Spore Totem

Plant a spectral spore totem for four seconds. Four pulses poison and damage visible enemies near it. Nature hits poison for four seconds.

**Role:** Sustain · **Cast:** 1.2s · **Cooldown:** 11s · **Range:** 24 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 50 |
| II | 2.5 | — | 60 |
| III | 3 | — | 70 |
| IV | 3.5 | — | 80 |
| V | 4 | — | 90 |

**Delivery:** up to 4 hits / pulses; the table gives the value for each one.

**Base active phase:** 4s.

Spell ID: `resonant_arcana:spore_totem`. Inspect any rank in game with `/resonantarcana spell spore_totem <level>`.

### Corrosive Rain

Call an acid cloud at your aim. Five falling pulses damage and poison visible enemies over four seconds. Nature hits poison for four seconds.

**Role:** Sustain · **Cast:** 1.4s · **Cooldown:** 12s · **Range:** 28 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 60 |
| II | 2.5 | — | 70 |
| III | 3 | — | 80 |
| IV | 3.5 | — | 90 |
| V | 4 | — | 100 |

**Delivery:** up to 5 hits / pulses; the table gives the value for each one.

**Base active phase:** 4.05s.

Spell ID: `resonant_arcana:corrosive_rain`. Inspect any rank in game with `/resonantarcana spell corrosive_rain <level>`.

### Gorgon Gaze

Fix visible enemies in a wide forward cone with a petrifying gaze, applying heavy slowness and weakness for two seconds at level I. Nature hits poison for four seconds.

**Role:** Control · **Cast:** 1.2s · **Cooldown:** 10s · **Range:** 10 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 48 |
| II | 2.5 | — | 57 |
| III | 3 | — | 66 |
| IV | 3.5 | — | 75 |
| V | 4 | — | 84 |

**Base active phase:** 2s. Duration at levels I–V without gear: 2s, 2.5s, 3s, 3.5s, 4s.

Spell ID: `resonant_arcana:gorgon_gaze`. Inspect any rank in game with `/resonantarcana spell gorgon_gaze <level>`.

### Kraken’s Grasp

Requires Bosses’Rise 2.1.2. Summon a spectral Kraken tentacle at a visible ground location. After rising, it slams nearby enemies, dealing Nature damage and slowing them for 3 seconds. Protects allies; does not grab or move bosses.

**Role:** Boss magic / control · **Cast:** 1.2s · **Cooldown:** 14s · **Range:** 18 blocks · **Radius / width:** 4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 60 |
| II | 22.5 | — | 65 |
| III | 27 | — | 70 |
| IV | 31.5 | — | 75 |
| V | 36 | — | 80 |

Spell ID: `resonant_arcana:krakens_grasp`. Inspect any rank in game with `/resonantarcana spell krakens_grasp <level>`.

<a id="holy-wand"></a>
## Holy Wand

**Holy school.** Saved item ID: `resonant_arcana:holy_wand`.

### Sanctuary

Restore up to 12 health to yourself and eligible allies within eight blocks; cleanse poison, wither, blindness and darkness. Allies include owned pets, scoreboard teammates, and teamless players when enabled.

**Role:** Healing · **Cast:** 2.5s · **Cooldown:** 18s · **Range:** 0 blocks · **Radius / width:** 8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 12 | 80 |
| II | — | 15 | 90 |
| III | — | 18 | 100 |
| IV | — | 21 | 110 |
| V | — | 24 | 120 |

Spell ID: `resonant_arcana:sanctuary`. Inspect any rank in game with `/resonantarcana spell sanctuary <level>`.

### Luminous Dart

Fire a quick dart of holy light. It stops at solid cover.

**Role:** Attack · **Cast:** instant · **Cooldown:** 1s · **Range:** 32 blocks · **Radius / width:** 0.7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 16 |
| II | 5 | — | 19 |
| III | 6 | — | 22 |
| IV | 7 | — | 25 |
| V | 8 | — | 28 |

Spell ID: `resonant_arcana:luminous_dart`. Inspect any rank in game with `/resonantarcana spell luminous_dart <level>`.

### Mending Light

Restore 6 health at level I to the most injured eligible ally within eight blocks, including yourself. Requires line of sight.

**Role:** Healing · **Cast:** 0.9s · **Cooldown:** 6s · **Range:** 0 blocks · **Radius / width:** 8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 6 | 32 |
| II | — | 7.5 | 38 |
| III | — | 9 | 44 |
| IV | — | 10.5 | 50 |
| V | — | 12 | 56 |

Spell ID: `resonant_arcana:mending_light`. Inspect any rank in game with `/resonantarcana spell mending_light <level>`.

### Blessing of Vigor

Gain 4 absorption health and Speed I for six seconds at level I. Spell power increases the shield and duration.

**Role:** Defense · **Cast:** 0.9s · **Cooldown:** 12s · **Range:** 0 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 42 |
| II | — | — | 50 |
| III | — | — | 58 |
| IV | — | — | 66 |
| V | — | — | 74 |

**Base active phase:** 6s. Duration at levels I–V without gear: 6s, 7.5s, 9s, 10.5s, 12s.

**Absorption at levels I–V:** 4, 5, 6, 7, 8 health.

Spell ID: `resonant_arcana:blessing_of_vigor`. Inspect any rank in game with `/resonantarcana spell blessing_of_vigor <level>`.

### Consecration

Place a consecrated beacon for four seconds. Each of four pulses damages enemies and restores 1 health at level I to eligible allies in its area.

**Role:** Healing · **Cast:** 1.2s · **Cooldown:** 12s · **Range:** 24 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | 1 | 55 |
| II | 2.5 | 1.25 | 65 |
| III | 3 | 1.5 | 75 |
| IV | 3.5 | 1.75 | 85 |
| V | 4 | 2 | 95 |

**Delivery:** up to 4 hits / pulses; the table gives the value for each one.

**Base active phase:** 4s.

Spell ID: `resonant_arcana:consecration`. Inspect any rank in game with `/resonantarcana spell consecration <level>`.

### Radiant Sweep

Sweep a radiant crescent through visible enemies in front of you.

**Role:** Attack · **Cast:** 0.6s · **Cooldown:** 5.5s · **Range:** 6 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 30 |
| II | 10 | — | 36 |
| III | 12 | — | 42 |
| IV | 14 | — | 48 |
| V | 16 | — | 54 |

Spell ID: `resonant_arcana:radiant_sweep`. Inspect any rank in game with `/resonantarcana spell radiant_sweep <level>`.

### Pilgrim Step

Move along your aim in a flash of light, stopping before obstacles. Higher levels extend the safe blink range.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 6s · **Range:** 8 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

Spell ID: `resonant_arcana:pilgrim_step`. Inspect any rank in game with `/resonantarcana spell pilgrim_step <level>`.

### Absolution

Cleanse poison, wither, blindness and darkness from eligible allies within eight blocks and grant brief Resistance I.

**Role:** Healing · **Cast:** 1s · **Cooldown:** 10s · **Range:** 0 blocks · **Radius / width:** 8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 40 |
| II | — | — | 48 |
| III | — | — | 56 |
| IV | — | — | 64 |
| V | — | — | 72 |

**Base active phase:** 3s. Duration at levels I–V without gear: 3s, 3.75s, 4.5s, 5.25s, 6s.

Spell ID: `resonant_arcana:absolution`. Inspect any rank in game with `/resonantarcana spell absolution <level>`.

### Dawn Ward

Deflect hostile incoming projectiles for four seconds at level I. Spell power extends the ward, up to eight seconds.

**Role:** Defense · **Cast:** 0.8s · **Cooldown:** 10s · **Range:** 0 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 42 |
| II | — | — | 50 |
| III | — | — | 58 |
| IV | — | — | 66 |
| V | — | — | 74 |

**Base active phase:** 4s. Duration at levels I–V without gear: 4s, 5s, 6s, 7s, 8s.

Spell ID: `resonant_arcana:dawn_ward`. Inspect any rank in game with `/resonantarcana spell dawn_ward <level>`.

### Judgement Ray

Charge a piercing ray of holy light, striking each visible enemy on its line once. Solid cover stops the ray.

**Role:** Attack · **Cast:** 2s · **Cooldown:** 13s · **Range:** 40 blocks · **Radius / width:** 0.7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 22 | — | 70 |
| II | 27.5 | — | 80 |
| III | 33 | — | 90 |
| IV | 38.5 | — | 100 |
| V | 44 | — | 110 |

Spell ID: `resonant_arcana:judgement_ray`. Inspect any rank in game with `/resonantarcana spell judgement_ray <level>`.

### Seraphic Descent

Mark your aim for one second, then deliver a pillar of light. It damages enemies and restores 6 health at level I to eligible allies at the impact.

**Role:** Healing · **Cast:** 2.5s · **Cooldown:** 20s · **Range:** 32 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 28 | 6 | 90 |
| II | 35 | 7.5 | 100 |
| III | 42 | 9 | 110 |
| IV | 49 | 10.5 | 120 |
| V | 56 | 12 | 130 |

**Base active phase:** 1s.

Spell ID: `resonant_arcana:seraphic_descent`. Inspect any rank in game with `/resonantarcana spell seraphic_descent <level>`.

### Renewal Hymn

Carry a healing hymn around yourself for four seconds. Four pulses each restore 2 health at level I to eligible allies with line of sight.

**Role:** Healing · **Cast:** 1.2s · **Cooldown:** 13s · **Range:** 0 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 2 | 55 |
| II | — | 2.5 | 65 |
| III | — | 3 | 75 |
| IV | — | 3.5 | 85 |
| V | — | 4 | 95 |

**Delivery:** up to 4 hits / pulses; the table gives the value for each one.

**Base active phase:** 4s.

Spell ID: `resonant_arcana:renewal_hymn`. Inspect any rank in game with `/resonantarcana spell renewal_hymn <level>`.

### Warscream

Roar with dragon fury: repel enemies, taunt nearby hostile mobs for 3 seconds, and grant visible allies bonus melee damage, spell power and temporary health.

**Role:** Battle cry · **Cast:** 0.6s · **Cooldown:** 20s · **Range:** 8 blocks · **Radius / width:** 8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 45 |
| II | — | — | 50 |
| III | — | — | 55 |
| IV | — | — | 60 |
| V | — | — | 65 |

**Base active phase:** 8s. Duration at levels I–V without gear: 8s, 10s, 12s, 14s, 16s.

**Ally damage:** +10%, +15%, +20%, +25%, +30% melee damage and native Iron’s spell power. **Temporary health:** 4, 5, 6, 7, 8. Taunts at most 32 visible hostile mobs, rechecking every half second for three seconds. Custom boss AI may override the target. Does not taunt animals, pets or players.

Spell ID: `resonant_arcana:warscream`. Inspect any rank in game with `/resonantarcana spell warscream <level>`.

### Bulwark Chant

Grant visible nearby allies Resistance I and temporary health for 8–16 seconds. Shields refresh rather than stack.

**Role:** Party defence · **Cast:** 0.8s · **Cooldown:** 20s · **Range:** 8 blocks · **Radius / width:** 8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 40 |
| II | — | — | 45 |
| III | — | — | 50 |
| IV | — | — | 55 |
| V | — | — | 60 |

**Base active phase:** 8s. Duration at levels I–V without gear: 8s, 10s, 12s, 14s, 16s.

**Defence:** Resistance I and 4, 5, 6, 7, 8 temporary health.

Spell ID: `resonant_arcana:bulwark_chant`. Inspect any rank in game with `/resonantarcana spell bulwark_chant <level>`.

<a id="frost-wand"></a>
## Frost Wand

**Ice school.** Saved item ID: `resonant_arcana:frost_wand`.

### Glacial Rupture

Send a broad glacial front forwards, damaging and chilling each visible enemy once. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Role:** Attack · **Cast:** 2.5s · **Cooldown:** 18s · **Range:** 18 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 28 | — | 85 |
| II | 35 | — | 95 |
| III | 42 | — | 105 |
| IV | 49 | — | 115 |
| V | 56 | — | 125 |

Spell ID: `resonant_arcana:glacial_rupture`. Inspect any rank in game with `/resonantarcana spell glacial_rupture <level>`.

### Frost Needle

Fire a fast ice needle that chills its target and stops at solid cover. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Role:** Attack · **Cast:** instant · **Cooldown:** 1s · **Range:** 32 blocks · **Radius / width:** 0.7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 16 |
| II | 5 | — | 19 |
| III | 6 | — | 22 |
| IV | 7 | — | 25 |
| V | 8 | — | 28 |

Spell ID: `resonant_arcana:frost_needle`. Inspect any rank in game with `/resonantarcana spell frost_needle <level>`.

### Ice Serpent

Send a low, weaving ice serpent horizontally along your aim. It chills each enemy struck and stops at solid cover; it does not climb terrain. Ice hits chill for two seconds and add 40 freezing ticks.

**Role:** Control · **Cast:** 0.7s · **Cooldown:** 5.5s · **Range:** 24 blocks · **Radius / width:** 1.4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 34 |
| II | 8.75 | — | 40 |
| III | 10.5 | — | 46 |
| IV | 12.25 | — | 52 |
| V | 14 | — | 58 |

Spell ID: `resonant_arcana:ice_serpent`. Inspect any rank in game with `/resonantarcana spell ice_serpent <level>`.

### Rime Nova

Expand a frozen ring around yourself, chilling and damaging each visible enemy once. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Role:** Control · **Cast:** 0.9s · **Cooldown:** 8s · **Range:** 0 blocks · **Radius / width:** 7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 44 |
| II | 11.25 | — | 52 |
| III | 13.5 | — | 60 |
| IV | 15.75 | — | 68 |
| V | 18 | — | 76 |

Spell ID: `resonant_arcana:rime_nova`. Inspect any rank in game with `/resonantarcana spell rime_nova <level>`.

### Cryo Lance

Fire a precise piercing ice lance through visible enemies. Solid cover blocks it. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Role:** Attack · **Cast:** 0.9s · **Cooldown:** 6s · **Range:** 28 blocks · **Radius / width:** 0.7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 38 |
| II | 12.5 | — | 45 |
| III | 15 | — | 52 |
| IV | 17.5 | — | 59 |
| V | 20 | — | 66 |

Spell ID: `resonant_arcana:cryo_lance`. Inspect any rank in game with `/resonantarcana spell cryo_lance <level>`.

### Frozen Aegis

Gain 6 absorption health and Resistance I for five seconds at level I. Spell power strengthens and extends the aegis.

**Role:** Defense · **Cast:** 0.9s · **Cooldown:** 12s · **Range:** 0 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 44 |
| II | — | — | 52 |
| III | — | — | 60 |
| IV | — | — | 68 |
| V | — | — | 76 |

**Base active phase:** 5s. Duration at levels I–V without gear: 5s, 6.25s, 7.5s, 8.75s, 10s.

**Absorption at levels I–V:** 6, 7.5, 9, 10.5, 12 health.

Spell ID: `resonant_arcana:frozen_aegis`. Inspect any rank in game with `/resonantarcana spell frozen_aegis <level>`.

### Glacier Step

Blink safely in a burst of snowflakes. Higher levels increase the range without allowing travel through walls.

**Role:** Mobility · **Cast:** instant · **Cooldown:** 6s · **Range:** 8 blocks · **Radius / width:** 0 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

Spell ID: `resonant_arcana:glacier_step`. Inspect any rank in game with `/resonantarcana spell glacier_step <level>`.

### Shatter

Strike an aimed enemy. Direct damage increases by 50% at 140 freezing ticks or Slowness III or stronger; combine with Glacial Prison or Absolute Zero. Ice hits chill for two seconds and add 40 freezing ticks.

**Role:** Attack · **Cast:** 0.9s · **Cooldown:** 8s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 42 |
| II | 12.5 | — | 50 |
| III | 15 | — | 58 |
| IV | 17.5 | — | 66 |
| V | 20 | — | 74 |

**Conditional direct damage at levels I–V:** 15, 18.75, 22.5, 26.25, 30.

Spell ID: `resonant_arcana:shatter`. Inspect any rank in game with `/resonantarcana spell shatter <level>`.

### Hailstorm

Call a hail cloud at your aim. Five pulses damage and chill visible enemies over four seconds. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Role:** Sustain · **Cast:** 1.4s · **Cooldown:** 12s · **Range:** 28 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2.5 | — | 60 |
| II | 3.125 | — | 70 |
| III | 3.75 | — | 80 |
| IV | 4.375 | — | 90 |
| V | 5 | — | 100 |

**Delivery:** up to 5 hits / pulses; the table gives the value for each one.

**Base active phase:** 4.05s.

Spell ID: `resonant_arcana:hailstorm`. Inspect any rank in game with `/resonantarcana spell hailstorm <level>`.

### Glacial Prison

Hold an aimed enemy in spectral ice for three seconds at level I. Cover, distance or large movement breaks the restraint.

**Role:** Control · **Cast:** 1s · **Cooldown:** 9s · **Range:** 24 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 45 |
| II | — | — | 54 |
| III | — | — | 63 |
| IV | — | — | 72 |
| V | — | — | 81 |

**Base active phase:** 3s. Duration at levels I–V without gear: 3s, 3.75s, 4.5s, 5.25s, 6s.

Spell ID: `resonant_arcana:glacial_prison`. Inspect any rank in game with `/resonantarcana spell glacial_prison <level>`.

### Crystal Barrage

Release five divergent crystal projectiles. Damage is per projectile; their impacts chill enemies. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Role:** Attack · **Cast:** 0.7s · **Cooldown:** 6s · **Range:** 30 blocks · **Radius / width:** 0.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 36 |
| II | 5 | — | 43 |
| III | 6 | — | 50 |
| IV | 7 | — | 57 |
| V | 8 | — | 64 |

**Delivery:** up to 5 projectiles; the table gives the value for each one.

Spell ID: `resonant_arcana:crystal_barrage`. Inspect any rank in game with `/resonantarcana spell crystal_barrage <level>`.

### Absolute Zero

Establish a frozen domain for four seconds. Four pulses chill and freeze visible enemies in its area. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Role:** Control · **Cast:** 2.5s · **Cooldown:** 20s · **Range:** 28 blocks · **Radius / width:** 7 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 85 |
| II | 3.75 | — | 95 |
| III | 4.5 | — | 105 |
| IV | 5.25 | — | 115 |
| V | 6 | — | 125 |

**Delivery:** up to 4 hits / pulses; the table gives the value for each one.

**Base active phase:** 4s.

Spell ID: `resonant_arcana:absolute_zero`. Inspect any rank in game with `/resonantarcana spell absolute_zero <level>`.

### Rime Thrust

Drive a piercing frost thrust through enemies in a narrow line, stopping at walls. Damage includes half your melee attack attribute; heavily slows for 2 seconds.

**Role:** Spellsword thrust · **Cast:** 0.4s · **Cooldown:** 6s · **Range:** 7 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 25 |
| II | 7.5 | — | 30 |
| III | 9 | — | 35 |
| IV | 10.5 | — | 40 |
| V | 12 | — | 45 |

**Weapon contribution:** add 50%, 62.5%, 75%, 87.5%, or 100% of the caster’s melee attack attribute at release to the listed base damage, at levels I–V respectively. This uses the equipped weapon’s attributes; the spell is not an automatic melee swing. Cast from an Iron’s spellbook while holding your weapon to use its attack damage.

Spell ID: `resonant_arcana:rime_thrust`. Inspect any rank in game with `/resonantarcana spell rime_thrust <level>`.

### Ice Spike Eruption

Requires Bosses’Rise 2.1.2. Five ice spikes erupt in a line across a visible ground location. Each enemy can take one hit per cast. Slows and chills enemies without freezing allies.

**Role:** Boss magic / eruption · **Cast:** 0.8s · **Cooldown:** 9s · **Range:** 16 blocks · **Radius / width:** 1.8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 40 |
| II | 12.5 | — | 45 |
| III | 15 | — | 50 |
| IV | 17.5 | — | 55 |
| V | 20 | — | 60 |

Spell ID: `resonant_arcana:ice_spike_eruption`. Inspect any rank in game with `/resonantarcana spell ice_spike_eruption <level>`.
