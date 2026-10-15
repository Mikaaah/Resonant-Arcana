# Kingdom Wands — NeoForge 1.21.1

[Download the current alpha JAR](downloads/kingdom-wands-0.1.0-alpha-neoforge-1.21.1.jar?raw=true) · [Validation](VALIDATION.md)

**0.1.0-alpha — independent first recreation, not a complete Bukkit plugin port.**

Kingdoms-style combat magic, using vanilla item textures, coloured trails, rings,
waves and client-side firework bursts. Inspired by the supplied KingdomWands
7.5 / ExtensiveMagicWands 1.4.1 plugins and the EmpireWandPlus project.

## Install

- Minecraft **1.21.1**, Java **21**, NeoForge **21.1.219 or later in the 21.1 series**.
- Put `kingdom-wands-0.1.0-alpha-neoforge-1.21.1.jar` in `mods` on **client and server**.
- No Bukkit/Paper server, original plugin, Iron's Spells, or other dependency is required.
- Use a test instance for this alpha. No terrain generation changes are made.

## Obtain wands

Use the **Kingdom Wands** creative tab, `/give @s kingdomwands:empire_wand`, or:

```
/kingdomwands give empire_wand
/kingdomwands give blood_wand
/kingdomwands give celcrius_wand
/kingdomwands status
```

Giving wands requires operator permission. Status is available to everyone.
Survival recipes and unlock progression are deliberately left to the modpack;
these are distinct registered items, so normal KubeJS recipes can produce them.

## Controls

- **Left-click:** cast selected spell. Casting suppresses the vanilla attack.
- **Right-click:** next spell, including when pointing at a block.
- **Sneak + right-click:** previous spell.
- **G:** alternate cast key; **V:** next spell (sneak for previous).
- Rebind both keys under **Controls → Kingdom Wands** to resolve pack conflicts.
- A wand must be in the **main hand**. Offhand casting is not implemented.
- Selection appears in the tooltip and action bar, and is saved per ItemStack.
- Wands cannot mine blocks.

If a combat mod intercepts left-click, use the alternate cast key. Set
`classicMouseCasting=false` in both client and server configurations if desired.
**No direct Epic Fight integration has been implemented or tested yet.**

## Included spells

The Empire Wand has **20 selections**:
Spark, Empire Spark, Blood Spark, Poison Spark, Blood Wave, Poison Wave,
Flame Wave, Confuse, Stun, Capture, Empire Comet, Fire Comet, Empire Pulse,
Fire Pulse, Fireball, Ignite, Launch, Leap, Lightning and Smite.

The Blood Wand has **5 selections**:
Blood Wave, Blood Spark, Blood Explode, Blood Stun and Blood Ward.

The Celcrius Wand has **6 selections**:
Fire Comet, Fire Twister, Flame Wave, Flame Shock Wave, Flamethrower and Fireball.

There are **26 distinct spells** across all three wands.

| Behaviour | Initial implementation |
| --- | --- |
| Spark / Fireball | Travelling bolt that stops at a solid collision or living target |
| Targeted sparks / Ignite | Aim-selected living target; particles and themed effects |
| Waves | Travelling coloured arc; each entity is affected once per wave |
| Comets | Slower bolt with area damage, knockback and a firework impact |
| Pulses / Shock Wave | Expanding ring around the caster; each entity affected once |
| Confuse | Nausea and short blindness; does not rewrite movement controls |
| Stun | Slowness and weakness; does not disable AI or Epic Fight animations |
| Capture | Three-second restraint using slowness and velocity suppression; not a physical cage |
| Blood Explode | Targeted area burst |
| Blood Ward | Four-second particle ward that redirects incoming non-allied projectiles; not a placed block shield |
| Fire Twister | Stationary stacked rotating rings; repeated area effects |
| Flamethrower | Short channel following the caster's current aim |
| Launch / Leap | Launch a target upward / propel the caster forward |
| Lightning / Smite | Visual lightning and targeted area damage; no damaging lightning entity |

## Behaviour and limits

This is newly written NeoForge code. It does **not** claim exact original spell
balance, timing or visual fidelity. Parameters are initial alpha values.

- No blocks are created, removed, or ignited by spells. Burning entities can
  still take ordinary fire damage. Firework bursts are client visuals, not rockets.
- Server PvP settings, vanilla scoreboard team friendly-fire settings, and the
  caster's own tameable pets are respected by spell targeting.
- **FTB Teams/Chunks, claim protection APIs and NPC faction systems are not integrated.**
  Use `allowPvp=false` for a non-PvP deployment, and test pack-specific protections.
- Solid-block targeting and collision are checked server-side. Timed spells stop
  at unloaded chunks, death, logout, dimension changes or expiration.
- Normal damage immunity frames are preserved. Multiple hit attempts do not
  guarantee multiple damage events during immunity frames.
- Limits: eight active timed spells per caster; configurable global limit.
- Client must have the mod to receive its visual burst packets.
- No mana economy, custom bindings, spell scrolls, recipes, configurable spell
  lists, sword arts, Cursed Bow, Assassin's Dagger, or other wand families yet.

## Configuration

`config/kingdomwands-common.toml` (restart after changing for predictable results):

| Key | Default | Purpose |
| --- | --- | --- |
| `allowPvp` | `true` | Allow affecting players when server and teams also allow it |
| `classicMouseCasting` | `true` | Capture attack input while holding a wand |
| `damageMultiplier` | `1.0` | Scale initial spell damage, not vanilla fire/poison damage |
| `cooldownMultiplier` | `1.0` | Scale each spell cooldown |
| `particleDensity` | `2` | Coloured trail density, 1–3 |
| `maxActiveSpells` | `128` | Global timed spell limit, 8–512 |

Cooldowns are per player and spell, shared across copies of the same wand.
Selection and cooldowns are validated on the server; cooldowns reset at restart.
Common configurations are local to each installation, not automatically synced.

## Build

With Java 21 installed:

```
./gradlew build
```

On Windows: `gradlew.bat build`. Output is in `build/libs`.
The development-only dedicated-server fixture runs with
`./gradlew runServer -Dkingdomwands.integrationTest=true`; it creates an arena
and stops the test server, so use an isolated development run directory.
It is **disabled during normal gameplay**.

## Credits

- JustDylan23 / _JustDylan_ and jetp250: ExtensiveMagicWands / Wands,
  https://github.com/JustDylan23/Wands
- Myuuiii and contributors: EmpireWandPlus,
  https://github.com/Myuuiii/EmpireWandPlus
- ExortisMagic Team: supplied KingdomWands 7.5 plugin.
- Minecraft's existing textures, particles, and sounds are referenced by ID;
  they are not redistributed in this mod.

No original plugin JARs, Bukkit libraries or original source files are bundled.
This project's independently written code is MIT licensed; see LICENSE.
