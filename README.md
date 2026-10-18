# Resonant Arcana

[Project status](https://github.com/Mikaaah/Mikaaah/blob/master/STATUS.md) · [Report an issue](https://github.com/Mikaaah/Resonant-Arcana/issues) · [Creator’s CurseForge projects](https://www.curseforge.com/members/mikaaah/projects)

An **Iron’s Spells ’n Spellbooks addon** for **Minecraft 1.21.1 / NeoForge**. **152 core spells**, twelve optional Bosses’Rise spells, three legendary spellbooks and one debug wand. Every spell has a functional category and a native magic school.

[Download 0.25.0-alpha](downloads/resonant-arcana-0.25.0-alpha-neoforge-1.21.1.jar?raw=true) · [Every spell and rank](SPELLS.md) · [Configuration](CONFIGURATION.md) · [Changelog](CHANGELOG.md)

## Equipment

The supplied textures are used unchanged. These are normal, inscribable Iron’s spellbooks. Equip one in the **Curios spellbook slot** to activate its perks; holding it alone does not grant equipment attributes.

| Book | Slots | Equipped perks |
| --- | ---: | --- |
| Grimoire of Runic Echoes | 14 | +450 mana, +15% Evocation power, +15% Ender power, +10% cooldown reduction |
| Codex of the Phoenix | 12 | +400 mana, +25% Fire power, +15% Fire magic resistance, +15% mana regeneration |
| Deathchill Grimoire | 12 | +425 mana, +25% Ice power, +15% Eldritch power, +10% cast-time reduction |

Each recipe uses a netherite Iron’s spellbook, a nether star and themed materials. Inscribe scrolls at Iron’s inscription table. The books accept both native Iron’s spells and addon spells; they have no fixed loadout.

**Debug Wand:** one creative/testing item with every enabled spell. It uses the supplied copper-chisel texture and has no survival recipe. Press **X** to open the categorized spell archive. Selecting a spell never casts it. The wand uses Iron’s normal mana, cast-time and cooldown rules. Sneak-use a higher-rank matching scroll in the offhand to teach that spell’s rank.

The ten old school wands, their recipes and their textures have been removed. Existing spell IDs and learned Iron’s spellbook entries remain unchanged; old wand items are not migrated into a new item.

## Spell categories

All scrolls share **one Spells creative tab**, alongside the Equipment tab. Functional categories remain in descriptions and as filters in the debug archive. Schools remain Evocation, Blood, Fire, Ender, Lightning, Eldritch, Nature, Holy and Ice.

## Rituals and new spells

Cataclysms charge through a **large seal beneath the caster**. Dense native particles spiral down from 8–13 blocks above into the seal, with a small amount flowing into the caster. Individual charge sounds repeat at stronger volume throughout the cast. Optional **AAAParticles** are limited to floor seals, Explosion’s five seals and the original Doom black-hole effect, now centred with its red spirals five blocks higher. Native particles remain available without AAA. Four spells use owner-supplied recordings at their original pitch; see [timing and overrides](CONFIGURATION.md#recorded-spells-in-025).

- **I Am Atomic:** thirteen-and-a-half-second anchored ritual. A translucent violet sphere contains captured enemies, absorbs hostile boundary-crossing projectiles and shelters allies. Mana gathers at a summoned netherite sword before one 512-base-damage detonation over sixteen blocks. Allies and terrain are protected. The full chant and initial blast play, followed by a shortened, faded tail.
- **Explosion:** redesigned Megumin audio, a fixed 7.7-second charge and immediate 256-damage detonation over sixteen blocks. The target locks at the start; five crimson/amber seals feed the diagonal beacon. Brief slowness and weakness follow the blast.
- **Seraph Ascendant:** twenty seconds of flight with radiant particle wings. Nearby allies gain +15% spell power and periodic healing. Cleanup removes its own flight modifier and grants brief Slow Falling.
- **Doom Beam:** a thirteen-second black-hole charge turns emerald halfway through, then releases a giant green plasma beam with white core and branching lightning. Doom keeps its own gold/red black-hole visuals and seven-layer native ambience.
- **Arcane Devastator:** a fixed 6.5-second charge followed by a 7.4-second cosmic beam. Each enemy entering the beam can take one hit. New cover clips the beam as it fires.
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

**Bosses’Rise 2.1.2** optionally adds twelve boss arts using its installed entities, geometry, textures and sounds. Other versions leave that adapter disabled. No third-party boss assets are bundled. Visual adapters cannot run original boss attacks, persist in saves or drop loot.

Offensive spells respect server PvP, scoreboard friendly-fire settings and owned pets. Healing uses visible eligible allies. Terrain is protected and spell tasks are bounded. Claim protection, FTB Teams and custom combat damage types need their own integration; no general compatibility with every combat mod is implied.

## Building and validation

Supply compile-only API JARs, then run with Java 21:

```sh
./gradlew build -PironsSpellbooksJar=/path/irons_spellbooks.jar -PbossesRiseJar=/path/bossesrise.jar -PgeckolibJar=/path/geckolib.jar -PcuriosJar=/path/curios.jar
```

The packaged-asset check verifies the four item assets, exactly four selective AAA effects and eight recorded OGG streams and rejects retired wand textures. Dedicated-server fixtures cover registration, native casting, mana, cancellation, targeting, equipment perks and cleanup. **0.25.0-alpha:** 10,505 dedicated-server checks pass without Bosses’Rise and 11,284 with it. In-game client visuals and sound require playtesting.

## Credits and license

By Towns & Kingdoms. [MIT license](LICENSE). User-supplied item artwork is retained unchanged. Vanilla/Iron’s particles and installed Bosses’Rise assets remain owned by their respective authors. Spell ideas draw on RPG class roles and familiar fantasy magic; the owner-supplied Explosion recording is credited separately in [Audio credits](AUDIO_CREDITS.md).
