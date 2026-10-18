package nl.townsandkingdoms.resonantarcana;

import java.util.Map;
import java.util.EnumMap;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Original-pitch cinematic recordings; native casting still owns mana, interruption and cooldown. */
final class RecordedSpells {
    static final DeferredRegister<SoundEvent> SOUNDS=DeferredRegister.create(Registries.SOUND_EVENT,ResonantArcana.ID);
    static final Map<Spell,String> PREFIX=new EnumMap<>(Spell.class);
    private static final Map<Spell,Supplier<SoundEvent>> CHARGE=new EnumMap<>(Spell.class),RELEASE=new EnumMap<>(Spell.class);
    static {PREFIX.put(Spell.EXPLOSION,"explosion");PREFIX.put(Spell.I_AM_ATOMIC,"atomic");PREFIX.put(Spell.ARCANE_DEVASTATOR,"cosmic");PREFIX.put(Spell.DOOM_BEAM,"doom_laser");PREFIX.forEach((spell,prefix)->{
        CHARGE.put(spell,register(spell==Spell.EXPLOSION?"explosion_chant":prefix+"_charge"));RELEASE.put(spell,register(spell==Spell.EXPLOSION?"explosion_blast":prefix+"_release"));
    });}
    private static Supplier<SoundEvent> register(String id){return SOUNDS.register(id,()->SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,id)));}
    static boolean handles(Spell spell){return PREFIX.containsKey(spell);}
    static SoundEvent chargeEvent(Spell spell){return CHARGE.get(spell).get();}
    static SoundEvent releaseEvent(Spell spell){return RELEASE.get(spell).get();}
    static void start(ServerPlayer caster,Spell spell){
        var cue=new WandNetwork.Chant(caster.getId(),spell.ordinal(),true,1,(float)(1.15*ArcanaConfig.SOUND_VOLUME.get()));
        for(var listener:caster.serverLevel().players())if(listener==caster||listener.distanceToSqr(caster)<64*64)PacketDistributor.sendToPlayer(listener,cue);
    }
    static void stop(ServerPlayer caster,Spell spell){
        var cue=new WandNetwork.Chant(caster.getId(),spell.ordinal(),false,1,0);
        for(var listener:caster.serverLevel().players())PacketDistributor.sendToPlayer(listener,cue);
    }
    static void release(SpellEngine.Cast c){
        if(!handles(c.spell))return;stop(c.caster,c.spell);
        float gain=(float)(1.8*ArcanaConfig.SOUND_VOLUME.get()*ArcanaConfig.IMPACT_VOLUME.get());if(gain<=0)return;
        boolean direct=ArcanaConfig.CASTER_IMPACTS.get();
        if(direct)c.caster.playNotifySound(releaseEvent(c.spell),SoundSource.PLAYERS,gain,1);
        c.level.playSound(direct?c.caster:null,c.pos.x,c.pos.y,c.pos.z,releaseEvent(c.spell),SoundSource.PLAYERS,gain,1);
    }
}
