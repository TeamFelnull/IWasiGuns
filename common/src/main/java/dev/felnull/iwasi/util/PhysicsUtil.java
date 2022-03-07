package dev.felnull.iwasi.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;

public class PhysicsUtil {
    public static float getDimensionGravity(ResourceLocation location) {
        if (DimensionType.NETHER_LOCATION.location().equals(location)) return 1.1f;
        if (DimensionType.END_LOCATION.location().equals(location)) return 0.9f;
        return 1f;
    }
}
