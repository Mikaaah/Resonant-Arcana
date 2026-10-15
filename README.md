# Resonant Arcana

Eight magic wands and 59 spells for Minecraft 1.21.1 on NeoForge. Each wand has its own spell list, particle effects and casting sounds.

[Download 0.8.0-alpha](downloads/resonant-arcana-0.8.0-alpha-neoforge-1.21.1.jar?raw=true) · [Spell guide](SPELLS.md) · [Changelog](CHANGELOG.md) · [Report an issue](https://github.com/Mikaaah/Kingdom-Wands/issues)

## Iron’s Spells ’n Spellbooks

With **Iron’s Spells ’n Spellbooks 1.21.1-3.16.3** installed, all 59 spells also register as spellbook spells. Find their scrolls in the Resonant Arcana creative tab, craft them through Iron’s scroll forge, and inscribe them into a compatible spellbook using its inscription table.

Spellbook casting uses Iron’s mana, cooldowns, school spell power, damage types and animations. Soul Cannon and Sonic Boom use its charge bar; they release without charging a second time. Spells currently have one level, with damage scaling through spell power. School assignments are Evocation for Arcane, Fire for Cinder, Blood, Lightning, Nature for Venom, Ender for Rift and Dark, and Eldritch for Soul. Soul spells do not require Eldritch research.

Wands remain available without Iron’s. Set `enableWandCasting = false` to make spellbooks the only casting route in a modpack. Iron’s and its required dependencies are installed separately.

## Soul Cannon

Select **Soul Cannon** on the Soul Wand. A two-second void charge releases a huge travelling echo blast, with small soul firework bursts every four blocks and a large sculk/crystal impact. It has a 48-block range, deals 16 base damage within a five-block impact radius and has a 14-second base cooldown. Aim during the charge; the released shot keeps its direction. Put the wand away to cancel charging.

With **AAAParticles**, the spell uses dedicated void absorption, echo-head, firework and detonation effects. Vanilla sonic, sculk and coloured firework particles remain available without it. Update both client and server to this version.

## Wands

| Wand | Spells | Theme |
| --- | ---: | --- |
| Arcane | 10 | Arcane attacks, movement and utility |
| Blood | 7 | Blood magic, piercing attacks and life drain |
| Cinder | 8 | Fire, flamethrowers and area damage |
| Dark | 6 | Shadows, curses and weakness |
| Thunder | 8 | Lightning, chain attacks and storms |
| Rift | 6 | Gravity, displacement and suspension |
| Soul | 9 | Sculk, sonic attacks and echo magic |
| Venom | 5 | Poison, wither and lingering clouds |

## Installation

Requires **Minecraft 1.21.1**, **NeoForge 21.1.219 or later within 21.1.x**, and **Java 21**.

Install the JAR on both client and server. Wands are available in the Resonant Arcana creative tab or through `/resonantarcana give <wand_id>`. For example:

```mcfunction
/resonantarcana give soul_wand
```

Giving wands requires operator permission. Survival recipes are not included.

## Controls

Hold a wand in your main hand.

| Input | Action |
| --- | --- |
| Left-click or **G** | Cast |
| Right-click or **V** | Next spell |
| Sneak + right-click or sneak + **V** | Previous spell |

Key bindings can be changed under **Controls → Resonant Arcana**. The selected spell and its effect appear in the item tooltip.

## Configuration

Settings are in `config/kingdomwands-common.toml`.

| Setting | Default | Purpose |
| --- | --- | --- |
| `enableWandCasting` | `true` | Allow direct wand casting; disable to use Iron’s spellbooks instead |
| `allowPvp` | `true` | Allow spells to affect players, subject to server and scoreboard rules |
| `classicMouseCasting` | `true` | Enable left-click casting; disable on both sides for combat-mod input conflicts |
| `damageMultiplier` | `1.0` | Scale direct spell damage |
| `cooldownMultiplier` | `1.0` | Scale cooldowns |
| `soundVolume` | `1.0` | Scale spell audio; also respects the Players sound slider |
| `impactVolume` | `1.0` | Additional impact volume multiplier, from 0 to 3 |
| `casterImpactFeedback` | `true` | Hear your own ranged hits without distance fade |
| `aaaParticles` | `true` | Enable the full optional AAAParticles spell effects on this client |
| `maxAaaEffectsPerTick` | `32` | Limit new native effects per client tick, from 8 to 96 |
| `particleDensity` | `2` | Trail density, from 1 to 3 |
| `maxParticlePointsPerTick` | `256` | Shared trail budget per server tick |
| `maxActiveSpells` | `128` | Maximum active timed spells across the server |

Cooldowns are shared across copies of a wand. Related healing, ward, stun and restraint spells also share cooldowns within their groups. Common configurations are local to each installation.

Cast and impact sounds use layered effects. Your own impacts play directly to
you; nearby players hear positional audio at the hit. Impact feedback is limited
to once every eight ticks per cast to keep repeated and area hits controlled.

## Compatibility

Effects combine vanilla particles, original short whooshes and impacts, and layered Minecraft sounds. With **AAAParticles 2.2.3 or later in the 2.x series** installed on the client, all 59 spells gain directional casting sweeps, bright projectile cores and flowing wakes, travelling crescents, energy beams, drain streams, lightning, large impact bursts and animated area effects. Wards surround the caster; cages surround their target; fields and pulses follow their gameplay radius. Soul effects mix cyan and amethyst, fire uses embers, blood uses crimson fragments, and venom leaves green mist. This is an experimental visual integration; AAAParticles is optional and is not bundled. Set `aaaParticles = false` locally to disable it. Clients and servers must both use 0.8.0-alpha because the visual protocol has changed.

Spells respect server PvP settings, scoreboard friendly fire and the caster's own tameable pets. They do not edit terrain. Claim-protection integrations and dedicated Epic Fight support are not included; use the alternate cast key if another combat mod captures left-click.

Resonant Arcana replaces Kingdom Wands. Remove the older JAR before installing it. Existing wands, selected spells and settings keep working; saved registry IDs and the config filename remain under `kingdomwands` for compatibility. The older `/kingdomwands` command remains an alias.

When upgrading from 0.1–0.3, retained spells keep their selection. An Arcane spell that moved to another wand resets that wand's selection to Spark.

If the extra effects are missing, run `/kwvisuals` for client diagnostics or `/kwvisuals test` for a three-second local effect test. The diagnostic reports loaded versions, configuration, packaged and registered effects, received visual packets and effect-loading failures.

## Building

With Java 21 installed, run `./gradlew build` on Linux/macOS or `gradlew.bat build` on Windows. Output is written to `build/libs/`. The build uses Iron’s public API as a compile-only dependency. For an offline build, supply a local JAR with `-PironsSpellbooksJar=/path/to/irons_spellbooks-1.21.1-3.16.3.jar`; it is never included in the output. Original effects, audio and spell icons can be rebuilt using the scripts in `tools/`.

## Credits and license

Inspired by [Wands / ExtensiveMagicWands](https://github.com/JustDylan23/Wands) by JustDylan23 and jetp250, [EmpireWandPlus](https://github.com/Myuuiii/EmpireWandPlus), and KingdomWands by ExortisMagic Team.

By Towns & Kingdoms. [MIT license](LICENSE).
