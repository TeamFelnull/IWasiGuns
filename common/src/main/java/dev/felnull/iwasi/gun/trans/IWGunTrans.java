package dev.felnull.iwasi.gun.trans;

public class IWGunTrans {
    public static final HoldGunTrans HOLD = new HoldGunTrans(false);
    public static final HoldGunTrans UN_HOLD = new HoldGunTrans(true);
    public static final Glock17ReloadGunTrans GLOCK_17_RELOAD = new Glock17ReloadGunTrans();

    public static void init() {
        GunTransRegistry.register(HOLD);
        GunTransRegistry.register(UN_HOLD);
        GunTransRegistry.register(GLOCK_17_RELOAD);
    }
}
