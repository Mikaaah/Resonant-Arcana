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
            check.accept(registered.getSchoolType()!=null&&registered.getMaxLevel()==1,"Iron school and level configured: "+spell);
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
    }
    private static Zombie target(ServerLevel level,double z){
        Zombie zombie=new Zombie(EntityType.ZOMBIE,level);zombie.setPos(0,81,z);zombie.setNoAi(true);zombie.setNoGravity(true);level.addFreshEntity(zombie);return zombie;
    }
}
