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
    static String castId(Spell.Theme theme){return irons(switch(theme){
        case MAGIC->"magic_arrow_release";case BLOOD->"cast.generic.blood";case POISON->"spell.acid_orb.cast";
        case FIRE->"cast.fireball";case THUNDER->"lightning_lance_cast";case DARK->"spell.shadow_slash.cast";
        case GRAVITY->"spell.gust.cast";case SOUL->"spell.eldritch_blast.cast";
    });}
    private static String irons(String path){return "irons_spellbooks:"+path;}
    static String chargeId(Spell spell){return switch(spell){
        case SOUL_CANNON,SONIC_BOOM->"entity.warden.sonic_charge";
        case SOLAR_CATACLYSM->irons("spell.fire_bomb.charge");
        case TEMPEST_SPEAR->irons("spell.thunderstorm.prepare");case EVENT_HORIZON->irons("spell.black_hole.charge");
        case SMITE->irons("spell.divine_smite.windup");case STORM_CALL->irons("spell.thunderstorm.prepare");
        case SINGULARITY->irons("spell.black_hole.charge");case GRAVITY_WELL->irons("spell.gravity_fissure.charge");
        case FIRE_COMET->irons("spell.fire_bomb.charge");case EMPIRE_COMET,ARCANE_VOLLEY->irons("spell.summon_swords.charge");
        case SOUL_PRISON,CAPTURE,GRAVITY_CHOKE->irons("spell.arcane_shackle.charge");
        default->irons(switch(spell.theme){
            case MAGIC->"magic_arrow_charge";case BLOOD->"heartstop_cast";case POISON->"spell.acid_orb.charge";
            case FIRE->"spell.heat_surge.prepare";case THUNDER->"spell.shockwave.prepare";
            case DARK->"cast.raise_dead.start";case GRAVITY->"spell.gust.charge";case SOUL->"cast.generic.eldritch_prepare";
        });
    };}
    static String castAccentId(Spell.Theme theme){return switch(theme){
        case BLOOD->"entity.player.attack.strong";case POISON->"block.brewing_stand.brew";
        case FIRE->"item.firecharge.use";case THUNDER->"entity.lightning_bolt.impact";
        case DARK->"entity.illusioner.cast_spell";case SOUL->"block.amethyst_block.resonate";
        case GRAVITY->"entity.breeze.wind_burst";case MAGIC->"block.amethyst_block.chime";
    };}
    static String impactId(Spell.Theme theme){return irons(switch(theme){
        case MAGIC->"entity.generic.arcane_impact";case BLOOD->"entity.blood_needle.impact";
        case POISON->"entity.acid_orb.impact";case FIRE->"entity.generic.fire_impact";
        case THUNDER->"entity.lightning_strike.strike";case DARK,GRAVITY->"force_impact";
        case SOUL->"entity.echoing_strike.echoing_strike";
    });}
    static String impactAccentId(Spell.Theme theme){return switch(theme){
        case BLOOD,POISON->"block.slime_block.break";case FIRE->"item.firecharge.use";
        case THUNDER->"entity.firework_rocket.blast";case DARK->"entity.evoker_fangs.attack";
        case SOUL,MAGIC->"block.amethyst_block.break";case GRAVITY->"block.respawn_anchor.deplete";
    };}
    static String sustainId(Spell.Theme theme){return switch(theme){
        case BLOOD->"block.bubble_column.whirlpool_inside";case POISON->"block.bubble_column.upwards_inside";case FIRE->"block.fire.ambient";
        case THUNDER->"block.conduit.ambient.short";case DARK->"block.portal.ambient";
        case SOUL->"entity.warden.heartbeat";case GRAVITY->"block.beacon.ambient";case MAGIC->"block.amethyst_block.chime";
    };}
    static SoundEvent event(String id){return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(id));}
    static float volume(float base){return (float)(base*KingdomWands.SOUND_VOLUME.get());}
    static String spellCastId(Spell spell){return switch(spell){
        case SOUL_CANNON,SONIC_BOOM->"entity.warden.sonic_charge";
        case SOLAR_CATACLYSM->irons("spell.fire_bomb.cast");
        case TEMPEST_SPEAR->irons("lightning_lance_cast");case EVENT_HORIZON->irons("spell.black_hole.cast");
        case SMITE->irons("spell.divine_smite.cast");case SINGULARITY->irons("spell.black_hole.cast");
        case FIRE_COMET->irons("spell.fire_bomb.cast");case EMPIRE_COMET,ARCANE_VOLLEY->irons("spell.summon_swords.cast");
        case FLAME_SHOCK_WAVE->irons("spell.flaming_strike.cast");case THUNDER_PULSE->irons("spell.shockwave.cast");
        case SHRIEKING_PULSE->"entity.warden.roar";
        case SOUL_PRISON->"block.sculk_shrieker.shriek";
        default->castId(spell.theme);
    };}
    private static float castPitch(Spell spell){return switch(spell.kind){
        case COMET,CANNON,BURST,SMITE,WELL,METEOR,RAIL,COLLAPSE->0.7f;
        case BOLT,VOLLEY,WAVE,LANCE->1.15f;
        case SIPHON,CAPTURE,CHOKE,SUSPEND->0.85f;
        default->1.0f;
    };}
    static void charge(ServerPlayer player,Spell spell){
        if(spell==Spell.SOUL_CANNON||spell==Spell.SONIC_BOOM){cast(player,spell);return;}
        cue(player,player.position(),chargeId(spell),1.15f,1.0f,true);
        cue(player,player.position(),castAccentId(spell.theme),0.45f,0.85f,true);
    }
    static void cast(ServerPlayer player,Spell spell){
        cue(player,player.position(),spellCastId(spell),1.3f,castPitch(spell),true);
        cue(player,player.position(),castAccentId(spell.theme),0.85f,castPitch(spell)+0.12f,true);
    }
    static void impact(ServerPlayer player,Vec3 pos,Spell spell){
        float weight=switch(spell.kind){case COMET,BURST,PULSE,SMITE,SONIC,MARK,METEOR,RAIL,COLLAPSE->1.15f;default->1.0f;};
        float gain=(float)(weight*KingdomWands.IMPACT_VOLUME.get());
        boolean direct=KingdomWands.CASTER_IMPACTS.get();
        String primary=spell.kind==Spell.Kind.SONIC?"entity.warden.sonic_boom":impactId(spell.theme);
        cue(player,pos,primary,1.6f*gain,spell.theme==Spell.Theme.BLOOD?0.7f:0.85f,direct);
        cue(player,pos,(spell.kind==Spell.Kind.METEOR||spell.kind==Spell.Kind.COLLAPSE)?"entity.generic.explode":impactAccentId(spell.theme),1.05f*gain,0.8f,direct);
    }
    static void cannonCharge(ServerPlayer player,Vec3 pos,float progress){
        cue(player,pos,"block.respawn_anchor.charge",0.8f+progress*0.6f,0.55f+progress*0.9f,true);
        cue(player,pos,"entity.warden.heartbeat",1.0f,0.7f+progress*0.5f,true);
    }
    static void cannon(ServerPlayer player,Vec3 pos,String phase){
        float gain=KingdomWands.IMPACT_VOLUME.get().floatValue();
        boolean direct=KingdomWands.CASTER_IMPACTS.get();
        switch(phase){
            case "release" -> {
                cue(player,pos,"entity.warden.sonic_boom",2.4f*gain,0.65f,true);
                cue(player,pos,"entity.warden.roar",1.5f*gain,0.8f,true);
            }
            case "burst" -> cue(player,pos,"entity.firework_rocket.blast",0.85f*gain,0.75f,direct);
            case "impact" -> {
                cue(player,pos,"entity.generic.explode",2.6f*gain,0.6f,direct);
                cue(player,pos,"entity.warden.sonic_boom",2.2f*gain,0.5f,direct);
                cue(player,pos,"block.amethyst_block.break",1.6f*gain,0.65f,direct);
            }
            case "tail" -> {
                cue(player,pos,"entity.firework_rocket.twinkle_far",1.2f*gain,0.6f,direct);
                cue(player,pos,"block.amethyst_block.resonate",1.4f*gain,0.6f,direct);
            }
            default -> throw new IllegalArgumentException(phase);
        }
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
