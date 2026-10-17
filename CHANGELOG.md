# Changelog

## 0.24.0-alpha

- Add Legendary I Am Atomic: a fixed 13.5-second anchored ritual, translucent violet containment sphere, captured-enemy boundary control, hostile-projectile absorption, temporary netherite sword focus and one protected 512-damage detonation over sixteen blocks. Moving more than three blocks interrupts it. Bosses and fully knockback-resistant foes resist containment; terrain is preserved.
- Use the entire supplied Atomic recording, including the 18.57-second release tail. Add a dedicated icon, native registration, category and descriptions.
- Replace the old bass-boosted Explosion track with the supplied Sound Redesign: 7.7-second charge plus full blast tail. Preserve its public sound-event IDs.
- Give Arcane Devastator the Cosmic Beam recording, a 4.2-second charge and a 9.7-second sustained piercing beam. Each target can be hit once, including late entrants; new cover clips the beam.
- Give Doom selected consecutive charge/release excerpts from Laser of Doom while preserving its ten-second cast and gameplay.
- Normalize each complete recording before splitting, retain original pitch and use fixed recorded windups. Native casting bonuses still affect other spells. Keep cancellation scoped to caster and spell; suppress competing generic cues and add subtitles.
- Extend server fixtures for containment, interruption, projectile filtering, exact damage, ally/cover protection, original-pitch timing, sustained beams and resource packaging.

## 0.23.0-alpha

- Include the owner-supplied Explosion chant and bass-heavy blast as two streamed OGG assets split at 20.95 seconds. Preserve the full decoded recording and keep the original MP3 untouched.
- Extend Explosion’s base native cast to 21 seconds. Match chant playback to native cast speed, bounded to the supported audio pitch range. Detonate on the first release tick, without the old 1.2-second delay.
- Track chant sound instances by caster. Cancellation, death and dimension changes clean up that instance without silencing another player’s ritual. Play the recorded blast at the locked target and provide direct caster feedback.
- Suppress competing Explosion charge/release/impact cues. Existing audio sliders and server gain still apply.
- Rework Explosion’s charge to crimson/red seals, amber counter-rotating ember ribbons and a warm pale core that brightens near completion. Add one dedicated crimson AAA seal; other spells retain their current palettes.
- Document audio attribution, regeneration and resource-pack overrides. Extend server fixtures for timing, native cast-speed alignment, target locking and packet validation.

## 0.22.0-alpha

- Combine scrolls into one Spells creative tab; keep Equipment separate and retain category filters in the debug archive.
- Restore three selective AAA effects: flattened floor seals, Explosion seals, and the original Doom effect. Native fallback circles remain. Doom’s core and red spirals share an origin five blocks higher.
- Increase charging audio volume and repeat distinct cues through the entire windup, including casts longer than four seconds.
- Add denser downward charge spirals from above the seal, with a smaller stream entering the caster.
- Reduce Final Judgement’s sword to scale four, correct its orientation for the renderer’s Y flip, and plant the tip before the final verdict.
- Lock Explosion’s target at the beginning of the native cast. Build a diagonal beacon and five sequential seals with incoming particles at different heights, then detonate with fire, smoke and shockwaves. Preserve mana, interruption and terrain protection.
- Add an optional `resonant_arcana:explosion_chant` sound slot, stopped on completion/cancellation. No anime recording is bundled; a resource pack can supply one.

## 0.21.0-alpha

- Remove the ten school wands, their models, textures, recipes and advancements. Add one uncrafteable Debug Wand with every enabled spell, using the supplied copper-chisel texture. Preserve all existing spell IDs.
- Add three inscribable legendary Iron’s spellbooks using the supplied textures unchanged: Runic Echoes (14 slots, mana and Evocation/Ender perks), Phoenix (12 slots, fire and recovery perks), and Deathchill (12 slots, Ice/Eldritch and casting perks). Native Curios equipment attributes apply only in the spellbook slot.
- Categorize every spell by function. Add categorized creative scroll tabs and a category filter in the debug spell archive. Reorganize and shorten descriptions and spell documentation.
- Remove all AAAParticles renderer code, configuration, assets and generation tooling. Cataclysm windups now use a large native summoning circle under the caster; themed particles gather into its perimeter with gradually increasing density and a clear centre. Ordinary charge particles are reduced at the beginning.
- Replace the common ritual pulse with individual sound profiles. Remove Doom’s solid orb and align its dark rings with its red accretion streams. Replace geometric shadow/soul structures with native particles.
- Worldroot Blessing uses moss fragments, flowering-azalea fragments, spores and drifting leaves. Eternal Winter becomes a sixteen-block, ten-second blizzard. Final Judgement summons an actual descending golden sword with overhead-aware scale.
- Add Explosion (large blast with exhaustion), Seraph Ascendant (native temporary flight, particle wings, healing and ally spell-power aura), and Arcane Devastator (focused piercing cannon).
- Add equipment, native inscription, flight coexistence, sword and cleanup checks. Require a fresh successful report for integration runs; a crashed server cannot reuse an older result.
- Pass 10,959 fresh dedicated-server checks with Bosses’Rise and 10,180 without, plus Java 21 compilation and packaged-asset checks. Client visuals and audio require in-game playtesting.

