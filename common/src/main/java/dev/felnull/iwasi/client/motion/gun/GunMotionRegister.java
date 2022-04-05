package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.iwasi.gun.Gun;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GunMotionRegister {
    private static final Map<Gun, GunMotion> GUN_MOTIONS = new HashMap<>();

    public static void register(@NotNull Gun gun, @Nullable GunMotion motion) {
        GUN_MOTIONS.put(gun, motion);
    }

    @Nullable
    public static GunMotion getMotion(Gun gun) {
        return GUN_MOTIONS.get(gun);
    }
}
