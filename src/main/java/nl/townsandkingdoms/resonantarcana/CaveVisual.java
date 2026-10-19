package nl.townsandkingdoms.resonantarcana;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Harmless tracking marker. Native boss/projectile instances exist only as client render proxies. */
final class CaveVisual extends Entity implements BossSpellVisual {
    private static final DeferredRegister<EntityType<?>> TYPES=DeferredRegister.create(Registries.ENTITY_TYPE,ResonantArcana.ID);
    static final net.neoforged.neoforge.registries.DeferredHolder<EntityType<?>,EntityType<CaveVisual>> TYPE=TYPES.register("cave_spell_visual",()->EntityType.Builder.<CaveVisual>of(CaveVisual::new,MobCategory.MISC).sized(2,3).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:cave_spell_visual"));
    private static final EntityDataAccessor<Integer> MODEL=SynchedEntityData.defineId(CaveVisual.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIFE=SynchedEntityData.defineId(CaveVisual.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SCALE=SynchedEntityData.defineId(CaveVisual.class,EntityDataSerializers.FLOAT);
    static final String[] MODELS={"vallumraptor","dinosaur_spirit","luxtructosaurus","tremorzilla","hullbreaker","forsaken","nuclear_bomb","desolate_dagger","gumball","spinning_peppermint","sugar_staff_hex","gum_worm","tephra","wave","water_bolt"};
    private static final EntityDataAccessor<Integer> OWNER=SynchedEntityData.defineId(CaveVisual.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT=SynchedEntityData.defineId(CaveVisual.class,EntityDataSerializers.INT);
    long lastBeamFrame=-1;
    Entity renderProxy;
    CaveVisual(EntityType<?> type,Level level){super(type,level);noPhysics=true;setNoGravity(true);setInvulnerable(true);}
    static void register(IEventBus bus){TYPES.register(bus);}
    static CaveVisual spawn(SpellEngine.Cast cast,int model,Vec3 position,float scale,int ticks){
        var visual=TYPE.get().create(cast.level);visual.entityData.set(MODEL,model);visual.entityData.set(VARIANT,model==18?1:CavesCompat.beamVariant(cast.caster));visual.entityData.set(OWNER,cast.caster.getId());visual.entityData.set(LIFE,ticks);visual.entityData.set(SCALE,scale);
        visual.setPos(position);visual.setYRot((float)Math.toDegrees(Math.atan2(-cast.dir.x,cast.dir.z)));visual.yRotO=visual.getYRot();cast.level.addFreshEntity(visual);cast.centrepieces.add(visual);return visual;
    }
    int variant(){return entityData.get(VARIANT);}
    int owner(){return entityData.get(OWNER);}
    int model(){return entityData.get(MODEL);}float scale(){return entityData.get(SCALE);}
    @Override protected void defineSynchedData(SynchedEntityData.Builder builder){builder.define(VARIANT,0).define(OWNER,-1).define(MODEL,0).define(LIFE,60).define(SCALE,1f);}
    @Override public void tick(){super.tick();if(getDeltaMovement().lengthSqr()>0)setPos(position().add(getDeltaMovement()));if(tickCount>entityData.get(LIFE))discard();}
    @Override public boolean isPickable(){return false;}
    @Override public boolean isPushable(){return false;}
    @Override public boolean canBeCollidedWith(){return false;}
    @Override protected void readAdditionalSaveData(CompoundTag tag){discard();}
    @Override protected void addAdditionalSaveData(CompoundTag tag){}
}
