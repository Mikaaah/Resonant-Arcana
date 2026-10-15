# Configuration

Resonant Arcana 0.12 separates authoritative server rules from local client preferences. Files are generated on first launch.

- **Server:** `<world>/serverconfig/resonantarcana-server.toml`. These values synchronize to joining clients. On a dedicated server, `<world>` is the configured level folder.
- **New-world defaults:** copy the server file to `defaultconfigs/resonantarcana-server.toml` before creating a world. This does not overwrite existing worlds.
- **Client:** `config/resonantarcana-client.toml`. Each player controls their own display, mouse input and optional AAAParticles settings.

Stop the game or server before editing. Restart after changes. The old `config/kingdomwands-common.toml` is no longer read; transfer desired values using the mapping below.

## Server settings

| Section and key | Default | Allowed values | Function |
| --- | ---: | --- | --- |
| `casting.enableWands` | `true` | boolean | Allow direct wand casting; disable for a spellbook-only pack. |
| `casting.maxActiveSpells` | `128` | 8–512 | Maximum concurrent timed spells across the server. |
| `casting.maxActivePerPlayer` | `8` | 1–16 | Maximum concurrent timed spells owned by one player. |
| `combat.allowPvp` | `true` | boolean | Allow offensive spells against players, subject to server PvP and team rules. |
| `combat.healTeamlessPlayers` | `true` | boolean | Allow healing between players who both have no scoreboard team. Set false for competitive servers. |
| `balance.damageMultiplier` | `1.0` | 0–10 | Multiply direct spell damage after level and equipment scaling. Burning, poison and wither remain vanilla effects. |
| `balance.healingMultiplier` | `1.0` | 0–10 | Multiply direct healing. Does not increase absorption shields. |
| `balance.cooldownMultiplier` | `1.0` | 0.1–10 | Multiply cooldowns before Iron’s equipment modifiers. |
| `audio.volume` | `1.0` | 0–2 | Overall spell audio gain. The Players sound slider still applies. |
| `audio.impactVolume` | `1.0` | 0–3 | Additional impact gain, including Soul Cannon. |
| `audio.personalImpactFeedback` | `true` | boolean | Deliver the caster’s own impact audio without distance attenuation. Other players hear positional impacts. |
| `visuals.trailDensity` | `2` | 1–3 | Vanilla particles per trail point. |
| `visuals.trailPointsPerTick` | `256` | 64–2048 | Shared vanilla trail-point budget per server tick. |
| `visuals.detailMessagesPerTick` | `64` | 16–256 | Shared flight, area and charge visual-message budget per tick. |
| `visuals.accentMessagesPerTick` | `32` | 8–128 | Reserved impact, strike and casting visual-message budget per tick. |
| `visuals.ironsParticlesPerTick` | `128` | 32–512 | Extra native Iron’s particle budget per tick. |

Visual budgets affect presentation; they never select targets or change damage. Active-spell limits can refuse a new cast when capacity is reached. All per-tick budgets are shared rather than granted separately to each caster.

## Client settings

| Section and key | Default | Allowed values | Function |
| --- | ---: | --- | --- |
| `controls.leftClickCasting` | `true` | boolean | Enable left-click wand casting. Disable for combat-mod input conflicts; the cast key remains available. |
| `display.showWandHud` | `true` | boolean | Show the selected spell and rank above the hotbar. |
| `particles.enableAaaParticles` | `true` | boolean | Use the extra AAAParticles effects when the optional mod is installed. |
| `particles.maxAaaEffectsPerTick` | `32` | 8–96 | Maximum new AAAParticles emitters on this client per tick. |

Keys are rebound under **Controls → Resonant Arcana**. `/kwvisuals` reports optional renderer status and loaded effect counts; `/kwvisuals test` plays a short local test.

## Individual spells

All 120 spells register through Iron’s native spell API. Use Iron’s spell configuration for individual enablement, maximum level, cooldown, school and crafting rules. Resonant Arcana’s server multipliers apply in addition to those rules. Scroll upgrades cannot exceed either the catalogue’s level cap or the server’s configured maximum.

The packaged `assets/kingdomwands/catalogue.json` defines the addon’s baseline spell damage, mana, timing and descriptions. It is a validated build resource, **not a hot-reloaded datapack or a per-world configuration file**. Maintain a source fork and rebuild if you want to change these defaults. The spell guide is generated from the same resource.

## Migration from 0.11 and earlier

| Old flat key | New key |
| --- | --- |
| `enableWandCasting` | Server `casting.enableWands` |
| `allowPvp` | Server `combat.allowPvp` |
| `classicMouseCasting` | Client `controls.leftClickCasting` |
| `damageMultiplier` | Server `balance.damageMultiplier` |
| `cooldownMultiplier` | Server `balance.cooldownMultiplier` |
| `soundVolume` | Server `audio.volume` |
| `impactVolume` | Server `audio.impactVolume` |
| `casterImpactFeedback` | Server `audio.personalImpactFeedback` |
| `aaaParticles` | Client `particles.enableAaaParticles` |
| `maxAaaEffectsPerTick` | Client `particles.maxAaaEffectsPerTick` |
| `particleDensity` | Server `visuals.trailDensity` |
| `maxParticlePointsPerTick` | Server `visuals.trailPointsPerTick` |
| `maxActiveSpells` | Server `casting.maxActiveSpells` |

The migration does not delete your previous file. Existing wands keep stable spell selections and saved IDs. New rank data is stored per spell on each wand; existing wands begin at level I.
