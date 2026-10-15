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
    SOUL_SEEKER("Shadow Seeker", Kind.SEEKER, Theme.DARK, 6, 2, 36, 70),
    DARK_WAVE("Dark Wave", Kind.WAVE, Theme.DARK, 5, 2.5, 28, 65),
    NIGHTFALL("Nightfall", Kind.BURST, Theme.DARK, 7, 5, 28, 120),
    SHADOW_CAGE("Dread Mark", Kind.MARK, Theme.DARK, 9, 3, 24, 140),
    SOUL_AURA("Dread Aura", Kind.AURA, Theme.DARK, 3, 8, 0, 160),
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
    GRAVITY_CHOKE("Suspension", Kind.SUSPEND, Theme.GRAVITY, 2, 1, 24, 140),
    SINGULARITY("Singularity", Kind.BURST, Theme.GRAVITY, 9, 5, 28, 160),
    ECHO_BOLT("Echo Bolt", Kind.BOLT, Theme.SOUL, 5, 1.5, 36, 25),
    SCULK_WAVE("Sculk Wave", Kind.WAVE, Theme.SOUL, 5, 2.5, 28, 65),
    SONIC_BOOM("Sonic Boom", Kind.SONIC, Theme.SOUL, 8, 0.8, 28, 120),
    SOUL_SIPHON("Soul Siphon", Kind.SIPHON, Theme.SOUL, 5, 1, 24, 100),
    SOUL_PRISON("Soul Prison", Kind.CHOKE, Theme.SOUL, 2, 1, 24, 140),
    SHRIEKING_PULSE("Shrieking Pulse", Kind.PULSE, Theme.SOUL, 6, 9, 0, 130),
    AMETHYST_NOVA("Amethyst Nova", Kind.BURST, Theme.SOUL, 8, 4.5, 28, 120),
    ECHO_WARD("Echo Ward", Kind.WARD, Theme.SOUL, 0, 3, 0, 180),
    ARCANE_VOLLEY("Arcane Volley", Kind.VOLLEY, Theme.MAGIC, 3, 1, 32, 90),
    SANGUINE_DRAIN("Sanguine Drain", Kind.SIPHON, Theme.BLOOD, 5, 1, 24, 100),
    BLOOD_LANCE("Blood Lance", Kind.LANCE, Theme.BLOOD, 6, 0.6, 24, 80),
    EMBER_VEIL("Ember Veil", Kind.AURA, Theme.FIRE, 2, 5, 0, 150),
    VENOM_BOLT("Venom Bolt", Kind.BOLT, Theme.POISON, 3, 1.5, 32, 35),
    MIASMA_FIELD("Miasma Field", Kind.FIELD, Theme.POISON, 2, 4, 24, 150),
    WITHERING_HEX("Withering Hex", Kind.TARGET, Theme.POISON, 3, 1, 24, 110);

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
    public String description(){return switch(this){
        case SHADOW_CAGE->"Curse a target; detonate after two seconds. Cover breaks the curse.";
        case GRAVITY_CHOKE->"Lift a target briefly, followed by a gentle descent.";
        case WITHERING_HEX->"Inflict poison and wither on an aimed target.";
        default->switch(kind){
            case BOLT->"A travelling projectile that stops at walls.";
            case TARGET->"Strike the living target you aim at.";
            case WAVE->"A wide travelling arc, hitting each enemy once.";
            case CONFUSE->"Briefly blind and disorient your target.";
            case STUN->"Slow and weaken your target.";
            case CAPTURE->"Hold a target briefly; cover breaks the tether.";
            case COMET->"A slow projectile with an area impact.";
            case PULSE->"An expanding shock ring around you.";
            case LAUNCH->"Launch your target upwards.";
            case LEAP->"Propel yourself forwards and upwards.";
            case LIGHTNING,SMITE->"Call a lightning strike at your aim.";
            case TWISTER->"A stationary vortex dealing repeated area damage.";
            case CHANNEL->"A short stream following your current aim.";
            case BURST->"An area burst centred on your aim.";
            case WARD->"Redirect incoming enemy projectiles for four seconds.";
            case SEEKER->"A projectile that follows your selected target.";
            case CHAIN->"Jump between up to five visible targets.";
            case STORM->"Call repeated lightning strikes over an area.";
            case WELL->"Draw nearby enemies into a gravity well.";
            case PUSH->"Push the target away from you.";
            case PULL->"Pull the target towards you.";
            case CHOKE->"Restrain and damage your target briefly.";
            case AURA->"A damaging aura that moves with you.";
            case SONIC->"Charge for 0.6 seconds, then fire a piercing sonic beam.";
            case SIPHON->"Drain health damage into healing, up to one heart.";
            case VOLLEY->"Fire three staggered arcane projectiles.";
            case LANCE->"An instant piercing line through visible enemies.";
            case FIELD->"Leave a poisonous cloud at your aim.";
            case MARK,SUSPEND->"Control an aimed living target.";
        };
    };}
    /** Shared recovery for equivalent healing, ward and restraint abilities across wands. */
    public String cooldownKey(){return switch(kind){
        case SIPHON->"shared_siphon";case WARD->"shared_ward";
        case CAPTURE,CHOKE,SUSPEND->"shared_restraint";case STUN->"shared_stun";
        default->name();
    };}
    public enum Kind { BOLT, TARGET, WAVE, CONFUSE, STUN, CAPTURE, COMET, PULSE, LAUNCH, LEAP, LIGHTNING, SMITE, TWISTER, CHANNEL, BURST, WARD, SEEKER, CHAIN, STORM, WELL, PUSH, PULL, CHOKE, AURA, SONIC, SIPHON, MARK, SUSPEND, VOLLEY, LANCE, FIELD }
    public enum Theme {
        MAGIC(0xB349F5), BLOOD(0xA10F28), POISON(0x68DB39), FIRE(0xFF821A), THUNDER(0x7BDFFF), DARK(0x632DB5), GRAVITY(0xCF58E8), SOUL(0x24D7CB);
        public final int color;
        Theme(int color) {this.color=color;}
    }
}
