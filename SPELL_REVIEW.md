# Spell review — 0.4.0-alpha

Every spell was reviewed for theme, role, duplicated selection and upgrade behavior.
The revised catalogue has **8 wands / 58 distinct spells**, each assigned to one wand.
These are design and functional changes; final combat balance still needs playtesting.

## Decisions

- Empire becomes an arcane utility wand: 20 selections become 10. Blood spells
  stay on Blood, fire spells on Celcrius, lightning on Thunder and poison on Venom.
- Fire Pulse is removed because Flame Shock Wave already fills its fire pulse role.
- Venom gets the existing Poison Spark/Wave plus Venom Bolt, Miasma Field and Withering Hex.
- Blood gains Sanguine Drain and Blood Lance. Healing is conditional on actual health damage.
- Empire gains Arcane Volley: three staggered travelling projectiles, retaining normal damage immunity frames.
- Celcrius gains Ignite and Ember Veil, a short-range mobile fire aura.
- Dark's Soul Seeker/Aura become Shadow Seeker/Dread Aura. Shadow Cage becomes
  Dread Mark: a stronger delayed curse, interrupted by cover, instead of another restraint.
- Mephi's Gravity Choke becomes Suspension: lift with a gentle descent. Its item
  texture is now an ender eye so the Soul Wand keeps a distinct echo shard appearance.
- Soul keeps all eight spells and its warden/sculk/amethyst identity.
- Healing, wards, stuns and restraints each have shared recovery groups across wands.
  Other spells keep individual cooldowns. Switching wands does not clear cooldowns.
- Added short effect descriptions to the selected-spell tooltip.
- Flamethrower now checks line of sight to prevent its wide hitbox reaching through thin walls.

## Saved wands

Existing item registry IDs are retained. Versions 0.1–0.3 saved only numeric
selections; those indices are read against their original layouts. If the old
spell remains on that wand, it stays selected. A moved/removed Empire selection
falls back to Spark. It does not silently select an unrelated numeric slot.
New selections also store a stable spell ID and retain unrelated custom data.

## Lineups

Base cooldowns below are before `cooldownMultiplier`. Damage immunity frames,
config multipliers, server PvP and scoreboard rules still apply.

### Empire Wand — 10 spells

Arcane precision, mobility and utility.

| Spell | Role | Base cooldown |
| --- | --- | --- |
| Spark | A travelling projectile that stops at walls. | 0.6s |
| Empire Spark | Strike the living target you aim at. | 1.75s |
| Confuse | Briefly blind and disorient your target. | 5s |
| Stun | shared_stun | 5s |
| Capture | shared_restraint | 8s |
| Empire Comet | A slow projectile with an area impact. | 5s |
| Empire Pulse | An expanding shock ring around you. | 5s |
| Launch | Launch your target upwards. | 3.5s |
| Leap | Propel yourself forwards and upwards. | 4s |
| Arcane Volley | Fire three staggered arcane projectiles. | 4.5s |

### Blood Wand — 7 spells

Crimson pressure, piercing and life drain.

| Spell | Role | Base cooldown |
| --- | --- | --- |
| Blood Wave | A wide travelling arc, hitting each enemy once. | 3s |
| Blood Spark | Strike the living target you aim at. | 1.75s |
| Blood Explode | An area burst centred on your aim. | 6s |
| Blood Stun | Slow and weaken the target with a crimson impact. | 5s |
| Blood Ward | shared_ward | 9s |
| Sanguine Drain | Crimson drain: heal from actual health damage, up to one heart. | 5s |
| Blood Lance | An instant piercing line through visible enemies. | 4s |

### Celcrius Wand — 8 spells

Fire channels, spreading pressure and area denial.

| Spell | Role | Base cooldown |
| --- | --- | --- |
| Fire Comet | A slow projectile with an area impact. | 5s |
| Fire Twister | A stationary vortex dealing repeated area damage. | 6s |
| Flame Wave | A wide travelling arc, hitting each enemy once. | 3s |
| Flame Shock Wave | An expanding shock ring around you. | 6.5s |
| Flamethrower | A short stream following your current aim. | 5s |
| Fireball | A travelling projectile that stops at walls. | 2.5s |
| Ignite | Strike the living target you aim at. | 2.5s |
| Ember Veil | Three-second moving fire aura with a five-block radius. | 7.5s |

### Dark Wand — 6 spells

Shadow pursuit, curses and weakness.

| Spell | Role | Base cooldown |
| --- | --- | --- |
| Shadow Bolt | A travelling projectile that stops at walls. | 1.25s |
| Shadow Seeker | A projectile that follows your selected target. | 3.5s |
| Dark Wave | A wide travelling arc, hitting each enemy once. | 3.25s |
| Nightfall | An area burst centred on your aim. | 6s |
| Dread Mark | Curse a target; detonate after two seconds. Cover breaks the curse. | 7s |
| Dread Aura | A damaging aura that moves with you. | 8s |

### Thunder Wand — 8 spells

Focused lightning, chain attacks and storms.

| Spell | Role | Base cooldown |
| --- | --- | --- |
| Thunder Bolt | A travelling projectile that stops at walls. | 1.5s |
| Chain Lightning | Jump between up to five visible targets. | 5.5s |
| Thunder Wave | A wide travelling arc, hitting each enemy once. | 3.25s |
| Storm Call | Call repeated lightning strikes over an area. | 9s |
| Thunder Pulse | An expanding shock ring around you. | 6s |
| Static Shock | shared_stun | 4s |
| Lightning | Focused lightning strike with a two-block impact radius. | 5s |
| Smite | Heavier lightning strike with a four-block impact radius. | 8s |

### Mephi Wand — 6 spells

Gravity, displacement and suspension.

| Spell | Role | Base cooldown |
| --- | --- | --- |
| Gravity Bolt | A travelling projectile that stops at walls. | 1.5s |
| Gravity Well | Draw nearby enemies into a gravity well. | 8s |
| Force Push | Push the target away from you. | 3.25s |
| Force Pull | Pull the target towards you. | 3.25s |
| Suspension | Lift a target briefly, followed by a gentle descent. | 7s |
| Singularity | An area burst centred on your aim. | 8s |

### Soul Wand — 8 spells

Sculk resonance, sonic attacks and echo magic.

| Spell | Role | Base cooldown |
| --- | --- | --- |
| Echo Bolt | A travelling projectile that stops at walls. | 1.25s |
| Sculk Wave | A wide travelling arc, hitting each enemy once. | 3.25s |
| Sonic Boom | Charge for 0.6 seconds, then fire a piercing sonic beam. | 6s |
| Soul Siphon | Drain essence into healing, up to one heart; shares recovery with Sanguine Drain. | 5s |
| Soul Prison | Three-second damaging restraint with darkness. | 7s |
| Shrieking Pulse | An expanding shock ring around you. | 6.5s |
| Amethyst Nova | Aimed sculk/crystal area burst with teal and violet sparks. | 6s |
| Echo Ward | shared_ward | 9s |

### Venom Wand — 5 spells

Poison, wither and lingering clouds.

| Spell | Role | Base cooldown |
| --- | --- | --- |
| Poison Spark | Strike the living target you aim at. | 2.5s |
| Poison Wave | A wide travelling arc, hitting each enemy once. | 3.5s |
| Venom Bolt | A travelling projectile that stops at walls. | 1.75s |
| Miasma Field | A stationary three-second poison field; damage pulses once per second. | 7.5s |
| Withering Hex | Inflict poison and wither on an aimed target. | 5.5s |
