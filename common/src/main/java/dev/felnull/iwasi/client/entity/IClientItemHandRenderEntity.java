package dev.felnull.iwasi.client.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface IClientItemHandRenderEntity {
    ItemStack getLastHandItem(InteractionHand hand);

    void setLastHandItem(InteractionHand hand, ItemStack stack);

    ItemStack getLastHandItemOld(InteractionHand hand);

    void setLastHandItemOld(InteractionHand hand, ItemStack stack);
}
