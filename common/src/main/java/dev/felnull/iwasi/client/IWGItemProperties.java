package dev.felnull.iwasi.client;

import dev.architectury.registry.item.ItemPropertiesRegistry;
import dev.felnull.iwasi.IWasiGuns;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.ration.Ration;
import dev.felnull.iwasi.item.ration.RationFoodCategory;
import net.minecraft.resources.ResourceLocation;

public class IWGItemProperties {
    public static void init() {
        ItemPropertiesRegistry.register(IWGItems.RATION.get(), new ResourceLocation(IWasiGuns.MODID, "golden_ration"), (itemStack, clientLevel, livingEntity, i) -> {
            RationFoodCategory fc = Ration.getCategory(itemStack).getLeft();
            return fc == RationFoodCategory.GOLDEN ? 1.0F : 0.0F;
        });
    }
}
