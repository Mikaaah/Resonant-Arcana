# Resonant Arcana

[Project status](https://github.com/Mikaaah/Mikaaah/blob/master/STATUS.md) · [Report a bug](https://github.com/Mikaaah/Resonant-Arcana/issues/new?template=bug_report.yml) · [Suggest a feature](https://github.com/Mikaaah/Resonant-Arcana/issues/new?template=feature_request.yml) · [Questions](https://github.com/Mikaaah/Resonant-Arcana/issues/new?template=question.yml) · [Creator's CurseForge projects](https://www.curseforge.com/members/mikaaah/projects)

[![Minecraft 1.21.1](https://img.shields.io/badge/Minecraft-1.21.1-5B8731?style=flat-square)](https://github.com/Mikaaah/Resonant-Arcana)
[![NeoForge](https://img.shields.io/badge/Loader-NeoForge-D97724?style=flat-square)](https://neoforged.net/)
[![Alpha](https://img.shields.io/badge/Release-Alpha-F59E0B?style=flat-square)](CHANGELOG.md)
[![10 Wands · 137 + 4 Spells](https://img.shields.io/badge/Content-10%20Wands%20%7C%20137%20%2B%204%20Spells-7756B4?style=flat-square)](SPELLS.md)
[![MIT License](https://img.shields.io/badge/License-MIT-64748B?style=flat-square)](LICENSE)

An **Iron’s Spells ’n Spellbooks addon** for Minecraft **1.21.1 / NeoForge**. Ten craftable wands, **137 core spells**, plus **four optional Bosses’Rise spells**, original wand artwork, and magic built around distinct attacks, movement, control and support roles.

[Download 0.16.0-alpha](downloads/resonant-arcana-0.16.0-alpha-neoforge-1.21.1.jar?raw=true) · [Every spell and level](SPELLS.md) · [Configuration](CONFIGURATION.md) · [Changelog](CHANGELOG.md) · [Issues](https://github.com/Mikaaah/Resonant-Arcana/issues)

## New in 0.16

Ten new RPG and spellsword spells: **Glacial Counter**, **Rift Draw**, **Crimson Reversal**, **Thunderbrand**, **Last Stand**, **Ashen Rebirth**, **Phantom Formation**, **Funeral Bell**, **Spellbreaker**, and optional **Kraken’s Wrath**. All ten support levels I–V, scroll inscription and their matching wands.

Reactive guards, melee-triggered lightning and spectral swords connect spells with weapon combat. Crimson Reversal uses Iron’s native three-second recast window: a free return cast, followed by the normal cooldown. Melee triggers use successful native player-attack damage; custom combat damage types require testing or explicit integration.

## Optional Bosses’Rise integration

Install **Bosses’Rise 2.1.2** on both client and server to add **Kraken’s Grasp**, **Ice Spike Eruption**, and **Helvar’s Rift**, and **Kraken’s Wrath**. These are native Iron’s spells with levels I–V. Their models, animations and sounds come from the installed Bosses’Rise mod; no third-party assets are included in this addon. Without it, the 137-spell core kit remains available. Other Bosses’Rise versions leave this integration disabled until validated.

Boss attacks are adapted for player use: visual entities cannot run native boss attacks, and our shared engine handles damage, ally protection, PvP and walls. Kraken’s Grasp is a delayed tentacle slam with slowing, rather than an enemy-grabbing mechanic. See [spell details and timing](SPELLS.md#optional-bossesrise-spells).

## Ten complete spell kits

New in 0.14: **Warscream**, **Soulblast**, three spellsword attacks and three weapon/party buffs. Expanded wand kits use pages: click the arrow buttons or press Left/Right; number shortcuts select a spell on the current page.

Each wand carries **12–16 spells**. Use a wand directly or inscribe the same spells into an existing Iron’s spellbook. Mana, casting bars, interruption, cooldowns, school attributes and player animations use Iron’s native systems.

| Wand | Iron’s school | Play style |
| --- | --- | --- |
| ![Arcane](src/main/resources/assets/resonant_arcana/textures/item/arcane_wand.png) Arcane | Evocation | Bolts, displacement, bindings, volleys and spectral fang rows |
| ![Blood](src/main/resources/assets/resonant_arcana/textures/item/blood_wand.png) Blood | Blood | Needles, crimson crescents, life drain, tethers and conditional executions |
| ![Cinder](src/main/resources/assets/resonant_arcana/textures/item/cinder_wand.png) Cinder | Fire | Firestorms, dragon breath, rising rushes and falling meteors |
| ![Dark](src/main/resources/assets/resonant_arcana/textures/item/dark_wand.png) Dark | Ender | Curses, invisibility, shadow traps, effigies and DOOM |
| ![Thunder](src/main/resources/assets/resonant_arcana/textures/item/thunder_wand.png) Thunder | Lightning | Chains, ricochets, thunderclaps and piercing storm lances |
| ![Rift](src/main/resources/assets/resonant_arcana/textures/item/rift_wand.png) Rift | Ender | Gravity wells, orbiting blades, spatial cuts and Prismatic Ruin |
| ![Soul](src/main/resources/assets/resonant_arcana/textures/item/soul_wand.png) Soul | Eldritch | Sculk, echo blasts, soul lanterns, sonic pressure and Soul Cannon |
| ![Venom](src/main/resources/assets/resonant_arcana/textures/item/venom_wand.png) Venom | Nature | Poison fans, thorns, spore totems, acid rain and cleansing mobility |
| ![Holy](src/main/resources/assets/resonant_arcana/textures/item/holy_wand.png) Holy | Holy | Healing, blessings, consecration, cleansing, wards and judgement rays |
| ![Frost](src/main/resources/assets/resonant_arcana/textures/item/frost_wand.png) Frost | Ice | Ice serpents, hail, freezing domains, crystal fans and shatter combos |

## Charge, release, impact

Basic projectiles and movement stay responsive. Larger spells have readable windups and distinct releases: a marked meteor impact, a growing black hole, an advancing ice front, or a broad spatial orb formed from opposing energies. **Soul Cannon** keeps its two-second void charge, travelling echo blast, four-block firework cadence and large warden/crystal detonation.

Effects use different shapes as well as school colours: sweeping crescents, orbiting blades, rising feathers, lantern totems, falling hail, frost spines, shadow petals and braided fusion trails. Charging effects use motes and flowing energy without camera-facing casting circles.

Layered **Iron’s and vanilla sound events** provide preparation, release and impact cues. Personal impact feedback lets the caster hear distant hits; nearby players hear positional audio. No parent-mod audio files are bundled.

**AAAParticles is optional.** Installing it adds 222 packaged native effects; Iron’s particles and vanilla trails remain available without it. The addon includes no native renderer or extra particle-mod dependency.

## Progression

**127 spells have levels I–V.** DOOM remains a single-level Legendary spell. Without equipment, direct damage and healing scale to 100%, 125%, 150%, 175%, and 200% of their level-I values. Mana costs increase with rank; high ranks can require more than the default mana pool, so mana-capacity equipment matters. Some utility spells improve duration, absorption or blink range; their effect amplifiers and other limits are documented in the [spell reference](SPELLS.md).

Craft a wand with **two sticks, two Arcane Essence and its school ingredient**. Recipes unlock when you acquire Arcane Essence; recipe viewers show the exact pattern. Ingredients range from amethyst and blaze powder to echo shards and blue ice.

A new wand has its whole kit at level I. To upgrade one spell:

1. Hold its wand in your main hand.
2. Place a matching higher-level **Iron’s scroll** in your offhand.
3. Sneak-use the wand. It learns that rank, selects the spell and consumes one scroll.

Other-wand scrolls, already-known ranks and server-disabled levels are rejected without consumption. Ranks save separately on each wand. Higher-rank scrolls can also be inscribed into normal Iron’s spellbooks. The addon does not add custom spellbooks.

## Controls and spell information

| Input | Action |
| --- | --- |
| Left-click or **G** | Cast the selected spell |
| Right-click or **V** | Next spell; cancel an active charge |
| Sneak + right-click or sneak + **V** | Previous spell, or learn the offhand scroll |
| **X** | Open the 12-spell picker |
| Picker keys **1–9, 0, −, =** | Select slots 1–12 |

Selection never casts a spell. The picker, wand tooltip and selected-spell HUD show rank and baseline values. Iron’s own spell information includes school scaling. Bindings can be changed under **Controls → Resonant Arcana**. Disable left-click casting locally if another combat mod captures attack inputs.

| Command | Function |
| --- | --- |
| `/resonantarcana info` | Describe the held wand’s selected spell and rank |
| `/resonantarcana spell <spell_id> [level]` | Inspect a spell’s baseline function and values |
| `/resonantarcana status` | Show version and active spell count |
| `/resonantarcana give <wand_id>` | Give a wand; operator permission required |
| `/resonantarcana scroll <spell_id> <level>` | Give a native scroll at that rank; operator permission required |
| `/arcana_visuals` | Report the optional AAAParticles renderer and loaded effects |
| `/arcana_visuals test` | Play a short local visual test |

Wand IDs use their current names, such as `resonant_arcana:arcane_wand`, `resonant_arcana:cinder_wand` and `resonant_arcana:rift_wand`.

## Installation

Requires **Java 21**, **Minecraft 1.21.1**, **NeoForge 21.1.219 or later within 21.1.x**, and **Iron’s Spells ’n Spellbooks 1.21.1-3.16.3** with its own dependencies. AAAParticles **2.3.3** is the tested optional version.

Install the same Resonant Arcana version on **client and server**. Version **0.13 is a clean namespace break**: all addon IDs now use `resonant_arcana`. Remove the previous addon JAR. Older wands, scrolls, spellbook entries and key bindings are not migrated. Obtain new items and reinscribe spells using the new IDs. There are no legacy registry aliases.

Settings remain in the world’s **serverconfig** and each client’s local config file. See the [configuration guide](CONFIGURATION.md). Servers can disable direct wand casting for a spellbook-only pack.

## Multiplayer and compatibility

Offensive spells respect server PvP, scoreboard friendly-fire rules and the caster’s own tameable pets. Healing affects the caster, pets and scoreboard teammates; by default it also affects other players when both players have no scoreboard team. Competitive servers can disable teamless healing.

Spells require appropriate line of sight and leave terrain intact. Visual and simulation budgets prevent unbounded effect spawning. Wards, life drains, healing, stuns and restraints share recovery within their respective groups; changing items does not bypass those cooldowns.

Dedicated claim-protection, FTB Teams and Epic Fight integrations are not included. This is an **alpha**: automated server and native effect checks do not replace testing balance, animation, audio and GUI behaviour in your modpack.

## Design references

The kits draw on RPG movement, control and support roles, including [Wynncraft’s class design](https://wynncraft.com/help/classes?class=mage), and familiar anime magic ideas such as charged beams and opposing-energy fusion. Spell implementations, icons, wand sprites and effect geometry are original. No anime or Wynncraft art or audio is distributed.

## Building

Use Java 21. Supply local compile-only copies of Bosses’Rise 2.1.2 and GeckoLib 4.8.2, then run:

```sh
./gradlew build -PbossesRiseJar=/path/to/bossesrise.jar -PgeckolibJar=/path/to/geckolib.jar
```

On Windows use `gradlew.bat` with the same properties. Optionally supply `-PironsSpellbooksJar=/path/to/irons_spellbooks.jar` for a local Iron’s API copy. Dependency JARs are used only to compile and are never included in the addon. Output is written to `build/libs/`. For an offline build, supply `-PironsSpellbooksJar=/path/to/irons_spellbooks-1.21.1-3.16.3.jar`; the dependency is compile-only and is never bundled.

The validated catalogue at `src/main/resources/assets/resonant_arcana/catalogue.json` supplies runtime defaults. `tools/build_docs.py`, `tools/build_spell_icons.py` and `tools/build_wands.py` regenerate the public spell guide, icons, wand sprites and recipes. Original Effekseer assets can be rebuilt with `tools/build_effects.py`; Pillow and Effekseer 1.80.7 are required for artwork and particle exports. `verifyEffects` checks the effect manifest and packaged texture paths during the build.

## Validation

0.16.0-alpha passed **6,270 dedicated-server checks without Bosses’Rise** and **6,547 with Bosses’Rise 2.1.2**. The build also validates the texture paths of all 222 packaged effects. In-game visuals, sound balance, controls and custom combat-mod interactions still need modpack playtesting.

## Contributing

Found a bug or want to improve the project? Read the [contribution guide](CONTRIBUTING.md) for reporting details, testing expectations and pull requests.

## Credits and license

By Towns & Kingdoms. [MIT license](LICENSE).
