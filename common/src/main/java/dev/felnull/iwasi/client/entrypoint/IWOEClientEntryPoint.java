package dev.felnull.iwasi.client.entrypoint;

import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.otyacraftengine.client.entrypoint.IOEClientEntryPoint;
import dev.felnull.otyacraftengine.client.entrypoint.OEClientEntryPoint;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

@OEClientEntryPoint
public class IWOEClientEntryPoint implements IOEClientEntryPoint {
    @Override
    public void onModelRegistry(Consumer<ResourceLocation> addModel) {
        IWModels.init(addModel);
    }
}
