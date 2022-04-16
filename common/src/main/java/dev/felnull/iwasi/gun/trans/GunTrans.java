package dev.felnull.iwasi.gun.trans;

import dev.felnull.iwasi.gun.Gun;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class GunTrans {

    abstract public List<Integer> getProgressList(ItemStack stack);

    public int getStep(ItemStack stack) {
        return getProgressList(stack).size();
    }

    public int getProgress(ItemStack stack, int step) {
        var lst = getProgressList(stack);
        if (step < 0 || lst.size() <= step)
            return 0;
        return lst.get(step);
    }

    public boolean tick(@NotNull ServerPlayer player, @NotNull InteractionHand hand, @NotNull Gun gun, @NotNull ItemStack stack, int progress, int step) {
        return true;
    }

    public boolean stepEnd(@NotNull ServerPlayer player, @NotNull InteractionHand hand, @NotNull Gun gun, @NotNull ItemStack stack, int step) {
        return true;
    }
}
