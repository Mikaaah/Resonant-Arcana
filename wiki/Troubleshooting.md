# Troubleshooting

## The mod does not load

Verify Minecraft **1.21.1**, a compatible **NeoForge 21.1.x** build, **Java 21**, and Iron's Spells ’n Spellbooks **1.21.1-3.16.3** with its dependencies. Both client and server must use the same Resonant Arcana release. Remove older JARs rather than loading two versions.

## Old wands or scrolls disappeared after upgrading

Version **0.13** switched to the `resonant_arcana` registry namespace without migration or legacy aliases. Old registered items, spellbook entries, keybinds and selections do not transfer automatically. Restore a world backup if needed, then obtain new items and reinscribe spells. See [Getting Started](Getting-Started.md).

## A spell doesn't appear in the picker

Some spells have more than 12 slots per wand. Use the picker’s page buttons or Left/Right navigation. Bosses’Rise spells only register when the tested **2.1.2** dependency is installed on both sides.

## The wand doesn't cast or casts unexpectedly

Check controls under **Controls → Resonant Arcana**. If a combat mod intercepts attacks, disable `controls.leftClickCasting` in the [client configuration](Configuration.md) and use the designated cast key. Check available mana, cooldowns and server spell enablement.

## Effects seem missing or reduced

AAAParticles is optional. Native Iron's and vanilla fallback particles should remain available. Review client particle settings and shared server visual budgets, then run `/arcana_visuals` for diagnostics.

## Multiplayer healing or damage behaves unexpectedly

PvP, scoreboard team friendly-fire, pet ownership, line of sight and `combat.healTeamlessPlayers` all matter. See [Multiplayer & Compatibility](Multiplayer-and-Compatibility.md).

## Reporting a bug

Use [New bug report](https://github.com/Mikaaah/Resonant-Arcana/issues/new?template=bug_report.yml). Include the mod version, dependency versions, spell ID and level, reproduction steps, logs and screenshots. Remove private information from logs.

[← Wiki Home](Home.md)
