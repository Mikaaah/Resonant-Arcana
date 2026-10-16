package nl.townsandkingdoms.resonantarcana;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.neoforged.neoforge.registries.DeferredRegister;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;

/** Named, non-stacking enchantments use native attributes and expire with their effects. */
final class ArcanaEffects {
    static final DeferredRegister<MobEffect> REGISTER=DeferredRegister.create(Registries.MOB_EFFECT,ResonantArcana.ID);
    static final net.minecraft.core.Holder<MobEffect> WARCRY=REGISTER.register("warcry",()->new Beneficial(0xffd58a)
        .addAttributeModifier(Attributes.ATTACK_DAMAGE,id("warcry_melee"),.05,Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(AttributeRegistry.SPELL_POWER,id("warcry_magic"),.05,Operation.ADD_MULTIPLIED_TOTAL));
    static final net.minecraft.core.Holder<MobEffect> ARCANE_EDGE=REGISTER.register("arcane_edge",()->new Beneficial(0xb349f5)
        .addAttributeModifier(Attributes.ATTACK_DAMAGE,id("edge_damage"),.5,Operation.ADD_VALUE)
        .addAttributeModifier(Attributes.ATTACK_SPEED,id("edge_speed"),.025,Operation.ADD_MULTIPLIED_TOTAL));
    static final net.minecraft.core.Holder<MobEffect> TEMPO=REGISTER.register("battle_tempo",()->new Beneficial(0x7bdfff)
        .addAttributeModifier(Attributes.ATTACK_SPEED,id("tempo_attack"),.025,Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(Attributes.MOVEMENT_SPEED,id("tempo_move"),.025,Operation.ADD_MULTIPLIED_TOTAL));
    private static ResourceLocation id(String path){return ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,path);}
    private static final class Beneficial extends MobEffect {Beneficial(int color){super(MobEffectCategory.BENEFICIAL,color);}}
    private ArcanaEffects(){}
}
