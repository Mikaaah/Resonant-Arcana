package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

/** Owned, positional sound beds. Expiry, cancellation and dimension changes stop every layer. */
final class SpellLoopAudio {
    private record Key(int caster,int spell,boolean release){}
    private static final class Playing {final ArrayList<Layer> sounds=new ArrayList<>();int expires;int variant;}
    private static final Map<Key,Playing> ACTIVE=new HashMap<>();
    private static ClientLevel world;private static int ticks;
    private static final class Layer extends AbstractTickableSoundInstance {
        final Entity owner;final float gain,basePitch;float progress;final boolean rising;
        Layer(Entity entity,String id,float gain,float pitch){super(SpellAudio.event(id),SoundSource.PLAYERS,entity.getRandom());owner=entity;this.gain=gain;basePitch=pitch;rising=id.startsWith("resonant_arcana:");looping=true;delay=0;relative=entity==Minecraft.getInstance().player;if(relative)attenuation=net.minecraft.client.resources.sounds.SoundInstance.Attenuation.NONE;tick();}
        @Override public void tick(){if(!owner.isAlive()){stop();return;}x=relative?0:owner.getX();y=relative?0:owner.getY()+owner.getBbHeight()*.55;z=relative?0:owner.getZ();volume=(float)(gain*ArcanaConfig.SOUND_VOLUME.get()*(rising?.75+progress*.55:1));pitch=basePitch*(rising?.9f+progress*.16f:1);}
    }
    static void handle(WandNetwork.Loop cue){
        if(!cue.valid())return;var mc=Minecraft.getInstance();if(mc.level==null)return;
        if(world!=mc.level){clear();world=mc.level;}
        Key key=new Key(cue.caster(),cue.spell(),cue.release());
        if(!cue.play()){stop(key);return;}Entity owner=mc.level.getEntity(cue.caster());if(owner==null||!owner.isAlive())return;
        Playing playing=ACTIVE.get(key);if(playing!=null&&playing.variant!=cue.variant()){stop(key);playing=null;}
        if(playing==null){if(ACTIVE.size()>=16)return;playing=new Playing();playing.variant=cue.variant();Spell spell=Spell.values()[cue.spell()];
            if(spell==Spell.DOOM){
                add(playing,owner,ResonantArcana.ID+(cue.release()?":doom_gravity_bed":":doom_charge_bed"),1.4f,1);
            }else if((spell==Spell.TREMORZILLA_BREATH||spell==Spell.TREMORZILLA_CATACLYSM)&&cue.release())add(playing,owner,"alexscaves:tremorzilla_beam_loop",1.1f,1);
            else if((GodSpells.handles(spell)||spell==Spell.ORBITAL_RAY)&&!cue.release())add(playing,owner,RitualAudio.charge(spell),1.7f,1);
            else if(spell==Spell.TREMORZILLA_BREATH&&!cue.release())add(playing,owner,"alexscaves:tremorzilla_charge_normal",1,1);
            if(playing.sounds.isEmpty())return;ACTIVE.put(key,playing);
        }
        for(Layer layer:playing.sounds){layer.progress=cue.progress();layer.tick();}
        playing.expires=ticks+14;
    }
    private static void add(Playing playing,Entity owner,String id,float gain,float pitch){var sound=new Layer(owner,id,gain,pitch);playing.sounds.add(sound);Minecraft.getInstance().getSoundManager().play(sound);}
    static void tick(){ticks++;var level=Minecraft.getInstance().level;if(level!=world){clear();world=level;}var it=ACTIVE.entrySet().iterator();while(it.hasNext()){var e=it.next();var owner=level==null?null:level.getEntity(e.getKey().caster());if(owner==null||!owner.isAlive()||e.getValue().expires<ticks){e.getValue().sounds.forEach(s->Minecraft.getInstance().getSoundManager().stop(s));it.remove();}}}
    private static void stop(Key key){var old=ACTIVE.remove(key);if(old!=null)old.sounds.forEach(s->Minecraft.getInstance().getSoundManager().stop(s));}
    private static void clear(){ACTIVE.values().forEach(p->p.sounds.forEach(s->Minecraft.getInstance().getSoundManager().stop(s)));ACTIVE.clear();}
    private SpellLoopAudio(){}
}
