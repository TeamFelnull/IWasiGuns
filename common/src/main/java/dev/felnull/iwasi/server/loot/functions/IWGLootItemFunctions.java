package dev.felnull.iwasi.server.loot.functions;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasiGuns;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import java.util.function.Supplier;

public class IWGLootItemFunctions {
    private static final DeferredRegister<LootItemFunctionType> LOOT_ITEM_FUNCTION_TYPE = DeferredRegister.create(IWasiGuns.MODID, Registry.LOOT_FUNCTION_REGISTRY);
    public static final RegistrySupplier<LootItemFunctionType> SET_RATION_TYPE = register("set_ration_type", SetRationTypeFunction.Serializer::new);

    private static RegistrySupplier<LootItemFunctionType> register(String name, Supplier<Serializer<? extends LootItemFunction>> serializer) {
        return LOOT_ITEM_FUNCTION_TYPE.register(name, () -> new LootItemFunctionType(serializer.get()));
    }

    public static void init() {
        LOOT_ITEM_FUNCTION_TYPE.register();
    }
}
