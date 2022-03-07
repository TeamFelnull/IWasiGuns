package dev.felnull.iwasi.client;

import dev.felnull.iwasi.client.handler.ClientHandler;
import dev.felnull.iwasi.client.physics.ClientWorldPhysicsManager;
import dev.felnull.iwasi.client.renderer.entity.IWEntityRenderers;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.iwasi.physics.CommonWorldPhysicsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class IWasiClient {

    public static void preInit() {
        IWEntityRenderers.init();
    }

    public static void init() {
        IWPackets.clientInit();
        ClientHandler.init();

        CommonWorldPhysicsManager.getInstance().setClientWorldPhysicsManager(ClientWorldPhysicsManager.getInstance());
    }
}
