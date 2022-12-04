package dev.felnull.iwasi.client.model;

import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import dev.felnull.otyacraftengine.client.model.ModelCollectiveRegister;
import dev.felnull.otyacraftengine.client.model.ModelHolder;
import net.minecraft.resources.ResourceLocation;

public class IWGModels {
    private static final ModelCollectiveRegister REGISTER = ModelCollectiveRegister.create();
    public static final ModelHolder GLOCK_17_MAIN = gunModel("glock_17", "main");
    public static final ModelHolder GLOCK_17_SLIDE = gunModel("glock_17", "slide");
    public static final ModelHolder GLOCK_17_MAGAZINE = gunModel("glock_17", "magazine");

    public static void init(ModelRegister modelRegister) {
        REGISTER.registering(modelRegister);
    }

    private static ModelHolder gunModel(String gun, String parts) {
        return REGISTER.register(new ResourceLocation(IWasiGuns.MODID, "gun/" + gun + "/" + parts));
    }
}
