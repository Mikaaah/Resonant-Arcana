package nl.townsandkingdoms.kingdomwands;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

/** Bounded visual messages; never used for collision, damage or target selection. */
final class SpellVisuals {
    enum Stage { CAST, FLIGHT, AREA, LINK, STRIKE, HIT, IMPACT, CHARGE, TRAIL_BURST }
    enum Shape { CAST, ORB, COMET, WAVE, IMPACT, NOVA, PULSE, FIELD, VORTEX, WARD, CAGE, CURSE, BEAM, DRAIN, LIGHTNING, FLAME, CHARGE, CANNON_CHARGE, CANNON_HEAD, CANNON_BURST, CANNON_IMPACT }
    private static int details, accents;
    private SpellVisuals() {}
    static void tick() { details=0; accents=0; }

    static Shape shape(Spell spell, Stage stage) {
        if(spell==Spell.SOUL_CANNON)return switch(stage){
            case CAST,CHARGE -> Shape.CANNON_CHARGE;
            case FLIGHT,LINK -> Shape.CANNON_HEAD;
            case TRAIL_BURST -> Shape.CANNON_BURST;
            case IMPACT -> Shape.CANNON_IMPACT;
            default -> Shape.CHARGE;
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
                default -> Shape.ORB;
            };
            case LINK -> switch(spell.kind) {
                case SIPHON,PULL -> Shape.DRAIN;
                case CHANNEL -> Shape.FLAME;
                case CHAIN -> Shape.LIGHTNING;
                default -> Shape.BEAM;
            };
            case AREA -> switch(spell.kind) {
                case PULSE -> Shape.PULSE;
                case TWISTER,WELL -> Shape.VORTEX;
                case WARD -> Shape.WARD;
                case CAPTURE,CHOKE,SUSPEND -> Shape.CAGE;
                case MARK,CONFUSE,STUN -> Shape.CURSE;
                default -> Shape.FIELD;
            };
        };
    }

    static boolean packaged(Shape shape,Spell.Theme theme){
        return !shape.name().startsWith("CANNON_")||theme==Spell.Theme.SOUL;
    }
    static int assetCount(){
        int count=0;for(var shape:Shape.values())for(var theme:Spell.Theme.values())if(packaged(shape,theme))count++;
        return count;
    }
    static void send(ServerLevel level, Spell spell, Stage stage, Vec3 pos, Vec3 end, double size) {
        boolean detail=stage==Stage.FLIGHT||stage==Stage.AREA||stage==Stage.CHARGE;
        if(detail ? details++>=64 : accents++>=32)return;
        var message=new WandNetwork.Visual(spell.ordinal(),stage.ordinal(),pos.x,pos.y,pos.z,end.x,end.y,end.z,(float)Math.clamp(size,0.1,16));
        if(IntegrationChecks.enabled())IntegrationChecks.visual(message);
        // Long beams are sent around their midpoint so both ends can be seen by observers.
        Vec3 center=pos.lerp(end,0.5);
        PacketDistributor.sendToPlayersNear(level,null,center.x,center.y,center.z,96,message);
    }
}
