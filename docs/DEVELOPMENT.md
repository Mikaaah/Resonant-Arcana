# Contributor reference

## Building and validation

Supply compile-only API JARs, then run with Java 21:

```sh
./gradlew build -PironsSpellbooksJar=/path/irons_spellbooks.jar -PbossesRiseJar=/path/bossesrise.jar -PgeckolibJar=/path/geckolib.jar -PcuriosJar=/path/curios.jar -PalexsCavesJar=/path/alexscaves-2.0.10.jar -PcitadelJar=/path/citadel.jar
```

The packaged-asset check verifies equipment, spell icons and tags, exactly four selective AAA effects and eight recorded OGG streams, and rejects retired wand textures. Dedicated-server fixtures cover registration, native casting, mana, cancellation, targeting, equipment perks and cleanup. **0.30.0-alpha:** dedicated-server validation passed with neither optional addon, each addon separately, and both together. The final combined run passed **24,057 checks** across 204 spells. Coverage includes native charging and cancellation, locked God-tier targets, mana, exact cave-art hit schedules, ally and cover rules, following beams, projectile protection, cave ceiling clearance, finite sound and visual cleanup, tags, tooltips, and actual recipe-manager selection for all 50 themed scroll recipes. In-game client visuals and sound require playtesting.

## Public 0.2.0 release

Public 0.1.0 corresponds to internal 0.28.0-alpha; 0.2.0 combines the subsequent 0.29–0.30 changes. This release renumbers the Gradle archive, NeoForge metadata and in-game status without changing protocol 21 or saved spell IDs. The integration results above belong to the prior 0.30 development build, not a new run. Historical labels are retained in [internal development history](INTERNAL_CHANGELOG.md).

The 0.2.0 release was rebuilt from source with Java 21 and passed `build`/`verifyPackagedAssets`. Archive inspection confirmed version 0.2.0 in NeoForge metadata and the status command, unchanged bytes for all 345 gameplay assets from 0.30, no bundled optional APIs or environment helpers, and ZIP integrity. This release did not rerun the dedicated-server integration matrix or client playtesting. The temporary workspace required proxy configuration and an explicit Java executable fallback for NeoForm; neither is part of the mod.

## 0.3.0 ritual and orbital update

Java 21 build and packaged-asset verification passed, including ten new original audio clips, the new orbital spell icon and all existing assets. Fresh dedicated-server runs passed **12,858 checks** without either optional gameplay addon and **18,450 checks** with Bosses’Rise 2.1.2 and Alex’s Caves 2.0.10 together. Focused coverage includes fixed 15-second native Orbital Ray casting and interruption, mark locking after turning, exact release tick and single-hit damage, allies/radius, overhead clearance, new beam obstruction, finite cleanup, no TNT entities, owned payload codecs, progress validation and unchanged supplied recording timings. Client rendering and subjective sound balance have not been playtested in game.

The sound design is reproducible with `tools/build_ritual_audio.py`; 0.3.0 uses protocol 22. Original recordings are byte-identical to 0.2.0. Only the new spell is appended to the enum, preserving prior saved ordinals and registry IDs.