## 0.20.0-alpha

- Add ten single-rank Legendary cataclysms across all nine native schools: Astral Apocalypse, Crimson Eclipse, World Pyre, Void Coronation, Heaven’s Thunder, Rift Collision, Soul Requiem, Worldroot Catastrophe, Final Judgement and Eternal Winter. Doom is separate from the ten.
- Give each a 10–12-second base cast, 440–500 mana cost, two-minute base cooldown, individual staged mechanics, particle choreography, sound cues and original icon. Preserve native Iron’s interruption, mana, cooldown and school systems.
- Rework Doom into a ten-second, 500-mana cast with one 256-damage hit per enemy. Render an opaque, depth-tested black core with inward-flowing red accretion spirals and a red collapse. Remove its generic purple native particles and AAAParticles circle.
- Lower tentacle roots from 1.25 to 2.25 blocks below ground. Grasp uses the full original cinematic Kraken geometry and red texture, with a spell-owned rise, tentacle curl and sink. It cannot run original boss AI, attacks, loot or cutscenes.
- Expand to 147 core spells plus twelve optional Bosses’Rise spells, while preserving all existing spell ordinals and saved identifiers.
- Add native long-cast, cancellation, staged damage, wall, teammate and full-model cleanup checks. Pass Java 21 compilation, packaged-effect validation, 8,001 dedicated-server checks without Bosses’Rise and 8,729 with Bosses’Rise 2.1.2. Client rendering and audio remain unverified in game.

## 0.19.0-alpha

- Add a bounded client native-particle renderer across all 149 spell definitions. Layer school-specific wave fronts, woven beams, projectile wakes, cages, rain, vortices and expanding impacts using vanilla and Iron’s particles. Keep AAAParticles limited to the existing 24 circles.
- Reserve native capacity for ground-following range outlines. Show the full radius of Last Stand, traps, totems, fields and rain; fix Funeral Bell’s visual wave radius. Limit decorative detail with distance and concurrent-ripple budgets.
- Rework Warscream into three damage pulses over eight blocks, inward pulls and Resistance I for visible nearby allies. Taunt and defense last 3/4/5/6/7 seconds at ranks I–V. Remove the explosion sound and retired damage/shield behavior.
- Place real temporary block-model entities for Wisp Lantern, Dread Effigy, Spore Totem and Consecration. Require visible ground; remove the entities on expiry, cancellation or caster cleanup without replacing terrain.
- Give Wisp Lantern revealing pulses and Dread Effigy Weakness II. Add individual placement and sustain sounds; remove duplicate generic totem sustain cues.
- Audit all descriptions, replace 52 generic early-spell descriptions, and document the new functions, timings, rank scaling and visual controls.
- Pass Java 21 compilation and Gradle packaged-effect checks, plus 5,340 dedicated-server checks without Bosses’Rise and 6,064 with Bosses’Rise 2.1.2. Client rendering and sound still require in-game playtesting.

## 0.18.0-alpha

- Bury Kraken tentacle visual roots 1.25 blocks below the ground and point Wrath’s four outer tentacles toward the central impact.
- Add two blue and cyan soul-fire spirals to both phases of Soul Cannon charging, within the shared native particle budget.
- Render Helvar’s original 3D held sword geometry and animated textures directly, with the blade tip aligned to the ground at impact.
- Replace Dragon’s Guard with Infernal Barrage: three charged, travelling fireballs that collide with enemies and walls. Replace Dragon’s Pyre with Dragon’s Cataclysm: a warned descending fireball and expanding flame shockwave. Preserve both spell registry IDs and scroll upgrades.
- Give the Yeti a seven-spike lingering crescent, stage Sandworm columns with their eruptions, and add shaped frost, water, sand and rift effects using vanilla and Iron’s particles.
- Remove the unused dragon guard visual entity and renderer. Preserve friendly-fire, line-of-sight, chunk, terrain and cleanup rules.
- Pass 5,025 dedicated-server checks without Bosses’Rise and 5,726 with it, plus Java 21 compilation and packaged-effect validation. Client rendering remains unverified.

## 0.17.0-alpha

