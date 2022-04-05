package dev.felnull.iwasi.client;

import dev.felnull.iwasi.client.data.IWKeyMappings;
import dev.felnull.iwasi.client.handler.ClientHandler;
import dev.felnull.iwasi.client.handler.RenderHandler;
import dev.felnull.iwasi.client.motion.gun.IWGunMotions;
import dev.felnull.iwasi.client.renderer.gun.IWGunRenderers;
import dev.felnull.iwasi.client.renderer.item.IWItemRenderers;
import dev.felnull.iwasi.networking.IWPackets;

public class IWasiClient {
    public static void preInit() {

    }

    public static void init() {
        IWPackets.clientInit();
        IWKeyMappings.init();
        ClientHandler.init();
        RenderHandler.init();
        IWGunRenderers.init();
        IWItemRenderers.init();
        IWGunMotions.init();
    }
}
