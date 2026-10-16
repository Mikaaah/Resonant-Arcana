package nl.townsandkingdoms.resonantarcana;

import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.GhostTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.entity.projectile.RiftProjectileEntity;

/** Loaded only for the supported optional mod. Reuses APIs, never boss combat ticks. */
final class BossesRiseBridge {
    private static final DeferredRegister<EntityType<?>> ENTITIES=DeferredRegister.create(Registries.ENTITY_TYPE,ResonantArcana.ID);
    static final DeferredHolder<EntityType<?>,EntityType<Tentacle>> TENTACLE=ENTITIES.register("kraken_spell_visual",()->EntityType.Builder.<Tentacle>of(Tentacle::new,MobCategory.MISC).sized(2,4).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:kraken_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<Ice>> ICE=ENTITIES.register("ice_spell_visual",()->EntityType.Builder.<Ice>of(Ice::new,MobCategory.MISC).sized(.8f,2).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:ice_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<Rift>> RIFT=ENTITIES.register("rift_spell_visual",()->EntityType.Builder.<Rift>of(Rift::new,MobCategory.MISC).sized(.5f,.5f).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:rift_spell_visual"));
    private BossesRiseBridge(){}
    static void register(IEventBus bus){
        ENTITIES.register(bus);bus.addListener(BossesRiseBridge::attributes);
        if(net.neoforged.fml.loading.FMLEnvironment.dist==net.neoforged.api.distmarker.Dist.CLIENT)BossesRiseClient.register(bus);
    }
    private static void attributes(EntityAttributeCreationEvent e){e.put(TENTACLE.get(),GhostTentacleEntity.createAttributes().build());}
    static Entity tentacle(SpellEngine.Cast c){
        var visual=TENTACLE.get().create(c.level);visual.setOwnerUUID(c.owner);visual.setPos(c.pos);visual.setYRot(c.caster.getYRot());visual.setYBodyRot(c.caster.getYRot());
        c.level.addFreshEntity(visual);return visual;
    }
    static Entity ice(SpellEngine.Cast c,Vec3 pos,int delay){
        var visual=ICE.get().create(c.level);visual.setOwnerUUID(c.owner);visual.setPos(pos);visual.setDelay(delay);visual.setScale(4);visual.setDamage(0);
        c.level.addFreshEntity(visual);return visual;
    }
    static Entity rift(SpellEngine.Cast c){
        var visual=RIFT.get().create(c.level);visual.setOwner(c.caster);visual.setPos(c.pos);visual.setDeltaMovement(c.dir.scale(1.2));
        c.level.addFreshEntity(visual);return visual;
    }
    static final class Tentacle extends GhostTentacleEntity implements BossSpellVisual {
        private int visualAge;
        @SuppressWarnings("unchecked") Tentacle(EntityType<Tentacle> type,Level level){
            super((EntityType<GhostTentacleEntity>)(EntityType<?>)type,level);setNoAi(true);setNoGravity(true);setInvulnerable(true);setSilent(true);
        }
        @Override public void tick(){
            // Deliberately do not call the parent tick: its baseTick performs boss melee attacks.
            if(level().isClientSide())return;
            int age=++visualAge;
            if(age==1)triggerAnim("main_controller","spawn");
            getEntityData().set(ATTACK_ANIMTIME,age>=30&&age<50?50-age:0);
            if(age==56)triggerAnim("main_controller","death");
            if(age>=88)discard();
        }
        @Override public boolean isPickable(){return false;}
        @Override public boolean canBeCollidedWith(){return false;}
        @Override protected boolean shouldDropLoot(){return false;}
    }
    static final class Ice extends IceSpikeEntity implements BossSpellVisual {
        private int visualAge;
        @SuppressWarnings("unchecked") Ice(EntityType<Ice> type,Level level){super((EntityType<IceSpikeEntity>)(EntityType<?>)type,level);setNoGravity(true);setInvulnerable(true);}
        @Override public void tick(){
            // The original baseTick freezes entities even when damage is zero; never invoke it.
            if(level().isClientSide())return;
            int age=++visualAge;
            getEntityData().set(DATA_SPAWN_ANIM_TIME,Math.max(0,age-getDelay()));
            if(age>=getDelay()+12)discard();
        }
        @Override public java.util.Collection<net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider.DangerZone> getDangerZones(){return List.of();}
        @Override public boolean isPickable(){return false;}
    }
    static final class Rift extends RiftProjectileEntity implements BossSpellVisual {
        private int visualAge;
        Rift(EntityType<Rift> type,Level level){super(type,level);setNoGravity(true);setInvulnerable(true);}
        @Override public void tick(){
            // Server movement and collision belong to SpellEngine, avoiding the fixed-damage parent.
            if(level().isClientSide())setPos(position().add(getDeltaMovement()));
            else if(++visualAge>40)discard();
        }
        @Override protected void onHitEntity(net.minecraft.world.phys.EntityHitResult hit){}
        @Override protected void onHitBlock(net.minecraft.world.phys.BlockHitResult hit){}
        @Override public boolean isPickable(){return false;}
    }
}
