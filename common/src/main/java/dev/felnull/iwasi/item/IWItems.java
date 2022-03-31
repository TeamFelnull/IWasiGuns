package dev.felnull.iwasi.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.item.gun.TestGunItem;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class IWItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(IWasi.MODID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> TEST_IWASI = register("test_iwasi", () -> new TestIWasiItem(new Item.Properties().tab(IWCreativeModeTab.MOD_TAB)));
    public static final RegistrySupplier<Item> TEST_GUN = register("test_gun", () -> new TestGunItem(new Item.Properties().tab(IWCreativeModeTab.MOD_TAB)));

    private static RegistrySupplier<Item> register(String name) {
        return register(name, () -> new Item(new Item.Properties().tab(IWCreativeModeTab.MOD_TAB)));
    }

    private static RegistrySupplier<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    public static void init() {
        ITEMS.register();
    }
}
