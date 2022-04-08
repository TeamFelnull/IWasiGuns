package dev.felnull.iwasi.gun.trans;

public abstract class AbstractReloadGunTrans extends GunTrans {
    @Override
    public boolean isUseBothHand() {
        return true;
    }
}
