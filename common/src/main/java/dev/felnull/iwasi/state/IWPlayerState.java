package dev.felnull.iwasi.state;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public class IWPlayerState {
    public static boolean isHolding(Player player) {
        return getActionData(player).hold();
    }

    public static boolean isPullTrigger(Player player) {
        return getActionData(player).pullTrigger();
    }

    public static ActionData getActionData(Player player) {
        return player.getEntityData().get(IWPlayerData.ACTION_DATA);
    }

    public static int getHoldingProgress(Player player, InteractionHand hand) {
        var es = hand == InteractionHand.MAIN_HAND ? IWPlayerData.MAIN_HAND_HOLDING : IWPlayerData.OFF_HAND_HOLDING;
        return player.getEntityData().get(es);
    }

    public static void setHoldingProgress(ServerPlayer player, InteractionHand hand, int value) {
        var es = hand == InteractionHand.MAIN_HAND ? IWPlayerData.MAIN_HAND_HOLDING : IWPlayerData.OFF_HAND_HOLDING;
        player.getEntityData().set(es, value);
    }
}
