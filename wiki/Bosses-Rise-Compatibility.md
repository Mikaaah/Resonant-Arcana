# Optional Bosses’Rise Integration

## Optional Bosses’Rise integration

Install **Bosses’Rise 2.1.2** on both client and server to add **Kraken’s Grasp**, **Ice Spike Eruption**, and **Helvar’s Rift**, and **Kraken’s Wrath**. These are native Iron’s spells with levels I–V. Their models, animations and sounds come from the installed Bosses’Rise mod; no third-party assets are included in this addon. Without it, the 137-spell core kit remains available. Other Bosses’Rise versions leave this integration disabled until validated.

Boss attacks are adapted for player use: visual entities cannot run native boss attacks, and our shared engine handles damage, ally protection, PvP and walls. Kraken’s Grasp is a delayed tentacle slam with slowing, rather than an enemy-grabbing mechanic. See [spell details and timing](SPELLS.md#optional-bossesrise-spells).

## Timing and behavior

### Details from the spell reference

Bosses’Rise **2.1.2** adds Kraken’s Grasp and Kraken’s Wrath (Venom / Nature), Ice Spike Eruption (Frost / Ice), and Helvar’s Rift (Dark / Ender). Without that mod, the four spells are absent from registries, scrolls, commands and wand selections. Existing spell IDs remain unchanged. Other Bosses’Rise versions leave this adapter disabled until validated.

Visual-only entities reference the installed mod’s models, animations and textures. They do not run original boss attack ticks, persist in saves, break terrain, drop loot or qualify as spell targets. Damage and status effects use Resonant Arcana’s targeting, line-of-sight, PvP and friendly-fire rules. Sounds also come from the installed mod; no third-party assets are bundled.

Kraken’s Grasp has a 1.2-second cast bar, followed by approximately 2.3 seconds of rise/attack animation before one slam; its visual fades afterward. Ice Spike Eruption has a 0.8-second cast bar, then five eruptions over 0.75–1.35 seconds; each enemy can take one hit per cast. Helvar’s Rift has a 0.9-second cast bar, then travels at 1.2 blocks per tick up to 28 blocks, stopping at walls or its first enemy. Ground spells need a visible solid surface within a bounded ground search; they cannot place attacks through ceilings or walls.

## Find the spells

- [Venom Wand](Venom-Wand.md): Kraken’s Grasp and Kraken’s Wrath.
- [Frost Wand](Frost-Wand.md): Ice Spike Eruption.
- [Dark Wand](Dark-Wand.md): Helvar’s Rift.

**Only Bosses’Rise 2.1.2 is documented as compatible.** Do not assume later versions are supported.

[← Wiki Home](Home.md)
