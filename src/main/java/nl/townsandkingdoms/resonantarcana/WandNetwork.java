package nl.townsandkingdoms.resonantarcana;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class WandNetwork {
    private WandNetwork() {}
    public record Action(int action) implements CustomPacketPayload {
        public static final Type<Action> TYPE=new Type<>(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"action"));
        public static final StreamCodec<RegistryFriendlyByteBuf,Action> CODEC=StreamCodec.of((buf,msg)->buf.writeVarInt(msg.action),buf->new Action(buf.readVarInt()));
        @Override public Type<Action> type(){return TYPE;}
    }
    public record Select(ResourceLocation wand,int slot) implements CustomPacketPayload {
        public static final Type<Select> TYPE=new Type<>(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"select"));
        public static final StreamCodec<RegistryFriendlyByteBuf,Select> CODEC=StreamCodec.of((b,m)->{b.writeResourceLocation(m.wand);b.writeVarInt(m.slot);},b->new Select(b.readResourceLocation(),b.readVarInt()));
        @Override public Type<Select> type(){return TYPE;}
    }
    /** Pure visual burst: the server never spawns damaging rocket entities. */
    public record Burst(double x,double y,double z,int color,float scale) implements CustomPacketPayload {
        public Burst(double x,double y,double z,int color){this(x,y,z,color,1);}
        public static final Type<Burst> TYPE=new Type<>(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"burst"));
        public static final StreamCodec<RegistryFriendlyByteBuf,Burst> CODEC=StreamCodec.of((b,m)->{b.writeDouble(m.x);b.writeDouble(m.y);b.writeDouble(m.z);b.writeInt(m.color);b.writeFloat(m.scale);},b->new Burst(b.readDouble(),b.readDouble(),b.readDouble(),b.readInt(),b.readFloat()));
        @Override public Type<Burst> type(){return TYPE;}
    }
    /** One finite native effect; endpoints orient beams and travelling spells. */
    public record Visual(int spell,int stage,double x,double y,double z,double endX,double endY,double endZ,float size) implements CustomPacketPayload {
        public static final Type<Visual> TYPE=new Type<>(ResourceLocation.fromNamespaceAndPath(ResonantArcana.ID,"visual"));
        public static final StreamCodec<RegistryFriendlyByteBuf,Visual> CODEC=StreamCodec.of((b,m)->{
            b.writeVarInt(m.spell);b.writeVarInt(m.stage);b.writeDouble(m.x);b.writeDouble(m.y);b.writeDouble(m.z);
            b.writeDouble(m.endX);b.writeDouble(m.endY);b.writeDouble(m.endZ);b.writeFloat(m.size);
        },b->new Visual(b.readVarInt(),b.readVarInt(),b.readDouble(),b.readDouble(),b.readDouble(),b.readDouble(),b.readDouble(),b.readDouble(),b.readFloat()));
        @Override public Type<Visual> type(){return TYPE;}
        public boolean valid(){
            return spell>=0&&spell<Spell.values().length&&stage>=0&&stage<SpellVisuals.Stage.values().length
                &&Double.isFinite(x)&&Double.isFinite(y)&&Double.isFinite(z)&&Double.isFinite(endX)&&Double.isFinite(endY)&&Double.isFinite(endZ)
                &&Float.isFinite(size)&&size>=0.1f&&size<=16&&Math.abs(x)<=30_000_000&&Math.abs(y)<=30_000_000&&Math.abs(z)<=30_000_000
                &&Math.abs(endX)<=30_000_000&&Math.abs(endY)<=30_000_000&&Math.abs(endZ)<=30_000_000
                &&new net.minecraft.world.phys.Vec3(x,y,z).distanceToSqr(new net.minecraft.world.phys.Vec3(endX,endY,endZ))<=128*128;
        }
    }
    static void select(ServerPlayer player,Select message){
        if(message.slot>=0&&player.getMainHandItem().getItem() instanceof WandItem wand&&message.slot<wand.spells.size()
            &&net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(player.getMainHandItem().getItem()).equals(message.wand))SpellEngine.action(player,message.slot+3);
    }
    public static void register(RegisterPayloadHandlersEvent e) {
        var r=e.registrar("10");
        r.playToServer(Action.TYPE,Action.CODEC,(msg,ctx)->ctx.enqueueWork(()->{
            if(ctx.player() instanceof ServerPlayer player)SpellEngine.action(player,msg.action);
        }));
        r.playToServer(Select.TYPE,Select.CODEC,(msg,ctx)->ctx.enqueueWork(()->{
            if(ctx.player() instanceof ServerPlayer p)select(p,msg);
        }));
        r.playToClient(Visual.TYPE,Visual.CODEC,(msg,ctx)->ctx.enqueueWork(()->AaaEffects.visual(msg)));
        r.playToClient(Burst.TYPE,Burst.CODEC,(msg,ctx)->ctx.enqueueWork(()->WandClient.burst(msg)));
    }
}
