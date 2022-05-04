package dev.felnull.iwasi.data;

import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.util.IWItemUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public enum HoldType {
    NONE(false),
    HOLD(false),
    LOWER(true),
    UPPER(true);
    private final boolean disarmament;

    HoldType(boolean disarmament) {
        this.disarmament = disarmament;
    }

    public static HoldType getIdeal(Player player, boolean hold) {
        var data = (IIWDataPlayer) player;
        if (hold) return HOLD;

        if (player.isSprinting() && data.getHoldGrace() <= 0) {
            var gunMain = IWItemUtil.getGunNullable(player.getItemInHand(InteractionHand.MAIN_HAND));
            boolean upperMain = gunMain != null && gunMain.getType().isUpperGun();

            var gunOff = IWItemUtil.getGunNullable(player.getItemInHand(InteractionHand.OFF_HAND));
            boolean upperOff = gunOff != null && gunOff.getType().isUpperGun();

            return (upperMain && upperOff) ? UPPER : LOWER;
        }
        return NONE;
    }

    public boolean isDisarmament() {
        return disarmament;
    }
}
