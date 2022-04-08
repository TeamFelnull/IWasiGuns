package dev.felnull.iwasi.server.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.networking.IWPackets;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

public class ServerMessageHandler {
    public static void onContinuousActionInputMessage(IWPackets.ContinuousActionInputMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> packetContext.getPlayer().getEntityData().set(IWPlayerData.CONTINUOUS_ACTION, message.data));
    }

    public static void onActionInputMessage(IWPackets.ActionInputMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            switch (message.action) {
                case RELOAD -> {
                    boolean flg = false;
                    for (InteractionHand hand : InteractionHand.values()) {
                        var ogt = IWPlayerData.getGunTrans(packetContext.getPlayer(), hand);
                        if (ogt != null)
                            flg = true;
                    }
                    if (flg) return;
                    for (InteractionHand hand : InteractionHand.values()) {
                        var item = packetContext.getPlayer().getItemInHand(hand);
                        if (item.getItem() instanceof GunItem gunItem && GunItem.canReload(item)) {
                            IWPlayerData.setGunTrans((ServerPlayer) packetContext.getPlayer(), hand, gunItem.getGun().getReloadTrans());
                            break;
                        }
                    }
                }
            }
        });
    }
}
