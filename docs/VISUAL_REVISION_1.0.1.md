# Resonant Arcana 1.0.1 — native spell presentation revision

This revision keeps the requested **1.0.1** version and its recolored spell icons. It fixes the
nine book inventory models and reworks the twenty God-tier spells, Doom Beam and the requested
small vanilla-particle addition to Rho Aias.

God-tier presentation now combines authored vanilla and Iron's particle paths with the existing
translucent formations. Those formations support the particles instead of replacing them.
The optional AAA bridge excludes all God-tier spells and Doom Beam; its unrelated spell effects
retain their existing routing.

## Twenty distinct God-tier particle profiles

The charge and active formations below are implemented in `GodParticleChoreography.sustain`.
Finite impact wakes use each spell's native particle family and the additional sequences listed.

| Spell | Charge and active presentation | Impact or trailing motion |
| --- | --- | --- |
| Arcane Creation | Three counter-rotating particle orbits and six four-point satellite stars linked to the heart. | Six ascending amethyst sparkle rays follow the expanding front. |
| Crimson Apotheosis | A blood-spore moon, six rising blood veins and falling blood droplets during the active phase. | A descending curtain of native Iron's blood particles. |
| Solar Supernova | Twelve curling solar rays around a flame-and-firework sphere. | Flame and smoke churn outward behind the release front. |
| Eventide Horizon | Five inward-flowing portal arms with a thin end-rod event-horizon rim. | Reverse-portal fragments converge inward after the outward tell. |
| Thunder of the Heavens | Three rotating electric storm tiers and five jagged rising lightning paths. | Native electric sparks and Iron's electricity trace the strikes and front. |
| Absolute Glaciation | A six-arm frost pattern with branching crystal veins and ice-chip tips. | Snow and blue-ice fragments rise into a serrated expanding front. |
| Verdant Awakening | Four spiralling spore vines rise into a crown of eight flowering-leaf branches. | Leaves and happy-villager motes rise along the growing front. |
| Heaven's Covenant | Seven particle feathers on each wing, four descending light shafts and an elevated cleanse halo. | Iron's cleanse motes and vanilla fireworks descend behind the release. |
| Soul Ascendancy | Seven moving native soul lanterns carry twisting soul-fire tails into a central ascension spiral. | Sculk souls and soul-fire bursts open into rising soul pillars and a teal shockwave. |
| Planetary Polarity | Red and blue electric pole rings are joined by four curved field lines. | Alternating electric particles retain the two-pole identity across the field. |
| Primordial Sovereign | Seven amber rib arches and eight three-claw footprint patterns accompany the dinosaur spirits. | Leaf fragments and supportive nature motes follow the ground fronts. |
| Tremorzilla Cataclysm | Four counter-rotating purple electric rings and a contracting retro-energy spiral support the existing beam. | Electricity and neutral-tinted sparkles retain the purple release palette. |
| Abyssal World Tide | Four inward-moving walls of splash and bubble particles. | Raised water-and-bubble fronts curl outward after the pressure release. |
| Forsaken Eclipse | An elevated spectral eye and three moving bands of sculk souls and falling ash. | Reverse-portal fragments fold inward through the dark wake. |
| Confection Apocalypse | Six rotating pink-and-mint candy pinwheels with falling cherry-leaf confetti. | Alternating pink/mint sparkle waves retain the candy pattern. |
| Kraken World Drown | Eight arching water tentacles bend over a three-turn inward whirlpool. | Splash and bubble crowns rise with the collapse front. |
| Yeti World Heart | A six-facet frozen heart and three curling snow ribbons. | Blue-ice chips and snow follow the jagged frost front. |
| Helvar's Final Edict | Eight ground fissures, a descending ember trail and an elevated ember crown follow the sword's approach. | The real final sword hit triggers flame/lava ejecta, native shockwave and blastwave particles, travelling fracture spokes and an ember geyser. |
| Sandworm World Devourer | Five rising sand spirals surround a ten-tooth maw. | Sand and cloud debris lift into a moving dust crown. |
| Dragon Primordial Inferno | Seven flame feathers per wing surround a rising corkscrew of flame and smoke. | Embers, smoke and small flames trail the release front. |

