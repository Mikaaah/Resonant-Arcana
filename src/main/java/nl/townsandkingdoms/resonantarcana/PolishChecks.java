package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

/** Presentation contracts, actual boundaries, clipped muzzles and the new support domain. */
final class PolishChecks {
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        for(Spell spell:Spell.enabledValues()){
            var tag=net.minecraft.tags.TagKey.create(io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY,net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"categories/"+spell.category().name().toLowerCase(Locale.ROOT)));
            check.accept(io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.getHolder(IronsCompat.id(spell)).orElseThrow().is(tag),"native spell registry category tag loads: "+spell);
            check.accept(SpellInfo.overview(spell,spell.power(1),spell.castTicks()/20.0).stream().allMatch(line->line.getString().length()<=64),"bounded readable tooltip lines: "+spell);
            check.accept(!SpellIndex.tags(spell).isEmpty()&&SpellIndex.matches(spell,SpellInfo.school(spell.theme)),"school keywords resolve: "+spell);
        }
        check.accept(SpellIndex.matches(Spell.VOID_DOMAIN,"ender buff aoe")&&!SpellIndex.matches(Spell.VOID_DOMAIN,"fire"),"archive search combines school role and area keywords");
        check.accept(SpellIndex.ordered().size()==Spell.enabledValues().length&&new HashSet<>(SpellIndex.ordered()).size()==Spell.enabledValues().length,"creative order contains every enabled spell once");
        var caster=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("ca925e5f-16dd-45c4-b080-b1d02bb544db"),"PolishTest"));caster.setPos(0,81,0);caster.setYRot(0);caster.setXRot(0);caster.removeAllEffects();
        var scroll=IronsCompat.scroll(Spell.VOID_DOMAIN);var lines=scroll.getTooltipLines(net.minecraft.world.item.Item.TooltipContext.of(level),caster,net.minecraft.world.item.TooltipFlag.NORMAL);
        check.accept(lines.stream().anyMatch(line->line.getString().contains("Keywords:")),"normal scroll tooltip includes searchable spell keywords without Shift");
        check.accept(BeamRitual.origin(caster).distanceToSqr(BeamRitual.body(caster,1).add(caster.getLookAngle().scale(BeamRitual.FORWARD)))<.001,"beam muzzle stays 0.18 blocks in front of the hands");
        level.setBlock(new BlockPos(0,81,0),Blocks.STONE.defaultBlockState(),3);
        try{check.accept(BeamRitual.origin(caster).distanceToSqr(BeamRitual.body(caster,1))<BeamRitual.FORWARD*BeamRitual.FORWARD,"nearby solid cover clips muzzle offset");}finally{level.setBlock(new BlockPos(0,81,0),Blocks.AIR.defaultBlockState(),3);}
        SpellEngine.clear();var team=level.getScoreboard().addPlayerTeam("domain_test");team.setAllowFriendlyFire(false);level.getScoreboard().addPlayerToTeam(caster.getScoreboardName(),team);
        Zombie enemy=enemy(level,0,6),outside=enemy(level,18,6),covered=enemy(level,0,10);
        var friend=new net.minecraft.world.entity.animal.Cow(EntityType.COW,level);friend.setNoAi(true);friend.setPos(2,81,6);level.addFreshEntity(friend);
        level.getScoreboard().addPlayerToTeam(friend.getScoreboardName(),team);
        for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlock(new BlockPos(x,y,8),Blocks.STONE.defaultBlockState(),3);
        try{
            check.accept(SpellEngine.cast(caster,Spell.VOID_DOMAIN),"Void Domain starts");SpellEngine.tick();
            check.accept(enemy.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)&&enemy.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier()==2&&enemy.getEffect(MobEffects.WEAKNESS).getAmplifier()==1&&enemy.hasEffect(MobEffects.DARKNESS),"domain suppresses enemies with Slowness III Weakness II and Darkness");
            check.accept(friend.hasEffect(ArcanaEffects.VOID_EMPOWERMENT)&&friend.hasEffect(MobEffects.REGENERATION)&&friend.hasEffect(MobEffects.DAMAGE_RESISTANCE)&&friend.hasEffect(MobEffects.MOVEMENT_SPEED)&&friend.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier()==1&&friend.getEffect(MobEffects.REGENERATION).getAmplifier()==1&&friend.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier()==1,"domain grants allied spell power Resistance II Regeneration II and Speed II");
            check.accept(!friend.hasEffect(MobEffects.WEAKNESS)&&!enemy.hasEffect(ArcanaEffects.VOID_EMPOWERMENT),"ally and enemy domain effects are mutually exclusive");
            check.accept(caster.hasEffect(ArcanaEffects.VOID_EMPOWERMENT)&&!outside.hasEffect(MobEffects.WEAKNESS)&&!covered.hasEffect(MobEffects.WEAKNESS),"domain includes caster and respects range and solid cover");
            caster.setPos(40,81,0);var moved=enemy(level,40,6);try{for(int i=0;i<12;i++)SpellEngine.tick();check.accept(!moved.hasEffect(MobEffects.WEAKNESS),"domain remains fixed at casting location when caster moves");}finally{moved.discard();}
            friend.setPos(20,81,0);friend.removeAllEffects();for(int i=0;i<12;i++)SpellEngine.tick();check.accept(!friend.hasEffect(ArcanaEffects.VOID_EMPOWERMENT),"leaving domain stops buff refresh");
            for(int i=0;i<320;i++)SpellEngine.tick();enemy.removeAllEffects();friend.setPos(2,81,6);for(int i=0;i<12;i++)SpellEngine.tick();
            check.accept(SpellEngine.activeCount()==0&&!enemy.hasEffect(MobEffects.WEAKNESS)&&!friend.hasEffect(ArcanaEffects.VOID_EMPOWERMENT),"domain expires and stops all effect refresh");
        }finally{
            for(var e:List.of(enemy,friend,outside,covered))e.discard();caster.removeAllEffects();level.getScoreboard().removePlayerTeam(team);SpellEngine.clear();
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlock(new BlockPos(x,y,8),Blocks.AIR.defaultBlockState(),3);
        }
        caster.setPos(0,81,0);caster.setYRot(0);caster.setXRot(0);
        for(Spell spell:List.of(Spell.FLAME_THROWER,Spell.ARCANE_VOLLEY)){
            SpellEngine.clear();var victim=enemy(level,0,6);victim.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(200);victim.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).setBaseValue(0);victim.setHealth(200);
            try{
                check.accept(SpellEngine.cast(caster,spell),"cadenced multi-hit cast starts: "+spell);
                // Age vanilla's immunity window, rather than clearing it before each damage pulse.
                for(int i=0;i<90;i++){if(victim.invulnerableTime>0)victim.invulnerableTime--;SpellEngine.tick();}
                float expected=spell==Spell.FLAME_THROWER?24:15;
                check.accept(Math.abs(200-victim.getHealth()-expected)<.01,"all advertised pulses survive normal damage immunity: "+spell);
            }finally{victim.discard();SpellEngine.clear();}
        }
    }
    private static Zombie enemy(ServerLevel level,double x,double z){var entity=new Zombie(EntityType.ZOMBIE,level);entity.setNoAi(true);entity.setPos(x,81,z);level.addFreshEntity(entity);return entity;}
}
