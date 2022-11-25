package dev.felnull.iwasi.utils;

import dev.felnull.iwasi.item.IWGItemTags;
import dev.felnull.otyacraftengine.tag.PlatformItemTags;
import dev.felnull.otyacraftengine.util.OEItemUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.CakeBlock;

public class IWGItemUtils {
    public static boolean isMeatFood(ItemStack stack) {
        return stack.is(IWGItemTags.MEAT_FOODS);
    }

    public static boolean isFishFood(ItemStack stack) {
        return stack.is(IWGItemTags.FISH_FOODS);
    }

    public static boolean isGrainFood(ItemStack stack) {
        if (OEItemUtils.getFoodProperties(stack, null) != null && (stack.is(PlatformItemTags.grains().getKey()) || stack.is(PlatformItemTags.seeds().getKey())))
            return true;
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
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof CakeBlock)
            return true;
        return stack.is(IWGItemTags.DESSERT_FOODS);
    }

    public static boolean isDrinkFood(ItemStack stack) {
        if (stack.is(Items.POTION))
            return true;

        return stack.is(IWGItemTags.DRINK_FOODS);
    }

    public static boolean isDarkFood(ItemStack stack) {
        return stack.is(IWGItemTags.DARK_FOODS);
    }

    public static boolean isGoldenFood(ItemStack stack) {
        if (OEItemUtils.getFoodProperties(stack, null) != null) {
            if (stack.getRarity() == Rarity.EPIC)
                return true;
        }
        return stack.is(IWGItemTags.GOLDEN_FOODS);
    }
}
