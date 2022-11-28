package dev.felnull.iwasi.client.handler;

import dev.architectury.event.EventResult;
import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.otyacraftengine.client.event.OBJLoaderEvent;
import net.minecraft.resources.ResourceLocation;

public class ClientHandler {
    public static void init() {
        OBJLoaderEvent.LOAD_CHECK.register(ClientHandler::objLoad);
    }

    private static EventResult objLoad(ResourceLocation resourceLocation) {
        if (IWasiGuns.MODID.equals(resourceLocation.getNamespace()))
            return EventResult.interruptTrue();
        return EventResult.pass();
    }
}
