package dev.felnull.iwasi.item.ration;

import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ration {

    public static Pair<RationFoodCategory, RationEffectCategory> getRationCategories(List<ItemStack> foods) {

        Map<RationEffectCategory, Float> effectCategories = new HashMap<>();

        for (ItemStack food : foods) {
            var catE = RationEffectCategory.getByFoodItemStack(food);
            var cat = catE.getLeft();
            float catCount = 0;
            var catCt = effectCategories.get(cat);
            if (catCt != null)
                catCount = catCt;

            effectCategories.put(cat, catCount + food.getCount() * Math.max(catE.getRight(), 1f));
        }

        var mostEffect = effectCategories.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).map(Map.Entry::getKey);


        return Pair.of(RationFoodCategory.NONE, mostEffect.orElse(RationEffectCategory.MEDIOCRE));
    }
}
