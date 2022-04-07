package dev.felnull.iwasi.client.renderer.gun;

import dev.felnull.iwasi.gun.Gun;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GunRendererRegister {
    private static final Map<Gun, GunRenderer> GUN_RENDERERS = new HashMap<>();

    public static void register(@NotNull Gun gun, @Nullable GunRenderer renderer) {
        GUN_RENDERERS.put(gun, renderer);
    }

    @Nullable
    public static GunRenderer getRenderer(Gun gun) {
        return GUN_RENDERERS.get(gun);
    }
}
