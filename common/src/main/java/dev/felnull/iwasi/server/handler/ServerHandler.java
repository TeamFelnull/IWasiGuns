package dev.felnull.iwasi.server.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.data.IIWCashPlayer;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.UUID;

public class ServerHandler {
    public static void init() {
        TickEvent.PLAYER_PRE.register(ServerHandler::onPlayerTick);
    }

    private static void onPlayerTick(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer) || player.level.isClientSide()) return;
        for (InteractionHand hand : InteractionHand.values()) {
            var item = serverPlayer.getItemInHand(hand);
            if (item.getItem() instanceof GunItem) {
                if (GunItem.getTmpUUID(item) == null)
                    GunItem.resetTmpUUID(item);
            }
        }
        var lastCash = (IIWCashPlayer) serverPlayer;
        int selected = serverPlayer.getInventory().selected;

        if (selected != lastCash.getLastSelected()) {
            var nitem = serverPlayer.getInventory().items.get(serverPlayer.getInventory().selected);
            if (nitem.getItem() instanceof GunItem)
                GunItem.resetTmpUUID(nitem);
            var item = serverPlayer.getInventory().getSelected();
            if (item.getItem() instanceof GunItem)
                GunItem.resetTmpUUID(item);
            lastCash.setLastSelected(selected);
        }

        boolean resetMainHand = false;
        boolean resetOffHand = false;
        for (InteractionHand hand : InteractionHand.values()) {
            boolean handFlg = hand == InteractionHand.MAIN_HAND;
            var lastItem = handFlg ? lastCash.getLastMainHandItem() : lastCash.getLastOffHandItem();
            var item = serverPlayer.getItemInHand(hand);
            if (item != lastItem) {
                UUID oid = null;
                if (item.getItem() instanceof GunItem)
                    oid = GunItem.getTmpUUID(item);
                UUID nid = null;
                if (lastItem.getItem() instanceof GunItem)
                    nid = GunItem.getTmpUUID(lastItem);
                if (!Objects.equals(oid, nid)) {
                    if (handFlg) {
                        resetMainHand = true;
                    } else {
                        resetOffHand = true;
                    }
                }

                if (handFlg) {
                    lastCash.setLastMainHandItem(item);
                } else {
                    lastCash.setLastOffHandItem(item);
                }
            }
        }

        if (resetMainHand) {

        }

        if (resetOffHand) {

        }

        for (InteractionHand hand : InteractionHand.values()) {
            var item = serverPlayer.getItemInHand(hand);
            if (!(item.getItem() instanceof GunItem gunItem)) {
                IWPlayerData.setGunTrans(serverPlayer, hand, null);
                continue;
            }
            var gsd = IWPlayerData.getGunTransData(serverPlayer, hand);
            var nd = gsd.tickNext(serverPlayer, hand, item);
            if (nd != null)
                IWPlayerData.setGunTransData(serverPlayer, hand, nd);
        }
    }
}
