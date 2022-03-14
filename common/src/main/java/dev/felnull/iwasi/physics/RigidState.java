package dev.felnull.iwasi.physics;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import com.mojang.math.Quaternion;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Quat4f;

public record RigidState(RigidPosition position, RigidRotation rotation) {

    public RigidState(double x, double y, double z, double rx, double ry, double rz, double rw) {
        this(new RigidPosition(x, y, z), new RigidRotation(rx, ry, rz, rw));
    }

    public static RigidState of(RigidBody rigidBody) {
        var trans = rigidBody.getMotionState().getWorldTransform(new Transform());
        var rotation = trans.getRotation(new Quat4f());
        return new RigidState(new RigidPosition(trans.origin.x, trans.origin.y, trans.origin.z), new RigidRotation(rotation.x, rotation.y, rotation.z, rotation.w));
    }

    public RigidState copy() {
        return new RigidState(position, rotation);
    }

    public void write(@NotNull FriendlyByteBuf buf) {
        position.write(buf);
        rotation.write(buf);
    }

    public static RigidState read(@NotNull FriendlyByteBuf buf) {
        return new RigidState(RigidPosition.read(buf), RigidRotation.read(buf));
    }

    public static RigidState lerp(double f, RigidState s, RigidState e) {
        if (s == null || e == null) return null;
        return new RigidState(RigidPosition.lerp(f, s.position, e.position), RigidRotation.lerp(f, s.rotation, e.rotation));
    }
}
