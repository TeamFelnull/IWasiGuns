package dev.felnull.iwasi.util;

import dev.felnull.iwasi.gun.Gun;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class IWItemUtil {
    @NotNull
    public static Gun getGun(ItemStack stack) {
        if (stack.getItem() instanceof GunItem gunItem)
            return gunItem.getGun();
        throw new IllegalStateException("Not gun item");
    }

    @Nullable
    public static UUID getGunTmpID(ItemStack stack) {
        if (stack.getItem() instanceof GunItem)
            return GunItem.getTmpUUID(stack);
        return null;
    }
}
