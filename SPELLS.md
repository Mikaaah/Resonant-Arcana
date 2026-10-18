# Spell reference

**152 core spells + 12 optional Bosses’Rise spells.** Every spell has one functional category and one native Iron’s school.

Cast through an Iron’s spellbook. The three legendary books are inscribable equipment; their perks apply in the Curios spellbook slot. The debug wand exposes every enabled spell and offers category filters. It has no survival recipe.

Values are defaults before school power, equipment, resistances and server settings. Damage and healing use health points: two health points equal one heart. Damage is per normal hit or pulse; descriptions explain final multipliers. Travel time and release sequences occur after the cast bar. Ordinary spells have up to five ranks; Doom and cataclysms have one Legendary rank.

Offensive spells protect the caster, eligible teammates and owned pets. Healing affects the caster, owned pets and visible scoreboard teammates, plus teamless players when enabled. Cover, enemy movement, normal damage immunity and status immunities can change results. Spells do not destroy terrain or intentionally load new chunks.

Cataclysm windups use a large seal at the caster’s feet, with dense native particles spiralling down from above and a small stream into the caster. Optional AAAParticles are limited to floor seals, Explosion’s five beam seals and Doom. Without AAA, native outlines remain. Charge cues repeat throughout the cast.

## Categories

| Category | Spells |
| --- | ---: |
| [Projectiles](#projectile) | 19 |
| [Beams & Channels](#beam) | 9 |
| [Area Magic](#area) | 35 |
| [Control](#control) | 15 |
| [Movement](#mobility) | 11 |
| [Support & Defense](#support) | 20 |
| [Summons & Constructs](#summon) | 9 |
| [Strikes & Drains](#melee) | 20 |
| [Boss Arts](#boss_art) | 12 |
| [Cataclysms](#cataclysm) | 14 |

## Schools

| School | Spells |
| --- | ---: |
| Evocation | 17 |
| Blood | 15 |
| Fire | 18 |
| Ender | 34 |
| Lightning | 15 |
| Eldritch | 15 |
| Nature | 16 |
| Holy | 17 |
| Ice | 17 |

<a id="projectile"></a>
## Projectiles

### Spark

Loose a compact arcane spark along your aim. It stops at the first enemy or solid surface, releasing a small arcane impact.

**Evocation** · Cast **0s** · Cooldown **0.6s** · Range **32 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 15 |
| II | 3.75 | — | 18 |
| III | 4.5 | — | 21 |
| IV | 5.25 | — | 24 |
| V | 6 | — | 27 |

Spell ID: `resonant_arcana:spark`. Inspect ranks with `/resonantarcana spell spark <rank>`.

### Astral Comet

Launch a slow astral comet with a bright core and trailing star motes. It bursts around the first enemy or solid surface reached, damaging visible enemies in the impact area.

**Evocation** · Cast **1s** · Cooldown **6s** · Range **48 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 45 |
| II | 17.5 | — | 54 |
| III | 21 | — | 63 |
| IV | 24.5 | — | 72 |
| V | 28 | — | 81 |

Spell ID: `resonant_arcana:astral_comet`. Inspect ranks with `/resonantarcana spell astral_comet <rank>`.

### Fire Comet

Hurl a slow ember comet with a burning wake. Its first collision releases an area detonation and ignites enemies hit for three seconds.

**Fire** · Cast **1s** · Cooldown **6s** · Range **44 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 45 |
| II | 15 | — | 54 |
| III | 18 | — | 63 |
| IV | 21 | — | 72 |
| V | 24 | — | 81 |

Spell ID: `resonant_arcana:fire_comet`. Inspect ranks with `/resonantarcana spell fire_comet <rank>`.

### Fireball

Throw a compact fireball with a coiling ember trail. Its first collision releases a small area burst and ignites enemies hit for three seconds.

**Fire** · Cast **0s** · Cooldown **2.5s** · Range **40 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 25 |
| II | 7.5 | — | 30 |
| III | 9 | — | 35 |
| IV | 10.5 | — | 40 |
| V | 12 | — | 45 |

Spell ID: `resonant_arcana:fireball`. Inspect ranks with `/resonantarcana spell fireball <rank>`.

### Shadow Bolt

Launch a twisting shadow bolt. Its first collision releases a small void impact, applying Weakness I for three seconds to enemies hit.

**Ender** · Cast **0s** · Cooldown **1.25s** · Range **36 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 20 |
| II | 6.25 | — | 24 |
| III | 7.5 | — | 28 |
| IV | 8.75 | — | 32 |
| V | 10 | — | 36 |

Spell ID: `resonant_arcana:shadow_bolt`. Inspect ranks with `/resonantarcana spell shadow_bolt <rank>`.

### Umbral Seeker

Release a dark wisp that steers toward the enemy selected at release. Its impact damages and weakens enemies; solid cover intercepts the projectile.

**Ender** · Cast **0.6s** · Cooldown **3.5s** · Range **36 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 27 |
| II | 7.5 | — | 32 |
| III | 9 | — | 37 |
| IV | 10.5 | — | 42 |
| V | 12 | — | 47 |

Spell ID: `resonant_arcana:soul_seeker`. Inspect ranks with `/resonantarcana spell soul_seeker <rank>`.

### Thunder Bolt

Fire a compact electric bolt with a jagged spark wake. The first enemy or solid surface stops it and releases a small electric impact.

**Lightning** · Cast **0s** · Cooldown **1.5s** · Range **40 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 23 |
| II | 7.5 | — | 27 |
| III | 9 | — | 31 |
| IV | 10.5 | — | 35 |
| V | 12 | — | 39 |

Spell ID: `resonant_arcana:thunder_bolt`. Inspect ranks with `/resonantarcana spell thunder_bolt <rank>`.

### Rift Bolt

Send a compressed spatial orb along your aim, trailed by inward-curling portal motes. Its first collision releases a small force impact.

**Ender** · Cast **0s** · Cooldown **1.5s** · Range **32 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 21 |
| II | 6.25 | — | 25 |
| III | 7.5 | — | 29 |
| IV | 8.75 | — | 33 |
| V | 10 | — | 37 |

Spell ID: `resonant_arcana:gravity_bolt`. Inspect ranks with `/resonantarcana spell gravity_bolt <rank>`.

### Echo Bolt

Fire a resonant soul bolt with blue sculk motes. It ends at the first enemy or solid surface, releasing a small echo impact.

**Eldritch** · Cast **0s** · Cooldown **1.25s** · Range **36 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 20 |
| II | 6.25 | — | 24 |
| III | 7.5 | — | 28 |
| IV | 8.75 | — | 32 |
| V | 10 | — | 36 |

Spell ID: `resonant_arcana:echo_bolt`. Inspect ranks with `/resonantarcana spell echo_bolt <rank>`.

### Arcane Volley

Fire three arcane bolts staggered 0.3 seconds apart. Each bolt follows your aim when it leaves, stops at cover and releases its own small impact; normal damage immunity still applies.

**Evocation** · Cast **0.5s** · Cooldown **4.5s** · Range **32 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 23 |
| II | 3.75 | — | 27 |
| III | 4.5 | — | 31 |
| IV | 5.25 | — | 35 |
| V | 6 | — | 39 |

Base release / active phase: **2.2s**.

Spell ID: `resonant_arcana:arcane_volley`. Inspect ranks with `/resonantarcana spell arcane_volley <rank>`.

### Venom Bolt

Spit a travelling acid orb with a spore-speckled wake. Its first collision releases a small impact that damages enemies and applies Poison I for four seconds.

**Nature** · Cast **0s** · Cooldown **1.75s** · Range **32 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 17 |
| II | 3.75 | — | 20 |
| III | 4.5 | — | 23 |
| IV | 5.25 | — | 26 |
| V | 6 | — | 29 |

Spell ID: `resonant_arcana:venom_bolt`. Inspect ranks with `/resonantarcana spell venom_bolt <rank>`.

### Blood Needles

Release five divergent blood needles. Damage is per projectile; projectiles stop at solid cover.

**Blood** · Cast **0.4s** · Cooldown **4s** · Range **28 blocks** · Radius / half-width **0.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 28 |
| II | 5 | — | 33 |
| III | 6 | — | 38 |
| IV | 7 | — | 43 |
| V | 8 | — | 48 |

Spell ID: `resonant_arcana:blood_needles`. Inspect ranks with `/resonantarcana spell blood_needles <rank>`.

### Arc Ricochet

Throw a travelling lightning orb that can ricochet between three visible enemies, hitting each once.

**Lightning** · Cast **0.7s** · Cooldown **7s** · Range **32 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 40 |
| II | 7.5 | — | 48 |
| III | 9 | — | 56 |
| IV | 10.5 | — | 64 |
| V | 12 | — | 72 |

Spell ID: `resonant_arcana:arc_ricochet`. Inspect ranks with `/resonantarcana spell arc_ricochet <rank>`.

### Prismatic Ruin

Merge opposing energies into a slow, broad spatial orb. It passes through enemies, hitting each once, and stops at solid cover or maximum range.

**Ender** · Cast **3.5s** · Cooldown **30s** · Range **48 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 40 | — | 100 |
| II | 50 | — | 110 |
| III | 60 | — | 120 |
| IV | 70 | — | 130 |
| V | 80 | — | 140 |

Spell ID: `resonant_arcana:prismatic_ruin`. Inspect ranks with `/resonantarcana spell prismatic_ruin <rank>`.

### Viper Fangs

Release five venom fangs in a spreading fan. Each projectile can poison the enemy it hits. Nature hits poison for four seconds.

**Nature** · Cast **0.5s** · Cooldown **4.5s** · Range **24 blocks** · Radius / half-width **0.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 28 |
| II | 3.75 | — | 33 |
| III | 4.5 | — | 38 |
| IV | 5.25 | — | 43 |
| V | 6 | — | 48 |

Spell ID: `resonant_arcana:viper_fangs`. Inspect ranks with `/resonantarcana spell viper_fangs <rank>`.

### Luminous Dart

Fire a quick dart of holy light. It stops at solid cover.

**Holy** · Cast **0s** · Cooldown **1s** · Range **32 blocks** · Radius / half-width **0.7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 16 |
| II | 5 | — | 19 |
| III | 6 | — | 22 |
| IV | 7 | — | 25 |
| V | 8 | — | 28 |

Spell ID: `resonant_arcana:luminous_dart`. Inspect ranks with `/resonantarcana spell luminous_dart <rank>`.

### Frost Needle

Fire a fast ice needle that chills its target and stops at solid cover. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Ice** · Cast **0s** · Cooldown **1s** · Range **32 blocks** · Radius / half-width **0.7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 16 |
| II | 5 | — | 19 |
| III | 6 | — | 22 |
| IV | 7 | — | 25 |
| V | 8 | — | 28 |

Spell ID: `resonant_arcana:frost_needle`. Inspect ranks with `/resonantarcana spell frost_needle <rank>`.

### Ice Serpent

Send a low, weaving ice serpent horizontally along your aim. It chills each enemy struck and stops at solid cover; it does not climb terrain. Ice hits chill for two seconds and add 40 freezing ticks.

**Ice** · Cast **0.7s** · Cooldown **5.5s** · Range **24 blocks** · Radius / half-width **1.4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 34 |
| II | 8.75 | — | 40 |
| III | 10.5 | — | 46 |
| IV | 12.25 | — | 52 |
| V | 14 | — | 58 |

Spell ID: `resonant_arcana:ice_serpent`. Inspect ranks with `/resonantarcana spell ice_serpent <rank>`.

### Crystal Barrage

Release five divergent crystal projectiles. Damage is per projectile; their impacts chill enemies. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Ice** · Cast **0.7s** · Cooldown **6s** · Range **30 blocks** · Radius / half-width **0.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 36 |
| II | 5 | — | 43 |
| III | 6 | — | 50 |
| IV | 7 | — | 57 |
| V | 8 | — | 64 |

Spell ID: `resonant_arcana:crystal_barrage`. Inspect ranks with `/resonantarcana spell crystal_barrage <rank>`.


<a id="beam"></a>
## Beams & Channels

### Flamethrower

Channel a narrow stream of flame following your current aim for 1.5 seconds. Six quarter-second pulses can hit enemies intersecting the stream; solid cover stops it. Hits ignite for three seconds.

**Fire** · Cast **0.4s** · Cooldown **5s** · Range **12 blocks** · Radius / half-width **1.4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 22 |
| II | 2.5 | — | 26 |
| III | 3 | — | 30 |
| IV | 3.5 | — | 34 |
| V | 4 | — | 38 |

Base release / active phase: **1.5s**.

Spell ID: `resonant_arcana:flame_thrower`. Inspect ranks with `/resonantarcana spell flame_thrower <rank>`.

### Chain Lightning

Connect up to five distinct visible enemies with jagged lightning links. Each jump chooses a nearby eligible enemy; cover and the jump radius can end the chain early.

**Lightning** · Cast **0.6s** · Cooldown **6s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 32 |
| II | 7.5 | — | 38 |
| III | 9 | — | 44 |
| IV | 10.5 | — | 50 |
| V | 12 | — | 56 |

Spell ID: `resonant_arcana:chain_lightning`. Inspect ranks with `/resonantarcana spell chain_lightning <rank>`.

### Blood Lance

Drive a straight crimson lance through visible enemies along your aim. Each enemy on the piercing line is hit once; solid cover ends the lance.

**Blood** · Cast **0.5s** · Cooldown **4.5s** · Range **24 blocks** · Radius / half-width **0.6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 35 |
| II | 11.25 | — | 42 |
| III | 13.5 | — | 49 |
| IV | 15.75 | — | 56 |
| V | 18 | — | 63 |

Spell ID: `resonant_arcana:blood_lance`. Inspect ranks with `/resonantarcana spell blood_lance <rank>`.

### Soul Cannon

Charge for two seconds, then launch a massive echo blast. Switch away to cancel charging.

**Eldritch** · Cast **2s** · Cooldown **14s** · Range **48 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 90 |
| II | 20 | — | 100 |
| III | 24 | — | 110 |
| IV | 28 | — | 120 |
| V | 32 | — | 130 |

Spell ID: `resonant_arcana:soul_cannon`. Inspect ranks with `/resonantarcana spell soul_cannon <rank>`.

### Tempest Spear

Charge for 2.5 seconds, then fire a piercing storm lance. Solid cover stops the beam.

**Lightning** · Cast **2.5s** · Cooldown **18s** · Range **56 blocks** · Radius / half-width **1.1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 30 | — | 85 |
| II | 37.5 | — | 95 |
| III | 45 | — | 105 |
| IV | 52.5 | — | 115 |
| V | 60 | — | 125 |

Spell ID: `resonant_arcana:tempest_spear`. Inspect ranks with `/resonantarcana spell tempest_spear <rank>`.

### Dragon Breath

Exhale a broad cone of flame following your aim for 1.5 seconds. Three pulses can hit enemies that remain in the cone. Fire hits ignite for three seconds.

**Fire** · Cast **1s** · Cooldown **8s** · Range **10 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 50 |
| II | 3.75 | — | 60 |
| III | 4.5 | — | 70 |
| IV | 5.25 | — | 80 |
| V | 6 | — | 90 |

Base release / active phase: **1.5s**.

Spell ID: `resonant_arcana:dragon_breath`. Inspect ranks with `/resonantarcana spell dragon_breath <rank>`.

### Judgement Ray

Charge a piercing ray of holy light, striking each visible enemy on its line once. Solid cover stops the ray.

**Holy** · Cast **2s** · Cooldown **13s** · Range **40 blocks** · Radius / half-width **0.7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 22 | — | 70 |
| II | 27.5 | — | 80 |
| III | 33 | — | 90 |
| IV | 38.5 | — | 100 |
| V | 44 | — | 110 |

Spell ID: `resonant_arcana:judgement_ray`. Inspect ranks with `/resonantarcana spell judgement_ray <rank>`.

### Cryo Lance

Fire a precise piercing ice lance through visible enemies. Solid cover blocks it. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Ice** · Cast **0.9s** · Cooldown **6s** · Range **28 blocks** · Radius / half-width **0.7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 38 |
| II | 12.5 | — | 45 |
| III | 15 | — | 52 |
| IV | 17.5 | — | 59 |
| V | 20 | — | 66 |

Spell ID: `resonant_arcana:cryo_lance`. Inspect ranks with `/resonantarcana spell cryo_lance <rank>`.

### Arcane Devastator

Charge for 6.5 seconds to the recording’s second attack, then release a broad cosmic beam for 7.4 seconds along your final aim. Each visible enemy entering it takes one 120-base-damage hit. Solid cover stops the beam. Fixed windup, original recording pitch.

**Evocation** · Cast **6.5s** · Cooldown **50s** · Range **48 blocks** · Radius / half-width **2.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 120 | — | 220 |
| II | 150 | — | 240 |
| III | 180 | — | 260 |
| IV | 210 | — | 280 |
| V | 240 | — | 300 |

Base release / active phase: **7.4s**.

Spell ID: `resonant_arcana:arcane_devastator`. Inspect ranks with `/resonantarcana spell arcane_devastator <rank>`.


<a id="area"></a>
## Area Magic

### Blood Wave

Send a broad crimson crescent along your aim. Its moving front cuts each visible enemy once and dissipates against solid cover.

**Blood** · Cast **0.35s** · Cooldown **3s** · Range **28 blocks** · Radius / half-width **1.8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:blood_wave`. Inspect ranks with `/resonantarcana spell blood_wave <rank>`.

### Poison Wave

Roll a low green acid wave forwards. Each enemy crossed by its broad front takes one direct hit and Poison I for four seconds.

**Nature** · Cast **0.35s** · Cooldown **3.5s** · Range **28 blocks** · Radius / half-width **1.8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 19 |
| II | 2.5 | — | 22 |
| III | 3 | — | 25 |
| IV | 3.5 | — | 28 |
| V | 4 | — | 31 |

Spell ID: `resonant_arcana:poison_wave`. Inspect ranks with `/resonantarcana spell poison_wave <rank>`.

### Flame Wave

Drive a broad wall of flickering embers forwards. Its advancing front hits each visible enemy once and ignites them for three seconds.

**Fire** · Cast **0.35s** · Cooldown **3s** · Range **28 blocks** · Radius / half-width **1.8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:flame_wave`. Inspect ranks with `/resonantarcana spell flame_wave <rank>`.

### Arcane Repulse

Release an expanding arcane pressure ring from your feet. Enemies touched by the advancing rim take one hit and are pushed away; walls block the hit.

**Evocation** · Cast **0.5s** · Cooldown **6s** · Range **0 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 36 |
| II | 10 | — | 43 |
| III | 12 | — | 50 |
| IV | 14 | — | 57 |
| V | 16 | — | 64 |

Spell ID: `resonant_arcana:arcane_repulse`. Inspect ranks with `/resonantarcana spell arcane_repulse <rank>`.

### Lightning

Call a vertical lightning strike onto your aim, damaging eligible visible enemies near the strike point. Uses addon damage rather than a terrain-igniting vanilla lightning entity.

**Lightning** · Cast **0.4s** · Cooldown **5s** · Range **32 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 32 |
| II | 8.75 | — | 38 |
| III | 10.5 | — | 44 |
| IV | 12.25 | — | 50 |
| V | 14 | — | 56 |

Spell ID: `resonant_arcana:lightning`. Inspect ranks with `/resonantarcana spell lightning <rank>`.

### Smite

Mark your aim with holy light, then deliver a vertical judgement strike. Visible enemies within its impact radius take the listed damage; allies are protected.

**Lightning** · Cast **1.4s** · Cooldown **10s** · Range **32 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 65 |
| II | 22.5 | — | 75 |
| III | 27 | — | 85 |
| IV | 31.5 | — | 95 |
| V | 36 | — | 105 |

Spell ID: `resonant_arcana:smite`. Inspect ranks with `/resonantarcana spell smite <rank>`.

### Cinder Vortex

Raise a stationary ember funnel at your aim for two seconds. Four half-second pulses can damage visible enemies inside and refresh three seconds of burning.

**Fire** · Cast **1.25s** · Cooldown **6s** · Range **30 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 34 |
| II | 8.75 | — | 40 |
| III | 10.5 | — | 46 |
| IV | 12.25 | — | 52 |
| V | 14 | — | 58 |

Base release / active phase: **2s**.

Spell ID: `resonant_arcana:fire_twister`. Inspect ranks with `/resonantarcana spell fire_twister <rank>`.

### Cinder Surge

Send an expanding ember ring across the ground around you. Its advancing rim damages each visible enemy once, pushes them away and ignites them for three seconds.

**Fire** · Cast **0.8s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **9 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 41 |
| II | 11.25 | — | 49 |
| III | 13.5 | — | 57 |
| IV | 15.75 | — | 65 |
| V | 18 | — | 73 |

Spell ID: `resonant_arcana:flame_shock_wave`. Inspect ranks with `/resonantarcana spell flame_shock_wave <rank>`.

### Crimson Rupture

Detonate a crimson bloom at the aimed enemy or reachable aim point. Its radial blood spray damages visible enemies in the impact area once.

**Blood** · Cast **1s** · Cooldown **7s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 46 |
| II | 15 | — | 55 |
| III | 18 | — | 64 |
| IV | 21 | — | 73 |
| V | 24 | — | 82 |

Spell ID: `resonant_arcana:blood_explode`. Inspect ranks with `/resonantarcana spell blood_explode <rank>`.

### Umbral Wave

Advance a broad curtain of shadow along your aim. Each enemy touched by the moving front takes one hit and Weakness I for three seconds.

**Ender** · Cast **0.35s** · Cooldown **3.25s** · Range **28 blocks** · Radius / half-width **2.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:dark_wave`. Inspect ranks with `/resonantarcana spell dark_wave <rank>`.

### Nightfall

Collapse a cloud of shadow at your aim. Visible enemies in the burst take one hit, Weakness I for three seconds and brief Darkness.

**Ender** · Cast **1s** · Cooldown **7s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 46 |
| II | 15 | — | 55 |
| III | 18 | — | 64 |
| IV | 21 | — | 73 |
| V | 24 | — | 82 |

Spell ID: `resonant_arcana:nightfall`. Inspect ranks with `/resonantarcana spell nightfall <rank>`.

### Umbral Veil

Carry a dark soul aura with you for three seconds. Three one-second pulses damage visible nearby enemies and apply Weakness I for three seconds.

**Ender** · Cast **0.5s** · Cooldown **8s** · Range **0 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 30 |
| II | 3.75 | — | 36 |
| III | 4.5 | — | 42 |
| IV | 5.25 | — | 48 |
| V | 6 | — | 54 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:soul_aura`. Inspect ranks with `/resonantarcana spell soul_aura <rank>`.

### Thunder Wave

Send a broad, crackling electric front forwards. Each enemy crossed by its moving rim takes one hit.

**Lightning** · Cast **0.35s** · Cooldown **3.25s** · Range **30 blocks** · Radius / half-width **2.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:thunder_wave`. Inspect ranks with `/resonantarcana spell thunder_wave <rank>`.

### Storm Call

Establish a lightning storm at your aim for 4.8 seconds. Five one-second pulses damage visible enemies; additional lightning columns show the active storm area.

**Lightning** · Cast **1.6s** · Cooldown **12s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 70 |
| II | 6.25 | — | 80 |
| III | 7.5 | — | 90 |
| IV | 8.75 | — | 100 |
| V | 10 | — | 110 |

Base release / active phase: **4.8s**.

Spell ID: `resonant_arcana:storm_call`. Inspect ranks with `/resonantarcana spell storm_call <rank>`.

### Thunder Pulse

Unleash an expanding electric ring from your feet. The moving rim damages and pushes each visible enemy once, with sparks tracing its current reach.

**Lightning** · Cast **0.7s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **9 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 43 |
| II | 12.5 | — | 51 |
| III | 15 | — | 59 |
| IV | 17.5 | — | 67 |
| V | 20 | — | 75 |

Spell ID: `resonant_arcana:thunder_pulse`. Inspect ranks with `/resonantarcana spell thunder_pulse <rank>`.

### Rift Well

Open a stationary gravity well for 4.8 seconds. Pull visible enemies horizontally toward its centre and deal five one-second area pulses; walls block damage and pulling.

**Ender** · Cast **1.25s** · Cooldown **9s** · Range **28 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 55 |
| II | 3.75 | — | 65 |
| III | 4.5 | — | 75 |
| IV | 5.25 | — | 85 |
| V | 6 | — | 95 |

Base release / active phase: **4.8s**.

Spell ID: `resonant_arcana:gravity_well`. Inspect ranks with `/resonantarcana spell gravity_well <rank>`.

### Singularity

Collapse a concentrated spatial orb at your aim. One radial force burst damages and pushes visible enemies; this instant spell leaves no lingering gravity well.

**Ender** · Cast **1.6s** · Cooldown **9s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 70 |
| II | 22.5 | — | 80 |
| III | 27 | — | 90 |
| IV | 31.5 | — | 100 |
| V | 36 | — | 110 |

Spell ID: `resonant_arcana:singularity`. Inspect ranks with `/resonantarcana spell singularity <rank>`.

### Sculk Wave

Roll a broad sculk resonance front forwards. Each visible enemy crossed by the advancing wave takes one hit.

**Eldritch** · Cast **0.35s** · Cooldown **3.25s** · Range **28 blocks** · Radius / half-width **2.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 24 |
| II | 6.25 | — | 28 |
| III | 7.5 | — | 32 |
| IV | 8.75 | — | 36 |
| V | 10 | — | 40 |

Spell ID: `resonant_arcana:sculk_wave`. Inspect ranks with `/resonantarcana spell sculk_wave <rank>`.

### Sonic Scream

Charge for one second, then unleash a deafening, wide sonic scream over 12 blocks. Cover blocks the blast.

**Eldritch** · Cast **1s** · Cooldown **8s** · Range **12 blocks** · Radius / half-width **12 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 55 |
| II | 17.5 | — | 65 |
| III | 21 | — | 75 |
| IV | 24.5 | — | 85 |
| V | 28 | — | 95 |

Spell ID: `resonant_arcana:sonic_scream`. Inspect ranks with `/resonantarcana spell sonic_scream <rank>`.

### Shrieking Pulse

Release a radial sculk shriek. An expanding soul ring damages and pushes each visible enemy once as its rim passes them.

**Eldritch** · Cast **0.8s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **9 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 41 |
| II | 11.25 | — | 49 |
| III | 13.5 | — | 57 |
| IV | 15.75 | — | 65 |
| V | 18 | — | 73 |

Spell ID: `resonant_arcana:shrieking_pulse`. Inspect ranks with `/resonantarcana spell shrieking_pulse <rank>`.

### Amethyst Nova

Shatter a resonant amethyst bloom at your aim. Crystal motes radiate from one area burst, damaging and pushing visible enemies in range.

**Eldritch** · Cast **1.2s** · Cooldown **8s** · Range **28 blocks** · Radius / half-width **5.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 55 |
| II | 17.5 | — | 65 |
| III | 21 | — | 75 |
| IV | 24.5 | — | 85 |
| V | 28 | — | 95 |

Spell ID: `resonant_arcana:amethyst_nova`. Inspect ranks with `/resonantarcana spell amethyst_nova <rank>`.

### Ember Veil

Carry a low ring of embers for three seconds. Three one-second pulses damage visible nearby enemies and ignite them for three seconds.

**Fire** · Cast **0.5s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 27 |
| II | 2.5 | — | 32 |
| III | 3 | — | 37 |
| IV | 3.5 | — | 42 |
| V | 4 | — | 47 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:ember_veil`. Inspect ranks with `/resonantarcana spell ember_veil <rank>`.

### Miasma Field

Leave a low poisonous cloud at your aim for 4.8 seconds. Five one-second pulses damage visible enemies inside and refresh Poison I for four seconds.

**Nature** · Cast **0.75s** · Cooldown **9s** · Range **24 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 50 |
| II | 3.75 | — | 60 |
| III | 4.5 | — | 70 |
| IV | 5.25 | — | 80 |
| V | 6 | — | 90 |

Base release / active phase: **4.8s**.

Spell ID: `resonant_arcana:miasma_field`. Inspect ranks with `/resonantarcana spell miasma_field <rank>`.

### Solar Cataclysm

Charge for three seconds, mark your aim, then drop a blazing meteor. Ceilings limit its drop. Fire hits ignite for three seconds.

**Fire** · Cast **3s** · Cooldown **20s** · Range **40 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 32 | — | 90 |
| II | 40 | — | 100 |
| III | 48 | — | 110 |
| IV | 56 | — | 120 |
| V | 64 | — | 130 |

Spell ID: `resonant_arcana:solar_cataclysm`. Inspect ranks with `/resonantarcana spell solar_cataclysm <rank>`.

### Event Horizon

Charge for 3.2 seconds, then pull visible enemies inward for 1.5 seconds before a crushing detonation.

**Ender** · Cast **3.2s** · Cooldown **24s** · Range **32 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 36 | — | 100 |
| II | 45 | — | 110 |
| III | 54 | — | 120 |
| IV | 63 | — | 130 |
| V | 72 | — | 140 |

Base release / active phase: **1.5s**.

Spell ID: `resonant_arcana:event_horizon`. Inspect ranks with `/resonantarcana spell event_horizon <rank>`.

### Glacial Rupture

Send a broad glacial front forwards, damaging and chilling each visible enemy once. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Ice** · Cast **2.5s** · Cooldown **18s** · Range **18 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 28 | — | 85 |
| II | 35 | — | 95 |
| III | 42 | — | 105 |
| IV | 49 | — | 115 |
| V | 56 | — | 125 |

Spell ID: `resonant_arcana:glacial_rupture`. Inspect ranks with `/resonantarcana spell glacial_rupture <rank>`.

### Gravity Stamp

Mark your aim for one second, then crush visible enemies with a vertical spatial impact.

**Ender** · Cast **1.4s** · Cooldown **11s** · Range **28 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 55 |
| II | 22.5 | — | 65 |
| III | 27 | — | 75 |
| IV | 31.5 | — | 85 |
| V | 36 | — | 95 |

Base release / active phase: **1s**.

Spell ID: `resonant_arcana:gravity_stamp`. Inspect ranks with `/resonantarcana spell gravity_stamp <rank>`.

### Bramble Ring

Expand a ring of thorns around yourself. Each visible enemy is damaged once, poisoned and briefly slowed. Nature hits poison for four seconds.

**Nature** · Cast **0.7s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 40 |
| II | 6.25 | — | 48 |
| III | 7.5 | — | 56 |
| IV | 8.75 | — | 64 |
| V | 10 | — | 72 |

Spell ID: `resonant_arcana:bramble_ring`. Inspect ranks with `/resonantarcana spell bramble_ring <rank>`.

### Corrosive Rain

Call an acid cloud at your aim. Five falling pulses damage and poison visible enemies over four seconds. Nature hits poison for four seconds.

**Nature** · Cast **1.4s** · Cooldown **12s** · Range **28 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 60 |
| II | 2.5 | — | 70 |
| III | 3 | — | 80 |
| IV | 3.5 | — | 90 |
| V | 4 | — | 100 |

Base release / active phase: **4.05s**.

Spell ID: `resonant_arcana:corrosive_rain`. Inspect ranks with `/resonantarcana spell corrosive_rain <rank>`.

### Seraphic Descent

Mark your aim for one second, then deliver a pillar of light. It damages enemies and restores 6 health at level I to eligible allies at the impact.

**Holy** · Cast **2.5s** · Cooldown **20s** · Range **32 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 28 | 6 | 90 |
| II | 35 | 7.5 | 100 |
| III | 42 | 9 | 110 |
| IV | 49 | 10.5 | 120 |
| V | 56 | 12 | 130 |

Base release / active phase: **1s**.

Spell ID: `resonant_arcana:seraphic_descent`. Inspect ranks with `/resonantarcana spell seraphic_descent <rank>`.

### Rime Nova

Expand a frozen ring around yourself, chilling and damaging each visible enemy once. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Ice** · Cast **0.9s** · Cooldown **8s** · Range **0 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 44 |
| II | 11.25 | — | 52 |
| III | 13.5 | — | 60 |
| IV | 15.75 | — | 68 |
| V | 18 | — | 76 |

Spell ID: `resonant_arcana:rime_nova`. Inspect ranks with `/resonantarcana spell rime_nova <rank>`.

### Hailstorm

Call a hail cloud at your aim. Five pulses damage and chill visible enemies over four seconds. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Ice** · Cast **1.4s** · Cooldown **12s** · Range **28 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2.5 | — | 60 |
| II | 3.125 | — | 70 |
| III | 3.75 | — | 80 |
| IV | 4.375 | — | 90 |
| V | 5 | — | 100 |

Base release / active phase: **4.05s**.

Spell ID: `resonant_arcana:hailstorm`. Inspect ranks with `/resonantarcana spell hailstorm <rank>`.

### Absolute Zero

Establish a frozen domain for four seconds. Four pulses chill and freeze visible enemies in its area. Ice hits chill for two seconds and add 40 freezing ticks, capped at 240.

**Ice** · Cast **2.5s** · Cooldown **20s** · Range **28 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 85 |
| II | 3.75 | — | 95 |
| III | 4.5 | — | 105 |
| IV | 5.25 | — | 115 |
| V | 6 | — | 125 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:absolute_zero`. Inspect ranks with `/resonantarcana spell absolute_zero <rank>`.

### Soulblast

Blast a forward cone of enemies away. Aim sharply downward to launch yourself with brief Slow Falling. While gliding, boost your Elytra along your aim instead.

**Eldritch** · Cast **0s** · Cooldown **5s** · Range **8 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 25 |
| II | 7.5 | — | 30 |
| III | 9 | — | 35 |
| IV | 10.5 | — | 40 |
| V | 12 | — | 45 |

Spell ID: `resonant_arcana:soulblast`. Inspect ranks with `/resonantarcana spell soulblast <rank>`.

### Funeral Bell

Charge a funeral bell. Two expanding sonic rings deal the listed damage; a third toll deals triple damage. Each ring strikes an enemy at most once, briefly slowing and pushing it. Walls block the waves.

**Eldritch** · Cast **3s** · Cooldown **21s** · Range **0 blocks** · Radius / half-width **9 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 95 |
| II | 10 | — | 105 |
| III | 12 | — | 115 |
| IV | 14 | — | 125 |
| V | 16 | — | 135 |

Base release / active phase: **3.1s**.

Spell ID: `resonant_arcana:funeral_bell`. Inspect ranks with `/resonantarcana spell funeral_bell <rank>`.


<a id="control"></a>
## Control

### Mind Haze

Wrap one visible aimed enemy in a spiralling hex. Apply Nausea for five seconds and Blindness for two seconds, with one direct hit.

**Evocation** · Cast **0.4s** · Cooldown **5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 18 |
| II | — | — | 21 |
| III | — | — | 24 |
| IV | — | — | 27 |
| V | — | — | 30 |

Spell ID: `resonant_arcana:confuse`. Inspect ranks with `/resonantarcana spell confuse <rank>`.

### Stun

Bind one visible aimed enemy with arcane sparks. Apply Slowness VI and Weakness II for three seconds, with one direct hit; immune enemies may resist the effects.

**Evocation** · Cast **0.4s** · Cooldown **5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 1 | — | 20 |
| II | 1.25 | — | 24 |
| III | 1.5 | — | 28 |
| IV | 1.75 | — | 32 |
| V | 2 | — | 36 |

Spell ID: `resonant_arcana:stun`. Inspect ranks with `/resonantarcana spell stun <rank>`.

### Arcane Bind

Wind an arcane cage around one aimed enemy for three seconds at level I. Suppress movement and melee damage while the link holds. Cover, range or displacement of more than four blocks breaks it.

**Evocation** · Cast **0.6s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 24 |
| II | — | — | 28 |
| III | — | — | 32 |
| IV | — | — | 36 |
| V | — | — | 40 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:capture`. Inspect ranks with `/resonantarcana spell capture <rank>`.

### Launch

Send a rising arcane strike into one visible aimed enemy, adding upward velocity. This lifts the target; it does not grant the enemy fall protection.

**Evocation** · Cast **0s** · Cooldown **3.5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 19 |
| II | 2.5 | — | 22 |
| III | 3 | — | 25 |
| IV | 3.5 | — | 28 |
| V | 4 | — | 31 |

Spell ID: `resonant_arcana:launch`. Inspect ranks with `/resonantarcana spell launch <rank>`.

### Hemorrhagic Shock

Close a crimson binding around one visible aimed enemy. One hit applies Slowness VI and Weakness II for three seconds; this spell does not drain health.

**Blood** · Cast **0.4s** · Cooldown **5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 22 |
| II | 2.5 | — | 26 |
| III | 3 | — | 30 |
| IV | 3.5 | — | 34 |
| V | 4 | — | 38 |

Spell ID: `resonant_arcana:blood_stun`. Inspect ranks with `/resonantarcana spell blood_stun <rank>`.

### Dread Mark

Fasten an orbiting shadow curse to one aimed enemy. After two seconds it bursts around that enemy, damaging and weakening nearby enemies. Losing line of sight or exceeding the link range breaks the curse.

**Ender** · Cast **0.5s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **3.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 52 |
| II | 17.5 | — | 62 |
| III | 21 | — | 72 |
| IV | 24.5 | — | 82 |
| V | 28 | — | 92 |

Base release / active phase: **2s**.

Spell ID: `resonant_arcana:shadow_cage`. Inspect ranks with `/resonantarcana spell shadow_cage <rank>`.

### Static Shock

Overload one visible aimed enemy with static. One direct hit applies Slowness VI and Weakness II for three seconds; it is a slowing debuff rather than an AI freeze.

**Lightning** · Cast **0.4s** · Cooldown **4s** · Range **28 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 22 |
| II | 3.75 | — | 26 |
| III | 4.5 | — | 30 |
| IV | 5.25 | — | 34 |
| V | 6 | — | 38 |

Spell ID: `resonant_arcana:static_shock`. Inspect ranks with `/resonantarcana spell static_shock <rank>`.

### Force Push

Strike one visible aimed enemy with an outward spatial impulse. Deal direct damage and launch it away from the caster with a small upward lift.

**Ender** · Cast **0s** · Cooldown **3.25s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 18 |
| II | 2.5 | — | 21 |
| III | 3 | — | 24 |
| IV | 3.5 | — | 27 |
| V | 4 | — | 30 |

Spell ID: `resonant_arcana:force_push`. Inspect ranks with `/resonantarcana spell force_push <rank>`.

### Force Pull

Catch one visible aimed enemy with a spatial link. Deal direct damage and pull it horizontally toward you with a small upward lift.

**Ender** · Cast **0s** · Cooldown **3.25s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 18 |
| II | 2.5 | — | 21 |
| III | 3 | — | 24 |
| IV | 3.5 | — | 27 |
| V | 4 | — | 30 |

Spell ID: `resonant_arcana:force_pull`. Inspect ranks with `/resonantarcana spell force_pull <rank>`.

### Suspension

Suspend one visible aimed enemy above its starting point for two seconds. Deal one hit, slow it and briefly grant Slow Falling. Cover, range or displacement beyond four blocks breaks the hold.

**Ender** · Cast **0.6s** · Cooldown **7s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 26 |
| II | 2.5 | — | 31 |
| III | 3 | — | 36 |
| IV | 3.5 | — | 41 |
| V | 4 | — | 46 |

Base release / active phase: **2s**.

Spell ID: `resonant_arcana:gravity_choke`. Inspect ranks with `/resonantarcana spell gravity_choke <rank>`.

### Soul Prison

Imprison one aimed enemy in a winding soul cage for three seconds at level I. Three one-second hits apply Darkness while the hold suppresses movement. Cover, range or displacement breaks the link.

**Eldritch** · Cast **0.6s** · Cooldown **7s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 26 |
| II | 2.5 | — | 31 |
| III | 3 | — | 36 |
| IV | 3.5 | — | 41 |
| V | 4 | — | 46 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:soul_prison`. Inspect ranks with `/resonantarcana spell soul_prison <rank>`.

### Crimson Tether

Tether an aimed enemy for 3 seconds, gently pulling it and dealing three damage pulses. Cover or moving out of range breaks the link.

**Blood** · Cast **0.8s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 42 |
| II | 2.5 | — | 50 |
| III | 3 | — | 58 |
| IV | 3.5 | — | 66 |
| V | 4 | — | 74 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:crimson_tether`. Inspect ranks with `/resonantarcana spell crimson_tether <rank>`.

### Thorn Lash

Catch an aimed enemy with a thorn tether, pull it towards you and poison it. Nature hits poison for four seconds.

**Nature** · Cast **0.5s** · Cooldown **5.5s** · Range **20 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 30 |
| II | 5 | — | 36 |
| III | 6 | — | 42 |
| IV | 7 | — | 48 |
| V | 8 | — | 54 |

Spell ID: `resonant_arcana:thorn_lash`. Inspect ranks with `/resonantarcana spell thorn_lash <rank>`.

### Gorgon Gaze

Fix visible enemies in a wide forward cone with a petrifying gaze, applying heavy slowness and weakness for two seconds at level I. Nature hits poison for four seconds.

**Nature** · Cast **1.2s** · Cooldown **10s** · Range **10 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 48 |
| II | 2.5 | — | 57 |
| III | 3 | — | 66 |
| IV | 3.5 | — | 75 |
| V | 4 | — | 84 |

Base release / active phase: **2s**.

Spell ID: `resonant_arcana:gorgon_gaze`. Inspect ranks with `/resonantarcana spell gorgon_gaze <rank>`.

### Glacial Prison

Hold an aimed enemy in spectral ice for three seconds at level I. Cover, distance or large movement breaks the restraint.

**Ice** · Cast **1s** · Cooldown **9s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 45 |
| II | — | — | 54 |
| III | — | — | 63 |
| IV | — | — | 72 |
| V | — | — | 81 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:glacial_prison`. Inspect ranks with `/resonantarcana spell glacial_prison <rank>`.


<a id="mobility"></a>
## Movement

### Leap

Vault forwards and upwards in an arcane wake. This is a movement spell with no direct damage; choose a safe landing.

**Evocation** · Cast **0s** · Cooldown **4s** · Range **0 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 16 |
| II | — | — | 19 |
| III | — | — | 22 |
| IV | — | — | 25 |
| V | — | — | 28 |

Spell ID: `resonant_arcana:leap`. Inspect ranks with `/resonantarcana spell leap <rank>`.

### Arcane Step

Blink along your aim to a clear, loaded position. Range grows by 1 block per additional spell level, up to 12 blocks.

**Evocation** · Cast **0s** · Cooldown **5s** · Range **8 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 24 |
| II | — | — | 28 |
| III | — | — | 32 |
| IV | — | — | 36 |
| V | — | — | 40 |

Spell ID: `resonant_arcana:arcane_step`. Inspect ranks with `/resonantarcana spell arcane_step <rank>`.

### Cinder Step

Blink through a safe line of sight in a burst of embers. Range grows by 1 block per additional spell level.

**Fire** · Cast **0s** · Cooldown **6s** · Range **8 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 28 |
| II | — | — | 33 |
| III | — | — | 38 |
| IV | — | — | 43 |
| V | — | — | 48 |

Spell ID: `resonant_arcana:cinder_step`. Inspect ranks with `/resonantarcana spell cinder_step <rank>`.

### Phoenix Rush

Launch forwards in a rising rush and hit nearby enemies in a four-block launch burst. Slow Falling protects the short descent. Fire hits ignite for three seconds.

**Fire** · Cast **0.8s** · Cooldown **9s** · Range **8 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 45 |
| II | 15 | — | 54 |
| III | 18 | — | 63 |
| IV | 21 | — | 72 |
| V | 24 | — | 81 |

Spell ID: `resonant_arcana:phoenix_rush`. Inspect ranks with `/resonantarcana spell phoenix_rush <rank>`.

### Umbral Step

Blink to a clear position and become invisible for one second. Higher levels extend blink range.

**Ender** · Cast **0s** · Cooldown **7s** · Range **10 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 30 |
| II | — | — | 36 |
| III | — | — | 42 |
| IV | — | — | 48 |
| V | — | — | 54 |

Spell ID: `resonant_arcana:umbral_step`. Inspect ranks with `/resonantarcana spell umbral_step <rank>`.

### Storm Step

Surge forwards and upwards with lightning afterimages. Slow Falling protects the short descent.

**Lightning** · Cast **0s** · Cooldown **5s** · Range **9 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

Spell ID: `resonant_arcana:storm_step`. Inspect ranks with `/resonantarcana spell storm_step <rank>`.

### Rift Blink

Blink along a clear path without crossing solid cover or loading terrain. Higher levels increase its range.

**Ender** · Cast **0s** · Cooldown **6s** · Range **10 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 28 |
| II | — | — | 33 |
| III | — | — | 38 |
| IV | — | — | 43 |
| V | — | — | 48 |

Spell ID: `resonant_arcana:rift_blink`. Inspect ranks with `/resonantarcana spell rift_blink <rank>`.

### Pilgrim Step

Move along your aim in a flash of light, stopping before obstacles. Higher levels extend the safe blink range.

**Holy** · Cast **0s** · Cooldown **6s** · Range **8 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

Spell ID: `resonant_arcana:pilgrim_step`. Inspect ranks with `/resonantarcana spell pilgrim_step <rank>`.

### Glacier Step

Blink safely in a burst of snowflakes. Higher levels increase the range without allowing travel through walls.

**Ice** · Cast **0s** · Cooldown **6s** · Range **8 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

Spell ID: `resonant_arcana:glacier_step`. Inspect ranks with `/resonantarcana spell glacier_step <rank>`.

### Crimson Reversal

Blink forward with a blood slash along the clear path, adding half your melee attack damage. Recast within three seconds to return safely to your starting position for no extra mana. The normal cooldown begins when the return is used or expires.

**Blood** · Cast **0.5s** · Cooldown **10s** · Range **8 blocks** · Radius / half-width **1.4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 35 |
| II | 12.5 | — | 40 |
| III | 15 | — | 45 |
| IV | 17.5 | — | 50 |
| V | 20 | — | 55 |

Weapon contribution: add half the melee attack attribute at release before spell-power scaling.

Spell ID: `resonant_arcana:crimson_reversal`. Inspect ranks with `/resonantarcana spell crimson_reversal <rank>`.

### Ashen Rebirth

Burst forward along a clear path as a phoenix, leaving a burning silhouette. After 0.8 seconds the silhouette detonates. Grants brief Slow Falling; the movement itself deals no damage.

**Fire** · Cast **0.6s** · Cooldown **12s** · Range **8 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 45 |
| II | 22.5 | — | 51 |
| III | 27 | — | 57 |
| IV | 31.5 | — | 63 |
| V | 36 | — | 69 |

Base release / active phase: **1.2s**.

Spell ID: `resonant_arcana:ashen_rebirth`. Inspect ranks with `/resonantarcana spell ashen_rebirth <rank>`.


<a id="support"></a>
## Support & Defense

### Blood Ward

Deflect incoming hostile projectiles for 4 seconds at level I. Higher spell power extends the duration, up to 8 seconds.

**Blood** · Cast **0.5s** · Cooldown **9s** · Range **0 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:blood_block`. Inspect ranks with `/resonantarcana spell blood_block <rank>`.

### Echo Ward

Deflect incoming hostile projectiles for 4 seconds at level I. Higher spell power extends the duration, up to 8 seconds.

**Eldritch** · Cast **0.5s** · Cooldown **9s** · Range **0 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 26 |
| II | — | — | 31 |
| III | — | — | 36 |
| IV | — | — | 41 |
| V | — | — | 46 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:echo_ward`. Inspect ranks with `/resonantarcana spell echo_ward <rank>`.

### Sanctuary

Restore up to 12 health to yourself and eligible allies within eight blocks; cleanse poison, wither, blindness and darkness. Allies include owned pets, scoreboard teammates, and teamless players when enabled.

**Holy** · Cast **2.5s** · Cooldown **18s** · Range **0 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 12 | 80 |
| II | — | 15 | 90 |
| III | — | 18 | 100 |
| IV | — | 21 | 110 |
| V | — | 24 | 120 |

Spell ID: `resonant_arcana:sanctuary`. Inspect ranks with `/resonantarcana spell sanctuary <rank>`.

### Sanguine Pact

Spend 2 health to gain 4 absorption health and Strength I for 6 seconds at level I. Requires more than 2 current health. Absorption and duration grow with spell power.

**Blood** · Cast **0.7s** · Cooldown **12s** · Range **0 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 40 |
| II | — | — | 48 |
| III | — | — | 56 |
| IV | — | — | 64 |
| V | — | — | 72 |

Base release / active phase: **6s**.

Spell ID: `resonant_arcana:sanguine_pact`. Inspect ranks with `/resonantarcana spell sanguine_pact <rank>`.

### Veil of Night

Gain invisibility and Speed I for six seconds at level I. Spell power extends the duration, up to twelve seconds.

**Ender** · Cast **0.9s** · Cooldown **13s** · Range **0 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 45 |
| II | — | — | 54 |
| III | — | — | 63 |
| IV | — | — | 72 |
| V | — | — | 81 |

Base release / active phase: **6s**.

Spell ID: `resonant_arcana:veil_of_night`. Inspect ranks with `/resonantarcana spell veil_of_night <rank>`.

### Resonant Guard

Gain 6 absorption health and Resistance I for five seconds at level I. Spell power improves absorption and duration.

**Eldritch** · Cast **0.9s** · Cooldown **12s** · Range **0 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 44 |
| II | — | — | 52 |
| III | — | — | 60 |
| IV | — | — | 68 |
| V | — | — | 76 |

Base release / active phase: **5s**.

Spell ID: `resonant_arcana:resonant_guard`. Inspect ranks with `/resonantarcana spell resonant_guard <rank>`.

### Toxic Molt

Shed poison and wither, restore 4 health at level I and gain Speed I for four seconds. Healing and duration scale with spell power.

**Nature** · Cast **0.8s** · Cooldown **11s** · Range **0 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 4 | 38 |
| II | — | 5 | 45 |
| III | — | 6 | 52 |
| IV | — | 7 | 59 |
| V | — | 8 | 66 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:toxic_molt`. Inspect ranks with `/resonantarcana spell toxic_molt <rank>`.

### Mending Light

Restore 6 health at level I to the most injured eligible ally within eight blocks, including yourself. Requires line of sight.

**Holy** · Cast **0.9s** · Cooldown **6s** · Range **0 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 6 | 32 |
| II | — | 7.5 | 38 |
| III | — | 9 | 44 |
| IV | — | 10.5 | 50 |
| V | — | 12 | 56 |

Spell ID: `resonant_arcana:mending_light`. Inspect ranks with `/resonantarcana spell mending_light <rank>`.

### Blessing of Vigor

Gain 4 absorption health and Speed I for six seconds at level I. Spell power increases the shield and duration.

**Holy** · Cast **0.9s** · Cooldown **12s** · Range **0 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 42 |
| II | — | — | 50 |
| III | — | — | 58 |
| IV | — | — | 66 |
| V | — | — | 74 |

Base release / active phase: **6s**.

Spell ID: `resonant_arcana:blessing_of_vigor`. Inspect ranks with `/resonantarcana spell blessing_of_vigor <rank>`.

### Absolution

Cleanse poison, wither, blindness and darkness from eligible allies within eight blocks and grant brief Resistance I.

**Holy** · Cast **1s** · Cooldown **10s** · Range **0 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 40 |
| II | — | — | 48 |
| III | — | — | 56 |
| IV | — | — | 64 |
| V | — | — | 72 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:absolution`. Inspect ranks with `/resonantarcana spell absolution <rank>`.

### Dawn Ward

Deflect hostile incoming projectiles for four seconds at level I. Spell power extends the ward, up to eight seconds.

**Holy** · Cast **0.8s** · Cooldown **10s** · Range **0 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 42 |
| II | — | — | 50 |
| III | — | — | 58 |
| IV | — | — | 66 |
| V | — | — | 74 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:dawn_ward`. Inspect ranks with `/resonantarcana spell dawn_ward <rank>`.

### Renewal Hymn

Carry a healing hymn around yourself for four seconds. Four pulses each restore 2 health at level I to eligible allies with line of sight.

**Holy** · Cast **1.2s** · Cooldown **13s** · Range **0 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 2 | 55 |
| II | — | 2.5 | 65 |
| III | — | 3 | 75 |
| IV | — | 3.5 | 85 |
| V | — | 4 | 95 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:renewal_hymn`. Inspect ranks with `/resonantarcana spell renewal_hymn <rank>`.

### Frozen Aegis

Gain 6 absorption health and Resistance I for five seconds at level I. Spell power strengthens and extends the aegis.

**Ice** · Cast **0.9s** · Cooldown **12s** · Range **0 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 44 |
| II | — | — | 52 |
| III | — | — | 60 |
| IV | — | — | 68 |
| V | — | — | 76 |

Base release / active phase: **5s**.

Spell ID: `resonant_arcana:frozen_aegis`. Inspect ranks with `/resonantarcana spell frozen_aegis <rank>`.

### Warscream

Unleash three ground-shaking roars over eight blocks. Each pulse damages visible enemies; the roar draws them closer and taunts hostile mobs. You and nearby eligible allies gain Resistance I. Taunt and defense last 3/4/5/6/7 seconds at levels I–V.

**Holy** · Cast **0.6s** · Cooldown **20s** · Range **8 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 45 |
| II | 3.75 | — | 50 |
| III | 4.5 | — | 55 |
| IV | 5.25 | — | 60 |
| V | 6 | — | 65 |

Base release / active phase: **8s**.

Resistance I and hostile-mob taunt last **3/4/5/6/7 seconds** at ranks I–V. Three damage pulses. No offensive damage buff or absorption shield.

Spell ID: `resonant_arcana:warscream`. Inspect ranks with `/resonantarcana spell warscream <rank>`.

### Arcane Edge

Empower yourself with flat melee attack damage and faster attacks for 8–16 seconds. Refreshes the existing enchantment.

**Evocation** · Cast **0.4s** · Cooldown **20s** · Range **0 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 35 |
| II | — | — | 40 |
| III | — | — | 45 |
| IV | — | — | 50 |
| V | — | — | 55 |

Base release / active phase: **8s**.

Spell ID: `resonant_arcana:arcane_edge`. Inspect ranks with `/resonantarcana spell arcane_edge <rank>`.

### Bulwark Chant

Grant visible nearby allies Resistance I and temporary health for 8–16 seconds. Shields refresh rather than stack.

**Holy** · Cast **0.8s** · Cooldown **20s** · Range **8 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 40 |
| II | — | — | 45 |
| III | — | — | 50 |
| IV | — | — | 55 |
| V | — | — | 60 |

Base release / active phase: **8s**.

Spell ID: `resonant_arcana:bulwark_chant`. Inspect ranks with `/resonantarcana spell bulwark_chant <rank>`.

### Battle Tempo

A rallying rhythm grants visible nearby allies faster attacks and movement for 8–16 seconds.

**Lightning** · Cast **0.4s** · Cooldown **20s** · Range **8 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 35 |
| II | — | — | 40 |
| III | — | — | 45 |
| IV | — | — | 50 |
| V | — | — | 55 |

Base release / active phase: **8s**.

Spell ID: `resonant_arcana:battle_tempo`. Inspect ranks with `/resonantarcana spell battle_tempo <rank>`.

### Glacial Counter

Guard for 0.9 seconds against one frontal, blockable hostile hit. The first 0.3 seconds block fully and return a 175% frost counter; later guards reduce damage by 70% and return a normal counter. Environmental damage and bypassing attacks are not blocked.

**Ice** · Cast **0s** · Cooldown **8s** · Range **6 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 30 |
| II | 15 | — | 34 |
| III | 18 | — | 38 |
| IV | 21 | — | 42 |
| V | 24 | — | 46 |

Base release / active phase: **0.9s**.

Spell ID: `resonant_arcana:glacial_counter`. Inspect ranks with `/resonantarcana spell glacial_counter <rank>`.

### Thunderbrand

Enchant your weapon for ten seconds. Every third successful player melee hit releases chain lightning to that enemy and up to two nearby enemies. Spell damage cannot build charges; recasting refreshes the enchantment without stacking it.

**Lightning** · Cast **0.8s** · Cooldown **16s** · Range **16 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 55 |
| II | 10 | — | 61 |
| III | 12 | — | 67 |
| IV | 14 | — | 73 |
| V | 16 | — | 79 |

Base release / active phase: **10s**.

Spell ID: `resonant_arcana:thunderbrand`. Inspect ranks with `/resonantarcana spell thunderbrand <rank>`.

### Seraph Ascendant

Unfurl radiant wings and gain flight for twenty seconds. Double-jump to fly. Nearby allies receive 15% more spell power and one base health per second while in your eight-block aura. Ending the spell grants brief Slow Falling.

**Holy** · Cast **4s** · Cooldown **60s** · Range **0 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 1 | 160 |
| II | — | 1.25 | 180 |
| III | — | 1.5 | 200 |
| IV | — | 1.75 | 220 |
| V | — | 2 | 240 |

Base release / active phase: **20s**.

Flight lasts twenty seconds at every rank. Healing scales with spell power; the nearby spell-power blessing stays at +15%. Flight is removed on cleanup, with four seconds of Slow Falling. Other sources of native flight are preserved.

Spell ID: `resonant_arcana:seraph_ascendant`. Inspect ranks with `/resonantarcana spell seraph_ascendant <rank>`.


<a id="summon"></a>
## Summons & Constructs

### Evoker's Edict

Send three staggered rows of spectral fangs along your aim. Each enemy takes one full hit.

**Evocation** · Cast **2.4s** · Cooldown **16s** · Range **24 blocks** · Radius / half-width **1.6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 26 | — | 80 |
| II | 32.5 | — | 90 |
| III | 39 | — | 100 |
| IV | 45.5 | — | 110 |
| V | 52 | — | 120 |

Spell ID: `resonant_arcana:evokers_edict`. Inspect ranks with `/resonantarcana spell evokers_edict <rank>`.

### Dread Effigy

Place a blackstone effigy with a carved head and soul torch at your aim for four seconds. Four pulses damage and weaken visible enemies near it. Ender shadow hits weaken for three seconds. Pulses suppress nearby enemies with Weakness II for 1.5 seconds. Requires visible solid ground; the boundary ring shows its pulse radius.

**Ender** · Cast **1.2s** · Cooldown **11s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 55 |
| II | 2.5 | — | 65 |
| III | 3 | — | 75 |
| IV | 3.5 | — | 85 |
| V | 4 | — | 95 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:dread_effigy`. Inspect ranks with `/resonantarcana spell dread_effigy <rank>`.

### Night Bloom

Place a shadow trap. After a half-second arming period, an enemy within two blocks triggers one area explosion. Untriggered traps expire after four seconds. Ender shadow hits weaken for three seconds.

**Ender** · Cast **1.1s** · Cooldown **11s** · Range **24 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 52 |
| II | 20 | — | 62 |
| III | 24 | — | 72 |
| IV | 28 | — | 82 |
| V | 32 | — | 92 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:night_bloom`. Inspect ranks with `/resonantarcana spell night_bloom <rank>`.

### Orbital Blades

Orbit three spatial blades around yourself for three seconds. Enemies can take one hit per second, up to three hits.

**Ender** · Cast **1s** · Cooldown **10s** · Range **0 blocks** · Radius / half-width **3.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 48 |
| II | 5 | — | 57 |
| III | 6 | — | 66 |
| IV | 7 | — | 75 |
| V | 8 | — | 84 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:orbital_blades`. Inspect ranks with `/resonantarcana spell orbital_blades <rank>`.

### Wisp Lantern

Place a soul lantern on a deepslate post for four seconds. Four echo pulses damage visible enemies near it. Each pulse reveals enemies with a glowing outline for 1.5 seconds. Requires visible solid ground; the boundary ring shows its pulse radius.

**Eldritch** · Cast **1.2s** · Cooldown **11s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 52 |
| II | 2.5 | — | 62 |
| III | 3 | — | 72 |
| IV | 3.5 | — | 82 |
| V | 4 | — | 92 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:wisp_lantern`. Inspect ranks with `/resonantarcana spell wisp_lantern <rank>`.

### Spore Totem

Plant a mushroom totem with a glowing shroomlight cap for four seconds. Four pulses poison and damage visible enemies near it. Nature hits poison for four seconds. Requires visible solid ground; the boundary ring shows its pulse radius.

**Nature** · Cast **1.2s** · Cooldown **11s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 50 |
| II | 2.5 | — | 60 |
| III | 3 | — | 70 |
| IV | 3.5 | — | 80 |
| V | 4 | — | 90 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:spore_totem`. Inspect ranks with `/resonantarcana spell spore_totem <rank>`.

### Consecration

Place a quartz altar topped with an end rod for four seconds. Each of four pulses damages enemies and restores 1 health at level I to eligible allies in its area. Requires visible solid ground; the boundary ring shows its pulse radius.

**Holy** · Cast **1.2s** · Cooldown **12s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | 1 | 55 |
| II | 2.5 | 1.25 | 65 |
| III | 3 | 1.5 | 75 |
| IV | 3.5 | 1.75 | 85 |
| V | 4 | 2 | 95 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:consecration`. Inspect ranks with `/resonantarcana spell consecration <rank>`.

### Last Stand

Plant a temporary golden banner for six seconds, surrounded by a radiant ward circle. Nearby allies gain Resistance I and one grant of temporary health. The final pulse heals allies still defending the area. Re-entering does not refill the shield.

**Holy** · Cast **1.3s** · Cooldown **20s** · Range **10 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | 6 | 70 |
| II | — | 7.5 | 78 |
| III | — | 9 | 86 |
| IV | — | 10.5 | 94 |
| V | — | 12 | 102 |

Base release / active phase: **6s**.

Spell ID: `resonant_arcana:last_stand`. Inspect ranks with `/resonantarcana spell last_stand <rank>`.

### Phantom Formation

Summon four to eight spectral swords for ten seconds. Each successful player melee hit sends one sword toward the struck enemy. Swords have an arming flight, collide with walls and do not trigger further swords or Thunderbrand.

**Evocation** · Cast **1.2s** · Cooldown **17s** · Range **20 blocks** · Radius / half-width **0.45 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 60 |
| II | 10 | — | 67 |
| III | 12 | — | 74 |
| IV | 14 | — | 81 |
| V | 16 | — | 88 |

Base release / active phase: **10s**.

Spell ID: `resonant_arcana:phantom_formation`. Inspect ranks with `/resonantarcana spell phantom_formation <rank>`.


<a id="melee"></a>
## Strikes & Drains

### Arcane Strike

Focus arcane energy directly on one visible aimed enemy. A woven link identifies the struck target; terrain blocks targeting.

**Evocation** · Cast **0.25s** · Cooldown **1.75s** · Range **24 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 21 |
| II | 6.25 | — | 25 |
| III | 7.5 | — | 29 |
| IV | 8.75 | — | 33 |
| V | 10 | — | 37 |

Spell ID: `resonant_arcana:arcane_strike`. Inspect ranks with `/resonantarcana spell arcane_strike <rank>`.

### Crimson Strike

Snap a crimson spark onto one visible aimed enemy. Blood droplets mark the impact; this attack does not restore health.

**Blood** · Cast **0.25s** · Cooldown **1.75s** · Range **24 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 21 |
| II | 6.25 | — | 25 |
| III | 7.5 | — | 29 |
| IV | 8.75 | — | 33 |
| V | 10 | — | 37 |

Spell ID: `resonant_arcana:blood_spark`. Inspect ranks with `/resonantarcana spell blood_spark <rank>`.

### Venom Sting

Burst an acid bubble on one visible aimed enemy, dealing direct damage and applying Poison I for four seconds.

**Nature** · Cast **0.25s** · Cooldown **2.5s** · Range **24 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 17 |
| II | 2.5 | — | 20 |
| III | 3 | — | 23 |
| IV | 3.5 | — | 26 |
| V | 4 | — | 29 |

Spell ID: `resonant_arcana:poison_spark`. Inspect ranks with `/resonantarcana spell poison_spark <rank>`.

### Ignite

Kindle one visible aimed enemy directly. Deal the listed impact damage and set it burning for three seconds; ordinary fire immunity still applies.

**Fire** · Cast **0.25s** · Cooldown **2.5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 2 | — | 17 |
| II | 2.5 | — | 20 |
| III | 3 | — | 23 |
| IV | 3.5 | — | 26 |
| V | 4 | — | 29 |

Spell ID: `resonant_arcana:ignite`. Inspect ranks with `/resonantarcana spell ignite <rank>`.

### Soul Siphon

Damage a visible aimed target and restore 40% of health actually removed, capped at 2 health at level I. Healing scales with spell power.

**Eldritch** · Cast **0.7s** · Cooldown **5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 28 |
| II | 6.25 | — | 33 |
| III | 7.5 | — | 38 |
| IV | 8.75 | — | 43 |
| V | 10 | — | 48 |

Spell ID: `resonant_arcana:soul_siphon`. Inspect ranks with `/resonantarcana spell soul_siphon <rank>`.

### Sanguine Drain

Damage a visible aimed target and restore 40% of health actually removed, capped at 2 health at level I. Healing scales with spell power.

**Blood** · Cast **0.7s** · Cooldown **5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 28 |
| II | 6.25 | — | 33 |
| III | 7.5 | — | 38 |
| IV | 8.75 | — | 43 |
| V | 10 | — | 48 |

Spell ID: `resonant_arcana:sanguine_drain`. Inspect ranks with `/resonantarcana spell sanguine_drain <rank>`.

### Withering Hex

Inflict poison and wither on an aimed target. Nature hits poison for four seconds.

**Nature** · Cast **0.25s** · Cooldown **5.5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 25 |
| II | 3.75 | — | 30 |
| III | 4.5 | — | 35 |
| IV | 5.25 | — | 40 |
| V | 6 | — | 45 |

Spell ID: `resonant_arcana:withering_hex`. Inspect ranks with `/resonantarcana spell withering_hex <rank>`.

### Blood Reap

Sweep a crimson crescent across a 120-degree forward arc. Each visible enemy is hit once.

**Blood** · Cast **0.7s** · Cooldown **5.5s** · Range **6 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 34 |
| II | 11.25 | — | 40 |
| III | 13.5 | — | 46 |
| IV | 15.75 | — | 52 |
| V | 18 | — | 58 |

Spell ID: `resonant_arcana:blood_reap`. Inspect ranks with `/resonantarcana spell blood_reap <rank>`.

### Crimson Harvest

Strike an aimed enemy. Deal 50% more direct damage below 35% maximum health; a killing hit restores up to 2 health at level I.

**Blood** · Cast **1.1s** · Cooldown **9s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 48 |
| II | 15 | — | 57 |
| III | 18 | — | 66 |
| IV | 21 | — | 75 |
| V | 24 | — | 84 |

Spell ID: `resonant_arcana:crimson_harvest`. Inspect ranks with `/resonantarcana spell crimson_harvest <rank>`.

### Dusk Cleave

Carve a shadow crescent across the space in front of you, weakening enemies hit by the arc. Ender shadow hits weaken for three seconds.

**Ender** · Cast **0.7s** · Cooldown **6s** · Range **6 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 36 |
| II | 12.5 | — | 43 |
| III | 15 | — | 50 |
| IV | 17.5 | — | 57 |
| V | 20 | — | 64 |

Spell ID: `resonant_arcana:dusk_cleave`. Inspect ranks with `/resonantarcana spell dusk_cleave <rank>`.

### Thunderclap

Release a broad thunder crescent and knock visible enemies away. Each target is hit once.

**Lightning** · Cast **0.9s** · Cooldown **7s** · Range **8 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 11 | — | 42 |
| II | 13.75 | — | 50 |
| III | 16.5 | — | 58 |
| IV | 19.25 | — | 66 |
| V | 22 | — | 74 |

Spell ID: `resonant_arcana:thunderclap`. Inspect ranks with `/resonantarcana spell thunderclap <rank>`.

### Spatial Shear

Cut a wide crescent through nearby space, striking each visible enemy in front of you once.

**Ender** · Cast **0.9s** · Cooldown **7.5s** · Range **8 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 42 |
| II | 15 | — | 50 |
| III | 18 | — | 58 |
| IV | 21 | — | 66 |
| V | 24 | — | 74 |

Spell ID: `resonant_arcana:spatial_shear`. Inspect ranks with `/resonantarcana spell spatial_shear <rank>`.

### Memory Fracture

Strike an aimed enemy. If it is affected by Darkness, direct damage increases by 50%; combine with Soul Prison.

**Eldritch** · Cast **0.9s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 42 |
| II | 12.5 | — | 50 |
| III | 15 | — | 58 |
| IV | 17.5 | — | 66 |
| V | 20 | — | 74 |

Spell ID: `resonant_arcana:memory_fracture`. Inspect ranks with `/resonantarcana spell memory_fracture <rank>`.

### Radiant Sweep

Sweep a radiant crescent through visible enemies in front of you.

**Holy** · Cast **0.6s** · Cooldown **5.5s** · Range **6 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 30 |
| II | 10 | — | 36 |
| III | 12 | — | 42 |
| IV | 14 | — | 48 |
| V | 16 | — | 54 |

Spell ID: `resonant_arcana:radiant_sweep`. Inspect ranks with `/resonantarcana spell radiant_sweep <rank>`.

### Shatter

Strike an aimed enemy. Direct damage increases by 50% at 140 freezing ticks or Slowness III or stronger; combine with Glacial Prison or Absolute Zero. Ice hits chill for two seconds and add 40 freezing ticks.

**Ice** · Cast **0.9s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 42 |
| II | 12.5 | — | 50 |
| III | 15 | — | 58 |
| IV | 17.5 | — | 66 |
| V | 20 | — | 74 |

Spell ID: `resonant_arcana:shatter`. Inspect ranks with `/resonantarcana spell shatter <rank>`.

### Cinder Cleave

Sweep a burning crescent through a 120-degree cone. Damage includes half your melee attack attribute at release; ignites enemies.

**Fire** · Cast **0.4s** · Cooldown **5s** · Range **5 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 25 |
| II | 7.5 | — | 30 |
| III | 9 | — | 35 |
| IV | 10.5 | — | 40 |
| V | 12 | — | 45 |

Weapon contribution: add half the melee attack attribute at release before spell-power scaling.

Spell ID: `resonant_arcana:cinder_cleave`. Inspect ranks with `/resonantarcana spell cinder_cleave <rank>`.

### Rime Thrust

Drive a piercing frost thrust through enemies in a narrow line, stopping at walls. Damage includes half your melee attack attribute; heavily slows for 2 seconds.

**Ice** · Cast **0.4s** · Cooldown **6s** · Range **7 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 25 |
| II | 7.5 | — | 30 |
| III | 9 | — | 35 |
| IV | 10.5 | — | 40 |
| V | 12 | — | 45 |

Weapon contribution: add half the melee attack attribute at release before spell-power scaling.

Spell ID: `resonant_arcana:rime_thrust`. Inspect ranks with `/resonantarcana spell rime_thrust <rank>`.

### Blood Rend

Rend a 90-degree crescent. Damage includes half your melee attack attribute. Restore 40% of health actually removed, capped at 3–6 health for the entire cast.

**Blood** · Cast **0.5s** · Cooldown **7s** · Range **5 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 30 |
| II | 7.5 | — | 35 |
| III | 9 | — | 40 |
| IV | 10.5 | — | 45 |
| V | 12 | — | 50 |

Weapon contribution: add half the melee attack attribute at release before spell-power scaling.

Spell ID: `resonant_arcana:blood_rend`. Inspect ranks with `/resonantarcana spell blood_rend <rank>`.

### Rift Draw

Draw a thin distortion along your aim. After a 0.6-second warning, a piercing slash hits visible enemies, adding half your melee attack damage. Walls stop the cut.

**Ender** · Cast **1.5s** · Cooldown **9s** · Range **14 blocks** · Radius / half-width **0.65 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 50 |
| II | 20 | — | 56 |
| III | 24 | — | 62 |
| IV | 28 | — | 68 |
| V | 32 | — | 74 |

Base release / active phase: **0.7s**.

Weapon contribution: add half the melee attack attribute at release before spell-power scaling.

Spell ID: `resonant_arcana:rift_draw`. Inspect ranks with `/resonantarcana spell rift_draw <rank>`.

### Spellbreaker

Sweep a close cone, adding half your melee attack damage. Destroy up to 32 hostile or unowned projectiles and interrupt eligible Iron’s casts. Allies, owned projectiles, protected casts and unrelated magic systems are not cancelled.

**Evocation** · Cast **0.4s** · Cooldown **8s** · Range **7 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 35 |
| II | 7.5 | — | 40 |
| III | 9 | — | 45 |
| IV | 10.5 | — | 50 |
| V | 12 | — | 55 |

Weapon contribution: add half the melee attack attribute at release before spell-power scaling.

Spell ID: `resonant_arcana:spellbreaker`. Inspect ranks with `/resonantarcana spell spellbreaker <rank>`.


<a id="boss_art"></a>
## Boss Arts

### Kraken’s Grasp

The full Kraken rises from beneath the marked ground, curls its tentacles and slams nearby enemies. The slam deals damage and applies three seconds of Slowness. It then sinks away.

**Nature** · Cast **1.2s** · Cooldown **14s** · Range **18 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 60 |
| II | 22.5 | — | 65 |
| III | 27 | — | 70 |
| IV | 31.5 | — | 75 |
| V | 36 | — | 80 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:krakens_grasp`. Inspect ranks with `/resonantarcana spell krakens_grasp <rank>`.

### Ice Spike Eruption

Five ice spikes erupt in a line across a visible ground location. Each enemy can take one hit per cast. Slows and chills enemies without freezing allies.

**Ice** · Cast **0.8s** · Cooldown **9s** · Range **16 blocks** · Radius / half-width **1.8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 40 |
| II | 12.5 | — | 45 |
| III | 15 | — | 50 |
| IV | 17.5 | — | 55 |
| V | 20 | — | 60 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:ice_spike_eruption`. Inspect ranks with `/resonantarcana spell ice_spike_eruption <rank>`.

### Helvar’s Rift

Launch a rift projectile with Helvar’s own model and sounds. It stops at walls and the first enemy hit, applies brief Weakness, and uses your spell power rather than fixed boss damage.

**Ender** · Cast **0.9s** · Cooldown **8s** · Range **28 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 40 |
| II | 17.5 | — | 45 |
| III | 21 | — | 50 |
| IV | 24.5 | — | 55 |
| V | 28 | — | 60 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:helvars_rift`. Inspect ranks with `/resonantarcana spell helvars_rift <rank>`.

### Kraken’s Wrath

Four red Kraken tentacles strike around the aimed ground location, then a central tentacle delivers a triple-damage slam. Each enemy takes at most one outer hit and one central hit.

**Nature** · Cast **3.6s** · Cooldown **30s** · Range **20 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 110 |
| II | 15 | — | 122 |
| III | 18 | — | 134 |
| IV | 21 | — | 146 |
| V | 24 | — | 158 |

Base release / active phase: **7.5s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:krakens_wrath`. Inspect ranks with `/resonantarcana spell krakens_wrath <rank>`.

### Kraken’s Undertow

A Kraken tentacle rises at a visible ground location. Its undertow pulls nearby enemies toward the water before one heavy slam. Uses the Kraken’s red model and texture; allies and bosses are not pulled.

**Nature** · Cast **1.3s** · Cooldown **16s** · Range **18 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 65 |
| II | 15 | — | 71 |
| III | 18 | — | 77 |
| IV | 21 | — | 83 |
| V | 24 | — | 89 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:krakens_undertow`. Inspect ranks with `/resonantarcana spell krakens_undertow <rank>`.

### Yeti’s Glacial Wall

Seven of the Yeti’s ice spikes rise together in a crescent across the aimed ground. The spikes linger with frost shards instead of flashing away. Each visible enemy is hit at most once, slowed and chilled; no blocks are placed.

**Ice** · Cast **1s** · Cooldown **11s** · Range **16 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 50 |
| II | 11.25 | — | 55 |
| III | 13.5 | — | 60 |
| IV | 15.75 | — | 65 |
| V | 18 | — | 70 |

Base release / active phase: **3.1s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:yetis_glacial_wall`. Inspect ranks with `/resonantarcana spell yetis_glacial_wall <rank>`.

### Helvar’s Fracture

Helvar’s rift projectile breaks against the first enemy or wall, releasing an area burst that weakens visible enemies. Terrain remains intact.

**Ender** · Cast **1.3s** · Cooldown **12s** · Range **24 blocks** · Radius / half-width **3.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 60 |
| II | 12.5 | — | 66 |
| III | 15 | — | 72 |
| IV | 17.5 | — | 78 |
| V | 20 | — | 84 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:helvars_fracture`. Inspect ranks with `/resonantarcana spell helvars_fracture <rank>`.

### Helvar’s Swordfall

Summon Helvar’s original sword above the marked ground. After a short warning, it drops point-first and damages and weakens nearby enemies. Requires space above the impact point.

**Ender** · Cast **1.3s** · Cooldown **12s** · Range **18 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 60 |
| II | 20 | — | 66 |
| III | 24 | — | 72 |
| IV | 28 | — | 78 |
| V | 32 | — | 84 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:helvars_swordfall`. Inspect ranks with `/resonantarcana spell helvars_swordfall <rank>`.

### Sandworm’s Eruption

The Sandworm’s own sand columns emerge in a row toward the aimed ground. The rising columns strike enemies once per cast; terrain stays intact.

**Ender** · Cast **1s** · Cooldown **11s** · Range **18 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 11 | — | 50 |
| II | 13.75 | — | 55 |
| III | 16.5 | — | 60 |
| IV | 19.25 | — | 65 |
| V | 22 | — | 70 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:sandworms_eruption`. Inspect ranks with `/resonantarcana spell sandworms_eruption <rank>`.

### Sandworm’s Quicksand

A spiralling sand vortex slows and draws visible enemies inward. Three sand columns then erupt around its rim for one final strike. Bosses and allies are never pulled; no terrain is changed.

**Ender** · Cast **1.2s** · Cooldown **15s** · Range **16 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 65 |
| II | 10 | — | 71 |
| III | 12 | — | 77 |
| IV | 14 | — | 83 |
| V | 16 | — | 89 |

Base release / active phase: **3s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:sandworms_quicksand`. Inspect ranks with `/resonantarcana spell sandworms_quicksand <rank>`.

### Infernal Barrage

Conjure three blazing orbs beside you, then launch them one after another toward the aimed ground. Each orb travels visibly and bursts against the first enemy or wall. No guard mobs are summoned; allies and terrain are protected.

**Fire** · Cast **1.2s** · Cooldown **14s** · Range **16 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 60 |
| II | 8.75 | — | 65 |
| III | 10.5 | — | 70 |
| IV | 12.25 | — | 75 |
| V | 14 | — | 80 |

Base release / active phase: **4s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:dragons_guard`. Inspect ranks with `/resonantarcana spell dragons_guard <rank>`.

### Dragon’s Cataclysm

Mark visible ground with an ember ring and call down a blazing fireball. After one second of warning it descends, detonating once in a heavy expanding fire shockwave. Requires space overhead; allies and terrain are protected.

**Fire** · Cast **1.6s** · Cooldown **18s** · Range **18 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 85 |
| II | 20 | — | 93 |
| III | 24 | — | 101 |
| IV | 28 | — | 109 |
| V | 32 | — | 117 |

Base release / active phase: **2.7s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:dragons_pyre`. Inspect ranks with `/resonantarcana spell dragons_pyre <rank>`.


<a id="cataclysm"></a>
## Cataclysms

### DOOM

Open an elevated black hole with a gold photon rim, flattened accretion disc and red infalling trails. It draws visible enemies inward before collapsing for one 256-damage hit per enemy. A heartbeat, portal hum and five atmospheric layers build throughout the ritual. Terrain remains intact.

**Ender** · Cast **10s** · Cooldown **120s** · Range **32 blocks** · Radius / half-width **12 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 256 | — | 500 |

Base release / active phase: **4.5s**.

Spell ID: `resonant_arcana:doom`. Inspect ranks with `/resonantarcana spell doom <rank>`.

### Astral Apocalypse

Call three groups of falling stars around the mark. Each star damages enemies within four blocks of its impact. A final arcane collapse deals double damage across fourteen blocks.

**Evocation** · Cast **10s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 96 | — | 440 |

Base release / active phase: **4.4s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:astral_apocalypse`. Inspect ranks with `/resonantarcana spell astral_apocalypse <rank>`.

### Crimson Eclipse

Release three blood tides around the mark, followed by a double-damage final tide. Heal for 8% of health actually removed, up to twenty health across the whole cast.

**Blood** · Cast **10s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 96 | — | 460 |

Base release / active phase: **4.5s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:crimson_eclipse`. Inspect ranks with `/resonantarcana spell crimson_eclipse <rank>`.

### World Pyre

Call five meteors around the mark. Each erupts in a five-block blast. A final fourteen-block firestorm deals 250% damage and ignites enemies.

**Fire** · Cast **12s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 100 | — | 500 |

Base release / active phase: **5s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:world_pyre`. Inspect ranks with `/resonantarcana spell world_pyre <rank>`.

### Void Coronation

Flood the marked area with shadow and drifting void embers. Three expanding damage pulses suppress enemies with Darkness and Weakness II, followed by a double-damage collapse.

**Ender** · Cast **11s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 104 | — | 480 |

Base release / active phase: **4.8s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:void_coronation`. Inspect ranks with `/resonantarcana spell void_coronation <rank>`.

### Heaven’s Thunder

Call six lightning columns around the mark, each striking a six-block area. A final fourteen-block thunderclap deals double damage. Columns can overlap.

**Lightning** · Cast **10s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 100 | — | 480 |

Base release / active phase: **5.6s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:heavens_thunder`. Inspect ranks with `/resonantarcana spell heavens_thunder <rank>`.

### Rift Collision

Pull visible enemies inward, then cut the area along two perpendicular lines. A final fourteen-block spatial implosion deals triple damage.

**Ender** · Cast **12s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 112 | — | 500 |

Base release / active phase: **5.2s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:rift_collision`. Inspect ranks with `/resonantarcana spell rift_collision <rank>`.

### Soul Requiem

Unleash three soul pulses at four, eight and twelve blocks from the mark. Enemies touched by a pulse are revealed and afflicted with Darkness. A final blast deals double damage across the area.

**Eldritch** · Cast **11s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 104 | — | 460 |

Base release / active phase: **4.6s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:soul_requiem`. Inspect ranks with `/resonantarcana spell soul_requiem <rank>`.

### Worldroot Blessing

Bless an area with moss, flowering leaves and drifting spores. Three pulses damage and poison enemies while slowing them. The final bloom deals double damage and heals nearby allies for eight base health.

**Nature** · Cast **10s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 88 | — | 440 |

Base release / active phase: **4.8s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:worldroot_catastrophe`. Inspect ranks with `/resonantarcana spell worldroot_catastrophe <rank>`.

### Final Judgement

Summon a giant golden sword above the mark. Three holy pulses damage enemies and heal allies for four base health each. Its final verdict deals double damage, heals twelve, cleanses afflictions and grants five seconds of Resistance I.

**Holy** · Cast **12s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 96 | — | 500 |

Base release / active phase: **5.2s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:final_judgement`. Inspect ranks with `/resonantarcana spell final_judgement <rank>`.

### Eternal Winter

Bury a sixteen-block area in a ten-second blizzard. Eight cold pulses damage enemies, applying Slowness IV and freezing buildup. The final burst deals triple damage. Terrain is not changed.

**Ice** · Cast **11s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **16 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 64 | — | 480 |

Base release / active phase: **10s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:eternal_winter`. Inspect ranks with `/resonantarcana spell eternal_winter <rank>`.

### Explosion

Lock the aimed location and charge for 7.7 seconds at the original recording pitch. Five crimson and amber seals gather ember ribbons into a diagonal beacon. Completion detonates the marked area for 256 base damage over sixteen blocks with the redesigned blast audio. Leaves you heavily slowed for four seconds and weakened for five. Terrain is not destroyed. This recorded ritual has a fixed windup.

**Fire** · Cast **7.7s** · Cooldown **120s** · Range **48 blocks** · Radius / half-width **16 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 256 | — | 480 |

Base release / active phase: **1.6s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:explosion`. Inspect ranks with `/resonantarcana spell explosion <rank>`.

### I Am Atomic

Anchor a sixteen-block violet containment sphere and compress mana at a spectral sword tip for 13.5 seconds. Captured enemies are held within the boundary; bosses and fully knockback-resistant foes resist containment. Allies gain brief Resistance I and hostile projectiles crossing the sphere are absorbed. Release one 512-damage violet detonation, sparing allies and terrain. Moving more than three blocks interrupts the ritual. The full recording plays at its original pitch; cast-speed bonuses do not shorten this ritual.

**Ender** · Cast **13.5s** · Cooldown **180s** · Range **32 blocks** · Radius / half-width **16 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 512 | — | 500 |

Base release / active phase: **4s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:i_am_atomic`. Inspect ranks with `/resonantarcana spell i_am_atomic <rank>`.

### Doom Beam

Compress a small black hole for 13 seconds. Halfway through, its rim and converging trails turn emerald. Release a giant white-hot green plasma beam for 9.5 seconds along your final aim. Branching lightning wraps the beam; each visible enemy entering it takes one 320-damage hit. Solid terrain blocks it. Fixed recorded windup.

**Ender** · Cast **13s** · Cooldown **150s** · Range **64 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 320 | — | 500 |

Base release / active phase: **9.5s**.

Spell ID: `resonant_arcana:doom_beam`. Inspect ranks with `/resonantarcana spell doom_beam <rank>`.
