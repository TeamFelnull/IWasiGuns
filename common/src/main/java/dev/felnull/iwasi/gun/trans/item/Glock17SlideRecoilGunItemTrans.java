package dev.felnull.iwasi.gun.trans.item;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Glock17SlideRecoilGunItemTrans extends GunItemTrans {
    private static final ImmutableList<Integer> progress = ImmutableList.of(2, 2);
    private static final ImmutableList<Integer> noBackProgress = ImmutableList.of(2);

    @Override
    public List<Integer> getProgressList(ItemStack stack) {
        if (stack.getItem() instanceof GunItem && GunItem.getChamberRemainingBullets(stack) <= 0)
            return noBackProgress;
        return progress;
    }
}
