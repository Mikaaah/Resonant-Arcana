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
        final Entity owner;
        Layer(Entity entity,String id,float gain,float pitch){super(SpellAudio.event(id),SoundSource.PLAYERS,entity.getRandom());owner=entity;looping=true;delay=0;volume=(float)(gain*ArcanaConfig.SOUND_VOLUME.get());this.pitch=pitch;tick();}
        @Override public void tick(){if(!owner.isAlive()){stop();return;}x=owner.getX();y=owner.getY()+owner.getBbHeight()*.55;z=owner.getZ();}
    }
    static void handle(WandNetwork.Loop cue){
        if(!cue.valid())return;var mc=Minecraft.getInstance();if(mc.level==null)return;
        if(world!=mc.level){clear();world=mc.level;}
        Key key=new Key(cue.caster(),cue.spell(),cue.release());
        if(!cue.play()){stop(key);return;}Entity owner=mc.level.getEntity(cue.caster());if(owner==null||!owner.isAlive())return;
        Playing playing=ACTIVE.get(key);if(playing!=null&&playing.variant!=cue.variant()){stop(key);playing=null;}
        if(playing==null){if(ACTIVE.size()>=16)return;playing=new Playing();playing.variant=cue.variant();Spell spell=Spell.values()[cue.spell()];
            if(spell==Spell.DOOM){
                add(playing,owner,"entity.warden.heartbeat",.95f,.85f);add(playing,owner,"block.portal.ambient",.45f,.9f);
                add(playing,owner,"block.respawn_anchor.ambient",.25f,.9f);add(playing,owner,"block.beacon.ambient",.2f,.8f);
                add(playing,owner,"block.sculk_sensor.clicking",.15f,.85f);add(playing,owner,"block.amethyst_block.resonate",.15f,.9f);add(playing,owner,"entity.enderman.stare",.12f,1);
            }else if((spell==Spell.TREMORZILLA_BREATH||spell==Spell.TREMORZILLA_CATACLYSM)&&cue.release())add(playing,owner,"alexscaves:tremorzilla_beam_loop",1.1f,1);
            else if(GodSpells.handles(spell)&&!cue.release())add(playing,owner,GodSpells.chargeSound(spell),1.25f,1);
            else if(spell==Spell.TREMORZILLA_BREATH&&!cue.release())add(playing,owner,"alexscaves:tremorzilla_charge_normal",1,1);
            if(playing.sounds.isEmpty())return;ACTIVE.put(key,playing);
        }
        playing.expires=ticks+14;
    }
    private static void add(Playing playing,Entity owner,String id,float gain,float pitch){var sound=new Layer(owner,id,gain,pitch);playing.sounds.add(sound);Minecraft.getInstance().getSoundManager().play(sound);}
    static void tick(){ticks++;var level=Minecraft.getInstance().level;if(level!=world){clear();world=level;}var it=ACTIVE.entrySet().iterator();while(it.hasNext()){var e=it.next();var owner=level==null?null:level.getEntity(e.getKey().caster());if(owner==null||!owner.isAlive()||e.getValue().expires<ticks){e.getValue().sounds.forEach(s->Minecraft.getInstance().getSoundManager().stop(s));it.remove();}}}
    private static void stop(Key key){var old=ACTIVE.remove(key);if(old!=null)old.sounds.forEach(s->Minecraft.getInstance().getSoundManager().stop(s));}
    private static void clear(){ACTIVE.values().forEach(p->p.sounds.forEach(s->Minecraft.getInstance().getSoundManager().stop(s)));ACTIVE.clear();}
    private SpellLoopAudio(){}
}
