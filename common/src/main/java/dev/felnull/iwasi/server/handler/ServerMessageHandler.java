package dev.felnull.iwasi.server.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.iwasi.state.IWPlayerData;

public class ServerMessageHandler {
    public static void onHoldInputMessage(IWPackets.HoldInputMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> packetContext.getPlayer().getEntityData().set(IWPlayerData.DATA_HOLD, message.hold));
    }
}
