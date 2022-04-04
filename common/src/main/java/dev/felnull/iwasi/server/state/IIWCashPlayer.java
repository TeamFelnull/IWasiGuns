package dev.felnull.iwasi.server.state;

import net.minecraft.world.item.ItemStack;

public interface IIWCashPlayer {
    int getLastSelected();

    void setLastSelected(int num);

    ItemStack getLastMainHandItem();

    ItemStack getLastOffHandItem();

    void setLastMainHandItem(ItemStack stack);

    void setLastOffHandItem(ItemStack stack);
}
