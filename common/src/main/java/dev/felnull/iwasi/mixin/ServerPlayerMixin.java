package dev.felnull.iwasi.mixin;

import dev.felnull.iwasi.data.IIWCashPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin implements IIWCashPlayer {
    private int lastSelected = -1;
    private ItemStack lastMainHandItem = ItemStack.EMPTY;
    private ItemStack lastOffHandItem = ItemStack.EMPTY;

    @Override
    public int getLastSelected() {
        return lastSelected;
    }

    @Override
    public void setLastSelected(int num) {
        this.lastSelected = num;
    }

    @Override
    public ItemStack getLastMainHandItem() {
        return lastMainHandItem;
    }

    @Override
    public ItemStack getLastOffHandItem() {
        return lastOffHandItem;
    }

    @Override
    public void setLastMainHandItem(ItemStack stack) {
        lastMainHandItem = stack;
    }

    @Override
    public void setLastOffHandItem(ItemStack stack) {
        lastOffHandItem = stack;
    }
}