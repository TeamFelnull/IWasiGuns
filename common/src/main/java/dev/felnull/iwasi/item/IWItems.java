package dev.felnull.iwasi.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.gun.IWGuns;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class IWItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(IWasi.MODID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> TEST = register("test_item", () -> new TestItem(new Item.Properties().tab(IWCreativeModeTab.MOD_TAB)));

    public static final RegistrySupplier<Item> TEST_GUN = register("test_gun", () -> new GunItem(IWGuns.TEST_GUN, new Item.Properties()));
    public static final RegistrySupplier<Item> GLOCK_17_GUN = register("glock_17", () -> new GunItem(IWGuns.GLOCK_17, new Item.Properties().tab(IWCreativeModeTab.MOD_TAB)));
    public static final RegistrySupplier<Item> AR_57_GUN = register("ar_57", () -> new GunItem(IWGuns.AR_57, new Item.Properties().tab(IWCreativeModeTab.MOD_TAB)));
    public static final RegistrySupplier<Item> PKP_GUN = register("pkp", () -> new GunItem(IWGuns.PKP, new Item.Properties().tab(IWCreativeModeTab.MOD_TAB)));

    public static final RegistrySupplier<Item> GLOCK_17_MAGAZINE = register("glock_17_magazine", () -> new MagazineItem(new Item.Properties().tab(IWCreativeModeTab.MOD_TAB)));

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
