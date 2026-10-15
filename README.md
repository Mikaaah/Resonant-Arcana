# Resonant Arcana

Ten magic wands and 66 spells for Minecraft 1.21.1 on NeoForge. Each wand has its own spell list, particle effects and casting sounds.

[Download 0.11.0-alpha](downloads/resonant-arcana-0.11.0-alpha-neoforge-1.21.1.jar?raw=true) · [Spell guide](SPELLS.md) · [Changelog](CHANGELOG.md) · [Report an issue](https://github.com/Mikaaah/Resonant-Arcana/issues)

## Casting and progression

**Iron’s Spells ’n Spellbooks is now required.** Wands and spellbooks use the same native casting system: mana, cooldowns, charge bars, interruption, school attributes and player animations. Existing wands remain usable as level-I casting implements while higher-level scrolls can be inscribed into spellbooks.

Basic bolts and movement spells remain instant. Waves and control spells have short windups; comets, storms and large area spells take about one to two seconds to prepare. The new heavy spells take 2.5–4 seconds. Charges gather themed particles before the release, with no camera-facing sigil. Cycling a wand cancels its charge; holding the cast button does not restart it. Mana is spent when the spell completes.

Most spells have **five levels**; heavy spells have **three**. DOOM is a single-level Legendary spell. Each level above I adds 25% base spell power and increases mana cost, on top of Iron’s school and gear scaling. Cast-speed and cooldown modifiers remain effective.

| Spell | Base windup | Level-I payoff |
| --- | ---: | --- |
| Astral Comet | 1s | 14 damage, 4.5-block impact radius |
| Smite | 1.4s | 18 damage, 5-block impact radius |
| Amethyst Nova | 1.2s | 14 damage, 5.5-block impact radius |
| Singularity | 1.6s | 18 damage, 6-block impact radius |
| Storm Call | 1.6s | Up to five 5-damage pulses over 4.8 seconds |
| Sonic Scream | 1s | 14 damage in a wide, 12-block forward cone; Enderman scream and sonic pressure fronts |
| Sanctuary | 2.5s | Restore up to 12 health and cleanse poison, wither, blindness and darkness |
| Glacial Rupture | 2.5s | 28 damage; an advancing ice front that chills targets |
| Evoker’s Edict | 2.4s | 26 damage; three staggered rows of spectral fangs |
| DOOM | 4s | 72 damage; a growing black hole that devours its core before collapsing |
| Soul Cannon | 2s | Existing 16-damage travelling cannon and impact sequence |
| Solar Cataclysm | 3s | 32 damage, 6-block radius; warning followed by a descending meteor |
| Tempest Spear | 2.5s | 30 damage; piercing lightning lance reaching 56 blocks |
| Event Horizon | 3.2s | Pull for 1.5 seconds, then 36 damage in a 7-block radius |

Solar Cataclysm costs 90 base mana, Tempest Spear 85, and Event Horizon 100; their base cooldowns are 20, 18 and 24 seconds. These three spells begin at Epic rarity and have three levels. Damage and timing above are level-I values before equipment and configuration. The meteor begins beneath a solid ceiling; it never passes through that ceiling. All three respect solid cover and leave terrain intact.

Find scrolls in the Resonant Arcana creative tab or use Iron’s scroll forge and inscription table. Existing Iron’s spellbooks accept these spells; this version adds no custom spellbooks. Set `enableWandCasting = false` to allow casting through spellbooks exclusively.

All nine Iron’s schools are represented: Evocation for Arcane, Fire for Cinder, Blood, Lightning, Nature for Venom, Ender for Rift and Dark, Eldritch for Soul, Holy, and Ice for Frost. Soul spells do not require Eldritch research.

## New schools and heavy spells

**Sanctuary** heals the caster, nearby scoreboard teammates and owned pets within eight blocks, subject to line of sight. It leaves hostile mobs unharmed and cannot overheal. It costs 80 base mana with an 18-second cooldown; Holy spell power increases its healing.

**Glacial Rupture** sends an 18-block ice front forwards, widening to a six-block half-width. Each enemy takes one direct hit, followed by chilling. It costs 85 base mana with an 18-second cooldown.

**Evoker’s Edict** sends three rows of spectral fang effects along your aim, reaching up to 24 blocks. Each enemy takes one full hit, even where rows overlap. It costs 80 base mana with a 16-second cooldown.

**DOOM** is a single-level Legendary Ender spell on the Dark Wand. After a four-second cast, the black hole grows to a 12-block radius, pulls visible enemies inward, starts devouring its core after two seconds, and collapses after 4.5 seconds. Each enemy takes at most one 72-damage hit. It costs 100 base mana with a 60-second cooldown. PvP and ally protections still apply; it never edits terrain.

**Sonic Scream** replaces this addon’s Sonic Boom. Its saved spell ID stays `kingdomwands:sonic_boom`, so existing scrolls and selections remain valid. It charges for one second and releases a 140-degree forward cone over 12 blocks, with an Enderman scream layered over a sonic boom. Each enemy is hit once; solid cover stops the damage.

The new Holy and Frost wands use vanilla item appearances. All new spells are also available as scrolls for Iron’s spellbooks. AAAParticles adds separate sonic pressure fronts, an opaque black-hole core and accretion discs, golden healing feathers, rising ice spires and paired spectral fangs. Native Iron’s particles remain active without AAAParticles.

## Soul Cannon

Select **Soul Cannon** on the Soul Wand. A two-second void charge releases a huge travelling echo blast, with small soul firework bursts every four blocks and a large sculk/crystal impact. It has a 48-block range, deals 16 base damage within a five-block impact radius and has a 14-second base cooldown. Aim during the charge; the released shot keeps its direction. Cycle the wand spell to cancel charging.

With **AAAParticles**, the spell uses dedicated void absorption, echo-head, firework and detonation effects. Vanilla sonic, sculk and coloured firework particles remain available without it. Update both client and server to this version.

## Wands

| Wand | Spells | Theme |
| --- | ---: | --- |
| Arcane | 11 | Arcane attacks, movement and utility |
| Blood | 7 | Blood magic, piercing attacks and life drain |
| Cinder | 9 | Fire, flamethrowers and area damage |
| Dark | 7 | Shadows, curses and weakness |
| Thunder | 9 | Lightning, chain attacks and storms |
| Rift | 7 | Gravity, displacement and suspension |
| Soul | 9 | Sculk, sonic attacks and echo magic |
| Venom | 5 | Poison, wither and lingering clouds |
| Holy | 1 | Area healing and cleansing |
| Frost | 1 | Advancing ice spires and chilling |

## Installation

Requires **Minecraft 1.21.1**, **NeoForge 21.1.219 or later within 21.1.x**, **Java 21**, and **Iron’s Spells ’n Spellbooks 1.21.1-3.16.3** with its required dependencies. **AAAParticles** remains optional.

Install the JAR on both client and server. Wands are available in the Resonant Arcana creative tab or through `/resonantarcana give <wand_id>`. For example:

```mcfunction
/resonantarcana give soul_wand
```

Giving wands requires operator permission. Wands have no survival recipes; scroll crafting and spellbook inscription use Iron’s systems.

## Controls

Hold a wand in your main hand.

| Input | Action |
| --- | --- |
| Left-click or **G** | Cast |
| Right-click or **V** | Next spell; cancel an active charge |
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
| `cooldownMultiplier` | `1.0` | Scale native spell cooldowns before Iron’s modifiers |
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

Casting, windup and impact audio use Iron’s sound events with selected vanilla accents. Soul Cannon retains its warden/firework/crystal sound sequence. No Iron’s audio files are copied into this mod. Iron’s particles now supplement every school even without AAAParticles: blood splashes, fire embers, acid bubbles, electricity, ender fragments, soul fire and arcane wisps. Their paths and timing differ by school.

With **AAAParticles 2.2.3 or later in the 2.x series**, the main casting, projectile, wave, field and impact effects use different school geometry: rising flame tongues, falling blood ribbons, low venom billows, forked lightning, shadow veils, inward rift debris, echo crystals and orbiting arcane diamonds. Blood Ward uses membranes; Echo Ward and Soul Prison use crystal formations. The three new heavy spells have dedicated charge, attack and impact effects. Soul Cannon keeps its existing assets. Generic firework bursts are reserved for soul magic.

AAAParticles is optional and is not bundled. Set `aaaParticles = false` locally to disable it. Update both client and server to **0.11.0-alpha**.

Spells respect server PvP settings, scoreboard friendly fire and the caster's own tameable pets. They do not edit terrain. Claim-protection integrations and dedicated Epic Fight support are not included; use the alternate cast key if another combat mod captures left-click.

Resonant Arcana replaces Kingdom Wands. Remove the older JAR before installing it. Existing wands, selected spells and settings keep working; saved registry IDs and the config filename remain under `kingdomwands` for compatibility. The older `/kingdomwands` command remains an alias.

When upgrading from 0.1–0.3, retained spells keep their selection. An Arcane spell that moved to another wand resets that wand's selection to Spark.

If the extra effects are missing, run `/kwvisuals` for client diagnostics or `/kwvisuals test` for a three-second local effect test. The diagnostic reports loaded versions, configuration, packaged and registered effects, received visual packets and effect-loading failures.

## Building

With Java 21 installed, run `./gradlew build` on Linux/macOS or `gradlew.bat build` on Windows. Output is written to `build/libs/`. The build uses Iron’s public API as a compile-only dependency. For an offline build, supply a local JAR with `-PironsSpellbooksJar=/path/to/irons_spellbooks-1.21.1-3.16.3.jar`; it is never included in the output. Original effects and spell icons can be rebuilt using the scripts in `tools/`.

## Credits and license

By Towns & Kingdoms. [MIT license](LICENSE).
