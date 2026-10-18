# Configuration

Resonant Arcana 0.13 separates authoritative server rules from local client preferences. Files are generated on first launch.

- **Server:** `<world>/serverconfig/resonantarcana-server.toml`. These values synchronize to joining clients. On a dedicated server, `<world>` is the configured level folder.
- **New-world defaults:** copy the server file to `defaultconfigs/resonantarcana-server.toml` before creating a world. This does not overwrite existing worlds.
- **Client:** `config/resonantarcana-client.toml`. Each player controls their own display, mouse input and native particle density.

Stop the game or server before editing. Restart after changes. Existing `resonantarcana-server.toml` and `resonantarcana-client.toml` settings from 0.12 can still be used. The namespace change does not migrate saved items or spells.

## Server settings

| Section and key | Default | Allowed values | Function |
| --- | ---: | --- | --- |
| `casting.enableWands` | `true` | boolean | Allow debug-wand casting; normal spellbooks remain available. |
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

Visual budgets affect presentation; they never select targets or change damage. Active-spell limits can refuse a new cast when capacity is reached. Server per-tick budgets are shared across casters. Native client budgets are local to each player. Range rings and expanding ripples reserve separate capacity, and detail decreases with distance. Up to 32 twelve-tick native ripples may be active per client.

## Client settings

| Section and key | Default | Allowed values | Function |
| --- | ---: | --- | --- |
| `controls.leftClickCasting` | `true` | boolean | Enable left-click wand casting. Disable for combat-mod input conflicts; the cast key remains available. |
| `display.showWandHud` | `true` | boolean | Show the selected spell and rank above the hotbar. |
| `particles.nativeParticlesPerTick` | `1800` | 300–6000 | Native client detail budget, plus a separate range-outline allowance of one third this value. Higher values add detail during crowded fights. |


## Individual spells

All core spells register through Iron’s native spell API. Use Iron’s spell configuration for individual enablement, maximum level, cooldown, school and crafting rules. Resonant Arcana’s server multipliers apply in addition to those rules. Scroll upgrades cannot exceed either the catalogue’s level cap or the server’s configured maximum.

The packaged `assets/resonant_arcana/catalogue.json` defines the addon’s baseline spell damage, mana, timing and descriptions. It is a validated build resource, **not a hot-reloaded datapack or a per-world configuration file**. Maintain a source fork and rebuild if you want to change these defaults. The spell guide is generated from the same resource.

## Namespace break in 0.13

All registered addon IDs now use `resonant_arcana`. Arcane, Cinder and Rift wands use `arcane_wand`, `cinder_wand` and `rift_wand`. Old registries, item selections and learned spell ranks are not migrated; obtain new wands and scrolls and reinscribe spellbooks. Key bindings also use the new namespace and may need rebinding. If you customized individual spells through Iron’s configuration, apply those overrides to their new `resonant_arcana` IDs.

The server/client configuration filenames introduced in 0.12 stay `resonantarcana-server.toml` and `resonantarcana-client.toml`.

## Optional Bosses’Rise spells

The adapter activates only for Bosses’Rise **2.1.2**. Both sides must have matching mod installations. Other versions leave the adapter disabled. Without Bosses’Rise, its twelve spells and visual entity types are not registered; core spells retain their IDs and behavior. Manage the twelve spells’ normal level, enablement and balance settings through Iron’s native spell configuration. Existing Resonant Arcana damage, cooldown, healing, PvP and active-cast limits still apply.

Ground spells require a loaded, visible solid surface within the bounded ground search. Temporary visual entities never save into chunks and are removed when their cast ends or the caster leaves. The addon does not copy, modify or redistribute Bosses’Rise assets.

## Reactive and spellsword spells (0.16)

Individual spell mana, cooldown, maximum level and enabled state use Iron’s spell
configuration. Resonant Arcana’s damage, healing and cooldown multipliers also
apply. Reactive buffs snapshot spell power at cast time. Weapon contributions
use the melee attack attribute at release. Buffs refresh without stacking.

Thunderbrand and Phantom Formation react only to successful native
`minecraft:player_attack` damage. Misses, cancelled hits and spell damage do
not charge them. Combat mods using their own damage types need an explicit
integration; no compatibility with every combat mod is implied.

Crimson Reversal uses a native three-second recast instance. The return is free,
then the normal cooldown starts; expiry also starts the cooldown. It requires
a clear, loaded destination in the same dimension and within 16 blocks.

Spellbreaker removes at most 32 visible hostile/unowned projectiles. It invokes
Iron’s cancellation API only for eligible long casts, checks interruption
immunity, and honours `CounterSpellEvent` cancellation. It cannot erase
already-released fields or arbitrarily cancel independent magic systems.

Kraken’s Wrath is available only with the supported Bosses’Rise 2.1.2 version.
Its visuals stay in that mod; no third-party assets are bundled.

