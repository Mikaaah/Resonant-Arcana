# Contributor reference

## Building and validation

Supply compile-only API JARs, then run with Java 21:

```sh
./gradlew build -PironsSpellbooksJar=/path/irons_spellbooks.jar -PbossesRiseJar=/path/bossesrise.jar -PgeckolibJar=/path/geckolib.jar -PcuriosJar=/path/curios.jar -PalexsCavesJar=/path/alexscaves-2.0.10.jar -PcitadelJar=/path/citadel.jar
```

The packaged-asset check verifies equipment, spell icons and tags, exactly four selective AAA effects and eight recorded OGG streams, and rejects retired wand textures. Dedicated-server fixtures cover registration, native casting, mana, cancellation, targeting, equipment perks and cleanup. **0.30.0-alpha:** dedicated-server validation passed with neither optional addon, each addon separately, and both together. The final combined run passed **24,057 checks** across 204 spells. Coverage includes native charging and cancellation, locked God-tier targets, mana, exact cave-art hit schedules, ally and cover rules, following beams, projectile protection, cave ceiling clearance, finite sound and visual cleanup, tags, tooltips, and actual recipe-manager selection for all 50 themed scroll recipes. In-game client visuals and sound require playtesting.
