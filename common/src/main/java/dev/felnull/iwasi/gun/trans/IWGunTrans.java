package dev.felnull.iwasi.gun.trans;

public class IWGunTrans {
    public static final GunTrans HOLD = new HoldGunTrans();

    public static void init() {
        GunTransRegistry.register(HOLD);
    }
}
