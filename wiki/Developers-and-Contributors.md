# Developers & Contributors

## Building

Use Java 21. Supply local compile-only copies of Bosses’Rise 2.1.2 and GeckoLib 4.8.2, then run:

```sh
./gradlew build -PbossesRiseJar=/path/to/bossesrise.jar -PgeckolibJar=/path/to/geckolib.jar
```

On Windows use `gradlew.bat` with the same properties. Optionally supply `-PironsSpellbooksJar=/path/to/irons_spellbooks.jar` for a local Iron’s API copy. Dependency JARs are used only to compile and are never included in the addon. Output is written to `build/libs/`. For an offline build, supply `-PironsSpellbooksJar=/path/to/irons_spellbooks-1.21.1-3.16.3.jar`; the dependency is compile-only and is never bundled.

The validated catalogue at `src/main/resources/assets/resonant_arcana/catalogue.json` supplies runtime defaults. `tools/build_docs.py`, `tools/build_spell_icons.py` and `tools/build_wands.py` regenerate the public spell guide, icons, wand sprites and recipes. Original Effekseer assets can be rebuilt with `tools/build_effects.py`; Pillow and Effekseer 1.80.7 are required for artwork and particle exports. `verifyEffects` checks the effect manifest and packaged texture paths during the build.

## Validation

0.19.0-alpha passed **5,340 dedicated-server checks without Bosses’Rise** and **6,064 with Bosses’Rise 2.1.2**. Java 21 compilation and Gradle checks pass, including verification that the JAR contains exactly 24 particle effects. These server checks cover combat, targeting, cast timing, allegiance, optional registration and cleanup; they do not validate client rendering, visual balance or sound. In-game client playtesting remains required.

## Contributing

Found a bug or want to improve the project? Read the [contribution guide](../CONTRIBUTING.md) for reporting details, testing expectations and pull requests.

## Credits and license

By Towns & Kingdoms. [MIT license](../LICENSE).

See the [full contribution guidelines](https://github.com/Mikaaah/Resonant-Arcana/blob/main/CONTRIBUTING.md), [source code](https://github.com/Mikaaah/Resonant-Arcana) and [issue tracker](https://github.com/Mikaaah/Resonant-Arcana/issues).

[← Wiki Home](Home.md)
