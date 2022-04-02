package dev.felnull.iwasi.client.model;

import dev.felnull.iwasi.IWasi;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class IWModels {
    public static final ResourceLocation TEST_GUN = new ResourceLocation(IWasi.MODID, "gun/mk23");
    public static final ResourceLocation ORIGIN = new ResourceLocation(IWasi.MODID, "item/origin");

    public static final ResourceLocation GLOCK_MAIN = new ResourceLocation(IWasi.MODID, "gun/glock/main");
    public static final ResourceLocation GLOCK_SLIDE = new ResourceLocation(IWasi.MODID, "gun/glock/slide");
    public static final ResourceLocation GLOCK_MAGAZINE = new ResourceLocation(IWasi.MODID, "gun/glock/magazine");

    public static void init(Consumer<ResourceLocation> addModel) {
        addModel.accept(TEST_GUN);
        addModel.accept(ORIGIN);

        addModel.accept(GLOCK_MAIN);
        addModel.accept(GLOCK_SLIDE);
        addModel.accept(GLOCK_MAGAZINE);
    }
}
