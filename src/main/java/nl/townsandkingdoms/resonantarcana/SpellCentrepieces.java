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
    static final class Prop extends Display.BlockDisplay {
        private int remaining=245;
        Prop(EntityType<?> type,Level level){super(type,level);setNoGravity(true);setInvulnerable(true);setSilent(true);}
        void configure(CompoundTag tag){super.readAdditionalSaveData(tag);}
        @Override public boolean isPickable(){return false;}
        @Override public void tick(){super.tick();if(!level().isClientSide&&--remaining<=0)discard();}
    }
}
