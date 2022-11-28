package dev.felnull.iwasi.client;

import dev.felnull.iwasi.client.handler.ClientHandler;
import dev.felnull.iwasi.client.renderer.item.IWGItemColors;
import dev.felnull.iwasi.client.renderer.item.IWGItemProperties;
import dev.felnull.iwasi.client.renderer.item.IWGItemRenderers;

public class IWasiGunsClient {
    public static void preInit() {
        IWGItemColors.init();
    }

    public static void init() {
        ClientHandler.init();

        IWGItemProperties.init();
        IWGItemRenderers.init();
    }
}
