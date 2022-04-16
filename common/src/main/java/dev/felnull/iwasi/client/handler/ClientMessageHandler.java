package dev.felnull.iwasi.client.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.networking.IWPackets;
import net.minecraft.client.Minecraft;

public class ClientMessageHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void onTmpHandItemsSyncMessage(IWPackets.TmpHandItemsSyncMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            var pl = mc.level.getPlayerByUUID(message.playerId);
            if (pl != null) {
                var data = (IIWDataPlayer) pl;
                data.setTmpHandItems(message.hand, message.items);
            }
        });
    }
}
