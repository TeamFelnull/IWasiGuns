package dev.felnull.iwasi.gun.trans.player;

public abstract class AbstractReloadGunTrans extends GunPlayerTrans {
    @Override
    public boolean isUseBothHand() {
        return true;
    }
}
