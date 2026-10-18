package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

/** Runtime fixtures exercise optional registration, actual hit schedules, cover and cleanup. */
final class CavesChecks {
    static final String[] PARTICLES={"scarlet_magnetic_flow","azure_magnetic_flow","magnet_lightning","amber_explosion","luxtructosaurus_ash","tephra_small","acid_bubble","acid_drop","proton","tremorzilla_explosion","nuclear_siren_sonar","mushroom_cloud","deep_one_magic","water_foam","big_splash","underzealot_magic","underzealot_explosion","forsaken_sonar","forsaken_sonar_large","sugar_flake","purple_soda_fizz","frostmint_explosion","purple_witch_explosion"};
    private static ResourceLocation id(String name){return ResourceLocation.fromNamespaceAndPath(CavesCompat.MOD,name);}
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        var spells=Arrays.stream(Spell.values()).filter(CavesCompat::optional).toList();check.accept(spells.size()==18,"eighteen optional cave arts exist");
        var areas=new HashMap<String,Set<String>>();for(var s:spells){areas.computeIfAbsent(s.definition.area(),k->new HashSet<>()).add(s.definition.tier());check.accept(s.available()==CavesCompat.available(),"optional cave availability: "+s);}
        check.accept(areas.size()==6&&areas.values().stream().allMatch(t->t.equals(Set.of("NOVICE","MASTER","CATACLYSM"))),"six cave areas each have novice master and cataclysm arts");
        if(!CavesCompat.available()){
            check.accept(!BuiltInRegistries.ENTITY_TYPE.containsKey(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"cave_spell_visual")),"without caves no visual type is registered");
            check.accept(spells.stream().noneMatch(s->io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.containsKey(IronsCompat.id(s))),"without caves no optional native spell is registered");return;
        }
        for(String name:PARTICLES)check.accept(BuiltInRegistries.PARTICLE_TYPE.containsKey(id(name)),"native cave particle exists: "+name);
        for(String name:CaveVisual.MODELS)check.accept(BuiltInRegistries.ENTITY_TYPE.containsKey(id(name)),"native cave model exists: "+name);
        for(String name:List.of("stunned","bubbled","sugar_rush"))check.accept(BuiltInRegistries.MOB_EFFECT.containsKey(id(name)),"native cave effect exists: "+name);
        var caster=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("6f58a183-0f46-40ab-bd0a-3029371761df"),"CavesTest"));
        var team=level.getScoreboard().addPlayerTeam("caves_test");team.setAllowFriendlyFire(false);level.getScoreboard().addPlayerToTeam(caster.getScoreboardName(),team);
        try{
            for(Spell s:spells){
                SpellEngine.clear();caster.removeAllEffects();caster.setPos(0,81,0);caster.setYRot(0);caster.setXRot(0);
                Zombie victim=enemy(level,0,6),ally=enemy(level,1,6);level.getScoreboard().addPlayerToTeam(ally.getScoreboardName(),team);
                try{
                    check.accept(SpellEngine.cast(caster,s),"cave release starts: "+s);
                    for(int t=0;t<s.duration()+8;t++){if(victim.invulnerableTime>0)victim.invulnerableTime--;SpellEngine.tick();}
                    float expected=switch(s){case TECTONIC_STAMPEDE,GUMBALL_VOLLEY,FORSAKEN_SONAR->s.damage*3;case FROSTMINT_HEX->s.damage*5;case LUX_EXTINCTION,ABYSSAL_DELUGE,CANDY_CATASTROPHE->s.damage*6;case FORLORN_RECKONING->s.damage*5;default->s.damage;};
                    check.accept(Math.abs(1024-victim.getHealth()-expected)<.1,"cave authored damage with normal immunity: "+s+" expected "+expected+" got "+(1024-victim.getHealth()));
                    check.accept(ally.getHealth()==1024&&!ally.hasEffect(net.minecraft.world.effect.MobEffects.WEAKNESS),"cave attacks protect teammate: "+s);
                    check.accept(SpellEngine.activeCount()==0&&level.getEntitiesOfClass(CaveVisual.class,caster.getBoundingBox().inflate(96)).isEmpty(),"cave art and markers expire: "+s);
                    check.accept(level.getBlockState(new BlockPos(0,80,6)).is(Blocks.STONE),"cave art preserves terrain: "+s);
                }finally{victim.discard();ally.discard();SpellEngine.clear();}
            }
            for(Spell s:List.of(Spell.POLARITY_BOLT,Spell.AMBER_TALON,Spell.ACID_GLOB,Spell.BRINE_BUBBLE,Spell.DESOLATE_DAGGER,Spell.GUMBALL_VOLLEY,Spell.TREMORZILLA_BREATH,Spell.MAGNETAR_ANNIHILATION)){
                SpellEngine.clear();caster.setPos(0,81,0);caster.setYRot(0);caster.setXRot(0);var victim=enemy(level,0,8);
                for(int x=-4;x<=4;x++)for(int y=81;y<=85;y++)level.setBlock(new BlockPos(x,y,4),Blocks.STONE.defaultBlockState(),3);
                try{check.accept(SpellEngine.cast(caster,s),"covered cave release starts: "+s);for(int i=0;i<s.duration()+2;i++)SpellEngine.tick();check.accept(victim.getHealth()==1024,"solid cover blocks cave attack: "+s);}
                finally{victim.discard();SpellEngine.clear();for(int x=-4;x<=4;x++)for(int y=81;y<=85;y++)level.setBlock(new BlockPos(x,y,4),Blocks.AIR.defaultBlockState(),3);}
            }
            SpellEngine.clear();var hostile=enemy(level,0,6);var hostileArrow=new Arrow(level,caster,new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.ARROW),null);hostileArrow.setOwner(hostile);hostileArrow.setPos(0,82,3);level.addFreshEntity(hostileArrow);
            var friendlyArrow=new Arrow(level,caster,new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.ARROW),null);friendlyArrow.setPos(1,82,3);level.addFreshEntity(friendlyArrow);
            try{check.accept(SpellEngine.cast(caster,Spell.EMP_SURGE),"EMP fixture starts");SpellEngine.tick();check.accept(hostile.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(BuiltInRegistries.MOB_EFFECT.get(id("stunned")))),"EMP applies native stun");check.accept(hostileArrow.isRemoved()&&!friendlyArrow.isRemoved(),"EMP removes hostile projectile and protects allied projectile");}
            finally{hostile.discard();hostileArrow.discard();friendlyArrow.discard();SpellEngine.clear();}
            for(Spell s:List.of(Spell.TREMORZILLA_BREATH,Spell.MAGNETAR_ANNIHILATION)){
                var first=enemy(level,0,6);var second=enemy(level,10,0);caster.setYRot(0);SpellEngine.clear();
                try{check.accept(SpellEngine.cast(caster,s),"following cave beam starts: "+s);SpellEngine.tick();caster.setYRot(-90);for(int i=0;i<8;i++)SpellEngine.tick();check.accept(first.getHealth()<1024&&second.getHealth()<1024,"cave beam follows caster aim and hits late entrant: "+s);float health=second.getHealth();for(int i=0;i<30;i++){second.invulnerableTime=0;SpellEngine.tick();}check.accept(second.getHealth()==health,"cave beam hits each enemy once: "+s);}
                finally{first.discard();second.discard();SpellEngine.clear();caster.setYRot(0);}
            }
            // In a cave, a visual warhead must begin below the actual ceiling.
            level.setBlock(new BlockPos(0,88,6),Blocks.STONE.defaultBlockState(),3);var ceilingTarget=enemy(level,0,6);
            try{SpellEngine.clear();check.accept(SpellEngine.cast(caster,Spell.NUCLEAR_STRIKE),"nuclear strike fits cave headroom");
                var bombs=level.getEntitiesOfClass(CaveVisual.class,caster.getBoundingBox().inflate(32),e->e.model()==6);check.accept(bombs.size()==1&&bombs.getFirst().getY()<88,"visual warhead spawns below cave ceiling");}
            finally{ceilingTarget.discard();SpellEngine.clear();level.setBlock(new BlockPos(0,88,6),Blocks.AIR.defaultBlockState(),3);}
            var victim=enemy(level,0,6);try{SpellEngine.clear();check.accept(SpellEngine.cast(caster,Spell.NUCLEAR_STRIKE),"nuclear warning fixture starts");for(int i=0;i<39;i++)SpellEngine.tick();check.accept(victim.getHealth()==1024,"nuclear strike gives forty tick warning before damage");SpellEngine.tick();check.accept(victim.getHealth()==640,"nuclear strike releases one bounded blast");}finally{victim.discard();SpellEngine.clear();}
        }finally{caster.removeAllEffects();level.getScoreboard().removePlayerTeam(team);SpellEngine.clear();}
    }
    private static Zombie enemy(ServerLevel level,double x,double z){var e=new Zombie(EntityType.ZOMBIE,level);e.setNoAi(true);e.setNoGravity(true);e.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);e.getAttribute(Attributes.ARMOR).setBaseValue(0);e.setHealth(1024);e.setPos(x,81,z);level.addFreshEntity(e);return e;}
}