Soul Ascendancy's mesh silhouette is especially subdued so its native souls and soul-fire paths
remain visible. Other formations also use reduced mesh opacity. Neutral Iron's Falling Sparkle
sprites carry tints that would otherwise be shifted by the old wisp texture's baked yellow.

Helvar's additional `HIT` presentation packet is sent at the existing age-112 sword landing.
Ordinary size-0.65 victim feedback cannot trigger that finale. It does not add a damage pulse or alter the sword's movement, radius or existing damage timing.

## Doom Beam

Doom Beam now has an entirely particle-based client route:

- Four open emerald focusing talons replace the solid black charging orb.
- Two interlaced luminous streams form the ray, surrounded by broken double helices and three
  travelling compression fronts.
- Contact creates a white-hot radial star, emerald sparks and backward exhaust.
- Vanilla end rods, electric sparks and a short impact flash accompany neutral-tinted Iron's
  Falling Sparkle particles. No beacon, plasma cylinder or source-sphere mesh is rendered for Doom.
- The existing low beam origin, first-person camera clearance and short caster-scoped leases remain.

The neutral carrier helper scales Falling Sparkle particles to retain a readable ray while using
the existing shared particle allowance and camera-safety checks. Arcane Devastator keeps its own
renderer and its previous update cadence.

## Books and Rho Aias

All nine outer `neoforge:separate_transforms` book models now explicitly use
`"gui_light": "front"`. NeoForge passes the outer model's lighting mode into the selected GUI
submodel, so specifying the generated flat icon only inside the GUI perspective was insufficient.
The original book textures, 3D geometry, item IDs, spell slots, equipment perks and recipes remain.
An external resource pack that replaces these outer model files can also replace their lighting
setting; the corrected files are the models shipped in this revision.

Rho Aias retains its rose-violet flower and three shield discs. Small vanilla enchanted-hit glints
now accompany the existing outline particles. Its mana drain, cancellation and protection mechanics
are unchanged.

## Scope and bounded lifetimes

Spell IDs, progression, mana costs, damage, targeting, ally protection, durations, cooldowns and
audio remain unchanged. Doom's description is updated to describe its new visible presentation.
God particle emissions use per-emission caps plus the existing configurable shared native-particle
budget. Formation and impact leases are finite, capped and cleared when the client world changes.

## Verification

All runs use Minecraft **1.21.1**, **NeoForge 21.1.265**, **Java 21** and Iron's Spells
**1.21.1-3.16.3**. Both addons means Bosses'Rise **2.1.2** and Alex's Caves **2.0.10**;
no optional dependency was added to this revision.

| Check | Evidence |
| --- | --- |
| Core dedicated server, without either optional addon | **14,354 checks passed.** |
| Dedicated server with both optional addons | **20,303 checks passed.** |
| All twenty God-tier presentation scenes | Rendered and inspected charge, active and impact frames. These synthetic messages check presentation rather than combat. |
| Doom Beam | Rendered charge and sustained beam from external and first-person views; open focus, particle stream and no black orb. |
| Book inventory lighting | Rendered all nine items through Minecraft's real item GUI renderer. |
| Real Helvar cast | The integrated server's real player casts the spell; logs confirm the native sword's descent and landing, with captured impact and aftermath. |
| Ordinary versus final Helvar hit | A targeted client regression with a real victim checks precursor feedback and the single final landing wake. See the accompanying validation report for the result. |

The source/resource regression tool separately verifies all 227 spell balance definitions, the
native routes and real dependency fields, protected texture/audio bytes, nine outer lighting fixes,
unchanged server combat after stripping presentation calls, and 40 ordinary/final cue cases.
The release package includes those reports, client captures and raw build/server/client logs.

Vanilla rendering is the graphical test environment. This does not certify shader/resource-pack
compatibility, subjective audio quality, or multiplayer stress performance. In crowded overlapping
casts, the existing global particle allowance can reduce later accents; finite per-profile and
impact limits keep the native effects bounded.
