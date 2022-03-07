package dev.felnull.iwasi.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.client.IWasiClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IWasi.MODID)
public class IWasiForge {
    public IWasiForge() {
        EventBuses.registerModEventBus(IWasi.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        IWasi.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(FMLClientSetupEvent e) {
        IWasiClient.init();
    }
}
