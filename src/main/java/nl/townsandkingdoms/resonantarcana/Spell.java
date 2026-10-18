package nl.townsandkingdoms.resonantarcana;

/** Stable saved identifiers backed by one validated, shared spell catalogue. */
public enum Spell {
    SPARK,
    ARCANE_STRIKE,
    BLOOD_SPARK,
    POISON_SPARK,
    BLOOD_WAVE,
    POISON_WAVE,
    FLAME_WAVE,
    CONFUSE,
    STUN,
    CAPTURE,
    ASTRAL_COMET,
    FIRE_COMET,
    ARCANE_REPULSE,
    FIREBALL,
    IGNITE,
    LAUNCH,
    LEAP,
    LIGHTNING,
    SMITE,
    FIRE_TWISTER,
    FLAME_THROWER,
    FLAME_SHOCK_WAVE,
    BLOOD_EXPLODE,
    BLOOD_STUN,
    BLOOD_BLOCK,
    SHADOW_BOLT,
    SOUL_SEEKER,
    DARK_WAVE,
    NIGHTFALL,
    SHADOW_CAGE,
    SOUL_AURA,
    THUNDER_BOLT,
    CHAIN_LIGHTNING,
    THUNDER_WAVE,
    STORM_CALL,
    THUNDER_PULSE,
    STATIC_SHOCK,
    GRAVITY_BOLT,
    GRAVITY_WELL,
    FORCE_PUSH,
    FORCE_PULL,
    GRAVITY_CHOKE,
    SINGULARITY,
    ECHO_BOLT,
    SCULK_WAVE,
    SONIC_SCREAM,
    SOUL_SIPHON,
    SOUL_PRISON,
    SHRIEKING_PULSE,
    AMETHYST_NOVA,
    ECHO_WARD,
    ARCANE_VOLLEY,
    SANGUINE_DRAIN,
    BLOOD_LANCE,
    EMBER_VEIL,
    VENOM_BOLT,
    MIASMA_FIELD,
    WITHERING_HEX,
    SOUL_CANNON,
    SOLAR_CATACLYSM,
    TEMPEST_SPEAR,
    EVENT_HORIZON,
    DOOM,
    SANCTUARY,
    GLACIAL_RUPTURE,
    EVOKERS_EDICT,
    ARCANE_STEP,
    BLOOD_NEEDLES,
    BLOOD_REAP,
    CRIMSON_TETHER,
    SANGUINE_PACT,
    CRIMSON_HARVEST,
    CINDER_STEP,
    PHOENIX_RUSH,
    DRAGON_BREATH,
    UMBRAL_STEP,
    DUSK_CLEAVE,
    DREAD_EFFIGY,
    VEIL_OF_NIGHT,
    NIGHT_BLOOM,
    STORM_STEP,
    THUNDERCLAP,
    ARC_RICOCHET,
    RIFT_BLINK,
    ORBITAL_BLADES,
    PRISMATIC_RUIN,
    GRAVITY_STAMP,
    SPATIAL_SHEAR,
    WISP_LANTERN,
    RESONANT_GUARD,
    MEMORY_FRACTURE,
    THORN_LASH,
    VIPER_FANGS,
    BRAMBLE_RING,
    TOXIC_MOLT,
    SPORE_TOTEM,
    CORROSIVE_RAIN,
    GORGON_GAZE,
    LUMINOUS_DART,
    MENDING_LIGHT,
    BLESSING_OF_VIGOR,
    CONSECRATION,
    RADIANT_SWEEP,
    PILGRIM_STEP,
    ABSOLUTION,
    DAWN_WARD,
    JUDGEMENT_RAY,
    SERAPHIC_DESCENT,
    RENEWAL_HYMN,
    FROST_NEEDLE,
    ICE_SERPENT,
    RIME_NOVA,
    CRYO_LANCE,
    FROZEN_AEGIS,
    GLACIER_STEP,
    SHATTER,
    HAILSTORM,
    GLACIAL_PRISON,
    CRYSTAL_BARRAGE,
    ABSOLUTE_ZERO,
    WARSCREAM, SOULBLAST, ARCANE_EDGE, BULWARK_CHANT, BATTLE_TEMPO, CINDER_CLEAVE, RIME_THRUST, BLOOD_REND, KRAKENS_GRASP, ICE_SPIKE_ERUPTION, HELVARS_RIFT, KRAKENS_WRATH, GLACIAL_COUNTER, RIFT_DRAW, CRIMSON_REVERSAL, THUNDERBRAND, LAST_STAND, ASHEN_REBIRTH, PHANTOM_FORMATION, FUNERAL_BELL, SPELLBREAKER, KRAKENS_UNDERTOW, YETIS_GLACIAL_WALL, HELVARS_FRACTURE, SANDWORMS_ERUPTION, SANDWORMS_QUICKSAND, DRAGONS_GUARD, DRAGONS_PYRE, HELVARS_SWORDFALL, ASTRAL_APOCALYPSE, CRIMSON_ECLIPSE, WORLD_PYRE, VOID_CORONATION, HEAVENS_THUNDER, RIFT_COLLISION, SOUL_REQUIEM, WORLDROOT_CATASTROPHE, FINAL_JUDGEMENT, ETERNAL_WINTER, EXPLOSION, SERAPH_ASCENDANT, ARCANE_DEVASTATOR, I_AM_ATOMIC, DOOM_BEAM, VOID_EXILE, VOID_DOMAIN;

