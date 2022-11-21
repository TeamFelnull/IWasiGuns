package dev.felnull.iwasi.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.RationItem;

public class IWGItemColors {
    public static void init() {
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> {
            var cat = RationItem.getCategory(itemStack);
            if (i == 0)
                return cat.getRight().getColor();
            if (i == 1)
                return 0x114514;
            return -1;
        }, IWGItems.RATION);
    }
}
