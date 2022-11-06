package dev.felnull.iwasi.fabric.data;

import dev.felnull.iwasi.data.IWasiGunsDataGenerator;
import dev.felnull.otyacraftengine.fabric.data.CrossDataGeneratorAccesses;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class IWasiGunsDataGeneratorFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        IWasiGunsDataGenerator.init(CrossDataGeneratorAccesses.create(fabricDataGenerator));
    }
}
