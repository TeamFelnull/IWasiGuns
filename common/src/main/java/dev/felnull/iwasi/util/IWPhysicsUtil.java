package dev.felnull.iwasi.util;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class IWPhysicsUtil {
    public static float getGravity(Level level) {
        if (DimensionType.NETHER_LOCATION.location().equals(level.dimension().location())) return 11f;
        if (DimensionType.END_LOCATION.location().equals(level.dimension().location())) return 9f;
        return 9.80665f;
    }
}
