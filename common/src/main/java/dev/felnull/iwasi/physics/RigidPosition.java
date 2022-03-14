package dev.felnull.iwasi.physics;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record RigidPosition(double x, double y, double z) {

    public void write(@NotNull FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public static RigidPosition read(@NotNull FriendlyByteBuf buf) {
        return new RigidPosition(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static RigidPosition lerp(double f, RigidPosition s, RigidPosition e) {
        return new RigidPosition(Mth.lerp(f, s.x(), e.x()), Mth.lerp(f, s.y(), e.y()), Mth.lerp(f, s.z(), e.z()));
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }
}
