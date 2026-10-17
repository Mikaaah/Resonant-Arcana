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
        SpellEngine.clear();check.accept(p.getAttributeValue(net.neoforged.neoforge.common.NeoForgeMod.CREATIVE_FLIGHT)>0,"Seraph cleanup preserves another flight source");flight.removeModifier(otherFlight.id());check.accept(flight.getValue()==0,"Seraph leaves no flight modifier after cleanup");
        SpellEngine.clear();check.accept(SpellEngine.cast(p,Spell.FINAL_JUDGEMENT),"holy sword releases");
        var swords=level.getEntitiesOfClass(SpellCentrepieces.HolySword.class,p.getBoundingBox().inflate(64));
        check.accept(swords.size()==1,"Final Judgement summons an actual sword display");double high=swords.getFirst().getY();for(int i=0;i<60;i++)SpellEngine.tick();
        var sword=swords.getFirst();check.accept(sword.getY()<high,"holy sword descends toward its impact");
        var tag=sword.saveWithoutId(new net.minecraft.nbt.CompoundTag());
        var transform=com.mojang.math.Transformation.EXTENDED_CODEC.parse(net.minecraft.nbt.NbtOps.INSTANCE,tag.get("transformation")).getOrThrow();
        // Golden sword's upper-right blade tip, after ItemDisplayRenderer's mandatory Y flip.
        var tip=transform.getMatrix().transformPosition(new org.joml.Vector3f(-.4375f,.4375f,0));
        check.accept(transform.getScale().x<=4&&Math.abs(tip.x)<.01&&tip.y<0,"holy sword has reduced scale and points its blade down after the renderer flip");
        for(int i=60;i<96;i++)SpellEngine.tick();
        var floor=BossesRiseCompat.ground(level,sword.position());
        check.accept(floor!=null&&Math.abs(sword.getY()+tip.y-floor.y)<.25,"holy sword tip reaches the ground before the final hit");
        SpellEngine.clear();check.accept(sword.isRemoved(),"holy sword cleans up");enemy.discard();
        p.removeAllEffects();
        explosionLock(level,p,check);
    }
    private static void explosionLock(ServerLevel level,net.minecraft.server.level.ServerPlayer p,BiConsumer<Boolean,String> check){
        var data=io.redspace.ironsspellbooks.api.magic.MagicData.getPlayerMagicData(p);
        var spell=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.EXPLOSION));
        var first=new Zombie(EntityType.ZOMBIE,level);var second=new Zombie(EntityType.ZOMBIE,level);
        first.setPos(0,81,24);second.setPos(24,81,0);
        for(var v:java.util.List.of(first,second)){v.setNoAi(true);v.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);v.getAttribute(Attributes.ARMOR).setBaseValue(0);v.setHealth(1024);level.addFreshEntity(v);}
        try{
            SpellEngine.clear();p.setPos(0,81,0);p.setYRot(0);p.setXRot(0);data.resetCastingState();data.setMana(1000);data.getPlayerCooldowns().clearCooldowns();
            data.initiateCast(spell,1,spell.getEffectiveCastTime(1,p),io.redspace.ironsspellbooks.api.spells.CastSource.SPELLBOOK,"mainhand");spell.onServerPreCast(level,1,p,data);
            check.accept(spell.getEffectiveCastTime(1,p)==420,"Explosion base windup uses the full twenty-one-second chant");
            check.accept(Math.abs(ExplosionRitual.chantPitch(420)*420-419)<.001,"chant pitch aligns the source recording to the native cast duration");
            var attribute=p.getAttribute(AttributeRegistry.CAST_TIME_REDUCTION);double old=attribute.getBaseValue();
            try{attribute.setBaseValue(old+.25);int accelerated=spell.getEffectiveCastTime(1,p);
                check.accept(accelerated<420&&Math.abs(ExplosionRitual.chantPitch(accelerated)*accelerated-419)<.01,"native cast reduction keeps the chant aligned");
                attribute.setBaseValue(old+4);check.accept(spell.getEffectiveCastTime(1,p)>=210,"extreme casting speed stays inside the supported audio pitch range");
            }finally{attribute.setBaseValue(old);}
            for(String id:java.util.List.of("explosion_chant","explosion_blast"))
                check.accept(EquipmentChecks.class.getResource("/assets/resonant_arcana/sounds/"+id+".ogg")!=null,"recorded audio is packaged: "+id);
            var locked=ExplosionRitual.locked(p);
            check.accept(locked!=null&&locked.point().distanceToSqr(first.getBoundingBox().getCenter())<.1,"Explosion marks its initial aimed enemy");
            check.accept(locked.top().y>locked.point().y+5&&locked.top().subtract(locked.point()).horizontalDistance()>2,"Explosion beacon is elevated and diagonal");
            p.setYRot(-90);
            check.accept(ExplosionRitual.locked(p)==locked,"turning during the charge preserves the original target");
            spell.castSpell(level,1,p,io.redspace.ironsspellbooks.api.spells.CastSource.SPELLBOOK,true);
            check.accept(first.getHealth()==1024,"Explosion does not damage before its release tick");
            SpellEngine.tick();check.accept(first.getHealth()==768,"Explosion detonates on the first release tick, aligned with the blast recording");
            for(int i=1;i<32;i++){first.invulnerableTime=0;second.invulnerableTime=0;SpellEngine.tick();}
            check.accept(first.getHealth()==768&&second.getHealth()==1024,"Explosion detonates the original mark after looking elsewhere");
            data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();SpellEngine.clear();
            data.initiateCast(spell,1,spell.getEffectiveCastTime(1,p),io.redspace.ironsspellbooks.api.spells.CastSource.SPELLBOOK,"mainhand");spell.onServerPreCast(level,1,p,data);
            check.accept(ExplosionRitual.locked(p).point().distanceToSqr(second.getBoundingBox().getCenter())<.1,"a new cast acquires a fresh target");
            IronsCompat.cancel(p);
            check.accept(!data.isCasting()&&ExplosionRitual.locked(p)==null,"cancelled Explosion clears its target snapshot");
            for(int i=0;i<40;i++)SpellEngine.tick();check.accept(second.getHealth()==1024,"cancelled Explosion cannot detonate");
        }finally{first.discard();second.discard();data.resetCastingState();data.getPlayerCooldowns().clearCooldowns();SpellEngine.clear();p.removeAllEffects();p.setYRot(0);}
    }
}