    public final String title;
    public final Kind kind;
    public final Theme theme;
    public final float damage;
    public final double radius, range;
    public final int cooldown;
    final SpellCatalogue.Definition definition;

    Spell() {
        definition=SpellCatalogue.definition(name());
        title=definition.title();kind=Kind.valueOf(definition.kind());theme=Theme.valueOf(definition.theme());
        damage=definition.damage();radius=definition.radius();range=definition.range();cooldown=definition.cooldown();
    }
    public boolean available(){return !BossesRiseCompat.optional(this)||BossesRiseCompat.available();}
    public static Spell[] enabledValues(){return java.util.Arrays.stream(values()).filter(Spell::available).toArray(Spell[]::new);}
    public int castTicks(){return definition.castTicks();}
    public int manaCost(){return definition.mana();}
    public int manaStep(){return definition.manaStep();}
    public int levelCap(){return definition.maxLevel();}
    public String bookId(){return definition.bookId();}
    public String description(){return definition.description();}
    public SpellCategory category(){return SpellCategory.valueOf(definition.category());}
    public String role(){return definition.role();}
    public int duration(){return definition.duration();}
    public int interval(){return definition.interval();}
    public int pulses(){return definition.pulses();}
    public float healing(){return definition.healing();}
    public float power(int level){return 1+SpellCatalogue.POWER_PER_LEVEL*Math.clamp(level-1,0,levelCap()-1)/100f;}
    public float damageAtLevel(int level){return damage*power(level);}
    public float healingAtLevel(int level){return healing()*power(level);}
    public int manaAtLevel(int level){return manaCost()+manaStep()*Math.clamp(level-1,0,levelCap()-1);}
    public String cooldownKey(){return switch(kind){
        case HEAL,REMEDY,CLEANSE,HYMN->"shared_heal";
        case SIPHON->"shared_siphon";case WARD->"shared_ward";
        case CAPTURE,CHOKE,SUSPEND->"shared_restraint";case STUN->"shared_stun";
        default->name();
    };}
    public enum Kind { BOLT, TARGET, WAVE, CONFUSE, STUN, CAPTURE, COMET, PULSE, LAUNCH, LEAP, LIGHTNING, SMITE, TWISTER, CHANNEL, BURST, WARD, SEEKER, CHAIN, STORM, WELL, PUSH, PULL, CHOKE, AURA, SONIC, SIPHON, MARK, SUSPEND, VOLLEY, LANCE, FIELD, CANNON, METEOR, RAIL, COLLAPSE, DOOM, HEAL, FROSTFRONT, FANGS, BLINK, FAN, SWEEP, TETHER, BUFF, EXECUTE, DASH, CONE_CHANNEL, TOTEM, TRAP, RICOCHET, ORBIT, PIERCING_ORB, PILLAR, CLEANSE, RAIN, GAZE, REMEDY, HYMN, SERPENT, DOMAIN, WARCRY, SOULBLAST, MARTIAL_BUFF, BLADE_ARC, BLADE_THRUST, BOSS_TENTACLE, BOSS_ICE, BOSS_RIFT, BOSS_WRATH, BOSS_SAND, BOSS_DRAGON, BOSS_SWORD, COUNTER, DRAW, REVERSAL, BRAND, BANNER, REBIRTH, FORMATION, BELL, BREAKER, CATACLYSM, ASCENSION, DEVASTATOR, EXILE, VOID_DOMAIN }
    public enum Theme {
        MAGIC(0xB349F5), BLOOD(0xA10F28), POISON(0x68DB39), FIRE(0xFF821A), THUNDER(0x7BDFFF), DARK(0x632DB5), GRAVITY(0xCF58E8), SOUL(0x24D7CB), HOLY(0xFFE5A1), ICE(0xA6E7FF);
        public final int color;
        Theme(int color){this.color=color;}
    }
}
