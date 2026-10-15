# Kingdom Wands — NeoForge 1.21.1

[Download the current alpha JAR](downloads/kingdom-wands-0.4.0-alpha-neoforge-1.21.1.jar?raw=true) · [Validation](VALIDATION.md)

**0.4.0-alpha — expanded independent recreation, not a complete Bukkit plugin port.**

Kingdoms-style combat magic, using vanilla item textures, coloured trails, rings,
waves and client-side firework bursts. Inspired by the supplied KingdomWands
7.5 / ExtensiveMagicWands 1.4.1 plugins and the EmpireWandPlus project.

## Upgrade from an earlier alpha

Remove the old Kingdom Wands JAR and install this version on **both client and
server**. Keep the existing common config. Existing item IDs are retained.
Numeric spell selections from 0.1–0.3 are migrated using their original wand
layouts. A moved/removed Empire spell falls back to Spark; retained spells stay
selected. New selections store a stable spell ID as well as an index.

## Install

- Minecraft **1.21.1**, Java **21**, NeoForge **21.1.219 or later in the 21.1 series**.
- Put `kingdom-wands-0.4.0-alpha-neoforge-1.21.1.jar` in `mods` on **client and server**.
- No Bukkit/Paper server, original plugin, Iron's Spells, or other dependency is required.
- Use a test instance for this alpha. No terrain generation changes are made.

## Obtain wands

Use the **Kingdom Wands** creative tab, `/give @s kingdomwands:empire_wand`, or:

```
/kingdomwands give empire_wand
/kingdomwands give blood_wand
/kingdomwands give celcrius_wand
/kingdomwands give dark_wand
/kingdomwands give thunder_wand
/kingdomwands give mephi_wand
/kingdomwands give soul_wand
/kingdomwands give venom_wand
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

**8 wands and 58 distinct spells**, with each spell assigned to one wand.
See [the complete spell review and lineups](SPELL_REVIEW.md) for every spell,
its role, cooldown and the reasons for the revised layout.

| Wand | Selections | Identity |
| --- | --- | --- |
| Empire | 10 | Arcane precision, mobility and utility; adds Arcane Volley |
| Blood | 7 | Crimson pressure; adds Sanguine Drain and Blood Lance |
| Celcrius | 8 | Fire channels and area denial; adds Ignite and Ember Veil |
| Dark | 6 | Shadow pursuit, weakness and delayed Dread Mark |
| Thunder | 8 | Lightning, chain attacks and storms; gains Lightning and Smite |
| Mephi | 6 | Gravity and displacement; Suspension replaces Gravity Choke |
| Soul | 8 | Echo shard; sculk, sonic attacks, warden audio and amethyst sparks |
| Venom | 5 | Spider eye; poison, wither and lingering Miasma Field |

Empire's elemental spells move to matching wands. Fire Pulse is removed in
favour of Flame Shock Wave. Dark's Soul Seeker/Aura are now named Shadow
Seeker/Dread Aura. Mephi uses an ender eye texture; Soul keeps the echo shard.

Blood uses dark crimson dust and red fragments. Fire uses flame accents,
Dark uses soul wisps, Thunder uses electric sparks, Mephi uses portal effects,
Venom uses green spores, and Empire uses enchant effects. Soul retains teal
sculk effects, violet/amethyst fragments, shriek ripples and sonic rings.
Comets have twin spiral trails and impacts use coloured firework sparks.

Casting, impacts and sustained spells have themed audio. Cast audio is sent
directly to the caster and separately to nearby players. Sustained audio is
limited to once per second per active spell. Use `soundVolume` and Minecraft's
**Players** sound slider to adjust volume.

**AAAParticles remains optional and is not used by this version.** Effects use
native Minecraft assets; an Effekseer integration would need authored effects.

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
  lists, sword arts, Cursed Bow or Assassin's Dagger yet.

## Configuration

`config/kingdomwands-common.toml` (restart after changing for predictable results):

| Key | Default | Purpose |
| --- | --- | --- |
| `allowPvp` | `true` | Allow affecting players when server and teams also allow it |
| `classicMouseCasting` | `true` | Capture attack input while holding a wand |
| `damageMultiplier` | `1.0` | Scale initial spell damage, not vanilla fire/poison damage |
| `cooldownMultiplier` | `1.0` | Scale each spell cooldown |
| `soundVolume` | `1.0` | Spell volume multiplier, 0–2, respecting Minecraft sound settings |
| `particleDensity` | `2` | Coloured trail density, 1–3 |
| `maxParticlePointsPerTick` | `256` | Shared visual trail budget per server tick, 64–2048; does not change damage |
| `maxActiveSpells` | `128` | Global timed spell limit, 8–512 |

Cooldowns are per player and shared across copies of the same wand. Siphon
healing, projectile wards, stuns, and restraints each share recovery within their
respective group across wands. Other spells keep individual cooldowns.
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
