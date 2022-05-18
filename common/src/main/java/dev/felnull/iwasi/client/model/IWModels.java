package dev.felnull.iwasi.client.model;

import dev.felnull.iwasi.IWasi;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class IWModels {
    public static final ResourceLocation ORIGIN = new ResourceLocation(IWasi.MODID, "item/origin");

    public static final ResourceLocation TEST_GUN = new ResourceLocation(IWasi.MODID, "gun/test_gun");

    public static final ResourceLocation GLOCK_17_MAIN = new ResourceLocation(IWasi.MODID, "gun/glock_17/main");
    public static final ResourceLocation GLOCK_17_SLIDE = new ResourceLocation(IWasi.MODID, "gun/glock_17/slide");
    public static final ResourceLocation GLOCK_17_MAGAZINE = new ResourceLocation(IWasi.MODID, "gun/glock_17/magazine");

    public static final ResourceLocation AR_57_MAIN = new ResourceLocation(IWasi.MODID, "gun/ar_57/main");

    public static final ResourceLocation PKP_MAIN = new ResourceLocation(IWasi.MODID, "gun/pkp/main");

    public static final ResourceLocation BULLET = new ResourceLocation(IWasi.MODID, "gun/bullet/bullet");

    public static void init(Consumer<ResourceLocation> addModel) {
        addModel.accept(ORIGIN);

        addModel.accept(TEST_GUN);

        addModel.accept(GLOCK_17_MAIN);
        addModel.accept(GLOCK_17_SLIDE);
        addModel.accept(GLOCK_17_MAGAZINE);

        addModel.accept(AR_57_MAIN);

        addModel.accept(PKP_MAIN);

        addModel.accept(BULLET);
    }
}
