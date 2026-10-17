package nl.townsandkingdoms.resonantarcana;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Temporary, server-owned block models. Never changes a player's blocks or drops loot. */
final class SpellCentrepieces {
    private static final DeferredRegister<EntityType<?>> TYPES=DeferredRegister.create(Registries.ENTITY_TYPE,ResonantArcana.ID);
    static final DeferredHolder<EntityType<?>,EntityType<Prop>> PROP=TYPES.register("spell_centrepiece",()->EntityType.Builder.<Prop>of(Prop::new,MobCategory.MISC)
        .sized(1,2).noSave().clientTrackingRange(8).updateInterval(20).build("resonant_arcana:spell_centrepiece"));
    static final DeferredHolder<EntityType<?>,EntityType<HolySword>> SWORD=TYPES.register("holy_sword",()->EntityType.Builder.<HolySword>of(HolySword::new,MobCategory.MISC)
        .sized(3,7).noSave().clientTrackingRange(8).updateInterval(2).build("resonant_arcana:holy_sword"));
    static void holySword(SpellEngine.Cast c){
        Vec3 ground=BossesRiseCompat.ground(c.level,c.pos);if(ground==null)ground=c.pos;
        var sword=new HolySword(SWORD.get(),c.level);sword.origin=ground;
        double available=SpellEngine.clipLoaded(c.level,ground.add(0,.1,0),new Vec3(0,1,0),32,c.caster).y-ground.y;
        float scale=(float)Math.clamp((available-.3)/1.42,.5,4);
        // ItemDisplayRenderer adds a 180-degree Y rotation: compensate before pointing the tip down.
        sword.baseHeight=scale*.62-.12;sword.startHeight=Math.max(sword.baseHeight,Math.min(sword.baseHeight+6,available-scale*.72-.2));
        var tag=new CompoundTag();tag.putString("item_display","none");tag.putFloat("view_range",2);tag.putFloat("width",20);tag.putFloat("height",24);tag.putInt("teleport_duration",2);
        var brightness=new CompoundTag();brightness.putInt("block",15);brightness.putInt("sky",15);tag.put("brightness",brightness);
        var transform=new com.mojang.math.Transformation(new org.joml.Vector3f(),new org.joml.Quaternionf().rotationZ((float)(3*Math.PI/4)),new org.joml.Vector3f(scale),new org.joml.Quaternionf());
        tag.put("transformation",com.mojang.math.Transformation.EXTENDED_CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE,transform).getOrThrow());
        sword.configure(tag);sword.getSlot(0).set(net.minecraft.world.item.Items.GOLDEN_SWORD.getDefaultInstance());sword.setYRot(c.caster.getYRot());sword.setPos(ground.add(0,sword.startHeight,0));
        if(c.level.addFreshEntity(sword))c.centrepieces.add(sword);
    }
    static Vec3 atomicTip(net.minecraft.server.level.ServerPlayer p){
        Vec3 dir=p.getLookAngle().multiply(1,0,1).normalize();if(dir.lengthSqr()<.01)dir=new Vec3(0,0,1);
        return p.position().add(dir.scale(1.3)).add(0,2.4,0);
    }
    static void atomicFocus(SpellEngine.Cast c){
        var sword=new HolySword(SWORD.get(),c.level);sword.life=900;
        var tag=new CompoundTag();tag.putString("item_display","none");tag.putFloat("view_range",2);tag.putFloat("width",5);tag.putFloat("height",5);tag.putInt("teleport_duration",2);
        var brightness=new CompoundTag();brightness.putInt("block",15);brightness.putInt("sky",15);tag.put("brightness",brightness);
        var transform=new com.mojang.math.Transformation(new org.joml.Vector3f(),new org.joml.Quaternionf().rotationZ((float)(-Math.PI/4)),new org.joml.Vector3f(1.45f),new org.joml.Quaternionf());
        tag.put("transformation",com.mojang.math.Transformation.EXTENDED_CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE,transform).getOrThrow());
        sword.configure(tag);sword.getSlot(0).set(net.minecraft.world.item.Items.NETHERITE_SWORD.getDefaultInstance());c.centrepieces.add(sword);
        moveAtomicFocus(c);c.level.addFreshEntity(sword);
    }
    static void moveAtomicFocus(SpellEngine.Cast c){for(var entity:c.centrepieces)if(entity instanceof HolySword sword){sword.setYRot(c.caster.getYRot());sword.setPos(atomicTip(c.caster).add(0,-.9,0));}}
    static void moveHolySword(SpellEngine.Cast c){for(var entity:c.centrepieces)if(entity instanceof HolySword sword){
        double progress=Math.clamp(c.age/(double)(c.duration-8),0,1);sword.setPos(sword.origin.add(0,sword.baseHeight+(sword.startHeight-sword.baseHeight)*(1-progress)*(1-progress),0));
    }}
    private SpellCentrepieces(){}
    static void register(IEventBus bus){TYPES.register(bus);}
    static void place(SpellEngine.Cast c){
        Vec3 ground=BossesRiseCompat.ground(c.level,c.pos);
        if(ground==null||!SpellEngine.visible(c,c.caster.getEyePosition(),ground.add(0,.2,0)))return;
        c.pos=ground.add(0,.15,0);
        switch(c.spell){
            case WISP_LANTERN -> {
                model(c,Blocks.DEEPSLATE_TILE_WALL.defaultBlockState(),ground);
                model(c,Blocks.SOUL_LANTERN.defaultBlockState(),ground.add(0,1,0));
            }
            case SPORE_TOTEM -> {
                model(c,Blocks.MUSHROOM_STEM.defaultBlockState(),ground);
                model(c,Blocks.SHROOMLIGHT.defaultBlockState(),ground.add(0,1,0));
                model(c,Blocks.RED_MUSHROOM.defaultBlockState(),ground.add(0,2,0));
            }
            case DREAD_EFFIGY -> {
                model(c,Blocks.POLISHED_BLACKSTONE_WALL.defaultBlockState(),ground);
                model(c,Blocks.CARVED_PUMPKIN.defaultBlockState(),ground.add(0,1,0));
                model(c,Blocks.SOUL_TORCH.defaultBlockState(),ground.add(0,2,0));
            }
            case CONSECRATION -> {
                model(c,Blocks.CHISELED_QUARTZ_BLOCK.defaultBlockState(),ground);
                model(c,Blocks.END_ROD.defaultBlockState(),ground.add(0,1,0));
            }
            default -> throw new IllegalArgumentException("Missing totem centrepiece: "+c.spell);
        }
    }
    private static void model(SpellEngine.Cast c,BlockState state,Vec3 pos){
        var block=net.minecraft.core.BlockPos.containing(pos);
        if(!c.level.hasChunkAt(block)||!c.level.getBlockState(block).getCollisionShape(c.level,block).isEmpty())return;
        var prop=new Prop(PROP.get(),c.level);
        var tag=new CompoundTag();tag.put("block_state",NbtUtils.writeBlockState(state));
        tag.putFloat("view_range",1);tag.putFloat("width",1.2f);tag.putFloat("height",2.5f);
        prop.configure(tag);prop.setPos(pos.x-.5,pos.y,pos.z-.5);prop.remaining=c.duration+5;
        if(c.level.addFreshEntity(prop))c.centrepieces.add(prop);
    }
    static void cleanup(SpellEngine.Cast c){c.centrepieces.forEach(net.minecraft.world.entity.Entity::discard);c.centrepieces.clear();}
    static final class HolySword extends Display.ItemDisplay {
        private Vec3 origin;private double baseHeight,startHeight;private int life=115;
        HolySword(EntityType<?> type,Level level){super(type,level);setNoGravity(true);setInvulnerable(true);setSilent(true);}
        void configure(CompoundTag tag){super.readAdditionalSaveData(tag);}
        @Override public boolean isPickable(){return false;}
        @Override public void tick(){super.tick();if(!level().isClientSide&&--life<=0)discard();}
    }
    static final class Prop extends Display.BlockDisplay {
        private int remaining=245;
        Prop(EntityType<?> type,Level level){super(type,level);setNoGravity(true);setInvulnerable(true);setSilent(true);}
        void configure(CompoundTag tag){super.readAdditionalSaveData(tag);}
        @Override public boolean isPickable(){return false;}
        @Override public void tick(){super.tick();if(!level().isClientSide&&--remaining<=0)discard();}
    }
}
