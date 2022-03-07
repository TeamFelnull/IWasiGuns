package dev.felnull.iwasi;

import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.felnull.iwasi.client.IWasiClient;
import dev.felnull.iwasi.entity.IWEntityType;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.iwasi.physics.CommonWorldPhysicsManager;
import dev.felnull.iwasi.server.handler.ServerHandler;
import dev.felnull.iwasi.server.physics.ServerWorldPhysicsManager;

public class IWasi {
    public static final String MODID = "iwasi";

    public static void init() {
        IWPackets.init();
        IWItems.init();
        IWEntityType.init();
        ServerHandler.init();

        CommonWorldPhysicsManager.getInstance().setServerWorldPhysicsManager(ServerWorldPhysicsManager.getInstance());

        EnvExecutor.runInEnv(Env.CLIENT, () -> IWasiClient::preInit);
    }
}
