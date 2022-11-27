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
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.ArrayList;
import java.util.List;

public class RationJEIRecipeMaker {
    public static List<CraftingRecipe> create() {
        List<CraftingRecipe> ret = new ArrayList<>();

        String group = "jei." + IWasiGuns.MODID + ".ration";
        Ingredient foods = Ingredient.of(Registry.ITEM.stream().filter(Ration::canContainFood).toArray(Item[]::new));
        Ingredient drinks = Ingredient.of(Ration.containDrinkTags());
        Ingredient rationCan = Ingredient.of(IWGItems.RATION_CAN.get());
        ItemStack output = new ItemStack(IWGItems.RATION.get());

        ret.add(new ShapelessRecipe(new ResourceLocation(IWasiGuns.MODID, "jei.ration.food_only"), group, output, NonNullList.of(Ingredient.EMPTY, rationCan, foods, foods, foods, foods, foods, foods, foods, foods)));
        ret.add(new ShapelessRecipe(new ResourceLocation(IWasiGuns.MODID, "jei.ration.drink"), group, output, NonNullList.of(Ingredient.EMPTY, rationCan, drinks, foods, foods, foods, foods, foods, foods, foods)));

        return ret;
    }
}
