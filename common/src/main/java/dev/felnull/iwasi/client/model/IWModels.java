package dev.felnull.iwasi.client.model;

import dev.felnull.iwasi.IWasi;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class IWModels {
    public static final ResourceLocation TEST_GUN = new ResourceLocation(IWasi.MODID, "gun/mk23");

    public static void init(Consumer<ResourceLocation> addModel) {
        addModel.accept(TEST_GUN);
    }
}
