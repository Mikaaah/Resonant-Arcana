package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;

/** Combat, optional recipes, native scroll data and safety contracts for the requested God tier. */
final class GodChecks {
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        var gods=Arrays.stream(Spell.values()).filter(GodSpells::handles).toList();
        check.accept(gods.size()==20,"twenty God spells cover schools and addon themes");
        check.accept(gods.stream().filter(s->s.definition.integration()==null).map(s->IronsCompat.school(s.theme)).distinct().count()==9,"each of the nine schools has a core God spell");
        check.accept(gods.stream().filter(CavesCompat::optional).count()==6&&gods.stream().filter(BossesRiseCompat::optional).count()==5,"God tier has one art for each cave biome and boss");
        for(Spell s:Spell.values())if(GodSpells.handles(s)||CavesCompat.optional(s)||BossesRiseCompat.optional(s)){
            ResourceLocation key=ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"scrolls/"+s.bookId());var recipe=level.getRecipeManager().byKey(key);
            check.accept(recipe.isPresent()==s.available(),"deterministic scroll recipe follows optional version gate: "+s);
            if(!s.available())continue;
            var registered=io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(s));check.accept(!registered.allowCrafting(),"generic scroll-forge crafting cannot bypass proof recipe: "+s);
            var output=recipe.orElseThrow().value().getResultItem(level.registryAccess());var spell=ISpellContainer.get(output);check.accept(spell!=null&&!spell.isEmpty()&&spell.getSpellAtIndex(0).getSpell().getSpellResource().equals(IronsCompat.id(s))&&spell.getSpellAtIndex(0).getLevel()==1,"recipe output preserves native spell component: "+s);
            var stacks=NonNullList.withSize(9,ItemStack.EMPTY);int i=0;for(var ingredient:recipe.orElseThrow().value().getIngredients()){check.accept(ingredient.getItems().length>0,"recipe has a registered material: "+s);stacks.set(i++,ingredient.getItems()[0].copy());}
            var input=CraftingInput.of(3,3,stacks);check.accept(((net.minecraft.world.item.crafting.CraftingRecipe)recipe.orElseThrow().value()).matches(input,level),"actual crafting grid accepts attunement materials: "+s);
            var selected=level.getRecipeManager().getRecipeFor(net.minecraft.world.item.crafting.RecipeType.CRAFTING,input,level);check.accept(selected.isPresent()&&selected.get().id().equals(key),"real recipe manager selects the intended scroll without ambiguity: "+s);
            stacks.set(0,ItemStack.EMPTY);check.accept(!((net.minecraft.world.item.crafting.CraftingRecipe)recipe.orElseThrow().value()).matches(CraftingInput.of(3,3,stacks),level),"attunement refuses missing consumed ingredient: "+s);
        }
        var caster=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("43ca91da-34fd-4218-9cad-aa2e435f8a8c"),"GodTest"));var team=level.getScoreboard().addPlayerTeam("god_test");team.setAllowFriendlyFire(false);level.getScoreboard().addPlayerToTeam(caster.getScoreboardName(),team);
        try{
            for(Spell s:gods){
                check.accept(s.castTicks()==200&&s.manaCost()==600&&s.levelCap()==1,"God tier has ten-second 600-mana single-rank gate: "+s);if(!s.available())continue;
                SpellEngine.clear();caster.setPos(0,81,0);caster.setYRot(0);caster.setXRot(0);caster.removeAllEffects();caster.setHealth(10);
                var enemy=enemy(level,0,6);var ally=enemy(level,2,6);var outside=enemy(level,s.radius+.5,6);level.getScoreboard().addPlayerToTeam(ally.getScoreboardName(),team);
                try{check.accept(SpellEngine.cast(caster,s),"God release starts: "+s);for(int t=0;t<s.duration()+10;t++){if(enemy.invulnerableTime>0)enemy.invulnerableTime--;SpellEngine.tick();}
                    check.accept(enemy.getHealth()<1024,"God release executes its combat pattern: "+s);check.accept(ally.getHealth()==1024,"God art protects teammates: "+s);check.accept(outside.getHealth()==1024,"God damage stays inside advertised field: "+s);
                    check.accept(SpellEngine.activeCount()==0,"God release is finite: "+s);check.accept(level.getBlockState(new BlockPos(0,80,6)).is(Blocks.STONE),"God art preserves terrain: "+s);
                    if(CavesCompat.available())check.accept(level.getEntitiesOfClass(CaveVisual.class,caster.getBoundingBox().inflate(96)).isEmpty(),"God cave proxies clean up: "+s);
                }finally{enemy.discard();ally.discard();outside.discard();SpellEngine.clear();}
            }
            check.accept(BeamRitual.origin(caster).y<caster.getEyeY()-.5&&BeamRitual.origin(caster).y>caster.getY()+.5,"all shared beam muzzles are at torso level below the face");
            if(CavesCompat.available()){
                caster.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND,net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse("alexscaves:amber_curiosity")).getDefaultInstance());check.accept(CavesCompat.beamVariant(caster)==1,"Amber Curiosity selects native purple retro beam");caster.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND,ItemStack.EMPTY);
                var enemy=enemy(level,0,6);try{SpellEngine.clear();check.accept(SpellEngine.cast(caster,Spell.TREMORZILLA_BREATH),"torso beam fixture starts");check.accept(level.getEntitiesOfClass(CaveVisual.class,caster.getBoundingBox().inflate(64)).stream().noneMatch(e->e.model()==3),"Tremorzilla beam does not summon a miniature boss");}finally{enemy.discard();SpellEngine.clear();}
            }
            var loop=new WandNetwork.Loop(1,Spell.DOOM.ordinal(),true,false,0);var buf=new net.minecraft.network.RegistryFriendlyByteBuf(io.netty.buffer.Unpooled.buffer(),level.registryAccess());try{WandNetwork.Loop.CODEC.encode(buf,loop);check.accept(loop.equals(WandNetwork.Loop.CODEC.decode(buf)),"owned sound lease packet round trips");check.accept(loop.valid()&&!new WandNetwork.Loop(-1,Spell.DOOM.ordinal(),true,false,0).valid()&&!new WandNetwork.Loop(1,Spell.DOOM.ordinal(),true,false,9).valid(),"sound leases reject invalid owners and variants");}finally{buf.release();}
        }finally{caster.removeAllEffects();level.getScoreboard().removePlayerTeam(team);SpellEngine.clear();}
    }
    private static Zombie enemy(ServerLevel level,double x,double z){var e=new Zombie(EntityType.ZOMBIE,level);e.setNoAi(true);e.setNoGravity(true);e.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);e.getAttribute(Attributes.ARMOR).setBaseValue(0);e.setHealth(1024);e.setPos(x,81,z);level.addFreshEntity(e);return e;}
}
