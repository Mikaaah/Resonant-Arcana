package nl.townsandkingdoms.resonantarcana;

/** A single functional home for every spell, used by the archive, scroll tabs and guide. */
enum SpellCategory {
    PROJECTILE("Projectiles"), BEAM("Beams & Channels"), AREA("Area Magic"), CONTROL("Control"),
    MOBILITY("Movement"), SUPPORT("Support & Defense"), SUMMON("Summons & Constructs"),
    MELEE("Strikes & Drains"), BOSS_ART("Boss Arts"), CATACLYSM("Cataclysms");
    final String title;SpellCategory(String title){this.title=title;}
}
