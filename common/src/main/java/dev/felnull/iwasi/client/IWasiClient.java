package dev.felnull.iwasi.client;

import dev.felnull.iwasi.client.data.IWKeyMappings;
import dev.felnull.iwasi.client.gun.IWClientGuns;
import dev.felnull.iwasi.client.handler.ClientHandler;
import dev.felnull.iwasi.client.handler.RenderHandler;
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
        IWClientGuns.init();
        IWItemRenderers.init();
    }
}
