package nl.townsandkingdoms.resonantarcana;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.sounds.SoundSource;

/** Client-only, caster-scoped chant instances. Stopping one ritual cannot silence another. */
final class RecordedAudio {
    private record Key(int caster,int spell){}
    private record Playing(EntityBoundSoundInstance sound,int expires){}
    private static final Map<Key,Playing> CHANTS=new HashMap<>();
    private static ClientLevel world;
    private static int ticks;
    static void handle(WandNetwork.Chant cue){
        if(!cue.valid())return;
        var mc=Minecraft.getInstance();if(mc.level==null)return;
        stop(new Key(cue.caster(),cue.spell()));if(!cue.start()||cue.gain()<=0)return;
        var caster=mc.level.getEntity(cue.caster());if(caster==null||!caster.isAlive())return;
        var sound=new EntityBoundSoundInstance(RecordedSpells.chargeEvent(Spell.values()[cue.spell()]),SoundSource.PLAYERS,cue.gain(),cue.pitch(),caster,caster.getRandom().nextLong());
        CHANTS.put(new Key(cue.caster(),cue.spell()),new Playing(sound,ticks+(int)Math.ceil(Spell.values()[cue.spell()].castTicks())+40));
        mc.getSoundManager().play(sound);
    }
    static void tick(){
        var mc=Minecraft.getInstance();ticks++;
        if(mc.level!=world){CHANTS.values().forEach(p->mc.getSoundManager().stop(p.sound()));CHANTS.clear();world=mc.level;}
        var it=CHANTS.entrySet().iterator();
        while(it.hasNext()){
            var p=it.next();var caster=mc.level==null?null:mc.level.getEntity(p.getKey().caster());
            if(caster==null||!caster.isAlive()||ticks>p.getValue().expires()){
                mc.getSoundManager().stop(p.getValue().sound());it.remove();
            }
        }
    }
    private static void stop(Key caster){var playing=CHANTS.remove(caster);if(playing!=null)Minecraft.getInstance().getSoundManager().stop(playing.sound());}
}
