# Validation — 0.3.0-alpha

## Result

Dedicated-server integration: **PASS — 347 checks, all 52 distinct spells**.
See `validation/server-integration.json` for the actual report.

Environment: Minecraft 1.21.1, NeoForge 21.1.219, Java 21.0.12.1,
Linux dedicated server, isolated flat test world, this mod only.
No original Bukkit plugin was loaded.

## Coverage

- Registry initialization and server startup without loading client-only classes.
- Every spell can start, rejects immediate cooldown repeats, produces an
  expected gameplay effect against test entities (or movement/ward effect),
  and expires without leaving timed tasks.
- Action and visual packet codecs round-trip with the actual Minecraft buffer.
- Selection wraps, retains unrelated custom data, and survives ItemStack save/load.
- A solid wall blocks targeted selection and projectile damage.
- Spell impact leaves the wall unchanged.
- Scoreboard allies with friendly fire disabled cannot be targeted.
- Missing wand, invalid actions, spectator actions and same-tick repeated actions
  are rejected by the server entry point.
- Eight concurrent spells clean up; a ninth active timed spell is rejected.
- Status command executes and reports seven wands / 52 spells.
- All seven registered wands expose their intended spell selections.
- All themed and Soul-specific sound references exist in the 1.21.1 registry.
- Chain Lightning damages two different visible entities.
- Force Push and Force Pull set movement in opposite expected directions.
- Soul Seeker tracks a target moved sideways after casting.
- Gravity Well draws a nearby target inward on its first tick.
- A wall rejects acquisition for Seeker, Chain Lightning and Force Pull.
- Soul sonic beam does not damage during its charge; it pierces two visible
  targets when released and leaves no active task.
- A solid wall stops sonic beam damage and Soul Siphon target acquisition.
- Soul Siphon heals up to one heart after successful health damage and does
  not heal from an invulnerable target.
- Soul Prison applies darkness and slowness.
- Both Blood Ward and Echo Ward redirect an incoming arrow.

Fixtures use NeoForge FakePlayer and actual server entities/collision/damage,
not a connected graphical client. Timed spell ticks are advanced directly for
repeatability; this does not measure real-time multiplayer performance.

An additional movement check exposed the initial damage knockback overcoming
Gravity Well's first pull. The well now sets inward horizontal movement while
retaining damped vertical motion; the movement check and all spell checks pass.
Final source was compiled into the delivered JAR.

## Not verified

- Actual client rendering, firework appearance, sounds or mouse/keyboard inputs.
- Remote client/server packet delivery (codec tests are not a connected client).
- Epic Fight, WOM, Nightfall, shaders, FTB Teams/Chunks, claims or a complete pack.
- PvP between connected players, every projectile type, or performance under load.
- Exact visual/balance equivalence with either original plugin.

## Suggested client test

1. Install on both client and server, or a standalone singleplayer instance.
2. Take all seven wands from the creative tab; confirm vanilla-textured items.
3. Test left-click, right-click and sneak-right-click; rebind G/V if occupied.
4. Test sparks, waves and comets on mobs and against a wall.
5. Test coloured spark impacts, crimson blood effects, pulses, fire twister, capture and the blood ward.
6. Test Dark homing/cage/aura, Thunder chain/storm, and Mephi gravity/push/pull.
7. Test all eight Soul spells, particularly charge/release audio, teal/violet
   sparks, sculk/amethyst impacts, healing and Echo Ward.
8. Check that normal blocks and items still behave normally after switching away.
9. Add Epic Fight, test its modes and use the alternate cast key if it captures clicks.
10. Verify pack-specific claims/PvP rules before using on the public server.

Check cast, travel/sustain and impact audio with the **Players** sound category
enabled. Adjust `soundVolume` in the common config to taste. AAAParticles is not
used by this release, and coexistence in the full pack is not tested here.

## Build notes

`build` compiles the client and server code and packages resources. The integrated
server fixture is a separate explicit developer run, not part of ordinary build.
The build environment reused existing matching Minecraft/NeoForge development
artifacts and skipped their regeneration and client-asset downloads; mod sources
were compiled freshly. The distributed source uses the standard Gradle/ModDev
build without environment-specific toolchain paths.
