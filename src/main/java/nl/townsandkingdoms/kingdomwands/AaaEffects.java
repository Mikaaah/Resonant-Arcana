package nl.townsandkingdoms.kingdomwands;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;

/** Optional client bridge. No AAAParticles types are linked on a dedicated server. */
final class AaaEffects {
    private static boolean checked, available;
    private static Constructor<?> emitter;
    private static Method position, add;
    private AaaEffects() {}

    static void burst(WandNetwork.Burst burst) {
        if (!KingdomWands.AAA_PARTICLES.get()) return;
        if (!checked) initialize();
        if (!available) return;
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        for (var theme : Spell.Theme.values()) {
            if (theme.color != burst.color()) continue;
            try {
                var id = ResourceLocation.fromNamespaceAndPath(KingdomWands.ID,
                    "impact_" + theme.name().toLowerCase(Locale.ROOT));
                var info = emitter.newInstance(id);
                position.invoke(info, burst.x(), burst.y(), burst.z());
                add.invoke(null, level, info);
            } catch (ReflectiveOperationException | LinkageError error) {
                available = false;
                KingdomWands.LOG.warn("AAAParticles impact effects disabled; vanilla effects remain available", error);
            }
            return;
        }
    }

    private static void initialize() {
        checked = true;
        if (!ModList.get().isLoaded("aaa_particles")) return;
        try {
            // Reflection keeps the renderer optional and avoids bundling its API or native libraries.
            var info = Class.forName("mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo");
            var api = Class.forName("mod.chloeprime.aaaparticles.api.common.AAALevel");
            emitter = info.getConstructor(ResourceLocation.class);
            position = info.getMethod("position", double.class, double.class, double.class);
            add = api.getMethod("addParticle", Level.class, info);
            available = true;
            KingdomWands.LOG.info("AAAParticles spell impact effects enabled");
        } catch (ReflectiveOperationException | LinkageError error) {
            KingdomWands.LOG.warn("AAAParticles API unavailable; using vanilla spell effects", error);
        }
    }
}
