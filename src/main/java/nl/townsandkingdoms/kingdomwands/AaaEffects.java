package nl.townsandkingdoms.kingdomwands;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;

/** Client-only optional API bridge. No renderer or native libraries are bundled. */
final class AaaEffects {
    private static boolean checked, available;
    private static int spawned;
    private static Constructor<?> emitter;
    private static Method position, rotation, scale, add;
    private AaaEffects() {}
    static void tick(){spawned=0;}

    static void visual(WandNetwork.Visual message) {
        if(!message.valid()||!KingdomWands.AAA_PARTICLES.get())return;
        var mc=Minecraft.getInstance();
        if(mc.level==null||mc.player==null||spawned>=KingdomWands.AAA_BUDGET.get())return;
        Vec3 start=new Vec3(message.x(),message.y(),message.z()),end=new Vec3(message.endX(),message.endY(),message.endZ());
        // Use the nearest point, not just the impact: a nearby beam's far end may be distant.
        Vec3 delta=end.subtract(start);double lengthSquared=delta.lengthSqr();
        Vec3 nearest=lengthSquared<0.001?start:start.add(delta.scale(Math.clamp(mc.player.getEyePosition().subtract(start).dot(delta)/lengthSquared,0,1)));
        if(mc.player.distanceToSqr(nearest)>96*96)return;
        if(!checked)initialize();
        if(!available)return;
        var spell=Spell.values()[message.spell()];var stage=SpellVisuals.Stage.values()[message.stage()];
        var shape=SpellVisuals.shape(spell,stage);
        float size=message.size(),sx=size,sy=size,sz=size;
        boolean directional=stage==SpellVisuals.Stage.LINK||stage==SpellVisuals.Stage.STRIKE||stage==SpellVisuals.Stage.CAST||stage==SpellVisuals.Stage.FLIGHT||stage==SpellVisuals.Stage.CHARGE;
        if(stage==SpellVisuals.Stage.LINK||stage==SpellVisuals.Stage.STRIKE){
            if(lengthSquared<0.01)return;
            sx=sy=shape==SpellVisuals.Shape.FLAME?0.8f:shape==SpellVisuals.Shape.LIGHTNING?0.7f:0.45f;
            sz=(float)Math.sqrt(lengthSquared);
        } else if(stage==SpellVisuals.Stage.FLIGHT){
            // Flight messages start at the visible head; their endpoint carries its direction.
            sy=shape==SpellVisuals.Shape.WAVE?0.9f:size;
        } else if(stage==SpellVisuals.Stage.AREA){
            sy=switch(shape){case PULSE -> 0.7f;case CAGE,CURSE -> 1.0f;case VORTEX -> Math.min(4,size);default -> Math.min(2.5f,size);};
        }
        try {
            var id=ResourceLocation.fromNamespaceAndPath(KingdomWands.ID,shape.name().toLowerCase(Locale.ROOT)+"_"+spell.theme.name().toLowerCase(Locale.ROOT));
            var info=emitter.newInstance(id);
            double lift=stage==SpellVisuals.Stage.AREA&&(shape==SpellVisuals.Shape.FIELD||shape==SpellVisuals.Shape.VORTEX)?0.08:0;
            position.invoke(info,start.x,start.y+lift,start.z);
            scale.invoke(info,sx,sy,sz);
            if(directional&&lengthSquared>0.001)rotation.invoke(info,delta.normalize());
            add.invoke(null,mc.level,info);spawned++;
        }catch(ReflectiveOperationException|LinkageError error){
            available=false;
            KingdomWands.LOG.warn("AAAParticles spell visuals disabled; vanilla effects remain available",error);
        }
    }

    private static void initialize() {
        checked=true;
        if(!ModList.get().isLoaded("aaa_particles"))return;
        try {
            var info=Class.forName("mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo");
            var api=Class.forName("mod.chloeprime.aaaparticles.api.common.AAALevel");
            emitter=info.getConstructor(ResourceLocation.class);
            position=info.getMethod("position",double.class,double.class,double.class);
            rotation=info.getMethod("rotationFromForward",Vec3.class);
            scale=info.getMethod("scale",float.class,float.class,float.class);
            add=api.getMethod("addParticle",Level.class,info);
            available=true;
            KingdomWands.LOG.info("AAAParticles full spell visuals enabled");
        }catch(ReflectiveOperationException|LinkageError error){
            KingdomWands.LOG.warn("AAAParticles API unavailable; using vanilla spell effects",error);
        }
    }
}
