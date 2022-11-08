package dev.felnull.iwasi.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.iwasi.recipe.crafting.RationRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

import java.util.function.Supplier;

public class IWGRecipeSerializers {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(IWasiGuns.MODID, Registry.RECIPE_SERIALIZER_REGISTRY);
    public static final RegistrySupplier<SimpleRecipeSerializer<RationRecipe>> RATION = register("ration", () -> new SimpleRecipeSerializer<>(RationRecipe::new));

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> RegistrySupplier<S> register(String name, Supplier<S> recipe) {
        return RECIPE_SERIALIZERS.register(name, recipe);
    }

    public static void init() {
        RECIPE_SERIALIZERS.register();
    }
}
