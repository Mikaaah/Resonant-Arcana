package nl.townsandkingdoms.resonantarcana;

import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.GhostTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.entity.projectile.RiftProjectileEntity;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardFireballEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager;

/** Loaded only for the supported optional mod. Reuses APIs, never boss combat ticks. */
final class BossesRiseBridge {
    private static final DeferredRegister<EntityType<?>> ENTITIES=DeferredRegister.create(Registries.ENTITY_TYPE,ResonantArcana.ID);
    static final DeferredHolder<EntityType<?>,EntityType<Tentacle>> TENTACLE=ENTITIES.register("kraken_spell_visual",()->EntityType.Builder.<Tentacle>of(Tentacle::new,MobCategory.MISC).sized(2,4).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:kraken_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<Ice>> ICE=ENTITIES.register("ice_spell_visual",()->EntityType.Builder.<Ice>of(Ice::new,MobCategory.MISC).sized(.8f,2).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:ice_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<Rift>> RIFT=ENTITIES.register("rift_spell_visual",()->EntityType.Builder.<Rift>of(Rift::new,MobCategory.MISC).sized(.5f,.5f).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:rift_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<Sand>> SAND=ENTITIES.register("sand_spell_visual",()->EntityType.Builder.<Sand>of(Sand::new,MobCategory.MISC).sized(1.3f,3).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:sand_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<Guard>> GUARD=ENTITIES.register("dragon_guard_spell_visual",()->EntityType.Builder.<Guard>of(Guard::new,MobCategory.MISC).sized(1.2f,2).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:dragon_guard_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<Sword>> SWORD=ENTITIES.register("helvar_sword_spell_visual",()->EntityType.Builder.<Sword>of(Sword::new,MobCategory.MISC).sized(.6f,2).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:helvar_sword_spell_visual"));
    private BossesRiseBridge(){}
    static void register(IEventBus bus){
        ENTITIES.register(bus);bus.addListener(BossesRiseBridge::attributes);
        if(net.neoforged.fml.loading.FMLEnvironment.dist==net.neoforged.api.distmarker.Dist.CLIENT)BossesRiseClient.register(bus);
    }
    private static void attributes(EntityAttributeCreationEvent e){
        e.put(TENTACLE.get(),GhostTentacleEntity.createAttributes().build());
        e.put(GUARD.get(),FlamingSkeletonGuardFireballEntity.createAttributes().build());
    }
    static Entity tentacle(SpellEngine.Cast c){
        var visual=TENTACLE.get().create(c.level);visual.setOwnerUUID(c.owner);visual.setPos(c.pos);visual.setYRot(c.caster.getYRot());visual.setYBodyRot(c.caster.getYRot());
        c.level.addFreshEntity(visual);tentacleRise(c.level,c.pos);return visual;
    }
    static Entity ice(SpellEngine.Cast c,Vec3 pos,int delay){
        var visual=ICE.get().create(c.level);visual.setOwnerUUID(c.owner);visual.setPos(pos);visual.setDelay(delay);visual.setScale(4);visual.setDamage(0);
        c.level.addFreshEntity(visual);return visual;
    }
    static Entity rift(SpellEngine.Cast c){
        var visual=RIFT.get().create(c.level);visual.setOwner(c.caster);visual.setPos(c.pos);visual.setDeltaMovement(c.dir.scale(1.2));
        c.level.addFreshEntity(visual);c.level.sendParticles(BossesRiseParticleTypes.RIFT_OPEN.get(),c.pos.x,c.pos.y,c.pos.z,1,0,0,0,0);return visual;
    }
    static Entity sand(SpellEngine.Cast c,Vec3 pos){
        var visual=SAND.get().create(c.level);visual.setOwner(c.caster);visual.setPos(pos);c.level.addFreshEntity(visual);return visual;
    }
    static Entity guard(SpellEngine.Cast c,Vec3 pos){
        var visual=GUARD.get().create(c.level);visual.setPos(pos);visual.setYRot(c.caster.getYRot());
        c.level.addFreshEntity(visual);return visual;
    }
    static Entity sword(SpellEngine.Cast c,Vec3 pos){
        var visual=SWORD.get().create(c.level);
        visual.getSlot(0).set(BossesRiseItems.KNIGHT_SWORD.get().getDefaultInstance());
        visual.setPos(pos);visual.setYRot(c.caster.getYRot());visual.setInvulnerable(true);
        c.level.addFreshEntity(visual);return visual;
    }
    static void tentacleRise(ServerLevel level,Vec3 pos){
        level.sendParticles(ParticleTypes.SPLASH,pos.x,pos.y+.15,pos.z,14,.55,.2,.55,.08);
        level.sendParticles(ParticleTypes.BUBBLE_POP,pos.x,pos.y+.1,pos.z,8,.5,.15,.5,.04);
    }
    static void waterCloud(ServerLevel level,Vec3 pos,int count){
        level.sendParticles(ParticleTypes.SPLASH,pos.x,pos.y+.2,pos.z,count,.8,.25,.8,.06);
    }
    static void tentacleImpact(ServerLevel level,Vec3 pos){
        level.sendParticles(BossesRiseParticleTypes.EXPLOSION.get(),pos.x,pos.y+.2,pos.z,1,0,0,0,0);
        level.sendParticles(ParticleTypes.SPLASH,pos.x,pos.y+.25,pos.z,22,1,.35,1,.1);
        level.sendParticles(ParticleTypes.BUBBLE_POP,pos.x,pos.y+.1,pos.z,12,.8,.2,.8,.06);
    }
    static void iceImpact(ServerLevel level,Vec3 pos){
        level.sendParticles(BossesRiseParticleTypes.SNOW_CLOUD.get(),pos.x,pos.y+.2,pos.z,12,.5,.6,.5,.04);
        level.sendParticles(BossesRiseParticleTypes.SNOW_FLAKE.get(),pos.x,pos.y+.5,pos.z,18,.6,.7,.6,.08);
    }
    static void riftTrail(ServerLevel level,Vec3 pos){
        level.sendParticles(BossesRiseParticleTypes.RIFT.get(),pos.x,pos.y,pos.z,2,.12,.12,.12,.01);
    }
    static void riftClose(ServerLevel level,Vec3 pos){
        level.sendParticles(BossesRiseParticleTypes.RIFT_CLOSE.get(),pos.x,pos.y,pos.z,1,0,0,0,0);
    }
    static void castCue(ServerLevel level,Spell spell,Vec3 pos){
        switch(spell.kind){
            case BOSS_TENTACLE,BOSS_WRATH -> level.sendParticles(ParticleTypes.SPLASH,pos.x,pos.y,pos.z,4,.25,.2,.25,.02);
            case BOSS_ICE -> level.sendParticles(BossesRiseParticleTypes.SNOW_FLAKE.get(),pos.x,pos.y,pos.z,5,.25,.3,.25,.02);
            case BOSS_RIFT,BOSS_SWORD -> level.sendParticles(BossesRiseParticleTypes.RIFT.get(),pos.x,pos.y,pos.z,2,.15,.15,.15,.01);
            case BOSS_SAND -> level.sendParticles(BossesRiseParticleTypes.FALLING_DUST.get(),pos.x,pos.y,pos.z,6,.25,.15,.25,.03);
            case BOSS_DRAGON -> level.sendParticles(BossesRiseParticleTypes.BLAZING_FLAME.get(),pos.x,pos.y,pos.z,5,.2,.2,.2,.025);
            default -> {}
        }
    }
    static void sandImpact(ServerLevel level,Vec3 pos){
        level.sendParticles(BossesRiseParticleTypes.SAND_ROCK.get(),pos.x,pos.y+.4,pos.z,12,.8,.35,.8,.07);
        level.sendParticles(BossesRiseParticleTypes.GROUND_DUST.get(),pos.x,pos.y+.15,pos.z,12,.8,.2,.8,.04);
    }
    static void dragonImpact(ServerLevel level,Vec3 pos,int count){
        level.sendParticles(BossesRiseParticleTypes.BLAZING_FLAME.get(),pos.x,pos.y+.8,pos.z,count,1,.7,1,.08);
        level.sendParticles(BossesRiseParticleTypes.BLAZING_CLOUD.get(),pos.x,pos.y+.5,pos.z,Math.max(3,count/3),.8,.5,.8,.035);
    }
    static void swordImpact(ServerLevel level,Vec3 pos){
        level.sendParticles(BossesRiseParticleTypes.RIFT_CLOSE.get(),pos.x,pos.y+.2,pos.z,1,0,0,0,0);
        level.sendParticles(ParticleTypes.SOUL,pos.x,pos.y+.5,pos.z,10,.8,.25,.8,.04);
        level.sendParticles(ParticleTypes.CRIT,pos.x,pos.y+.5,pos.z,8,.65,.3,.65,.08);
    }
    static final class Sword extends Display.ItemDisplay implements BossSpellVisual {
        private int visualAge;
        Sword(EntityType<Sword> type,Level level){super(type,level);setNoGravity(true);}
        @Override public void tick(){super.tick();if(!level().isClientSide()&&++visualAge>=55)discard();}
        @Override public boolean isPickable(){return false;}
        @Override public boolean canBeCollidedWith(){return false;}
    }
    static final class Tentacle extends GhostTentacleEntity implements BossSpellVisual {
        private int visualAge;
        @SuppressWarnings("unchecked") Tentacle(EntityType<Tentacle> type,Level level){
            super((EntityType<GhostTentacleEntity>)(EntityType<?>)type,level);setNoAi(true);setNoGravity(true);setInvulnerable(true);setSilent(true);
        }
        @Override public void tick(){
            // Deliberately do not call the parent tick: its baseTick performs boss melee attacks.
            tickCount++;
            if(level().isClientSide())return;
            int age=++visualAge;
            if(age==30)triggerAnim("main_controller","slam");
            if(age==56)triggerAnim("main_controller","despawn");
            if(age>=88)discard();
        }
        @Override public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
            controllers.add(new AnimationController<>(this,"main_controller",0,state->state.setAndContinue(RawAnimation.begin().thenLoop("1st_phase_idle")))
                .triggerableAnim("slam",RawAnimation.begin().thenPlay("light_slam"))
                .triggerableAnim("despawn",RawAnimation.begin().thenPlay("death_tentacle")));
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
    static final class Sand extends SandColumnEntity implements BossSpellVisual {
        private int visualAge;
        @SuppressWarnings("unchecked") Sand(EntityType<Sand> type,Level level){super((EntityType<SandColumnEntity>)(EntityType<?>)type,level);setNoGravity(true);setInvulnerable(true);}
        @Override public void tick(){
            tickCount++;if(level().isClientSide())return;
            if(++visualAge==1)triggerAnim("base_controller","emerge");
            if(visualAge>=62)discard();
        }
        @Override public boolean isPickable(){return false;}
        @Override public boolean canBeCollidedWith(){return false;}
    }
    static final class Guard extends FlamingSkeletonGuardFireballEntity implements BossSpellVisual {
        private int visualAge;
        @SuppressWarnings("unchecked") Guard(EntityType<Guard> type,Level level){super((EntityType<FlamingSkeletonGuardFireballEntity>)(EntityType<?>)type,level);setNoAi(true);setNoGravity(true);setInvulnerable(true);setSilent(true);}
        @Override public void tick(){tickCount++;if(!level().isClientSide()&&++visualAge>=85)discard();}
        @Override public boolean isPickable(){return false;}
        @Override public boolean canBeCollidedWith(){return false;}
        @Override protected boolean shouldDropLoot(){return false;}
    }
}
