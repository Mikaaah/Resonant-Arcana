package nl.townsandkingdoms.resonantarcana;

import io.redspace.ironsspellbooks.api.spells.ICastDataSerializable;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

/** Native recast data survives the same serialization lifecycle as Iron's own recasts. */
public final class ReversalData implements ICastDataSerializable {
    Vec3 point=Vec3.ZERO;ResourceLocation dimension=ResourceLocation.parse("minecraft:overworld");
    public ReversalData(){}
    ReversalData(ServerPlayer p){point=p.position();dimension=p.serverLevel().dimension().location();}
    boolean safe(ServerPlayer p){
        if(!p.serverLevel().dimension().location().equals(dimension)||!Double.isFinite(point.x)||!Double.isFinite(point.y)||!Double.isFinite(point.z)||p.position().distanceToSqr(point)>16*16)return false;
        var level=p.serverLevel();Vec3 delta=point.subtract(p.position());
        return level.hasChunkAt(net.minecraft.core.BlockPos.containing(point))&&!level.getFluidState(net.minecraft.core.BlockPos.containing(point)).is(net.minecraft.tags.FluidTags.LAVA)
            &&level.noCollision(p,p.getBoundingBox().move(delta));
    }
    void returnPlayer(ServerPlayer p){
        if(!safe(p)){p.displayClientMessage(net.minecraft.network.chat.Component.literal("The return point is obstructed or out of reach."),true);return;}
        Vec3 old=p.getEyePosition();p.teleportTo(point.x,point.y,point.z);p.fallDistance=0;
        SpellVisuals.send(p.serverLevel(),Spell.CRIMSON_REVERSAL,SpellVisuals.Stage.LINK,old,p.getEyePosition(),1.2);
        SpellAudio.world(p.serverLevel(),point,"irons_spellbooks:cast.blood_step",1,.9f);
    }
    @Override public void reset(){} // The immutable anchor expires with its native recast instance.
    @Override public void writeToBuffer(FriendlyByteBuf b){b.writeDouble(point.x);b.writeDouble(point.y);b.writeDouble(point.z);b.writeResourceLocation(dimension);}
    @Override public void readFromBuffer(FriendlyByteBuf b){point=new Vec3(b.readDouble(),b.readDouble(),b.readDouble());dimension=b.readResourceLocation();}
    @Override public CompoundTag serializeNBT(HolderLookup.Provider provider){var tag=new CompoundTag();tag.putDouble("x",point.x);tag.putDouble("y",point.y);tag.putDouble("z",point.z);tag.putString("dimension",dimension.toString());return tag;}
    @Override public void deserializeNBT(HolderLookup.Provider provider,CompoundTag tag){point=new Vec3(tag.getDouble("x"),tag.getDouble("y"),tag.getDouble("z"));dimension=ResourceLocation.tryParse(tag.getString("dimension"));if(dimension==null)dimension=ResourceLocation.parse("minecraft:overworld");}
}
