package nl.townsandkingdoms.kingdomwands;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

/** Explicitly gated developer fixture: starts and stops only with -Dkingdomwands.integrationTest=true. */
public final class IntegrationChecks {
    private static final List<String> checks=new ArrayList<>();
    private IntegrationChecks() {}
    public static boolean enabled(){return Boolean.getBoolean("kingdomwands.integrationTest");}
    public static void start(ServerStartedEvent event){if(enabled())event.getServer().execute(()->run(event.getServer()));}
    private static void check(boolean condition,String name){if(!condition)throw new IllegalStateException(name);checks.add(name);}
    private static void run(MinecraftServer server){
        Map<String,Object> report=new LinkedHashMap<>();List<Map<String,Object>> results=new ArrayList<>();
        try {
            ServerLevel level=server.overworld();
            for(int x=-1;x<=1;x++)for(int z=-1;z<=2;z++)level.getChunk(x,z);
            for(int x=-12;x<=12;x++)for(int z=-8;z<=24;z++){
                level.setBlockAndUpdate(new BlockPos(x,80,z),Blocks.STONE.defaultBlockState());
                for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,z),Blocks.AIR.defaultBlockState());
            }
            FakePlayer player=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("6341cb70-4863-4d65-a614-1fd6719c50b8"),"WandTest"));
            player.setGameMode(GameType.SURVIVAL);resetPlayer(player);
            WandItem wand=KingdomWands.EMPIRE.get();ItemStack stack=wand.getDefaultInstance();
            wand.select(stack,-1);check(wand.selection(stack)==wand.spells.size()-1,"previous selection wraps");
            var custom=stack.get(DataComponents.CUSTOM_DATA).copyTag();custom.putString("external","preserved");stack.set(DataComponents.CUSTOM_DATA,CustomData.of(custom));
            wand.select(stack,2);check(stack.get(DataComponents.CUSTOM_DATA).copyTag().getString("external").equals("preserved"),"selection preserves unrelated custom data");
            ItemStack restored=ItemStack.parseOptional(server.registryAccess(),(CompoundTag)stack.save(server.registryAccess()));
            check(wand.selected(restored)==Spell.BLOOD_SPARK,"selected spell survives item serialization");
            RegistryFriendlyByteBuf buffer=new RegistryFriendlyByteBuf(Unpooled.buffer(),server.registryAccess());
            try {
                var a=new WandNetwork.Action(2);WandNetwork.Action.CODEC.encode(buffer,a);check(WandNetwork.Action.CODEC.decode(buffer).equals(a),"action packet codec round trip");
                buffer.clear();var b=new WandNetwork.Burst(1,82,-3,0xDE1342);WandNetwork.Burst.CODEC.encode(buffer,b);check(WandNetwork.Burst.CODEC.decode(buffer).equals(b),"burst packet codec round trip");
            }finally{buffer.release();}
            check(KingdomWands.ITEMS.getEntries().size()==7,"seven registered wands");
            for(var item:KingdomWands.ITEMS.getEntries()){
                WandItem w=(WandItem)item.get();ItemStack ws=w.getDefaultInstance();
                for(int i=0;i<w.spells.size();i++){w.select(ws,i);check(w.selected(ws)==w.spells.get(i),item.getId()+" selection "+i);}
            }
            for(Spell.Theme theme:Spell.Theme.values())for(String id:List.of(SpellAudio.castId(theme),SpellAudio.impactId(theme),SpellAudio.sustainId(theme)))
                check(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.containsKey(net.minecraft.resources.ResourceLocation.withDefaultNamespace(id)),"sound exists: "+id);
            for(String id:List.of(SpellAudio.spellCastId(Spell.SONIC_BOOM),SpellAudio.spellCastId(Spell.SHRIEKING_PULSE),SpellAudio.spellCastId(Spell.SOUL_PRISON),"entity.warden.sonic_boom"))
                check(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.containsKey(net.minecraft.resources.ResourceLocation.withDefaultNamespace(id)),"special soul sound exists: "+id);
            for(Spell spell:Spell.values()){
                SpellEngine.clear();resetPlayer(player);
                player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,KingdomWands.EMPIRE.toStack());
                Zombie zombie=spawn(level,0,81,6);Arrow arrow=null;
                if(spell.kind==Spell.Kind.WARD){arrow=new Arrow(EntityType.ARROW,level);arrow.setOwner(zombie);arrow.setPos(0,82,2);arrow.setDeltaMovement(0,0,-0.8);level.addFreshEntity(arrow);}
                check(SpellEngine.cast(player,spell),spell.name()+" starts");
                check(!SpellEngine.cast(player,spell),spell.name()+" cooldown rejects immediate repeat");
                for(int t=0;t<100;t++)SpellEngine.tick();
                boolean observed=switch(spell.kind){
                    case LEAP->player.getDeltaMovement().y>0;
                    case WARD->arrow.getDeltaMovement().z>0;
                    case CONFUSE->zombie.hasEffect(MobEffects.BLINDNESS);
                    case CAPTURE->zombie.hasEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    default->zombie.getHealth()<zombie.getMaxHealth();
                };
                check(observed,spell.name()+" has expected gameplay effect");
                check(SpellEngine.activeCount()==0,spell.name()+" timed tasks expire");
                results.add(Map.of("spell",spell.name(),"targetHealth",zombie.getHealth(),"observed",true));
                if(arrow!=null)arrow.discard();zombie.discard();
            }
            SpellEngine.clear();resetPlayer(player);Zombie chainFirst=spawn(level,0,81,6),chainSecond=spawn(level,3,81,6);
            check(SpellEngine.cast(player,Spell.CHAIN_LIGHTNING),"chain casts at first target");
            check(chainFirst.getHealth()<20&&chainSecond.getHealth()<20,"chain damages two distinct visible targets");chainFirst.discard();chainSecond.discard();
            for(Spell force:List.of(Spell.FORCE_PUSH,Spell.FORCE_PULL)){
                SpellEngine.clear();resetPlayer(player);Zombie z=spawn(level,0,81,6);
                check(SpellEngine.cast(player,force),force+" casts");
                check(force==Spell.FORCE_PUSH?z.getDeltaMovement().z>0:z.getDeltaMovement().z<0,force+" correct movement direction");z.discard();
            }
            SpellEngine.clear();resetPlayer(player);Zombie moving=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.SOUL_SEEKER),"seeker acquires target");moving.setPos(2,81,6);
            for(int t=0;t<60;t++)SpellEngine.tick();check(moving.getHealth()<20,"seeker follows target after sideways movement");moving.discard();
            SpellEngine.clear();resetPlayer(player);Zombie centre=spawn(level,0,81,6),edge=spawn(level,3,81,6);
            check(SpellEngine.cast(player,Spell.GRAVITY_WELL),"well casts");SpellEngine.tick();
            check(edge.getDeltaMovement().x<0,"gravity well pulls nearby target inward");centre.discard();edge.discard();
            SpellEngine.clear();resetPlayer(player);Zombie sonicFront=spawn(level,0,81,5),sonicBack=spawn(level,0,81,9);
            check(SpellEngine.cast(player,Spell.SONIC_BOOM),"sonic beam begins charge");
            for(int t=0;t<11;t++)SpellEngine.tick();
            check(sonicFront.getHealth()==20&&sonicBack.getHealth()==20,"sonic beam does no damage during charge");
            SpellEngine.tick();check(sonicFront.getHealth()<20&&sonicBack.getHealth()<20,"sonic beam pierces two visible targets on release");
            check(SpellEngine.activeCount()==0,"sonic charge task ends on release");sonicFront.discard();sonicBack.discard();
            SpellEngine.clear();resetPlayer(player);player.setHealth(10);Zombie drain=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.SOUL_SIPHON),"siphon casts on living target");
            check(drain.getHealth()<20&&player.getHealth()>10&&player.getHealth()<=12,"siphon heals at most one heart after real damage");drain.discard();
            SpellEngine.clear();resetPlayer(player);player.setHealth(10);Zombie invulnerable=spawn(level,0,81,6);invulnerable.setInvulnerable(true);
            check(SpellEngine.cast(player,Spell.SOUL_SIPHON),"siphon may attempt invulnerable target");
            check(invulnerable.getHealth()==20&&player.getHealth()==10,"blocked damage gives no siphon healing");invulnerable.discard();
            SpellEngine.clear();resetPlayer(player);Zombie prisoner=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.SOUL_PRISON),"soul prison casts");SpellEngine.tick();
            check(prisoner.hasEffect(MobEffects.DARKNESS)&&prisoner.hasEffect(MobEffects.MOVEMENT_SLOWDOWN),"soul prison applies darkness and restraint");prisoner.discard();
            // A real obstruction must stop both entity targeting and travelling spells.
            SpellEngine.clear();resetPlayer(player);Zombie hidden=spawn(level,0,81,6);
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.STONE.defaultBlockState());
            check(!SpellEngine.cast(player,Spell.EMPIRE_SPARK),"targeted spell cannot select through solid wall");
            check(!SpellEngine.cast(player,Spell.SOUL_SEEKER),"seeker cannot acquire through solid wall");
            check(!SpellEngine.cast(player,Spell.CHAIN_LIGHTNING),"chain cannot start through solid wall");
            check(!SpellEngine.cast(player,Spell.FORCE_PULL),"force pull cannot select through solid wall");
            check(SpellEngine.cast(player,Spell.SPARK),"projectile can cast toward wall");for(int t=0;t<40;t++)SpellEngine.tick();
            check(hidden.getHealth()==20,"solid wall blocks projectile damage");
            SpellEngine.clear();check(SpellEngine.cast(player,Spell.SONIC_BOOM),"sonic beam may charge toward a wall");
            for(int t=0;t<20;t++)SpellEngine.tick();check(hidden.getHealth()==20,"solid wall stops sonic beam damage");
            check(!SpellEngine.cast(player,Spell.SOUL_SIPHON),"siphon cannot acquire through wall");
            check(level.getBlockState(new BlockPos(0,82,3)).is(Blocks.STONE),"spell impact preserves solid blocks");
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.AIR.defaultBlockState());hidden.discard();
            var team=level.getScoreboard().addPlayerTeam("wand_test_allies");team.setAllowFriendlyFire(false);
            level.getScoreboard().addPlayerToTeam(player.getScoreboardName(),team);
            Zombie ally=spawn(level,0,81,6);level.getScoreboard().addPlayerToTeam(ally.getScoreboardName(),team);
            check(!SpellEngine.allowed(player,ally),"scoreboard friendly fire is respected");
            SpellEngine.clear();check(!SpellEngine.cast(player,Spell.EMPIRE_SPARK),"allied target is rejected");
            level.getScoreboard().removePlayerTeam(team);ally.discard();
            SpellEngine.clear();resetPlayer(player);player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,new ItemStack(Items.STICK));
            SpellEngine.action(player,0);check(SpellEngine.activeCount()==0,"unarmed action packet is rejected");
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,KingdomWands.EMPIRE.toStack());
            SpellEngine.action(player,99);check(SpellEngine.activeCount()==0,"invalid action packet is rejected");
            SpellEngine.action(player,0);check(SpellEngine.activeCount()==1,"valid held wand action casts");
            SpellEngine.action(player,1);check(wand.selection(player.getMainHandItem())==0,"multiple actions in one tick are rate limited");
            SpellEngine.clear();player.setGameMode(GameType.SPECTATOR);SpellEngine.action(player,0);check(SpellEngine.activeCount()==0,"spectator casting is rejected");player.setGameMode(GameType.SURVIVAL);
            SpellEngine.clear();resetPlayer(player);
            for(Spell spell:List.of(Spell.SPARK,Spell.BLOOD_WAVE,Spell.POISON_WAVE,Spell.FLAME_WAVE,Spell.EMPIRE_COMET,Spell.FIRE_COMET,Spell.FIREBALL,Spell.EMPIRE_PULSE))check(SpellEngine.cast(player,spell),"concurrent cast "+spell);
            check(!SpellEngine.cast(player,Spell.FIRE_PULSE),"per-player active spell limit is enforced");
            for(int t=0;t<100;t++)SpellEngine.tick();check(SpellEngine.activeCount()==0,"concurrent tasks clean up");
            check(server.getCommands().getDispatcher().execute("kingdomwands status",server.createCommandSourceStack())==1,"status command executes");
            report.put("success",true);report.put("checksPassed",checks.size());
        } catch(Throwable error){report.put("success",false);report.put("error",error.toString());KingdomWands.LOG.error("Wand integration failed",error);}
        finally{
            report.put("checks",checks);report.put("spells",results);report.put("clientRenderingTested",false);
            try{Path path=Path.of("../validation/server-integration.json");Files.createDirectories(path.getParent());Files.writeString(path,new GsonBuilder().setPrettyPrinting().create().toJson(report));}catch(Exception e){KingdomWands.LOG.error("Could not write report",e);}
            KingdomWands.LOG.info("KINGDOM_WANDS_INTEGRATION {}",report.get("success"));SpellEngine.clear();server.halt(false);
        }
    }
    private static void resetPlayer(FakePlayer player){player.setPos(0,81,0);player.setYRot(0);player.setXRot(0);player.setDeltaMovement(Vec3.ZERO);player.setHealth(player.getMaxHealth());player.removeAllEffects();}
    private static Zombie spawn(ServerLevel level,double x,double y,double z){Zombie zombie=new Zombie(EntityType.ZOMBIE,level);zombie.setPos(x,y,z);zombie.setNoAi(true);zombie.setNoGravity(true);level.addFreshEntity(zombie);return zombie;}
}
