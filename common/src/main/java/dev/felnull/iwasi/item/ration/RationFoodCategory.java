package dev.felnull.iwasi.item.ration;

import dev.felnull.iwasi.utils.IWGItemUtils;
import dev.felnull.otyacraftengine.util.OEItemUtils;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Comparator;

public enum RationFoodCategory implements StringRepresentable {
    NONE("none", RationFoodCategory::noneContent, 0x6D3F1C),
    MEAT("meat", RationFoodCategory::meatContent, 0x561E00),
    FISH("fish", RationFoodCategory::fishContent, 0x74B8BF),
    GRAIN("grain", RationFoodCategory::grainContent, 0xC69645),
    VEGETABLE("vegetable", RationFoodCategory::vegetableContent, 0x072F18),
    SEAWEED("seaweed", RationFoodCategory::seaweedContent, 0x103E28),
    FRUIT("fruit", RationFoodCategory::fruitContent, 0xEA6E13),
    DESSERT("dessert", RationFoodCategory::dessertContent, 0xB5B5B5),
    DRINK("drink", RationFoodCategory::drinkContent, 0x933333),
    DARK("dark", RationFoodCategory::darkContent, 0x2D2D2D),
    GOLDEN("golden", RationFoodCategory::goldenContent, 0xE9B115);
    private final String name;
    private final ContentGetter contentGetter;
    private final int color;

    RationFoodCategory(String name, ContentGetter contentGetter, int color) {
        this.name = name;
        this.contentGetter = contentGetter;
        this.color = color;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public static Pair<RationFoodCategory, Float> getByItemStack(ItemStack stack) {
        var most = Arrays.stream(values()).map(n -> Pair.of(n, n.contentGetter.calculate(stack))).max(Comparator.comparingDouble(Pair::getRight));
        return most.orElse(Pair.of(RationFoodCategory.NONE, 0f));
    }

    private static float noneContent(ItemStack stack) {
        return 1f;
    }

    private static float meatContent(ItemStack stack) {
        float val = 0f;
        var food = OEItemUtils.getFoodProperties(stack, null);
        if (food != null && food.isMeat())
            val += 1.5f;
        if (IWGItemUtils.isMeatFood(stack))
            val += 3f;
        return val;
    }

    private static float fishContent(ItemStack stack) {
        if (IWGItemUtils.isFishFood(stack))
            return 3f;
        return 0f;
    }

    private static float grainContent(ItemStack stack) {
        if (IWGItemUtils.isGrainFood(stack))
            return 3f;
        return 0f;
    }

    private static float vegetableContent(ItemStack stack) {
        if (IWGItemUtils.isVegetableFood(stack))
            return 3f;
        return 0f;
    }

    private static float seaweedContent(ItemStack stack) {
        if (IWGItemUtils.isSeaweedFood(stack))
            return 3f;
        return 0f;
    }

    private static float fruitContent(ItemStack stack) {
        if (IWGItemUtils.isFruitFood(stack))
            return 3f;
        return 0f;
    }

    private static float dessertContent(ItemStack stack) {
        if (IWGItemUtils.isDessertFood(stack))
            return 3f;
        return 0f;
    }

    private static float drinkContent(ItemStack stack) {
        if (IWGItemUtils.isDrinkFood(stack))
            return 3f;
        return 0f;
    }

    private static float darkContent(ItemStack stack) {
        if (IWGItemUtils.isDarkFood(stack))
            return 5f;

        FoodProperties fp = OEItemUtils.getFoodProperties(stack, null);
        if (fp != null) {
            int p = 0;

            var efs = fp.getEffects();
            for (com.mojang.datafixers.util.Pair<MobEffectInstance, Float> ef : efs) {
                var efc = ef.getFirst().getEffect();
                if (efc.getCategory() == MobEffectCategory.BENEFICIAL)
                    p--;
                else if (efc.getCategory() == MobEffectCategory.HARMFUL)
                    p++;
            }

            if (p > 0)
                return p * 3;
        }

        return 0f;
    }

    private static float goldenContent(ItemStack stack) {
        if (IWGItemUtils.isGoldenFood(stack))
            return 5f;

        FoodProperties fp = OEItemUtils.getFoodProperties(stack, null);
        if (fp != null) {
            int p = 0;

            var efs = fp.getEffects();
            for (com.mojang.datafixers.util.Pair<MobEffectInstance, Float> ef : efs) {
                var efc = ef.getFirst().getEffect();
                if (efc.getCategory() == MobEffectCategory.BENEFICIAL)
                    p++;
                else if (efc.getCategory() == MobEffectCategory.HARMFUL)
                    p--;
            }

            if (p > 0)
                return p * 2;
        }
        
        return 0f;
    }

    private static interface ContentGetter {
        float calculate(ItemStack stack);
    }
}
