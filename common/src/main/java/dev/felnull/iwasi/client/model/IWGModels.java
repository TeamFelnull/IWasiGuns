package dev.felnull.iwasi.client.model;

import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import dev.felnull.otyacraftengine.client.model.ModelCollectiveRegister;
import dev.felnull.otyacraftengine.client.model.ModelHolder;
import net.minecraft.resources.ResourceLocation;

public class IWGModels {
    private static final ModelCollectiveRegister REGISTER = ModelCollectiveRegister.create();
    public static final ModelHolder SKEWER = REGISTER.register(new ResourceLocation(IWasiGuns.MODID, "item/skewer/kusi"));

    public static void init(ModelRegister modelRegister) {
        REGISTER.registering(modelRegister);
    }
}
