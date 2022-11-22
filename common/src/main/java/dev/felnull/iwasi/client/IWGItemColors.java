package dev.felnull.iwasi.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.ration.Ration;

public class IWGItemColors {
    public static void init() {
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> {
            var cat = Ration.getCategory(itemStack);
            if (i == 0)
                return cat.getRight().getColor();
            if (i == 1)
                return cat.getLeft().getColor();
            return -1;
        }, IWGItems.RATION);
    }
}
