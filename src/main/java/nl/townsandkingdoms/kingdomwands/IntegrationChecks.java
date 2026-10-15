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
            for(Spell spell:Spell.values()){
                SpellEngine.clear();resetPlayer(player);
                player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,KingdomWands.EMPIRE.toStack());
                Zombie zombie=spawn(level,0,81,6);Arrow arrow=null;
                if(spell==Spell.BLOOD_BLOCK){arrow=new Arrow(EntityType.ARROW,level);arrow.setOwner(zombie);arrow.setPos(0,82,2);arrow.setDeltaMovement(0,0,-0.8);level.addFreshEntity(arrow);}
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
            // A real obstruction must stop both entity targeting and travelling spells.
            SpellEngine.clear();resetPlayer(player);Zombie hidden=spawn(level,0,81,6);
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.STONE.defaultBlockState());
            check(!SpellEngine.cast(player,Spell.EMPIRE_SPARK),"targeted spell cannot select through solid wall");
            check(SpellEngine.cast(player,Spell.SPARK),"projectile can cast toward wall");for(int t=0;t<40;t++)SpellEngine.tick();
            check(hidden.getHealth()==20,"solid wall blocks projectile damage");
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
