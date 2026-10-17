package nl.townsandkingdoms.resonantarcana;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

/** Bounded visual messages; never used for collision, damage or target selection. */
final class SpellVisuals {
    enum Stage { CAST, FLIGHT, AREA, LINK, STRIKE, HIT, IMPACT, CHARGE, TRAIL_BURST }
    enum Shape { CAST, ORB, COMET, WAVE, IMPACT, NOVA, PULSE, FIELD, VORTEX, WARD, CAGE, CURSE, BEAM, DRAIN, LIGHTNING, FLAME, CHARGE, CANNON_CHARGE, CANNON_HEAD, CANNON_BURST, CANNON_IMPACT, PYRE_CHARGE, PYRE_FALL, PYRE_IMPACT, TEMPEST_CHARGE, TEMPEST_BEAM, TEMPEST_IMPACT, HORIZON_CHARGE, HORIZON_WELL, HORIZON_IMPACT, SCREAM_FRONT, SCREAM_IMPACT, DOOM_CHARGE, DOOM_WELL, DOOM_IMPACT, SANCTUARY_CHARGE, SANCTUARY_FIELD, SANCTUARY_BLOOM, GLACIER_CHARGE, GLACIER_FRONT, GLACIER_IMPACT, FANG_CHARGE, FANG_ROW, FANG_IMPACT, SLASH, ORBIT, TOTEM, RAIN, TRAP, SERPENT, BLESSING, DOMAIN, FUSION_CHARGE, FUSION_ORB, FUSION_IMPACT }
    private static int details, accents;
    private SpellVisuals() {}
    static void tick() { details=0; accents=0; SpellParticles.tick(); }

