package dev.felnull.iwasi.state;

import net.minecraft.world.entity.player.Player;

public class IWPlayerState {
    public static boolean isHolding(Player player) {
        return player.getEntityData().get(IWPlayerData.DATA_HOLD);
    }
}
