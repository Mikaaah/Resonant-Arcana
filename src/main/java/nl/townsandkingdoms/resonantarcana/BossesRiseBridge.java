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
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
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
    static final DeferredHolder<EntityType<?>,EntityType<Sword>> SWORD=ENTITIES.register("helvar_sword_spell_visual",()->EntityType.Builder.<Sword>of(Sword::new,MobCategory.MISC).sized(.6f,2).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:helvar_sword_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<FlameOrb>> FLAME=ENTITIES.register("dragon_flame_spell_visual",()->EntityType.Builder.<FlameOrb>of(FlameOrb::new,MobCategory.MISC).sized(.8f,.8f).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:dragon_flame_spell_visual"));
    static final DeferredHolder<EntityType<?>,EntityType<FullKraken>> FULL_KRAKEN=ENTITIES.register("kraken_grasp_visual",()->EntityType.Builder.<FullKraken>of(FullKraken::new,MobCategory.MISC).sized(18,10).noSave().clientTrackingRange(8).updateInterval(1).build("resonant_arcana:kraken_grasp_visual"));
    static final double TENTACLE_BURY_DEPTH=2.25;
    static final float SWORD_SCALE=5f;
    // The original held model's blade tip is at -9 model pixels before scaling.
    static final double SWORD_TIP_OFFSET=9.0/16*SWORD_SCALE;
    static final double SWORD_TOP_OFFSET=24.0/16*SWORD_SCALE;
    private BossesRiseBridge(){}
    static void register(IEventBus bus){
        ENTITIES.register(bus);bus.addListener(BossesRiseBridge::attributes);
        if(net.neoforged.fml.loading.FMLEnvironment.dist==net.neoforged.api.distmarker.Dist.CLIENT)BossesRiseClient.register(bus);
    }
    private static void attributes(EntityAttributeCreationEvent e){
        e.put(TENTACLE.get(),GhostTentacleEntity.createAttributes().build());
        e.put(FULL_KRAKEN.get(),GhostTentacleEntity.createAttributes().build());
    }
    static Entity tentacle(SpellEngine.Cast c){
        if(c.spell==Spell.KRAKENS_GRASP){
            var visual=FULL_KRAKEN.get().create(c.level);visual.setOwnerUUID(c.owner);visual.origin=c.pos;
            visual.setPos(c.pos.add(0,-3.25,0));visual.setYRot(tentacleYaw(c.dir));visual.yRotO=visual.getYRot();
            c.level.addFreshEntity(visual);tentacleRise(c.level,c.pos);return visual;
        }
        return tentacle(c,c.pos,c.dir);
    }
    static float tentacleYaw(Vec3 direction){return (float)Math.toDegrees(Math.atan2(-direction.x,direction.z));}
    static Vec3 tentacleAnchor(Vec3 ground){return ground.add(0,-TENTACLE_BURY_DEPTH,0);}
    static Entity tentacle(SpellEngine.Cast c,Vec3 ground,Vec3 facing){
        var visual=TENTACLE.get().create(c.level);visual.setOwnerUUID(c.owner);visual.setPos(tentacleAnchor(ground));
        float yaw=tentacleYaw(facing);visual.setYRot(yaw);visual.yRotO=yaw;
        visual.setYBodyRot(yaw);visual.yBodyRotO=yaw;visual.setYHeadRot(yaw);visual.yHeadRotO=yaw;
        c.level.addFreshEntity(visual);tentacleRise(c.level,ground);return visual;
    }
    static Entity ice(SpellEngine.Cast c,Vec3 pos,int delay){
        var visual=ICE.get().create(c.level);visual.setOwnerUUID(c.owner);visual.setPos(pos);visual.setDelay(delay);visual.setScale(4);visual.setDamage(0);
        visual.holdTicks=c.spell==Spell.YETIS_GLACIAL_WALL?28:0;
        c.level.addFreshEntity(visual);return visual;
    }
    static Entity rift(SpellEngine.Cast c){
        var visual=RIFT.get().create(c.level);visual.setOwner(c.caster);visual.setPos(c.pos);visual.setDeltaMovement(c.dir.scale(1.2));
        c.level.addFreshEntity(visual);c.level.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME,c.pos.x,c.pos.y,c.pos.z,1,0,0,0,0);return visual;
    }
    static Entity sand(SpellEngine.Cast c,Vec3 pos){
        return sand(c,pos,0);
    }
    static Entity sand(SpellEngine.Cast c,Vec3 pos,int delay){
        var visual=SAND.get().create(c.level);visual.setOwner(c.caster);visual.setPos(pos);visual.delay=delay;visual.setInvisible(delay>0);c.level.addFreshEntity(visual);return visual;
    }
    static Entity flame(SpellEngine.Cast c,Vec3 pos){
        var visual=FLAME.get().create(c.level);visual.setOwner(c.caster);visual.setPos(pos);c.level.addFreshEntity(visual);return visual;
    }
    static Entity sword(SpellEngine.Cast c,Vec3 pos){
        var visual=SWORD.get().create(c.level);
        visual.getSlot(0).set(BossesRiseItems.KNIGHT_SWORD.get().getDefaultInstance());
        // The held model has a native -90 degree bone turn; compensate so its blade face
        // is visible from the caster rather than presenting only its thin edge.
        visual.setPos(pos);visual.setYRot(c.caster.getYRot()+90);visual.setInvulnerable(true);
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
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.SMALL_FLAME,pos.x,pos.y,pos.z,2,.12,.12,.12,.01);
    }
    static void riftClose(ServerLevel level,Vec3 pos){
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.ASH,pos.x,pos.y,pos.z,1,0,0,0,0);
    }
    static void castCue(ServerLevel level,Spell spell,Vec3 pos){
        switch(spell.kind){
            case BOSS_TENTACLE,BOSS_WRATH -> level.sendParticles(ParticleTypes.SPLASH,pos.x,pos.y,pos.z,4,.25,.2,.25,.02);
            case BOSS_ICE -> level.sendParticles(BossesRiseParticleTypes.SNOW_FLAKE.get(),pos.x,pos.y,pos.z,5,.25,.3,.25,.02);
            case BOSS_RIFT,BOSS_SWORD -> level.sendParticles(net.minecraft.core.particles.ParticleTypes.SMALL_FLAME,pos.x,pos.y,pos.z,2,.15,.15,.15,.01);
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
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.ASH,pos.x,pos.y+.2,pos.z,1,0,0,0,0);
        level.sendParticles(ParticleTypes.FLAME,pos.x,pos.y+.5,pos.z,10,.8,.25,.8,.04);
        level.sendParticles(ParticleTypes.CRIT,pos.x,pos.y+.5,pos.z,8,.65,.3,.65,.08);
    }
    static final class Sword extends Display.ItemDisplay implements BossSpellVisual {
        private int visualAge;
        Sword(EntityType<Sword> type,Level level){super(type,level);setNoGravity(true);}
        @Override public void tick(){super.tick();if(!level().isClientSide()&&++visualAge>=240)discard();}
        @Override public boolean isPickable(){return false;}
        @Override public boolean canBeCollidedWith(){return false;}
    }
    static final class FlameOrb extends BlazingFireBallEntity implements BossSpellVisual {
        private static final net.minecraft.world.item.ItemStack CORE=net.minecraft.world.item.Items.FIRE_CHARGE.getDefaultInstance();
        private int visualAge;
        FlameOrb(EntityType<FlameOrb> type,Level level){super(type,level);setNoGravity(true);setInvulnerable(true);setSilent(true);}
        // Bosses'Rise uses an invisible placeholder item and generates its body in combat ticks.
        // Our harmless adapter needs an explicit visible core because those ticks are disabled.
        @Override public net.minecraft.world.item.ItemStack getItem(){return CORE;}
        @Override public void tick(){
            // The original projectile tick explodes and edits terrain. Only SpellEngine may hit anything.
            if(!level().isClientSide()&&++visualAge>90)discard();
        }
        @Override public void onHitEntity(net.minecraft.world.phys.EntityHitResult hit){}
        @Override public void onHitBlock(net.minecraft.world.phys.BlockHitResult hit){}
        @Override public boolean isPickable(){return false;}
        @Override public boolean canBeCollidedWith(){return false;}
    }
    /** Full installed cinematic geometry, with no original boss AI or damage ticks. */
    static final class FullKraken extends GhostTentacleEntity implements BossSpellVisual {
        private int visualAge;private Vec3 origin;
        @SuppressWarnings("unchecked") FullKraken(EntityType<FullKraken> type,Level level){
            super((EntityType<GhostTentacleEntity>)(EntityType<?>)type,level);setNoAi(true);setNoGravity(true);setInvulnerable(true);setSilent(true);
        }
        @Override public void tick(){
            float yaw=getYRot();setYBodyRot(yaw);yBodyRotO=yaw;setYHeadRot(yaw);yHeadRotO=yaw;
            if(level().isClientSide())return;
            int age=++visualAge;if(origin==null)origin=position().add(0,3.25,0);
            double rise=Math.min(1,age/24.0),sink=Math.max(0,(age-55)/28.0);
            setPos(origin.add(0,-3.25+rise*2.0-sink*3.0,0));if(age>=88)discard();
        }
        // The native cinematic tracks hide the head or run a 24-second cutscene. Use the full
        // rest pose and the spell-owned rise/slam/sink timing, without invoking either track.
        @Override public void registerControllers(AnimatableManager.ControllerRegistrar controllers){}
        @Override public boolean isPickable(){return false;}
        @Override public boolean canBeCollidedWith(){return false;}
        @Override protected boolean shouldDropLoot(){return false;}
    }
    static final class Tentacle extends GhostTentacleEntity implements BossSpellVisual {
        private int visualAge;
        @SuppressWarnings("unchecked") Tentacle(EntityType<Tentacle> type,Level level){
            super((EntityType<GhostTentacleEntity>)(EntityType<?>)type,level);setNoAi(true);setNoGravity(true);setInvulnerable(true);setSilent(true);
        }
        @Override public void tick(){
            // Deliberately do not call the parent tick: its baseTick performs boss melee attacks.
            // The level already increments tickCount; incrementing again doubles client animation speed.
            if(level().isClientSide()){
                // Body/head yaw fields are not spawn-packet data, and our visual skips LivingEntity AI.
                // Copy the synchronized entity yaw so GeckoLib does not render every tentacle at yaw zero.
                float yaw=getYRot();setYBodyRot(yaw);yBodyRotO=yaw;setYHeadRot(yaw);yHeadRotO=yaw;return;
            }
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
        private int visualAge,holdTicks;
        @SuppressWarnings("unchecked") Ice(EntityType<Ice> type,Level level){super((EntityType<IceSpikeEntity>)(EntityType<?>)type,level);setNoGravity(true);setInvulnerable(true);}
        @Override public void tick(){
            // The original baseTick freezes entities even when damage is zero; never invoke it.
            if(level().isClientSide())return;
            int age=++visualAge;
            int animationAge=age-getDelay();
            if(holdTicks>0&&animationAge>=8)animationAge=Math.max(8,animationAge-holdTicks);
            getEntityData().set(DATA_SPAWN_ANIM_TIME,Math.max(0,animationAge));
            if(age>=getDelay()+12+holdTicks)discard();
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
        private int visualAge,delay;
        @SuppressWarnings("unchecked") Sand(EntityType<Sand> type,Level level){super((EntityType<SandColumnEntity>)(EntityType<?>)type,level);setNoGravity(true);setInvulnerable(true);}
        @Override public void tick(){
            if(level().isClientSide())return;
            if(++visualAge==delay+1){setInvisible(false);triggerAnim("base_controller","emerge");}
            if(visualAge>=delay+62)discard();
        }
        @Override public boolean isPickable(){return false;}
        @Override public boolean canBeCollidedWith(){return false;}
    }
}
