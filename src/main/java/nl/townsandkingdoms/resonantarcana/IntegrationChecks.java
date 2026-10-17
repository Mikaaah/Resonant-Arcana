package nl.townsandkingdoms.resonantarcana;

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

/** Explicitly gated developer fixture: starts and stops only with -Dresonant_arcana.integrationTest=true. */
public final class IntegrationChecks {
    private static final List<String> checks=new ArrayList<>();
    private static final List<WandNetwork.Visual> visuals=new ArrayList<>();
    static void visual(WandNetwork.Visual message){if(visuals.size()<512)visuals.add(message);}
    private IntegrationChecks() {}
    public static boolean enabled(){return Boolean.getBoolean("resonant_arcana.integrationTest");}
    public static void start(ServerStartedEvent event){if(enabled())event.getServer().execute(()->run(event.getServer()));}
    private static void check(boolean condition,String name){if(!condition)throw new IllegalStateException(name);checks.add(name);}
    private static void run(MinecraftServer server){
        Map<String,Object> report=new LinkedHashMap<>();List<Map<String,Object>> results=new ArrayList<>();
        try {
            ServerLevel level=server.overworld();
            // Iron's builds its server spell config during the player/datapack sync lifecycle.
            io.redspace.ironsspellbooks.api.config.SpellConfigManager.onDatapackSync(new net.neoforged.neoforge.event.OnDatapackSyncEvent(server.getPlayerList(),null));
            for(int x=-1;x<=1;x++)for(int z=-1;z<=2;z++)level.getChunk(x,z);
            for(int x=-12;x<=12;x++)for(int z=-8;z<=24;z++){
                level.setBlockAndUpdate(new BlockPos(x,80,z),Blocks.STONE.defaultBlockState());
                for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,z),Blocks.AIR.defaultBlockState());
            }
            FakePlayer player=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("6341cb70-4863-4d65-a614-1fd6719c50b8"),"WandTest"));
            player.setGameMode(GameType.SURVIVAL);resetPlayer(player);
            WandItem wand=ResonantArcana.ARCANE.get();ItemStack stack=wand.getDefaultInstance();
            wand.select(stack,-1);check(wand.selection(stack)==wand.spells.size()-1,"previous selection wraps");
            var custom=stack.get(DataComponents.CUSTOM_DATA).copyTag();custom.putString("external","preserved");stack.set(DataComponents.CUSTOM_DATA,CustomData.of(custom));
            wand.select(stack,2);check(stack.get(DataComponents.CUSTOM_DATA).copyTag().getString("external").equals("preserved"),"selection preserves unrelated custom data");
            ItemStack restored=ItemStack.parseOptional(server.registryAccess(),(CompoundTag)stack.save(server.registryAccess()));
            check(wand.selected(restored)==Spell.CONFUSE,"selected spell survives item serialization");
            RegistryFriendlyByteBuf buffer=new RegistryFriendlyByteBuf(Unpooled.buffer(),server.registryAccess());
            try {
                var a=new WandNetwork.Action(2);WandNetwork.Action.CODEC.encode(buffer,a);check(WandNetwork.Action.CODEC.decode(buffer).equals(a),"action packet codec round trip");
                buffer.clear();var selection=new WandNetwork.Select(ResonantArcana.ARCANE.getId(),11);WandNetwork.Select.CODEC.encode(buffer,selection);check(WandNetwork.Select.CODEC.decode(buffer).equals(selection),"wand selection packet round trip");
                buffer.clear();var b=new WandNetwork.Burst(1,82,-3,0xDE1342);WandNetwork.Burst.CODEC.encode(buffer,b);check(WandNetwork.Burst.CODEC.decode(buffer).equals(b),"burst packet codec round trip");
                buffer.clear();var visual=new WandNetwork.Visual(Spell.SONIC_SCREAM.ordinal(),SpellVisuals.Stage.LINK.ordinal(),1,82,-3,1,82,25,1);
                WandNetwork.Visual.CODEC.encode(buffer,visual);check(WandNetwork.Visual.CODEC.decode(buffer).equals(visual),"visual packet codec round trip");
                check(visual.valid(),"finite beam packet accepted");
                check(!new WandNetwork.Visual(-1,0,0,0,0,0,0,0,1).valid(),"unknown visual spell rejected");
                check(!new WandNetwork.Visual(0,999,0,0,0,0,0,0,1).valid(),"unknown visual stage rejected");
                check(!new WandNetwork.Visual(0,0,Double.NaN,0,0,0,0,0,1).valid(),"nonfinite visual rejected");
                check(!new WandNetwork.Visual(0,0,0,0,0,500,0,0,1).valid(),"unbounded beam rejected");
                check(!new WandNetwork.Visual(0,0,0,0,0,0,0,0,Float.POSITIVE_INFINITY).valid(),"nonfinite effect size rejected");
            }finally{buffer.release();}
            check(ResonantArcana.ITEMS.getEntries().size()==10,"ten registered wands");
            var catalog=EnumSet.noneOf(Spell.class);
            for(var item:ResonantArcana.ITEMS.getEntries()){
                check(item.getId().getNamespace().equals("resonant_arcana"),item.getId()+" uses the new namespace");
                WandItem w=(WandItem)item.get();ItemStack ws=w.getDefaultInstance();check(w.spells.size()>=12&&w.spells.size()<=18,item.getId()+" has a bounded expanded kit");
                check(server.getRecipeManager().byKey(item.getId()).isPresent(),item.getId()+" has a loaded survival recipe");
                for(int i=0;i<w.spells.size();i++){
                    w.select(ws,i);check(w.selected(ws)==w.spells.get(i),item.getId()+" selection "+i);
                    check(catalog.add(w.spells.get(i)),w.spells.get(i)+" has exactly one wand home");
                }

            }
            check(catalog.size()==Spell.enabledValues().length,"all catalog spells are obtainable");
            var identityTag=new CompoundTag();identityTag.putInt("resonant_arcana_spell",999);identityTag.putString("resonant_arcana_spell_id","ARCANE_REPULSE");
            var identityStack=wand.getDefaultInstance();identityStack.set(DataComponents.CUSTOM_DATA,CustomData.of(identityTag));
            check(wand.selected(identityStack)==Spell.ARCANE_REPULSE,"stable spell ID takes priority over stale numeric slot");
            for(Spell.Theme theme:Spell.Theme.values())for(String id:List.of(SpellAudio.castId(theme),SpellAudio.castAccentId(theme),SpellAudio.impactId(theme),SpellAudio.impactAccentId(theme),SpellAudio.sustainId(theme)))
                check(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.containsKey(net.minecraft.resources.ResourceLocation.parse(id)),"sound exists: "+id);
            for(Spell spell:Spell.enabledValues()){
                String id=SpellAudio.chargeId(spell);
                check(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.containsKey(net.minecraft.resources.ResourceLocation.parse(id)),"charge sound exists: "+spell);
                check(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.containsKey(net.minecraft.resources.ResourceLocation.parse(SpellAudio.spellCastId(spell))),"release sound exists: "+spell);
                check(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.containsKey(net.minecraft.resources.ResourceLocation.parse(SpellAudio.sustainId(spell))),"sustain sound exists: "+spell);
                check(!SpellAudio.castId(spell.theme).startsWith("resonant_arcana:"),"retired vacuum cast is not referenced: "+spell);
            }
            for(String id:List.of(SpellAudio.spellCastId(Spell.SONIC_SCREAM),SpellAudio.spellCastId(Spell.SHRIEKING_PULSE),SpellAudio.spellCastId(Spell.SOUL_PRISON),"entity.warden.sonic_boom"))
                check(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.containsKey(net.minecraft.resources.ResourceLocation.parse(id)),"special soul sound exists: "+id);
            visuals.clear();SpellVisuals.tick();
            for(int i=0;i<100;i++)SpellVisuals.send(level,Spell.SPARK,SpellVisuals.Stage.FLIGHT,new Vec3(0,82,0),new Vec3(0,82,1),1);
            check(visuals.size()==64,"detail visual traffic capped at 64 per tick");
            for(int i=0;i<100;i++)SpellVisuals.send(level,Spell.SPARK,SpellVisuals.Stage.CAST,new Vec3(0,82,0),new Vec3(0,82,1),1);
            check(visuals.size()==96,"32 accent events reserved when detail budget is exhausted");
            SpellVisuals.tick();SpellVisuals.send(level,Spell.SPARK,SpellVisuals.Stage.CAST,new Vec3(0,82,0),new Vec3(0,82,1),1);
            check(visuals.size()==97,"visual budgets recover on the next tick");
            for(var shape:SpellVisuals.Shape.values())for(var theme:Spell.Theme.values()){
            if(!SpellVisuals.packaged(shape,theme))continue;
                String asset="/assets/resonant_arcana/effeks/"+shape.name().toLowerCase(java.util.Locale.ROOT)+"_"+theme.name().toLowerCase(java.util.Locale.ROOT)+".efkefc";
                try(var input=IntegrationChecks.class.getResourceAsStream(asset)){
                    check(input!=null&&new String(input.readNBytes(4),java.nio.charset.StandardCharsets.US_ASCII).equals("EFKE"),"native effect packaged: "+shape+"/"+theme);
                }
            }
            check(SpellVisuals.assetCount()==24,"only the 24 circular AAAParticles effects are packaged");
            for(Spell spell:Spell.values())if(BossesRiseCompat.optional(spell))check(!SpellVisuals.usesAaa(spell,SpellVisuals.Stage.AREA),spell+" uses Bosses'Rise visuals without AAAParticles");
            for(Spell spell:Spell.enabledValues()){
                if(AdvancedSpells.handles(spell.kind)||spell==Spell.KRAKENS_WRATH)continue; // Reactive kits have dedicated combat fixtures below.
                SpellEngine.clear();resetPlayer(player);visuals.clear();
                player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,ResonantArcana.ARCANE.toStack());
                if(spell.healing()>0)player.setHealth(5);
                Zombie zombie=spawn(level,0,81,spell.kind==Spell.Kind.ORBIT?3:spell.kind==Spell.Kind.BLADE_ARC?4:spell.kind==Spell.Kind.SWEEP||spell.kind==Spell.Kind.GAZE?5:spell.kind==Spell.Kind.PULSE?Math.min(5,spell.radius-1):spell.kind==Spell.Kind.AURA?Math.min(6,spell.radius-1):6);Arrow arrow=null;
                if(spell.kind==Spell.Kind.WARD){arrow=new Arrow(EntityType.ARROW,level);arrow.setOwner(zombie);arrow.setPos(0,82,2);arrow.setDeltaMovement(0,0,-0.8);level.addFreshEntity(arrow);}
                check(SpellEngine.cast(player,spell),spell.name()+" starts");
                var props=level.getEntitiesOfClass(SpellCentrepieces.Prop.class,player.getBoundingBox().inflate(64));
                if(spell.kind==Spell.Kind.TOTEM){
                    check(props.size()>=2,spell+" places a physical centrepiece");
                    check(props.stream().allMatch(e->e.getY()>=81&&e.getY()<=83&&!e.isPickable()),spell+" props sit on ground and cannot be attacked");
                    check(level.getBlockState(new BlockPos(0,80,6)).is(Blocks.STONE),spell+" props preserve ground blocks");
                }
                check(!SpellEngine.cast(player,spell),spell.name()+" cooldown rejects immediate repeat");
                for(int t=0;t<100;t++)SpellEngine.tick();
                boolean observed=switch(spell.kind){
                    case HEAL,REMEDY,HYMN->player.getHealth()>5&&zombie.getHealth()==20;
                    case CLEANSE->spell==Spell.TOXIC_MOLT?player.getHealth()>5:player.hasEffect(MobEffects.DAMAGE_RESISTANCE);
                    case WARCRY->player.hasEffect(MobEffects.DAMAGE_RESISTANCE)&&zombie.getHealth()<20&&zombie.getDeltaMovement().z<0;
                    case MARTIAL_BUFF->player.hasEffect(ArcanaEffects.ARCANE_EDGE)||player.hasEffect(ArcanaEffects.TEMPO)||player.hasEffect(MobEffects.DAMAGE_RESISTANCE);
                    case BUFF->player.getAbsorptionAmount()>0||player.hasEffect(MobEffects.INVISIBILITY);
                    case BLINK->player.hasEffect(MobEffects.SLOW_FALLING)&&visuals.stream().anyMatch(v->v.stage()==SpellVisuals.Stage.LINK.ordinal()&&v.endZ()>1);
                    case DASH->player.getDeltaMovement().lengthSqr()>.1;
                    case LEAP->player.getDeltaMovement().y>0;
                    case WARD->arrow.getDeltaMovement().z>0;
                    case CONFUSE->zombie.hasEffect(MobEffects.BLINDNESS);
                    case CAPTURE->zombie.hasEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    default->zombie.getHealth()<zombie.getMaxHealth();
                };
                check(observed,spell.name()+" has expected gameplay effect");
                if(spell==Spell.WISP_LANTERN)check(zombie.hasEffect(MobEffects.GLOWING),"lantern reveals affected enemies");
                if(spell==Spell.DREAD_EFFIGY)check(zombie.getEffect(MobEffects.WEAKNESS).getAmplifier()==1,"effigy suppresses melee with Weakness II");
                if(spell.kind==Spell.Kind.TOTEM||spell.kind==Spell.Kind.RAIN||spell.kind==Spell.Kind.DOMAIN||spell.kind==Spell.Kind.TRAP)
                    check(visuals.stream().anyMatch(v->v.stage()==SpellVisuals.Stage.AREA.ordinal()&&Math.abs(v.size()-spell.radius)<.001),spell+" advertises its actual area radius");
                check(SpellEngine.activeCount()==0,spell.name()+" timed tasks expire");
                check(props.stream().allMatch(net.minecraft.world.entity.Entity::isRemoved),spell+" centrepieces expire with their cast");
                check(visuals.stream().allMatch(WandNetwork.Visual::valid),spell+" emits bounded visual packets");
                check(visuals.stream().anyMatch(v->v.stage()==SpellVisuals.Stage.CAST.ordinal()),spell+" has casting visuals");
                SpellVisuals.Stage expectedStage=switch(spell.kind){
                    case BOSS_RIFT,SOULBLAST,BOLT,COMET,WAVE,SEEKER,VOLLEY,CANNON,METEOR,SONIC,FROSTFRONT,FAN,RICOCHET,PIERCING_ORB,SERPENT -> SpellVisuals.Stage.FLIGHT;
                    case BOSS_ICE,BOSS_TENTACLE,BOSS_SAND,BOSS_DRAGON,BOSS_SWORD,WARCRY,MARTIAL_BUFF,BLADE_ARC,PULSE,CAPTURE,CHOKE,SUSPEND,MARK,WELL,STORM,AURA,FIELD,TWISTER,WARD,COLLAPSE,DOOM,FANGS,HEAL,SWEEP,GAZE,ORBIT,BUFF,CLEANSE,HYMN,TOTEM,RAIN,DOMAIN,TRAP -> SpellVisuals.Stage.AREA;
                    case LIGHTNING,SMITE,PILLAR -> SpellVisuals.Stage.STRIKE;
                    case BURST,REMEDY,EXECUTE -> SpellVisuals.Stage.IMPACT;
                    default -> SpellVisuals.Stage.LINK;
                };
                check(visuals.stream().anyMatch(v->v.stage()==expectedStage.ordinal()),spell+" has "+expectedStage+" visuals");
                for(var visual:visuals){
                    var emitted=Spell.values()[visual.spell()];var shape=SpellVisuals.shape(emitted,SpellVisuals.Stage.values()[visual.stage()]);
                    var stage=SpellVisuals.Stage.values()[visual.stage()];
                    if(SpellVisuals.usesAaa(emitted,stage))check(SpellVisuals.packaged(shape,emitted.theme),emitted+" circular effect exists: "+shape);
                }
                results.add(Map.of("spell",spell.name(),"targetHealth",zombie.getHealth(),"observed",true));
                if(arrow!=null)arrow.discard();zombie.discard();
            }
            SpellEngine.clear();resetPlayer(player);player.setXRot(-90);
            for(Spell totem:List.of(Spell.WISP_LANTERN,Spell.DREAD_EFFIGY,Spell.SPORE_TOTEM,Spell.CONSECRATION)){
                check(!SpellEngine.canStart(player,totem),totem+" refuses an aim without a floor");
                check(!SpellEngine.cast(player,totem),totem+" refuses release without a floor");
            }
            resetPlayer(player);Zombie totemTarget=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.WISP_LANTERN),"lantern starts before explicit cancellation");
            var cancelledProps=level.getEntitiesOfClass(SpellCentrepieces.Prop.class,player.getBoundingBox().inflate(64));
            check(!cancelledProps.isEmpty(),"cancellation fixture has physical props");SpellEngine.clear();
            check(cancelledProps.stream().allMatch(net.minecraft.world.entity.Entity::isRemoved),"cancelling a cast removes every centrepiece immediately");totemTarget.discard();
            resetPlayer(player);player.setXRot(60);visuals.clear();check(SpellEngine.cast(player,Spell.LAST_STAND),"banner range fixture starts");SpellEngine.tick();
            check(visuals.stream().anyMatch(v->v.spell()==Spell.LAST_STAND.ordinal()&&v.stage()==SpellVisuals.Stage.AREA.ordinal()&&v.size()==Spell.LAST_STAND.radius),"banner outline shows its full defense radius");
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
            SpellEngine.clear();resetPlayer(player);Zombie sonicFront=spawn(level,0,81,5),sonicBack=spawn(level,0,81,9),sonicFlank=spawn(level,6,81,3),sonicBehind=spawn(level,0,81,-5),sonicFar=spawn(level,0,81,16);
            check(SpellEngine.cast(player,Spell.SONIC_SCREAM),"sonic scream begins charge");
            for(int t=0;t<19;t++)SpellEngine.tick();
            check(sonicFront.getHealth()==20&&sonicBack.getHealth()==20,"sonic scream does no damage during charge");
            for(int t=0;t<8;t++)SpellEngine.tick();check(sonicFront.getHealth()==6&&sonicBack.getHealth()==6&&sonicFlank.getHealth()==6,"sonic scream fills a wide forward cone and hits once");
            check(sonicBehind.getHealth()==20&&sonicFar.getHealth()==20,"sonic scream excludes rear and distant targets");
            check(SpellEngine.activeCount()==0,"sonic charge task ends on release");sonicFront.discard();sonicBack.discard();sonicFlank.discard();sonicBehind.discard();sonicFar.discard();
            SpellEngine.clear();resetPlayer(player);player.setHealth(10);Zombie drain=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.SOUL_SIPHON),"siphon casts on living target");
            check(drain.getHealth()<20&&player.getHealth()>10&&player.getHealth()<=12,"siphon heals at most one heart after real damage");drain.discard();
            SpellEngine.clear();resetPlayer(player);player.setHealth(10);Zombie invulnerable=spawn(level,0,81,6);invulnerable.setInvulnerable(true);
            check(SpellEngine.cast(player,Spell.SOUL_SIPHON),"siphon may attempt invulnerable target");
            check(invulnerable.getHealth()==20&&player.getHealth()==10,"blocked damage gives no siphon healing");invulnerable.discard();
            SpellEngine.clear();resetPlayer(player);Zombie prisoner=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.SOUL_PRISON),"soul prison casts");SpellEngine.tick();
            check(prisoner.hasEffect(MobEffects.DARKNESS)&&prisoner.hasEffect(MobEffects.MOVEMENT_SLOWDOWN),"soul prison applies darkness and restraint");prisoner.discard();
            SpellEngine.clear();resetPlayer(player);Zombie marked=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.SHADOW_CAGE),"dread mark arms");for(int t=0;t<39;t++)SpellEngine.tick();
            check(marked.getHealth()==20,"dread mark waits two seconds before damage");SpellEngine.tick();
            check(marked.getHealth()<20&&SpellEngine.activeCount()==0,"dread mark detonates and expires");marked.discard();
            SpellEngine.clear();resetPlayer(player);Zombie suspended=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.GRAVITY_CHOKE),"suspension starts");SpellEngine.tick();
            check(suspended.getDeltaMovement().y>0&&suspended.hasEffect(MobEffects.SLOW_FALLING),"suspension lifts target and protects its landing");suspended.discard();
            SpellEngine.clear();resetPlayer(player);Zombie lanceA=spawn(level,0,81,5),lanceB=spawn(level,0,81,9);
            check(SpellEngine.cast(player,Spell.BLOOD_LANCE),"blood lance casts");check(lanceA.getHealth()<20&&lanceB.getHealth()<20,"blood lance pierces two visible targets");lanceA.discard();lanceB.discard();
            SpellEngine.clear();resetPlayer(player);player.setHealth(10);Zombie sharedDrain=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.SANGUINE_DRAIN),"blood siphon casts");
            check(player.getHealth()>10&&player.getHealth()<=12,"blood siphon heals after damage");
            check(!SpellEngine.cast(player,Spell.SOUL_SIPHON),"blood and soul healing share cooldown");sharedDrain.discard();
            SpellEngine.clear();resetPlayer(player);check(SpellEngine.cast(player,Spell.BLOOD_BLOCK),"blood ward casts for shared recovery test");
            check(!SpellEngine.cast(player,Spell.ECHO_WARD),"blood and echo wards share cooldown");
            SpellEngine.clear();resetPlayer(player);var pool=new net.minecraft.world.entity.animal.Cow(EntityType.COW,level);
            pool.setPos(0,81,6);pool.setNoAi(true);pool.setNoGravity(true);level.addFreshEntity(pool);
            check(SpellEngine.cast(player,Spell.MIASMA_FIELD),"miasma field casts");SpellEngine.tick();
            check(pool.hasEffect(MobEffects.POISON),"miasma poisons target");pool.discard();
            SpellEngine.clear();resetPlayer(player);Zombie hex=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.WITHERING_HEX),"withering hex casts");check(hex.hasEffect(MobEffects.WITHER),"withering hex applies wither");hex.discard();
            SpellEngine.clear();resetPlayer(player);check(SpellEngine.cast(player,Spell.ARCANE_VOLLEY),"arcane volley casts");
            Zombie volleyA=spawn(level,0,81,3);for(int t=0;t<4;t++)SpellEngine.tick();check(volleyA.getHealth()<20,"volley first projectile hits");volleyA.discard();
            Zombie volleyB=spawn(level,0,81,3);for(int t=0;t<6;t++)SpellEngine.tick();check(volleyB.getHealth()<20,"volley second projectile hits");volleyB.discard();
            Zombie volleyC=spawn(level,0,81,3);for(int t=0;t<8;t++)SpellEngine.tick();check(volleyC.getHealth()<20,"volley third projectile hits");volleyC.discard();
            SpellEngine.clear();resetPlayer(player);Zombie outsideFire=spawn(level,0,81,7);
            check(SpellEngine.cast(player,Spell.EMBER_VEIL),"ember veil casts for radius test");for(int t=0;t<60;t++)SpellEngine.tick();
            check(outsideFire.getHealth()==20,"ember veil leaves distant target unharmed");outsideFire.discard();
            // A real obstruction must stop both entity targeting and travelling spells.
            SpellEngine.clear();resetPlayer(player);Zombie hidden=spawn(level,0,81,6);
            check(SpellEngine.cast(player,Spell.SHADOW_CAGE),"mark arms before target takes cover");
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.STONE.defaultBlockState());
            for(int t=0;t<40;t++)SpellEngine.tick();check(hidden.getHealth()==20,"taking cover cancels a pending dread mark");SpellEngine.clear();
            check(!SpellEngine.cast(player,Spell.ARCANE_STRIKE),"targeted spell cannot select through solid wall");
            check(!SpellEngine.cast(player,Spell.SOUL_SEEKER),"seeker cannot acquire through solid wall");
            check(!SpellEngine.cast(player,Spell.CHAIN_LIGHTNING),"chain cannot start through solid wall");
            check(!SpellEngine.cast(player,Spell.FORCE_PULL),"force pull cannot select through solid wall");
            check(SpellEngine.cast(player,Spell.SPARK),"projectile can cast toward wall");for(int t=0;t<40;t++)SpellEngine.tick();
            check(hidden.getHealth()==20,"solid wall blocks projectile damage");
            SpellEngine.clear();check(SpellEngine.cast(player,Spell.SONIC_SCREAM),"sonic beam may charge toward a wall");
            for(int t=0;t<20;t++)SpellEngine.tick();check(hidden.getHealth()==20,"solid wall stops sonic beam damage");
            check(!SpellEngine.cast(player,Spell.SOUL_SIPHON),"siphon cannot acquire through wall");
            check(!SpellEngine.cast(player,Spell.SHADOW_CAGE),"dread mark cannot acquire through wall");
            check(SpellEngine.cast(player,Spell.BLOOD_LANCE),"blood lance may cast toward wall");check(hidden.getHealth()==20,"wall stops blood lance");
            check(SpellEngine.cast(player,Spell.FLAME_THROWER),"flamethrower may cast toward wall");for(int t=0;t<40;t++)SpellEngine.tick();
            check(hidden.getHealth()==20,"wall stops flamethrower damage");
            check(level.getBlockState(new BlockPos(0,82,3)).is(Blocks.STONE),"spell impact preserves solid blocks");
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.AIR.defaultBlockState());hidden.discard();
            var team=level.getScoreboard().addPlayerTeam("wand_test_allies");team.setAllowFriendlyFire(false);
            level.getScoreboard().addPlayerToTeam(player.getScoreboardName(),team);
            Zombie ally=spawn(level,0,81,6);level.getScoreboard().addPlayerToTeam(ally.getScoreboardName(),team);
            check(!SpellEngine.allowed(player,ally),"scoreboard friendly fire is respected");
            SpellEngine.clear();check(!SpellEngine.cast(player,Spell.ARCANE_STRIKE),"allied target is rejected");
            level.getScoreboard().removePlayerTeam(team);ally.discard();
            SpellEngine.clear();resetPlayer(player);player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,new ItemStack(Items.STICK));
            SpellEngine.action(player,0);check(SpellEngine.activeCount()==0,"unarmed action packet is rejected");
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,ResonantArcana.ARCANE.toStack());
            SpellEngine.action(player,99);check(SpellEngine.activeCount()==0,"invalid action packet is rejected");
            var actionMagic=io.redspace.ironsspellbooks.api.magic.MagicData.getPlayerMagicData(player);
            if(actionMagic.getSyncedData()==null)actionMagic.setSyncedData(new io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData(player));
            actionMagic.setMana(100);
            SpellEngine.action(player,0);check(actionMagic.isCasting(),"held wand starts native Iron casting");
            SpellEngine.action(player,1);check(wand.selection(player.getMainHandItem())==0,"multiple actions in one tick are rate limited");
            IronsCompat.cancel(player);
            SpellEngine.clear();player.setGameMode(GameType.SPECTATOR);SpellEngine.action(player,0);check(SpellEngine.activeCount()==0,"spectator casting is rejected");player.setGameMode(GameType.SURVIVAL);
            SpellEngine.clear();resetPlayer(player);
            for(Spell spell:List.of(Spell.SPARK,Spell.BLOOD_WAVE,Spell.POISON_WAVE,Spell.FLAME_WAVE,Spell.ASTRAL_COMET,Spell.FIRE_COMET,Spell.FIREBALL,Spell.ARCANE_REPULSE))check(SpellEngine.cast(player,spell),"concurrent cast "+spell);
            check(!SpellEngine.cast(player,Spell.FLAME_SHOCK_WAVE),"per-player active spell limit is enforced");
            for(int t=0;t<100;t++)SpellEngine.tick();check(SpellEngine.activeCount()==0,"concurrent tasks clean up");
            check(server.getCommands().getDispatcher().execute("resonantarcana status",server.createCommandSourceStack())==1,"status command executes");
            expansionChecks(level,player);martialChecks(level,player);bossChecks(level,player);
            schoolChecks(level,player);
            heavyChecks(level,player);
            cannonChecks(level);
            audioChecks(level);
            IronsIntegrationChecks.run(level,IntegrationChecks::check);
            AdvancedChecks.run(level,IntegrationChecks::check);
            report.put("success",true);report.put("checksPassed",checks.size());
        } catch(Throwable error){report.put("success",false);report.put("error",error.toString());ResonantArcana.LOG.error("Wand integration failed",error);}
        finally{
            report.put("checks",checks);report.put("spells",results);report.put("clientRenderingTested",false);
            try{Path path=Path.of("../validation/server-integration.json");Files.createDirectories(path.getParent());Files.writeString(path,new GsonBuilder().setPrettyPrinting().create().toJson(report));}catch(Exception e){ResonantArcana.LOG.error("Could not write report",e);}
            ResonantArcana.LOG.info("RESONANT_ARCANA_INTEGRATION {}",report.get("success"));SpellEngine.clear();server.halt(false);
        }
    }
    private static void bossChecks(ServerLevel level,FakePlayer player){
        var optional=List.of(Spell.KRAKENS_GRASP,Spell.KRAKENS_UNDERTOW,Spell.ICE_SPIKE_ERUPTION,Spell.YETIS_GLACIAL_WALL,Spell.HELVARS_RIFT,Spell.HELVARS_FRACTURE,Spell.HELVARS_SWORDFALL,
            Spell.SANDWORMS_ERUPTION,Spell.SANDWORMS_QUICKSAND,Spell.DRAGONS_GUARD,Spell.DRAGONS_PYRE);
        for(var spell:optional){
            check(spell.available()==BossesRiseCompat.available(),spell+" follows optional dependency availability");
            check(io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.containsKey(IronsCompat.id(spell))==BossesRiseCompat.available(),spell+" native registry follows optional dependency");
        }
        if(!BossesRiseCompat.available()){
            for(var spell:optional){SpellEngine.clear();resetPlayer(player);check(!SpellEngine.cast(player,spell),spell+" safely rejects casting without dependency");}
            return;
        }
        SpellEngine.clear();resetPlayer(player);Zombie wrathTarget=spawn(level,0,81,6);
        check(SpellEngine.cast(player,Spell.KRAKENS_WRATH),"Wrath orientation fixture begins");
        for(int t=0;t<32;t++)SpellEngine.tick();
        var tentacles=level.getEntitiesOfClass(BossesRiseBridge.Tentacle.class,new net.minecraft.world.phys.AABB(-12,76,-8,12,90,24),e->!e.isRemoved());
        check(tentacles.size()==4,"Wrath creates four outer tentacles before its central slam");
        var headings=new java.util.HashSet<Integer>();
        for(var tentacle:tentacles){
            check(Math.abs(tentacle.getY()-(81-BossesRiseBridge.TENTACLE_BURY_DEPTH))<.01,"Kraken visual root is buried below the ground surface");
            Vec3 towardCenter=new Vec3(0,81,6).subtract(tentacle.position());
            check(Math.abs(net.minecraft.util.Mth.wrapDegrees(tentacle.getYRot()-BossesRiseBridge.tentacleYaw(towardCenter)))<.01,"Wrath outer tentacles face the shared impact center");
            headings.add(Math.round(tentacle.getYRot()));
        }
        check(headings.size()==4,"Wrath does not reuse one facing for every outer tentacle");
        SpellEngine.clear();wrathTarget.discard();
        resetPlayer(player);Zombie ceilingTarget=spawn(level,0,81,6);
        for(int x=-2;x<=2;x++)for(int z=4;z<=8;z++)level.setBlockAndUpdate(new BlockPos(x,85,z),Blocks.STONE.defaultBlockState());
        check(!SpellEngine.canStart(player,Spell.HELVARS_SWORDFALL),"The full 3D sword rejects headroom that would clip its hilt into a ceiling");
        for(int x=-2;x<=2;x++)for(int z=4;z<=8;z++)level.setBlockAndUpdate(new BlockPos(x,85,z),Blocks.AIR.defaultBlockState());
        check(SpellEngine.canStart(player,Spell.HELVARS_SWORDFALL),"Swordfall remains available with open headroom");
        ceilingTarget.discard();
        for(var spell:optional)for(int rank=1;rank<=5;rank++){
            SpellEngine.clear();resetPlayer(player);Zombie victim=spawn(level,0,81,6);
            victim.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(200);victim.setHealth(200);
            victim.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).setBaseValue(0);
            check(SpellEngine.castFromSpellbook(player,spell,spell.power(rank),level.damageSources().indirectMagic(player,player),spell.cooldown,false),spell+" rank "+rank+" begins");
            if(spell==Spell.KRAKENS_GRASP){for(int t=0;t<45;t++)SpellEngine.tick();check(victim.getHealth()==200,"tentacle has a real rise and attack windup");}
            if(spell==Spell.HELVARS_SWORDFALL){for(int t=0;t<23;t++)SpellEngine.tick();check(victim.getHealth()==200,"Helvar sword deals no damage before impact");}
            if(spell==Spell.DRAGONS_GUARD){
                var flames=level.getEntitiesOfClass(BossesRiseBridge.FlameOrb.class,new net.minecraft.world.phys.AABB(-12,76,-8,12,92,24),e->!e.isRemoved());
                check(flames.size()==3,"Infernal Barrage creates three travelling flame visuals");
                for(var flame:flames)check(flame.getItem().is(net.minecraft.world.item.Items.FIRE_CHARGE),"Dragon orb has a visible core instead of the native invisible placeholder");
                for(int t=0;t<9;t++)SpellEngine.tick();
                check(victim.getHealth()==200,"Infernal Barrage has a charge before its first projectile launches");
            }
            // The fixture advances spells synchronously rather than world ticks: simulate normal hurt recovery.
            for(int t=0;t<100;t++){if(victim.invulnerableTime>0)victim.invulnerableTime--;SpellEngine.tick();}
            int hits=spell==Spell.DRAGONS_GUARD?3:1;
            check(Math.abs(victim.getHealth()-(200-spell.damageAtLevel(rank)*hits))<.01,spell+" uses native level damage "+hits+" time(s) at rank "+rank);
            check(SpellEngine.activeCount()==0,spell+" releases expire");victim.discard();
        }
        SpellEngine.clear();resetPlayer(player);
        var party=level.getScoreboard().addPlayerTeam("boss_spell_party");party.setAllowFriendlyFire(false);level.getScoreboard().addPlayerToTeam(player.getScoreboardName(),party);
        Zombie friend=spawn(level,1,81,6),enemy=spawn(level,0,81,6);level.getScoreboard().addPlayerToTeam(friend.getScoreboardName(),party);
        try{
            for(var spell:optional){
                SpellEngine.clear();friend.removeAllEffects();friend.setTicksFrozen(0);enemy.setHealth(20);enemy.invulnerableTime=0;
                check(SpellEngine.cast(player,spell),spell+" party fixture begins");
                // Exercise visual ticks directly: they must not call original boss damage/status routines.
                var visualEntities=level.getEntitiesOfClass(net.minecraft.world.entity.Entity.class,new net.minecraft.world.phys.AABB(-12,80,-8,12,90,24),e->e instanceof BossSpellVisual);
                check(!visualEntities.isEmpty(),spell+" spawns installed-mod visual adapters");
                for(var v:visualEntities){
                    check(!v.save(new CompoundTag()),"optional visuals do not persist in world saves");
                    check(!SpellEngine.allowed(player,v),"visual creatures cannot be targeted as enemies");
                    for(int t=0;t<90;t++)v.tick();
                }
                check(friend.getHealth()==20&&friend.getActiveEffects().isEmpty()&&friend.getTicksFrozen()==0,"native visual ticks cannot hurt, slow or freeze allies");
                for(int t=0;t<100;t++)SpellEngine.tick();
                check(friend.getHealth()==20&&friend.getActiveEffects().isEmpty()&&friend.getTicksFrozen()==0,spell+" protects friendly targets from damage and status");
                check(enemy.getHealth()<20,spell+" still affects enemies");
                check(visualEntities.stream().allMatch(net.minecraft.world.entity.Entity::isRemoved),spell+" cleans up visuals");
            }
        }finally{level.getScoreboard().removePlayerTeam(party);friend.discard();enemy.discard();}
        for(var spell:optional){
            SpellEngine.clear();resetPlayer(player);Zombie hidden=spawn(level,0,81,6);
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.STONE.defaultBlockState());
            SpellEngine.cast(player,spell);for(int t=0;t<100;t++)SpellEngine.tick();
            check(hidden.getHealth()==20,spell+" respects blocking walls");hidden.discard();
            for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.AIR.defaultBlockState());
        }
        SpellEngine.clear();resetPlayer(player);SpellEngine.cast(player,Spell.KRAKENS_GRASP);
        var visuals=level.getEntitiesOfClass(net.minecraft.world.entity.Entity.class,new net.minecraft.world.phys.AABB(-12,80,-8,12,90,24),e->e instanceof BossSpellVisual&&!e.isRemoved());
        SpellEngine.clear();check(visuals.stream().allMatch(net.minecraft.world.entity.Entity::isRemoved),"reset removes optional visuals immediately");
    }
    private static void martialChecks(ServerLevel level,FakePlayer player){
        var attack=net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE;
        var speed=net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED;
        SpellEngine.clear();resetPlayer(player);
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,ResonantArcana.HOLY.toStack());
        WandNetwork.select(player,new WandNetwork.Select(ResonantArcana.HOLY.getId(),13));
        check(ResonantArcana.HOLY.get().selected(player.getMainHandItem())==Spell.BULWARK_CHANT,"second-page selection accepted");
        SpellEngine.tick();WandNetwork.select(player,new WandNetwork.Select(ResonantArcana.HOLY.getId(),999));
        check(ResonantArcana.HOLY.get().selected(player.getMainHandItem())==Spell.BULWARK_CHANT,"invalid page selection rejected");
        SpellEngine.clear();resetPlayer(player);
        double baseAttack=player.getAttributeValue(attack),baseSpeed=player.getAttributeValue(speed);
        check(SpellEngine.cast(player,Spell.ARCANE_EDGE),"arcane edge starts");
        check(Math.abs(player.getAttributeValue(attack)-baseAttack-2)<.001,"edge grants two flat attack at I");
        check(Math.abs(player.getAttributeValue(speed)-baseSpeed*1.1)<.001,"edge grants ten percent attack speed at I");
        SpellEngine.clear();SpellEngine.cast(player,Spell.ARCANE_EDGE);
        check(Math.abs(player.getAttributeValue(attack)-baseAttack-2)<.001,"edge refresh does not stack");
        player.removeAllEffects();check(Math.abs(player.getAttributeValue(attack)-baseAttack)<.001,"edge expiry removes attributes");
        for(int rank=1;rank<=5;rank++){
            SpellEngine.clear();resetPlayer(player);Zombie enemy=spawn(level,0,81,4),behind=spawn(level,0,81,-4);
            double spellPower=player.getAttributeValue(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER);
            check(SpellEngine.castFromSpellbook(player,Spell.WARSCREAM,Spell.WARSCREAM.power(rank),level.damageSources().indirectMagic(player,player),400,false),"warscream rank casts "+rank);
            check(Math.abs(player.getAttributeValue(attack)-baseAttack)<.001,"warcry preserves melee attributes "+rank);
            check(Math.abs(player.getAttributeValue(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER)-spellPower)<.001,"warcry preserves spell attributes "+rank);
            check(player.hasEffect(MobEffects.DAMAGE_RESISTANCE)&&player.getEffect(MobEffects.DAMAGE_RESISTANCE).getDuration()==(rank+2)*20,"warcry defense duration "+rank);
            check(enemy.getTarget()==player&&behind.getTarget()==player,"warcry radial hostile taunt "+rank);
            enemy.setTarget(null);for(int i=0;i<10;i++){enemy.invulnerableTime=0;behind.invulnerableTime=0;SpellEngine.tick();}check(enemy.getTarget()==player,"taunt reasserts target");
            check(enemy.getDeltaMovement().z<0&&behind.getDeltaMovement().z>0,"warcry pulls enemies inward "+rank);
            for(int i=10;i<26;i++){enemy.invulnerableTime=0;behind.invulnerableTime=0;SpellEngine.tick();}
            check(Math.abs(enemy.getHealth()-(20-9*Spell.WARSCREAM.power(rank)))<.01,"warcry deals three distinct pulses "+rank);
            for(int i=26;i<(rank+2)*20-1;i++)SpellEngine.tick();check(SpellEngine.activeCount()==1,"taunt remains active for rank duration "+rank);
            SpellEngine.tick();enemy.setTarget(null);SpellEngine.tick();check(enemy.getTarget()==null&&SpellEngine.activeCount()==0,"taunt expires");
            enemy.discard();behind.discard();
        }
        SpellEngine.clear();resetPlayer(player);
        var party=level.getScoreboard().addPlayerTeam("martial_party");party.setAllowFriendlyFire(false);
        level.getScoreboard().addPlayerToTeam(player.getScoreboardName(),party);
        Zombie ally=spawn(level,1,81,3),outsider=spawn(level,-1,81,3);
        level.getScoreboard().addPlayerToTeam(ally.getScoreboardName(),party);
        try{
            SpellEngine.cast(player,Spell.WARSCREAM);
            check(ally.hasEffect(MobEffects.DAMAGE_RESISTANCE),"warscream defends visible teammates");
            check(!outsider.hasEffect(MobEffects.DAMAGE_RESISTANCE),"warscream does not defend enemies");
            check(ally.getTarget()==null&&ally.getDeltaMovement().lengthSqr()==0,"warscream protects friendly teammates from push and taunt");
            SpellEngine.clear();SpellEngine.cast(player,Spell.WARSCREAM);check(ally.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier()==0,"party defense refreshes without stacking");
            SpellEngine.clear();SpellEngine.cast(player,Spell.BULWARK_CHANT);check(ally.hasEffect(MobEffects.DAMAGE_RESISTANCE),"bulwark protects nearby allies");
            SpellEngine.clear();SpellEngine.cast(player,Spell.BATTLE_TEMPO);check(ally.hasEffect(ArcanaEffects.TEMPO)&&!outsider.hasEffect(ArcanaEffects.TEMPO),"tempo respects party allegiance");
        }finally{level.getScoreboard().removePlayerTeam(party);ally.discard();outsider.discard();}
        SpellEngine.clear();resetPlayer(player);Zombie front=spawn(level,0,81,4),rear=spawn(level,0,81,-4);
        SpellEngine.cast(player,Spell.SOULBLAST);check(front.getHealth()<20&&front.getDeltaMovement().z>1&&rear.getHealth()==20,"soulblast forward cone repels only in front");front.discard();rear.discard();
        SpellEngine.clear();resetPlayer(player);player.setXRot(90);SpellEngine.cast(player,Spell.SOULBLAST);
        check(player.getDeltaMovement().y>1&&player.hasEffect(MobEffects.SLOW_FALLING),"downward soulblast launches with brief slow falling");
        SpellEngine.clear();resetPlayer(player);player.startFallFlying();player.setDeltaMovement(0,0,1);SpellEngine.cast(player,Spell.SOULBLAST);
        check(player.isFallFlying()&&player.getDeltaMovement().z>1.7&&player.getDeltaMovement().length()<=3,"soulblast boosts gliding without ending flight");player.stopFallFlying();
        SpellEngine.clear();resetPlayer(player);player.setHealth(5);
        Zombie a=spawn(level,0,81,3),b=spawn(level,1,81,3);a.setInvulnerable(true);b.setInvulnerable(true);
        SpellEngine.cast(player,Spell.BLOOD_REND);check(player.getHealth()==5,"blood rend cannot heal from blocked damage");a.discard();b.discard();
        SpellEngine.clear();resetPlayer(player);player.setHealth(5);a=spawn(level,0,81,3);b=spawn(level,1,81,3);
        SpellEngine.cast(player,Spell.BLOOD_REND);check(player.getHealth()>5&&player.getHealth()<=8.001,"blood rend healing budget is shared by the entire cast");a.discard();b.discard();
        SpellEngine.clear();resetPlayer(player);player.getAttribute(attack).setBaseValue(10);a=spawn(level,0,81,4);
        SpellEngine.cast(player,Spell.CINDER_CLEAVE);check(Math.abs(a.getHealth()-9)<.01,"cleave includes half of melee attack damage");a.discard();player.getAttribute(attack).setBaseValue(baseAttack);
        SpellEngine.clear();resetPlayer(player);Zombie blocked=spawn(level,0,81,4);
        for(int x=-1;x<=1;x++)for(int y=81;y<=84;y++)level.setBlockAndUpdate(new BlockPos(x,y,2),Blocks.STONE.defaultBlockState());
        SpellEngine.cast(player,Spell.RIME_THRUST);check(blocked.getHealth()==20,"rime thrust stops at a wall");
        SpellEngine.clear();SpellEngine.cast(player,Spell.WARSCREAM);check(blocked.getTarget()==null&&blocked.getDeltaMovement().lengthSqr()==0,"warcry cannot taunt or push through walls");blocked.discard();
        for(int x=-1;x<=1;x++)for(int y=81;y<=84;y++)level.setBlockAndUpdate(new BlockPos(x,y,2),Blocks.AIR.defaultBlockState());
        SpellEngine.clear();resetPlayer(player);
    }
    private static void expansionChecks(ServerLevel level,FakePlayer player){
        var wand=ResonantArcana.FROST.get();var stack=wand.getDefaultInstance();
        var tag=new CompoundTag();tag.putString("external","keep");stack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));
        check(wand.level(stack,Spell.FROST_NEEDLE)==1,"unmodified wands start at level I");
        wand.learn(stack,Spell.FROST_NEEDLE,5);wand.select(stack,wand.spells.indexOf(Spell.FROST_NEEDLE));
        check(wand.level(stack)==5,"wand remembers selected spell level V");
        check(stack.get(DataComponents.CUSTOM_DATA).copyTag().getString("external").equals("keep"),"level upgrades preserve unrelated item data");
        var restored=ItemStack.parseOptional(level.registryAccess(),(CompoundTag)stack.save(level.registryAccess()));
        check(wand.level(restored,Spell.FROST_NEEDLE)==5,"wand rank survives serialization");
        boolean rejected=false;try{wand.learn(stack,Spell.SOUL_CANNON,5);}catch(IllegalArgumentException expected){rejected=true;}check(rejected&&wand.level(stack,Spell.FROST_NEEDLE)==5,"foreign spell cannot replace own rank");
        SpellEngine.clear();resetPlayer(player);player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,stack);
        var nativeSpell=io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.FROST_NEEDLE));
        Zombie victim=spawn(level,0,81,6);victim.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR).setBaseValue(0);
        SpellEngine.castFromSpellbook(player,Spell.FROST_NEEDLE,2,nativeSpell.getDamageSource(player),80,true);
        for(int i=0;i<30;i++)SpellEngine.tick();
        check(Math.abs(victim.getHealth()-12)<.01,"level V projectile deals twice level I direct damage (health="+victim.getHealth()+")");
        check(victim.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)&&victim.getTicksFrozen()>0,"Frost attacks apply chill and freeze buildup");victim.discard();
        SpellEngine.clear();resetPlayer(player);player.setHealth(5);
        var healSpell=io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.MENDING_LIGHT));
        check(SpellEngine.castFromSpellbook(player,Spell.MENDING_LIGHT,2,healSpell.getDamageSource(player),80,true),"level V remedy casts");
        check(player.getHealth()==17,"level V remedy restores twelve health without equipment");
        for(Spell execute:List.of(Spell.CRIMSON_HARVEST,Spell.SHATTER,Spell.MEMORY_FRACTURE)){
            SpellEngine.clear();resetPlayer(player);victim=spawn(level,0,81,5);
            if(execute==Spell.CRIMSON_HARVEST){victim.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(100);victim.setHealth(30);}
            if(execute==Spell.SHATTER)victim.setTicksFrozen(150);
            if(execute==Spell.MEMORY_FRACTURE)victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.DARKNESS,60));
            float before=victim.getHealth();SpellEngine.cast(player,execute);
            check(!victim.isAlive()||Math.abs(before-victim.getHealth()-execute.damage*1.5)<.01,execute+" gains its conditional fifty percent bonus");victim.discard();
        }
        SpellEngine.clear();resetPlayer(player);player.setHealth(5);victim=spawn(level,0,81,5);victim.setHealth(6);
        SpellEngine.cast(player,Spell.CRIMSON_HARVEST);check(player.getHealth()==7,"crimson harvest restores two health after a killing hit");victim.discard();
        SpellEngine.clear();resetPlayer(player);
        var wardSpell=io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.DAWN_WARD));
        SpellEngine.castFromSpellbook(player,Spell.DAWN_WARD,2,wardSpell.getDamageSource(player),240,true);
        for(int t=0;t<159;t++)SpellEngine.tick();check(SpellEngine.activeCount()==1,"level V ward remains active for nearly eight seconds");
        SpellEngine.tick();SpellEngine.tick();check(SpellEngine.activeCount()==0,"extended level V ward expires after eight seconds");
        SpellEngine.clear();resetPlayer(player);player.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.POISON,200));player.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.WITHER,200));
        check(SpellEngine.cast(player,Spell.TOXIC_MOLT),"toxic molt casts");
        check(!player.hasEffect(MobEffects.POISON)&&!player.hasEffect(MobEffects.WITHER)&&player.hasEffect(MobEffects.MOVEMENT_SPEED),"toxic molt cleanses both toxins and adds mobility");
        SpellEngine.clear();resetPlayer(player);player.setHealth(2);
        check(!SpellEngine.canStart(player,Spell.SANGUINE_PACT),"blood pact cannot spend the caster's last heart");
        SpellEngine.clear();resetPlayer(player);
        for(int y=81;y<=84;y++)level.setBlockAndUpdate(new BlockPos(0,y,3),Blocks.STONE.defaultBlockState());
        var destination=SpellActions.blinkDestination(player,Spell.ARCANE_STEP,8);
        check(SpellEngine.cast(player,Spell.ARCANE_STEP),"blink finds a clear position before cover");
        check(destination!=null&&destination.z>0&&destination.z<3&&level.noCollision(player,player.getBoundingBox().move(destination.subtract(player.position()))),"blink stops before walls with a collision-free destination");
        for(int y=81;y<=84;y++)level.setBlockAndUpdate(new BlockPos(0,y,3),Blocks.AIR.defaultBlockState());
        SpellEngine.clear();resetPlayer(player);
    }
    private static void schoolChecks(ServerLevel level,FakePlayer player){
        check(Arrays.stream(Spell.Theme.values()).map(IronsCompat::school).distinct().count()==9,"all nine native Iron schools are represented");
        check(Spell.SONIC_SCREAM.bookId().equals("sonic_scream")&&Spell.SONIC_SCREAM.title.equals("Sonic Scream"),"sonic scream uses its current spell ID");
        for(Spell spell:List.of(Spell.DOOM,Spell.GLACIAL_RUPTURE,Spell.EVOKERS_EDICT)){
            SpellEngine.clear();resetPlayer(player);
            Zombie a=spawn(level,0,81,6),b=spawn(level,0,81,10);
            for(var z:List.of(a,b)){z.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(100);z.setHealth(100);}
            check(SpellEngine.cast(player,spell),spell+" school test begins");
            if(spell==Spell.DOOM){
                for(int t=0;t<39;t++)SpellEngine.tick();
                check(a.getHealth()==100&&b.getHealth()==100,"DOOM grows before it starts devouring");
                check(b.getDeltaMovement().z<0,"DOOM drags a visible outer target inward");
                SpellEngine.tick();check(a.getHealth()==28&&b.getHealth()==100,"DOOM devours its core before the outer collapse");
            }
            for(int t=0;t<100;t++)SpellEngine.tick();
            check(Math.abs(a.getHealth()-(100-spell.damage))<.01&&Math.abs(b.getHealth()-(100-spell.damage))<.01,spell+" gives each target one full hit");
            if(spell==Spell.GLACIAL_RUPTURE)check(a.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)&&a.getTicksFrozen()>=160,"ice front chills its targets");
            check(SpellEngine.activeCount()==0,spell+" school task expires");a.discard();b.discard();
            SpellEngine.clear();resetPlayer(player);Zombie hidden=spawn(level,0,81,6);
            for(int x=-8;x<=8;x++)for(int y=81;y<=100;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.STONE.defaultBlockState());
            check(SpellEngine.cast(player,spell),spell+" casts at cover");for(int t=0;t<100;t++)SpellEngine.tick();
            check(hidden.getHealth()==20,spell+" respects solid cover");hidden.discard();
            for(int x=-8;x<=8;x++)for(int y=81;y<=100;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.AIR.defaultBlockState());
        }
        SpellEngine.clear();resetPlayer(player);player.setHealth(5);
        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.POISON,100,0));
        var pet=new net.minecraft.world.entity.animal.Wolf(EntityType.WOLF,level);pet.setOwnerUUID(player.getUUID());pet.setPos(2,81,0);pet.setHealth(4);level.addFreshEntity(pet);
        Zombie enemy=spawn(level,0,81,6);enemy.setHealth(5);
        check(SpellEngine.cast(player,Spell.SANCTUARY),"sanctuary casts");
        check(player.getHealth()==17&&!player.hasEffect(MobEffects.POISON),"sanctuary heals and cleanses its caster");
        check(pet.getHealth()>4,"sanctuary restores an owned pet");check(enemy.getHealth()==5,"sanctuary does not heal hostile mobs");pet.discard();enemy.discard();
        SpellEngine.clear();resetPlayer(player);player.setHealth(19);SpellEngine.cast(player,Spell.SANCTUARY);
        check(player.getHealth()==player.getMaxHealth(),"sanctuary does not overheal");
    }
    private static void heavyChecks(ServerLevel level,FakePlayer player){
        for(Spell spell:List.of(Spell.SOLAR_CATACLYSM,Spell.TEMPEST_SPEAR,Spell.EVENT_HORIZON)){
            SpellEngine.clear();resetPlayer(player);visuals.clear();
            Zombie a=spawn(level,0,81,6),b=spawn(level,0,81,10);
            for(var z:List.of(a,b)){
                z.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(100);z.setHealth(100);
            }
            check(SpellEngine.cast(player,spell),spell+" heavy test starts");
            if(spell.kind==Spell.Kind.COLLAPSE){
                for(int t=0;t<30;t++)SpellEngine.tick();
                check(a.getHealth()==100&&b.getHealth()==100,"collapse gathers without premature damage");
                check(b.getDeltaMovement().z<0,"collapse pulls a visible enemy toward its centre");
            }else if(spell.kind==Spell.Kind.METEOR){
                for(int t=0;t<12;t++)SpellEngine.tick();
                check(a.getHealth()==100,"meteor warning does not deal early damage");
            }
            for(int t=0;t<100;t++)SpellEngine.tick();
            check(Math.abs(a.getHealth()-(100-spell.damage))<.01,spell+" deals one full base hit");
            check(Math.abs(b.getHealth()-(100-spell.damage))<.01,spell+" hits a second visible enemy");
            check(SpellEngine.activeCount()==0,spell+" heavy task expires");a.discard();b.discard();
            // Full cover blocks all three attacks, including the final area detonation.
            SpellEngine.clear();resetPlayer(player);Zombie hidden=spawn(level,0,81,6);
            for(int x=-8;x<=8;x++)for(int y=81;y<=100;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.STONE.defaultBlockState());
            check(SpellEngine.cast(player,spell),spell+" may cast at solid cover");
            for(int t=0;t<100;t++)SpellEngine.tick();
            check(hidden.getHealth()==20,spell+" cannot damage through cover");
            check(level.getBlockState(new BlockPos(0,82,3)).is(Blocks.STONE),spell+" leaves terrain intact");hidden.discard();
            for(int x=-8;x<=8;x++)for(int y=81;y<=100;y++)level.setBlockAndUpdate(new BlockPos(x,y,3),Blocks.AIR.defaultBlockState());
        }
        for(var theme:Spell.Theme.values())check(net.minecraft.core.registries.BuiltInRegistries.PARTICLE_TYPE.containsValue(SpellParticles.particle(theme).getType()),"Iron particle is registered: "+theme);
    }
    private static void cannonChecks(ServerLevel level){
        var player=new AudioPlayer(level,new GameProfile(UUID.fromString("ab438590-0c2e-4559-819e-31116824450f"),"CannonTest"));
        player.setGameMode(GameType.SURVIVAL);resetPlayer(player);
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,ResonantArcana.SOUL.toStack());
        SpellEngine.clear();visuals.clear();Zombie victim=spawn(level,0,81,20);
        check(SpellEngine.cast(player,Spell.SOUL_CANNON),"cannon begins charge");
        for(int t=0;t<39;t++)SpellEngine.tick();
        check(victim.getHealth()==20,"cannon cannot damage during two second charge");
        check(visuals.stream().noneMatch(v->v.stage()==SpellVisuals.Stage.FLIGHT.ordinal()),"cannon has no flight before release");
        check(visuals.stream().filter(v->v.stage()==SpellVisuals.Stage.CHARGE.ordinal()).count()==13,"cannon sends bounded inward charge cues");
        SpellEngine.tick();check(victim.getHealth()==20,"cannon travels instead of instantly hitting distant target");
        player.setYRot(90);for(int t=0;t<30;t++)SpellEngine.tick();
        check(victim.getHealth()==4,"cannon deals sixteen damage once and keeps released direction");
        var mini=visuals.stream().filter(v->v.stage()==SpellVisuals.Stage.TRAIL_BURST.ordinal()).toList();
        check(mini.size()==4,"cannon produces four separate mini bursts before twenty block target");
        for(int i=1;i<mini.size();i++)check(Math.abs(mini.get(i).z()-mini.get(i-1).z()-4)<0.01,"cannon mini bursts are spaced four blocks apart");
        check(visuals.stream().filter(v->v.stage()==SpellVisuals.Stage.IMPACT.ordinal()).count()==1,"cannon has one final detonation");
        check(SpellEngine.activeCount()==0,"cannon ends after short impact sound tail");
        for(String id:List.of("entity.warden.sonic_charge","block.respawn_anchor.charge","entity.warden.heartbeat","entity.warden.sonic_boom","entity.warden.roar","entity.firework_rocket.blast","entity.generic.explode","entity.firework_rocket.twinkle_far","block.amethyst_block.resonate"))
            check(player.heard.contains("minecraft:"+id),"cannon delivers audio layer: "+id);
        victim.discard();SpellEngine.clear();resetPlayer(player);visuals.clear();
        check(SpellEngine.cast(player,Spell.SOUL_CANNON),"cannon cancellation begins");
        for(int t=0;t<12;t++)SpellEngine.tick();
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,ItemStack.EMPTY);SpellEngine.tick();
        check(SpellEngine.activeCount()==0&&visuals.stream().noneMatch(v->v.stage()==SpellVisuals.Stage.FLIGHT.ordinal()),"putting wand away cancels cannon before firing");
        SpellEngine.clear();resetPlayer(player);visuals.clear();player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,ResonantArcana.SOUL.toStack());
        level.getChunk(0,3);
        check(SpellEngine.cast(player,Spell.SOUL_CANNON),"cannon can fire into empty sky");
        for(int t=0;t<80;t++)SpellEngine.tick();
        var impact=visuals.stream().filter(v->v.stage()==SpellVisuals.Stage.IMPACT.ordinal()).toList();
        check(impact.size()==1&&Math.abs(impact.getFirst().z()-48.95)<0.01,"cannon detonates once at maximum range");
        check(SpellEngine.activeCount()==0,"empty cannon flight cannot leave a task behind");
        SpellEngine.clear();resetPlayer(player);visuals.clear();Zombie hidden=spawn(level,0,81,6);
        for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,4),Blocks.STONE.defaultBlockState());
        check(SpellEngine.cast(player,Spell.SOUL_CANNON),"cannon can charge towards a wall");
        for(int t=0;t<80;t++)SpellEngine.tick();
        check(hidden.getHealth()==20,"cannon splash cannot damage through a solid wall");
        check(level.getBlockState(new BlockPos(0,82,4)).is(Blocks.STONE),"cannon leaves terrain intact");
        check(visuals.stream().filter(v->v.stage()==SpellVisuals.Stage.IMPACT.ordinal()).count()==1,"wall stops cannon with a single impact");
        for(int x=-2;x<=2;x++)for(int y=81;y<=85;y++)level.setBlockAndUpdate(new BlockPos(x,y,4),Blocks.AIR.defaultBlockState());
        hidden.discard();SpellEngine.clear();
    }
    private static void audioChecks(ServerLevel level){
        boolean oldFeedback=ArcanaConfig.CASTER_IMPACTS.get();double oldVolume=ArcanaConfig.SOUND_VOLUME.get(),oldImpact=ArcanaConfig.IMPACT_VOLUME.get();
        var player=new AudioPlayer(level,new GameProfile(UUID.fromString("52182f8d-8d17-4310-b1e1-34954061e635"),"AudioTest"));
        player.setGameMode(GameType.SURVIVAL);
        try{
            ArcanaConfig.SOUND_VOLUME.set(1.0);ArcanaConfig.IMPACT_VOLUME.set(1.0);ArcanaConfig.CASTER_IMPACTS.set(true);
            SpellEngine.clear();resetPlayer(player);Zombie distant=spawn(level,0,81,24);
            check(SpellEngine.cast(player,Spell.SPARK),"audio test projectile casts");int casts=player.heard.size();
            check(casts==2,"cast emits two distinct sound layers to its caster");
            for(int t=0;t<32;t++)SpellEngine.tick();
            check(distant.getHealth()<20,"audio test projectile reaches distant target");
            check(player.heard.subList(casts,player.heard.size()).contains(SpellAudio.impactId(Spell.Theme.MAGIC)),"caster receives impact cue beyond normal sound range");
            check(player.heard.size()==casts+2,"direct hit and burst do not double-play impact audio");distant.discard();
            SpellEngine.clear();resetPlayer(player);player.heard.clear();ArcanaConfig.CASTER_IMPACTS.set(false);
            Zombie positional=spawn(level,0,81,24);check(SpellEngine.cast(player,Spell.SPARK),"positional-only audio test casts");casts=player.heard.size();
            for(int t=0;t<32;t++)SpellEngine.tick();
            check(positional.getHealth()<20&&player.heard.size()==casts,"caster feedback option disables direct impact delivery");positional.discard();
            SpellEngine.clear();resetPlayer(player);player.heard.clear();ArcanaConfig.CASTER_IMPACTS.set(true);ArcanaConfig.SOUND_VOLUME.set(0.0);
            Zombie muted=spawn(level,0,81,6);check(SpellEngine.cast(player,Spell.SPARK),"muted audio test casts");for(int t=0;t<32;t++)SpellEngine.tick();
            check(muted.getHealth()<20&&player.heard.isEmpty(),"soundVolume zero mutes casts and impacts without changing damage");muted.discard();
            SpellEngine.clear();resetPlayer(player);player.heard.clear();ArcanaConfig.SOUND_VOLUME.set(1.0);ArcanaConfig.IMPACT_VOLUME.set(0.0);
            Zombie noImpact=spawn(level,0,81,6);check(SpellEngine.cast(player,Spell.SPARK),"impact-volume audio test casts");casts=player.heard.size();
            for(int t=0;t<32;t++)SpellEngine.tick();check(noImpact.getHealth()<20&&player.heard.size()==casts,"impactVolume zero preserves cast audio and mutes hits");noImpact.discard();
            SpellEngine.clear();resetPlayer(player);player.heard.clear();ArcanaConfig.IMPACT_VOLUME.set(1.0);
            Zombie a=spawn(level,0,81,3),b=spawn(level,2,81,4),c=spawn(level,-2,81,4);
            check(SpellEngine.cast(player,Spell.SOUL_AURA),"area audio test casts");casts=player.heard.size();SpellEngine.tick();
            check(a.getHealth()<20&&b.getHealth()<20&&c.getHealth()<20,"area audio test affects three targets");
            check(player.heard.size()==casts+2,"area hit audio is limited to one layered impact per cast");a.discard();b.discard();c.discard();
        }finally{
            SpellEngine.clear();ArcanaConfig.CASTER_IMPACTS.set(oldFeedback);ArcanaConfig.SOUND_VOLUME.set(oldVolume);ArcanaConfig.IMPACT_VOLUME.set(oldImpact);
        }
    }
    /** Records the server's direct audio calls; a connected graphical client is still needed for listening tests. */
    private static final class AudioPlayer extends FakePlayer{
        final List<String> heard=new ArrayList<>();
        AudioPlayer(ServerLevel level,GameProfile profile){super(level,profile);}
        @Override public void playNotifySound(net.minecraft.sounds.SoundEvent sound,net.minecraft.sounds.SoundSource source,float volume,float pitch){
            if(volume>0)heard.add(net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getKey(sound).toString());
            super.playNotifySound(sound,source,volume,pitch);
        }
    }
    private static void resetPlayer(FakePlayer player){player.setPos(0,81,0);player.setYRot(0);player.setXRot(0);player.setDeltaMovement(Vec3.ZERO);player.setHealth(player.getMaxHealth());player.removeAllEffects();}
    private static Zombie spawn(ServerLevel level,double x,double y,double z){Zombie zombie=new Zombie(EntityType.ZOMBIE,level);zombie.setPos(x,y,z);zombie.setNoAi(true);zombie.setNoGravity(true);level.addFreshEntity(zombie);return zombie;}
}
