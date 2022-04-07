package dev.felnull.iwasi.gun.trans;

public class IWGunTrans {
    public static final GunTrans HOLD = new HoldGunTrans(false);
    public static final GunTrans UN_HOLD = new HoldGunTrans(true);

    public static void init() {
        GunTransRegistry.register(HOLD);
        GunTransRegistry.register(UN_HOLD);
    }
}
