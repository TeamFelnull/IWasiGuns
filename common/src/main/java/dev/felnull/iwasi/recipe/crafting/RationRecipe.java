package dev.felnull.iwasi.recipe.crafting;

import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.ration.Ration;
import dev.felnull.iwasi.recipe.IWGRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class RationRecipe extends CustomRecipe {
    public RationRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        int ration = 0;
        int food = 0;
        int drink = 0;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(IWGItems.RATION_CAN.get())) {
                    ration++;
                } else if (Ration.canContainDrink(stack)) {
                    drink++;
                } else if (Ration.canContainFood(stack)) {
                    food++;
                } else {
                    return false;
                }

                if (ration > 1 || food > getMaxFoodCount() || drink > 1)
                    return false;
            }
        }

        int foodCount = food + drink;
        return ration == 1 && foodCount >= 1 && foodCount <= getMaxFoodCount();
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        List<ItemStack> foods = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.is(IWGItems.RATION_CAN.get()) && (Ration.canContainFood(stack) || Ration.canContainDrink(stack))) {
                ItemStack food = stack.copy();
                food.setCount(1);
                foods.add(food);
            }
        }

        return Ration.create(new ItemStack(IWGItems.RATION.get()), Ration.sortFoods(foods));
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return w * h >= getMaxFoodCount() + 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IWGRecipeSerializers.RATION.get();
    }

    private static int getMaxFoodCount() {
        return 8;
    }
}
