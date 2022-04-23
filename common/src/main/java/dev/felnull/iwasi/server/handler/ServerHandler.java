package dev.felnull.iwasi.server.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.data.GunItemTransData;
import dev.felnull.iwasi.data.GunPlayerTransData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.IIWCashServerPlayer;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.iwasi.util.IWPlayerUtil;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.List;

public class ServerHandler {
    public static void init() {
        TickEvent.PLAYER_PRE.register(ServerHandler::onPlayerTick);
    }

    private static void onPlayerTick(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer) || player.level.isClientSide()) return;

        var lastCash = (IIWCashServerPlayer) serverPlayer;

        var continuousAction = IWPlayerData.getContinuousAction(player);
        boolean canChangeHold = canChangeHold(player);
        var data = (IIWDataPlayer) player;

        for (InteractionHand hand : InteractionHand.values()) {
            if (data.isTmpHandItemsUpdate(hand)) {
                var lch = (LevelChunk) player.getLevel().getChunk(player.blockPosition());
                OEPlayerUtil.doPlayers(lch, n -> NetworkManager.sendToPlayer(n, IWPackets.TMP_HAND_ITEMS_SYNC, new IWPackets.TmpHandItemsSyncMessage(player.getGameProfile().getId(), hand, data.getTmpHandItems(hand)).toFBB()));
                data.setTmpHandItemsUpdate(hand, false);
            }
        }


        if (lastCash.getLastContinuousHold() != continuousAction.hold() && canChangeHold) {
            data.setHoldType(HoldType.getIdeal(continuousAction.hold(), player.isSprinting(), data.getHoldGrace()));
            lastCash.setLastContinuousHold(continuousAction.hold());
        }

        for (InteractionHand hand : InteractionHand.values()) {
            var item = serverPlayer.getItemInHand(hand);
            if (item.getItem() instanceof GunItem) {
                if (GunItem.getTmpUUID(item) == null)
                    GunItem.resetTmpUUID(item);
            }
        }

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


        for (InteractionHand hand : InteractionHand.values()) {
            var item = player.getItemInHand(hand);
            var gun = IWItemUtil.getGunNullable(item);
            if (gun == null) continue;
            var gitl = GunItem.getGunItemTransList(item);
            List<GunItemTransData> nl = new ArrayList<>();
            for (GunItemTransData entry : gitl) {
                var gt = entry.getGunTrans();
                if (gt == null) continue;
                boolean next;
                next = gt.tick(serverPlayer, hand, gun, item, entry.progress(), entry.step());
                int mp = gt.getProgress(item, entry.step());
                if (mp - 1 <= entry.progress()) {
                    if (next)
                        next = gt.stepEnd(serverPlayer, hand, gun, item, entry.step());
                    if (next && gt.getStep(item) - 1 > entry.step())
                        nl.add(new GunItemTransData(entry.getGunTrans(), 0, entry.step() + 1, entry.updateId()));
                } else {
                    nl.add(new GunItemTransData(entry.getGunTrans(), entry.progress() + 1, entry.step(), entry.updateId()));
                }
            }
            GunItem.setGunItemTransList(item, nl);
        }

        for (InteractionHand hand : InteractionHand.values()) {
            IWPlayerUtil.shotGun(serverPlayer, hand, data.isPullTrigger());
        }

  /*
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

    private static boolean canChangeHold(Player player) {
        var data = (IIWDataPlayer) player;
        return IWPlayerUtil.getMaxHoldProgress(player) <= data.getHoldProgress();
    }
}
