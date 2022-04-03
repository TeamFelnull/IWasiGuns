package dev.felnull.iwasi;

import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.felnull.iwasi.client.IWasiClient;
import dev.felnull.iwasi.entity.ActionDataEntityDataSerializer;
import dev.felnull.iwasi.entity.IWEntityType;
import dev.felnull.iwasi.entity.RigidStateEntityDataSerializer;
import dev.felnull.iwasi.handler.CommonHandler;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.iwasi.server.handler.ServerHandler;

public class IWasi {
    public static final String MODID = "iwasi";

    public static void init() {
        IWPackets.init();
        IWItems.init();
        IWEntityType.init();
        RigidStateEntityDataSerializer.init();
        ActionDataEntityDataSerializer.init();
        CommonHandler.init();
        ServerHandler.init();
        EnvExecutor.runInEnv(Env.CLIENT, () -> IWasiClient::preInit);
    }
}
