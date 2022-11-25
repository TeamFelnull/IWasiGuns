package dev.felnull.iwasi.data;

import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.recipe.IWGRecipeSerializers;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import dev.felnull.otyacraftengine.tag.PlatformItemTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class IWGRecipeProviderWrapper extends RecipeProviderWrapper {
    public IWGRecipeProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    @Override
    public void generateRecipe(Consumer<FinishedRecipe> exporter, RecipeProviderAccess providerAccess) {
        ShapedRecipeBuilder.shaped(IWGItems.RATION_CAN.get())
                .define('I', PlatformItemTags.ironIngots())
                .define('N', PlatformItemTags.ironNuggets().getKey())
                .pattern(" N ")
                .pattern("I I")
                .pattern(" I ")
                .unlockedBy(providerAccess.getHasName(Items.IRON_INGOT), providerAccess.has(Items.IRON_INGOT))
                .save(exporter);

        SpecialRecipeBuilder.special(IWGRecipeSerializers.RATION.get()).save(exporter, modLocText("ration"));
    }

    private static String modLocText(String id) {
        return new ResourceLocation(IWasiGuns.MODID, id).toString();
    }
}
