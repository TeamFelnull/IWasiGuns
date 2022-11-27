package dev.felnull.iwasi.integration.jei;

import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.iwasi.item.IWGItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShulkerBoxColoring;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

//https://github.com/mezz/JustEnoughItems/blob/HEAD/Common/src/main/java/mezz/jei/common/plugins/vanilla/VanillaPlugin.java
@JeiPlugin
public class IWasiGunsJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(IWasiGuns.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var jeiHelper = registration.getJeiHelpers();
        var stackHelper = jeiHelper.getStackHelper();

        var level = Minecraft.getInstance().level;
        Objects.requireNonNull(level, "Level is null");
        var recipeManager = level.getRecipeManager();

        registration.addRecipes(RecipeTypes.CRAFTING, specialCraftingRecipe(recipeManager));
       // registration.addItemStackInfo(new ItemStack(IWGItems.RATION.get()), Component.literal("FCOH"));
    }


    private List<CraftingRecipe> specialCraftingRecipe(RecipeManager recipeManager) {
        Map<Class<? extends CraftingRecipe>, Supplier<List<CraftingRecipe>>> recipes = new IdentityHashMap<>();

        recipes.put(ShulkerBoxColoring.class, RationJEIRecipeMaker::createRecipes);

        var allRecipe = recipeManager.getAllRecipesFor(RecipeType.CRAFTING);
        return allRecipe.stream()
                .map(CraftingRecipe::getClass)
                .distinct()
                .filter(recipes::containsKey)
                .limit(recipes.size())
                .flatMap(rc -> {
                    Supplier<List<CraftingRecipe>> supplier = recipes.get(rc);
                    try {
                        return supplier.get().stream();
                    } catch (RuntimeException ignored) {
                    }
                    return Stream.of();
                }).toList();
    }
}
