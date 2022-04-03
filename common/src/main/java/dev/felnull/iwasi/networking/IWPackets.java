package dev.felnull.iwasi.networking;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.server.handler.ServerMessageHandler;
import dev.felnull.iwasi.state.ActionData;
import dev.felnull.otyacraftengine.networking.PacketMessage;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class IWPackets {
    public static final ResourceLocation ACTION_INPUT = new ResourceLocation(IWasi.MODID, "action_input");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), ACTION_INPUT, (friendlyByteBuf, packetContext) -> ServerMessageHandler.onActionInputMessage(new ActionInputMessage(friendlyByteBuf), packetContext));
    }

    public static void clientInit() {

    }

    public static class ActionInputMessage implements PacketMessage {
        public final ActionData data;

        public ActionInputMessage(FriendlyByteBuf buf) {
            this.data = ActionData.read(buf);
        }

        public ActionInputMessage(ActionData data) {
            this.data = data;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            data.write(buf);
            return buf;
        }
    }
}
