# Validation — 0.4.0-alpha

Dedicated-server integration: **PASS — 575 checks, all 58 distinct spells**.
See `validation/server-integration.json` for the report and `SPELL_REVIEW.md`
for the complete catalogue and design decisions.

Environment: Minecraft 1.21.1, NeoForge 21.1.219, Java 21.0.12.1,
Linux dedicated server, isolated flat test world, this mod only.

## Coverage

- Eight wand registries initialize and the server loads without client-only classes.
- Every catalogue spell is available on exactly one wand; selections round-trip.
- All 57 numeric selection slots from the previous seven wands migrate using
  their original layouts, retaining unrelated custom data. A retained spell
  keeps its selection; a moved/removed selection falls back to the first spell.
- Stable spell IDs take priority over stale numeric indices.
- Item serialization, selection wrapping and both network packet codecs work.
- All 58 spells start, reject immediate cooldown repeats, produce their expected
  gameplay effect and expire without leaving timed tasks.
- Themed and Soul-specific sound IDs resolve in the actual sound registry.
- Arcane Volley fires three projectiles that can each hit a fresh target.
- Blood Lance and Sonic Boom each pierce two visible targets; walls stop damage.
- Sonic Boom deals no damage during its charge and ends its task on release.
- Dread Mark waits before damaging; taking cover interrupts the pending curse.
- Suspension lifts the target and applies slow falling for descent.
- Miasma applies poison to a susceptible living target; Withering Hex applies wither.
- Ember Veil affects an enemy inside its intended range and leaves a distant one unharmed.
- Both siphons heal only after actual health damage, up to one heart. An
  invulnerable target gives no healing. Switching siphons cannot bypass recovery.
- Blood Ward and Echo Ward redirect incoming arrows and share recovery.
- Soul Prison applies darkness and slowness. Gravity Well pulls inward.
- Chain Lightning jumps to two different visible targets. Shadow Seeker
  follows a target moved sideways after the cast. Force Push/Pull move in the expected directions.
- Wall checks cover targeted selection, travelling bolts, homing acquisition,
  chain acquisition, force targeting, siphons, curses, beams and Flamethrower.
- Impacts preserve blocks. Scoreboard friendly fire is respected.
- Missing wand, invalid action, spectator and same-tick repeated actions are rejected.
- Eight concurrent timed spells clean up; a ninth is rejected. Status command executes.

Fixtures use NeoForge FakePlayer, actual server entities and collision/damage.
Timed spell ticks are advanced directly for repeatability. They do not measure
real-time multiplayer performance or guarantee final combat balance.

## Not verified

- Graphical rendering, particle appearance, audio balance and mouse/keyboard input.
- Remote client/server delivery; codec checks do not represent a connected client.
- Epic Fight, shaders, AAAParticles, FTB Teams/Chunks, claims or the full modpack.
- Connected-player PvP, every projectile type and server load under many players.
- Exact behavior or visual equivalence with the original Bukkit plugins.

## Client test

Replace the old JAR on both client and server. Take all eight wands from the
creative tab. Check spell names, descriptions and G/V or mouse controls. Try
old saved wands: a retained spell should stay selected; moved Empire spells
start at Spark. Check cast/sustain/impact audio with the **Players** slider enabled.

Prioritize Volley, delayed Dread Mark/cover, Suspension/landing, Blood Lance,
siphon switching, Ember Veil radius, Venom status effects, and the complete
Soul lineup. Check performance and combat feel in the full pack before release.

## Build

The mod source was freshly compiled and packaged with Gradle/ModDev. This build
reused matching Minecraft/NeoForge development artifacts and skipped their
regeneration and graphical asset downloads. The distributed source uses the
standard Java 21 Gradle build without environment-specific paths.

The explicit developer fixture runs with
`./gradlew runServer -Dkingdomwands.integrationTest=true` and creates an arena
before stopping the isolated test server. It is disabled during normal gameplay.
