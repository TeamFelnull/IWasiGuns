package dev.felnull.iwasi.integration.jei;

import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.ration.Ration;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class RationJEIRecipeMaker {
    public static List<CraftingRecipe> createRecipes() {
        List<CraftingRecipe> ret = new ArrayList<>();
        Item[] foods = Registry.ITEM.stream().filter(n -> n.getFoodProperties() != null).toArray(Item[]::new);

        Ingredient foodIngredient = Ingredient.of(foods);
        Ingredient rationCanIngredient = Ingredient.of(new ItemStack(IWGItems.RATION_CAN.get()));

        NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                foodIngredient, foodIngredient, foodIngredient,
                foodIngredient, rationCanIngredient, foodIngredient,
                foodIngredient, foodIngredient, foodIngredient);

        ret.add(new ShapedRecipe(new ResourceLocation(IWasiGuns.MODID, "ration.jei"), "jei.ration", 3, 3, inputs, Ration.createB1Unit(new ItemStack(IWGItems.RATION.get()))));

        return ret;
    }
}
