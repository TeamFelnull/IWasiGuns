package dev.felnull.iwasi.data;

import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.iwasi.recipe.IWGRecipeSerializers;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class IWGRecipeProviderWrapper extends RecipeProviderWrapper {
    public IWGRecipeProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    @Override
    public void generateRecipe(Consumer<FinishedRecipe> exporter, RecipeProviderAccess providerAccess) {
        SpecialRecipeBuilder.special(IWGRecipeSerializers.RATION.get()).save(exporter, getModLocationText("ration"));
    }

    private static String getModLocationText(String path) {
        return new ResourceLocation(IWasiGuns.MODID, path).toString();
    }
}
