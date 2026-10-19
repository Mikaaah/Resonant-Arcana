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
        for(Spell spell:Spell.enabledValues())SPELLS.register(spell.bookId(),()->new ArcanaSpell(spell));
        SPELLS.register(bus);
        ResonantArcana.LOG.info("Registering {} Resonant Arcana spells with Iron's Spellbooks",Spell.enabledValues().length);
    }
    static net.minecraft.world.item.ItemStack scroll(Spell spell){return scroll(spell,1);}
    static net.minecraft.world.item.ItemStack scroll(Spell spell,int level){
        var stack=io.redspace.ironsspellbooks.registries.ItemRegistry.SCROLL.get().getDefaultInstance();
        ISpellContainer.createScrollContainer(SpellRegistry.REGISTRY.get(id(spell)),Math.clamp(level,1,spell.levelCap()),stack);
        if(GodSpells.handles(spell))stack.set(net.minecraft.core.component.DataComponents.ENCHANTMENT_GLINT_OVERRIDE,true);
        return stack;
    }
    static boolean castWand(ServerPlayer player,Spell spell,net.minecraft.world.item.ItemStack stack){
        MagicData data=MagicData.getPlayerMagicData(player);
        if(data.isCasting())return false; // Holding the attack key must not repeatedly cancel a windup.
        var nativeSpell=SpellRegistry.REGISTRY.get(id(spell));
        if(!nativeSpell.isEnabled())return false;
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
            defaults=new DefaultConfig().setMinRarity((GodSpells.handles(spell)||CavesCompat.cataclysm(spell)||spell==Spell.DOOM||spell==Spell.DOOM_BEAM||spell.kind==Spell.Kind.CATACLYSM)?SpellRarity.LEGENDARY:(spell.kind==Spell.Kind.CANNON||castTime>=40)?SpellRarity.EPIC:castTime>=20||spell.cooldown>=140?SpellRarity.RARE:SpellRarity.COMMON)
                .setSchoolResource(school(spell.theme)).setMaxLevel(spell.levelCap()).setCooldownSeconds(spell.cooldown/20.0).setAllowCrafting(!GodSpells.handles(spell)&&!CavesCompat.optional(spell)&&!BossesRiseCompat.optional(spell)).build();
        }
        @Override public boolean attemptInitiateCast(net.minecraft.world.item.ItemStack stack,int rank,Level level,Player player,CastSource source,boolean triggerCooldown,String slot){
            if(spell==Spell.CRIMSON_REVERSAL&&!level.isClientSide()&&player instanceof ServerPlayer p){
                var data=MagicData.getPlayerMagicData(p);
                if(data.getPlayerRecasts().hasRecastForSpell(getSpellId())){
                    if(data.isCasting()||!canBeCastedBy(rank,source,data,p).isSuccess()||!checkPreCastConditions(level,rank,p,data))return false;
                    var event=new io.redspace.ironsspellbooks.api.events.SpellPreCastEvent(p,getSpellId(),rank,getSchoolType(),source);
                    net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(event);if(event.isCanceled())return false;
                    castSpell(level,rank,p,source,triggerCooldown);return true;
                }
            }
            return super.attemptInitiateCast(stack,rank,level,player,source,triggerCooldown,slot);
        }
        @Override public ResourceLocation getSpellResource(){return id(spell);}
        @Override public DefaultConfig getDefaultConfig(){return defaults;}
        @Override public int getSpellCooldown(){return Math.max(1,(int)Math.ceil(super.getSpellCooldown()*ArcanaConfig.COOLDOWNS.get()));}
        @Override public int getRecastCount(int level,LivingEntity entity){return spell==Spell.CRIMSON_REVERSAL?2:0;}
        @Override public int getEffectiveCastTime(int level,LivingEntity entity){
            if(RecordedSpells.handles(spell))return spell.castTicks(); // Preserve the original voices and release cues without pitch shifting.
            return spell==Spell.CRIMSON_REVERSAL&&entity!=null&&MagicData.getPlayerMagicData(entity).getPlayerRecasts().hasRecastForSpell(getSpellId())?0:super.getEffectiveCastTime(level,entity);
        }
        @Override public CastType getCastType(){return castTime>0?CastType.LONG:CastType.INSTANT;}
        @Override public MutableComponent getDisplayName(Player player){return Component.literal(spell.title);}
        @Override public boolean requiresLearning(){return false;}
        @Override public Optional<net.minecraft.sounds.SoundEvent> getCastStartSound(){return castTime>0&&!SpellLoops.supports(spell)&&!CavesCompat.optional(spell)&&spell!=Spell.DOOM&&!RecordedSpells.handles(spell)?Optional.of(SpellAudio.event(SpellAudio.chargeId(spell))):Optional.empty();}
        @Override public Optional<net.minecraft.sounds.SoundEvent> getCastFinishSound(){return Optional.empty();}
        @Override public AnimationHolder getCastStartAnimation(){return switch(spell.kind){
            case CANNON,SONIC,CHANNEL,LANCE,RAIL,PIERCING_ORB,CONE_CHANNEL->SpellAnimations.ONE_HANDED_RAY_CHARGE;
            case STORM,SMITE,TWISTER,METEOR,FROSTFRONT,PILLAR,RAIN->SpellAnimations.CHARGE_RAISED_HAND;
            case ASCENSION,DEVASTATOR,CATACLYSM,BURST,WELL,FIELD,MARK,COLLAPSE,DOOM,FANGS->SpellAnimations.CHARGE_WAVY_ANIMATION;
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
            if(!isEnabled())return false;
            if(spell==Spell.CRIMSON_REVERSAL&&entity instanceof ServerPlayer player&&data.getPlayerRecasts().hasRecastForSpell(getSpellId())){
                var recast=data.getPlayerRecasts().getRecastInstance(getSpellId());return recast.getCastData() instanceof ReversalData anchor&&anchor.safe(player);
            }
            return entity instanceof ServerPlayer player&&SpellEngine.canStart(player,spell);
        }
        @Override public void onServerPreCast(Level level,int spellLevel,LivingEntity entity,MagicData data){
            if(entity instanceof ServerPlayer player){
                if(GodSpells.handles(spell))GodSpells.lock(player,spell);
                if(spell==Spell.EXPLOSION)ExplosionRitual.lock(player,data);
                else {if(spell==Spell.I_AM_ATOMIC)AtomicRitual.lock(player,data);if(RecordedSpells.handles(spell))RecordedSpells.start(player,spell);}
            }
            if(castTime>0&&!RecordedSpells.handles(spell)&&!(spell==Spell.CRIMSON_REVERSAL&&data.getPlayerRecasts().hasRecastForSpell(getSpellId()))&&entity instanceof ServerPlayer player)SpellAudio.charge(player,spell);
        }
        @Override public void onServerCastComplete(Level level,int spellLevel,LivingEntity entity,MagicData data,boolean cancelled){
            if(entity instanceof ServerPlayer player){
                if(RecordedSpells.handles(spell))RecordedSpells.stop(player,spell);
                if(spell==Spell.I_AM_ATOMIC&&cancelled)AtomicRitual.end(player,data);
                if(spell==Spell.DOOM_BEAM&&cancelled)BeamRitual.stop(player);
            }
            if(entity instanceof ServerPlayer player){SpellLoops.stop(player,spell,false);GodSpells.unlock(player);if(cancelled)SpellLoops.stop(player,spell,true);}
            super.onServerCastComplete(level,spellLevel,entity,data,cancelled);
        }
        @Override public void onServerCastTick(Level level,int spellLevel,LivingEntity entity,MagicData data){
            if(castTime>0&&entity instanceof ServerPlayer player){
                if(spell==Spell.SANGUINE_PACT&&player.getHealth()<=2){cancel(player);return;}
                SpellEngine.bookCharge(player,spell,Math.clamp(data.getCastCompletionPercent(),0,1),data.getCastDuration()-data.getCastDurationRemaining());
            }
        }
        @Override public void onCast(Level level,int spellLevel,LivingEntity entity,CastSource source,MagicData data){
            if(entity instanceof ServerPlayer player){
                if(spell==Spell.CRIMSON_REVERSAL&&data.getPlayerRecasts().hasRecastForSpell(getSpellId())){
                    var recast=data.getPlayerRecasts().getRecastInstance(getSpellId());if(recast.getCastData() instanceof ReversalData anchor)anchor.returnPlayer(player);return;
                }
                ReversalData anchor=spell==Spell.CRIMSON_REVERSAL?new ReversalData(player):null;
                int cooldown=Utils.applyCooldownReduction(getSpellCooldown(),player);
                if(castTime>0&&!RecordedSpells.handles(spell)&&spell!=Spell.SOUL_CANNON&&spell!=Spell.SONIC_SCREAM)SpellAudio.cast(player,spell);
                boolean success=SpellEngine.castFromSpellbook(player,spell,getSpellPower(spellLevel,player)/100f,getDamageSource(player),cooldown,castTime>0);
                if(success&&anchor!=null)data.getPlayerRecasts().addRecast(new io.redspace.ironsspellbooks.capabilities.magic.RecastInstance(getSpellId(),spellLevel,2,60,source,anchor),data);
            }
        }
        @Override public List<MutableComponent> getUniqueInfo(int spellLevel,LivingEntity entity){
            return SpellInfo.overview(spell,getSpellPower(spellLevel,entity)/100f,getEffectiveCastTime(spellLevel,entity)/20.0);
        }
    }
}
