package dev.felnull.iwasi.fabric.client;

import dev.felnull.iwasi.client.IWasiGunsClient;
import net.fabricmc.api.ClientModInitializer;

public class IWasiGunsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IWasiGunsClient.init();
    }
}
