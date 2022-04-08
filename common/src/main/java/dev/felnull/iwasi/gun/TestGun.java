package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.gun.trans.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.IWGunTrans;
import dev.felnull.iwasi.gun.type.IWGunTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class TestGun extends Gun {
    public TestGun() {
        super(IWGunTypes.HAND_GUN, new GunProperties.Builder().create());
    }

    @Override
    public AbstractReloadGunTrans getReloadTrans() {
        return IWGunTrans.GLOCK_17_RELOAD;
    }

    @Override
    public Item getMagazine() {
        return Items.APPLE;
    }
}
