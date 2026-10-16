# Getting Started

## Installation

Requires **Java 21**, **Minecraft 1.21.1**, **NeoForge 21.1.219 or later within 21.1.x**, and **Iron’s Spells ’n Spellbooks 1.21.1-3.16.3** with its own dependencies. AAAParticles **2.3.3** is the tested optional version.

Install the same Resonant Arcana version on **client and server**. Version **0.13 is a clean namespace break**: all addon IDs now use `resonant_arcana`. Remove the previous addon JAR. Older wands, scrolls, spellbook entries and key bindings are not migrated. Obtain new items and reinscribe spells using the new IDs. There are no legacy registry aliases.

Settings remain in the world’s **serverconfig** and each client’s local config file. See the [configuration guide](CONFIGURATION.md). Servers can disable direct wand casting for a spellbook-only pack.

## Crafting and upgrading spells

### Wands, levels and scrolls

**Core spells have levels I–V except DOOM.** DOOM remains a single-level Legendary spell. Without equipment, direct damage and healing scale to 100%, 125%, 150%, 175%, and 200% of their level-I values. Mana costs increase with rank; high ranks can require more than the default mana pool, so mana-capacity equipment matters. Some utility spells improve duration, absorption or blink range; their effect amplifiers and other limits are documented in the [spell reference](SPELLS.md).

Craft a wand with **two sticks, two Arcane Essence and its school ingredient**. Recipes unlock when you acquire Arcane Essence; recipe viewers show the exact pattern. Ingredients range from amethyst and blaze powder to echo shards and blue ice.

A new wand has its whole kit at level I. To upgrade one spell:

1. Hold its wand in your main hand.
2. Place a matching higher-level **Iron’s scroll** in your offhand.
3. Sneak-use the wand. It learns that rank, selects the spell and consumes one scroll.

Other-wand scrolls, already-known ranks and server-disabled levels are rejected without consumption. Ranks save separately on each wand. Higher-rank scrolls can also be inscribed into normal Iron’s spellbooks. The addon does not add custom spellbooks.

## Next steps

- [Wand Index](Wand-Index.md) — pick a school and compare the available spells.
- [Controls & Commands](Controls-and-Commands.md) — learn the keybinds and admin commands.
- [Configuration](Configuration.md) — server, balance, audio and client settings.
- [Troubleshooting](Troubleshooting.md) — common installation and behavior questions.

[← Wiki Home](Home.md)
