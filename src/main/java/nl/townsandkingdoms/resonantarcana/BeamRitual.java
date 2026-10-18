package nl.townsandkingdoms.resonantarcana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

/** The server owns damage and clipping; clients receive short caster-scoped visual leases. */
final class BeamRitual {
    static Vec3 origin(ServerPlayer p){Vec3 eye=p.getEyePosition().add(0,-.25,0);return SpellEngine.clipLoaded(p.serverLevel(),eye,p.getLookAngle().normalize(),.9,p);}
    static void sync(ServerPlayer p,int phase,double progress,double length){
        var cue=new WandNetwork.BeamFocus(p.getId(),phase,(float)Math.clamp(progress,0,1),(float)Math.clamp(length,0,64));
        PacketDistributor.sendToPlayersNear(p.serverLevel(),null,p.getX(),p.getY(),p.getZ(),96,cue);
    }
    static void stop(ServerPlayer p){sync(p,0,0,0);}
}
