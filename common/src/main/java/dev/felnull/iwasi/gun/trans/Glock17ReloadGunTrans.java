package dev.felnull.iwasi.gun.trans;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.gun.Gun;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.item.IWItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Glock17ReloadGunTrans extends AbstractReloadGunTrans {
    private static final List<Integer> progress = ImmutableList.of(10, 10, 10, 10);

    @Override
    public List<Integer> getProgressList() {
        return progress;
    }

    @Override
    public void stepEnd(@NotNull ServerPlayer player, @NotNull InteractionHand hand, @NotNull Gun gun, @NotNull ItemStack stack, int step) {
        super.stepEnd(player, hand, gun, stack, step);
        var mg = GunItem.getMagazine(stack);
        if (step == 0) {
            if (!mg.isEmpty())
                GunItem.setMagazine(player, stack, ItemStack.EMPTY);
        } else if (step == 2) {
            GunItem.setMagazine(player, stack, new ItemStack(IWItems.GLOCK_17_MAGAZINE.get()));
        }
    }
}
