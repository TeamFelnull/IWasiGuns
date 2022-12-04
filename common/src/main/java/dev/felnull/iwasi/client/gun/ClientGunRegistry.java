package dev.felnull.iwasi.client.gun;

import com.google.common.collect.ImmutableMap;
import dev.felnull.iwasi.gun.Gun;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;

public class ClientGunRegistry {
    private static final Map<Gun, GunClientInfo<?>> CLIENT_GUN_INFO_REGISTRY = new HashMap<>();

    public static <T extends Gun> void register(@NotNull T gun, @NotNull GunClientInfo<T> clientGunInfo) {
        CLIENT_GUN_INFO_REGISTRY.put(gun, clientGunInfo);
    }

    public static <T extends Gun> GunClientInfo<T> get(T gun){
        return (GunClientInfo<T>) CLIENT_GUN_INFO_REGISTRY.get(gun);
    }

    @Unmodifiable
    @NotNull
    public static Map<Gun, GunClientInfo<?>> getAll() {
        return ImmutableMap.copyOf(CLIENT_GUN_INFO_REGISTRY);
    }
}
