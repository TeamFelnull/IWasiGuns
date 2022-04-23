package dev.felnull.iwasi.client.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.iwasi.util.IWItemUtil;
import net.minecraft.client.Minecraft;

public class ClientMessageHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void onActionClientSyncMessage(IWPackets.ActionClientSyncMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            var pl = mc.level.getPlayerByUUID(message.playerId);
            if (pl != null) {
                var itm = pl.getItemInHand(message.hand);
                if (message.gunTmpId.equals(IWItemUtil.getGunTmpID(itm))) {
                    var gun = IWItemUtil.getGunNullable(itm);
                    if (gun != null)
                        gun.shot(mc.level, mc.player, message.hand, itm);
                }
            }
        });
    }

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