    static Shape shape(Spell spell, Stage stage) {
        if(spell.kind==Spell.Kind.BOSS_TENTACLE&&stage==Stage.AREA)return Shape.FIELD;
        if(spell.kind==Spell.Kind.BOSS_ICE&&stage==Stage.AREA)return Shape.NOVA;
        if(spell.kind==Spell.Kind.WARCRY&&stage==Stage.AREA)return Shape.PULSE;
        if(spell.kind==Spell.Kind.MARTIAL_BUFF&&stage==Stage.AREA)return Shape.WARD;
        if(spell.kind==Spell.Kind.BLADE_ARC&&stage==Stage.AREA)return Shape.SLASH;
        if(spell==Spell.SOULBLAST&&stage==Stage.FLIGHT)return Shape.SCREAM_FRONT;
        if(spell==Spell.RIFT_DRAW||spell==Spell.CRIMSON_REVERSAL)return stage==Stage.AREA||stage==Stage.IMPACT?Shape.SLASH:Shape.BEAM;
        if(spell==Spell.GLACIAL_COUNTER)return stage==Stage.AREA?Shape.WARD:stage==Stage.IMPACT?Shape.GLACIER_IMPACT:Shape.CHARGE;
        if(spell==Spell.THUNDERBRAND)return stage==Stage.AREA||stage==Stage.LINK?Shape.LIGHTNING:Shape.CHARGE;
        if(spell==Spell.LAST_STAND)return stage==Stage.AREA?Shape.TOTEM:stage==Stage.IMPACT?Shape.SANCTUARY_BLOOM:Shape.CHARGE;
        if(spell==Spell.ASHEN_REBIRTH)return stage==Stage.AREA?Shape.FLAME:stage==Stage.IMPACT?Shape.PYRE_IMPACT:Shape.COMET;
        if(spell==Spell.PHANTOM_FORMATION)return stage==Stage.AREA?Shape.CHARGE:stage==Stage.LINK?Shape.BEAM:Shape.ORB;
        if(spell==Spell.FUNERAL_BELL)return stage==Stage.AREA?Shape.PULSE:Shape.CHARGE;
        if(spell==Spell.SPELLBREAKER)return stage==Stage.AREA?Shape.WAVE:Shape.BEAM;
        if(spell==Spell.SOUL_CANNON)return switch(stage){
            case CAST,CHARGE -> Shape.CANNON_CHARGE;
            case FLIGHT,LINK -> Shape.CANNON_HEAD;
            case TRAIL_BURST -> Shape.CANNON_BURST;
            case IMPACT -> Shape.CANNON_IMPACT;
            default -> Shape.CHARGE;
        };
        if(spell==Spell.SOLAR_CATACLYSM)return switch(stage){
            case CAST,CHARGE -> Shape.PYRE_CHARGE;case FLIGHT -> Shape.PYRE_FALL;
            case HIT,IMPACT -> Shape.PYRE_IMPACT;default -> Shape.FIELD;
        };
        if(spell==Spell.TEMPEST_SPEAR)return switch(stage){
            case CAST,CHARGE -> Shape.TEMPEST_CHARGE;case LINK,STRIKE -> Shape.TEMPEST_BEAM;
            case HIT,IMPACT -> Shape.TEMPEST_IMPACT;default -> Shape.LIGHTNING;
        };
        if(spell==Spell.EVENT_HORIZON)return switch(stage){
            case CAST,CHARGE -> Shape.HORIZON_CHARGE;case AREA -> Shape.HORIZON_WELL;
            case HIT,IMPACT -> Shape.HORIZON_IMPACT;default -> Shape.DRAIN;
        };
        if(spell==Spell.SONIC_SCREAM)return switch(stage){
            case FLIGHT -> Shape.SCREAM_FRONT;case IMPACT -> Shape.SCREAM_IMPACT;default -> Shape.CHARGE;
        };
        if(spell==Spell.DOOM)return switch(stage){
            case CAST,CHARGE -> Shape.DOOM_CHARGE;case AREA -> Shape.DOOM_WELL;default -> Shape.DOOM_IMPACT;
        };
        if(spell==Spell.SANCTUARY)return switch(stage){
            case CAST,CHARGE -> Shape.SANCTUARY_CHARGE;case AREA -> Shape.SANCTUARY_FIELD;default -> Shape.SANCTUARY_BLOOM;
        };
        if(spell==Spell.GLACIAL_RUPTURE)return switch(stage){
            case CAST,CHARGE -> Shape.GLACIER_CHARGE;case FLIGHT -> Shape.GLACIER_FRONT;default -> Shape.GLACIER_IMPACT;
        };
        if(spell==Spell.EVOKERS_EDICT)return switch(stage){
            case CAST,CHARGE -> Shape.FANG_CHARGE;case AREA -> Shape.FANG_ROW;default -> Shape.FANG_IMPACT;
        };
        if(spell==Spell.PRISMATIC_RUIN)return switch(stage){
            case CAST,CHARGE -> Shape.FUSION_CHARGE;case FLIGHT -> Shape.FUSION_ORB;
            default -> Shape.FUSION_IMPACT;
        };
        return switch(stage) {
            case CAST -> Shape.CAST;
            case HIT,TRAIL_BURST -> Shape.CHARGE;
            case IMPACT -> switch(spell.kind) {
                case BURST,SMITE,COMET -> Shape.NOVA;
                default -> Shape.IMPACT;
            };
            case CHARGE -> Shape.CHARGE;
            case STRIKE -> Shape.LIGHTNING;
            case FLIGHT -> switch(spell.kind) {
                case COMET -> Shape.COMET;
                case WAVE -> Shape.WAVE;
                case SERPENT -> Shape.SERPENT;
                default -> Shape.ORB;
            };
            case LINK -> switch(spell.kind) {
                case SIPHON,PULL,TETHER -> Shape.DRAIN;
                case CHANNEL,CONE_CHANNEL -> Shape.FLAME;
                case CHAIN -> Shape.LIGHTNING;
                default -> Shape.BEAM;
            };
            case AREA -> switch(spell.kind) {
                case SWEEP -> Shape.SLASH;
                case ORBIT -> Shape.ORBIT;
                case TOTEM -> Shape.TOTEM;
                case RAIN -> Shape.RAIN;
                case TRAP -> Shape.TRAP;
                case BUFF,CLEANSE,HYMN -> Shape.BLESSING;
                case DOMAIN -> Shape.DOMAIN;
                case PULSE -> Shape.PULSE;
                case TWISTER,WELL -> Shape.VORTEX;
                case WARD -> Shape.WARD;
                case CAPTURE,CHOKE,SUSPEND -> Shape.CAGE;
                case MARK,CONFUSE,STUN -> Shape.CURSE;
                default -> Shape.FIELD;
            };
        };
    }

    static void send(ServerLevel level, Spell spell, Stage stage, Vec3 pos, Vec3 end, double size) {
        boolean detail=stage==Stage.FLIGHT||stage==Stage.AREA||stage==Stage.CHARGE;
        if(detail ? details++>=ArcanaConfig.DETAIL_BUDGET.get() : accents++>=ArcanaConfig.ACCENT_BUDGET.get())return;
        if(!BossesRiseCompat.optional(spell))SpellParticles.emit(level,spell,stage,pos,end,size);
        var message=new WandNetwork.Visual(spell.ordinal(),stage.ordinal(),pos.x,pos.y,pos.z,end.x,end.y,end.z,(float)Math.clamp(size,0.1,16));
        if(IntegrationChecks.enabled())IntegrationChecks.visual(message);
        // Long beams are sent around their midpoint so both ends can be seen by observers.
        Vec3 center=pos.lerp(end,0.5);
        PacketDistributor.sendToPlayersNear(level,null,center.x,center.y,center.z,96,message);
    }
}
