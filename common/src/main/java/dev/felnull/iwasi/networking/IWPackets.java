package dev.felnull.iwasi.networking;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.server.handler.ServerMessageHandler;
import dev.felnull.otyacraftengine.networking.PacketMessage;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class IWPackets {
    public static final ResourceLocation HOLD_INPUT = new ResourceLocation(IWasi.MODID, "hold_input");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), HOLD_INPUT, (friendlyByteBuf, packetContext) -> ServerMessageHandler.onHoldInputMessage(new HoldInputMessage(friendlyByteBuf), packetContext));
    }

    public static void clientInit() {

    }

    public static class HoldInputMessage implements PacketMessage {
        public final boolean hold;

        public HoldInputMessage(FriendlyByteBuf buf) {
            this.hold = buf.readBoolean();
        }

        public HoldInputMessage(boolean hold) {
            this.hold = hold;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeBoolean(hold);
            return buf;
        }
    }
}
