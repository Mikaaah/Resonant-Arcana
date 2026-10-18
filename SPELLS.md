# Spell reference

**154 core spells + 12 optional Bosses’Rise spells + 18 optional Alex’s Caves spells.** Every spell has one functional category and one native Iron’s school.

Cast through an Iron’s spellbook. The three legendary books are inscribable equipment; their perks apply in the Curios spellbook slot. The debug wand exposes every enabled spell and offers category filters. It has no survival recipe.

Values are defaults before school power, equipment, resistances and server settings. Damage and healing use health points: two health points equal one heart. Damage is per normal hit or pulse; descriptions explain final multipliers. Travel time and release sequences occur after the cast bar. Ordinary spells have up to five ranks; Doom and cataclysms have one Legendary rank.

Offensive spells protect the caster, eligible teammates and owned pets. Healing affects the caster, owned pets and visible scoreboard teammates, plus teamless players when enabled. Cover, enemy movement, normal damage immunity and status immunities can change results. Spells do not destroy terrain. Void Exile temporarily loads its isolated cell and return chunks; ordinary spells use loaded terrain.

Cataclysm windups use a large seal at the caster’s feet, with dense native particles spiralling down from above and a small stream into the caster. Optional AAAParticles are limited to floor seals, Explosion’s five beam seals and Doom. Without AAA, native outlines remain. Charge cues repeat throughout the cast.

## Categories

