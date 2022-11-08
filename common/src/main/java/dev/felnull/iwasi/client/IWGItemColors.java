package dev.felnull.iwasi.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.felnull.iwasi.item.IWGItems;

public class IWGItemColors {
    public static void init() {
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> {
            if (i == 0)
                return 0xFF00FF;
            if (i == 1)
                return 0x114514;
            return -1;
        }, IWGItems.RATION);
    }
}
