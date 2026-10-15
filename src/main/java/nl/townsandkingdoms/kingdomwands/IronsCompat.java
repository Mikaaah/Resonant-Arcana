package nl.townsandkingdoms.kingdomwands;

import java.util.*;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Loaded only when Iron's is present. Its API and assets are never bundled. */
final class IronsCompat {
    private static final DeferredRegister<AbstractSpell> SPELLS=DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY,KingdomWands.ID);
    static void register(IEventBus bus){
        for(Spell spell:Spell.values())SPELLS.register(spell.bookId(),()->new ArcanaSpell(spell));
        SPELLS.register(bus);
        KingdomWands.LOG.info("Registering {} Resonant Arcana spells with Iron's Spellbooks",Spell.values().length);
    }
    static net.minecraft.world.item.ItemStack scroll(Spell spell){
        var stack=io.redspace.ironsspellbooks.registries.ItemRegistry.SCROLL.get().getDefaultInstance();
        ISpellContainer.createScrollContainer(SpellRegistry.REGISTRY.get(id(spell)),1,stack);
        return stack;
    }
    static ResourceLocation id(Spell spell){return ResourceLocation.fromNamespaceAndPath(KingdomWands.ID,spell.bookId());}
    static ResourceLocation school(Spell.Theme theme){return switch(theme){
        case FIRE->SchoolRegistry.FIRE_RESOURCE;
        case BLOOD->SchoolRegistry.BLOOD_RESOURCE;
        case POISON->SchoolRegistry.NATURE_RESOURCE;
        case THUNDER->SchoolRegistry.LIGHTNING_RESOURCE;
        case SOUL->SchoolRegistry.ELDRITCH_RESOURCE;
        case GRAVITY,DARK->SchoolRegistry.ENDER_RESOURCE;
        case MAGIC->SchoolRegistry.EVOCATION_RESOURCE;
    };}
    static final class ArcanaSpell extends AbstractSpell {
        final Spell spell;
        private final DefaultConfig defaults;
        ArcanaSpell(Spell spell){
            this.spell=spell;
            baseManaCost=spell==Spell.SOUL_CANNON?90:Math.max(12,Math.min(65,8+(int)(spell.damage*2)+spell.cooldown/10));
            manaCostPerLevel=0;baseSpellPower=100;spellPowerPerLevel=0;
            castTime=spell.kind==Spell.Kind.CANNON?40:spell.kind==Spell.Kind.SONIC?12:0;
            defaults=new DefaultConfig().setMinRarity(spell.kind==Spell.Kind.CANNON?SpellRarity.EPIC:spell.cooldown>=140?SpellRarity.RARE:SpellRarity.COMMON)
                .setSchoolResource(school(spell.theme)).setMaxLevel(1).setCooldownSeconds(spell.cooldown/20.0).setAllowCrafting(true).build();
        }
        @Override public ResourceLocation getSpellResource(){return id(spell);}
        @Override public DefaultConfig getDefaultConfig(){return defaults;}
        @Override public CastType getCastType(){return castTime>0?CastType.LONG:CastType.INSTANT;}
        @Override public MutableComponent getDisplayName(Player player){return Component.literal(spell.title);}
        @Override public boolean requiresLearning(){return false;}
        @Override public Optional<net.minecraft.sounds.SoundEvent> getCastStartSound(){return Optional.empty();}
        @Override public Optional<net.minecraft.sounds.SoundEvent> getCastFinishSound(){return Optional.empty();}
        @Override public AnimationHolder getCastStartAnimation(){return castTime>0?SpellAnimations.ONE_HANDED_RAY_CHARGE:SpellAnimations.ANIMATION_INSTANT_CAST;}
        @Override public AnimationHolder getCastFinishAnimation(){return castTime>0?SpellAnimations.ONE_HANDED_RAY_SHOOT:SpellAnimations.ANIMATION_INSTANT_CAST;}
        @Override public boolean checkPreCastConditions(Level level,int spellLevel,LivingEntity entity,MagicData data){
            return entity instanceof ServerPlayer player&&SpellEngine.canStart(player,spell);
        }
        @Override public void onServerPreCast(Level level,int spellLevel,LivingEntity entity,MagicData data){
            if(castTime>0&&entity instanceof ServerPlayer player)SpellAudio.cast(player,spell);
        }
        @Override public void onServerCastTick(Level level,int spellLevel,LivingEntity entity,MagicData data){
            if(castTime>0&&entity instanceof ServerPlayer player)
                SpellEngine.bookCharge(player,spell,Math.clamp(data.getCastCompletionPercent(),0,1),data.getCastDuration()-data.getCastDurationRemaining());
        }
        @Override public void onCast(Level level,int spellLevel,LivingEntity entity,CastSource source,MagicData data){
            if(entity instanceof ServerPlayer player){
                int cooldown=Utils.applyCooldownReduction(getSpellCooldown(),player);
                SpellEngine.castFromSpellbook(player,spell,getSpellPower(spellLevel,player)/100f,getDamageSource(player),cooldown,castTime>0);
            }
        }
        @Override public List<MutableComponent> getUniqueInfo(int spellLevel,LivingEntity entity){
            var info=new ArrayList<MutableComponent>();
            info.add(Component.literal(spell.description()));
            if(spell.damage>0)info.add(Component.literal(String.format(Locale.ROOT,"Base damage: %.1f",spell.damage*getSpellPower(spellLevel,entity)/100f)));
            if(spell.range>0)info.add(Component.literal("Range: "+(int)spell.range+" blocks"));
            return info;
        }
    }
}
