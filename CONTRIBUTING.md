# Contributing to Resonant Arcana

Resonant Arcana is an experimental addon for **Minecraft 1.21.1**, **NeoForge**, and **Iron's Spells 'n Spellbooks**. Suggestions, bug reports, spell tuning and focused contributions are welcome.

## Bug reports

Please include:

- Resonant Arcana, NeoForge and Iron's Spells versions; client/server environment and relevant optional mods.
- Wand name, spell ID, school, spell rank and keybind used.
- Expected versus actual behavior, reproducible steps, logs and screenshots or a short recording where useful.
- Whether the problem also happens without unrelated mods.

Please remove private credentials, tokens and addresses from logs.

## Changes and pull requests

- Keep spell changes consistent with existing mana, cooldown, casting and networking systems.
- Update [SPELLS.md](SPELLS.md), [CONFIGURATION.md](CONFIGURATION.md) and [CHANGELOG.md](CHANGELOG.md) when relevant.
- Test on the supported NeoForge/Java 21 environment when possible and list anything untested.
- Do not bundle Iron's Spells or other mods' source, textures or audio.
- **Do not rename the existing `kingdomwands` registry namespace casually.** Current saves depend on those identifiers. A rename requires an explicit migration and compatibility plan.

To build, use Java 21 and follow the [README build instructions](README.md#building). 
