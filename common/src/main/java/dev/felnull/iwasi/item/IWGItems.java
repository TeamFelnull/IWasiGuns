package dev.felnull.iwasi.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasiGuns;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class IWGItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(IWasiGuns.MODID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> RATION_CAN = register("ration_can");
    public static final RegistrySupplier<Item> RATION = register("ration", () -> new RationItem(new Item.Properties().tab(IWGCreativeModeTab.MOD_TAB).stacksTo(4).food(IWGFoods.RATION)));

    private static RegistrySupplier<Item> register(String name) {
        return register(name, () -> new Item(new Item.Properties().tab(IWGCreativeModeTab.MOD_TAB)));
    }

    private static RegistrySupplier<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    public static void init() {
        ITEMS.register();
    }
}
