package dev.felnull.iwasi.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.iwasi.client.IWasiGunsClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IWasiGuns.MODID)
public class IWasiGunsForge {
    public IWasiGunsForge() {
        EventBuses.registerModEventBus(IWasiGuns.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        IWasiGuns.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(FMLClientSetupEvent e) {
        IWasiGunsClient.init();
    }
}
