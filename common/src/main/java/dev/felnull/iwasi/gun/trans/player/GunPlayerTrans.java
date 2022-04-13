package dev.felnull.iwasi.gun.trans.player;

import dev.felnull.iwasi.gun.trans.GunTrans;
import dev.felnull.iwasi.gun.trans.GunTransRegistry;

public abstract class GunPlayerTrans extends GunTrans {

    public boolean isUseBothHand() {
        return false;
    }

    public int getId() {
        return GunTransRegistry.getId(this);
    }
}
