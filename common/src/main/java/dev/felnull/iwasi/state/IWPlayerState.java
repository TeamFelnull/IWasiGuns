package dev.felnull.iwasi.state;

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
}
