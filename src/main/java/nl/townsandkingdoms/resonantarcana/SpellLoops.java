package nl.townsandkingdoms.resonantarcana;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

/** Short leases stop only the requesting caster's sound instances, including long native clips. */
final class SpellLoops {
    static boolean supports(Spell s){return s==Spell.DOOM||s==Spell.TREMORZILLA_BREATH||GodSpells.handles(s);}
    static void sync(ServerPlayer caster,Spell s,boolean play,boolean release,int variant){
        if(!supports(s))return;
        var cue=new WandNetwork.Loop(caster.getId(),s.ordinal(),play,release,Math.clamp(variant,0,2));
        for(var listener:caster.serverLevel().players())if(!play||listener==caster||listener.distanceToSqr(caster)<96*96)PacketDistributor.sendToPlayer(listener,cue);
    }
    static void stop(ServerPlayer caster,Spell s,boolean release){sync(caster,s,false,release,0);}
    private SpellLoops(){}
}
