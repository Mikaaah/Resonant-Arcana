package nl.townsandkingdoms.resonantarcana;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

/** The server owns damage and clipping; clients receive short caster-scoped visual leases. */
final class BeamRitual {
    static Vec3 body(net.minecraft.world.entity.Entity entity,float partial){return entity.getPosition(partial).add(0,entity.getBbHeight()*.55,0);}
    static final double FORWARD=.18;
    static Vec3 origin(ServerPlayer p){Vec3 eye=body(p,1);return SpellEngine.clipLoaded(p.serverLevel(),eye,p.getLookAngle().normalize(),FORWARD,p);}
    static void sync(ServerPlayer p,int phase,double progress,double length){sync(p,Spell.DOOM_BEAM,phase,progress,length);}
    static void sync(ServerPlayer p,Spell spell,int phase,double progress,double length){
        var cue=new WandNetwork.BeamFocus(p.getId(),spell.ordinal(),phase,(float)Math.clamp(progress,0,1),(float)Math.clamp(length,0,64));
        PacketDistributor.sendToPlayersNear(p.serverLevel(),null,p.getX(),p.getY(),p.getZ(),96,cue);
    }
    static void stop(ServerPlayer p){sync(p,0,0,0);}
}