- Expand Bosses’Rise support to twelve spells across all five bosses, including Helvar’s Swordfall using the original Knight Sword item.
- Add Kraken’s Undertow, Yeti’s Glacial Wall, Helvar’s Fracture, Sandworm’s Eruption and Quicksand, and Dragon’s Guard and Pyre.
- Use original installed boss entities, textures, sounds and particles, including the red Kraken tentacle; keep damage and ally protection in the shared spell engine.
- Limit AAAParticles to 24 spell-circle effects; use vanilla, Iron’s or Bosses’Rise visuals for other effects.
- Show a real banner for Last Stand and split the creative inventory into Wands and Scrolls tabs.
- Refresh the spell catalogue, icons, documentation and wiki for 149 spells, preserving existing spell identifiers.
- Clear stale resource output during packaging and verify that the JAR contains exactly the current effect manifest.
- Pass Java 21 compilation and Gradle build checks; in-game visual testing and fresh dedicated-server checks remain pending.

## 0.16.0-alpha

- Add Glacial Counter, Rift Draw, Crimson Reversal, Thunderbrand, Last Stand, Ashen Rebirth, Phantom Formation, Funeral Bell and Spellbreaker.
- Add optional Kraken’s Wrath for Bosses’Rise 2.1.2, with four outer strikes and a heavy central slam.
- Add a timed perfect guard, successful-melee proc hooks, bounded spectral swords, a defensive banner and native Iron’s recasts.
- Give every new spell levels I–V, sound profiles, original icons, tooltips and rank documentation.
- Respect native interruption immunity and CounterSpellEvent for Spellbreaker.
- Keep proc attacks from triggering further melee procs; cap projectile removal and clean up buffs on expiry.
- Expand to 137 core spells plus four optional spells, while preserving existing IDs and saved wand selections.

## 0.15.0-alpha

- Add optional Bosses’Rise 2.1.2 integration with Kraken’s Grasp, Ice Spike Eruption and Helvar’s Rift, each at levels I–V.
- Reuse installed boss models, animations and sounds through non-damaging visual adapters; native boss combat routines never execute.
- Keep damage, statuses, PvP, friendly-fire and line-of-sight checks in the shared spell engine.
- Hide optional spells, scrolls, commands and visual entity types when the supported dependency is absent.
- Add original icons, timing documentation and explicit compile-only dependency instructions.


## 0.14.0-alpha

- Add Warscream: dragon roar, explosion ring, radial knockback, short hostile-mob taunt, party damage and temporary health.
- Add Soulblast with forward repulsion, downward self-launch, and directional Elytra boost.
- Add Arcane Edge, Bulwark Chant and Battle Tempo for spellsword and party support.
- Add weapon-scaling Cinder Cleave, Rime Thrust and Blood Rend, each with levels I–V.
- Expand to 128 spells and add wand selection pages with validated server-side slots.
- Add original spell/effect icons and document damage, buffs, duration and mobility modes.


## 0.13.0-alpha

- Move every addon registry, asset, translation and network ID to `resonant_arcana`.
- Rename the main class and Java package to Resonant Arcana.
- Use current Arcane, Cinder and Rift wand IDs; remove legacy command aliases and selection migration code.
- Clean break: previous items, scrolls, spellbook entries and saved wand ranks are not migrated.
- Keep the existing server/client configuration filenames.

## 0.12.0-alpha

- Expand every wand to 12 spells: 120 spells across ten complete kits.
- Add Holy healing, blessings, cleansing, consecration, judgement and support spells.
- Add Frost needles, an ice serpent, hail, crystal fans, shatter combos and a freezing domain.
- Add crimson executions, shadow traps, lightning ricochets, orbiting blades, dragon breath and Prismatic Ruin.
- Give 119 spells levels I–V, with explicit damage, healing and mana progression. DOOM remains single-level Legendary.
- Add original wand sprites, survival recipes, a 12-slot spell picker and a selected-spell HUD.
- Let wands learn higher ranks by consuming matching native Iron’s scrolls.
- Expand to 222 optional AAAParticles effects, with distinct slashes, totems, hail, protective ribbons and fusion geometry. Add varied native Iron’s release sounds and timed accents.
- Separate synchronized server rules from local client display, input and particle settings. Add healing, teamless-healing and per-player simulation controls.
- Generate the complete spell reference from the validated runtime catalogue. Add configuration and migration documentation.
- Preserve saved spell IDs and the Soul Cannon sequence; retain native Iron’s mana, casting, cooldown, inscription and school scaling.

## 0.11.0-alpha — Scream, sanctuary and DOOM

- Replace Sonic Boom with Sonic Scream: a one-second windup, wide 12-block cone, Enderman scream and layered sonic pressure fronts. Keep the existing saved spell ID.
- Add DOOM: a Legendary Ender spell with a growing black hole, inward pull, core devouring and a final collapse. Each enemy takes at most one 72-damage hit.
- Add Sanctuary to the Holy school, with area healing and cleansing for the caster, scoreboard teammates and owned pets.
- Add Glacial Rupture to the Ice school, with advancing ice spires and chilling.
- Add Evoker’s Edict to Evocation, with three staggered rows of spectral fangs.
- Add Holy and Frost wands. All nine native Iron’s schools now have spells in the addon.
- Add original icons, native Iron’s particles and dedicated AAAParticles sequences. Update both client and server.

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
