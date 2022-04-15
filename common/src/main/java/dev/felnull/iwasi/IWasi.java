package dev.felnull.iwasi;

import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.felnull.iwasi.client.IWasiClient;
import dev.felnull.iwasi.entity.IWEntityDataSerializers;
import dev.felnull.iwasi.gun.trans.item.IWGunItemTrans;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.iwasi.handler.CommonHandler;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.iwasi.server.handler.ServerHandler;
import dev.felnull.iwasi.sound.IWSounds;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class IWasi {
    public static final String MODID = "iwasi";
    private static final IWConfig CONFIG = AutoConfig.register(IWConfig.class, Toml4jConfigSerializer::new).getConfig();

    public static void init() {
        IWPackets.init();
        IWItems.init();
        IWEntityDataSerializers.init();
        IWGunPlayerTrans.init();
        IWGunItemTrans.init();
        IWSounds.init();
        CommonHandler.init();
        ServerHandler.init();
        EnvExecutor.runInEnv(Env.CLIENT, () -> IWasiClient::preInit);
    }

    public static IWConfig getConfig() {
        return CONFIG;
    }
}
