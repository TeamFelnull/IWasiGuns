package dev.felnull.iwasi.fabric;

import dev.felnull.iwasi.IWasiGuns;
import net.fabricmc.api.ModInitializer;

public class IWasiGunsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        IWasiGuns.init();
    }
}