| Category | Spells |
| --- | ---: |
| [Projectiles](#projectile) | 24 |
| [Beams & Channels](#beam) | 11 |
| [Area Magic](#area) | 36 |
| [Control](#control) | 18 |
| [Movement](#mobility) | 11 |
| [Support & Defense](#support) | 21 |
| [Summons & Constructs](#summon) | 11 |
| [Strikes & Drains](#melee) | 20 |
| [Boss Arts](#boss_art) | 12 |
| [Cataclysms](#cataclysm) | 20 |

## Schools

| School | Spells |
| --- | ---: |
| Evocation | 19 |
| Blood | 15 |
| Fire | 19 |
| Ender | 39 |
| Lightning | 18 |
| Eldritch | 15 |
| Nature | 21 |
| Holy | 17 |
| Ice | 21 |

<a id="projectile"></a>
## Projectiles

### Spark

Loose a compact arcane spark along your aim. It stops at the first enemy or solid surface, releasing a small arcane impact.

**Evocation** · Cast **0s** · Cooldown **0.6s** · Range **32 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 15 |
| II | 6.25 | — | 18 |
| III | 7.5 | — | 21 |
| IV | 8.75 | — | 24 |
| V | 10 | — | 27 |

Spell ID: `resonant_arcana:spark`. Inspect ranks with `/resonantarcana spell spark <rank>`.

### Astral Comet

Launch a slow astral comet with a bright core and trailing star motes. It bursts around the first enemy or solid surface reached, damaging visible enemies in the impact area.

**Evocation** · Cast **1s** · Cooldown **6s** · Range **48 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 22 | — | 45 |
| II | 27.5 | — | 54 |
| III | 33 | — | 63 |
| IV | 38.5 | — | 72 |
| V | 44 | — | 81 |

Spell ID: `resonant_arcana:astral_comet`. Inspect ranks with `/resonantarcana spell astral_comet <rank>`.

### Fire Comet

Hurl a slow ember comet with a burning wake. Its first collision releases an area detonation and ignites enemies hit for three seconds.

**Fire** · Cast **1s** · Cooldown **6s** · Range **44 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 20 | — | 45 |
| II | 25 | — | 54 |
| III | 30 | — | 63 |
| IV | 35 | — | 72 |
| V | 40 | — | 81 |

Spell ID: `resonant_arcana:fire_comet`. Inspect ranks with `/resonantarcana spell fire_comet <rank>`.

### Fireball

Throw a compact fireball with a coiling ember trail. Its first collision releases a small area burst and ignites enemies hit for three seconds.

**Fire** · Cast **0s** · Cooldown **2.5s** · Range **40 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 25 |
| II | 10 | — | 30 |
| III | 12 | — | 35 |
| IV | 14 | — | 40 |
| V | 16 | — | 45 |

Spell ID: `resonant_arcana:fireball`. Inspect ranks with `/resonantarcana spell fireball <rank>`.

### Shadow Bolt

Launch a twisting shadow bolt. Its first collision releases a small void impact, applying Weakness I for three seconds to enemies hit.

**Ender** · Cast **0s** · Cooldown **1.25s** · Range **36 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 20 |
| II | 8.75 | — | 24 |
| III | 10.5 | — | 28 |
| IV | 12.25 | — | 32 |
| V | 14 | — | 36 |

Spell ID: `resonant_arcana:shadow_bolt`. Inspect ranks with `/resonantarcana spell shadow_bolt <rank>`.

### Umbral Seeker

Release a dark wisp that steers toward the enemy selected at release. Its impact damages and weakens enemies; solid cover intercepts the projectile.

**Ender** · Cast **0.6s** · Cooldown **3.5s** · Range **36 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 27 |
| II | 12.5 | — | 32 |
| III | 15 | — | 37 |
| IV | 17.5 | — | 42 |
| V | 20 | — | 47 |

Spell ID: `resonant_arcana:soul_seeker`. Inspect ranks with `/resonantarcana spell soul_seeker <rank>`.

### Thunder Bolt

Fire a compact electric bolt with a jagged spark wake. The first enemy or solid surface stops it and releases a small electric impact.

**Lightning** · Cast **0s** · Cooldown **1.5s** · Range **40 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 23 |
| II | 10 | — | 27 |
| III | 12 | — | 31 |
| IV | 14 | — | 35 |
| V | 16 | — | 39 |

Spell ID: `resonant_arcana:thunder_bolt`. Inspect ranks with `/resonantarcana spell thunder_bolt <rank>`.

### Rift Bolt

Send a compressed spatial orb along your aim, trailed by inward-curling portal motes. Its first collision releases a small force impact.

**Ender** · Cast **0s** · Cooldown **1.5s** · Range **32 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 21 |
| II | 8.75 | — | 25 |
| III | 10.5 | — | 29 |
| IV | 12.25 | — | 33 |
| V | 14 | — | 37 |

Spell ID: `resonant_arcana:gravity_bolt`. Inspect ranks with `/resonantarcana spell gravity_bolt <rank>`.

### Echo Bolt

Fire a resonant soul bolt with blue sculk motes. It ends at the first enemy or solid surface, releasing a small echo impact.

**Eldritch** · Cast **0s** · Cooldown **1.25s** · Range **36 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 20 |
| II | 8.75 | — | 24 |
| III | 10.5 | — | 28 |
| IV | 12.25 | — | 32 |
| V | 14 | — | 36 |

Spell ID: `resonant_arcana:echo_bolt`. Inspect ranks with `/resonantarcana spell echo_bolt <rank>`.

### Arcane Volley

Fire three arcane bolts half a second apart. Each bolt follows your aim when released and bursts on the first enemy or solid surface.

**Evocation** · Cast **0.5s** · Cooldown **4.5s** · Range **32 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 23 |
| II | 6.25 | — | 27 |
| III | 7.5 | — | 31 |
| IV | 8.75 | — | 35 |
| V | 10 | — | 39 |

Base release / active phase: **2.2s**.

Spell ID: `resonant_arcana:arcane_volley`. Inspect ranks with `/resonantarcana spell arcane_volley <rank>`.

### Venom Bolt

Spit a travelling acid orb with a spore-speckled wake. Its first collision releases a small impact that damages enemies and applies Poison I for four seconds.

**Nature** · Cast **0s** · Cooldown **1.75s** · Range **32 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 17 |
| II | 5 | — | 20 |
| III | 6 | — | 23 |
| IV | 7 | — | 26 |
| V | 8 | — | 29 |

Spell ID: `resonant_arcana:venom_bolt`. Inspect ranks with `/resonantarcana spell venom_bolt <rank>`.

### Blood Needles

Release five divergent blood needles. Damage is per projectile; projectiles stop at solid cover.

**Blood** · Cast **0.4s** · Cooldown **4s** · Range **28 blocks** · Radius / half-width **0.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 28 |
| II | 6.25 | — | 33 |
| III | 7.5 | — | 38 |
| IV | 8.75 | — | 43 |
| V | 10 | — | 48 |

Spell ID: `resonant_arcana:blood_needles`. Inspect ranks with `/resonantarcana spell blood_needles <rank>`.

### Arc Ricochet

Throw a travelling lightning orb that can ricochet between three visible enemies, hitting each once.

**Lightning** · Cast **0.7s** · Cooldown **7s** · Range **32 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 40 |
| II | 12.5 | — | 48 |
| III | 15 | — | 56 |
| IV | 17.5 | — | 64 |
| V | 20 | — | 72 |

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

Release five venom fangs in a spreading fan. Each projectile can poison the enemy it hits.

**Nature** · Cast **0.5s** · Cooldown **4.5s** · Range **24 blocks** · Radius / half-width **0.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 28 |
| II | 5 | — | 33 |
| III | 6 | — | 38 |
| IV | 7 | — | 43 |
| V | 8 | — | 48 |

Spell ID: `resonant_arcana:viper_fangs`. Inspect ranks with `/resonantarcana spell viper_fangs <rank>`.

### Luminous Dart

Fire a quick dart of holy light. It stops at solid cover.

**Holy** · Cast **0s** · Cooldown **1s** · Range **32 blocks** · Radius / half-width **0.7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 16 |
| II | 7.5 | — | 19 |
| III | 9 | — | 22 |
| IV | 10.5 | — | 25 |
| V | 12 | — | 28 |

Spell ID: `resonant_arcana:luminous_dart`. Inspect ranks with `/resonantarcana spell luminous_dart <rank>`.

### Frost Needle

Fire a fast ice needle that chills its target and stops at solid cover.

**Ice** · Cast **0s** · Cooldown **1s** · Range **32 blocks** · Radius / half-width **0.7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 16 |
| II | 7.5 | — | 19 |
| III | 9 | — | 22 |
| IV | 10.5 | — | 25 |
| V | 12 | — | 28 |

Spell ID: `resonant_arcana:frost_needle`. Inspect ranks with `/resonantarcana spell frost_needle <rank>`.

### Ice Serpent

Send a low, weaving ice serpent horizontally along your aim. It chills each enemy struck and stops at solid cover; it does not climb terrain.

**Ice** · Cast **0.7s** · Cooldown **5.5s** · Range **24 blocks** · Radius / half-width **1.4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 34 |
| II | 15 | — | 40 |
| III | 18 | — | 46 |
| IV | 21 | — | 52 |
| V | 24 | — | 58 |

Spell ID: `resonant_arcana:ice_serpent`. Inspect ranks with `/resonantarcana spell ice_serpent <rank>`.

### Crystal Barrage

Release five divergent crystal projectiles. Damage is per projectile; their impacts chill enemies.

**Ice** · Cast **0.7s** · Cooldown **6s** · Range **30 blocks** · Radius / half-width **0.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 36 |
| II | 6.25 | — | 43 |
| III | 7.5 | — | 50 |
| IV | 8.75 | — | 57 |
| V | 10 | — | 64 |

Spell ID: `resonant_arcana:crystal_barrage`. Inspect ranks with `/resonantarcana spell crystal_barrage <rank>`.

### Polarity Bolt

Fire a scarlet-and-azure magnetic bolt. Its first enemy impact deals damage and pulls the target toward you; solid cover stops it.

**Lightning** · Cast **0.4s** · Cooldown **3.5s** · Range **24 blocks** · Radius / half-width **1.2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 24 |
| II | 11.25 | — | 32 |
| III | 13.5 | — | 40 |
| IV | 15.75 | — | 48 |
| V | 18 | — | 56 |

Base release / active phase: **2.5s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Magnetic Caves · Novice**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:polarity_bolt`. Inspect ranks with `/resonantarcana spell polarity_bolt <rank>`.

### Acid Glob

Lob a glowing acid glob along your aim. It splashes on the first enemy or solid surface, damaging enemies in a small area and poisoning them for three seconds.

**Nature** · Cast **0.5s** · Cooldown **4.5s** · Range **24 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 25 |
| II | 10 | — | 33 |
| III | 12 | — | 41 |
| IV | 14 | — | 49 |
| V | 16 | — | 57 |

Base release / active phase: **2.25s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Toxic Caves · Novice**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:acid_glob`. Inspect ranks with `/resonantarcana spell acid_glob <rank>`.

### Brine Bubble

Launch a Deep One water bolt. Its first enemy impact deals damage and traps non-boss targets in Alex’s Caves bubble effect for two seconds.

**Ice** · Cast **0.6s** · Cooldown **5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 26 |
| II | 12.5 | — | 34 |
| III | 15 | — | 42 |
| IV | 17.5 | — | 50 |
| V | 20 | — | 58 |

Base release / active phase: **2.5s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Abyssal Chasm · Novice**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:brine_bubble`. Inspect ranks with `/resonantarcana spell brine_bubble <rank>`.

### Desolate Dagger

Send a spectral Desolate Dagger along your aim. It pierces up to three enemies, dealing one hit and Weakness I to each before fading. Solid cover stops the blade.

**Ender** · Cast **0.5s** · Cooldown **5.5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 28 |
| II | 15 | — | 36 |
| III | 18 | — | 44 |
| IV | 21 | — | 52 |
| V | 24 | — | 60 |

Base release / active phase: **2.25s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Forlorn Hollows · Novice**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:desolate_dagger`. Inspect ranks with `/resonantarcana spell desolate_dagger <rank>`.

### Gumball Volley

Fire three native gumballs half a second apart. Each projectile bounces off one solid surface and damages the first enemy it reaches.

**Evocation** · Cast **0.6s** · Cooldown **6s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 30 |
| II | 6.25 | — | 38 |
| III | 7.5 | — | 46 |
| IV | 8.75 | — | 54 |
| V | 10 | — | 62 |

Base release / active phase: **3.5s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Candy Cavity · Novice**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:gumball_volley`. Inspect ranks with `/resonantarcana spell gumball_volley <rank>`.


<a id="beam"></a>
## Beams & Channels

### Flamethrower

Channel a narrow flame stream along your aim for three seconds. Six pulses damage enemies and ignite them for three seconds. Solid cover stops the stream.

**Fire** · Cast **0.4s** · Cooldown **5s** · Range **12 blocks** · Radius / half-width **1.4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 40 |
| II | 5 | — | 44 |
| III | 6 | — | 48 |
| IV | 7 | — | 52 |
| V | 8 | — | 56 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:flame_thrower`. Inspect ranks with `/resonantarcana spell flame_thrower <rank>`.

### Chain Lightning

Connect up to five distinct visible enemies with jagged lightning links. Each jump chooses a nearby eligible enemy; cover and the jump radius can end the chain early.

**Lightning** · Cast **0.6s** · Cooldown **6s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 32 |
| II | 11.25 | — | 38 |
| III | 13.5 | — | 44 |
| IV | 15.75 | — | 50 |
| V | 18 | — | 56 |

Spell ID: `resonant_arcana:chain_lightning`. Inspect ranks with `/resonantarcana spell chain_lightning <rank>`.

### Blood Lance

Drive a straight crimson lance through visible enemies along your aim. Each enemy on the piercing line is hit once; solid cover ends the lance.

**Blood** · Cast **0.5s** · Cooldown **4.5s** · Range **24 blocks** · Radius / half-width **0.6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 35 |
| II | 20 | — | 42 |
| III | 24 | — | 49 |
| IV | 28 | — | 56 |
| V | 32 | — | 63 |

Spell ID: `resonant_arcana:blood_lance`. Inspect ranks with `/resonantarcana spell blood_lance <rank>`.

### Soul Cannon

Charge an echo cannon for two seconds, then launch a travelling soul blast. It bursts against the first enemy or solid surface.

**Eldritch** · Cast **2s** · Cooldown **14s** · Range **48 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 48 | — | 90 |
| II | 60 | — | 100 |
| III | 72 | — | 110 |
| IV | 84 | — | 120 |
| V | 96 | — | 130 |

Spell ID: `resonant_arcana:soul_cannon`. Inspect ranks with `/resonantarcana spell soul_cannon <rank>`.

### Tempest Spear

Charge for 2.5 seconds, then fire a piercing storm lance. Solid cover stops the beam.

**Lightning** · Cast **2.5s** · Cooldown **18s** · Range **56 blocks** · Radius / half-width **1.1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 56 | — | 85 |
| II | 70 | — | 95 |
| III | 84 | — | 105 |
| IV | 98 | — | 115 |
| V | 112 | — | 125 |

Spell ID: `resonant_arcana:tempest_spear`. Inspect ranks with `/resonantarcana spell tempest_spear <rank>`.

### Dragon Breath

Exhale a broad cone of flame following your aim for 1.5 seconds. Three pulses can hit enemies that remain in the cone. Fire hits ignite for three seconds.

**Fire** · Cast **1s** · Cooldown **8s** · Range **10 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 50 |
| II | 8.75 | — | 60 |
| III | 10.5 | — | 70 |
| IV | 12.25 | — | 80 |
| V | 14 | — | 90 |

Base release / active phase: **1.5s**.

Spell ID: `resonant_arcana:dragon_breath`. Inspect ranks with `/resonantarcana spell dragon_breath <rank>`.

### Judgement Ray

Charge a piercing ray of holy light, striking each visible enemy on its line once. Solid cover stops the ray.

**Holy** · Cast **2s** · Cooldown **13s** · Range **40 blocks** · Radius / half-width **0.7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 44 | — | 70 |
| II | 55 | — | 80 |
| III | 66 | — | 90 |
| IV | 77 | — | 100 |
| V | 88 | — | 110 |

Spell ID: `resonant_arcana:judgement_ray`. Inspect ranks with `/resonantarcana spell judgement_ray <rank>`.

### Cryo Lance

Fire a precise piercing ice lance through visible enemies. Solid cover blocks it.

**Ice** · Cast **0.9s** · Cooldown **6s** · Range **28 blocks** · Radius / half-width **0.7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 38 |
| II | 22.5 | — | 45 |
| III | 27 | — | 52 |
| IV | 31.5 | — | 59 |
| V | 36 | — | 66 |

Spell ID: `resonant_arcana:cryo_lance`. Inspect ranks with `/resonantarcana spell cryo_lance <rank>`.

### Arcane Devastator

Channel cosmic energy, then release a sustained beam along your final aim. Each enemy entering its path takes one hit. New cover stops the beam.

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

### Tremorzilla Breath

Manifest Tremorzilla’s charged breath as a green atomic beam following your hands and aim for five seconds. Each enemy entering its path takes one hit. Solid cover stops the breath.

**Nature** · Cast **4s** · Cooldown **50s** · Range **48 blocks** · Radius / half-width **2.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 72 | — | 180 |
| II | 90 | — | 188 |
| III | 108 | — | 196 |
| IV | 126 | — | 204 |
| V | 144 | — | 212 |

Base release / active phase: **5s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Toxic Caves · Master**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:tremorzilla_breath`. Inspect ranks with `/resonantarcana spell tremorzilla_breath <rank>`.

### Forsaken Sonar

Manifest the Forsaken’s jaws and release three aimed sonar waves. Each wave damages visible enemies in a broad forward cone, reveals them and briefly slows them.

**Ender** · Cast **3s** · Cooldown **37.5s** · Range **28 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 22 | — | 140 |
| II | 27.5 | — | 148 |
| III | 33 | — | 156 |
| IV | 38.5 | — | 164 |
| V | 44 | — | 172 |

Base release / active phase: **3.3s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Forlorn Hollows · Master**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:forsaken_sonar`. Inspect ranks with `/resonantarcana spell forsaken_sonar <rank>`.


<a id="area"></a>
## Area Magic

### Blood Wave

Send a broad crimson crescent along your aim. Its moving front cuts each visible enemy once and dissipates against solid cover.

**Blood** · Cast **0.35s** · Cooldown **3s** · Range **28 blocks** · Radius / half-width **1.8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 24 |
| II | 8.75 | — | 28 |
| III | 10.5 | — | 32 |
| IV | 12.25 | — | 36 |
| V | 14 | — | 40 |

Spell ID: `resonant_arcana:blood_wave`. Inspect ranks with `/resonantarcana spell blood_wave <rank>`.

### Poison Wave

Roll a low green acid wave forwards. Each enemy crossed by its broad front takes one direct hit and Poison I for four seconds.

**Nature** · Cast **0.35s** · Cooldown **3.5s** · Range **28 blocks** · Radius / half-width **1.8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 19 |
| II | 3.75 | — | 22 |
| III | 4.5 | — | 25 |
| IV | 5.25 | — | 28 |
| V | 6 | — | 31 |

Spell ID: `resonant_arcana:poison_wave`. Inspect ranks with `/resonantarcana spell poison_wave <rank>`.

### Flame Wave

Drive a broad wall of flickering embers forwards. Its advancing front hits each visible enemy once and ignites them for three seconds.

**Fire** · Cast **0.35s** · Cooldown **3s** · Range **28 blocks** · Radius / half-width **1.8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 24 |
| II | 8.75 | — | 28 |
| III | 10.5 | — | 32 |
| IV | 12.25 | — | 36 |
| V | 14 | — | 40 |

Spell ID: `resonant_arcana:flame_wave`. Inspect ranks with `/resonantarcana spell flame_wave <rank>`.

### Arcane Repulse

Release an expanding arcane pressure ring from your feet. Enemies touched by the advancing rim take one hit and are pushed away; walls block the hit.

**Evocation** · Cast **0.5s** · Cooldown **6s** · Range **0 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 36 |
| II | 15 | — | 43 |
| III | 18 | — | 50 |
| IV | 21 | — | 57 |
| V | 24 | — | 64 |

Spell ID: `resonant_arcana:arcane_repulse`. Inspect ranks with `/resonantarcana spell arcane_repulse <rank>`.

### Lightning

Call a vertical lightning strike onto your aim. Enemies near the strike take one hit; the spell leaves terrain intact.

**Lightning** · Cast **0.4s** · Cooldown **5s** · Range **32 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 32 |
| II | 15 | — | 38 |
| III | 18 | — | 44 |
| IV | 21 | — | 50 |
| V | 24 | — | 56 |

Spell ID: `resonant_arcana:lightning`. Inspect ranks with `/resonantarcana spell lightning <rank>`.

### Smite

Mark your aim with holy light, then deliver a vertical judgement strike. Visible enemies within its impact radius take the listed damage; allies are protected.

**Lightning** · Cast **1.4s** · Cooldown **10s** · Range **32 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 32 | — | 65 |
| II | 40 | — | 75 |
| III | 48 | — | 85 |
| IV | 56 | — | 95 |
| V | 64 | — | 105 |

Spell ID: `resonant_arcana:smite`. Inspect ranks with `/resonantarcana spell smite <rank>`.

### Cinder Vortex

Raise a stationary ember funnel at your aim for two seconds. Four half-second pulses can damage visible enemies inside and refresh three seconds of burning.

**Fire** · Cast **1.25s** · Cooldown **6s** · Range **30 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 9 | — | 34 |
| II | 11.25 | — | 40 |
| III | 13.5 | — | 46 |
| IV | 15.75 | — | 52 |
| V | 18 | — | 58 |

Base release / active phase: **2s**.

Spell ID: `resonant_arcana:fire_twister`. Inspect ranks with `/resonantarcana spell fire_twister <rank>`.

### Cinder Surge

Send an expanding ember ring across the ground around you. Its advancing rim damages each visible enemy once, pushes them away and ignites them for three seconds.

**Fire** · Cast **0.8s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **9 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 41 |
| II | 20 | — | 49 |
| III | 24 | — | 57 |
| IV | 28 | — | 65 |
| V | 32 | — | 73 |

Spell ID: `resonant_arcana:flame_shock_wave`. Inspect ranks with `/resonantarcana spell flame_shock_wave <rank>`.

### Crimson Rupture

Detonate a crimson bloom at the aimed enemy or reachable aim point. Its radial blood spray damages visible enemies in the impact area once.

**Blood** · Cast **1s** · Cooldown **7s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 20 | — | 46 |
| II | 25 | — | 55 |
| III | 30 | — | 64 |
| IV | 35 | — | 73 |
| V | 40 | — | 82 |

Spell ID: `resonant_arcana:blood_explode`. Inspect ranks with `/resonantarcana spell blood_explode <rank>`.

### Umbral Wave

Advance a broad curtain of shadow along your aim. Each enemy touched by the moving front takes one hit and Weakness I for three seconds.

**Ender** · Cast **0.35s** · Cooldown **3.25s** · Range **28 blocks** · Radius / half-width **2.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 24 |
| II | 8.75 | — | 28 |
| III | 10.5 | — | 32 |
| IV | 12.25 | — | 36 |
| V | 14 | — | 40 |

Spell ID: `resonant_arcana:dark_wave`. Inspect ranks with `/resonantarcana spell dark_wave <rank>`.

### Nightfall

Collapse a cloud of shadow at your aim. Visible enemies in the burst take one hit, Weakness I for three seconds and brief Darkness.

**Ender** · Cast **1s** · Cooldown **7s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 20 | — | 46 |
| II | 25 | — | 55 |
| III | 30 | — | 64 |
| IV | 35 | — | 73 |
| V | 40 | — | 82 |

Spell ID: `resonant_arcana:nightfall`. Inspect ranks with `/resonantarcana spell nightfall <rank>`.

### Umbral Veil

Carry a dark soul aura with you for three seconds. Three one-second pulses damage visible nearby enemies and apply Weakness I for three seconds.

**Ender** · Cast **0.5s** · Cooldown **8s** · Range **0 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 30 |
| II | 6.25 | — | 36 |
| III | 7.5 | — | 42 |
| IV | 8.75 | — | 48 |
| V | 10 | — | 54 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:soul_aura`. Inspect ranks with `/resonantarcana spell soul_aura <rank>`.

### Thunder Wave

Send a broad, crackling electric front forwards. Each enemy crossed by its moving rim takes one hit.

**Lightning** · Cast **0.35s** · Cooldown **3.25s** · Range **30 blocks** · Radius / half-width **2.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 24 |
| II | 8.75 | — | 28 |
| III | 10.5 | — | 32 |
| IV | 12.25 | — | 36 |
| V | 14 | — | 40 |

Spell ID: `resonant_arcana:thunder_wave`. Inspect ranks with `/resonantarcana spell thunder_wave <rank>`.

### Storm Call

Establish a lightning storm at your aim for 4.8 seconds. Five one-second pulses damage visible enemies; additional lightning columns show the active storm area.

**Lightning** · Cast **1.6s** · Cooldown **12s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 70 |
| II | 10 | — | 80 |
| III | 12 | — | 90 |
| IV | 14 | — | 100 |
| V | 16 | — | 110 |

Base release / active phase: **4.8s**.

Spell ID: `resonant_arcana:storm_call`. Inspect ranks with `/resonantarcana spell storm_call <rank>`.

### Thunder Pulse

Unleash an expanding electric ring from your feet. The moving rim damages and pushes each visible enemy once, with sparks tracing its current reach.

**Lightning** · Cast **0.7s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **9 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 43 |
| II | 20 | — | 51 |
| III | 24 | — | 59 |
| IV | 28 | — | 67 |
| V | 32 | — | 75 |

Spell ID: `resonant_arcana:thunder_pulse`. Inspect ranks with `/resonantarcana spell thunder_pulse <rank>`.

### Rift Well

Open a stationary gravity well for 4.8 seconds. Pull visible enemies horizontally toward its centre and deal five one-second area pulses; walls block damage and pulling.

**Ender** · Cast **1.25s** · Cooldown **9s** · Range **28 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 55 |
| II | 6.25 | — | 65 |
| III | 7.5 | — | 75 |
| IV | 8.75 | — | 85 |
| V | 10 | — | 95 |

Base release / active phase: **4.8s**.

Spell ID: `resonant_arcana:gravity_well`. Inspect ranks with `/resonantarcana spell gravity_well <rank>`.

### Singularity

Collapse a concentrated spatial orb at your aim. One radial force burst damages and pushes visible enemies; this instant spell leaves no lingering gravity well.

**Ender** · Cast **1.6s** · Cooldown **9s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 32 | — | 70 |
| II | 40 | — | 80 |
| III | 48 | — | 90 |
| IV | 56 | — | 100 |
| V | 64 | — | 110 |

Spell ID: `resonant_arcana:singularity`. Inspect ranks with `/resonantarcana spell singularity <rank>`.

### Sculk Wave

Roll a broad sculk resonance front forwards. Each visible enemy crossed by the advancing wave takes one hit.

**Eldritch** · Cast **0.35s** · Cooldown **3.25s** · Range **28 blocks** · Radius / half-width **2.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 24 |
| II | 8.75 | — | 28 |
| III | 10.5 | — | 32 |
| IV | 12.25 | — | 36 |
| V | 14 | — | 40 |

Spell ID: `resonant_arcana:sculk_wave`. Inspect ranks with `/resonantarcana spell sculk_wave <rank>`.

### Sonic Scream

Charge for one second, then unleash a deafening, wide sonic scream over 12 blocks. Cover blocks the blast.

**Eldritch** · Cast **1s** · Cooldown **8s** · Range **12 blocks** · Radius / half-width **12 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 28 | — | 55 |
| II | 35 | — | 65 |
| III | 42 | — | 75 |
| IV | 49 | — | 85 |
| V | 56 | — | 95 |

Spell ID: `resonant_arcana:sonic_scream`. Inspect ranks with `/resonantarcana spell sonic_scream <rank>`.

### Shrieking Pulse

Release a radial sculk shriek. An expanding soul ring damages and pushes each visible enemy once as its rim passes them.

**Eldritch** · Cast **0.8s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **9 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 41 |
| II | 20 | — | 49 |
| III | 24 | — | 57 |
| IV | 28 | — | 65 |
| V | 32 | — | 73 |

Spell ID: `resonant_arcana:shrieking_pulse`. Inspect ranks with `/resonantarcana spell shrieking_pulse <rank>`.

### Amethyst Nova

Shatter a resonant amethyst bloom at your aim. Crystal motes radiate from one area burst, damaging and pushing visible enemies in range.

**Eldritch** · Cast **1.2s** · Cooldown **8s** · Range **28 blocks** · Radius / half-width **5.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 24 | — | 55 |
| II | 30 | — | 65 |
| III | 36 | — | 75 |
| IV | 42 | — | 85 |
| V | 48 | — | 95 |

Spell ID: `resonant_arcana:amethyst_nova`. Inspect ranks with `/resonantarcana spell amethyst_nova <rank>`.

### Ember Veil

Carry a low ring of embers for three seconds. Three one-second pulses damage visible nearby enemies and ignite them for three seconds.

**Fire** · Cast **0.5s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 27 |
| II | 5 | — | 32 |
| III | 6 | — | 37 |
| IV | 7 | — | 42 |
| V | 8 | — | 47 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:ember_veil`. Inspect ranks with `/resonantarcana spell ember_veil <rank>`.

### Miasma Field

Leave a low poisonous cloud at your aim for 4.8 seconds. Five one-second pulses damage visible enemies inside and refresh Poison I for four seconds.

**Nature** · Cast **0.75s** · Cooldown **9s** · Range **24 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 50 |
| II | 6.25 | — | 60 |
| III | 7.5 | — | 70 |
| IV | 8.75 | — | 80 |
| V | 10 | — | 90 |

Base release / active phase: **4.8s**.

Spell ID: `resonant_arcana:miasma_field`. Inspect ranks with `/resonantarcana spell miasma_field <rank>`.

### Solar Cataclysm

Charge for three seconds, mark your aim, then drop a blazing meteor. Ceilings limit its drop. Fire hits ignite for three seconds.

**Fire** · Cast **3s** · Cooldown **20s** · Range **40 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 64 | — | 90 |
| II | 80 | — | 100 |
| III | 96 | — | 110 |
| IV | 112 | — | 120 |
| V | 128 | — | 130 |

Spell ID: `resonant_arcana:solar_cataclysm`. Inspect ranks with `/resonantarcana spell solar_cataclysm <rank>`.

### Event Horizon

Charge for 3.2 seconds, then pull visible enemies inward for 1.5 seconds before a crushing detonation.

**Ender** · Cast **3.2s** · Cooldown **24s** · Range **32 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 72 | — | 100 |
| II | 90 | — | 110 |
| III | 108 | — | 120 |
| IV | 126 | — | 130 |
| V | 144 | — | 140 |

Base release / active phase: **1.5s**.

Spell ID: `resonant_arcana:event_horizon`. Inspect ranks with `/resonantarcana spell event_horizon <rank>`.

### Glacial Rupture

Send a broad glacial front forwards, damaging and chilling each visible enemy once.

**Ice** · Cast **2.5s** · Cooldown **18s** · Range **18 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 52 | — | 85 |
| II | 65 | — | 95 |
| III | 78 | — | 105 |
| IV | 91 | — | 115 |
| V | 104 | — | 125 |

Spell ID: `resonant_arcana:glacial_rupture`. Inspect ranks with `/resonantarcana spell glacial_rupture <rank>`.

### Gravity Stamp

Mark your aim for one second, then crush visible enemies with a vertical spatial impact.

**Ender** · Cast **1.4s** · Cooldown **11s** · Range **28 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 20 | — | 55 |
| II | 25 | — | 65 |
| III | 30 | — | 75 |
| IV | 35 | — | 85 |
| V | 40 | — | 95 |

Base release / active phase: **1s**.

Spell ID: `resonant_arcana:gravity_stamp`. Inspect ranks with `/resonantarcana spell gravity_stamp <rank>`.

### Bramble Ring

Expand a ring of thorns around yourself. Each visible enemy is damaged once, poisoned and briefly slowed.

**Nature** · Cast **0.7s** · Cooldown **7.5s** · Range **0 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 40 |
| II | 12.5 | — | 48 |
| III | 15 | — | 56 |
| IV | 17.5 | — | 64 |
| V | 20 | — | 72 |

Spell ID: `resonant_arcana:bramble_ring`. Inspect ranks with `/resonantarcana spell bramble_ring <rank>`.

### Corrosive Rain

Call an acid cloud at your aim. Five falling pulses damage and poison visible enemies over four seconds.

**Nature** · Cast **1.4s** · Cooldown **12s** · Range **28 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 60 |
| II | 6.25 | — | 70 |
| III | 7.5 | — | 80 |
| IV | 8.75 | — | 90 |
| V | 10 | — | 100 |

Base release / active phase: **4.05s**.

Spell ID: `resonant_arcana:corrosive_rain`. Inspect ranks with `/resonantarcana spell corrosive_rain <rank>`.

### Seraphic Descent

Mark your aim for one second, then deliver a pillar of light. It damages enemies and restores 6 health at level I to eligible allies at the impact.

**Holy** · Cast **2.5s** · Cooldown **20s** · Range **32 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 56 | 6 | 90 |
| II | 70 | 7.5 | 100 |
| III | 84 | 9 | 110 |
| IV | 98 | 10.5 | 120 |
| V | 112 | 12 | 130 |

Base release / active phase: **1s**.

Spell ID: `resonant_arcana:seraphic_descent`. Inspect ranks with `/resonantarcana spell seraphic_descent <rank>`.

### Rime Nova

Expand a frozen ring around yourself, chilling and damaging each visible enemy once.

**Ice** · Cast **0.9s** · Cooldown **8s** · Range **0 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 44 |
| II | 20 | — | 52 |
| III | 24 | — | 60 |
| IV | 28 | — | 68 |
| V | 32 | — | 76 |

Spell ID: `resonant_arcana:rime_nova`. Inspect ranks with `/resonantarcana spell rime_nova <rank>`.

### Hailstorm

Call a hail cloud at your aim. Five pulses damage and chill visible enemies over four seconds.

**Ice** · Cast **1.4s** · Cooldown **12s** · Range **28 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 60 |
| II | 6.25 | — | 70 |
| III | 7.5 | — | 80 |
| IV | 8.75 | — | 90 |
| V | 10 | — | 100 |

Base release / active phase: **4.05s**.

Spell ID: `resonant_arcana:hailstorm`. Inspect ranks with `/resonantarcana spell hailstorm <rank>`.

### Absolute Zero

Establish a frozen domain for four seconds. Four pulses chill and freeze visible enemies in its area.

**Ice** · Cast **2.5s** · Cooldown **20s** · Range **28 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 85 |
| II | 10 | — | 95 |
| III | 12 | — | 105 |
| IV | 14 | — | 115 |
| V | 16 | — | 125 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:absolute_zero`. Inspect ranks with `/resonantarcana spell absolute_zero <rank>`.

### Soulblast

Blast a forward cone of enemies away. Aim sharply downward to launch yourself with brief Slow Falling. While gliding, boost your Elytra along your aim instead.

**Eldritch** · Cast **0s** · Cooldown **5s** · Range **8 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 10 | — | 25 |
| II | 12.5 | — | 30 |
| III | 15 | — | 35 |
| IV | 17.5 | — | 40 |
| V | 20 | — | 45 |

Spell ID: `resonant_arcana:soulblast`. Inspect ranks with `/resonantarcana spell soulblast <rank>`.

### Funeral Bell

Charge a funeral bell. Two expanding sonic rings deal the listed damage; a third toll deals triple damage. Each ring strikes an enemy at most once, briefly slowing and pushing it. Walls block the waves.

**Eldritch** · Cast **3s** · Cooldown **21s** · Range **0 blocks** · Radius / half-width **9 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 95 |
| II | 20 | — | 105 |
| III | 24 | — | 115 |
| IV | 28 | — | 125 |
| V | 32 | — | 135 |

Base release / active phase: **3.1s**.

Spell ID: `resonant_arcana:funeral_bell`. Inspect ranks with `/resonantarcana spell funeral_bell <rank>`.

### Hullbreaker Surge

Call a Hullbreaker apparition through a rushing tidal wave. The advancing surge damages each enemy once, knocks it away and briefly slows it.

**Ice** · Cast **3.2s** · Cooldown **36s** · Range **28 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 60 | — | 135 |
| II | 75 | — | 143 |
| III | 90 | — | 151 |
| IV | 105 | — | 159 |
| V | 120 | — | 167 |

Base release / active phase: **3.5s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Abyssal Chasm · Master**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:hullbreaker_surge`. Inspect ranks with `/resonantarcana spell hullbreaker_surge <rank>`.


<a id="control"></a>
## Control

### Mind Haze

Disorient one visible enemy with Nausea for five seconds and Blindness for two seconds. Deals no direct damage.

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
| I | 2 | — | 20 |
| II | 2.5 | — | 24 |
| III | 3 | — | 28 |
| IV | 3.5 | — | 32 |
| V | 4 | — | 36 |

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
| I | 3 | — | 22 |
| II | 3.75 | — | 26 |
| III | 4.5 | — | 30 |
| IV | 5.25 | — | 34 |
| V | 6 | — | 38 |

Spell ID: `resonant_arcana:blood_stun`. Inspect ranks with `/resonantarcana spell blood_stun <rank>`.

### Dread Mark

Fasten an orbiting shadow curse to one aimed enemy. After two seconds it bursts around that enemy, damaging and weakening nearby enemies. Losing line of sight or exceeding the link range breaks the curse.

**Ender** · Cast **0.5s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **3.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 24 | — | 52 |
| II | 30 | — | 62 |
| III | 36 | — | 72 |
| IV | 42 | — | 82 |
| V | 48 | — | 92 |

Base release / active phase: **2s**.

Spell ID: `resonant_arcana:shadow_cage`. Inspect ranks with `/resonantarcana spell shadow_cage <rank>`.

### Static Shock

Overload one visible enemy with static. Deal one hit and apply Slowness VI and Weakness II for three seconds.

**Lightning** · Cast **0.4s** · Cooldown **4s** · Range **28 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 22 |
| II | 5 | — | 26 |
| III | 6 | — | 30 |
| IV | 7 | — | 34 |
| V | 8 | — | 38 |

Spell ID: `resonant_arcana:static_shock`. Inspect ranks with `/resonantarcana spell static_shock <rank>`.

### Force Push

Strike one visible aimed enemy with an outward spatial impulse. Deal direct damage and launch it away from the caster with a small upward lift.

**Ender** · Cast **0s** · Cooldown **3.25s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 18 |
| II | 3.75 | — | 21 |
| III | 4.5 | — | 24 |
| IV | 5.25 | — | 27 |
| V | 6 | — | 30 |

Spell ID: `resonant_arcana:force_push`. Inspect ranks with `/resonantarcana spell force_push <rank>`.

### Force Pull

Catch one visible aimed enemy with a spatial link. Deal direct damage and pull it horizontally toward you with a small upward lift.

**Ender** · Cast **0s** · Cooldown **3.25s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 18 |
| II | 3.75 | — | 21 |
| III | 4.5 | — | 24 |
| IV | 5.25 | — | 27 |
| V | 6 | — | 30 |

Spell ID: `resonant_arcana:force_pull`. Inspect ranks with `/resonantarcana spell force_pull <rank>`.

### Suspension

Suspend one visible aimed enemy above its starting point for two seconds. Deal one hit, slow it and briefly grant Slow Falling. Cover, range or displacement beyond four blocks breaks the hold.

**Ender** · Cast **0.6s** · Cooldown **7s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 26 |
| II | 3.75 | — | 31 |
| III | 4.5 | — | 36 |
| IV | 5.25 | — | 41 |
| V | 6 | — | 46 |

Base release / active phase: **2s**.

Spell ID: `resonant_arcana:gravity_choke`. Inspect ranks with `/resonantarcana spell gravity_choke <rank>`.

### Soul Prison

Imprison one aimed enemy in a winding soul cage for three seconds at level I. Three one-second hits apply Darkness while the hold suppresses movement. Cover, range or displacement breaks the link.

**Eldritch** · Cast **0.6s** · Cooldown **7s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 26 |
| II | 3.75 | — | 31 |
| III | 4.5 | — | 36 |
| IV | 5.25 | — | 41 |
| V | 6 | — | 46 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:soul_prison`. Inspect ranks with `/resonantarcana spell soul_prison <rank>`.

### Crimson Tether

Tether an aimed enemy for 3 seconds, gently pulling it and dealing three damage pulses. Cover or moving out of range breaks the link.

**Blood** · Cast **0.8s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 42 |
| II | 6.25 | — | 50 |
| III | 7.5 | — | 58 |
| IV | 8.75 | — | 66 |
| V | 10 | — | 74 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:crimson_tether`. Inspect ranks with `/resonantarcana spell crimson_tether <rank>`.

### Thorn Lash

Catch an aimed enemy with a thorn tether, pull it towards you and poison it.

**Nature** · Cast **0.5s** · Cooldown **5.5s** · Range **20 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 30 |
| II | 20 | — | 36 |
| III | 24 | — | 42 |
| IV | 28 | — | 48 |
| V | 32 | — | 54 |

Spell ID: `resonant_arcana:thorn_lash`. Inspect ranks with `/resonantarcana spell thorn_lash <rank>`.

### Gorgon Gaze

Fix visible enemies in a wide forward cone with a petrifying gaze, applying heavy slowness and weakness for two seconds at level I.

**Nature** · Cast **1.2s** · Cooldown **10s** · Range **10 blocks** · Radius / half-width **0 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 48 |
| II | 5 | — | 57 |
| III | 6 | — | 66 |
| IV | 7 | — | 75 |
| V | 8 | — | 84 |

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

### Void Exile

Banish one hostile living target for ten seconds, leaving a violet rift. On return, it takes damage before receiving Weakness II for five seconds. Bosses, mounted, leashed and dimension-immune targets resist.

**Ender** · Cast **4s** · Cooldown **30s** · Range **24 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 20 | — | 110 |
| II | 25 | — | 120 |
| III | 30 | — | 130 |
| IV | 35 | — | 140 |
| V | 40 | — | 150 |

Base release / active phase: **10s**.

Spell ID: `resonant_arcana:void_exile`. Inspect ranks with `/resonantarcana spell void_exile <rank>`.

### EMP Surge

Release an electromagnetic pulse around yourself. Damage enemies, stun non-bosses for three seconds, interrupt eligible spellcasts and destroy hostile projectiles.

**Lightning** · Cast **3s** · Cooldown **30s** · Range **0 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 56 | — | 120 |
| II | 70 | — | 128 |
| III | 84 | — | 136 |
| IV | 98 | — | 144 |
| V | 112 | — | 152 |

Base release / active phase: **1.2s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Magnetic Caves · Master**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:emp_surge`. Inspect ranks with `/resonantarcana spell emp_surge <rank>`.

### Frostmint Hex

Place a sugar-staff hex surrounded by orbiting frostmint discs. Five pulses damage and freeze enemies inside; eligible allies gain Sugar Rush while holding the circle.

**Ice** · Cast **3.2s** · Cooldown **37.5s** · Range **28 blocks** · Radius / half-width **7 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 140 |
| II | 15 | — | 148 |
| III | 18 | — | 156 |
| IV | 21 | — | 164 |
| V | 24 | — | 172 |

Base release / active phase: **5s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Candy Cavity · Master**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:frostmint_hex`. Inspect ranks with `/resonantarcana spell frostmint_hex <rank>`.


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
| I | 20 | — | 45 |
| II | 25 | — | 54 |
| III | 30 | — | 63 |
| IV | 35 | — | 72 |
| V | 40 | — | 81 |

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
| I | 16 | — | 35 |
| II | 20 | — | 40 |
| III | 24 | — | 45 |
| IV | 28 | — | 50 |
| V | 32 | — | 55 |

Weapon contribution: add half the melee attack attribute at release before spell-power scaling.

Spell ID: `resonant_arcana:crimson_reversal`. Inspect ranks with `/resonantarcana spell crimson_reversal <rank>`.

### Ashen Rebirth

Burst forward along a clear path as a phoenix, leaving a burning silhouette. After 0.8 seconds the silhouette detonates. Grants brief Slow Falling; the movement itself deals no damage.

**Fire** · Cast **0.6s** · Cooldown **12s** · Range **8 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 32 | — | 45 |
| II | 40 | — | 51 |
| III | 48 | — | 57 |
| IV | 56 | — | 63 |
| V | 64 | — | 69 |

Base release / active phase: **1.2s**.

Spell ID: `resonant_arcana:ashen_rebirth`. Inspect ranks with `/resonantarcana spell ashen_rebirth <rank>`.


<a id="support"></a>
## Support & Defense

### Crimson Ward

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

Release three ground-shaking roars that damage, pull and taunt nearby enemies. You and eligible allies gain Resistance I for 3–7 seconds, depending on rank.

**Holy** · Cast **0.6s** · Cooldown **20s** · Range **8 blocks** · Radius / half-width **8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 45 |
| II | 6.25 | — | 50 |
| III | 7.5 | — | 55 |
| IV | 8.75 | — | 60 |
| V | 10 | — | 65 |

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
| I | 18 | — | 30 |
| II | 22.5 | — | 34 |
| III | 27 | — | 38 |
| IV | 31.5 | — | 42 |
| V | 36 | — | 46 |

Base release / active phase: **0.9s**.

Spell ID: `resonant_arcana:glacial_counter`. Inspect ranks with `/resonantarcana spell glacial_counter <rank>`.

### Thunderbrand

Enchant your weapon for ten seconds. Every third successful player melee hit releases chain lightning to that enemy and up to two nearby enemies. Spell damage cannot build charges; recasting refreshes the enchantment without stacking it.

**Lightning** · Cast **0.8s** · Cooldown **16s** · Range **16 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 55 |
| II | 15 | — | 61 |
| III | 18 | — | 67 |
| IV | 21 | — | 73 |
| V | 24 | — | 79 |

Base release / active phase: **10s**.

Spell ID: `resonant_arcana:thunderbrand`. Inspect ranks with `/resonantarcana spell thunderbrand <rank>`.

### Seraph Ascendant

Gain flight for twenty seconds; double-jump to fly. Nearby eligible allies gain 15% spell power and receive healing once per second. Slow Falling protects you when the blessing ends.

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

### Void Domain

Raise a vast black sphere at your casting location for fifteen seconds. Enemies inside suffer Slowness III, Weakness II and Darkness. You and eligible allies gain Resistance II, Regeneration II, Speed II and 25% spell power. Effects fade shortly after leaving; walls block their influence.

**Ender** · Cast **5s** · Cooldown **90s** · Range **0 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | — | — | 240 |
| II | — | — | 260 |
| III | — | — | 280 |
| IV | — | — | 300 |
| V | — | — | 320 |

Base release / active phase: **15s**.

Spell ID: `resonant_arcana:void_domain`. Inspect ranks with `/resonantarcana spell void_domain <rank>`.


<a id="summon"></a>
## Summons & Constructs

### Evoker's Edict

Send three staggered rows of spectral fangs along your aim. Each enemy takes one full hit.

**Evocation** · Cast **2.4s** · Cooldown **16s** · Range **24 blocks** · Radius / half-width **1.6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 44 | — | 80 |
| II | 55 | — | 90 |
| III | 66 | — | 100 |
| IV | 77 | — | 110 |
| V | 88 | — | 120 |

Spell ID: `resonant_arcana:evokers_edict`. Inspect ranks with `/resonantarcana spell evokers_edict <rank>`.

### Dread Effigy

Place a blackstone effigy on visible ground for four seconds. Four pulses damage nearby enemies and refresh Weakness II for 1.5 seconds.

**Ender** · Cast **1.2s** · Cooldown **11s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 55 |
| II | 6.25 | — | 65 |
| III | 7.5 | — | 75 |
| IV | 8.75 | — | 85 |
| V | 10 | — | 95 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:dread_effigy`. Inspect ranks with `/resonantarcana spell dread_effigy <rank>`.

### Night Bloom

Place a shadow trap. After a half-second arming period, an enemy within two blocks triggers one area explosion. Untriggered traps expire after four seconds.

**Ender** · Cast **1.1s** · Cooldown **11s** · Range **24 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 28 | — | 52 |
| II | 35 | — | 62 |
| III | 42 | — | 72 |
| IV | 49 | — | 82 |
| V | 56 | — | 92 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:night_bloom`. Inspect ranks with `/resonantarcana spell night_bloom <rank>`.

### Orbital Blades

Orbit three spatial blades around yourself for three seconds. Enemies can take one hit per second, up to three hits.

**Ender** · Cast **1s** · Cooldown **10s** · Range **0 blocks** · Radius / half-width **3.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 6 | — | 48 |
| II | 7.5 | — | 57 |
| III | 9 | — | 66 |
| IV | 10.5 | — | 75 |
| V | 12 | — | 84 |

Base release / active phase: **3s**.

Spell ID: `resonant_arcana:orbital_blades`. Inspect ranks with `/resonantarcana spell orbital_blades <rank>`.

### Wisp Lantern

Place a soul lantern on a deepslate post for four seconds. Four echo pulses damage visible enemies near it. Each pulse reveals enemies with a glowing outline for 1.5 seconds. Requires visible solid ground; the boundary ring shows its pulse radius.

**Eldritch** · Cast **1.2s** · Cooldown **11s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 5 | — | 52 |
| II | 6.25 | — | 62 |
| III | 7.5 | — | 72 |
| IV | 8.75 | — | 82 |
| V | 10 | — | 92 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:wisp_lantern`. Inspect ranks with `/resonantarcana spell wisp_lantern <rank>`.

### Spore Totem

Plant a mushroom totem on visible ground for four seconds. Four pulses damage nearby enemies and refresh Poison I for four seconds.

**Nature** · Cast **1.2s** · Cooldown **11s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 50 |
| II | 5 | — | 60 |
| III | 6 | — | 70 |
| IV | 7 | — | 80 |
| V | 8 | — | 90 |

Base release / active phase: **4s**.

Spell ID: `resonant_arcana:spore_totem`. Inspect ranks with `/resonantarcana spell spore_totem <rank>`.

### Consecration

Place a radiant altar on visible ground for four seconds. Four pulses damage enemies and heal eligible allies in the marked area.

**Holy** · Cast **1.2s** · Cooldown **12s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | 1 | 55 |
| II | 5 | 1.25 | 65 |
| III | 6 | 1.5 | 75 |
| IV | 7 | 1.75 | 85 |
| V | 8 | 2 | 95 |

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
| I | 12 | — | 60 |
| II | 15 | — | 67 |
| III | 18 | — | 74 |
| IV | 21 | — | 81 |
| V | 24 | — | 88 |

Base release / active phase: **10s**.

Spell ID: `resonant_arcana:phantom_formation`. Inspect ranks with `/resonantarcana spell phantom_formation <rank>`.

### Amber Talon

Conjure a spectral Subterranodon that dives along your aim. Its first collision slashes one enemy and briefly lifts it; the spirit fades after the strike.

**Nature** · Cast **0.6s** · Cooldown **5s** · Range **18 blocks** · Radius / half-width **1.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 12 | — | 28 |
| II | 15 | — | 36 |
| III | 18 | — | 44 |
| IV | 21 | — | 52 |
| V | 24 | — | 60 |

Base release / active phase: **2.25s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Primordial Caves · Novice**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:amber_talon`. Inspect ranks with `/resonantarcana spell amber_talon <rank>`.

### Tectonic Stampede

Summon three Tremorsaurus spirits that charge the marked ground in sequence. Each charge releases a ground shockwave that damages and staggers visible enemies.

**Nature** · Cast **3s** · Cooldown **35s** · Range **24 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 22 | — | 130 |
| II | 27.5 | — | 138 |
| III | 33 | — | 146 |
| IV | 38.5 | — | 154 |
| V | 44 | — | 162 |

Base release / active phase: **4.3s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Primordial Caves · Master**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:tectonic_stampede`. Inspect ranks with `/resonantarcana spell tectonic_stampede <rank>`.


<a id="melee"></a>
## Strikes & Drains

### Arcane Strike

Focus arcane energy directly on one visible aimed enemy. A woven link identifies the struck target; terrain blocks targeting.

**Evocation** · Cast **0.25s** · Cooldown **1.75s** · Range **24 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 21 |
| II | 10 | — | 25 |
| III | 12 | — | 29 |
| IV | 14 | — | 33 |
| V | 16 | — | 37 |

Spell ID: `resonant_arcana:arcane_strike`. Inspect ranks with `/resonantarcana spell arcane_strike <rank>`.

### Crimson Strike

Snap a crimson spark onto one visible aimed enemy. Blood droplets mark the impact; this attack does not restore health.

**Blood** · Cast **0.25s** · Cooldown **1.75s** · Range **24 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 7 | — | 21 |
| II | 8.75 | — | 25 |
| III | 10.5 | — | 29 |
| IV | 12.25 | — | 33 |
| V | 14 | — | 37 |

Spell ID: `resonant_arcana:blood_spark`. Inspect ranks with `/resonantarcana spell blood_spark <rank>`.

### Venom Sting

Burst an acid bubble on one visible aimed enemy, dealing direct damage and applying Poison I for four seconds.

**Nature** · Cast **0.25s** · Cooldown **2.5s** · Range **24 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 17 |
| II | 3.75 | — | 20 |
| III | 4.5 | — | 23 |
| IV | 5.25 | — | 26 |
| V | 6 | — | 29 |

Spell ID: `resonant_arcana:poison_spark`. Inspect ranks with `/resonantarcana spell poison_spark <rank>`.

### Ignite

Kindle one visible aimed enemy directly. Deal the listed impact damage and set it burning for three seconds; ordinary fire immunity still applies.

**Fire** · Cast **0.25s** · Cooldown **2.5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 3 | — | 17 |
| II | 3.75 | — | 20 |
| III | 4.5 | — | 23 |
| IV | 5.25 | — | 26 |
| V | 6 | — | 29 |

Spell ID: `resonant_arcana:ignite`. Inspect ranks with `/resonantarcana spell ignite <rank>`.

### Soul Siphon

Damage a visible aimed target and restore 40% of health actually removed, capped at 2 health at level I. Healing scales with spell power.

**Eldritch** · Cast **0.7s** · Cooldown **5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 28 |
| II | 10 | — | 33 |
| III | 12 | — | 38 |
| IV | 14 | — | 43 |
| V | 16 | — | 48 |

Spell ID: `resonant_arcana:soul_siphon`. Inspect ranks with `/resonantarcana spell soul_siphon <rank>`.

### Sanguine Drain

Damage a visible aimed target and restore 40% of health actually removed, capped at 2 health at level I. Healing scales with spell power.

**Blood** · Cast **0.7s** · Cooldown **5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 8 | — | 28 |
| II | 10 | — | 33 |
| III | 12 | — | 38 |
| IV | 14 | — | 43 |
| V | 16 | — | 48 |

Spell ID: `resonant_arcana:sanguine_drain`. Inspect ranks with `/resonantarcana spell sanguine_drain <rank>`.

### Withering Hex

Hex one visible enemy with direct damage, Poison I for four seconds and Wither I for three seconds.

**Nature** · Cast **0.25s** · Cooldown **5.5s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 4 | — | 25 |
| II | 5 | — | 30 |
| III | 6 | — | 35 |
| IV | 7 | — | 40 |
| V | 8 | — | 45 |

Spell ID: `resonant_arcana:withering_hex`. Inspect ranks with `/resonantarcana spell withering_hex <rank>`.

### Blood Reap

Sweep a crimson crescent across a 120-degree forward arc. Each visible enemy is hit once.

**Blood** · Cast **0.7s** · Cooldown **5.5s** · Range **6 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 16 | — | 34 |
| II | 20 | — | 40 |
| III | 24 | — | 46 |
| IV | 28 | — | 52 |
| V | 32 | — | 58 |

Spell ID: `resonant_arcana:blood_reap`. Inspect ranks with `/resonantarcana spell blood_reap <rank>`.

### Crimson Harvest

Strike an aimed enemy. Deal 50% more direct damage below 35% maximum health; a killing hit restores up to 2 health at level I.

**Blood** · Cast **1.1s** · Cooldown **9s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 22 | — | 48 |
| II | 27.5 | — | 57 |
| III | 33 | — | 66 |
| IV | 38.5 | — | 75 |
| V | 44 | — | 84 |

Spell ID: `resonant_arcana:crimson_harvest`. Inspect ranks with `/resonantarcana spell crimson_harvest <rank>`.

### Dusk Cleave

Carve a shadow crescent across the space in front of you, weakening enemies hit by the arc.

**Ender** · Cast **0.7s** · Cooldown **6s** · Range **6 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 36 |
| II | 22.5 | — | 43 |
| III | 27 | — | 50 |
| IV | 31.5 | — | 57 |
| V | 36 | — | 64 |

Spell ID: `resonant_arcana:dusk_cleave`. Inspect ranks with `/resonantarcana spell dusk_cleave <rank>`.

### Thunderclap

Release a broad thunder crescent and knock visible enemies away. Each target is hit once.

**Lightning** · Cast **0.9s** · Cooldown **7s** · Range **8 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 20 | — | 42 |
| II | 25 | — | 50 |
| III | 30 | — | 58 |
| IV | 35 | — | 66 |
| V | 40 | — | 74 |

Spell ID: `resonant_arcana:thunderclap`. Inspect ranks with `/resonantarcana spell thunderclap <rank>`.

### Spatial Shear

Cut a wide crescent through nearby space, striking each visible enemy in front of you once.

**Ender** · Cast **0.9s** · Cooldown **7.5s** · Range **8 blocks** · Radius / half-width **6 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 26 | — | 42 |
| II | 32.5 | — | 50 |
| III | 39 | — | 58 |
| IV | 45.5 | — | 66 |
| V | 52 | — | 74 |

Spell ID: `resonant_arcana:spatial_shear`. Inspect ranks with `/resonantarcana spell spatial_shear <rank>`.

### Memory Fracture

Strike an aimed enemy. If it is affected by Darkness, direct damage increases by 50%; combine with Soul Prison.

**Eldritch** · Cast **0.9s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 24 | — | 42 |
| II | 30 | — | 50 |
| III | 36 | — | 58 |
| IV | 42 | — | 66 |
| V | 48 | — | 74 |

Spell ID: `resonant_arcana:memory_fracture`. Inspect ranks with `/resonantarcana spell memory_fracture <rank>`.

### Radiant Sweep

Sweep a radiant crescent through visible enemies in front of you.

**Holy** · Cast **0.6s** · Cooldown **5.5s** · Range **6 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 30 |
| II | 17.5 | — | 36 |
| III | 21 | — | 42 |
| IV | 24.5 | — | 48 |
| V | 28 | — | 54 |

Spell ID: `resonant_arcana:radiant_sweep`. Inspect ranks with `/resonantarcana spell radiant_sweep <rank>`.

### Shatter

Strike one aimed enemy for 50% more direct damage while it is deeply frozen or affected by Slowness III or stronger. Combine with Glacial Prison or Absolute Zero.

**Ice** · Cast **0.9s** · Cooldown **8s** · Range **24 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 20 | — | 42 |
| II | 25 | — | 50 |
| III | 30 | — | 58 |
| IV | 35 | — | 66 |
| V | 40 | — | 74 |

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
| I | 28 | — | 50 |
| II | 35 | — | 56 |
| III | 42 | — | 62 |
| IV | 49 | — | 68 |
| V | 56 | — | 74 |

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
| I | 32 | — | 60 |
| II | 40 | — | 65 |
| III | 48 | — | 70 |
| IV | 56 | — | 75 |
| V | 64 | — | 80 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:krakens_grasp`. Inspect ranks with `/resonantarcana spell krakens_grasp <rank>`.

### Ice Spike Eruption

Five ice spikes erupt in a line across a visible ground location. Each enemy can take one hit per cast. Slows and chills enemies without freezing allies.

**Ice** · Cast **0.8s** · Cooldown **9s** · Range **16 blocks** · Radius / half-width **1.8 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 40 |
| II | 22.5 | — | 45 |
| III | 27 | — | 50 |
| IV | 31.5 | — | 55 |
| V | 36 | — | 60 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:ice_spike_eruption`. Inspect ranks with `/resonantarcana spell ice_spike_eruption <rank>`.

### Helvar’s Rift

Launch Helvar’s rift projectile. It stops at the first enemy or solid surface, dealing damage and applying brief Weakness.

**Ender** · Cast **0.9s** · Cooldown **8s** · Range **28 blocks** · Radius / half-width **1 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 24 | — | 40 |
| II | 30 | — | 45 |
| III | 36 | — | 50 |
| IV | 42 | — | 55 |
| V | 48 | — | 60 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:helvars_rift`. Inspect ranks with `/resonantarcana spell helvars_rift <rank>`.

### Kraken’s Wrath

Four Kraken tentacles strike around the marked ground before a central, triple-damage slam. Each enemy can take one outer hit and one central hit.

**Nature** · Cast **3.6s** · Cooldown **30s** · Range **20 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 18 | — | 110 |
| II | 22.5 | — | 122 |
| III | 27 | — | 134 |
| IV | 31.5 | — | 146 |
| V | 36 | — | 158 |

Base release / active phase: **7.5s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:krakens_wrath`. Inspect ranks with `/resonantarcana spell krakens_wrath <rank>`.

### Kraken’s Undertow

A Kraken tentacle rises at a visible ground location. Its undertow pulls nearby enemies toward the water before one heavy slam. Uses the Kraken’s red model and texture; allies and bosses are not pulled.

**Nature** · Cast **1.3s** · Cooldown **16s** · Range **18 blocks** · Radius / half-width **5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 26 | — | 65 |
| II | 32.5 | — | 71 |
| III | 39 | — | 77 |
| IV | 45.5 | — | 83 |
| V | 52 | — | 89 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:krakens_undertow`. Inspect ranks with `/resonantarcana spell krakens_undertow <rank>`.

### Yeti’s Glacial Wall

Raise seven lingering ice spikes in a crescent across visible ground. Each enemy takes at most one hit and is slowed and chilled. Terrain remains intact.

**Ice** · Cast **1s** · Cooldown **11s** · Range **16 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 20 | — | 50 |
| II | 25 | — | 55 |
| III | 30 | — | 60 |
| IV | 35 | — | 65 |
| V | 40 | — | 70 |

Base release / active phase: **3.1s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:yetis_glacial_wall`. Inspect ranks with `/resonantarcana spell yetis_glacial_wall <rank>`.

### Helvar’s Fracture

Helvar’s rift projectile breaks against the first enemy or wall, releasing an area burst that weakens visible enemies. Terrain remains intact.

**Ender** · Cast **1.3s** · Cooldown **12s** · Range **24 blocks** · Radius / half-width **3.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 22 | — | 60 |
| II | 27.5 | — | 66 |
| III | 33 | — | 72 |
| IV | 38.5 | — | 78 |
| V | 44 | — | 84 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:helvars_fracture`. Inspect ranks with `/resonantarcana spell helvars_fracture <rank>`.

### Helvar’s Swordfall

Mark visible ground and summon Helvar’s sword above it. The blade drops point-first after a brief warning, damaging and weakening enemies. Requires clear space overhead.

**Ender** · Cast **1.3s** · Cooldown **12s** · Range **18 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 36 | — | 60 |
| II | 45 | — | 66 |
| III | 54 | — | 72 |
| IV | 63 | — | 78 |
| V | 72 | — | 84 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:helvars_swordfall`. Inspect ranks with `/resonantarcana spell helvars_swordfall <rank>`.

### Sandworm’s Eruption

The Sandworm’s own sand columns emerge in a row toward the aimed ground. The rising columns strike enemies once per cast; terrain stays intact.

**Ender** · Cast **1s** · Cooldown **11s** · Range **18 blocks** · Radius / half-width **2 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 22 | — | 50 |
| II | 27.5 | — | 55 |
| III | 33 | — | 60 |
| IV | 38.5 | — | 65 |
| V | 44 | — | 70 |

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:sandworms_eruption`. Inspect ranks with `/resonantarcana spell sandworms_eruption <rank>`.

### Sandworm’s Quicksand

A spiralling sand vortex slows and draws visible enemies inward. Three sand columns then erupt around its rim for one final strike. Bosses and allies are never pulled; no terrain is changed.

**Ender** · Cast **1.2s** · Cooldown **15s** · Range **16 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 24 | — | 65 |
| II | 30 | — | 71 |
| III | 36 | — | 77 |
| IV | 42 | — | 83 |
| V | 48 | — | 89 |

Base release / active phase: **3s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:sandworms_quicksand`. Inspect ranks with `/resonantarcana spell sandworms_quicksand <rank>`.

### Dragon’s Volley

Conjure three blazing orbs, then launch them in sequence toward the marked ground. Each orb explodes against the first enemy or solid surface.

**Fire** · Cast **1.2s** · Cooldown **14s** · Range **16 blocks** · Radius / half-width **4 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 14 | — | 60 |
| II | 17.5 | — | 65 |
| III | 21 | — | 70 |
| IV | 24.5 | — | 75 |
| V | 28 | — | 80 |

Base release / active phase: **4s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:dragons_guard`. Inspect ranks with `/resonantarcana spell dragons_guard <rank>`.

### Dragon’s Cataclysm

Mark visible ground with an ember ring. After a one-second warning, a descending fireball releases one heavy shockwave. Requires clear space overhead.

**Fire** · Cast **1.6s** · Cooldown **18s** · Range **18 blocks** · Radius / half-width **4.5 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 44 | — | 85 |
| II | 55 | — | 93 |
| III | 66 | — | 101 |
| IV | 77 | — | 109 |
| V | 88 | — | 117 |

Base release / active phase: **2.7s**.

Requires **Bosses’Rise 2.1.2** on client and server. Visual adapters use its installed models and textures; original boss AI and attack routines do not run.

Spell ID: `resonant_arcana:dragons_pyre`. Inspect ranks with `/resonantarcana spell dragons_pyre <rank>`.


<a id="cataclysm"></a>
## Cataclysms

### DOOM

Open an elevated black hole that draws visible enemies inward, then collapses for one heavy area hit. Allies and terrain are protected.

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
| I | 80 | — | 440 |

Base release / active phase: **4.4s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:astral_apocalypse`. Inspect ranks with `/resonantarcana spell astral_apocalypse <rank>`.

### Crimson Eclipse

Release three blood tides around the mark, followed by a double-damage final tide. Heal for 8% of health actually removed, up to twenty health across the whole cast.

**Blood** · Cast **10s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 80 | — | 460 |

Base release / active phase: **4.5s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:crimson_eclipse`. Inspect ranks with `/resonantarcana spell crimson_eclipse <rank>`.

### World Pyre

Call five meteors around the mark. Each erupts in a five-block blast. A final fourteen-block firestorm deals 250% damage and ignites enemies.

**Fire** · Cast **12s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 72 | — | 500 |

Base release / active phase: **5s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:world_pyre`. Inspect ranks with `/resonantarcana spell world_pyre <rank>`.

### Void Coronation

Flood the marked area with shadow and drifting void embers. Three expanding damage pulses suppress enemies with Darkness and Weakness II, followed by a double-damage collapse.

**Ender** · Cast **11s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 88 | — | 480 |

Base release / active phase: **4.8s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:void_coronation`. Inspect ranks with `/resonantarcana spell void_coronation <rank>`.

### Heaven’s Thunder

Call six lightning columns around the mark, each striking a six-block area. A final fourteen-block thunderclap deals double damage. Columns can overlap.

**Lightning** · Cast **10s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 64 | — | 480 |

Base release / active phase: **5.6s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:heavens_thunder`. Inspect ranks with `/resonantarcana spell heavens_thunder <rank>`.

### Rift Collision

Pull visible enemies inward, then cut the area along two perpendicular lines. A final fourteen-block spatial implosion deals triple damage.

**Ender** · Cast **12s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 96 | — | 500 |

Base release / active phase: **5.2s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:rift_collision`. Inspect ranks with `/resonantarcana spell rift_collision <rank>`.

### Soul Requiem

Unleash three soul pulses at four, eight and twelve blocks from the mark. Enemies touched by a pulse are revealed and afflicted with Darkness. A final blast deals double damage across the area.

**Eldritch** · Cast **11s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 96 | — | 460 |

Base release / active phase: **4.6s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:soul_requiem`. Inspect ranks with `/resonantarcana spell soul_requiem <rank>`.

### Worldroot Blessing

Overgrow the mark with moss, flowering leaves and spores. Three pulses damage, poison and slow enemies. A final bloom deals double damage and heals eligible allies.

**Nature** · Cast **10s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 72 | — | 440 |

Base release / active phase: **4.8s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:worldroot_catastrophe`. Inspect ranks with `/resonantarcana spell worldroot_catastrophe <rank>`.

### Final Judgement

Summon a golden sword over the mark. Three holy pulses damage enemies and heal allies; the final verdict deals double damage, heals, cleanses afflictions and grants Resistance I for five seconds.

**Holy** · Cast **12s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 80 | — | 500 |

Base release / active phase: **5.2s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:final_judgement`. Inspect ranks with `/resonantarcana spell final_judgement <rank>`.

### Eternal Winter

Unleash a ten-second blizzard over the marked area. Eight pulses damage and freeze enemies with Slowness IV. A final burst deals triple damage. Terrain remains intact.

**Ice** · Cast **11s** · Cooldown **120s** · Range **40 blocks** · Radius / half-width **16 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 40 | — | 480 |

Base release / active phase: **10s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:eternal_winter`. Inspect ranks with `/resonantarcana spell eternal_winter <rank>`.

### Explosion

Lock your aim and channel five golden seals into a towering ignition beam. Release one immense explosion; the rising fireball and shockwave deal no extra hits. Brief Slowness and Weakness follow the blast.

**Fire** · Cast **7.7s** · Cooldown **120s** · Range **48 blocks** · Radius / half-width **16 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 256 | — | 480 |

Base release / active phase: **4.5s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:explosion`. Inspect ranks with `/resonantarcana spell explosion <rank>`.

### I Am Atomic

Anchor a violet containment sphere and compress mana at a spectral sword. Hold enemies inside, shelter allies and absorb hostile projectiles crossing the boundary. Release one devastating detonation. Moving more than three blocks interrupts the ritual; bosses resist containment.

**Ender** · Cast **13.5s** · Cooldown **180s** · Range **32 blocks** · Radius / half-width **16 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 512 | — | 500 |

Base release / active phase: **4s**.

Single-rank Legendary. The listed damage is one normal impact or pulse; the description states the final multiplier. Casting can be interrupted.

Spell ID: `resonant_arcana:i_am_atomic`. Inspect ranks with `/resonantarcana spell i_am_atomic <rank>`.

### Doom Beam

Compress a black singularity that turns emerald halfway through the charge. Release a green plasma beam that follows your movement and aim. Each enemy entering its path takes one hit; solid cover stops it.

**Ender** · Cast **13s** · Cooldown **150s** · Range **64 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 320 | — | 500 |

Base release / active phase: **9.5s**.

Spell ID: `resonant_arcana:doom_beam`. Inspect ranks with `/resonantarcana spell doom_beam <rank>`.

### Magnetar Annihilation

Compress opposing magnetic poles, then unleash a blue-white annihilation beam following your aim for seven seconds. Each enemy entering it takes one heavy hit. Solid cover stops the beam.

**Lightning** · Cast **11s** · Cooldown **130s** · Range **64 blocks** · Radius / half-width **3 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 320 | — | 480 |

Base release / active phase: **7s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Magnetic Caves · Cataclysm**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:magnetar_annihilation`. Inspect ranks with `/resonantarcana spell magnetar_annihilation <rank>`.

### Luxtructosaurus Extinction

Manifest a Luxtructosaurus at the mark. Four tephra eruptions scorch the area before a double-damage extinction blast. Allies and terrain are protected.

**Fire** · Cast **12s** · Cooldown **140s** · Range **40 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 80 | — | 500 |

Base release / active phase: **6.3s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Primordial Caves · Cataclysm**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:lux_extinction`. Inspect ranks with `/resonantarcana spell lux_extinction <rank>`.

### Nuclear Strike

Mark a nuclear strike. A descending warhead detonates after a visible warning, releasing one immense blast and Alex’s Caves mushroom cloud. Enemies are weakened by the fallout; allies and terrain remain protected.

**Nature** · Cast **13s** · Cooldown **150s** · Range **48 blocks** · Radius / half-width **16 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 384 | — | 500 |

Base release / active phase: **9s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Toxic Caves · Cataclysm**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:nuclear_strike`. Inspect ranks with `/resonantarcana spell nuclear_strike <rank>`.

### Abyssal Deluge

Open a deep-sea vortex at the mark. Four tidal pulses drag enemies inward before a double-damage pressure collapse. Deep One magic and towering splashes trace the battlefield.

**Ice** · Cast **11s** · Cooldown **140s** · Range **40 blocks** · Radius / half-width **16 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 72 | — | 480 |

Base release / active phase: **6.5s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Abyssal Chasm · Cataclysm**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:abyssal_deluge`. Inspect ranks with `/resonantarcana spell abyssal_deluge <rank>`.

### Forlorn Reckoning

Call forth a colossal Forsaken apparition. Three growing sonar shockwaves damage, reveal and darken enemies before a double-damage final roar.

**Ender** · Cast **12s** · Cooldown **140s** · Range **36 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 88 | — | 490 |

Base release / active phase: **6s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Forlorn Hollows · Cataclysm**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:forlorn_reckoning`. Inspect ranks with `/resonantarcana spell forlorn_reckoning <rank>`.

### Candyland Catastrophe

Raise a candy-witch vortex over the mark. Four peppermint storms batter enemies while a Gum Worm emerges below, then a double-damage candy burst ends the spell. Allies and terrain are protected.

**Evocation** · Cast **11s** · Cooldown **140s** · Range **36 blocks** · Radius / half-width **14 blocks**.

| Rank | Base damage | Base healing | Mana |
| --- | ---: | ---: | ---: |
| I | 72 | — | 480 |

Base release / active phase: **6.5s**.

Requires **Alex’s Caves 2.0.10** and its Citadel dependency on client and server. **Candy Cavity · Cataclysm**. Native visuals are provided by the installed mod; no boss AI or terrain destruction runs.

Spell ID: `resonant_arcana:candy_catastrophe`. Inspect ranks with `/resonantarcana spell candy_catastrophe <rank>`.
