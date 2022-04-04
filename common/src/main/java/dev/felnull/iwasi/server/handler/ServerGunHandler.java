package dev.felnull.iwasi.server.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.item.gun.GunItem;
import dev.felnull.iwasi.server.state.IIWCashPlayer;
import dev.felnull.iwasi.state.IWPlayerState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class ServerGunHandler {
    public static void init() {
        TickEvent.PLAYER_PRE.register(ServerGunHandler::onPlayerTick);
    }

    private static void onPlayerTick(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
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

        var lastMainItem = lastCash.getLastMainHandItem();
        var lastOffItem = lastCash.getLastOffHandItem();

        var mainItem = serverPlayer.getMainHandItem();
        var offItem = serverPlayer.getOffhandItem();

        boolean resetMainHandHold = false;
        boolean resetOffHandHold = false;

        if (mainItem != lastMainItem) {
            if (!ItemStack.matches(mainItem, lastMainItem)) {
                UUID oid = null;
                if (mainItem.getItem() instanceof GunItem)
                    oid = GunItem.getTmpUUID(mainItem);
                UUID nid = null;
                if (lastMainItem.getItem() instanceof GunItem)
                    nid = GunItem.getTmpUUID(lastMainItem);
                if (!Objects.equals(oid, nid)) {
                    if (mainItem.getItem() instanceof GunItem gunItem)
                        gunItem.reset(mainItem, serverPlayer, InteractionHand.MAIN_HAND);
                    if (lastMainItem.getItem() instanceof GunItem gunItem)
                        gunItem.reset(lastMainItem, serverPlayer, InteractionHand.MAIN_HAND);
                    resetMainHandHold = true;
                }
            }
            lastCash.setLastMainHandItem(mainItem);
        }

        if (offItem != lastOffItem) {
            if (!ItemStack.matches(offItem, lastOffItem)) {
                UUID oid = null;
                if (offItem.getItem() instanceof GunItem)
                    oid = GunItem.getTmpUUID(offItem);
                UUID nid = null;
                if (lastOffItem.getItem() instanceof GunItem)
                    nid = GunItem.getTmpUUID(lastOffItem);
                if (!Objects.equals(oid, nid)) {
                    if (offItem.getItem() instanceof GunItem gunItem)
                        gunItem.reset(offItem, serverPlayer, InteractionHand.OFF_HAND);
                    if (lastOffItem.getItem() instanceof GunItem gunItem)
                        gunItem.reset(lastOffItem, serverPlayer, InteractionHand.OFF_HAND);
                    resetOffHandHold = true;
                }
            }
            lastCash.setLastOffHandItem(offItem);
        }

        if (resetMainHandHold)
            IWPlayerState.setHoldingProgress(serverPlayer, InteractionHand.MAIN_HAND, 0);


        if (resetOffHandHold)
            IWPlayerState.setHoldingProgress(serverPlayer, InteractionHand.OFF_HAND, 0);

        for (InteractionHand hand : InteractionHand.values()) {
            var item = serverPlayer.getItemInHand(hand);
            if (item.getItem() instanceof GunItem gunItem) {
                boolean resetHold = hand == InteractionHand.MAIN_HAND ? resetMainHandHold : resetOffHandHold;
                if (!resetHold) {
                    int crntHolfing = IWPlayerState.getHoldingProgress(serverPlayer, hand);
                    int a = IWPlayerState.isHolding(serverPlayer) ? 1 : -1;
                    IWPlayerState.setHoldingProgress(serverPlayer, hand, Mth.clamp(crntHolfing + a, 0, gunItem.getHoldingTime()));
                }
                gunItem.handTick(item, serverPlayer, hand);
            } else {
                IWPlayerState.setHoldingProgress(serverPlayer, hand, 0);
            }
        }
    }
}