## Equipment and renderer changes in 0.21

The ten survival wands and their assets are removed. One debug wand exposes all enabled spells. Three native spellbooks grant equipment perks only in the Curios spellbook slot. Cataclysm rituals use native particles at the caster’s feet. AAA settings and renderer support are removed.


## Selective rituals in 0.22

The Spells creative tab contains every enabled scroll. Categories remain available in the debug archive. The client `particles.aaaRituals` option (default true) enables only floor seals, Explosion seals and Doom when AAAParticles is installed and supported. Native detail uses the existing budget. Without AAA, native circles, the vertical Explosion beacon and incoming streams still render.

Explosion snapshots the aimed entity position or block/range mark at the beginning of its native cast. Turning does not move it. A new cast acquires a new mark; cancellation clears the snapshot and cannot detonate it. The mark must remain loaded and within 64 blocks with line of sight at release.

### Recorded spells in 0.26

These four spells have fixed windups so voices and sound effects keep their original pitch. Cast-speed equipment still affects other spells. Native mana, cooldowns, interruption, spell power and enabled-state settings remain active.

| Spell | Fixed charge | Audio after release | Source |
| --- | ---: | ---: | --- |
| Explosion | 7.7 s | 6.28 s | Megumin Explosion (Sound Redesign), entire recording |
| I Am Atomic | 13.5 s | 9 s | iamatomic, shortened blast tail with 1.5 s fade |
| Arcane Devastator | 6.5 s | 7.36 s | Cosmic Beam, entire recording |
| Doom Beam | 13 s | 9.5 s | Laser of Doom, selected consecutive excerpts |

Each complete source is loudness-normalized before splitting; neither phase is independently amplified. The old bass-boosted Explosion recording and pitch scaling are removed. Voices and buildup follow their caster, while released audio comes from the spell origin. Optional direct caster impact feedback and all existing audio sliders still apply. Caster/spell-specific sound instances stop on interruption, death and dimension changes without silencing another ritual. Released tails finish naturally.

Explosion's existing sound-event IDs `resonant_arcana:explosion_chant` and `resonant_arcana:explosion_blast` are preserved and point to the redesigned recording. Other IDs are `atomic_charge`, `atomic_release`, `cosmic_charge`, `cosmic_release`, `doom_laser_charge` and `doom_laser_release`, all in the `resonant_arcana` namespace. Resource packs can override those events through `assets/resonant_arcana/sounds.json`. Replacement charge recordings should retain the corresponding fixed duration.

### Atomic containment

I Am Atomic anchors a sixteen-block sphere at the start of its native cast. Moving more than three blocks interrupts it. Enemies captured at initiation are returned to their last clear position if they cross the sphere, subject to loaded-space and collision checks. Bosses and fully knockback-resistant enemies resist this displacement. Hostile-owned projectiles crossing the boundary are absorbed; allied projectiles are preserved. Visible allies receive brief, refreshed Resistance I while channeling.

The focus is a temporary, invulnerable netherite sword display. Interruption and release remove it; it cannot save into chunks or drop an item. The violet membrane is translucent, with native energy streams gathering at the sword tip. One 512-base-damage detonation occurs at the first release tick. Its expanding shock fronts are visual only. Caster/allied protection, PvP rules and line of sight remain authoritative, and terrain is never destroyed.

Arcane Devastator now sustains its beam for 7.4 seconds after a fixed 6.5-second windup. Each enemy can be damaged once, including enemies entering after release. The path is clipped against current loaded terrain each tick, so newly placed cover also stops it.

Doom uses native Warden heartbeat, portal ambience, respawn-anchor hum, beacon hum, sculk clicks, amethyst resonance and Enderman tension. These are staggered throughout charging and the active black hole. Doom Beam uses the Laser of Doom recording instead.

### Attached Doom Beam and reference Explosion in 0.26

Doom Beam's damage path follows the caster's hands and current look direction on every server tick. A target can still take damage only once over the full release, even if you sweep across it again. Every path is clipped against currently loaded terrain. Client geometry follows the interpolated caster on each rendered frame; short visual leases expire after interruption, death, dimension changes or missing updates. The centre uses Iron's beacon texture and ray hull helper; the outer plasma texture is original. Iron's wisp and electricity sprites receive per-particle green tint, without changing other spells or copying their assets. Native particle budgets still apply.

Doom/Atomic's core and membrane renderers no longer apply the world camera rotation twice. The core and its rings now share the same world-space centre.

Explosion keeps its 7.7-second chant, locked target and single 256-damage release. A vertical ignition column and five orange-gold seals build over the mark, with coloured mana sparks around the caster. An original textured fireball expands, rises and fades over 4.5 seconds with an expanding ground shockwave. The extended release spectacle adds no repeated damage or terrain destruction. Existing audio and particle sliders apply.
