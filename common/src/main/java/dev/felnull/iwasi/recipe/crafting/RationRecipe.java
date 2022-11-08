package dev.felnull.iwasi.recipe.crafting;

import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.RationItem;
import dev.felnull.iwasi.recipe.IWGRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.HoneyBottleItem;
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

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(IWGItems.RATION_CAN.get())) {
                    ration++;
                } else if (isCanningFood(stack)) {
                    food++;
                } else {
                    return false;
                }

                if (ration > 1 || food > 3)
                    return false;
            }
        }

        return ration == 1 && food == 3;
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        List<ItemStack> foods = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.is(IWGItems.RATION_CAN.get()) && isCanningFood(stack)) {
                ItemStack food = stack.copy();
                food.setCount(1);
                foods.add(food);
            }
        }

        ItemStack ret = new ItemStack(IWGItems.RATION.get());
        RationItem.setFoods(ret, foods);

        return ret;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return w * h >= 4;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IWGRecipeSerializers.RATION.get();
    }

    private static boolean isCanningFood(ItemStack stack) {
        return stack.isEdible() && !(stack.getItem() instanceof BowlFoodItem || stack.getItem() instanceof RationItem || stack.getItem() instanceof HoneyBottleItem);
    }
}
