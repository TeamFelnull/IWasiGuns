package dev.felnull.iwasi.server.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.IIWCashServerPlayer;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ServerHandler {
    public static void init() {
        TickEvent.PLAYER_PRE.register(ServerHandler::onPlayerTick);
    }

    private static void onPlayerTick(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer) || player.level.isClientSide()) return;

        var lastCash = (IIWCashServerPlayer) serverPlayer;

        var continuousAction = IWPlayerData.getContinuousAction(player);
        boolean canChangeHold = canChangeHold(player);
        if (lastCash.getLastContinuousHold() != continuousAction.hold() && canChangeHold) {
            IWPlayerData.setHold(serverPlayer, continuousAction.hold() ? HoldType.HOLD : HoldType.NONE);
            lastCash.setLastContinuousHold(continuousAction.hold());
        }

      /*  for (InteractionHand hand : InteractionHand.values()) {
            var item = serverPlayer.getItemInHand(hand);
            if (item.getItem() instanceof GunItem) {
                if (GunItem.getTmpUUID(item) == null)
                    GunItem.resetTmpUUID(item);
            }
        }
        var lastCash = (IIWCashServerPlayer) serverPlayer;
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

        boolean reset = false;
        for (InteractionHand hand : InteractionHand.values()) {
            boolean handFlg = hand == InteractionHand.MAIN_HAND;
            var lastItem = lastCash.getLastHandItem(hand);
            var item = serverPlayer.getItemInHand(hand);
            if (item != lastItem) {
                UUID oid = null;
                if (item.getItem() instanceof GunItem)
                    oid = GunItem.getTmpUUID(item);
                UUID nid = null;
                if (lastItem.getItem() instanceof GunItem)
                    nid = GunItem.getTmpUUID(lastItem);
                if (!Objects.equals(oid, nid))
                    reset = true;
                lastCash.setLastHandItem(hand, item);
            }
        }

        if (reset) {
            for (InteractionHand hand : InteractionHand.values()) {
                var item = player.getItemInHand(hand);
                if (item.getItem() instanceof GunItem)
                    GunItem.resetTmpUUID(item);
                IWPlayerData.setGunTrans(serverPlayer, hand, null);
                var es = hand == InteractionHand.MAIN_HAND ? IWPlayerData.MAIN_HAND_HOLDING : IWPlayerData.OFF_HAND_HOLDING;
                player.getEntityData().set(es, false);
                lastCash.setLastHold(false);
            }
        }

        var ca = IWPlayerData.getContinuousAction(serverPlayer);
        boolean lastHold = lastCash.getLastHold();
        if (lastHold != ca.hold()) {
            setHoldTrans(serverPlayer, InteractionHand.MAIN_HAND, serverPlayer.getMainHandItem(), ca.hold());
            setHoldTrans(serverPlayer, InteractionHand.OFF_HAND, serverPlayer.getOffhandItem(), ca.hold());
            lastCash.setLastHold(ca.hold());
        }

        for (InteractionHand hand : InteractionHand.values()) {
            var item = serverPlayer.getItemInHand(hand);
            var gt = IWPlayerData.getGunTransData(player, hand);
            if (!(item.getItem() instanceof GunItem)) {
                if (gt.getGunTrans() != null)
                    setGunTrans(serverPlayer, hand, new GunTransData(null, 0, 0, gt.updateId() + 1));
                continue;
            }
            var ng = gt.next(serverPlayer, hand, item);
            if (ng != null)
                setGunTrans(serverPlayer, hand, ng);
        }*/
    }

    private static void setGunTrans(ServerPlayer player, InteractionHand hand, GunTransData gunTransData) {
        var es = hand == InteractionHand.MAIN_HAND ? IWPlayerData.MAIN_HAND_GUN_TRANS : IWPlayerData.OFF_HAND_GUN_TRANS;
        player.getEntityData().set(es, gunTransData);
    }

    private static void setHoldTrans(ServerPlayer player, InteractionHand hand, ItemStack stack, boolean hold) {
        if (!(stack.getItem() instanceof GunItem gunItem)) return;
        var holdGT = gunItem.getGun().getHoldTrans();
        var unHoldGT = gunItem.getGun().getUnHoldTrans();
        var ogt = IWPlayerData.getGunTransData(player, hand);
        var hgt = hold ? holdGT : unHoldGT;
        var gt = ogt.getGunTrans();
        if (gt == null) {
            IWPlayerData.setGunTrans(player, hand, hgt);
            return;
        }
        if (gt != unHoldGT && gt != holdGT) return;
        int ms = hgt.getProgress(gunItem.getGun(), 0);
        setGunTrans(player, hand, new GunTransData(hgt, ms - 1 - ogt.progress(), 0, ogt.updateId() + 1));
    }

    private static boolean canChangeHold(Player player) {
        var data = (IIWDataPlayer) player;
        return IWPlayerData.getMaxHoldProgress(player) <= data.getHoldProgress();
    }
}
