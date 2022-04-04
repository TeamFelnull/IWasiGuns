package dev.felnull.iwasi.client;

import dev.felnull.iwasi.client.handler.ClientGunHandler;
import dev.felnull.iwasi.client.handler.ClientHandler;
import dev.felnull.iwasi.client.handler.RenderHandler;
import dev.felnull.iwasi.client.renderer.IWTestRenderers;
import dev.felnull.iwasi.client.renderer.entity.IWEntityRenderers;
import dev.felnull.iwasi.client.renderer.item.IWItemRenderers;
import dev.felnull.iwasi.networking.IWPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class IWasiClient {

    public static void preInit() {
        IWEntityRenderers.init();
    }

    public static void init() {
        IWKeyMapping.init();
        IWPackets.clientInit();
        ClientHandler.init();
        RenderHandler.init();
        ClientGunHandler.init();
        IWItemRenderers.init();
        IWTestRenderers.init();
    }
}
