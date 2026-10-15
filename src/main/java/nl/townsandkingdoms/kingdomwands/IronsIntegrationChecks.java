package nl.townsandkingdoms.kingdomwands;

import java.util.*;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

/** Invoked only by the explicitly enabled integration fixture, with Iron's installed. */
final class IronsIntegrationChecks {
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        var player=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("d8a06677-1d44-4ed0-aef9-ef4aab2cb009"),"ArcanaBookTest"));
        player.setGameMode(GameType.SURVIVAL);player.setPos(0,81,0);player.setYRot(0);player.setXRot(0);
        MagicData data=MagicData.getPlayerMagicData(player);
        if(data.getSyncedData()==null)data.setSyncedData(new io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData(player));
        for(Spell spell:Spell.values()){
            var registered=SpellRegistry.REGISTRY.get(IronsCompat.id(spell));
            check.accept(registered instanceof IronsCompat.ArcanaSpell,"Iron spell registered: "+spell);
            check.accept(registered.isEnabled()&&registered.allowCrafting(),"Iron spell enabled and craftable: "+spell);
            check.accept(registered.getSchoolType()==io.redspace.ironsspellbooks.api.registry.SchoolRegistry.getSchool(IronsCompat.school(spell.theme))&&registered.getMaxLevel()==spell.levelCap(),"Iron school and level configured: "+spell+" school="+registered.getSchoolType()+" max="+registered.getMaxLevel()+" expected="+spell.levelCap()+" defaults="+registered.getDefaultConfig().maxLevel);
            ItemStack scroll=IronsCompat.scroll(spell);
            check.accept(ISpellContainer.get(scroll).getSpellAtIndex(0).getSpell().equals(registered),"Iron scroll contains spell: "+spell);
            ItemStack restored=ItemStack.parseOptional(level.registryAccess(),(CompoundTag)scroll.save(level.registryAccess()));
            check.accept(ISpellContainer.get(restored).getSpellAtIndex(0).getSpell().equals(registered),"Iron scroll survives serialization: "+spell);
            var book=ItemRegistry.IRON_SPELL_BOOK.get().getDefaultInstance();
            var contents=ISpellContainer.create(3,true,false).mutableCopy();
            check.accept(contents.addSpell(registered,1,false),"Iron book accepts spell: "+spell);
            ISpellContainer.set(book,contents.toImmutable());
            restored=ItemStack.parseOptional(level.registryAccess(),(CompoundTag)book.save(level.registryAccess()));
            check.accept(ISpellContainer.get(restored).getSpellAtIndex(0).getSpell().equals(registered),"Iron book saves spell: "+spell);
            String icon="/assets/"+registered.getSpellIconResource().getNamespace()+"/"+registered.getSpellIconResource().getPath();
            check.accept(IronsIntegrationChecks.class.getResource(icon)!=null,"Iron spell icon packaged: "+spell);
        }
        var spell=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.SPARK));
        SpellEngine.clear();data.getPlayerCooldowns().clearCooldowns();data.setMana(0);
        check.accept(!spell.canBeCastedBy(1,CastSource.SPELLBOOK,data,player).isSuccess(),"Iron mana gate rejects an empty mana pool");
        data.setMana(100);Zombie victim=target(level,6);
        check.accept(spell.checkPreCastConditions(level,1,player,data),"Iron preflight accepts a valid caster");
        check.accept(spell.canBeCastedBy(1,CastSource.SPELLBOOK,data,player).isSuccess(),"Iron mana gate permits a funded cast");
        spell.castSpell(level,1,player,CastSource.SPELLBOOK,true);
        check.accept(data.getMana()==100-spell.getManaCost(1),"Iron framework consumes the configured mana once (actual="+data.getMana()+", cost="+spell.getManaCost(1)+")");
        check.accept(data.getPlayerCooldowns().isOnCooldown(spell),"Iron framework applies its own spell cooldown");
        check.accept(!spell.canBeCastedBy(1,CastSource.SPELLBOOK,data,player).isSuccess(),"Iron cooldown gate rejects immediate repeats");
        for(int i=0;i<35;i++)SpellEngine.tick();
        float baseDamage=20-victim.getHealth();check.accept(baseDamage>0,"Iron spellbook cast runs the projectile gameplay");victim.discard();
        SpellEngine.clear();data.getPlayerCooldowns().clearCooldowns();data.setMana(100);
        var power=player.getAttribute(AttributeRegistry.SPELL_POWER);double oldPower=power.getBaseValue();
        power.setBaseValue(oldPower+1);victim=target(level,6);
        spell.castSpell(level,1,player,CastSource.SPELLBOOK,true);for(int i=0;i<35;i++)SpellEngine.tick();
        check.accept(20-victim.getHealth()>baseDamage*1.5,"Iron spell power increases actual projectile damage");
        power.setBaseValue(oldPower);victim.discard();
        SpellEngine.clear();data.getPlayerCooldowns().clearCooldowns();data.setMana(100);
        var cannon=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.SOUL_CANNON));victim=target(level,20);
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,ItemStack.EMPTY);
        check.accept(cannon.getCastType()==CastType.LONG&&cannon.getCastTime(1)==40,"Iron Soul Cannon uses a real two second cast bar");
        data.initiateCast(cannon,1,40,CastSource.SPELLBOOK,"mainhand");
        cannon.onServerPreCast(level,1,player,data);
        for(int t=0;t<39;t++){cannon.onServerCastTick(level,1,player,data);data.handleCastDuration();}
        check.accept(victim.getHealth()==20&&SpellEngine.activeCount()==0,"Iron charging does not launch or damage before completion");
        cannon.castSpell(level,1,player,CastSource.SPELLBOOK,true);SpellEngine.tick();
        check.accept(victim.getHealth()==20,"Iron cannon releases a travelling shot without a second charge");
        for(int i=0;i<30;i++)SpellEngine.tick();
        check.accept(victim.getHealth()<20&&SpellEngine.activeCount()==0,"Iron cannon works without holding a wand and completes cleanly");
        victim.discard();data.resetCastingState();SpellEngine.clear();
        data.getPlayerCooldowns().clearCooldowns();
        var targeted=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.EMPIRE_SPARK));
        check.accept(!targeted.checkPreCastConditions(level,1,player,data),"Iron targeted spells fail preflight when no target exists");
        check.accept(data.getMana()==100-cannon.getManaCost(1),"failed Iron preflight leaves mana unchanged");
        chargedWandChecks(level,check,player,data);
    }
    /** Uses Iron's real public tick loop, not a stand-in windup timer. */
    private static void chargedWandChecks(ServerLevel level,BiConsumer<Boolean,String> check,net.neoforged.neoforge.common.util.FakePlayer player,MagicData data){
        var manager=(io.redspace.ironsspellbooks.capabilities.magic.MagicManager)io.redspace.ironsspellbooks.api.magic.MagicHelper.MAGIC_MANAGER;
        var regen=player.getAttribute(AttributeRegistry.MANA_REGEN);double previousRegen=regen.getBaseValue();regen.setBaseValue(0);
        level.addNewPlayer(player);
        try{
            for(Spell selected:List.of(Spell.FIRE_COMET,Spell.SMITE,Spell.AMETHYST_NOVA,Spell.SINGULARITY,Spell.STORM_CALL,Spell.BLOOD_LANCE,Spell.SOLAR_CATACLYSM,Spell.TEMPEST_SPEAR,Spell.EVENT_HORIZON,Spell.DOOM,Spell.SANCTUARY,Spell.GLACIAL_RUPTURE,Spell.EVOKERS_EDICT,Spell.SONIC_BOOM)){
                SpellEngine.clear();data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();data.setMana(100);
                player.setPos(0,81,0);player.setYRot(0);player.setXRot(0);player.setHealth(selected==Spell.SANCTUARY?5:20);
                var wand=KingdomWands.ITEMS.getEntries().stream().map(e->(WandItem)e.get()).filter(w->w.spells.contains(selected)).findFirst().orElseThrow();
                ItemStack stack=wand.getDefaultInstance();wand.select(stack,wand.spells.indexOf(selected));
                player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,stack);Zombie victim=target(level,6);
                SpellEngine.action(player,0);
                var registered=SpellRegistry.REGISTRY.get(IronsCompat.id(selected));
                check.accept(data.isCasting()&&data.getCastDuration()>0,"wand uses Iron windup: "+selected);
                for(int t=0;t<registered.getEffectiveCastTime(1,player);t++){
                    manager.tick(level);SpellEngine.tick();
                    check.accept(victim.getHealth()==20,"no damage before native charge completes: "+selected);
                    if(selected==Spell.SANCTUARY)check.accept(player.getHealth()==5,"no healing before native charge completes");
                }
                check.accept(data.getMana()==100,"native windup does not charge mana early: "+selected);
                manager.tick(level);SpellEngine.tick();
                check.accept(data.getMana()==100-registered.getManaCost(1),"wand spends native mana exactly once: "+selected);
                for(int t=0;t<100;t++){manager.tick(level);SpellEngine.tick();}
                check.accept(selected==Spell.SANCTUARY?player.getHealth()>5&&victim.getHealth()==20:victim.getHealth()<20,"charged wand releases its gameplay: "+selected);
                check.accept(!data.isCasting()&&SpellEngine.activeCount()==0,"charged wand and effect clean up: "+selected);victim.discard();
            }
            for(Spell selected:List.of(Spell.SOLAR_CATACLYSM,Spell.TEMPEST_SPEAR,Spell.EVENT_HORIZON,Spell.DOOM,Spell.SANCTUARY,Spell.GLACIAL_RUPTURE,Spell.EVOKERS_EDICT,Spell.SONIC_BOOM)){
                SpellEngine.clear();data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();data.setMana(100);
                var registered=SpellRegistry.REGISTRY.get(IronsCompat.id(selected));
                Zombie untouched=target(level,6);
                check.accept(registered.attemptInitiateCast(ItemStack.EMPTY,1,level,player,CastSource.SPELLBOOK,false,"test"),"heavy spellbook windup begins: "+selected);
                for(int t=0;t<20;t++){manager.tick(level);SpellEngine.tick();}
                IronsCompat.cancel(player);
                for(int t=0;t<100;t++){manager.tick(level);SpellEngine.tick();}
                check.accept(untouched.getHealth()==20&&data.getMana()==100&&SpellEngine.activeCount()==0,"cancelled heavy spell has no residual damage or mana cost: "+selected);untouched.discard();
            }
            SpellEngine.clear();data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();data.setMana(100);
            ItemStack stack=KingdomWands.CELCRIUS.toStack();KingdomWands.CELCRIUS.get().select(stack,0);player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,stack);
            Zombie victim=target(level,6);SpellEngine.action(player,0);manager.tick(level);SpellEngine.tick();
            check.accept(data.isCasting(),"wand cancellation test is charging");
            SpellEngine.action(player,1);
            check.accept(!data.isCasting(),"cycling wand cancels native charge");
            for(int t=0;t<60;t++){manager.tick(level);SpellEngine.tick();}
            check.accept(victim.getHealth()==20&&data.getMana()==100,"cancelled charge does no damage and spends no mana");victim.discard();
            SpellEngine.clear();data.getPlayerCooldowns().clearCooldowns();data.setMana(0);
            KingdomWands.CELCRIUS.get().select(stack,0);SpellEngine.action(player,0);
            check.accept(!data.isCasting()&&SpellEngine.activeCount()==0,"wand cannot bypass Iron mana gate");
            for(Spell selected:Spell.values()){
                var registered=SpellRegistry.REGISTRY.get(IronsCompat.id(selected));
                check.accept(registered.getCastTime(1)==selected.castTicks(),"spell cast tier is applied: "+selected);
                check.accept(registered.getMaxLevel()==1||registered.getSpellPower(registered.getMaxLevel(),player)>registered.getSpellPower(1,player),"spell tiers increase power: "+selected);
            }
            check.accept(Spell.SPARK.castTicks()==0&&Spell.LEAP.castTicks()==0,"basic bolts and movement stay responsive");
            check.accept(Spell.SOUL_CANNON.damage==16&&Spell.SOUL_CANNON.range==48&&Spell.SOUL_CANNON.castTicks()==40,"Soul Cannon base behavior is preserved");
            check.accept(Spell.SMITE.damage==18&&Spell.SINGULARITY.damage==18&&Spell.AMETHYST_NOVA.damage==14,"heavy spells receive meaningful base damage");
        }finally{
            data.resetCastingState();SpellEngine.clear();regen.setBaseValue(previousRegen);
            level.removePlayerImmediately(player,net.minecraft.world.entity.Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        }
    }
    private static Zombie target(ServerLevel level,double z){
        Zombie zombie=new Zombie(EntityType.ZOMBIE,level);zombie.setPos(0,81,z);zombie.setNoAi(true);zombie.setNoGravity(true);level.addFreshEntity(zombie);return zombie;
    }
}
