package dev.felnull.iwasi.util;

import dev.felnull.iwasi.gun.Gun;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.world.item.ItemStack;

public class IWItemUtil {
    public static Gun getGun(ItemStack stack) {
        if (stack.getItem() instanceof GunItem gunItem)
            return gunItem.getGun();
        throw new IllegalStateException("Not gun item");
    }
}
