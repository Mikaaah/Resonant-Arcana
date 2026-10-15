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

/** Iron's casting integration. Parent API classes and assets are never bundled. */
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
    static boolean castWand(ServerPlayer player,Spell spell,net.minecraft.world.item.ItemStack stack){
        MagicData data=MagicData.getPlayerMagicData(player);
        if(data.isCasting())return false; // Holding the attack key must not repeatedly cancel a windup.
        var nativeSpell=SpellRegistry.REGISTRY.get(id(spell));
        ISpellContainer.createImbuedContainer(nativeSpell,1,stack);
        return nativeSpell.attemptInitiateCast(stack,1,player.serverLevel(),player,CastSource.SPELLBOOK,true,
            io.redspace.ironsspellbooks.api.magic.SpellSelectionManager.MAINHAND);
    }
    static void cancel(ServerPlayer player){
        if(MagicData.getPlayerMagicData(player).isCasting())Utils.serverSideCancelCast(player);
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
            baseManaCost=spell.manaCost();
            manaCostPerLevel=Math.max(2,Math.min(10,baseManaCost/5));baseSpellPower=100;spellPowerPerLevel=25;
            castTime=spell.castTicks();
            defaults=new DefaultConfig().setMinRarity((spell.kind==Spell.Kind.CANNON||castTime>=40)?SpellRarity.EPIC:castTime>=20||spell.cooldown>=140?SpellRarity.RARE:SpellRarity.COMMON)
                .setSchoolResource(school(spell.theme)).setMaxLevel(spell.levelCap()).setCooldownSeconds(spell.cooldown/20.0).setAllowCrafting(true).build();
        }
        @Override public ResourceLocation getSpellResource(){return id(spell);}
        @Override public DefaultConfig getDefaultConfig(){return defaults;}
        @Override public int getSpellCooldown(){return Math.max(1,(int)Math.ceil(super.getSpellCooldown()*KingdomWands.COOLDOWNS.get()));}
        @Override public CastType getCastType(){return castTime>0?CastType.LONG:CastType.INSTANT;}
        @Override public MutableComponent getDisplayName(Player player){return Component.literal(spell.title);}
        @Override public boolean requiresLearning(){return false;}
        @Override public Optional<net.minecraft.sounds.SoundEvent> getCastStartSound(){return castTime>0?Optional.of(SpellAudio.event(SpellAudio.chargeId(spell))):Optional.empty();}
        @Override public Optional<net.minecraft.sounds.SoundEvent> getCastFinishSound(){return Optional.empty();}
        @Override public AnimationHolder getCastStartAnimation(){return switch(spell.kind){
            case CANNON,SONIC,CHANNEL,LANCE,RAIL->SpellAnimations.ONE_HANDED_RAY_CHARGE;
            case STORM,SMITE,TWISTER,METEOR->SpellAnimations.CHARGE_RAISED_HAND;
            case BURST,WELL,FIELD,MARK,COLLAPSE->SpellAnimations.CHARGE_WAVY_ANIMATION;
            case WARD,AURA->SpellAnimations.PREPARE_CROSS_ARMS;
            default->castTime>0?SpellAnimations.ANIMATION_LONG_CAST:SpellAnimations.ANIMATION_INSTANT_CAST;
        };}
        @Override public AnimationHolder getCastFinishAnimation(){return switch(spell.kind){
            case CANNON,SONIC,CHANNEL,LANCE,RAIL->SpellAnimations.ONE_HANDED_RAY_SHOOT;
            case STORM,SMITE,TWISTER,METEOR->SpellAnimations.ANIMATION_LONG_CAST_FINISH;
            default->SpellAnimations.ANIMATION_INSTANT_CAST;
        };}
        @Override public void onClientPreCast(Level level,int spellLevel,LivingEntity entity,net.minecraft.world.InteractionHand hand,MagicData data){
            // Audio is sent once by the server, with the same event exposed for native cancellation.
            if(getCastType().immediatelySuppressRightClicks()&&io.redspace.ironsspellbooks.player.ClientInputEvents.isUseKeyDown())
                io.redspace.ironsspellbooks.player.ClientSpellCastHelper.setSuppressRightClicks(true);
        }
        @Override public boolean checkPreCastConditions(Level level,int spellLevel,LivingEntity entity,MagicData data){
            return entity instanceof ServerPlayer player&&SpellEngine.canStart(player,spell);
        }
        @Override public void onServerPreCast(Level level,int spellLevel,LivingEntity entity,MagicData data){
            if(castTime>0&&entity instanceof ServerPlayer player)SpellAudio.charge(player,spell);
        }
        @Override public void onServerCastTick(Level level,int spellLevel,LivingEntity entity,MagicData data){
            if(castTime>0&&entity instanceof ServerPlayer player)
                SpellEngine.bookCharge(player,spell,Math.clamp(data.getCastCompletionPercent(),0,1),data.getCastDuration()-data.getCastDurationRemaining());
        }
        @Override public void onCast(Level level,int spellLevel,LivingEntity entity,CastSource source,MagicData data){
            if(entity instanceof ServerPlayer player){
                int cooldown=Utils.applyCooldownReduction(getSpellCooldown(),player);
                if(castTime>0&&spell!=Spell.SOUL_CANNON&&spell!=Spell.SONIC_BOOM)SpellAudio.cast(player,spell);
                SpellEngine.castFromSpellbook(player,spell,getSpellPower(spellLevel,player)/100f,getDamageSource(player),cooldown,castTime>0);
            }
        }
        @Override public List<MutableComponent> getUniqueInfo(int spellLevel,LivingEntity entity){
            var info=new ArrayList<MutableComponent>();
            info.add(Component.literal(spell.description()));
            if(spell.damage>0)info.add(Component.literal(String.format(Locale.ROOT,"Base damage: %.1f",spell.damage*getSpellPower(spellLevel,entity)/100f)));
            if(castTime>0)info.add(Component.literal(String.format(Locale.ROOT,"Windup: %.2fs",getEffectiveCastTime(spellLevel,entity)/20.0)));
            if(spell.range>0)info.add(Component.literal("Range: "+(int)spell.range+" blocks"));
            return info;
        }
    }
}
