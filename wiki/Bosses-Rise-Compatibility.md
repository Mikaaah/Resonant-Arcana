# Optional Bosses’Rise Integration


Bosses’Rise **2.1.2** adds twelve spells from five bosses. Kraken: Grasp, Wrath and Undertow. Yeti: Ice Spike Eruption and Glacial Wall. Underworld Knight (Helvar): Rift, Fracture and Swordfall. Sandworm: Eruption and Quicksand. Infernal Dragon: Infernal Barrage and Dragon’s Cataclysm. Without that mod, they are absent from registries, scrolls, commands and wand selections. Existing spell IDs remain unchanged. Other Bosses’Rise versions leave this adapter disabled until validated.

Visual-only entities reference the installed mod’s models, animations, textures and items: red Kraken tentacles, Yeti ice spikes, Helvar’s rift projectile and Knight Sword, Sandworm sand columns and the Infernal Dragon’s blazing projectiles. They do not run original boss attack ticks, persist in saves, break terrain, drop loot or qualify as spell targets. Damage and status effects use Resonant Arcana’s targeting, line-of-sight, PvP and friendly-fire rules. Sounds also come from the installed mod; no third-party assets are bundled.

Kraken tentacle roots are buried 2.25 blocks below the surface, and Wrath’s outer tentacles face inward. Kraken’s Grasp has a 1.2-second cast bar, followed by approximately 2.3 seconds of rise/attack animation before one slam; its visual fades afterward. Ice Spike Eruption has a 0.8-second cast bar, then five eruptions over 0.75–1.35 seconds; each enemy can take one hit per cast. Helvar’s Rift has a 0.9-second cast bar, then travels at 1.2 blocks per tick up to 28 blocks, stopping at walls or its first enemy. Ground spells need a visible solid surface within a bounded ground search; they cannot place attacks through ceilings or walls.


| Boss | Spells | Wand |
| --- | --- | --- |
| Kraken | Grasp, Wrath, Undertow | [Venom](Venom-Wand.md) |
| Yeti | Ice Spike Eruption, Glacial Wall | [Frost](Frost-Wand.md) |
| Underworld Knight / Helvar | Rift, Fracture, Swordfall | [Dark](Dark-Wand.md) |
| Sandworm | Eruption, Quicksand | [Rift](Rift-Wand.md) |
| Infernal Dragon | Infernal Barrage, Dragon’s Cataclysm | [Cinder](Cinder-Wand.md) |

Helvar’s Swordfall renders the installed mod’s original held Knight Sword geometry and animated textures, with its blade tip pointing down. Its saved spell ID is unchanged. After the cast bar, the sword falls for 1.2 seconds before one area impact. It does not drop an obtainable item or alter blocks.


[← Wiki Home](Home.md)
