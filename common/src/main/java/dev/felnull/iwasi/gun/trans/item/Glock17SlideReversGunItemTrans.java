package dev.felnull.iwasi.gun.trans.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Glock17SlideReversGunItemTrans extends GunItemTrans {
    private static final ImmutableList<Integer> progress = ImmutableList.of(2);

    @Override
    public List<Integer> getProgressList(ItemStack stack) {
        return progress;
    }
}
