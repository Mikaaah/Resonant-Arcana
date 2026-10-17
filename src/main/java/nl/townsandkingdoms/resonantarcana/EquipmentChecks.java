package nl.townsandkingdoms.resonantarcana;

import java.util.UUID;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import top.theillusivec4.curios.api.SlotContext;

final class EquipmentChecks {
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        var p=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("e82df465-df42-45b6-89d7-83d8329ac9d4"),"ArcanaEquipment"));
        p.removeAllEffects();p.setPos(0,81,0);p.setYRot(0);p.setXRot(0);
        for(var entry:ResonantArcana.ITEMS.getEntries())if(entry.get() instanceof LegendarySpellBook book){
            var stack=book.getDefaultInstance();book.initializeSpellContainer(stack);var container=ISpellContainer.get(stack);
            check.accept(container.getMaxSpellCount()==(book.style==LegendarySpellBook.Style.RUNIC?14:12),entry.getId()+" has native spell slots");
            var mutable=container.mutableCopy();
            check.accept(mutable.addSpell(SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.SPARK)),1,false),"book accepts addon inscription");
            check.accept(mutable.addSpell(SpellRegistry.REGISTRY.get(ResourceLocation.parse("irons_spellbooks:fireball")),1,false),"book accepts native Iron's inscription");
            ISpellContainer.set(stack,mutable.toImmutable());
            var context=new SlotContext("spellbook",p,0,false,true);
            var modifiers=book.getAttributeModifiers(context,entry.getId(),stack);
            check.accept(modifiers.size()==4,entry.getId()+" provides four native equipment perks");
            double before=p.getAttributeValue(AttributeRegistry.MAX_MANA);
            modifiers.forEach((attribute,modifier)->p.getAttribute(attribute).addTransientModifier(modifier));
            double mana=book.style==LegendarySpellBook.Style.RUNIC?450:book.style==LegendarySpellBook.Style.PHOENIX?400:425;
            check.accept(Math.abs(p.getAttributeValue(AttributeRegistry.MAX_MANA)-before-mana)<.001,"equipped book grants its flat mana pool");
            var school=book.style==LegendarySpellBook.Style.RUNIC?AttributeRegistry.EVOCATION_SPELL_POWER:book.style==LegendarySpellBook.Style.PHOENIX?AttributeRegistry.FIRE_SPELL_POWER:AttributeRegistry.ICE_SPELL_POWER;
            check.accept(p.getAttributeValue(school)>1,"equipped book grants school power");
            modifiers.forEach((attribute,modifier)->p.getAttribute(attribute).removeModifier(modifier.id()));
            check.accept(Math.abs(p.getAttributeValue(AttributeRegistry.MAX_MANA)-before)<.001&&Math.abs(p.getAttributeValue(school)-1)<.001,"unequipping removes book perks");
            check.accept(stack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM,ResourceLocation.parse("curios:spellbook"))),"book has native Curios slot tag");
        }
        for(String retired:new String[]{"arcane_wand","blood_wand","cinder_wand","dark_wand","thunder_wand","rift_wand","soul_wand","venom_wand","holy_wand","frost_wand"})
            check.accept(!BuiltInRegistries.ITEM.containsKey(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,retired)),"retired wand is not registered: "+retired);
        SpellEngine.clear();p.setHealth(10);Zombie enemy=new Zombie(EntityType.ZOMBIE,level);enemy.setPos(0,81,6);enemy.setNoAi(true);level.addFreshEntity(enemy);
        var otherFlight=new net.minecraft.world.entity.ai.attributes.AttributeModifier(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"test_other_flight"),1,net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE);
        var flight=p.getAttribute(net.neoforged.neoforge.common.NeoForgeMod.CREATIVE_FLIGHT);flight.addTransientModifier(otherFlight);
        check.accept(SpellEngine.cast(p,Spell.SERAPH_ASCENDANT),"Seraph blessing releases");SpellEngine.tick();
        check.accept(p.getAttributeValue(net.neoforged.neoforge.common.NeoForgeMod.CREATIVE_FLIGHT)>0,"Seraph grants native flight permission");
        check.accept(p.getHealth()>10&&p.hasEffect(ArcanaEffects.SERAPH_GRACE),"Seraph heals and empowers the caster");
        SpellEngine.clear();check.accept(p.getAttributeValue(net.neoforged.neoforge.common.NeoForgeMod.CREATIVE_FLIGHT)>0,"Seraph cleanup preserves another flight source");flight.removeModifier(otherFlight.id());check.accept(flight.getValue()==0,"Seraph leaves no flight modifier after cleanup");enemy.discard();
        SpellEngine.clear();check.accept(SpellEngine.cast(p,Spell.FINAL_JUDGEMENT),"holy sword releases");
        var swords=level.getEntitiesOfClass(SpellCentrepieces.HolySword.class,p.getBoundingBox().inflate(64));
        check.accept(swords.size()==1,"Final Judgement summons an actual sword display");double high=swords.getFirst().getY();for(int i=0;i<60;i++)SpellEngine.tick();
        check.accept(swords.getFirst().getY()<high,"holy sword descends toward its impact");SpellEngine.clear();check.accept(swords.getFirst().isRemoved(),"holy sword cleans up");
        p.removeAllEffects();
    }
}
