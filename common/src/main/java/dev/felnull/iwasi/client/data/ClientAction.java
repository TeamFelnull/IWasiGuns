package dev.felnull.iwasi.client.data;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.data.ContinuousActionData;
import dev.felnull.iwasi.networking.IWPackets;
import net.minecraft.client.Minecraft;

public class ClientAction {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final boolean toggle = false;
    private static boolean holding;
    private static boolean lastHold;
    private static boolean pullTrigger;

    public static void tick() {
        if (mc.level == null || mc.player == null) {
            reset();
            return;
        }

        if (toggle) {
            boolean n = mc.options.keyUse.isDown();
            if (lastHold != n) {
                if (n)
                    holding = !holding;
                lastHold = n;
            }
        } else {
            holding = mc.options.keyUse.isDown();
        }
        pullTrigger = mc.options.keyAttack.isDown();

        NetworkManager.sendToServer(IWPackets.CONTINUOUS_ACTION_INPUT, new IWPackets.ContinuousActionInputMessage(ClientAction.getContinuousData()).toFBB());

        while (IWKeyMappings.RELOAD.consumeClick()) {
            sendActionPacket(IWPackets.ActionInputMessage.ActionType.RELOAD);
        }
    }

    public static void sendActionPacket(IWPackets.ActionInputMessage.ActionType actionType) {
        NetworkManager.sendToServer(IWPackets.ACTION_INPUT, new IWPackets.ActionInputMessage(actionType).toFBB());
    }

    public static void reset() {
        holding = false;
        lastHold = false;
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
}
