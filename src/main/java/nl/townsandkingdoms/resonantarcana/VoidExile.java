package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;

/** Durable returns are independent of the caster and the ordinary active-spell list. */
final class VoidExile {
    static final ResourceKey<Level> DIMENSION=ResourceKey.create(Registries.DIMENSION,id("void_exile"));
    static final String STORAGE="resonant_arcana_void_exile";
    private static final TagKey<EntityType<?>> IMMUNE=TagKey.create(Registries.ENTITY_TYPE,id("exile_immune"));
    private static final TicketType<Integer> TICKET=TicketType.create("resonant_arcana_exile",Integer::compare,240);
    private static ResourceLocation id(String path){return ResourceLocation.fromNamespaceAndPath("resonant_arcana",path);}
    static void register(){
        NeoForge.EVENT_BUS.addListener((MobDespawnEvent e)->{
            if(e.getEntity().level() instanceof ServerLevel level&&level.dimension().equals(DIMENSION)&&state(level.getServer()).entries.containsKey(e.getEntity().getUUID()))e.setResult(MobDespawnEvent.Result.DENY);
        });
        NeoForge.EVENT_BUS.addListener((EntityJoinLevelEvent e)->{
            if(e.getLevel() instanceof ServerLevel level&&level.dimension().equals(DIMENSION)&&e.getEntity() instanceof LivingEntity living){
                State data=state(level.getServer());Entry entry=data.entries.get(living.getUUID());
                if(entry!=null){data.live.put(entry.id,living);hold(living,cell(entry.slot));}
            }
        });
    }
    static State state(MinecraftServer server){return server.overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(State::new,State::load),STORAGE);}
    static boolean canStart(ServerPlayer caster){
        Vec3 eye=caster.getEyePosition();
        return eligible(caster,SpellEngine.target(caster,eye,SpellEngine.clipLoaded(caster.serverLevel(),eye,caster.getLookAngle(),24,caster),.5));
    }
    static boolean eligible(ServerPlayer caster,LivingEntity target){
        MinecraftServer server=caster.server;ServerLevel pocket=server.getLevel(DIMENSION);
        if(pocket==null||target==null||!SpellEngine.allowed(caster,target)||target.isInvulnerable()||target instanceof ServerPlayer p&&p.isCreative()||target.getType().is(Tags.EntityTypes.BOSSES)||target.getType().is(IMMUNE)||target instanceof Leashable leash&&leash.isLeashed()||target.isPassenger()||target.isVehicle()||target.level()==pocket||!target.canChangeDimensions(target.level(),pocket))return false;
        State data=state(server);
        return !data.entries.containsKey(target.getUUID())&&data.entries.values().stream().filter(e->!offlineReturn(server,data,e)).count()<128;
    }
    static boolean begin(SpellEngine.Cast cast){
        LivingEntity victim=cast.target;if(!eligible(cast.caster,victim))return false;
        MinecraftServer server=cast.level.getServer();State data=state(server);ServerLevel pocket=server.getLevel(DIMENSION);
        int slot=0;while(data.slotUsed(server,slot))slot++;
        Entry entry=new Entry(victim.getUUID(),cast.owner,cast.level.dimension(),victim.position(),cast.dir,victim.getYRot(),victim.getXRot(),victim.getBbHeight(),slot,data.clock+200,(float)(cast.spell.damage*ArcanaConfig.DAMAGE.get()*(cast.book==null?1:cast.book.power())),victim.isInvulnerable(),victim.isNoGravity(),victim instanceof Mob mob&&mob.isNoAi(),victim instanceof ServerPlayer,cast.caster.getTeam()==null?"":cast.caster.getTeam().getName());
        data.entries.put(entry.id,entry);data.setDirty();
        pocket.getChunkSource().addRegionTicket(TICKET,new ChunkPos(BlockPos.containing(cell(slot))),2,slot,true);
        pocket.getChunkAt(BlockPos.containing(cell(slot)));
        if(victim instanceof ServerPlayer player)IronsCompat.cancel(player);
        Entity moved=victim.changeDimension(new DimensionTransition(pocket,cell(slot),Vec3.ZERO,entry.yaw,entry.pitch,DimensionTransition.DO_NOTHING));
        if(!(moved instanceof LivingEntity living)){data.entries.remove(entry.id);data.setDirty();releaseTicket(server,data,entry);return false;}
        data.live.put(entry.id,living);hold(living,cell(slot));
        SpellVisuals.send(cast.level,Spell.VOID_EXILE,SpellVisuals.Stage.LINK,cast.caster.getEyePosition(),entry.center(),1);
        rift(cast.level,entry,SpellVisuals.Stage.STRIKE);SpellAudio.world(cast.level,entry.center(),"block.portal.trigger",1.1f,.7f);return true;
    }
    private static Vec3 cell(int slot){return new Vec3((slot%16)*128+8,80,(slot/16)*128+8);}
    private static void hold(LivingEntity living,Vec3 pos){
        living.setInvulnerable(true);living.setNoGravity(true);
        if(living instanceof Mob mob){mob.setNoAi(true);mob.setTarget(null);mob.getNavigation().stop();}
        if(living.position().distanceToSqr(pos)>.01)living.teleportTo(pos.x,pos.y,pos.z);
        living.setDeltaMovement(Vec3.ZERO);living.fallDistance=0;
    }
    private static Entity find(MinecraftServer server,UUID id){
        var player=server.getPlayerList().getPlayer(id);if(player!=null&&!player.isRemoved())return player;
        for(ServerLevel level:server.getAllLevels()){Entity entity=level.getEntity(id);if(entity!=null&&!entity.isRemoved())return entity;}return null;
    }
    private static boolean offlineReturn(MinecraftServer server,State data,Entry entry){return entry.player&&data.clock>=entry.due&&find(server,entry.id)==null;}
    static void tick(MinecraftServer server){
        if(server==null)return;State data=state(server);if(data.entries.isEmpty())return;
        if(data.entries.values().stream().anyMatch(e->!offlineReturn(server,data,e))){data.clock++;data.setDirty();}
        ServerLevel pocket=server.getLevel(DIMENSION);
        for(Entry entry:new ArrayList<>(data.entries.values())){
            if(offlineReturn(server,data,entry)){data.live.remove(entry.id);releaseTicket(server,data,entry);continue;}
            if(pocket!=null){
                if(data.clock%100==0)pocket.getChunkSource().addRegionTicket(TICKET,new ChunkPos(BlockPos.containing(cell(entry.slot))),2,entry.slot,true);
                pocket.getChunkAt(BlockPos.containing(cell(entry.slot)));
            }
            Entity entity=data.live.get(entry.id);if(entity==null||entity.isRemoved())entity=find(server,entry.id);
            if(entity instanceof LivingEntity living){
                if(!living.isAlive()){restore(living,entry);data.entries.remove(entry.id);data.live.remove(entry.id);data.setDirty();releaseTicket(server,data,entry);continue;}
                if(data.clock>=entry.due||living.level()!=pocket){returnTarget(server,data,entry,living);continue;}
                hold(living,cell(entry.slot));
                if(living instanceof ServerPlayer player&&data.clock%20==0)player.displayClientMessage(net.minecraft.network.chat.Component.literal("Void Exile · "+(int)Math.ceil((entry.due-data.clock)/20.0)+"s"),true);
            }
            ServerLevel home=server.getLevel(entry.home);if(home!=null&&data.clock<entry.due&&data.clock%4==0)rift(home,entry,SpellVisuals.Stage.AREA);
        }
    }
    private static void releaseTicket(MinecraftServer server,State data,Entry entry){
        if(data.entries.values().stream().anyMatch(other->!other.id.equals(entry.id)&&other.slot==entry.slot&&!offlineReturn(server,data,other)))return;
        ServerLevel pocket=server.getLevel(DIMENSION);if(pocket!=null)pocket.getChunkSource().removeRegionTicket(TICKET,new ChunkPos(BlockPos.containing(cell(entry.slot))),2,entry.slot,true);
    }
    private static void restore(LivingEntity living,Entry entry){
        living.setInvulnerable(entry.invulnerable);living.setNoGravity(entry.noGravity);if(living instanceof Mob mob)mob.setNoAi(entry.noAi);
        living.setDeltaMovement(Vec3.ZERO);living.fallDistance=0;
    }
    private static boolean harmAllowed(MinecraftServer server,Entry entry,LivingEntity target){
        Entity owner=find(server,entry.owner);if(owner instanceof ServerPlayer caster)return SpellEngine.allowed(caster,target);
        if(target instanceof ServerPlayer&&(!ArcanaConfig.PVP.get()||!server.isPvpAllowed()))return false;
        if(target instanceof TamableAnimal pet&&entry.owner.equals(pet.getOwnerUUID()))return false;
        var team=server.getScoreboard().getPlayerTeam(entry.team);
        return team==null||team.isAllowFriendlyFire()||!team.equals(target.getTeam());
    }
    private static void returnTarget(MinecraftServer server,State data,Entry entry,LivingEntity target){
        ServerLevel home=server.getLevel(entry.home);boolean missing=home==null;if(missing)home=server.overworld();
        Vec3 origin=missing?Vec3.atBottomCenterOf(home.getSharedSpawnPos()):entry.position;
        Vec3 destination=safeReturn(home,target,origin);if(destination==null)destination=safeReturn(home,target,Vec3.atBottomCenterOf(home.getSharedSpawnPos()));if(destination==null)return;
        Entity moved=target.changeDimension(new DimensionTransition(home,destination,Vec3.ZERO,entry.yaw,entry.pitch,DimensionTransition.DO_NOTHING));
        if(!(moved instanceof LivingEntity returned))return;
        data.entries.remove(entry.id);data.live.remove(entry.id);data.setDirty();restore(returned,entry);releaseTicket(server,data,entry);
        rift(home,entry,SpellVisuals.Stage.RITUAL_END);
        SpellVisuals.send(home,Spell.VOID_EXILE,SpellVisuals.Stage.IMPACT,returned.getBoundingBox().getCenter(),returned.getBoundingBox().getCenter().add(entry.direction),1.5);
        SpellAudio.world(home,returned.position(),"entity.enderman.teleport",1.3f,.8f);
        if(harmAllowed(server,entry,returned)){
            var spell=SpellRegistry.REGISTRY.get(IronsCompat.id(Spell.VOID_EXILE));Entity owner=find(server,entry.owner);
            DamageSource source=owner instanceof LivingEntity living?spell.getDamageSource(living):new DamageSource(home.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(spell.getSchoolType().getDamageType()));
            returned.hurt(source,entry.damage);
            if(returned.isAlive())SpellEngine.effect(returned,MobEffects.WEAKNESS,100,1);
        }
    }
    private static Vec3 safeReturn(ServerLevel home,LivingEntity target,Vec3 origin){
        home.getChunkAt(BlockPos.containing(origin));
        for(int radius=0;radius<=4;radius++)for(int y=0;y<=8;y++)for(int x=-radius;x<=radius;x++)for(int z=-radius;z<=radius;z++){
            if(Math.max(Math.abs(x),Math.abs(z))!=radius)continue;Vec3 pos=origin.add(x,y,z);BlockPos block=BlockPos.containing(pos);
            if(pos.y<home.getMinBuildHeight()||pos.y+target.getBbHeight()>=home.getMaxBuildHeight()||!home.getWorldBorder().isWithinBounds(block))continue;
            if(!home.hasChunkAt(block))home.getChunkAt(block);
            if(!target.fireImmune()&&home.getFluidState(block).is(net.minecraft.tags.FluidTags.LAVA))continue;
            if(home.noCollision(target,target.getBoundingBox().move(pos.subtract(target.position()))))return pos;
        }return null;
    }
    private static void rift(ServerLevel level,Entry entry,SpellVisuals.Stage stage){SpellVisuals.send(level,Spell.VOID_EXILE,stage,entry.center(),entry.center().add(entry.direction),(float)Math.clamp(entry.height*.65,.8,2));}
    static final class State extends SavedData {
        long clock;final Map<UUID,Entry> entries=new LinkedHashMap<>();final Map<UUID,LivingEntity> live=new HashMap<>();
        boolean slotUsed(MinecraftServer server,int slot){return entries.values().stream().anyMatch(e->e.slot==slot&&!offlineReturn(server,this,e));}
        static State load(CompoundTag tag,HolderLookup.Provider lookup){State state=new State();state.clock=tag.getLong("Clock");ListTag returns=tag.getList("Returns",10);for(int i=0;i<returns.size();i++){Entry entry=Entry.load(returns.getCompound(i));state.entries.put(entry.id,entry);}return state;}
        @Override public CompoundTag save(CompoundTag tag,HolderLookup.Provider lookup){tag.putLong("Clock",clock);ListTag returns=new ListTag();entries.values().forEach(e->returns.add(e.save()));tag.put("Returns",returns);return tag;}
    }
    private record Entry(UUID id,UUID owner,ResourceKey<Level> home,Vec3 position,Vec3 direction,float yaw,float pitch,float height,int slot,long due,float damage,boolean invulnerable,boolean noGravity,boolean noAi,boolean player,String team){
        Vec3 center(){return position.add(0,height*.5,0);}
        CompoundTag save(){CompoundTag tag=new CompoundTag();tag.putUUID("Entity",id);tag.putUUID("Owner",owner);tag.putString("Home",home.location().toString());vector(tag,"Position",position);vector(tag,"Direction",direction);tag.putFloat("Yaw",yaw);tag.putFloat("Pitch",pitch);tag.putFloat("Height",height);tag.putInt("Slot",slot);tag.putLong("Due",due);tag.putFloat("Damage",damage);tag.putBoolean("Invulnerable",invulnerable);tag.putBoolean("NoGravity",noGravity);tag.putBoolean("NoAI",noAi);tag.putBoolean("Player",player);tag.putString("Team",team);return tag;}
        static Entry load(CompoundTag tag){return new Entry(tag.getUUID("Entity"),tag.getUUID("Owner"),ResourceKey.create(Registries.DIMENSION,ResourceLocation.parse(tag.getString("Home"))),vector(tag,"Position"),vector(tag,"Direction"),tag.getFloat("Yaw"),tag.getFloat("Pitch"),tag.getFloat("Height"),tag.getInt("Slot"),tag.getLong("Due"),tag.getFloat("Damage"),tag.getBoolean("Invulnerable"),tag.getBoolean("NoGravity"),tag.getBoolean("NoAI"),tag.getBoolean("Player"),tag.getString("Team"));}
        private static void vector(CompoundTag tag,String key,Vec3 v){tag.putDouble(key+"X",v.x);tag.putDouble(key+"Y",v.y);tag.putDouble(key+"Z",v.z);}
        private static Vec3 vector(CompoundTag tag,String key){return new Vec3(tag.getDouble(key+"X"),tag.getDouble(key+"Y"),tag.getDouble(key+"Z"));}
    }
}
