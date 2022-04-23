package dev.felnull.iwasi.client.data;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.data.ContinuousActionData;
import dev.felnull.iwasi.handler.CommonHandler;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.iwasi.util.IWPlayerUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

public class ClientAction {
    private static final Minecraft mc = Minecraft.getInstance();
    private static boolean holding;
    private static boolean pullTrigger;

    public static void tick() {
        if (mc.level == null || mc.player == null || !haveGun()) {
            reset();
            return;
        }

        while (IWKeyMappings.RELOAD.consumeClick()) {
            sendActionPacket(IWPackets.ActionInputMessage.ActionType.RELOAD);
        }

        while (getTriggerKey().consumeClick()) {
            sendActionPacket(IWPackets.ActionInputMessage.ActionType.TRIGGER);

            for (InteractionHand hand : InteractionHand.values()) {
                IWPlayerUtil.shotGun(mc.player, hand);
            }
        }

        if (IWasi.getConfig().toggleHold) {
            while (getHoldKey().consumeClick()) {
                holding = !holding;
            }
        } else {
            holding = getHoldKey().isDown();
        }
        pullTrigger = getTriggerKey().isDown();

        NetworkManager.sendToServer(IWPackets.CONTINUOUS_ACTION_INPUT, new IWPackets.ContinuousActionInputMessage(ClientAction.getContinuousData()).toFBB());
    }

    private static boolean haveGun() {
        return IWItemUtil.getGunNullable(mc.player.getItemInHand(InteractionHand.MAIN_HAND)) != null || IWItemUtil.getGunNullable(mc.player.getItemInHand(InteractionHand.OFF_HAND)) != null;
    }

    public static void sendActionPacket(IWPackets.ActionInputMessage.ActionType actionType) {
        NetworkManager.sendToServer(IWPackets.ACTION_INPUT, new IWPackets.ActionInputMessage(actionType).toFBB());
    }

    public static void reset() {
        holding = false;
        pullTrigger = false;
    }

    public static boolean isHolding() {
        return holding;
    }

    public static boolean isPullTrigger() {
        return pullTrigger;
    }

    public static ContinuousActionData getContinuousData() {
        return new ContinuousActionData(isHolding(), isPullTrigger());
    }

    public static KeyMapping getHoldKey() {
        return mc.options.keyUse;
    }

    public static KeyMapping getTriggerKey() {
        return mc.options.keyAttack;
    }
}
