# Spell reference

**147 core spells + 12 optional Bosses’Rise spells · 10 wands · 13–17 spells per wand.**

Values below are defaults before armour, resistance, school power, equipment and configuration. Damage and healing use **health points**: 2 points equal one heart. A listed damage value is per successful hit or pulse, rather than guaranteed total damage. Normal Minecraft damage immunity and status-effect immunities still apply.

Ordinary spells have five levels. **DOOM** and the ten **Cataclysm** spells have one Legendary rank. Base power is 100%, 125%, 150%, 175%, and 200%. Mana increases by the spell’s listed step for each level above I. Higher levels do not change the base cast time or cooldown. Iron’s cast-speed, cooldown and school modifiers still apply.

Craft a wand to access its level-I kit. To upgrade an individual spell, hold a matching higher-level Iron’s scroll in your offhand and sneak-use the wand. The scroll is consumed. Native Iron’s spellbooks also accept all spells through normal inscription.

Healing affects the caster, owned pets and scoreboard teammates. By default it also affects players who, like the caster, have no scoreboard team; servers can disable this. Healing requires line of sight and cannot exceed maximum health. Offensive spells respect server PvP and scoreboard friendly-fire settings, and protect the caster’s own pets. Spells do not intentionally load new chunks or damage terrain. Last Stand temporarily places and later removes its own banner; lantern and totem centrepieces use temporary display entities without replacing world blocks.

## Optional Bosses’Rise spells

Bosses’Rise **2.1.2** adds twelve spells from five bosses. Kraken: Grasp, Wrath and Undertow. Yeti: Ice Spike Eruption and Glacial Wall. Underworld Knight (Helvar): Rift, Fracture and Swordfall. Sandworm: Eruption and Quicksand. Infernal Dragon: Infernal Barrage and Dragon’s Cataclysm. Without that mod, they are absent from registries, scrolls, commands and wand selections. Existing spell IDs remain unchanged. Other Bosses’Rise versions leave this adapter disabled until validated.

Visual-only entities reference the installed mod’s models, animations, textures and items: red Kraken tentacles, Yeti ice spikes, Helvar’s rift projectile and Knight Sword, Sandworm sand columns and the Infernal Dragon’s blazing projectiles. They do not run original boss attack ticks, persist in saves, break terrain, drop loot or qualify as spell targets. Damage and status effects use Resonant Arcana’s targeting, line-of-sight, PvP and friendly-fire rules. Sounds also come from the installed mod; no third-party assets are bundled.

Kraken tentacle roots are buried 2.25 blocks below the surface, and Wrath’s outer tentacles face inward. Kraken’s Grasp has a 1.2-second cast bar, followed by approximately 2.3 seconds of rise/attack animation before one slam; its visual fades afterward. Ice Spike Eruption has a 0.8-second cast bar, then five eruptions over 0.75–1.35 seconds; each enemy can take one hit per cast. Helvar’s Rift has a 0.9-second cast bar, then travels at 1.2 blocks per tick up to 28 blocks, stopping at walls or its first enemy. Ground spells need a visible solid surface within a bounded ground search; they cannot place attacks through ceilings or walls.

## Visual presentation

Native vanilla and Iron’s particles layer distinct projectile wakes, beams, rain, restraints, vortex interiors and impacts. Persistent areas use ground-following boundary rings; decorative particles have a separate allowance. Last Stand shows its full six-block defense radius. Wisp Lantern, Dread Effigy, Spore Totem and Consecration place temporary physical centrepieces on visible ground. Spell effects and centrepieces expire with the cast.

