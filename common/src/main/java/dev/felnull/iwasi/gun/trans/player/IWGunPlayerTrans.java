package dev.felnull.iwasi.gun.trans.player;

import dev.felnull.iwasi.gun.trans.GunTransRegistry;
import dev.felnull.iwasi.gun.trans.player.Glock17ReloadGunTrans;

public class IWGunPlayerTrans {
    public static final Glock17ReloadGunTrans GLOCK_17_RELOAD = new Glock17ReloadGunTrans();

    public static void init() {
        GunTransRegistry.register(GLOCK_17_RELOAD);
    }
}
