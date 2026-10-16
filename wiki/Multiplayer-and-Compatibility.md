# Multiplayer & Compatibility

## Multiplayer and compatibility

Offensive spells respect server PvP, scoreboard friendly-fire rules and the caster’s own tameable pets. Healing affects the caster, pets and scoreboard teammates; by default it also affects other players when both players have no scoreboard team. Competitive servers can disable teamless healing.

Spells require appropriate line of sight and leave terrain intact. Visual and simulation budgets prevent unbounded effect spawning. Wards, life drains, healing, stuns and restraints share recovery within their respective groups; changing items does not bypass those cooldowns.

Dedicated claim-protection, FTB Teams and Epic Fight integrations are not included. This is an **alpha**: automated server and native effect checks do not replace testing balance, animation, audio and GUI behaviour in your modpack.

## Charge, release, impact

Basic projectiles and movement stay responsive. Larger spells have readable windups and distinct releases: a marked meteor impact, a growing black hole, an advancing ice front, or a broad spatial orb formed from opposing energies. **Soul Cannon** keeps its two-second void charge, travelling echo blast, four-block firework cadence and large warden/crystal detonation.

Effects use different shapes as well as school colours: sweeping crescents, orbiting blades, rising feathers, lantern totems, falling hail, frost spines, shadow petals and braided fusion trails. Charging effects use motes and flowing energy without camera-facing casting circles.

Layered **Iron’s and vanilla sound events** provide preparation, release and impact cues. Personal impact feedback lets the caster hear distant hits; nearby players hear positional audio. No parent-mod audio files are bundled.

**AAAParticles is optional.** Installing it adds 222 packaged native effects; Iron’s particles and vanilla trails remain available without it. The addon includes no native renderer or extra particle-mod dependency.

## Optional content

- [Bosses’Rise Compatibility](Bosses-Rise-Compatibility.md) — tested Bosses’Rise version and behavior.
- [Configuration](Configuration.md) — server-side damage, healing, PvP and particle budgets.

## Compatibility reporting

When reporting conflicts with Epic Fight, claim plugins or other systems, include mod versions, the specific spell, a minimal reproduction and relevant logs. **Do not assume native support for integrations not listed above.**

[← Wiki Home](Home.md)
