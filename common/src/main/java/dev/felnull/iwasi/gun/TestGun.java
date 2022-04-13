package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.iwasi.gun.type.IWGunTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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
        return Items.APPLE;
    }
}
