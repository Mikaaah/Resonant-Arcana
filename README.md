# Kingdom Wands

Eight magic wands and 58 spells for Minecraft 1.21.1 on NeoForge. Each wand has its own spell list, particle effects and casting sounds.

[Download 0.5.0-alpha](downloads/kingdom-wands-0.5.0-alpha-neoforge-1.21.1.jar?raw=true) · [Spell guide](SPELLS.md) · [Changelog](CHANGELOG.md) · [Report an issue](https://github.com/Mikaaah/Kingdom-Wands/issues)

## Wands

| Wand | Spells | Theme |
| --- | ---: | --- |
| Empire | 10 | Arcane attacks, movement and utility |
| Blood | 7 | Blood magic, piercing attacks and life drain |
| Celcrius | 8 | Fire, flamethrowers and area damage |
| Dark | 6 | Shadows, curses and weakness |
| Thunder | 8 | Lightning, chain attacks and storms |
| Mephi | 6 | Gravity, displacement and suspension |
| Soul | 8 | Sculk, sonic attacks and echo magic |
| Venom | 5 | Poison, wither and lingering clouds |

## Installation

Requires **Minecraft 1.21.1**, **NeoForge 21.1.219 or later within 21.1.x**, and **Java 21**.

Install the JAR on both client and server. Wands are available in the Kingdom Wands creative tab or through `/kingdomwands give <wand_id>`. For example:

```mcfunction
/kingdomwands give soul_wand
```

Giving wands requires operator permission. Survival recipes are not included.

## Controls

Hold a wand in your main hand.

| Input | Action |
| --- | --- |
| Left-click or **G** | Cast |
| Right-click or **V** | Next spell |
| Sneak + right-click or sneak + **V** | Previous spell |

Key bindings can be changed under **Controls → Kingdom Wands**. The selected spell and its effect appear in the item tooltip.

## Configuration

Settings are in `config/kingdomwands-common.toml`.

| Setting | Default | Purpose |
| --- | --- | --- |
| `allowPvp` | `true` | Allow spells to affect players, subject to server and scoreboard rules |
| `classicMouseCasting` | `true` | Enable left-click casting; disable on both sides for combat-mod input conflicts |
| `damageMultiplier` | `1.0` | Scale direct spell damage |
| `cooldownMultiplier` | `1.0` | Scale cooldowns |
| `soundVolume` | `1.0` | Scale spell audio; also respects the Players sound slider |
| `impactVolume` | `1.0` | Additional impact volume multiplier, from 0 to 3 |
| `casterImpactFeedback` | `true` | Hear your own ranged hits without distance fade |
| `aaaParticles` | `true` | Add optional AAAParticles impact rings and flashes on this client |
| `particleDensity` | `2` | Trail density, from 1 to 3 |
| `maxParticlePointsPerTick` | `256` | Shared trail budget per server tick |
| `maxActiveSpells` | `128` | Maximum active timed spells across the server |

Cooldowns are shared across copies of a wand. Related healing, ward, stun and restraint spells also share cooldowns within their groups. Common configurations are local to each installation.

Cast and impact sounds use layered effects. Your own impacts play directly to
you; nearby players hear positional audio at the hit. Impact feedback is limited
to once every eight ticks per cast to keep repeated and area hits controlled.

## Compatibility

Effects use vanilla Minecraft particles and sounds. With **AAAParticles 2.2.3 or later in the 2.x series** installed on the client, impacts also produce custom expanding rings and flashes in each wand’s colours. Soul impacts mix cyan and amethyst. This is an experimental visual integration; AAAParticles is optional and is not bundled. Set `aaaParticles = false` locally to disable it.

Spells respect server PvP settings, scoreboard friendly fire and the caster's own tameable pets. They do not edit terrain. Claim-protection integrations and dedicated Epic Fight support are not included; use the alternate cast key if another combat mod captures left-click.

When upgrading from 0.1–0.3, retained spells keep their selection. An Empire spell that moved to another wand resets that wand's selection to Spark.

## Building

With Java 21 installed, run `./gradlew build` on Linux/macOS or `gradlew.bat build` on Windows. Output is written to `build/libs/`.

## Credits and license

Inspired by [Wands / ExtensiveMagicWands](https://github.com/JustDylan23/Wands) by JustDylan23 and jetp250, [EmpireWandPlus](https://github.com/Myuuiii/EmpireWandPlus), and KingdomWands by ExortisMagic Team.

By Towns & Kingdoms. [MIT license](LICENSE).
