# Catalogue audit — 0.2.0

All damage values are rank-one health points per direct hit, before equipment, defenses and server multipliers. Two health points equal one heart. Burning, poison, wither and melee contributions are additional. Multi-stage totals depend on coverage, damage immunity and the individual sequence; a beam hits each enemy once per cast.

The balance pass raises underpowered basic, sustained and heavy spells, with heavy releases bridging into the 44–72 damage range. Cinematic single hits remain 120–512. Several repeated cataclysm sequences have lower per-hit damage to account for their full release. Utility and support spells retain their roles.

Flamethrower now delivers six pulses over three seconds. Arcane Volley launches three bolts half a second apart. Both respect normal hurt immunity. These timings allow every advertised pulse to land against a stationary unprotected target without bypassing vanilla immunity.

| Spell | Category | School | Damage / hit | Pulses¹ | Mana | Cast (s) | Cooldown (s) |
| --- | --- | --- | ---: | ---: | ---: | ---: | ---: |
| Spark | Projectile | Evocation | 5 | 1 | 15 | 0 | 0.6 |
| Arcane Strike | Melee | Evocation | 8 | 1 | 21 | 0.25 | 1.75 |
| Crimson Strike | Melee | Blood | 7 | 1 | 21 | 0.25 | 1.75 |
| Venom Sting | Melee | Nature | 3 | 1 | 17 | 0.25 | 2.5 |
| Blood Wave | Area | Blood | 7 | 1 | 24 | 0.35 | 3 |
| Poison Wave | Area | Nature | 3 | 1 | 19 | 0.35 | 3.5 |
| Flame Wave | Area | Fire | 7 | 1 | 24 | 0.35 | 3 |
| Mind Haze | Control | Evocation | 0 | 1 | 18 | 0.4 | 5 |
| Stun | Control | Evocation | 2 | 1 | 20 | 0.4 | 5 |
| Arcane Bind | Control | Evocation | 0 | 1 | 24 | 0.6 | 8 |
| Astral Comet | Projectile | Evocation | 22 | 1 | 45 | 1 | 6 |
| Fire Comet | Projectile | Fire | 20 | 1 | 45 | 1 | 6 |
| Arcane Repulse | Area | Evocation | 12 | 1 | 36 | 0.5 | 6 |
| Fireball | Projectile | Fire | 8 | 1 | 25 | 0 | 2.5 |
| Ignite | Melee | Fire | 3 | 1 | 17 | 0.25 | 2.5 |
| Launch | Control | Evocation | 2 | 1 | 19 | 0 | 3.5 |
| Leap | Mobility | Evocation | 0 | 1 | 16 | 0 | 4 |
| Lightning | Area | Lightning | 12 | 1 | 32 | 0.4 | 5 |
| Smite | Area | Lightning | 32 | 1 | 65 | 1.4 | 10 |
| Cinder Vortex | Area | Fire | 9 | 4 | 34 | 1.25 | 6 |
| Flamethrower | Beam | Fire | 4 | 6 | 40 | 0.4 | 5 |
| Cinder Surge | Area | Fire | 16 | 1 | 41 | 0.8 | 7.5 |
| Crimson Rupture | Area | Blood | 20 | 1 | 46 | 1 | 7 |
| Hemorrhagic Shock | Control | Blood | 3 | 1 | 22 | 0.4 | 5 |
| Crimson Ward | Support | Blood | 0 | 1 | 26 | 0.5 | 9 |
| Shadow Bolt | Projectile | Ender | 7 | 1 | 20 | 0 | 1.25 |
| Umbral Seeker | Projectile | Ender | 10 | 1 | 27 | 0.6 | 3.5 |
| Umbral Wave | Area | Ender | 7 | 1 | 24 | 0.35 | 3.25 |
| Nightfall | Area | Ender | 20 | 1 | 46 | 1 | 7 |
| Dread Mark | Control | Ender | 24 | 1 | 52 | 0.5 | 8 |
| Umbral Veil | Area | Ender | 5 | 3 | 30 | 0.5 | 8 |
| Thunder Bolt | Projectile | Lightning | 8 | 1 | 23 | 0 | 1.5 |
| Chain Lightning | Beam | Lightning | 9 | 1 | 32 | 0.6 | 6 |
| Thunder Wave | Area | Lightning | 7 | 1 | 24 | 0.35 | 3.25 |
| Storm Call | Area | Lightning | 8 | 5 | 70 | 1.6 | 12 |
| Thunder Pulse | Area | Lightning | 16 | 1 | 43 | 0.7 | 7.5 |
| Static Shock | Control | Lightning | 4 | 1 | 22 | 0.4 | 4 |
| Rift Bolt | Projectile | Ender | 7 | 1 | 21 | 0 | 1.5 |
| Rift Well | Area | Ender | 5 | 5 | 55 | 1.25 | 9 |
| Force Push | Control | Ender | 3 | 1 | 18 | 0 | 3.25 |
| Force Pull | Control | Ender | 3 | 1 | 18 | 0 | 3.25 |
| Suspension | Control | Ender | 3 | 1 | 26 | 0.6 | 7 |
| Singularity | Area | Ender | 32 | 1 | 70 | 1.6 | 9 |
| Echo Bolt | Projectile | Eldritch | 7 | 1 | 20 | 0 | 1.25 |
| Sculk Wave | Area | Eldritch | 7 | 1 | 24 | 0.35 | 3.25 |
| Sonic Scream | Area | Eldritch | 28 | 1 | 55 | 1 | 8 |
| Soul Siphon | Melee | Eldritch | 8 | 1 | 28 | 0.7 | 5 |
| Soul Prison | Control | Eldritch | 3 | 3 | 26 | 0.6 | 7 |
| Shrieking Pulse | Area | Eldritch | 16 | 1 | 41 | 0.8 | 7.5 |
| Amethyst Nova | Area | Eldritch | 24 | 1 | 55 | 1.2 | 8 |
| Echo Ward | Support | Eldritch | 0 | 1 | 26 | 0.5 | 9 |
| Arcane Volley | Projectile | Evocation | 5 | 3 | 23 | 0.5 | 4.5 |
| Sanguine Drain | Melee | Blood | 8 | 1 | 28 | 0.7 | 5 |
| Blood Lance | Beam | Blood | 16 | 1 | 35 | 0.5 | 4.5 |
| Ember Veil | Area | Fire | 4 | 3 | 27 | 0.5 | 7.5 |
| Venom Bolt | Projectile | Nature | 4 | 1 | 17 | 0 | 1.75 |
| Miasma Field | Area | Nature | 5 | 5 | 50 | 0.75 | 9 |
| Withering Hex | Melee | Nature | 4 | 1 | 25 | 0.25 | 5.5 |
| Soul Cannon | Beam | Eldritch | 48 | 1 | 90 | 2 | 14 |
| Solar Cataclysm | Area | Fire | 64 | 1 | 90 | 3 | 20 |
| Tempest Spear | Beam | Lightning | 56 | 1 | 85 | 2.5 | 18 |
| Event Horizon | Area | Ender | 72 | 1 | 100 | 3.2 | 24 |
| DOOM | Cataclysm | Ender | 256 | 1 | 500 | 10 | 120 |
| Sanctuary | Support | Holy | 0 | 1 | 80 | 2.5 | 18 |
| Glacial Rupture | Area | Ice | 52 | 1 | 85 | 2.5 | 18 |
| Evoker's Edict | Summon | Evocation | 44 | 1 | 80 | 2.4 | 16 |
| Arcane Step | Mobility | Evocation | 0 | 1 | 24 | 0 | 5 |
| Blood Needles | Projectile | Blood | 5 | 5 | 28 | 0.4 | 4 |
| Blood Reap | Melee | Blood | 16 | 1 | 34 | 0.7 | 5.5 |
| Crimson Tether | Control | Blood | 5 | 3 | 42 | 0.8 | 8 |
| Sanguine Pact | Support | Blood | 0 | 1 | 40 | 0.7 | 12 |
| Crimson Harvest | Melee | Blood | 22 | 1 | 48 | 1.1 | 9 |
| Cinder Step | Mobility | Fire | 0 | 1 | 28 | 0 | 6 |
| Phoenix Rush | Mobility | Fire | 20 | 1 | 45 | 0.8 | 9 |
| Dragon Breath | Beam | Fire | 7 | 3 | 50 | 1 | 8 |
| Umbral Step | Mobility | Ender | 0 | 1 | 30 | 0 | 7 |
| Dusk Cleave | Melee | Ender | 18 | 1 | 36 | 0.7 | 6 |
| Dread Effigy | Summon | Ender | 5 | 4 | 55 | 1.2 | 11 |
| Veil of Night | Support | Ender | 0 | 1 | 45 | 0.9 | 13 |
| Night Bloom | Summon | Ender | 28 | 1 | 52 | 1.1 | 11 |
| Storm Step | Mobility | Lightning | 0 | 1 | 26 | 0 | 5 |
| Thunderclap | Melee | Lightning | 20 | 1 | 42 | 0.9 | 7 |
| Arc Ricochet | Projectile | Lightning | 10 | 1 | 40 | 0.7 | 7 |
| Rift Blink | Mobility | Ender | 0 | 1 | 28 | 0 | 6 |
| Orbital Blades | Summon | Ender | 6 | 3 | 48 | 1 | 10 |
| Prismatic Ruin | Projectile | Ender | 40 | 1 | 100 | 3.5 | 30 |
| Gravity Stamp | Area | Ender | 20 | 1 | 55 | 1.4 | 11 |
| Spatial Shear | Melee | Ender | 26 | 1 | 42 | 0.9 | 7.5 |
| Wisp Lantern | Summon | Eldritch | 5 | 4 | 52 | 1.2 | 11 |
| Resonant Guard | Support | Eldritch | 0 | 1 | 44 | 0.9 | 12 |
| Memory Fracture | Melee | Eldritch | 24 | 1 | 42 | 0.9 | 8 |
| Thorn Lash | Control | Nature | 16 | 1 | 30 | 0.5 | 5.5 |
| Viper Fangs | Projectile | Nature | 4 | 5 | 28 | 0.5 | 4.5 |
| Bramble Ring | Area | Nature | 10 | 1 | 40 | 0.7 | 7.5 |
| Toxic Molt | Support | Nature | 0 | 1 | 38 | 0.8 | 11 |
| Spore Totem | Summon | Nature | 4 | 4 | 50 | 1.2 | 11 |
| Corrosive Rain | Area | Nature | 5 | 5 | 60 | 1.4 | 12 |
| Gorgon Gaze | Control | Nature | 4 | 1 | 48 | 1.2 | 10 |
| Luminous Dart | Projectile | Holy | 6 | 1 | 16 | 0 | 1 |
| Mending Light | Support | Holy | 0 | 1 | 32 | 0.9 | 6 |
| Blessing of Vigor | Support | Holy | 0 | 1 | 42 | 0.9 | 12 |
| Consecration | Summon | Holy | 4 | 4 | 55 | 1.2 | 12 |
| Radiant Sweep | Melee | Holy | 14 | 1 | 30 | 0.6 | 5.5 |
| Pilgrim Step | Mobility | Holy | 0 | 1 | 26 | 0 | 6 |
| Absolution | Support | Holy | 0 | 1 | 40 | 1 | 10 |
| Dawn Ward | Support | Holy | 0 | 1 | 42 | 0.8 | 10 |
| Judgement Ray | Beam | Holy | 44 | 1 | 70 | 2 | 13 |
| Seraphic Descent | Area | Holy | 56 | 1 | 90 | 2.5 | 20 |
| Renewal Hymn | Support | Holy | 0 | 4 | 55 | 1.2 | 13 |
| Frost Needle | Projectile | Ice | 6 | 1 | 16 | 0 | 1 |
| Ice Serpent | Projectile | Ice | 12 | 1 | 34 | 0.7 | 5.5 |
| Rime Nova | Area | Ice | 16 | 1 | 44 | 0.9 | 8 |
| Cryo Lance | Beam | Ice | 18 | 1 | 38 | 0.9 | 6 |
| Frozen Aegis | Support | Ice | 0 | 1 | 44 | 0.9 | 12 |
| Glacier Step | Mobility | Ice | 0 | 1 | 26 | 0 | 6 |
| Shatter | Melee | Ice | 20 | 1 | 42 | 0.9 | 8 |
| Hailstorm | Area | Ice | 5 | 5 | 60 | 1.4 | 12 |
| Glacial Prison | Control | Ice | 0 | 1 | 45 | 1 | 9 |
| Crystal Barrage | Projectile | Ice | 5 | 5 | 36 | 0.7 | 6 |
| Absolute Zero | Area | Ice | 8 | 4 | 85 | 2.5 | 20 |
| Warscream | Support | Holy | 5 | 3 | 45 | 0.6 | 20 |
| Soulblast | Area | Eldritch | 10 | 1 | 25 | 0 | 5 |
| Arcane Edge | Support | Evocation | 0 | 1 | 35 | 0.4 | 20 |
| Bulwark Chant | Support | Holy | 0 | 1 | 40 | 0.8 | 20 |
| Battle Tempo | Support | Lightning | 0 | 1 | 35 | 0.4 | 20 |
| Cinder Cleave | Melee | Fire | 6 | 1 | 25 | 0.4 | 5 |
| Rime Thrust | Melee | Ice | 6 | 1 | 25 | 0.4 | 6 |
| Blood Rend | Melee | Blood | 6 | 1 | 30 | 0.5 | 7 |
| Kraken’s Grasp | Boss Art | Nature | 32 | 1 | 60 | 1.2 | 14 |
| Ice Spike Eruption | Boss Art | Ice | 18 | 1 | 40 | 0.8 | 9 |
| Helvar’s Rift | Boss Art | Ender | 24 | 1 | 40 | 0.9 | 8 |
| Kraken’s Wrath | Boss Art | Nature | 18 | 2 | 110 | 3.6 | 30 |
| Kraken’s Undertow | Boss Art | Nature | 26 | 1 | 65 | 1.3 | 16 |
| Yeti’s Glacial Wall | Boss Art | Ice | 20 | 1 | 50 | 1 | 11 |
| Helvar’s Fracture | Boss Art | Ender | 22 | 1 | 60 | 1.3 | 12 |
| Helvar’s Swordfall | Boss Art | Ender | 36 | 1 | 60 | 1.3 | 12 |
| Sandworm’s Eruption | Boss Art | Ender | 22 | 1 | 50 | 1 | 11 |
| Sandworm’s Quicksand | Boss Art | Ender | 24 | 1 | 65 | 1.2 | 15 |
| Dragon’s Volley | Boss Art | Fire | 14 | 3 | 60 | 1.2 | 14 |
| Dragon’s Cataclysm | Boss Art | Fire | 44 | 1 | 85 | 1.6 | 18 |
| Glacial Counter | Support | Ice | 18 | 1 | 30 | 0 | 8 |
| Rift Draw | Melee | Ender | 28 | 1 | 50 | 1.5 | 9 |
| Crimson Reversal | Mobility | Blood | 16 | 1 | 35 | 0.5 | 10 |
| Thunderbrand | Support | Lightning | 12 | 1 | 55 | 0.8 | 16 |
| Last Stand | Summon | Holy | 0 | 1 | 70 | 1.3 | 20 |
| Ashen Rebirth | Mobility | Fire | 32 | 1 | 45 | 0.6 | 12 |
| Phantom Formation | Summon | Evocation | 12 | 1 | 60 | 1.2 | 17 |
| Funeral Bell | Area | Eldritch | 16 | 3 | 95 | 3 | 21 |
| Spellbreaker | Melee | Evocation | 6 | 1 | 35 | 0.4 | 8 |
| Astral Apocalypse | Cataclysm | Evocation | 80 | 1 | 440 | 10 | 120 |
| Crimson Eclipse | Cataclysm | Blood | 80 | 1 | 460 | 10 | 120 |
| World Pyre | Cataclysm | Fire | 72 | 1 | 500 | 12 | 120 |
| Void Coronation | Cataclysm | Ender | 88 | 1 | 480 | 11 | 120 |
| Heaven’s Thunder | Cataclysm | Lightning | 64 | 1 | 480 | 10 | 120 |
| Rift Collision | Cataclysm | Ender | 96 | 1 | 500 | 12 | 120 |
| Soul Requiem | Cataclysm | Eldritch | 96 | 1 | 460 | 11 | 120 |
| Worldroot Blessing | Cataclysm | Nature | 72 | 1 | 440 | 10 | 120 |
| Final Judgement | Cataclysm | Holy | 80 | 1 | 500 | 12 | 120 |
| Eternal Winter | Cataclysm | Ice | 40 | 9 | 480 | 11 | 120 |
| Explosion | Cataclysm | Fire | 256 | 1 | 480 | 7.7 | 120 |
| Seraph Ascendant | Support | Holy | 0 | 1 | 160 | 4 | 60 |
| Arcane Devastator | Beam | Evocation | 120 | 1 | 220 | 6.5 | 50 |
| I Am Atomic | Cataclysm | Ender | 512 | 1 | 500 | 13.5 | 180 |
| Doom Beam | Cataclysm | Ender | 320 | 1 | 500 | 13 | 150 |
| Void Exile | Control | Ender | 20 | 1 | 110 | 4 | 30 |
| Void Domain | Support | Ender | 0 | 1 | 240 | 5 | 90 |
| Polarity Bolt | Projectile | Lightning | 9 | 1 | 24 | 0.4 | 3.5 |
| EMP Surge | Control | Lightning | 56 | 1 | 120 | 3 | 30 |
| Magnetar Annihilation | Cataclysm | Lightning | 320 | 1 | 480 | 11 | 130 |
| Amber Talon | Summon | Nature | 12 | 1 | 28 | 0.6 | 5 |
| Primordial Instinct | Support | Nature | 0 | 1 | 130 | 3 | 35 |
| Lux’s Caldera | Cataclysm | Fire | 80 | 5 | 500 | 12 | 140 |
| Acid Glob | Projectile | Nature | 8 | 1 | 25 | 0.5 | 4.5 |
| Tremorzilla Breath | Beam | Nature | 72 | 1 | 180 | 4 | 50 |
| Nuclear Strike | Cataclysm | Nature | 384 | 1 | 500 | 13 | 150 |
| Brine Bubble | Projectile | Ice | 10 | 1 | 26 | 0.6 | 5 |
| Hullbreaker’s Breach | Area | Ice | 60 | 1 | 135 | 3.2 | 36 |
| Abyssal Deluge | Cataclysm | Ice | 72 | 5 | 480 | 11 | 140 |
| Desolate Dagger | Projectile | Ender | 12 | 1 | 28 | 0.5 | 5.5 |
| Forsaken Sonar | Beam | Ender | 22 | 3 | 140 | 3 | 37.5 |
| Forlorn Reckoning | Cataclysm | Ender | 88 | 4 | 490 | 12 | 140 |
| Gumball Volley | Projectile | Evocation | 5 | 3 | 30 | 0.6 | 6 |
| Frostmint Hex | Control | Ice | 12 | 5 | 140 | 3.2 | 37.5 |
| Candyland Catastrophe | Cataclysm | Evocation | 72 | 5 | 480 | 11 | 140 |
| Thunder of the Heavens | God | Lightning | 96 | 1 | 600 | 20 | 240 |
| Arcane Creation | God | Evocation | 140 | 1 | 600 | 20 | 240 |
| Crimson Apotheosis | God | Blood | 120 | 1 | 600 | 20 | 240 |
| Solar Supernova | God | Fire | 960 | 1 | 600 | 20 | 240 |
| Eventide Horizon | God | Ender | 150 | 1 | 600 | 20 | 240 |
| Absolute Glaciation | God | Ice | 120 | 1 | 600 | 20 | 240 |
| Verdant Awakening | God | Nature | 90 | 1 | 600 | 20 | 240 |
| Heaven’s Covenant | God | Holy | 80 | 1 | 600 | 20 | 240 |
| Soul Ascendancy | God | Eldritch | 130 | 1 | 600 | 20 | 240 |
| Planetary Polarity | God | Lightning | 140 | 1 | 600 | 20 | 240 |
| Primordial Sovereign | God | Nature | 120 | 1 | 600 | 20 | 240 |
| Tremorzilla Cataclysm | God | Nature | 880 | 1 | 600 | 20 | 240 |
| Abyssal World Tide | God | Ice | 150 | 1 | 600 | 20 | 240 |
| Forsaken Eclipse | God | Ender | 170 | 1 | 600 | 20 | 240 |
| Confection Apocalypse | God | Evocation | 140 | 1 | 600 | 20 | 240 |
| Kraken’s World Drown | God | Ice | 160 | 1 | 600 | 20 | 240 |
| Yeti’s World Heart | God | Ice | 150 | 1 | 600 | 20 | 240 |
| Helvar’s Final Edict | God | Ender | 240 | 1 | 600 | 20 | 240 |
| Sandworm’s World Devourer | God | Nature | 150 | 1 | 600 | 20 | 240 |
| Dragon’s Primordial Inferno | God | Fire | 160 | 1 | 600 | 20 | 240 |

¹ Catalogue pulse count is not a guaranteed hit count. Cataclysm sequences, reactive weapon enchantments and projectile fans are described in [the spell reference](SPELLS.md).

Verification includes native casting and cancellation, rank scaling, team/PvP protections, obstruction, finite lifetimes, spell-registry tags, searchable scroll keywords, Void Domain boundaries and effects, and ordinary damage immunity for the corrected multi-hit spells. Client rendering and sound still require in-game playtesting.
