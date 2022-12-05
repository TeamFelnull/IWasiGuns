package dev.felnull.iwasi.server.loot;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.item.ration.Ration;
import dev.felnull.iwasi.item.ration.RationEffectCategory;
import dev.felnull.iwasi.item.ration.RationFoodCategory;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public enum RationLootType implements StringRepresentable {
    B1("b1", Ration::createB1Unit),
    B2("b2", Ration::createB2Unit),
    B3("b3", Ration::createB3Unit),
    GOLDEN("golden", RationLootType::createGoldenUnit),
    DARK("dark", RationLootType::createDarkUnit),
    SEA("sea", RationLootType::createSeaUnit),
    CAVE("cave", RationLootType::createCaveUnit),
    NETHER("nether", RationLootType::createNetherUnit),
    END("end", RationLootType::createEndUnit);

    private final String name;
    private final Function<ItemStack, ItemStack> applyStack;

    RationLootType(String name, Function<ItemStack, ItemStack> applyStack) {
        this.name = name;
        this.applyStack = applyStack;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public ItemStack apply(ItemStack stack) {
        return applyStack.apply(stack);
    }

    public static RationLootType getByName(String name) {
        for (RationLootType rlt : values()) {
            if (rlt.getSerializedName().equals(name))
                return rlt;
        }
        return B1;
    }

    public static ItemStack createGoldenUnit(ItemStack stack) {
        List<ItemStack> foods = new ArrayList<>(ImmutableList.of(Items.CAKE, Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_CARROT, Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.COOKED_CHICKEN, Items.COOKED_MUTTON).stream().map(ItemStack::new).toList());
        foods.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING));
        Ration.setFoods(stack, foods);

        Ration.setCategory(stack, RationFoodCategory.GOLDEN, RationEffectCategory.HEALTHY);
        return stack;
    }

    public static ItemStack createDarkUnit(ItemStack stack) {
        List<ItemStack> foods = new ArrayList<>(ImmutableList.of(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.POISONOUS_POTATO, Items.CHICKEN, Items.PUFFERFISH).stream().map(ItemStack::new).toList());
        foods.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HARMING));
        Ration.setFoods(stack, foods);

        Ration.setCategory(stack, RationFoodCategory.DARK, RationEffectCategory.UNHEALTHY);
        return stack;
    }

    public static ItemStack createSeaUnit(ItemStack stack) {
        List<ItemStack> foods = new ArrayList<>(ImmutableList.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.DRIED_KELP).stream().map(ItemStack::new).toList());
        foods.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LONG_WATER_BREATHING));
        Ration.setFoods(stack, foods);

        Ration.setCategory(stack, RationFoodCategory.FISH, RationEffectCategory.MEDIOCRE);
        return stack;
    }

    public static ItemStack createCaveUnit(ItemStack stack) {
        List<ItemStack> foods = new ArrayList<>(ImmutableList.of(Items.MUSHROOM_STEW, Items.COOKED_BEEF, Items.BAKED_POTATO, Items.GLOW_BERRIES).stream().map(ItemStack::new).toList());
        foods.add(0, new ItemStack(Items.BREAD, 3));
        foods.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LONG_NIGHT_VISION));
        Ration.setFoods(stack, foods);

        Ration.setCategory(stack, RationFoodCategory.DESSERT, RationEffectCategory.MEDIOCRE);
        return stack;
    }

    public static ItemStack createNetherUnit(ItemStack stack) {
        List<ItemStack> foods = new ArrayList<>(ImmutableList.of(Items.GOLDEN_APPLE).stream().map(ItemStack::new).toList());
        foods.add(new ItemStack(Items.COOKED_PORKCHOP, 6));
        foods.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LONG_FIRE_RESISTANCE));
        Ration.setFoods(stack, foods);

        Ration.setCategory(stack, RationFoodCategory.FRUIT, RationEffectCategory.HEALTHY);
        return stack;
    }

    public static ItemStack createEndUnit(ItemStack stack) {
        Ration.setFoods(stack, ImmutableList.of(new ItemStack(Items.CHORUS_FRUIT, 7), new ItemStack(Items.MILK_BUCKET)));

        Ration.setCategory(stack, RationFoodCategory.DESSERT, RationEffectCategory.UNHEALTHY);
        return stack;
    }
}
