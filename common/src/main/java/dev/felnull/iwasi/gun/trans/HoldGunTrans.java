package dev.felnull.iwasi.gun.trans;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.gun.Gun;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HoldGunTrans extends GunTrans {
    private static final List<Integer> progress = ImmutableList.of(10);
    private final boolean revers;

    public HoldGunTrans(boolean revers) {
        this.revers = revers;
    }

    public boolean isRevers() {
        return revers;
    }

    @Override
    public List<Integer> getProgressList() {
        return progress;
    }

    @Override
    public int getProgress(@NotNull Gun gun, int step) {
        return gun.getHoldSpeed();
    }

    @Override
    public void stepEnd(@NotNull ServerPlayer player, @NotNull InteractionHand hand, @NotNull Gun gun, @NotNull ItemStack stack, int step) {
        super.stepEnd(player, hand, gun, stack, step);
        if (getStep() - 1 == step) {
            var es = hand == InteractionHand.MAIN_HAND ? IWPlayerData.MAIN_HAND_HOLDING : IWPlayerData.OFF_HAND_HOLDING;
            player.getEntityData().set(es, !revers);
        }
    }
}
