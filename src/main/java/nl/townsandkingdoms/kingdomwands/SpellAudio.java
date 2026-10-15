package nl.townsandkingdoms.kingdomwands;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

/** The caster receives cast audio explicitly; world audio excludes that player to avoid doubles. */
final class SpellAudio {
    private SpellAudio() {}
    static String castId(Spell.Theme theme){return switch(theme){
        case BLOOD->"entity.evoker.cast_spell";case FIRE->"item.firecharge.use";
        case THUNDER->"entity.breeze.shoot";case DARK->"entity.illusioner.cast_spell";
        case GRAVITY->"block.beacon.power_select";default->"block.enchantment_table.use";
    };}
    static String impactId(Spell.Theme theme){return switch(theme){
        case BLOOD->"block.slime_block.break";case FIRE->"entity.generic.explode";
        case THUNDER->"entity.lightning_bolt.impact";case DARK->"entity.wither.hurt";
        case GRAVITY->"block.respawn_anchor.deplete";default->"block.amethyst_block.resonate";
    };}
    static String sustainId(Spell.Theme theme){return switch(theme){
        case BLOOD->"block.bubble_column.whirlpool_inside";case FIRE->"block.fire.ambient";
        case THUNDER->"block.conduit.ambient.short";case DARK->"block.sculk_shrieker.shriek";
        case GRAVITY->"block.beacon.ambient";default->"block.amethyst_block.chime";
    };}
    static SoundEvent event(String id){return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace(id));}
    static float volume(float base){return (float)(base*KingdomWands.SOUND_VOLUME.get());}
    static void cast(ServerPlayer player,Spell.Theme theme){
        SoundEvent sound=event(castId(theme));float v=volume(1.0f);
        if(v<=0)return;
        player.playNotifySound(sound,SoundSource.PLAYERS,v,0.9f);
        Vec3 p=player.position();player.serverLevel().playSound(player,p.x,p.y,p.z,sound,SoundSource.PLAYERS,v,0.9f);
    }
    static void world(ServerLevel level,Vec3 pos,String id,float volume,float pitch){
        float v=volume(volume);if(v>0)level.playSound(null,pos.x,pos.y,pos.z,event(id),SoundSource.PLAYERS,v,pitch);
    }
}
