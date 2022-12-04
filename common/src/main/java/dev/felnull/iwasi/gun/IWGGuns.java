package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.IWasiGuns;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class IWGGuns {
    private static final Map<String, Gun> REGISTER_HOLDER = new HashMap<>();
    public static final Glock17Gun GLOCK_17 = register("glock_17", new Glock17Gun(GunProperties.builder().maxContinuousShotCount(1).build()));

    private static <T extends Gun> T register(String name, T gun) {
        REGISTER_HOLDER.put(name, gun);
        return gun;
    }

    public static void init() {
        REGISTER_HOLDER.forEach((name, gun) -> GunRegistry.register(new ResourceLocation(IWasiGuns.MODID, name), gun));
    }
}
