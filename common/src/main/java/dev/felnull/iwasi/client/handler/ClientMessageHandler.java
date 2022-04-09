package dev.felnull.iwasi.client.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.client.data.ClientGunTrans;
import dev.felnull.iwasi.networking.IWPackets;

public class ClientMessageHandler {
    public static void onGunTransResetMessage(IWPackets.GunTransResetMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> ClientGunTrans.reset(message.hand, message.gunTrans, false));
    }
}
