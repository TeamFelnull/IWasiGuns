package dev.felnull.iwasi.client.renderer.gun;

import dev.felnull.iwasi.gun.IWGuns;

public class IWGunRenderers {
    public static void init() {
        GunRendererRegister.register(IWGuns.TEST_GUN, new TestGunRenderer());
        GunRendererRegister.register(IWGuns.GLOCK_17, new Glock17GunRenderer());
    }
}
