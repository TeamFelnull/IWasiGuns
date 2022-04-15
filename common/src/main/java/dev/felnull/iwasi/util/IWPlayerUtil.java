package dev.felnull.iwasi.util;

import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class IWPlayerUtil {
    public static float getHoldProgress(@NotNull Player player, InteractionHand hand, float delta) {
        var g = IWItemUtil.getGunNullable(player.getItemInHand(hand));
        if (g != null) {
            var data = (IIWDataPlayer) player;
            return Math.min(data.getHoldProgress(delta) / (float) g.getRequiredHoldTime(), 1f);
        }
        return 0;
    }

    public static int getMaxHoldProgress(@NotNull Player player) {
        var mg = IWItemUtil.getGunNullable(player.getMainHandItem());
        var og = IWItemUtil.getGunNullable(player.getOffhandItem());
        return Math.max(mg != null ? mg.getRequiredHoldTime() : 0, og != null ? og.getRequiredHoldTime() : 0);
    }

    public static int getHoldSpeed(@NotNull Player player, @NotNull HoldType old, @NotNull HoldType last, boolean hurry) {
        var mg = IWItemUtil.getGunNullable(player.getMainHandItem());
        var og = IWItemUtil.getGunNullable(player.getOffhandItem());
        if (mg == null && og == null) return 1;
        if (mg == null) return og.getHoldSpeed(old, last, hurry);
        if (og == null) return mg.getHoldSpeed(old, last, hurry);
        return Math.min(mg.getHoldSpeed(old, last, hurry), og.getHoldSpeed(old, last, hurry));
    }

    public static boolean isHolding(@NotNull Player player, InteractionHand hand) {
        var g = IWItemUtil.getGunNullable(player.getItemInHand(hand));
        if (g != null) {
            var data = (IIWDataPlayer) player;
            return data.getHoldProgress() >= g.getRequiredHoldTime();
        }
        return false;
    }
}
