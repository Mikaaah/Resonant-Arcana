# Changelog

## 0.4.0-alpha

- Reviewed every spell; reorganized into eight themed wands and 58 distinct spells.
- Empire focuses on arcane utility; elemental spells move to Blood, Celcrius, Thunder and the new Venom Wand.
- Added Arcane Volley, Sanguine Drain, Blood Lance, Ember Veil, Venom Bolt, Miasma Field and Withering Hex.
- Reworked Shadow Cage into delayed Dread Mark and Gravity Choke into lifting Suspension. Renamed Dark's Soul Seeker/Aura to Shadow Seeker/Dread Aura.
- Removed redundant Fire Pulse; Flame Shock Wave retains the fire pulse role.
- Added shared recovery for healing, wards, stuns and restraints; existing copies share cooldowns as before.
- Added stable spell selection IDs and migration for older numeric selections. Moved/removed Empire selections start at Spark.
- Added selected-spell effect descriptions, poison audio/spores, and an ender-eye texture for Mephi.
- Fixed Flamethrower hitbox damage through thin walls with explicit line-of-sight checks.
- Full decisions and spell lists: SPELL_REVIEW.md.


## 0.3.0-alpha

- Added the Soul Wand using the vanilla echo shard texture: seven wands and 52 distinct spells total.
- Added Echo Bolt, Sculk Wave, Sonic Boom, Soul Siphon, Soul Prison, Shrieking Pulse, Amethyst Nova and Echo Ward.
- Sonic Boom charges before releasing a piercing, wall-clipped beam. Soul Siphon heals only after actual health damage, up to one heart.
- Added sculk wisps, charge pops, shriek ripples, amethyst fragments and teal/violet firework sparks.
- Added warden charge, sonic boom, roar, heartbeat and tendril sounds plus crystal impacts.
- Preserved the previous six wands and their spell order; no new dependencies.

## 0.2.0-alpha

- Added Dark, Thunder and Mephi wands: 18 new spells, 44 distinct spells total.
- Added homing bolts, chain lightning, storms, damage auras, gravity wells, force push/pull and damaging restraints.
- Reworked blood particles with dark crimson transitions, red fragments and impact rings; removed their white fireworks.
- Added casting runes, comet spiral trails and themed particle accents.
- Added distinct cast, sustain and impact audio plus configurable sound volume. Cast audio goes directly to the caster as well as nearby players.
- Preserved original wand IDs, spell order, cooldown protections and non-destructive spell behavior.
- AAAParticles remains optional; no Effekseer integration in this version.

## 0.1.0-alpha

Initial experimental build. See README.md for supported features and VALIDATION.md for completed checks and testing limits.
