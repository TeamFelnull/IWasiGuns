package dev.felnull.iwasi.item;

import dev.felnull.iwasi.IWasiGuns;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class IWGItemTags {
    public static final TagKey<Item> MEAT_FOODS = bind("meat_foods");
    public static final TagKey<Item> FISH_FOODS = bind("fish_foods");
    public static final TagKey<Item> GRAIN_FOODS = bind("grain_foods");
    public static final TagKey<Item> VEGETABLE_FOODS = bind("vegetable_foods");
    public static final TagKey<Item> SEAWEED_FOODS = bind("seaweed_foods");
    public static final TagKey<Item> FRUIT_FOODS = bind("fruit_foods");
    public static final TagKey<Item> DESSERT_FOODS = bind("dessert_foods");
    public static final TagKey<Item> DRINK_FOODS = bind("drink_foods");
    public static final TagKey<Item> DARK_FOODS = bind("dark_foods");
    public static final TagKey<Item> GOLDEN_FOODS = bind("golden_foods");

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(IWasiGuns.MODID, name));
    }
}
