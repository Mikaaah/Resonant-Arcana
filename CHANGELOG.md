# Changelog

## 0.2.0

This release combines development after the CurseForge **0.1.0** baseline (internally 0.28.0-alpha), including the work previously labelled 0.29 and 0.30. Public releases use plain version numbers.

### New magic and recipes

- Added optional Alex’s Caves magic across Magnetic, Primordial, Toxic, Abyssal, Forlorn and Candy caves, with native themed particles, sounds, animations and constructs. Requires the supported Alex’s Caves 2.0.10 build and Citadel on both sides.
- Added twenty God-tier rituals: nine native-school spells, six cave arts and five Bosses’Rise arts. Each has one Legendary rank, a 20-second base cast, 600 mana cost and a four-minute cooldown.
- Added Thunder of the Heavens, which locks a 28-block field and releases 200 purple lightning strikes. Other God arts feature distinct sectors, crossing cuts, fronts, tethers, allied blessings and native addon constructs.
- Added fifty material-based scroll recipes for addon and God-tier spells. Generic Scroll Forge shortcuts are disabled for these spells. God scrolls glint while retaining Iron’s native school textures.

### Spell presentation and behaviour

- Replaced Tremorzilla’s rectangular beam with native beam materials and an animated impact model. Green is the default; offhand Amber Curiosity and Tectonic Shard select retro purple and tectonic orange. Removed the miniature boss.
- Moved beam sources to torso height. Arcane Devastator uses a continuous violet beam, and Doom Beam has sustained impact eruptions.
- Rebuilt Hullbreaker’s Breach as a warned rising bite and dive, and Lux’s attack as dodgeable volcanic vents and a falling molten core.
- Replaced Tectonic Stampede with Primordial Instinct, a 20-second non-stacking allied melee, speed and resistance blessing, while preserving its saved scroll ID.
- Refined Polarity Bolt’s red/azure trail and EMP’s radial discharge; removed the unrelated block-seeking magnetic lightning.
- Reworked Helvar’s effects with black-iron red embers and warm fracture sparks, a larger native sword, corrected headroom checks and an expanding damaging shockwave.
- Added cave-specific projectile, pierce, bounce, stun, interruption, hostile-projectile clearing, wave, vortex, Sugar Rush and staged impact behaviours. Native boss AI and destructive explosions never run.

### Audio, catalogue and compatibility

- Matched native charge-cue repetition to clip lengths and prevented stacked nuclear sirens. Nuclear Strike now uses shorter reactor and placement cues before its explosion ambience.
- Made Doom, Tremorzilla and God sound beds caster-owned and cancellable, with cleanup on disconnect, death and dimension changes. Cancelling one caster cannot silence another.
- Added themed icons, biome/tier search keywords, optional spell tags, a God category and accurate sequence information. Retained the current GUI, single Spells creative tab and saved core spell IDs.
- The catalogue contains 163 core spells, 17 optional Bosses’Rise spells and 24 optional Alex’s Caves spells: 204 with both integrations.
- Install **0.2.0 on both client and server**; network protocol remains **21**.

## 0.1.0

Initial CurseForge release, corresponding to the internal 0.28 development baseline. Includes the nine-school spell catalogue, legendary spellbooks, searchable spell archive, Void Domain and Void Exile, optional Bosses’Rise integration, and the spell balance and presentation improvements delivered up to that baseline.
