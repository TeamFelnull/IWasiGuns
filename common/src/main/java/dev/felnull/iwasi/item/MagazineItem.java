package dev.felnull.iwasi.item;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagazineItem extends Item {
    public MagazineItem(Properties properties) {
        super(properties);
    }

    public int getCapacity() {
        return 17;
    }

    public static int getRemainingBullets(ItemStack stack) {
        if (stack.getTag() != null)
            return stack.getTag().getInt("RemainingBullets");
        return 0;
    }

    public static ItemStack setRemainingBullets(ItemStack stack, int remainingBullets) {
        stack.getOrCreateTag().putInt("RemainingBullets", remainingBullets);
        return stack;
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab creativeModeTab, @NotNull NonNullList<ItemStack> nonNullList) {
        if (this.allowdedIn(creativeModeTab)) {
            nonNullList.add(new ItemStack(this));
            nonNullList.add(setRemainingBullets(new ItemStack(this), getCapacity()));
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(new TextComponent("Remaining Bullets: " + getRemainingBullets(itemStack)));
    }
}
