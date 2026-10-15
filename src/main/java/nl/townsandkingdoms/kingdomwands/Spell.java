package nl.townsandkingdoms.kingdomwands;

/** Spell catalogue. Enum identifiers remain stable for existing saved wands. */
public enum Spell {
    SPARK("Spark", Kind.BOLT, Theme.MAGIC, 3, 1, 32, 12),
    EMPIRE_SPARK("Arcane Strike", Kind.TARGET, Theme.MAGIC, 5, 2, 24, 35),
    BLOOD_SPARK("Crimson Strike", Kind.TARGET, Theme.BLOOD, 5, 2, 24, 35),
    POISON_SPARK("Venom Sting", Kind.TARGET, Theme.POISON, 2, 2, 24, 50),
    BLOOD_WAVE("Blood Wave", Kind.WAVE, Theme.BLOOD, 5, 1.8, 28, 60),
    POISON_WAVE("Poison Wave", Kind.WAVE, Theme.POISON, 2, 1.8, 28, 70),
    FLAME_WAVE("Flame Wave", Kind.WAVE, Theme.FIRE, 5, 1.8, 28, 60),
    CONFUSE("Mind Haze", Kind.CONFUSE, Theme.MAGIC, 0, 1, 24, 100),
    STUN("Stun", Kind.STUN, Theme.MAGIC, 1, 1, 24, 100),
    CAPTURE("Arcane Bind", Kind.CAPTURE, Theme.MAGIC, 0, 1, 24, 160),
    EMPIRE_COMET("Astral Comet", Kind.COMET, Theme.MAGIC, 14, 4.5, 48, 120),
    FIRE_COMET("Fire Comet", Kind.COMET, Theme.FIRE, 12, 4.5, 44, 120),
    EMPIRE_PULSE("Arcane Repulse", Kind.PULSE, Theme.MAGIC, 8, 7, 0, 120),
    FIREBALL("Fireball", Kind.BOLT, Theme.FIRE, 6, 2, 40, 50),
    IGNITE("Ignite", Kind.TARGET, Theme.FIRE, 2, 1, 24, 50),
    LAUNCH("Launch", Kind.LAUNCH, Theme.MAGIC, 2, 1, 24, 70),
    LEAP("Leap", Kind.LEAP, Theme.MAGIC, 0, 0, 0, 80),
    LIGHTNING("Lightning", Kind.LIGHTNING, Theme.THUNDER, 7, 2, 32, 100),
    SMITE("Smite", Kind.SMITE, Theme.THUNDER, 18, 5, 32, 200),
    FIRE_TWISTER("Cinder Vortex", Kind.TWISTER, Theme.FIRE, 7, 3, 30, 120),
    FLAME_THROWER("Flamethrower", Kind.CHANNEL, Theme.FIRE, 2, 1.4, 12, 100),
    FLAME_SHOCK_WAVE("Cinder Surge", Kind.PULSE, Theme.FIRE, 9, 9, 0, 150),
    BLOOD_EXPLODE("Crimson Rupture", Kind.BURST, Theme.BLOOD, 12, 5, 24, 140),
    BLOOD_STUN("Hemorrhagic Shock", Kind.STUN, Theme.BLOOD, 2, 1, 24, 100),
    BLOOD_BLOCK("Blood Ward", Kind.WARD, Theme.BLOOD, 0, 3, 0, 180),
    SHADOW_BOLT("Shadow Bolt", Kind.BOLT, Theme.DARK, 5, 1.5, 36, 25),
    SOUL_SEEKER("Umbral Seeker", Kind.SEEKER, Theme.DARK, 6, 2, 36, 70),
    DARK_WAVE("Umbral Wave", Kind.WAVE, Theme.DARK, 5, 2.5, 28, 65),
    NIGHTFALL("Nightfall", Kind.BURST, Theme.DARK, 12, 6, 28, 140),
    SHADOW_CAGE("Dread Mark", Kind.MARK, Theme.DARK, 14, 3.5, 24, 160),
    SOUL_AURA("Umbral Veil", Kind.AURA, Theme.DARK, 3, 8, 0, 160),
    THUNDER_BOLT("Thunder Bolt", Kind.BOLT, Theme.THUNDER, 6, 1.5, 40, 30),
    CHAIN_LIGHTNING("Chain Lightning", Kind.CHAIN, Theme.THUNDER, 6, 6, 28, 120),
    THUNDER_WAVE("Thunder Wave", Kind.WAVE, Theme.THUNDER, 5, 2.5, 30, 65),
    STORM_CALL("Storm Call", Kind.STORM, Theme.THUNDER, 5, 6, 28, 240),
    THUNDER_PULSE("Thunder Pulse", Kind.PULSE, Theme.THUNDER, 10, 9, 0, 150),
    STATIC_SHOCK("Static Shock", Kind.STUN, Theme.THUNDER, 3, 1, 28, 80),
    GRAVITY_BOLT("Rift Bolt", Kind.BOLT, Theme.GRAVITY, 5, 2, 32, 30),
    GRAVITY_WELL("Rift Well", Kind.WELL, Theme.GRAVITY, 3, 5, 28, 180),
    FORCE_PUSH("Force Push", Kind.PUSH, Theme.GRAVITY, 2, 1, 24, 65),
    FORCE_PULL("Force Pull", Kind.PULL, Theme.GRAVITY, 2, 1, 24, 65),
    GRAVITY_CHOKE("Suspension", Kind.SUSPEND, Theme.GRAVITY, 2, 1, 24, 140),
    SINGULARITY("Singularity", Kind.BURST, Theme.GRAVITY, 18, 6, 28, 180),
    ECHO_BOLT("Echo Bolt", Kind.BOLT, Theme.SOUL, 5, 1.5, 36, 25),
    SCULK_WAVE("Sculk Wave", Kind.WAVE, Theme.SOUL, 5, 2.5, 28, 65),
    SONIC_BOOM("Sonic Boom", Kind.SONIC, Theme.SOUL, 8, 0.8, 28, 120),
    SOUL_SIPHON("Soul Siphon", Kind.SIPHON, Theme.SOUL, 5, 1, 24, 100),
    SOUL_PRISON("Soul Prison", Kind.CHOKE, Theme.SOUL, 2, 1, 24, 140),
    SHRIEKING_PULSE("Shrieking Pulse", Kind.PULSE, Theme.SOUL, 9, 9, 0, 150),
    AMETHYST_NOVA("Amethyst Nova", Kind.BURST, Theme.SOUL, 14, 5.5, 28, 160),
    ECHO_WARD("Echo Ward", Kind.WARD, Theme.SOUL, 0, 3, 0, 180),
    ARCANE_VOLLEY("Arcane Volley", Kind.VOLLEY, Theme.MAGIC, 3, 1, 32, 90),
    SANGUINE_DRAIN("Sanguine Drain", Kind.SIPHON, Theme.BLOOD, 5, 1, 24, 100),
    BLOOD_LANCE("Blood Lance", Kind.LANCE, Theme.BLOOD, 9, 0.6, 24, 90),
    EMBER_VEIL("Ember Veil", Kind.AURA, Theme.FIRE, 2, 5, 0, 150),
    VENOM_BOLT("Venom Bolt", Kind.BOLT, Theme.POISON, 3, 1.5, 32, 35),
    MIASMA_FIELD("Miasma Field", Kind.FIELD, Theme.POISON, 3, 4.5, 24, 180),
    WITHERING_HEX("Withering Hex", Kind.TARGET, Theme.POISON, 3, 1, 24, 110),
    SOUL_CANNON("Soul Cannon", Kind.CANNON, Theme.SOUL, 16, 5, 48, 280);

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
    /** Base windup in ticks. Movement and basic bolts remain immediate. */
    public int castTicks(){return switch(this){
        case SOUL_CANNON->40;case SONIC_BOOM->12;
        case SINGULARITY,STORM_CALL->32;case SMITE->28;case FIRE_TWISTER,GRAVITY_WELL->25;
        case AMETHYST_NOVA->24;case EMPIRE_COMET,FIRE_COMET,BLOOD_EXPLODE,NIGHTFALL->20;
        case SHRIEKING_PULSE,FLAME_SHOCK_WAVE->16;case MIASMA_FIELD->15;
        case THUNDER_PULSE->14;case CHAIN_LIGHTNING,SOUL_SEEKER->12;
        case BLOOD_LANCE,ARCANE_VOLLEY,EMPIRE_PULSE,SHADOW_CAGE->10;
        default->switch(kind){
            case BOLT,LEAP,LAUNCH,PUSH,PULL->0;
            case TARGET->5;case WAVE->7;case STUN,CONFUSE,CHANNEL->8;
            case CAPTURE,CHOKE,SUSPEND->12;case SIPHON->14;
            case WARD,AURA->10;default->8;
        };
    };}
    public int levelCap(){return castTicks()>=20?3:5;}
    public int manaCost(){return switch(this){
        case SOUL_CANNON->90;case SMITE->65;case STORM_CALL,SINGULARITY->70;
        case AMETHYST_NOVA->55;case EMPIRE_COMET,FIRE_COMET->45;case GRAVITY_WELL->55;
        case MIASMA_FIELD->50;default->Math.max(12,Math.min(65,8+(int)(damage*2)+cooldown/10));
    };}
    public String bookId(){return switch(this){
        case EMPIRE_SPARK->"arcane_strike";case EMPIRE_COMET->"astral_comet";case EMPIRE_PULSE->"arcane_repulse";
        default->name().toLowerCase(java.util.Locale.ROOT);
    };}
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
            case CANNON->"Charge for two seconds, then launch a massive echo blast. Switch away to cancel charging.";
            case SONIC->"Charge for 0.6 seconds, then fire a piercing sonic beam.";
            case SIPHON->"Drain health damage into healing, up to one heart.";
            case VOLLEY->"Fire three staggered arcane projectiles.";
            case LANCE->"An instant piercing line through visible enemies.";
            case FIELD->"Leave a poisonous cloud for nearly five seconds.";
            case MARK,SUSPEND->"Control an aimed living target.";
        };
    };}
    /** Shared recovery for equivalent healing, ward and restraint abilities across wands. */
    public String cooldownKey(){return switch(kind){
        case SIPHON->"shared_siphon";case WARD->"shared_ward";
        case CAPTURE,CHOKE,SUSPEND->"shared_restraint";case STUN->"shared_stun";
        default->name();
    };}
    public enum Kind { BOLT, TARGET, WAVE, CONFUSE, STUN, CAPTURE, COMET, PULSE, LAUNCH, LEAP, LIGHTNING, SMITE, TWISTER, CHANNEL, BURST, WARD, SEEKER, CHAIN, STORM, WELL, PUSH, PULL, CHOKE, AURA, SONIC, SIPHON, MARK, SUSPEND, VOLLEY, LANCE, FIELD, CANNON }
    public enum Theme {
        MAGIC(0xB349F5), BLOOD(0xA10F28), POISON(0x68DB39), FIRE(0xFF821A), THUNDER(0x7BDFFF), DARK(0x632DB5), GRAVITY(0xCF58E8), SOUL(0x24D7CB);
        public final int color;
        Theme(int color) {this.color=color;}
    }
}
