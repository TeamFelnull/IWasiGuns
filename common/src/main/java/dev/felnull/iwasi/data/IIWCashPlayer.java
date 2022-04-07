package dev.felnull.iwasi.data;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface IIWCashPlayer {
    int getLastSelected();

    void setLastSelected(int num);

    ItemStack getLastHandItem(InteractionHand hand);

    void setLastHandItem(InteractionHand hand, ItemStack stack);

    boolean getLastHold();

    void setLastHold(boolean hold);
}