# Changelog

## 0.10.0-alpha — Heavy magic

- Add Solar Cataclysm: a three-second charge, target warning and descending meteor with a 32-damage area impact.
- Add Tempest Spear: a 2.5-second charge followed by a 30-damage piercing lightning lance and fading afterimages.
- Add Event Horizon: a 3.2-second charge, inward pull and delayed 36-damage collapse.
- Add all three to their wands, scroll crafting and Iron’s spellbooks, with original icons, three levels and Epic minimum rarity.
- Retouch core casting, projectile, wave, field and impact effects with different geometry and movement for each school. Add dedicated AAAParticles sequences for the new heavy spells.
- Add native Iron’s particles throughout the spell effects; distinguish blood membranes and soul crystal wards. Remove the shared firework burst from non-soul impacts.
- Preserve Soul Cannon’s existing behavior and effects. Update both client and server.

## 0.9.0-alpha — Cast and release

- Make Iron’s Spells ’n Spellbooks the required casting backend. Existing wands now use native mana, charge bars, cooldowns and animations.
- Replace the synthesized casting and impact samples with Iron’s sound events and vanilla accents. Keep Soul Cannon’s existing effect and sound sequence.
- Add short windups to waves and control spells, and longer windups to comets, storms, smites and large bursts. Basic bolts and movement spells remain instant.
- Increase selected heavy spell damage and impact radius. Extend Storm Call, Rift Well and Miasma Field to 4.8 seconds.
- Add five spell levels for most spells and three for heavy spells, with increased spell power and mana costs at higher levels.
- Support cancelling wand charges by cycling spells; repeated cast input no longer restarts a charge.
- Add distinct charging and release animations for beam, storm, burst and defensive spells.
- Keep saved item and spell IDs. Update both client and server, and install Iron’s with its dependencies.

## 0.8.0-alpha — Resonant Arcana

- Rebrand Kingdom Wands as Resonant Arcana. Rename Empire, Celcrius and Mephi wands to Arcane, Cinder and Rift, with related spell names updated.
- Replace camera-facing casting sigils with compact sweeps, wisps and sparks. Keep world-space ward and trap effects.
- Add faster projectile wakes, flowing beams and impact crescents, plus 16 original whoosh and impact sounds.
- Register all 59 spells with Iron’s Spells ’n Spellbooks when installed, with scrolls, icons, mana costs, school scaling, animations and cooldowns.
- Support Iron’s charge bar for Soul Cannon and Sonic Boom without doubling their charge time.
- Add `enableWandCasting` for modpacks that prefer spellbook-only casting.
- Preserve saved wands, spell selections and configuration. Remove the previous Kingdom Wands JAR before updating both client and server.

## 0.7.0-alpha

- Add Soul Cannon to the Soul Wand: absorb void energy, then fire a massive travelling echo blast.
- Add small soul firework bursts along its path, a large sculk/crystal impact and layered charge, release and impact sounds.
- Add four dedicated AAAParticles effects, with vanilla particles when the optional mod is absent.
- Charge for two seconds; 48-block range, 16 base damage, five-block impact radius and 14-second base cooldown.
- Update both client and server; the particle packet protocol has changed.

## 0.6.2-alpha

- Fix AAAParticles effects failing to load because exported textures referenced the build machine’s directories.
- Re-export all 136 effects with portable paths to textures packaged in the mod.
- Check every native texture reference during the build; reject external paths and missing textures.

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
