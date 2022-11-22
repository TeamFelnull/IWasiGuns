package dev.felnull.iwasi.recipe.crafting;

import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.RationItem;
import dev.felnull.iwasi.item.ration.Ration;
import dev.felnull.iwasi.recipe.IWGRecipeSerializers;
import dev.felnull.otyacraftengine.util.OEItemUtils;
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

                if (ration > 1 || food > getMaxFoodCount())
                    return false;
            }
        }

        return ration == 1 && food >= 1 && food <= getMaxFoodCount();
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
        Ration.setFoods(ret, OEItemUtils.overlapItemStacks(foods));

        return ret;
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

    private static boolean isCanningFood(ItemStack stack) {
        return stack.isEdible() && !(stack.getItem() instanceof RationItem);
    }
}