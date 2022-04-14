package dev.felnull.iwasi.gun.trans.player;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.gun.Gun;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Glock17ReloadGunTrans extends AbstractReloadGunTrans {
    private static final List<Integer> progress = ImmutableList.of(6, 6, 5, 6);

    @Override
    public List<Integer> getProgressList() {
        return progress;
    }

    @Override
    public void stepEnd(@NotNull ServerPlayer player, @NotNull InteractionHand hand, @NotNull Gun gun, @NotNull ItemStack stack, int step) {
        super.stepEnd(player, hand, gun, stack, step);
        if (step == 0) {
            unReload(player.getLevel(), player, hand, stack);
        } else if (step == 2) {
            reload(player.getLevel(), player, hand, stack);
        }
    }
}
