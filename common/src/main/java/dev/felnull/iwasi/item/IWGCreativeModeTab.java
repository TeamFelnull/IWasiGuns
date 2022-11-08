package dev.felnull.iwasi.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.felnull.iwasi.IWasiGuns;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class IWGCreativeModeTab {
    public static final CreativeModeTab MOD_TAB = CreativeTabRegistry.create(new ResourceLocation(IWasiGuns.MODID, IWasiGuns.MODID), () -> new ItemStack(Items.APPLE));
}
