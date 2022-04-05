package dev.felnull.iwasi.client.renderer.gun;

import dev.felnull.iwasi.gun.Gun;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GunRendererRegister {
    private static final Map<Gun, IGunRenderer> GUN_RENDERERS = new HashMap<>();

    public static void register(@NotNull Gun gun, @Nullable IGunRenderer renderer) {
        GUN_RENDERERS.put(gun, renderer);
    }

    @Nullable
    public static IGunRenderer getRenderer(Gun gun) {
        return GUN_RENDERERS.get(gun);
    }
}