The readability and bounded-detail approach is inspired by [Wynncraft’s official Spellbound combat notes](https://wynncraft.com/updates/2.0). Geometry and implementation are original; no Wynncraft assets are included.

## Wand index

| Wand | School | Spell kit |
| --- | --- | --- |
| [Arcane Wand](#arcane-wand) | Evocation | Spark, Arcane Strike, Mind Haze, Stun, Arcane Bind, Astral Comet, Arcane Repulse, Launch, Leap, Arcane Volley, Evoker's Edict, Arcane Step, Arcane Edge, Phantom Formation, Spellbreaker, Astral Apocalypse |
| [Blood Wand](#blood-wand) | Blood | Blood Wave, Crimson Strike, Crimson Rupture, Hemorrhagic Shock, Blood Ward, Sanguine Drain, Blood Lance, Blood Needles, Blood Reap, Crimson Tether, Sanguine Pact, Crimson Harvest, Blood Rend, Crimson Reversal, Crimson Eclipse |
| [Cinder Wand](#cinder-wand) | Fire | Fire Comet, Cinder Vortex, Flame Wave, Cinder Surge, Flamethrower, Fireball, Ignite, Ember Veil, Solar Cataclysm, Cinder Step, Phoenix Rush, Dragon Breath, Cinder Cleave, Ashen Rebirth, Infernal Barrage, Dragon’s Cataclysm, World Pyre |
| [Dark Wand](#dark-wand) | Ender | Shadow Bolt, Umbral Seeker, Umbral Wave, Nightfall, Dread Mark, Umbral Veil, DOOM, Umbral Step, Dusk Cleave, Dread Effigy, Veil of Night, Night Bloom, Helvar’s Rift, Helvar’s Fracture, Helvar’s Swordfall, Void Coronation |
| [Thunder Wand](#thunder-wand) | Lightning | Thunder Bolt, Chain Lightning, Thunder Wave, Storm Call, Thunder Pulse, Static Shock, Lightning, Smite, Tempest Spear, Storm Step, Thunderclap, Arc Ricochet, Battle Tempo, Thunderbrand, Heaven’s Thunder |
| [Rift Wand](#rift-wand) | Ender | Rift Bolt, Rift Well, Force Push, Force Pull, Suspension, Singularity, Event Horizon, Rift Blink, Orbital Blades, Prismatic Ruin, Gravity Stamp, Spatial Shear, Rift Draw, Sandworm’s Eruption, Sandworm’s Quicksand, Rift Collision |
| [Soul Wand](#soul-wand) | Eldritch | Echo Bolt, Sculk Wave, Sonic Scream, Soul Siphon, Soul Prison, Shrieking Pulse, Amethyst Nova, Echo Ward, Soul Cannon, Wisp Lantern, Resonant Guard, Memory Fracture, Soulblast, Funeral Bell, Soul Requiem |
| [Venom Wand](#venom-wand) | Nature | Venom Sting, Poison Wave, Venom Bolt, Miasma Field, Withering Hex, Thorn Lash, Viper Fangs, Bramble Ring, Toxic Molt, Spore Totem, Corrosive Rain, Gorgon Gaze, Kraken’s Grasp, Kraken’s Wrath, Kraken’s Undertow, Worldroot Catastrophe |
| [Holy Wand](#holy-wand) | Holy | Sanctuary, Luminous Dart, Mending Light, Blessing of Vigor, Consecration, Radiant Sweep, Pilgrim Step, Absolution, Dawn Ward, Judgement Ray, Seraphic Descent, Renewal Hymn, Warscream, Bulwark Chant, Last Stand, Final Judgement |
| [Frost Wand](#frost-wand) | Ice | Glacial Rupture, Frost Needle, Ice Serpent, Rime Nova, Cryo Lance, Frozen Aegis, Glacier Step, Shatter, Hailstorm, Glacial Prison, Crystal Barrage, Absolute Zero, Rime Thrust, Ice Spike Eruption, Glacial Counter, Yeti’s Glacial Wall, Eternal Winter |

## Shared recovery

Healing and cleansing spells share a recovery group. Blood Ward, Echo Ward and Dawn Ward share another; stuns, restraints and life drains each share their own group. Swapping wands or switching to a spellbook does not bypass the recovery already applied. Other cooldowns are per spell and player, rather than per item.

## Secondary effects and level scaling

- Fire hits ignite for three seconds. Nature hits poison for four seconds. Dark shadow hits apply Weakness I for three seconds; Nightfall also applies Darkness.
- Frost hits apply Slowness I for two seconds and add 40 freezing ticks, capped at 240. Freeze buildup is not a guaranteed stun and can decay or be resisted normally. Absolute Zero additionally imposes Slowness IV and at least 160 freezing ticks.
- Ward, restraint, tether, blessing, cleansing-speed and gaze durations scale with spell power, capped at twice their base duration. Resistance and Strength amplifiers remain fixed. Absorption scales with spell power, capped at 16 health, and refreshes rather than adding unlimited shields.
- Blink range gains up to four blocks from spell power, reaching the extra four at level V without gear. The destination must be clear, loaded, and outside lava. Blinks follow the caster’s line of sight; they do not search for a safe floor, so aiming into open air still requires care.
- Siphons restore 40% of health actually removed, capped at 2 health times spell power, before the healing multiplier. They provide no healing when damage is blocked. Projectile fans and volleys list damage per projectile; normal hit immunity prevents treating all overlapping projectiles as guaranteed combined damage.

## Heavy cast timing

Windup is the native Iron’s cast bar. Some spells also have a release phase: The ten Legendary cataclysms have 10–12-second base cast bars, cost 440–500 mana and have two-minute base cooldowns. Doom also has a ten-second cast and costs 500 mana; it is separate from those ten. Their release sequences continue for roughly 4.4–5.6 seconds. Solar Cataclysm warns for 0.6 seconds before its meteor falls; Event Horizon pulls for 1.5 seconds; DOOM grows and devours its core before collapsing at 4.5 seconds. Gravity Stamp and Seraphic Descent mark their impact for one second after the cast bar. Other attacks may take additional time to travel. Interrupting the cast bar cancels the release; already-released fields continue for their stated lifetime.

<a id="arcane-wand"></a>
## Arcane Wand

**Evocation school.** Saved item ID: `resonant_arcana:arcane_wand`.

### Spark

Loose a compact arcane spark along your aim. It stops at the first enemy or solid surface, releasing a small arcane impact.

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

Focus arcane energy directly on one visible aimed enemy. A woven link identifies the struck target; terrain blocks targeting.

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

Wrap one visible aimed enemy in a spiralling hex. Apply Nausea for five seconds and Blindness for two seconds, with one direct hit.

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

Bind one visible aimed enemy with arcane sparks. Apply Slowness VI and Weakness II for three seconds, with one direct hit; immune enemies may resist the effects.

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

Wind an arcane cage around one aimed enemy for three seconds at level I. Suppress movement and melee damage while the link holds. Cover, range or displacement of more than four blocks breaks it.

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

Launch a slow astral comet with a bright core and trailing star motes. It bursts around the first enemy or solid surface reached, damaging visible enemies in the impact area.

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

Release an expanding arcane pressure ring from your feet. Enemies touched by the advancing rim take one hit and are pushed away; walls block the hit.

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

Send a rising arcane strike into one visible aimed enemy, adding upward velocity. This lifts the target; it does not grant the enemy fall protection.

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

Vault forwards and upwards in an arcane wake. This is a movement spell with no direct damage; choose a safe landing.

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

Fire three arcane bolts staggered 0.3 seconds apart. Each bolt follows your aim when it leaves, stops at cover and releases its own small impact; normal damage immunity still applies.

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

### Phantom Formation

Summon four to eight spectral swords for ten seconds. Each successful player melee hit sends one sword toward the struck enemy. Swords have an arming flight, collide with walls and do not trigger further swords or Thunderbrand.

**Role:** Melee follow-up · **Cast:** 1.2s · **Cooldown:** 17s · **Range:** 20 blocks · **Radius / width:** 0.45 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 60 |
| II | 10 | — | 67 |
| III | 12 | — | 74 |
| IV | 14 | — | 81 |
| V | 16 | — | 88 |

**Base active phase:** 10s.

**Sword count I–V:** 4, 5, 6, 7, 8. Each successful native player melee hit queues one sword, which arms for 0.5 seconds before flying along the attack direction. It collides with the first enemy or wall, travels at most 20 blocks and cannot create further proc attacks. Unused swords expire after ten seconds.

Spell ID: `resonant_arcana:phantom_formation`. Inspect any rank in game with `/resonantarcana spell phantom_formation <level>`.

### Spellbreaker

Sweep a close cone, adding half your melee attack damage. Destroy up to 32 hostile or unowned projectiles and interrupt eligible Iron’s casts. Allies, owned projectiles, protected casts and unrelated magic systems are not cancelled.

**Role:** Anti-magic sweep · **Cast:** 0.4s · **Cooldown:** 8s · **Range:** 7 blocks · **Radius / width:** 1 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 35 |
| II | 7.5 | — | 40 |
| III | 9 | — | 45 |
| IV | 10.5 | — | 50 |
| V | 12 | — | 55 |

**Weapon contribution:** add 50%, 62.5%, 75%, 87.5%, or 100% of the melee attack damage attribute at release, for levels I–V respectively.

**Counterspell:** interrupts native Iron’s player and IMagicEntity long casts when their interruption rules and CounterSpellEvent allow it. Does not cancel already-released spells or arbitrary other magic systems. Up to 32 visible hostile/unowned projectiles in the forward cone are removed; owned projectiles, allies and Bosses’Rise visual adapters are protected.

Spell ID: `resonant_arcana:spellbreaker`. Inspect any rank in game with `/resonantarcana spell spellbreaker <level>`.

### Astral Apocalypse

Three triads of falling stars burst around the marked centre, then their lattice collapses for double damage over 14 blocks. Damage is per star impact; multiple stars can overlap. Cover blocks damage. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 10s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 96 | — | 440 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 4.4s.

Spell ID: `resonant_arcana:astral_apocalypse`. Inspect any rank in game with `/resonantarcana spell astral_apocalypse <level>`.

<a id="blood-wand"></a>
## Blood Wand

**Blood school.** Saved item ID: `resonant_arcana:blood_wand`.

### Blood Wave

Send a broad crimson crescent along your aim. Its moving front cuts each visible enemy once and dissipates against solid cover.

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

Snap a crimson spark onto one visible aimed enemy. Blood droplets mark the impact; this attack does not restore health.

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

Detonate a crimson bloom at the aimed enemy or reachable aim point. Its radial blood spray damages visible enemies in the impact area once.

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

Close a crimson binding around one visible aimed enemy. One hit applies Slowness VI and Weakness II for three seconds; this spell does not drain health.

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

Drive a straight crimson lance through visible enemies along your aim. Each enemy on the piercing line is hit once; solid cover ends the lance.

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

### Crimson Reversal

Blink forward with a blood slash along the clear path, adding half your melee attack damage. Recast within three seconds to return safely to your starting position for no extra mana. The normal cooldown begins when the return is used or expires.

**Role:** Dash / return · **Cast:** 0.5s · **Cooldown:** 10s · **Range:** 8 blocks · **Radius / width:** 1.4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 35 |
| II | 12.5 | — | 40 |
| III | 15 | — | 45 |
| IV | 17.5 | — | 50 |
| V | 20 | — | 55 |

**Weapon contribution:** add 50%, 62.5%, 75%, 87.5%, or 100% of the melee attack damage attribute at release, for levels I–V respectively.

**Native recast:** a three-second return window. The return costs no mana and has no second windup; using or timing out the return starts the normal cooldown. The saved anchor must remain in the same dimension, within 16 blocks, loaded, outside lava and free of collisions. The return cannot be used as a directly consumed scroll; inscribe it in a spellbook or cast with a wand.

Spell ID: `resonant_arcana:crimson_reversal`. Inspect any rank in game with `/resonantarcana spell crimson_reversal <level>`.

### Crimson Eclipse

Raise a blood-red eclipse. Three radial blood tides damage enemies before a double-damage final tide. Restore 8% of health actually removed, capped at 20 health across the entire cast. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 10s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 96 | — | 460 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 4.5s.

Spell ID: `resonant_arcana:crimson_eclipse`. Inspect any rank in game with `/resonantarcana spell crimson_eclipse <level>`.

<a id="cinder-wand"></a>
## Cinder Wand

**Fire school.** Saved item ID: `resonant_arcana:cinder_wand`.

### Fire Comet

Hurl a slow ember comet with a burning wake. Its first collision releases an area detonation and ignites enemies hit for three seconds.

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

Raise a stationary ember funnel at your aim for two seconds. Four half-second pulses can damage visible enemies inside and refresh three seconds of burning.

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

Drive a broad wall of flickering embers forwards. Its advancing front hits each visible enemy once and ignites them for three seconds.

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

Send an expanding ember ring across the ground around you. Its advancing rim damages each visible enemy once, pushes them away and ignites them for three seconds.

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

Channel a narrow stream of flame following your current aim for 1.5 seconds. Six quarter-second pulses can hit enemies intersecting the stream; solid cover stops it. Hits ignite for three seconds.

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

Throw a compact fireball with a coiling ember trail. Its first collision releases a small area burst and ignites enemies hit for three seconds.

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

Kindle one visible aimed enemy directly. Deal the listed impact damage and set it burning for three seconds; ordinary fire immunity still applies.

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

Carry a low ring of embers for three seconds. Three one-second pulses damage visible nearby enemies and ignite them for three seconds.

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

### Ashen Rebirth

Burst forward along a clear path as a phoenix, leaving a burning silhouette. After 0.8 seconds the silhouette detonates. Grants brief Slow Falling; the movement itself deals no damage.

**Role:** Escape / delayed blast · **Cast:** 0.6s · **Cooldown:** 12s · **Range:** 8 blocks · **Radius / width:** 4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 45 |
| II | 22.5 | — | 51 |
| III | 27 | — | 57 |
| IV | 31.5 | — | 63 |
| V | 36 | — | 69 |

**Base active phase:** 1.2s.

**Release:** safe forward blink up to eight blocks, with three seconds of Slow Falling. The silhouette remains at the departure point and explodes after 0.8 seconds. The movement itself deals no damage.

Spell ID: `resonant_arcana:ashen_rebirth`. Inspect any rank in game with `/resonantarcana spell ashen_rebirth <level>`.

### Infernal Barrage

Requires Bosses’Rise 2.1.2. Conjure three blazing orbs beside you, then launch them one after another toward the aimed ground. Each orb travels visibly and bursts against the first enemy or wall. No guard mobs are summoned; allies and terrain are protected.

**Role:** Infernal Dragon / flame barrage · **Cast:** 1.2s · **Cooldown:** 14s · **Range:** 16 blocks · **Radius / width:** 4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 60 |
| II | 8.75 | — | 65 |
| III | 10.5 | — | 70 |
| IV | 12.25 | — | 75 |
| V | 14 | — | 80 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Base active phase:** 4s.

Spell ID: `resonant_arcana:dragons_guard`. Inspect any rank in game with `/resonantarcana spell dragons_guard <level>`.

### Dragon’s Cataclysm

Requires Bosses’Rise 2.1.2. Mark visible ground with an ember ring and call down a blazing fireball. After one second of warning it descends, detonating once in a heavy expanding fire shockwave. Requires space overhead; allies and terrain are protected.

**Role:** Infernal Dragon / descending fire strike · **Cast:** 1.6s · **Cooldown:** 18s · **Range:** 18 blocks · **Radius / width:** 4.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 85 |
| II | 20 | — | 93 |
| III | 24 | — | 101 |
| IV | 28 | — | 109 |
| V | 32 | — | 117 |

**Base active phase:** 2.7s.

Spell ID: `resonant_arcana:dragons_pyre`. Inspect any rank in game with `/resonantarcana spell dragons_pyre <level>`.

### World Pyre

Call five descending meteors onto separate points around the mark, each bursting within five blocks. Finish with a 14-block firestorm dealing 250% damage. Fire hits ignite enemies for three seconds. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 12s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 100 | — | 500 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 5s.

Spell ID: `resonant_arcana:world_pyre`. Inspect any rank in game with `/resonantarcana spell world_pyre <level>`.

<a id="dark-wand"></a>
## Dark Wand

**Ender school.** Saved item ID: `resonant_arcana:dark_wand`.

### Shadow Bolt

Launch a twisting shadow bolt. Its first collision releases a small void impact, applying Weakness I for three seconds to enemies hit.

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

Release a dark wisp that steers toward the enemy selected at release. Its impact damages and weakens enemies; solid cover intercepts the projectile.

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

Advance a broad curtain of shadow along your aim. Each enemy touched by the moving front takes one hit and Weakness I for three seconds.

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

Collapse a cloud of shadow at your aim. Visible enemies in the burst take one hit, Weakness I for three seconds and brief Darkness.

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

Fasten an orbiting shadow curse to one aimed enemy. After two seconds it bursts around that enemy, damaging and weakening nearby enemies. Losing line of sight or exceeding the link range breaks the curse.

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

Carry a dark soul aura with you for three seconds. Three one-second pulses damage visible nearby enemies and apply Weakness I for three seconds.

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

Channel for ten seconds to form a chaotic black hole with a dark core and red accretion rings that spiral inward and vanish into it. Pull visible enemies toward the event horizon, devour enemies reaching the core, then collapse over twelve blocks. Each enemy takes at most one 256-damage hit. Doom is an additional Legendary spell, separate from the school cataclysms.

**Role:** Attack · **Cast:** 10s · **Cooldown:** 120s · **Range:** 32 blocks · **Radius / width:** 12 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 256 | — | 500 |

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

Place a blackstone effigy with a carved head and soul torch at your aim for four seconds. Four pulses damage and weaken visible enemies near it. Ender shadow hits weaken for three seconds. Pulses suppress nearby enemies with Weakness II for 1.5 seconds. Requires visible solid ground; the boundary ring shows its pulse radius.

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

### Helvar’s Fracture

Requires Bosses’Rise 2.1.2. Helvar’s rift projectile breaks against the first enemy or wall, releasing an area burst that weakens visible enemies. Terrain remains intact.

**Role:** Helvar / rift burst · **Cast:** 1.3s · **Cooldown:** 12s · **Range:** 24 blocks · **Radius / width:** 3.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 60 |
| II | 12.5 | — | 66 |
| III | 15 | — | 72 |
| IV | 17.5 | — | 78 |
| V | 20 | — | 84 |

Spell ID: `resonant_arcana:helvars_fracture`. Inspect any rank in game with `/resonantarcana spell helvars_fracture <level>`.

### Helvar’s Swordfall

Requires Bosses’Rise 2.1.2. Summon Helvar’s original three-dimensional held Knight Sword above visible ground. A runic warning tightens as the blade falls point-first, striking after 1.2 seconds and weakening nearby enemies. Allies and terrain are protected.

**Role:** Helvar / falling sword · **Cast:** 1.3s · **Cooldown:** 12s · **Range:** 18 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 60 |
| II | 20 | — | 66 |
| III | 24 | — | 72 |
| IV | 28 | — | 78 |
| V | 32 | — | 84 |

Spell ID: `resonant_arcana:helvars_swordfall`. Inspect any rank in game with `/resonantarcana spell helvars_swordfall <level>`.

### Void Coronation

Crown the mark with five towering void spires. Three expanding shadow tides deal damage while Darkness and Weakness II suppress nearby enemies, followed by a double-damage void collapse. This is separate from Doom. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 11s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 104 | — | 480 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 4.8s.

Spell ID: `resonant_arcana:void_coronation`. Inspect any rank in game with `/resonantarcana spell void_coronation <level>`.

<a id="thunder-wand"></a>
## Thunder Wand

**Lightning school.** Saved item ID: `resonant_arcana:thunder_wand`.

### Thunder Bolt

Fire a compact electric bolt with a jagged spark wake. The first enemy or solid surface stops it and releases a small electric impact.

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

Connect up to five distinct visible enemies with jagged lightning links. Each jump chooses a nearby eligible enemy; cover and the jump radius can end the chain early.

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

Send a broad, crackling electric front forwards. Each enemy crossed by its moving rim takes one hit.

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

Establish a lightning storm at your aim for 4.8 seconds. Five one-second pulses damage visible enemies; additional lightning columns show the active storm area.

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

Unleash an expanding electric ring from your feet. The moving rim damages and pushes each visible enemy once, with sparks tracing its current reach.

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

Overload one visible aimed enemy with static. One direct hit applies Slowness VI and Weakness II for three seconds; it is a slowing debuff rather than an AI freeze.

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

Call a vertical lightning strike onto your aim, damaging eligible visible enemies near the strike point. Uses addon damage rather than a terrain-igniting vanilla lightning entity.

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

Mark your aim with holy light, then deliver a vertical judgement strike. Visible enemies within its impact radius take the listed damage; allies are protected.

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

### Thunderbrand

Enchant your weapon for ten seconds. Every third successful player melee hit releases chain lightning to that enemy and up to two nearby enemies. Spell damage cannot build charges; recasting refreshes the enchantment without stacking it.

**Role:** Melee enchantment · **Cast:** 0.8s · **Cooldown:** 16s · **Range:** 16 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 55 |
| II | 10 | — | 61 |
| III | 12 | — | 67 |
| IV | 14 | — | 73 |
| V | 16 | — | 79 |

**Base active phase:** 10s.

**Trigger:** every third successful native player melee hit. The third hit gains the listed damage before its normal mitigation; lightning separately strikes up to two nearby enemies. Lightning and spell damage never add charges. Duration is 10 seconds, with no stacking on recast. Mods must route melee damage through the native player-attack damage type; custom combat damage types need an explicit integration.

Spell ID: `resonant_arcana:thunderbrand`. Inspect any rank in game with `/resonantarcana spell thunderbrand <level>`.

### Heaven’s Thunder

Six sky-to-ground lightning columns strike around the mark, each damaging enemies within six blocks. A final 14-block thunderclap deals double damage. Consecutive columns may overlap. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 10s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 100 | — | 480 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 5.6s.

Spell ID: `resonant_arcana:heavens_thunder`. Inspect any rank in game with `/resonantarcana spell heavens_thunder <level>`.

<a id="rift-wand"></a>
## Rift Wand

**Ender school.** Saved item ID: `resonant_arcana:rift_wand`.

### Rift Bolt

Send a compressed spatial orb along your aim, trailed by inward-curling portal motes. Its first collision releases a small force impact.

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

Open a stationary gravity well for 4.8 seconds. Pull visible enemies horizontally toward its centre and deal five one-second area pulses; walls block damage and pulling.

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

Strike one visible aimed enemy with an outward spatial impulse. Deal direct damage and launch it away from the caster with a small upward lift.

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

Catch one visible aimed enemy with a spatial link. Deal direct damage and pull it horizontally toward you with a small upward lift.

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

Suspend one visible aimed enemy above its starting point for two seconds. Deal one hit, slow it and briefly grant Slow Falling. Cover, range or displacement beyond four blocks breaks the hold.

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

Collapse a concentrated spatial orb at your aim. One radial force burst damages and pushes visible enemies; this instant spell leaves no lingering gravity well.

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

### Rift Draw

Draw a thin distortion along your aim. After a 0.6-second warning, a piercing slash hits visible enemies, adding half your melee attack damage. Walls stop the cut.

**Role:** Delayed spellsword cut · **Cast:** 1.5s · **Cooldown:** 9s · **Range:** 14 blocks · **Radius / width:** 0.65 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 50 |
| II | 20 | — | 56 |
| III | 24 | — | 62 |
| IV | 28 | — | 68 |
| V | 32 | — | 74 |

**Base active phase:** 0.7s.

**Weapon contribution:** add 50%, 62.5%, 75%, 87.5%, or 100% of the melee attack damage attribute at release, for levels I–V respectively.

Spell ID: `resonant_arcana:rift_draw`. Inspect any rank in game with `/resonantarcana spell rift_draw <level>`.

### Sandworm’s Eruption

Requires Bosses’Rise 2.1.2. The Sandworm’s own sand columns emerge in a row toward the aimed ground. The rising columns strike enemies once per cast; terrain stays intact.

**Role:** Sandworm / eruption · **Cast:** 1s · **Cooldown:** 11s · **Range:** 18 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 11 | — | 50 |
| II | 13.75 | — | 55 |
| III | 16.5 | — | 60 |
| IV | 19.25 | — | 65 |
| V | 22 | — | 70 |

Spell ID: `resonant_arcana:sandworms_eruption`. Inspect any rank in game with `/resonantarcana spell sandworms_eruption <level>`.

### Sandworm’s Quicksand

Requires Bosses’Rise 2.1.2. A spiralling sand vortex slows and draws visible enemies inward. Three sand columns then erupt around its rim for one final strike. Bosses and allies are never pulled; no terrain is changed.

**Role:** Sandworm / control · **Cast:** 1.2s · **Cooldown:** 15s · **Range:** 16 blocks · **Radius / width:** 4.5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 65 |
| II | 10 | — | 71 |
| III | 12 | — | 77 |
| IV | 14 | — | 83 |
| V | 16 | — | 89 |

**Base active phase:** 3s.

Spell ID: `resonant_arcana:sandworms_quicksand`. Inspect any rank in game with `/resonantarcana spell sandworms_quicksand <level>`.

### Rift Collision

Draw visible enemies inward between two spatial gates. Two perpendicular piercing cuts deal damage along their lines, followed by a triple-damage spatial implosion over 14 blocks. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 12s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 112 | — | 500 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 5.2s.

Spell ID: `resonant_arcana:rift_collision`. Inspect any rank in game with `/resonantarcana spell rift_collision <level>`.

<a id="soul-wand"></a>
## Soul Wand

**Eldritch school.** Saved item ID: `resonant_arcana:soul_wand`.

### Echo Bolt

Fire a resonant soul bolt with blue sculk motes. It ends at the first enemy or solid surface, releasing a small echo impact.

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

Roll a broad sculk resonance front forwards. Each visible enemy crossed by the advancing wave takes one hit.

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

Imprison one aimed enemy in a winding soul cage for three seconds at level I. Three one-second hits apply Darkness while the hold suppresses movement. Cover, range or displacement breaks the link.

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

Release a radial sculk shriek. An expanding soul ring damages and pushes each visible enemy once as its rim passes them.

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

Shatter a resonant amethyst bloom at your aim. Crystal motes radiate from one area burst, damaging and pushing visible enemies in range.

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

Place a soul lantern on a deepslate post for four seconds. Four echo pulses damage visible enemies near it. Each pulse reveals enemies with a glowing outline for 1.5 seconds. Requires visible solid ground; the boundary ring shows its pulse radius.

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

### Funeral Bell

Charge a funeral bell. Two expanding sonic rings deal the listed damage; a third toll deals triple damage. Each ring strikes an enemy at most once, briefly slowing and pushing it. Walls block the waves.

**Role:** Expanding pressure waves · **Cast:** 3s · **Cooldown:** 21s · **Range:** 0 blocks · **Radius / width:** 9 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 95 |
| II | 10 | — | 105 |
| III | 12 | — | 115 |
| IV | 14 | — | 125 |
| V | 16 | — | 135 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Base active phase:** 3.1s.

**Tolls:** rings start at release, 1.1 seconds and 2.2 seconds. The last ring deals triple damage: 24, 30, 36, 42, 48. Each enemy is hit at most once by each ring. The pressure waves expand from the release location; enemies can escape or take cover.

Spell ID: `resonant_arcana:funeral_bell`. Inspect any rank in game with `/resonantarcana spell funeral_bell <level>`.

### Soul Requiem

Three sonic shells strike enemies around radii four, eight and twelve blocks, revealing them and applying Darkness. Enemies can move between shells. A final 14-block requiem deals double damage. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 11s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 104 | — | 460 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 4.6s.

Spell ID: `resonant_arcana:soul_requiem`. Inspect any rank in game with `/resonantarcana spell soul_requiem <level>`.

<a id="venom-wand"></a>
## Venom Wand

**Nature school.** Saved item ID: `resonant_arcana:venom_wand`.

### Venom Sting

Burst an acid bubble on one visible aimed enemy, dealing direct damage and applying Poison I for four seconds.

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

Roll a low green acid wave forwards. Each enemy crossed by its broad front takes one direct hit and Poison I for four seconds.

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

Spit a travelling acid orb with a spore-speckled wake. Its first collision releases a small impact that damages enemies and applies Poison I for four seconds.

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

Leave a low poisonous cloud at your aim for 4.8 seconds. Five one-second pulses damage visible enemies inside and refresh Poison I for four seconds.

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

Plant a mushroom totem with a glowing shroomlight cap for four seconds. Four pulses poison and damage visible enemies near it. Nature hits poison for four seconds. Requires visible solid ground; the boundary ring shows its pulse radius.

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

Requires Bosses’Rise 2.1.2. Summon the complete Kraken cinematic model—head and all eight tentacles—at visible ground. It rises from below the surface, curls its tentacles for a grasping slam and sinks away. The slam damages and slows visible enemies for three seconds. Uses the installed original red texture; no boss AI or original boss damage runs.

**Role:** Boss magic / control · **Cast:** 1.2s · **Cooldown:** 14s · **Range:** 18 blocks · **Radius / width:** 4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 60 |
| II | 22.5 | — | 65 |
| III | 27 | — | 70 |
| IV | 31.5 | — | 75 |
| V | 36 | — | 80 |

Spell ID: `resonant_arcana:krakens_grasp`. Inspect any rank in game with `/resonantarcana spell krakens_grasp <level>`.

### Kraken’s Wrath

Requires Bosses’Rise 2.1.2. Four red Kraken tentacles strike around the aimed ground location, then a central tentacle delivers a triple-damage slam. Each enemy takes at most one outer hit and one central hit.

**Role:** Boss magic / ultimate · **Cast:** 3.6s · **Cooldown:** 30s · **Range:** 20 blocks · **Radius / width:** 4 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 110 |
| II | 15 | — | 122 |
| III | 18 | — | 134 |
| IV | 21 | — | 146 |
| V | 24 | — | 158 |

**Delivery:** up to 2 hits / pulses; the table gives the value for each one.

**Base active phase:** 7.5s.

**Requires Bosses’Rise 2.1.2.** Four outer tentacles rise ten ticks apart, then a central tentacle. Outer impacts deal the table value at most once per enemy across the entire ring. The central slam deals triple damage at 5.3 seconds after release. All damage, targeting and cleanup use the addon’s normal rules.

Spell ID: `resonant_arcana:krakens_wrath`. Inspect any rank in game with `/resonantarcana spell krakens_wrath <level>`.

### Kraken’s Undertow

Requires Bosses’Rise 2.1.2. A Kraken tentacle rises at a visible ground location. Its undertow pulls nearby enemies toward the water before one heavy slam. Uses the Kraken’s red model and texture; allies and bosses are not pulled.

**Role:** Kraken / water control · **Cast:** 1.3s · **Cooldown:** 16s · **Range:** 18 blocks · **Radius / width:** 5 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 65 |
| II | 15 | — | 71 |
| III | 18 | — | 77 |
| IV | 21 | — | 83 |
| V | 24 | — | 89 |

Spell ID: `resonant_arcana:krakens_undertow`. Inspect any rank in game with `/resonantarcana spell krakens_undertow <level>`.

### Worldroot Catastrophe

Grow a colossal branching worldroot field. Three pulses poison and damage enemies while Slowness III holds the area. The double-damage final bloom restores up to eight health to eligible allies. No terrain is replaced. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 10s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 88 | — | 440 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 4.8s.

Spell ID: `resonant_arcana:worldroot_catastrophe`. Inspect any rank in game with `/resonantarcana spell worldroot_catastrophe <level>`.

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

Place a quartz altar topped with an end rod for four seconds. Each of four pulses damages enemies and restores 1 health at level I to eligible allies in its area. Requires visible solid ground; the boundary ring shows its pulse radius.

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

Unleash three ground-shaking roars over eight blocks. Each pulse damages visible enemies; the roar draws them closer and taunts hostile mobs. You and nearby eligible allies gain Resistance I. Taunt and defense last 3/4/5/6/7 seconds at levels I–V.

**Role:** Battle cry · **Cast:** 0.6s · **Cooldown:** 20s · **Range:** 8 blocks · **Radius / width:** 8 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 45 |
| II | 3.75 | — | 50 |
| III | 4.5 | — | 55 |
| IV | 5.25 | — | 60 |
| V | 6 | — | 65 |

**Delivery:** up to 3 hits / pulses; the table gives the value for each one.

**Roar:** three damage pulses at 0.05, 0.65 and 1.25 seconds after release, in an eight-block radius. Pulls eligible visible enemies inward, respecting knockback resistance. **Defense and taunt at ranks I–V:** 3, 4, 5, 6, 7 seconds. Grants Resistance I to the caster and visible eligible allies. Taunts at most 32 hostile mobs, rechecking every half second. Custom boss AI may override the target; animals, pets and players are not taunted.

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

### Last Stand

Plant a temporary golden banner for six seconds, surrounded by a radiant ward circle. Nearby allies gain Resistance I and one grant of temporary health. The final pulse heals allies still defending the area. Re-entering does not refill the shield.

**Role:** Defensive rally · **Cast:** 1.3s · **Cooldown:** 20s · **Range:** 10 blocks · **Radius / width:** 6 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 6 | 70 |
| II | — | 7.5 | 78 |
| III | — | 9 | 86 |
| IV | — | 10.5 | 94 |
| V | — | 12 | 102 |

**Base active phase:** 6s.

**Banner:** six seconds, fixed location, six-block ally radius. Grants Resistance I while defending the area and 4, 5, 6, 7, 8 temporary health once per ally. Re-entering does not refill absorption. Only allies still in range and visible at the end receive the table’s healing.

Spell ID: `resonant_arcana:last_stand`. Inspect any rank in game with `/resonantarcana spell last_stand <level>`.

### Final Judgement

Raise a radiant crown of judgement. Three pulses damage visible enemies and heal eligible allies for four health each. The double-damage final verdict heals twelve health, cleanses afflictions and grants Resistance I for five seconds. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 12s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 96 | — | 500 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 5.2s.

Spell ID: `resonant_arcana:final_judgement`. Inspect any rank in game with `/resonantarcana spell final_judgement <level>`.

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

### Glacial Counter

Guard for 0.9 seconds against one frontal, blockable hostile hit. The first 0.3 seconds block fully and return a 175% frost counter; later guards reduce damage by 70% and return a normal counter. Environmental damage and bypassing attacks are not blocked.

**Role:** Reactive guard · **Cast:** instant · **Cooldown:** 8s · **Range:** 6 blocks · **Radius / width:** 3 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 30 |
| II | 15 | — | 34 |
| III | 18 | — | 38 |
| IV | 21 | — | 42 |
| V | 24 | — | 46 |

**Base active phase:** 0.9s.

**Guard:** 0.9 seconds, consumed by one frontal blockable hostile hit. The first 0.3 seconds prevent all damage and deal a 175% counter: 21, 26.25, 31.5, 36.75, 42 damage. Later guards reduce incoming damage by 70% and deal the table value. Rear hits, environmental damage and attacks tagged to bypass shields do not trigger the counter.

Spell ID: `resonant_arcana:glacial_counter`. Inspect any rank in game with `/resonantarcana spell glacial_counter <level>`.

### Yeti’s Glacial Wall

Requires Bosses’Rise 2.1.2. Seven of the Yeti’s ice spikes rise together in a crescent across the aimed ground. The spikes linger with frost shards instead of flashing away. Each visible enemy is hit at most once, slowed and chilled; no blocks are placed.

**Role:** Yeti / ice barrier · **Cast:** 1s · **Cooldown:** 11s · **Range:** 16 blocks · **Radius / width:** 2 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 50 |
| II | 11.25 | — | 55 |
| III | 13.5 | — | 60 |
| IV | 15.75 | — | 65 |
| V | 18 | — | 70 |

**Base active phase:** 3.1s.

Spell ID: `resonant_arcana:yetis_glacial_wall`. Inspect any rank in game with `/resonantarcana spell yetis_glacial_wall <level>`.

### Eternal Winter

Establish a 14-block glacial storm, applying Slowness IV and at least 180 freezing ticks to visible enemies. Three damage pulses precede a triple-damage shattering implosion. Freeze immunity still applies. Legendary, single rank. Long windup can be interrupted; allies and terrain are protected.

**Role:** Cataclysm · **Cast:** 11s · **Cooldown:** 120s · **Range:** 40 blocks · **Radius / width:** 14 blocks.

| Level | Direct damage | Healing | Mana |
| --- | ---: | ---: | ---: |
| I | 88 | — | 480 |

**Cataclysm damage:** the table gives one normal pulse, impact or cut. The description specifies the final-hit multiplier and delivery sequence. Overlapping attacks, enemy movement, cover and normal damage immunity affect the total.

**Base active phase:** 5s.

Spell ID: `resonant_arcana:eternal_winter`. Inspect any rank in game with `/resonantarcana spell eternal_winter <level>`.
