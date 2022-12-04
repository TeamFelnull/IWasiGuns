package dev.felnull.iwasi.client.gun;

import dev.felnull.iwasi.client.renderer.gun.Glock17GunRenderer;
import dev.felnull.iwasi.gun.IWGGuns;

public class IWGClientGuns {
    public static void init() {
        ClientGunRegistry.register(IWGGuns.GLOCK_17, new GunClientInfo<>(new Glock17GunRenderer()));
    }
}
