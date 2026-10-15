# Changelog

## 0.6.1-alpha

- Observe AAAParticles effect loading and report failures instead of silently ignoring failed spawns.
- Add `/kwvisuals` client diagnostics and `/kwvisuals test` to test effects independently of the server.
- Report missing effect resources, registration failures and unsupported native renderers.

## 0.6.0-alpha

- Expand AAAParticles to casting, projectile flight, beams, waves and lingering effects across all 58 spells.
- Add 17 effect forms in eight wand palettes, with original glow, rune, ember, lightning and crystal textures.
- Add large impact bursts, animated cages and wards, swirling vortices and visible poison fields.
- Keep effect positions, beam endpoints and area sizes tied to the server’s spell simulation.
- Bound visual traffic and add `maxAaaEffectsPerTick` for client performance.
- Update both client and server; the visual packet protocol has changed.

## 0.5.0-alpha

- Add optional AAAParticles impact effects for all eight wand themes.
- Add expanding shock rings and short impact flashes, including cyan and amethyst Soul effects.
- Keep vanilla particles and sounds when AAAParticles is absent or disabled.
- Add the `aaaParticles` setting.

## 0.4.1-alpha

- Replaced quiet casting cues with stronger themed sounds and layered impacts.
- Added direct caster feedback so distant hits remain audible.
- Added impact audio to damage pulses, restraints and projectile deflections.
- Limited repeated hit audio and prevented duplicate hit/burst sounds.
- Added `impactVolume` and `casterImpactFeedback` settings.

## 0.4.0-alpha

- Added the Venom Wand and seven spells: Arcane Volley, Sanguine Drain, Blood Lance, Ember Veil, Venom Bolt, Miasma Field and Withering Hex.
- Reorganized the catalogue into eight wands and 58 spells. Elemental spells moved from Empire to their matching wands.
- Replaced Shadow Cage with Dread Mark and Gravity Choke with Suspension.
- Renamed Dark's Soul Seeker and Soul Aura to Shadow Seeker and Dread Aura.
- Removed Fire Pulse in favour of Flame Shock Wave.
- Added shared cooldowns for related healing, ward, stun and restraint spells.
- Added spell descriptions and migration for saved wand selections.
- Fixed Flamethrower damage through thin walls.

## 0.3.0-alpha

- Added the Soul Wand with eight spells.
- Added sculk, amethyst and sonic effects with warden sounds.
- Added Sonic Boom's charge and piercing beam, and damage-based Soul Siphon healing.

## 0.2.0-alpha

- Added Dark, Thunder and Mephi wands with 18 spells.
- Added homing projectiles, chain lightning, storms, gravity wells and force attacks.
- Reworked blood effects and added themed casting, sustained and impact audio.
- Added configurable sound volume.

## 0.1.0-alpha

- Initial release with Empire, Blood and Celcrius wands and 26 spells.
