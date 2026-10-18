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
    static final int CHANT_TICKS=154;
    static final DeferredRegister<SoundEvent> SOUNDS=RecordedSpells.SOUNDS;
    static final java.util.function.Supplier<SoundEvent> CHANT=()->RecordedSpells.chargeEvent(Spell.EXPLOSION);
    static final java.util.function.Supplier<SoundEvent> BLAST=()->RecordedSpells.releaseEvent(Spell.EXPLOSION);
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
        RecordedSpells.start(p,Spell.EXPLOSION);
    }
    static Locked locked(ServerPlayer p){return MagicData.getPlayerMagicData(p).getAdditionalCastData() instanceof Locked l?l:null;}
    static Vec3 beamTop(ServerPlayer p,Vec3 point){
        Vec3 side=point.subtract(p.position()).multiply(1,0,1).normalize();if(side.lengthSqr()<.01)side=new Vec3(1,0,0);
        // The reference uses a vertical ignition column with stacked horizontal seals. Clip against loaded ceilings.
        Vec3 offset=new Vec3(0,24,0);
        return SpellEngine.clipLoaded(p.serverLevel(),point,offset.normalize(),offset.length(),p);
    }
    static void charge(ServerPlayer p,double progress,int elapsed){
        var l=locked(p);if(l==null)return;
        if(elapsed%4==0)SpellVisuals.send(p.serverLevel(),Spell.EXPLOSION,SpellVisuals.Stage.RITUAL_TARGET,l.point(),l.top(),5+2*progress);
    }
    static float chantPitch(int castTicks){return 1;}
    static void blast(SpellEngine.Cast c){RecordedSpells.release(c);}
    static void stopChant(ServerPlayer p){RecordedSpells.stop(p,Spell.EXPLOSION);}
}
