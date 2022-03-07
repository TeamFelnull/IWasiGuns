package dev.felnull.iwasi.fabric;

import dev.felnull.iwasi.IWasi;
import net.fabricmc.api.ModInitializer;

public class IWasiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        IWasi.init();
    }
}
