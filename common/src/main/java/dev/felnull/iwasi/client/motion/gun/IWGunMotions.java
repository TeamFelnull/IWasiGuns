package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.iwasi.gun.IWGuns;

public class IWGunMotions {
    public static void init() {
        GunMotionRegister.register(IWGuns.GLOCK_17, new Glock17GunMotion());
        GunMotionRegister.register(IWGuns.TEST_GUN, new TestGunMotion());
    }
}
