package dev.felnull.iwasi.gun;

import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;

public class GunRegistry {
    private static final Map<ResourceLocation, Gun> GUN_REGISTRY = new HashMap<>();

    public static void register(@NotNull ResourceLocation id, @NotNull Gun gun) {
        GUN_REGISTRY.put(id, gun);
    }

    @Unmodifiable
    @NotNull
    public static Map<ResourceLocation, Gun> getAll() {
        return ImmutableMap.copyOf(GUN_REGISTRY);
    }
}
