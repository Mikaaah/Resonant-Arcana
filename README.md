# Resonant Arcana

[Project status](https://github.com/Mikaaah/Mikaaah/blob/master/STATUS.md) · [Report an issue](https://github.com/Mikaaah/Resonant-Arcana/issues) · [Creator’s CurseForge projects](https://www.curseforge.com/members/mikaaah/projects)

An **Iron’s Spells ’n Spellbooks addon** for **Minecraft 1.21.1 / NeoForge**. **163 core spells**, seventeen optional Bosses’Rise spells, **twenty-four optional Alex’s Caves spells**, three legendary spellbooks and one debug wand. Every spell has a functional category and a native magic school.

[Download 0.30.0-alpha](downloads/resonant-arcana-0.30.0-alpha-neoforge-1.21.1.jar?raw=true) · [Every spell and rank](SPELLS.md) · [Configuration](CONFIGURATION.md) · [Changelog](CHANGELOG.md)

## Equipment

The supplied textures are used unchanged. These are normal, inscribable Iron’s spellbooks. Equip one in the **Curios spellbook slot** to activate its perks; holding it alone does not grant equipment attributes.

| Book | Slots | Equipped perks |
| --- | ---: | --- |
| Grimoire of Runic Echoes | 14 | +450 mana, +15% Evocation power, +15% Ender power, +10% cooldown reduction |
| Codex of the Phoenix | 12 | +400 mana, +25% Fire power, +15% Fire magic resistance, +15% mana regeneration |
| Deathchill Grimoire | 12 | +425 mana, +25% Ice power, +15% Eldritch power, +10% cast-time reduction |

Each recipe uses a netherite Iron’s spellbook, a nether star and themed materials. Inscribe scrolls at Iron’s inscription table. The books accept both native Iron’s spells and addon spells; they have no fixed loadout.

**Debug Wand:** one creative/testing item with every enabled spell. It uses the supplied copper-chisel texture and has no survival recipe. Press **X** to open the searchable spell archive. Search names, schools and keywords such as `ender buff aoe`, then narrow the results with category filters. Selecting a spell never casts it. The wand uses Iron’s normal mana, cast-time and cooldown rules. Sneak-use a higher-rank matching scroll in the offhand to teach that spell’s rank.

## Spell categories

All scrolls share **one Spells creative tab**, alongside the Equipment tab. Scrolls are ordered by school, category and name; server-disabled spells are hidden. Functional categories remain in descriptions and as filters in the debug archive. Schools remain Evocation, Blood, Fire, Ender, Lightning, Eldritch, Nature, Holy and Ice.

## God-tier magic

A new tier above Cataclysm: **20-second base cast**, **600 mana**, **one Legendary rank** and a **four-minute base cooldown**. Equipment and normal server settings still apply. The giant marked field locks when casting begins. Mana is consumed only when the native cast completes; interruption removes the ritual and its owned sound layers.

| School | God-tier spell |
| --- | --- |
| Lightning | Thunder of the Heavens — 200 violet lightning strikes over a 28-block field |
| Evocation | Arcane Creation — six collapsing star sectors and a final constellation blast |
| Blood | Crimson Apotheosis — blood tethers, five tithes, capped healing and a final nova |
| Fire | Solar Supernova — warned compression followed by an expanding single-hit solar front |
| Ender | Eventide Horizon — four crossing dimensional cuts and a final field fold |
| Ice | Absolute Glaciation — advancing frost front, cold pulses and a shattering finish |
| Nature | Verdant Awakening — a flowering grove with hostile roots and allied healing |
| Holy | Heaven’s Covenant — holy verdicts, cleansing, healing, defense and +35% allied spell power |
| Eldritch | Soul Ascendancy — five soul harvests, an absorption ward and a final soul burst |

Alex’s Caves adds **Planetary Polarity, Primordial Sovereign, Tremorzilla Cataclysm, Abyssal World Tide, Forsaken Eclipse and Confection Apocalypse**. Bosses’Rise adds **Kraken’s World Drown, Yeti’s World Heart, Helvar’s Final Edict, Sandworm’s World Devourer and Dragon’s Primordial Inferno**. These eleven arts appear only with their supported optional mod installed.

All God spells protect allies and terrain and respect solid cover. Purple lightning uses visual geometry rather than destructive vanilla lightning entities. New spell icons are temporary school-coloured placeholders awaiting the supplied artwork.

## Scroll attunement

Core non-God spells remain available in Iron’s Scroll Forge. Addon and God spells have **50 deterministic crafting recipes** using paper, the appropriate ink, a unique catalyst and themed cave/boss materials. Cataclysms add a nether star; God recipes add both a nether star and dragon breath. Optional recipes obey the same version gates as spell registration. See [every ingredient](SCROLL_RECIPES.md).

Iron’s already provides nine school-specific scroll textures, which are retained without copying those assets. God scrolls add the native enchantment glint. The existing single Spells tab, searchable tags and wrapped information remain.

## Alex’s Caves spells

Install **Alex’s Caves 2.0.10** and its Citadel dependency on **both client and server** to unlock the eighteen base cave arts below and six God-tier cave arts above. The adapter uses the installed mod’s particles, sounds, models and attack themes. Other versions leave the adapter disabled; the core addon works without Alex’s Caves.

| Cave area | Novice | Master | Cataclysm |
| --- | --- | --- | --- |
| Magnetic Caves | Polarity Bolt | EMP Surge | Magnetar Annihilation |
| Primordial Caves | Amber Talon | Primordial Instinct | Lux’s Caldera |
| Toxic Caves | Acid Glob | Tremorzilla Breath | Nuclear Strike |
| Abyssal Chasm | Brine Bubble | Hullbreaker’s Breach | Abyssal Deluge |
| Forlorn Hollows | Desolate Dagger | Forsaken Sonar | Forlorn Reckoning |
| Candy Cavity | Gumball Volley | Frostmint Hex | Candyland Catastrophe |

Cataclysms have **11–13-second base casts**, **480–500 mana costs** and a single Legendary rank. Tremorzilla’s beam uses its native scrolling material and animated impact model, without a miniature boss. Green is the default; hold Amber Curiosity offhand for purple retro breath or a Tectonic Shard for orange. All beam muzzles are at torso level. The large beams follow current aim and hit each enemy once. Nuclear Strike marks a location and drops a visual bomb, giving two seconds of warning before a protected blast and native mushroom cloud. No native boss AI or destructive nuclear explosion runs. All offensive spells retain ally protection and solid-cover checks.

Search the spell archive with `alexscaves`, a biome name or a tier; native spell tags include `integrations/alexscaves`, `caves/<biome>` and `tiers/<tier>`. These scrolls use the existing single Spells tab.

## Rituals and new spells

**Void Domain:** a five-second cast establishes a fixed, fourteen-block sphere for fifteen seconds at base power. It weakens enemies and strongly empowers eligible allies; walls and allegiance rules apply. The sphere is dark from outside and translucent inside.

See the [catalogue audit](CATALOGUE_AUDIT.md) for every reviewed damage value. Tooltips distinguish damage per hit from multi-stage sequences.

### Void Exile

Four-second Ender control spell: banish one hostile target for ten seconds, leaving a violet rift. On return it takes 20 base damage, then Weakness II for five seconds. Bosses and mounted or leashed creatures resist. Ally and PvP protection apply.

Cataclysms charge through a **large seal beneath the caster**. Dense native particles spiral down from 8–13 blocks above into the seal, with a small amount flowing into the caster. Individual charge sounds repeat at stronger volume throughout the cast. Optional **AAAParticles** are limited to floor seals, Explosion’s five seals and the original Doom black-hole effect, now centred with its red spirals five blocks higher. Native particles remain available without AAA. Four spells use owner-supplied recordings at their original pitch; see [timing and overrides](CONFIGURATION.md#recorded-spells-in-026).

- **I Am Atomic:** thirteen-and-a-half-second anchored ritual. A translucent violet sphere contains captured enemies, absorbs hostile boundary-crossing projectiles and shelters allies. Mana gathers at a summoned netherite sword before one 512-base-damage detonation over sixteen blocks. Allies and terrain are protected. The full chant and initial blast play, followed by a shortened, faded tail.
- **Explosion:** redesigned Megumin audio, a fixed 7.7-second charge and immediate 256-damage detonation over sixteen blocks. The target locks at the start; five orange-gold seals feed a vertical ignition column; multicoloured charge sparks lead into an expanding, rising fireball and ground shockwave. Brief slowness and weakness follow the blast.
- **Seraph Ascendant:** twenty seconds of flight with radiant particle wings. Nearby allies gain +15% spell power and periodic healing. Cleanup removes its own flight modifier and grants brief Slow Falling.
- **Doom Beam:** a thirteen-second black-hole charge turns emerald halfway through, then releases a giant green plasma beam that follows the caster’s position and aim, with an Iron’s beacon core, original scrolling plasma texture, green-tinted wisps and electricity. Doom keeps its own gold/red black-hole visuals and seven-layer native ambience.
- **Arcane Devastator:** a fixed 6.5-second charge followed by a 7.4-second continuous violet beam that follows your position and aim. Each enemy entering the beam can take one hit. New cover clips the beam as it fires.
- **Final Judgement:** an actual descending golden sword accompanies holy damage, healing and the final cleansing verdict. Its visual scale adapts to available overhead room.
- **Eternal Winter:** a sixteen-block, ten-second blizzard with eight freezing pulses and a triple-damage final burst.
- **Worldroot Blessing:** moss and flowering-azalea fragments, spores and drifting leaves accompany its control, damage and ally-healing bloom.
- **Doom:** retains inward red accretion streams; three dark rings replace the opaque core, all sharing the same centre.

The [spell guide](SPELLS.md) lists the complete mechanics and baseline values. Cataclysms have high mana costs and long native cast bars. Equipment and server configuration can modify their final values.

## Controls and commands

Normal books use **Iron’s native controls**. The debug wand additionally supports:

| Input | Action |
| --- | --- |
| G / left-click | Cast selected spell |
| V / right-click | Next spell; cancel current windup |
| Shift + V / right-click | Previous spell, or learn offhand scroll |
| X | Open categorized archive |
| Arrow keys / page buttons | Change archive page |

Use `/resonantarcana spell <id> [rank]` for details, `/resonantarcana scroll <id> <rank>` for scrolls, `/resonantarcana give <item_id>` for equipment, and `/resonantarcana status` for version and active spells. Give/scroll commands require operator permission. Debug input can be configured locally.

## Installation and compatibility

Requires **Java 21**, **NeoForge 21.1.219–21.1.x**, and **Iron’s Spells ’n Spellbooks 1.21.1-3.16.3** with its dependencies, including Curios. Install the same addon version on both sides.

**Bosses’Rise 2.1.2** optionally adds seventeen boss arts using its installed entities, geometry, textures and sounds. Other versions leave that adapter disabled. No third-party boss assets are bundled. Visual adapters cannot run original boss attacks, persist in saves or drop loot.

Offensive spells respect server PvP, scoreboard friendly-fire settings and owned pets. Healing uses visible eligible allies. Terrain is protected and spell tasks are bounded. Claim protection, FTB Teams and custom combat damage types need their own integration; no general compatibility with every combat mod is implied.

## Building and validation

Supply compile-only API JARs, then run with Java 21:

```sh
./gradlew build -PironsSpellbooksJar=/path/irons_spellbooks.jar -PbossesRiseJar=/path/bossesrise.jar -PgeckolibJar=/path/geckolib.jar -PcuriosJar=/path/curios.jar -PalexsCavesJar=/path/alexscaves-2.0.10.jar -PcitadelJar=/path/citadel.jar
```

The packaged-asset check verifies equipment, spell icons and tags, exactly four selective AAA effects and eight recorded OGG streams, and rejects retired wand textures. Dedicated-server fixtures cover registration, native casting, mana, cancellation, targeting, equipment perks and cleanup. **0.30.0-alpha:** dedicated-server validation passed with neither optional addon, each addon separately, and both together. The final combined run passed **24,057 checks** across 204 spells. Coverage includes native charging and cancellation, locked God-tier targets, mana, exact cave-art hit schedules, ally and cover rules, following beams, projectile protection, cave ceiling clearance, finite sound and visual cleanup, tags, tooltips, and actual recipe-manager selection for all 50 themed scroll recipes. In-game client visuals and sound require playtesting.

## Credits and license

By Towns & Kingdoms. [MIT license](LICENSE). User-supplied item artwork is retained unchanged. Vanilla/Iron’s particles and installed Bosses’Rise / Alex’s Caves assets remain owned by their respective authors. Spell ideas draw on RPG class roles and familiar fantasy magic; the owner-supplied Explosion recording is credited separately in [Audio credits](AUDIO_CREDITS.md).
