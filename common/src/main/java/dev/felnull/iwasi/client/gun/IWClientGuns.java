package dev.felnull.iwasi.client.gun;

import dev.felnull.iwasi.client.motion.gun.*;
import dev.felnull.iwasi.client.renderer.gun.*;
import dev.felnull.iwasi.gun.Gun;
import dev.felnull.iwasi.gun.IWGuns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IWClientGuns {
    public static void init() {
        register(IWGuns.TEST_GUN, new TestGunRenderer(), new TestGunMotion());
        register(IWGuns.GLOCK_17, new Glock17GunRenderer(), new Glock17GunMotion());
        register(IWGuns.AR_57, new AR57GunRenderer(), new AR57GunMotion());
        register(IWGuns.PKP, new PKPGunRenderer(), new AR57GunMotion());
    }

    private static void register(@NotNull Gun gun, @Nullable GunRenderer<? extends GunMotion> renderer, @Nullable GunMotion motion) {
        GunRendererRegister.register(gun, renderer);
        GunMotionRegister.register(gun, motion);
    }
}
