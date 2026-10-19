package nl.townsandkingdoms.resonantarcana;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/** Recipes obey the same version gates as optional spell registration. */
record IntegrationCondition(String integration) implements ICondition {
    static final MapCodec<IntegrationCondition> CODEC=RecordCodecBuilder.mapCodec(b->b.group(com.mojang.serialization.Codec.STRING.fieldOf("integration").forGetter(IntegrationCondition::integration)).apply(b,IntegrationCondition::new));
    private static final DeferredRegister<MapCodec<? extends ICondition>> TYPES=DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS,ResonantArcana.ID);
    static void register(IEventBus bus){TYPES.register("supported_integration",()->CODEC);TYPES.register(bus);}
    @Override public boolean test(IContext context){return switch(integration){case "alexscaves"->CavesCompat.available();case "bossesrise"->BossesRiseCompat.available();default->false;};}
    @Override public MapCodec<? extends ICondition> codec(){return CODEC;}
}
