package dev.felnull.iwasi.item.ration;

import dev.felnull.otyacraftengine.util.OENbtUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Ration {

    public static Pair<RationFoodCategory, RationEffectCategory> getRationCategories(List<ItemStack> foods) {

        Map<RationEffectCategory, Float> effectCategories = new HashMap<>();
        Map<RationFoodCategory, Float> foodCategories = new HashMap<>();

        for (ItemStack food : foods) {
            var ecatE = RationEffectCategory.getByItemStack(food);
            var ecat = ecatE.getLeft();
            float ecatCount = 0;
            var ecatCt = effectCategories.get(ecat);
            if (ecatCt != null)
                ecatCount = ecatCt;

            effectCategories.put(ecat, ecatCount + food.getCount() * Math.max(ecatE.getRight(), 1f));

            var fcatE = RationFoodCategory.getByItemStack(food);
            var fcat = fcatE.getLeft();

            float fcatCount = 0;

            var fcatCt = foodCategories.get(fcat);
            if (fcatCt != null)
                fcatCount = fcatCt;

            foodCategories.put(fcat, fcatCount + food.getCount() * Math.max(fcatE.getRight(), 1f));
        }

        var mostEffect = effectCategories.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).map(Map.Entry::getKey);
        var mostFood = foodCategories.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).map(Map.Entry::getKey);

        return Pair.of(mostFood.orElse(RationFoodCategory.NONE), mostEffect.orElse(RationEffectCategory.MEDIOCRE));
    }

    public static void setFoods(ItemStack stack, List<ItemStack> foodStacks) {
        OENbtUtils.writeList(stack.getOrCreateTag(), "foods", foodStacks.stream().filter(n -> !n.isEmpty()).toList(), stack1 -> stack1.save(new CompoundTag()));
        setCategory(stack, Ration.getRationCategories(foodStacks));
    }

    public static void setCategory(ItemStack stack, Pair<RationFoodCategory, RationEffectCategory> category) {
        var tag = stack.getOrCreateTag();
        OENbtUtils.writeEnum(tag, "food", category.getLeft());
        OENbtUtils.writeEnum(tag, "effect", category.getRight());
    }

    public static List<ItemStack> getFoods(ItemStack stack) {
        List<ItemStack> stacks = new ArrayList<>();

        if (stack.hasTag())
            OENbtUtils.readList(stack.getTag(), "foods", stacks, tag -> ItemStack.of((CompoundTag) tag), Tag.TAG_COMPOUND);

        return stacks;
    }

    public static Pair<RationFoodCategory, RationEffectCategory> getCategory(ItemStack stack) {
        if (!stack.hasTag())
            return Pair.of(RationFoodCategory.NONE, RationEffectCategory.MEDIOCRE);

        var tag = stack.getTag();
        var food = OENbtUtils.readEnum(tag, "food", RationFoodCategory.class, RationFoodCategory.NONE);
        var effect = OENbtUtils.readEnum(tag, "effect", RationEffectCategory.class, RationEffectCategory.MEDIOCRE);

        return Pair.of(food, effect);
    }
}
