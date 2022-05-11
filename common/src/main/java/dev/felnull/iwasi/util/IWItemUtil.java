package dev.felnull.iwasi.util;

import dev.felnull.iwasi.gun.Gun;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class IWItemUtil {
    @NotNull
    public static Gun getGun(@NotNull ItemStack stack) {
        if (stack.getItem() instanceof GunItem gunItem)
            return gunItem.getGun();
        throw new IllegalStateException("Not gun item");
    }

    @Nullable
    public static Gun getGunNullable(@NotNull ItemStack stack) {
        if (stack.getItem() instanceof GunItem gunItem)
            return gunItem.getGun();
        return null;
    }

    @Nullable
    public static UUID getGunTmpID(@NotNull ItemStack stack) {
        if (stack.getItem() instanceof GunItem)
            return GunItem.getTmpUUID(stack);
        return null;
    }

    public static boolean isSlideDown(@NotNull ItemStack stack) {
        return GunItem.getChamberRemainingBullets(stack) <= 0;
    }

    public static boolean isKnife(ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }
}
