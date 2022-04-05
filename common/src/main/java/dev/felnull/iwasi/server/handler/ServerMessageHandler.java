package dev.felnull.iwasi.server.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.networking.IWPackets;

public class ServerMessageHandler {
    public static void onContinuousActionInputMessage(IWPackets.ContinuousActionInputMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> packetContext.getPlayer().getEntityData().set(IWPlayerData.CONTINUOUS_ACTION, message.data));
    }

    public static void onActionInputMessage(IWPackets.ActionInputMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            switch (message.action) {
                case RELOAD -> {
                    System.out.println("Reload");
                }
            }
        });
    }
}
