package dev.felnull.iwasi.gun.trans;

import dev.felnull.iwasi.gun.Gun;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class GunTrans {

    public boolean isUseBothHand() {
        return false;
    }

    abstract public List<Integer> getProgressList();

    public int getStep() {
        return getProgressList().size();
    }

    public int getProgress(@NotNull Gun gun, int step) {
        return getProgressList().get(step);
    }

    public void tick(@NotNull ServerPlayer player, @NotNull InteractionHand hand, @NotNull Gun gun, @NotNull ItemStack stack, int progress, int step) {

    }

    public void stepEnd(@NotNull ServerPlayer player, @NotNull InteractionHand hand, @NotNull Gun gun, @NotNull ItemStack stack, int step) {

    }
}
