package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.iwasi.gun.type.IWGunTypes;
import dev.felnull.iwasi.item.IWItems;
import net.minecraft.world.item.Item;

public class TestGun extends Gun {
    public TestGun() {
        super(IWGunTypes.HAND_GUN, new GunProperties.Builder().setSize16(1.4f, 5.6f, 8.175f).create());
    }

    @Override
    public AbstractReloadGunTrans getReloadTrans() {
        return IWGunPlayerTrans.GLOCK_17_RELOAD;
    }

    @Override
    public Item getMagazine() {
        return IWItems.GLOCK_17_MAGAZINE.get();
    }
}
