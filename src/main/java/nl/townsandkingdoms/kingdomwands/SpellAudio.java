package nl.townsandkingdoms.kingdomwands;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

/** Layered spell audio. The caster can hear their own impacts independently of world attenuation. */
final class SpellAudio {
    private SpellAudio() {}
    static String castId(Spell.Theme theme){return switch(theme){
        case BLOOD->"entity.player.attack.sweep";case POISON->"entity.witch.throw";
        case FIRE->"entity.blaze.shoot";case THUNDER->"entity.breeze.shoot";
        case DARK->"entity.wither.shoot";case SOUL->"entity.warden.attack_impact";
        case GRAVITY->"block.respawn_anchor.charge";case MAGIC->"entity.evoker.prepare_attack";
    };}
    static String castAccentId(Spell.Theme theme){return switch(theme){
        case BLOOD->"entity.player.attack.strong";case POISON->"block.brewing_stand.brew";
        case FIRE->"item.firecharge.use";case THUNDER->"entity.lightning_bolt.impact";
        case DARK->"entity.illusioner.cast_spell";case SOUL->"block.amethyst_block.resonate";
        case GRAVITY->"entity.breeze.wind_burst";case MAGIC->"block.amethyst_block.chime";
    };}
    static String impactId(Spell.Theme theme){return switch(theme){
        case BLOOD->"entity.player.attack.strong";case POISON->"entity.slime.squish";
        case FIRE->"entity.generic.explode";case THUNDER->"entity.lightning_bolt.impact";
        case DARK->"entity.player.attack.crit";case SOUL->"entity.warden.attack_impact";
        case GRAVITY->"entity.breeze.wind_burst";case MAGIC->"entity.player.attack.crit";
    };}
    static String impactAccentId(Spell.Theme theme){return switch(theme){
        case BLOOD,POISON->"block.slime_block.break";case FIRE->"item.firecharge.use";
        case THUNDER->"entity.firework_rocket.blast";case DARK->"entity.wither.hurt";
        case SOUL,MAGIC->"block.amethyst_block.break";case GRAVITY->"block.respawn_anchor.deplete";
    };}
    static String sustainId(Spell.Theme theme){return switch(theme){
        case BLOOD->"block.bubble_column.whirlpool_inside";case POISON->"block.bubble_column.upwards_inside";case FIRE->"block.fire.ambient";
        case THUNDER->"block.conduit.ambient.short";case DARK->"block.portal.ambient";
        case SOUL->"entity.warden.heartbeat";case GRAVITY->"block.beacon.ambient";case MAGIC->"block.amethyst_block.chime";
    };}
    static SoundEvent event(String id){return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace(id));}
    static float volume(float base){return (float)(base*KingdomWands.SOUND_VOLUME.get());}
    static String spellCastId(Spell spell){return switch(spell){
        case SONIC_BOOM->"entity.warden.sonic_charge";
        case SHRIEKING_PULSE->"entity.warden.roar";
        case SOUL_PRISON->"block.sculk_shrieker.shriek";
        default->castId(spell.theme);
    };}
    static void cast(ServerPlayer player,Spell spell){
        cue(player,player.position(),spellCastId(spell),1.3f,0.85f,true);
        cue(player,player.position(),castAccentId(spell.theme),0.8f,0.8f,true);
    }
    static void impact(ServerPlayer player,Vec3 pos,Spell spell){
        float weight=switch(spell.kind){case COMET,BURST,PULSE,SMITE,SONIC,MARK->1.15f;default->1.0f;};
        float gain=(float)(weight*KingdomWands.IMPACT_VOLUME.get());
        boolean direct=KingdomWands.CASTER_IMPACTS.get();
        String primary=spell.kind==Spell.Kind.SONIC?"entity.warden.sonic_boom":impactId(spell.theme);
        cue(player,pos,primary,1.6f*gain,spell.theme==Spell.Theme.BLOOD?0.7f:0.85f,direct);
        cue(player,pos,impactAccentId(spell.theme),1.05f*gain,0.8f,direct);
    }
    private static void cue(ServerPlayer player,Vec3 pos,String id,float base,float pitch,boolean direct){
        float gain=volume(base);if(gain<=0)return;
        SoundEvent sound=event(id);
        // Excluding the caster from world playback prevents hearing both copies at close range.
        if(direct)player.playNotifySound(sound,SoundSource.PLAYERS,gain,pitch);
        player.serverLevel().playSound(direct?player:null,pos.x,pos.y,pos.z,sound,SoundSource.PLAYERS,gain,pitch);
    }
    static void world(ServerLevel level,Vec3 pos,String id,float base,float pitch){
        float gain=volume(base);if(gain>0)level.playSound(null,pos.x,pos.y,pos.z,event(id),SoundSource.PLAYERS,gain,pitch);
    }
}
