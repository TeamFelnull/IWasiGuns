package dev.felnull.iwasi.utils;

import dev.felnull.iwasi.item.IWGItemTags;
import net.minecraft.world.item.ItemStack;

public class IWGItemUtils {
    public static boolean isMeatFood(ItemStack stack) {
        return stack.is(IWGItemTags.MEAT_FOODS);
    }

    public static boolean isFishFood(ItemStack stack) {
        return stack.is(IWGItemTags.FISH_FOODS);
    }

    public static boolean isGrainFood(ItemStack stack) {
        return stack.is(IWGItemTags.GRAIN_FOODS);
    }

    public static boolean isVegetableFood(ItemStack stack) {
        return stack.is(IWGItemTags.VEGETABLE_FOODS);
    }

    public static boolean isSeaweedFood(ItemStack stack) {
        return stack.is(IWGItemTags.SEAWEED_FOODS);
    }

    public static boolean isFruitFood(ItemStack stack) {
        return stack.is(IWGItemTags.FRUIT_FOODS);
    }

    public static boolean isDessertFood(ItemStack stack) {
        return stack.is(IWGItemTags.DESSERT_FOODS);
    }

    public static boolean isDrinkFood(ItemStack stack) {
        return stack.is(IWGItemTags.DRINK_FOODS);
    }

    public static boolean isDarkFood(ItemStack stack) {
        return stack.is(IWGItemTags.DARK_FOODS);
    }

    public static boolean isGoldenFood(ItemStack stack) {
        return stack.is(IWGItemTags.GOLDEN_FOODS);
    }
}
