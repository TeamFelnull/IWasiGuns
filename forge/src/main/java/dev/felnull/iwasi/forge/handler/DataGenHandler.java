package dev.felnull.iwasi.forge.handler;

import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.iwasi.data.IWasiGunsDataGenerator;
import dev.felnull.otyacraftengine.forge.data.CrossDataGeneratorAccesses;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IWasiGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenHandler {
    @SubscribeEvent
    public static void onDataGen(GatherDataEvent event) {
        IWasiGunsDataGenerator.init(CrossDataGeneratorAccesses.create(event));
    }
}