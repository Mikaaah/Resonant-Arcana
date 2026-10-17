package nl.townsandkingdoms.resonantarcana;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.ICastData;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredRegister;

/** One immutable aim snapshot per native cast; cancellation clears it with Iron's cast data. */
final class ExplosionRitual {
    static final int CHANT_TICKS=419; // 20.95 seconds in the supplied source recording.
    static final ResourceLocation CHANT_ID=ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"explosion_chant");
    static final DeferredRegister<SoundEvent> SOUNDS=DeferredRegister.create(Registries.SOUND_EVENT,ResonantArcana.ID);
    static final java.util.function.Supplier<SoundEvent> CHANT=SOUNDS.register("explosion_chant",()->SoundEvent.createVariableRangeEvent(CHANT_ID));
    static final java.util.function.Supplier<SoundEvent> BLAST=SOUNDS.register("explosion_blast",()->SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"explosion_blast")));
    record Locked(Vec3 point,Vec3 top,ResourceKey<Level> dimension) implements ICastData {
        @Override public void reset(){} // Scoped to MagicData, never retained in a global player map.
        boolean valid(ServerPlayer p){
            return p.serverLevel().dimension().equals(dimension)&&p.serverLevel().hasChunkAt(net.minecraft.core.BlockPos.containing(point))
                &&p.getEyePosition().distanceToSqr(point)<=64*64
                &&SpellEngine.clipLoaded(p.serverLevel(),p.getEyePosition(),point.subtract(p.getEyePosition()).normalize(),p.getEyePosition().distanceTo(point),p).distanceToSqr(point)<.25;
        }
    }
    static void lock(ServerPlayer p,MagicData data){
        Vec3 eye=p.getEyePosition(),point=SpellEngine.clipLoaded(p.serverLevel(),eye,p.getLookAngle(),Spell.EXPLOSION.range,p);
        var marked=SpellEngine.target(p,eye,point,.5);
        if(marked!=null)point=marked.getBoundingBox().getCenter();
        // Keep the mark just outside the hit surface, so its blast has line of sight.
        point=point.subtract(p.getLookAngle().scale(.08));
        data.setAdditionalCastData(new Locked(point,beamTop(p,point),p.serverLevel().dimension()));
        var cue=new WandNetwork.Chant(p.getId(),true,chantPitch(data.getCastDuration()),(float)(1.5*ArcanaConfig.SOUND_VOLUME.get()));
        for(var listener:p.serverLevel().players())if(listener==p||listener.distanceToSqr(p)<64*64)PacketDistributor.sendToPlayer(listener,cue);
    }
    static Locked locked(ServerPlayer p){return MagicData.getPlayerMagicData(p).getAdditionalCastData() instanceof Locked l?l:null;}
    static Vec3 beamTop(ServerPlayer p,Vec3 point){
        Vec3 side=point.subtract(p.position()).multiply(1,0,1).normalize();if(side.lengthSqr()<.01)side=new Vec3(1,0,0);
        // A diagonal beacon, not a vertical fire column. Clip against loaded terrain/ceilings.
        Vec3 offset=side.scale(10).add(0,24,0);
        return SpellEngine.clipLoaded(p.serverLevel(),point,offset.normalize(),offset.length(),p);
    }
    static void charge(ServerPlayer p,double progress,int elapsed){
        var l=locked(p);if(l==null)return;
        if(elapsed%4==0)SpellVisuals.send(p.serverLevel(),Spell.EXPLOSION,SpellVisuals.Stage.RITUAL_TARGET,l.point(),l.top(),5+2*progress);
    }
    static float chantPitch(int castTicks){return Math.clamp(CHANT_TICKS/(float)Math.max(1,castTicks),.5f,2);}
    static void blast(SpellEngine.Cast c){
        stopChant(c.caster);
        float gain=(float)(2.4*ArcanaConfig.SOUND_VOLUME.get()*ArcanaConfig.IMPACT_VOLUME.get());
        if(gain<=0)return;
        boolean direct=ArcanaConfig.CASTER_IMPACTS.get();
        if(direct)c.caster.playNotifySound(BLAST.get(),SoundSource.PLAYERS,gain,1);
        c.level.playSound(direct?c.caster:null,c.pos.x,c.pos.y,c.pos.z,BLAST.get(),SoundSource.PLAYERS,gain,1);
    }
    static void stopChant(ServerPlayer p){
        var packet=new WandNetwork.Chant(p.getId(),false,1,0);
        // Each listener stops only this caster's instance, including listeners who have moved away.
        for(var listener:p.serverLevel().players())PacketDistributor.sendToPlayer(listener,packet);
    }
}
