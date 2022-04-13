package dev.felnull.iwasi.gun.trans.item;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class Glock17SlideRecoilGunItemTrans extends GunItemTrans {
    private static final ImmutableList<Integer> progress = ImmutableList.of(5, 2, 3);

    @Override
    public List<Integer> getProgressList() {
        return progress;
    }
}
