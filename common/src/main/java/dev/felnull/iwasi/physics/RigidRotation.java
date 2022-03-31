package dev.felnull.iwasi.physics;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import dev.felnull.fnjl.math.FNVec3f;
import dev.felnull.fnjl.math.FNVec4d;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

//x,y,z
public record RigidRotation(double x, double y, double z, double w) {

    public static RigidRotation of(float roll, float pitch, float yaw, boolean degrees) {
        var e = new FNVec3f(roll, pitch, yaw);
        if (degrees)
            e.degrees();
        var o = e.toQuaternion();
        return new RigidRotation(o.getX(), o.getY(), o.getZ(), o.getW());
    }

    public void write(@NotNull FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(w);
    }

    public static RigidRotation read(@NotNull FriendlyByteBuf buf) {
        return new RigidRotation(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public Vector3f toVec3(boolean degrees) {
        var q = new FNVec4d(x, y, z, w).toEulerAngle();
        if (degrees)
            q.degrees();
        return new Vector3f((float) q.getX(), (float) q.getY(), (float) q.getZ());
    }

    public Vector3f toPYRVec3(boolean degrees) {
        var v3 = toVec3(degrees);
        return new Vector3f(v3.x(), v3.y(), v3.z());
    }

    public static RigidRotation lerp(double f, RigidRotation s, RigidRotation e) {
        var a3 = lf4(s, e, f);
        return new RigidRotation(a3.x(), a3.y(), a3.z(), a3.w());
    }

    public Quaternion toQuaternion() {
        return new Quaternion((float) x, (float) y, (float) z, (float) w);
    }

    public static RigidRotation lf4(RigidRotation r1, RigidRotation r2, double f) {
        double r = Math.acos(qdot(r1, r2));
        double is = 1.0 / Math.sin(r);

        return add(mul(r1, (float) (Math.sin((1.0 - f) * r) * is)), mul(r2, (float) (Math.sin(f * r) * is)));
    }

    public static double qdot(RigidRotation q1, RigidRotation q2) {
        return q1.x * q2.x + q1.y * q2.y + q1.z * q2.z + q1.w * q2.w;
    }

    public static RigidRotation add(RigidRotation q1, RigidRotation q2) {
        return new RigidRotation(q1.x + q2.x, q1.y + q2.y, q1.z + q2.z, q1.w + q2.w);
    }

    public static RigidRotation mul(RigidRotation q, float f) {
        return new RigidRotation(f * q.x, f * q.y, f * q.z, f * q.w);
    }

}
