package dev.felnull.iwasi.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.felnull.iwasi.IWasi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class IWCreativeModeTab {
    public static final CreativeModeTab MOD_TAB = CreativeTabRegistry.create(new ResourceLocation(IWasi.MODID, IWasi.MODID), () -> new ItemStack(Items.APPLE));
}
