package dev.felnull.iwasi.gun.trans.player;

import dev.felnull.iwasi.gun.trans.GunTransRegistry;

public class IWGunPlayerTrans {
    public static final Glock17ReloadGunTrans GLOCK_17_RELOAD = new Glock17ReloadGunTrans();
    public static final Glock17EmptyReloadGunTrans GLOCK_17_EMPTY_RELOAD = new Glock17EmptyReloadGunTrans();

    public static void init() {
        GunTransRegistry.register(GLOCK_17_RELOAD);
        GunTransRegistry.register(GLOCK_17_EMPTY_RELOAD);
    }
}
