package dev.felnull.iwasi.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface IIWCashServerPlayer {
    int getLastSelected();

    void setLastSelected(int num);

    ItemStack getLastHandItem(InteractionHand hand);

    void setLastHandItem(InteractionHand hand, ItemStack stack);

    boolean getLastContinuousHold();

    void setLastContinuousHold(boolean hold);
}