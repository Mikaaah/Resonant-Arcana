package nl.townsandkingdoms.kingdomwands;

/** Independent spell catalogue; values are initial recreation balance, not a binary plugin port. */
public enum Spell {
    SPARK("Spark", Kind.BOLT, Theme.MAGIC, 3, 1, 32, 12),
    EMPIRE_SPARK("Empire Spark", Kind.TARGET, Theme.MAGIC, 5, 2, 24, 35),
    BLOOD_SPARK("Blood Spark", Kind.TARGET, Theme.BLOOD, 5, 2, 24, 35),
    POISON_SPARK("Poison Spark", Kind.TARGET, Theme.POISON, 2, 2, 24, 50),
    BLOOD_WAVE("Blood Wave", Kind.WAVE, Theme.BLOOD, 5, 1.8, 28, 60),
    POISON_WAVE("Poison Wave", Kind.WAVE, Theme.POISON, 2, 1.8, 28, 70),
    FLAME_WAVE("Flame Wave", Kind.WAVE, Theme.FIRE, 5, 1.8, 28, 60),
    CONFUSE("Confuse", Kind.CONFUSE, Theme.MAGIC, 0, 1, 24, 100),
    STUN("Stun", Kind.STUN, Theme.MAGIC, 1, 1, 24, 100),
    CAPTURE("Capture", Kind.CAPTURE, Theme.MAGIC, 0, 1, 24, 160),
    EMPIRE_COMET("Empire Comet", Kind.COMET, Theme.MAGIC, 8, 3.5, 44, 100),
    FIRE_COMET("Fire Comet", Kind.COMET, Theme.FIRE, 8, 3.5, 44, 100),
    EMPIRE_PULSE("Empire Pulse", Kind.PULSE, Theme.MAGIC, 6, 7, 0, 100),
    FIRE_PULSE("Fire Pulse", Kind.PULSE, Theme.FIRE, 6, 7, 0, 100),
    FIREBALL("Fireball", Kind.BOLT, Theme.FIRE, 6, 2, 40, 50),
    IGNITE("Ignite", Kind.TARGET, Theme.FIRE, 2, 1, 24, 50),
    LAUNCH("Launch", Kind.LAUNCH, Theme.MAGIC, 2, 1, 24, 70),
    LEAP("Leap", Kind.LEAP, Theme.MAGIC, 0, 0, 0, 80),
    LIGHTNING("Lightning", Kind.LIGHTNING, Theme.THUNDER, 7, 2, 32, 100),
    SMITE("Smite", Kind.SMITE, Theme.THUNDER, 10, 4, 32, 160),
    FIRE_TWISTER("Fire Twister", Kind.TWISTER, Theme.FIRE, 7, 3, 30, 120),
    FLAME_THROWER("Flamethrower", Kind.CHANNEL, Theme.FIRE, 2, 1.4, 12, 100),
    FLAME_SHOCK_WAVE("Flame Shock Wave", Kind.PULSE, Theme.FIRE, 7, 9, 0, 130),
    BLOOD_EXPLODE("Blood Explode", Kind.BURST, Theme.BLOOD, 8, 4, 24, 120),
    BLOOD_STUN("Blood Stun", Kind.STUN, Theme.BLOOD, 2, 1, 24, 100),
    BLOOD_BLOCK("Blood Ward", Kind.WARD, Theme.BLOOD, 0, 3, 0, 180),
    SHADOW_BOLT("Shadow Bolt", Kind.BOLT, Theme.DARK, 5, 1.5, 36, 25),
    SOUL_SEEKER("Soul Seeker", Kind.SEEKER, Theme.DARK, 6, 2, 36, 70),
    DARK_WAVE("Dark Wave", Kind.WAVE, Theme.DARK, 5, 2.5, 28, 65),
    NIGHTFALL("Nightfall", Kind.BURST, Theme.DARK, 7, 5, 28, 120),
    SHADOW_CAGE("Shadow Cage", Kind.CHOKE, Theme.DARK, 2, 1, 24, 140),
    SOUL_AURA("Soul Aura", Kind.AURA, Theme.DARK, 3, 8, 0, 160),
    THUNDER_BOLT("Thunder Bolt", Kind.BOLT, Theme.THUNDER, 6, 1.5, 40, 30),
    CHAIN_LIGHTNING("Chain Lightning", Kind.CHAIN, Theme.THUNDER, 5, 6, 28, 110),
    THUNDER_WAVE("Thunder Wave", Kind.WAVE, Theme.THUNDER, 5, 2.5, 30, 65),
    STORM_CALL("Storm Call", Kind.STORM, Theme.THUNDER, 4, 5, 28, 180),
    THUNDER_PULSE("Thunder Pulse", Kind.PULSE, Theme.THUNDER, 7, 9, 0, 120),
    STATIC_SHOCK("Static Shock", Kind.STUN, Theme.THUNDER, 3, 1, 28, 80),
    GRAVITY_BOLT("Gravity Bolt", Kind.BOLT, Theme.GRAVITY, 5, 2, 32, 30),
    GRAVITY_WELL("Gravity Well", Kind.WELL, Theme.GRAVITY, 3, 5, 28, 160),
    FORCE_PUSH("Force Push", Kind.PUSH, Theme.GRAVITY, 2, 1, 24, 65),
    FORCE_PULL("Force Pull", Kind.PULL, Theme.GRAVITY, 2, 1, 24, 65),
    GRAVITY_CHOKE("Gravity Choke", Kind.CHOKE, Theme.GRAVITY, 2, 1, 24, 140),
    SINGULARITY("Singularity", Kind.BURST, Theme.GRAVITY, 9, 5, 28, 160);

    public final String title;
    public final Kind kind;
    public final Theme theme;
    public final float damage;
    public final double radius, range;
    public final int cooldown;
    Spell(String title, Kind kind, Theme theme, float damage, double radius, double range, int cooldown) {
        this.title=title; this.kind=kind; this.theme=theme; this.damage=damage;
        this.radius=radius; this.range=range; this.cooldown=cooldown;
    }
    public enum Kind { BOLT, TARGET, WAVE, CONFUSE, STUN, CAPTURE, COMET, PULSE, LAUNCH, LEAP, LIGHTNING, SMITE, TWISTER, CHANNEL, BURST, WARD, SEEKER, CHAIN, STORM, WELL, PUSH, PULL, CHOKE, AURA }
    public enum Theme {
        MAGIC(0xB349F5), BLOOD(0xA10F28), POISON(0x68DB39), FIRE(0xFF821A), THUNDER(0x7BDFFF), DARK(0x632DB5), GRAVITY(0xCF58E8);
        public final int color;
        Theme(int color) {this.color=color;}
    }
}
