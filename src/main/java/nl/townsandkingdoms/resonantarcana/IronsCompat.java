package nl.townsandkingdoms.resonantarcana;

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
    private static final DeferredRegister<AbstractSpell> SPELLS=DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY,ResonantArcana.ID);
    static void register(IEventBus bus){
        for(Spell spell:Spell.values())SPELLS.register(spell.bookId(),()->new ArcanaSpell(spell));
        SPELLS.register(bus);
        ResonantArcana.LOG.info("Registering {} Resonant Arcana spells with Iron's Spellbooks",Spell.values().length);
    }
    static net.minecraft.world.item.ItemStack scroll(Spell spell){return scroll(spell,1);}
    static net.minecraft.world.item.ItemStack scroll(Spell spell,int level){
        var stack=io.redspace.ironsspellbooks.registries.ItemRegistry.SCROLL.get().getDefaultInstance();
        ISpellContainer.createScrollContainer(SpellRegistry.REGISTRY.get(id(spell)),Math.clamp(level,1,spell.levelCap()),stack);
        return stack;
    }
    static boolean castWand(ServerPlayer player,Spell spell,net.minecraft.world.item.ItemStack stack){
        MagicData data=MagicData.getPlayerMagicData(player);
        if(data.isCasting())return false; // Holding the attack key must not repeatedly cancel a windup.
        var nativeSpell=SpellRegistry.REGISTRY.get(id(spell));
        int rank=stack.getItem() instanceof WandItem wand?Math.min(wand.level(stack,spell),nativeSpell.getMaxLevel()):1;
        ISpellContainer.createImbuedContainer(nativeSpell,rank,stack);
        return nativeSpell.attemptInitiateCast(stack,rank,player.serverLevel(),player,CastSource.SPELLBOOK,true,
            io.redspace.ironsspellbooks.api.magic.SpellSelectionManager.MAINHAND);
    }
    static boolean learnOffhand(ServerPlayer player,WandItem wand,net.minecraft.world.item.ItemStack stack){
        var scroll=player.getOffhandItem();
        if(!scroll.is(io.redspace.ironsspellbooks.registries.ItemRegistry.SCROLL.get()))return false;
        var container=ISpellContainer.get(scroll);
        if(container==null||container.isEmpty()){
            player.displayClientMessage(Component.literal("This scroll contains no spell."),true);return true;
        }
        var data=container.getSpellAtIndex(0);
        Spell matched=Arrays.stream(Spell.values()).filter(s->id(s).equals(data.getSpell().getSpellResource())).findFirst().orElse(null);
        String result;
        if(matched==null||!wand.spells.contains(matched))result="This scroll belongs to another wand.";
        else if(data.getLevel()<=wand.level(stack,matched))result="This wand already knows that spell level.";
        else if(!data.getSpell().isEnabled()||data.getLevel()>Math.min(matched.levelCap(),data.getSpell().getMaxLevel()))result="That spell level is disabled by the server.";
        else{
            cancel(player);wand.learn(stack,matched,data.getLevel());wand.select(stack,wand.spells.indexOf(matched));
            if(!player.getAbilities().instabuild)scroll.shrink(1);
            SpellEngine.switchEffect(player,matched.theme);result="Learned "+matched.title+" "+WandItem.roman(data.getLevel())+".";
        }
        player.displayClientMessage(Component.literal(result),true);return true;
    }
    static void cancel(ServerPlayer player){
        if(MagicData.getPlayerMagicData(player).isCasting())Utils.serverSideCancelCast(player);
    }
    static ResourceLocation id(Spell spell){return ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,spell.bookId());}
    static ResourceLocation school(Spell.Theme theme){return switch(theme){
        case HOLY->SchoolRegistry.HOLY_RESOURCE;case ICE->SchoolRegistry.ICE_RESOURCE;
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
            manaCostPerLevel=spell.manaStep();baseSpellPower=100;spellPowerPerLevel=SpellCatalogue.POWER_PER_LEVEL;
            castTime=spell.castTicks();
            defaults=new DefaultConfig().setMinRarity(spell==Spell.DOOM?SpellRarity.LEGENDARY:(spell.kind==Spell.Kind.CANNON||castTime>=40)?SpellRarity.EPIC:castTime>=20||spell.cooldown>=140?SpellRarity.RARE:SpellRarity.COMMON)
                .setSchoolResource(school(spell.theme)).setMaxLevel(spell.levelCap()).setCooldownSeconds(spell.cooldown/20.0).setAllowCrafting(true).build();
        }
        @Override public ResourceLocation getSpellResource(){return id(spell);}
        @Override public DefaultConfig getDefaultConfig(){return defaults;}
        @Override public int getSpellCooldown(){return Math.max(1,(int)Math.ceil(super.getSpellCooldown()*ArcanaConfig.COOLDOWNS.get()));}
        @Override public CastType getCastType(){return castTime>0?CastType.LONG:CastType.INSTANT;}
        @Override public MutableComponent getDisplayName(Player player){return Component.literal(spell.title);}
        @Override public boolean requiresLearning(){return false;}
        @Override public Optional<net.minecraft.sounds.SoundEvent> getCastStartSound(){return castTime>0?Optional.of(SpellAudio.event(SpellAudio.chargeId(spell))):Optional.empty();}
        @Override public Optional<net.minecraft.sounds.SoundEvent> getCastFinishSound(){return Optional.empty();}
        @Override public AnimationHolder getCastStartAnimation(){return switch(spell.kind){
            case CANNON,SONIC,CHANNEL,LANCE,RAIL,PIERCING_ORB,CONE_CHANNEL->SpellAnimations.ONE_HANDED_RAY_CHARGE;
            case STORM,SMITE,TWISTER,METEOR,FROSTFRONT,PILLAR,RAIN->SpellAnimations.CHARGE_RAISED_HAND;
            case BURST,WELL,FIELD,MARK,COLLAPSE,DOOM,FANGS->SpellAnimations.CHARGE_WAVY_ANIMATION;
            case WARD,AURA,HEAL,BUFF,CLEANSE,HYMN,REMEDY->SpellAnimations.PREPARE_CROSS_ARMS;
            default->castTime>0?SpellAnimations.ANIMATION_LONG_CAST:SpellAnimations.ANIMATION_INSTANT_CAST;
        };}
        @Override public AnimationHolder getCastFinishAnimation(){return switch(spell.kind){
            case CANNON,SONIC,CHANNEL,LANCE,RAIL,PIERCING_ORB,CONE_CHANNEL->SpellAnimations.ONE_HANDED_RAY_SHOOT;
            case STORM,SMITE,TWISTER,METEOR,FROSTFRONT,PILLAR,RAIN->SpellAnimations.ANIMATION_LONG_CAST_FINISH;
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
            if(castTime>0&&entity instanceof ServerPlayer player){
                if(spell==Spell.SANGUINE_PACT&&player.getHealth()<=2){cancel(player);return;}
                SpellEngine.bookCharge(player,spell,Math.clamp(data.getCastCompletionPercent(),0,1),data.getCastDuration()-data.getCastDurationRemaining());
            }
        }
        @Override public void onCast(Level level,int spellLevel,LivingEntity entity,CastSource source,MagicData data){
            if(entity instanceof ServerPlayer player){
                int cooldown=Utils.applyCooldownReduction(getSpellCooldown(),player);
                if(castTime>0&&spell!=Spell.SOUL_CANNON&&spell!=Spell.SONIC_SCREAM)SpellAudio.cast(player,spell);
                SpellEngine.castFromSpellbook(player,spell,getSpellPower(spellLevel,player)/100f,getDamageSource(player),cooldown,castTime>0);
            }
        }
        @Override public List<MutableComponent> getUniqueInfo(int spellLevel,LivingEntity entity){
            var info=new ArrayList<MutableComponent>();
            info.add(Component.literal(spell.description()));
            float power=getSpellPower(spellLevel,entity)/100f;
            info.add(Component.literal(spell.role()+" • "+SpellInfo.school(spell.theme)));
            if(spell.damage>0)info.add(Component.literal(String.format(Locale.ROOT,"Damage: %.1f%s",spell.damage*power,spell.pulses()>1?" per hit / pulse":"")));
            if(spell.healing()>0)info.add(Component.literal(String.format(Locale.ROOT,"Healing: %.1f health%s",spell.healing()*power,spell.pulses()>1?" per pulse":"")));
            if(castTime>0)info.add(Component.literal(String.format(Locale.ROOT,"Windup: %.2fs",getEffectiveCastTime(spellLevel,entity)/20.0)));
            if(spell.range>0)info.add(Component.literal("Base range: "+(int)spell.range+" blocks"));
            if(spell.duration()>0)info.add(Component.literal(String.format(Locale.ROOT,"Base duration: %.1fs",spell.duration()/20.0)));
            info.addAll(SpellInfo.martial(spell,power));return info;
        }
    }
}
