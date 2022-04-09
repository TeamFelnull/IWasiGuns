package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.gun.trans.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.IWGunTrans;
import dev.felnull.iwasi.gun.type.IWGunTypes;
import dev.felnull.iwasi.item.IWItems;
import net.minecraft.world.item.Item;

//https://www.hyperdouraku.com/colum/cqbguam/glock17gen5.html
public class Glock17Gun extends Gun {
    public Glock17Gun() {
        super(IWGunTypes.HAND_GUN, new GunProperties.Builder().setShotCoolDown(3).setWeight(716).setSize16(1.525f, 6.55f, 12.225f).create());
    }

    @Override
    public AbstractReloadGunTrans getReloadTrans() {
        return IWGunTrans.GLOCK_17_RELOAD;
    }

    @Override
    public Item getMagazine() {
        return IWItems.GLOCK_17_MAGAZINE.get();
    }
}
