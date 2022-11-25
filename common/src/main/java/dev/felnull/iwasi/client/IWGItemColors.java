package dev.felnull.iwasi.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.ration.Ration;
import dev.felnull.iwasi.item.ration.RationFoodCategory;

public class IWGItemColors {
    public static void init() {
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> {
            var cat = Ration.getCategory(itemStack);
            if (i == 0)
                return cat.getRight().getColor();
            if (i == 1 && cat.getLeft() != RationFoodCategory.GOLDEN)
                return cat.getLeft().getColor();
            return -1;
        }, IWGItems.RATION);
    }
}
