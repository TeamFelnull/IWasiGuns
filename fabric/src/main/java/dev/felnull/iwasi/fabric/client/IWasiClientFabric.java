package dev.felnull.iwasi.fabric.client;

import dev.felnull.iwasi.client.IWasiClient;
import net.fabricmc.api.ClientModInitializer;

public class IWasiClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IWasiClient.init();
    }
}
