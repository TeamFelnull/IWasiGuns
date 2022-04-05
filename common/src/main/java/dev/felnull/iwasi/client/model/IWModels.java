package dev.felnull.iwasi.client.model;

import dev.felnull.iwasi.IWasi;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class IWModels {
    public static final ResourceLocation GLOCK_17_MAIN = new ResourceLocation(IWasi.MODID, "gun/glock_17/main");
    public static final ResourceLocation GLOCK_17_SLIDE = new ResourceLocation(IWasi.MODID, "gun/glock_17/slide");
    public static final ResourceLocation GLOCK_17_MAGAZINE = new ResourceLocation(IWasi.MODID, "gun/glock_17/magazine");

    public static void init(Consumer<ResourceLocation> addModel) {
        addModel.accept(GLOCK_17_MAIN);
        addModel.accept(GLOCK_17_SLIDE);
        addModel.accept(GLOCK_17_MAGAZINE);
    }
}
