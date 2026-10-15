package nl.townsandkingdoms.kingdomwands;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class WandNetwork {
    private WandNetwork() {}
    public record Action(int action) implements CustomPacketPayload {
        public static final Type<Action> TYPE=new Type<>(ResourceLocation.fromNamespaceAndPath(KingdomWands.ID,"action"));
        public static final StreamCodec<RegistryFriendlyByteBuf,Action> CODEC=StreamCodec.of((buf,msg)->buf.writeVarInt(msg.action),buf->new Action(buf.readVarInt()));
        @Override public Type<Action> type(){return TYPE;}
    }
    /** Pure visual burst: the server never spawns damaging rocket entities. */
    public record Burst(double x,double y,double z,int color) implements CustomPacketPayload {
        public static final Type<Burst> TYPE=new Type<>(ResourceLocation.fromNamespaceAndPath(KingdomWands.ID,"burst"));
        public static final StreamCodec<RegistryFriendlyByteBuf,Burst> CODEC=StreamCodec.of((b,m)->{b.writeDouble(m.x);b.writeDouble(m.y);b.writeDouble(m.z);b.writeInt(m.color);},b->new Burst(b.readDouble(),b.readDouble(),b.readDouble(),b.readInt()));
        @Override public Type<Burst> type(){return TYPE;}
    }
    public static void register(RegisterPayloadHandlersEvent e) {
        var r=e.registrar("1");
        r.playToServer(Action.TYPE,Action.CODEC,(msg,ctx)->ctx.enqueueWork(()->{
            if(ctx.player() instanceof ServerPlayer player)SpellEngine.action(player,msg.action);
        }));
        r.playToClient(Burst.TYPE,Burst.CODEC,(msg,ctx)->ctx.enqueueWork(()->WandClient.burst(msg)));
    }
}
