package dev.felnull.iwasi.util;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import dev.felnull.iwasi.entity.IPhysicsEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import org.apache.commons.lang3.tuple.Triple;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector4f;

public class PhysicsUtil {
    private static final Vector4f X = new Vector4f(1.0F, 0.0F, 0.0F, 0f);
    private static final Vector4f Y = new Vector4f(0.0F, 1.0F, 0.0F, 0f);
    private static final Vector4f Z = new Vector4f(0.0F, 0.0F, 1.0F, 0f);

    public static float getDimensionGravity(ResourceLocation location) {
        if (DimensionType.NETHER_LOCATION.location().equals(location)) return 11f;
        if (DimensionType.END_LOCATION.location().equals(location)) return 9f;
        return 9.80665f;
    }

    public static IPhysicsEntity.RigidState getRigidState(RigidBody body) {
        var trans = new Transform();
        body.getMotionState().getWorldTransform(trans);
        var rot = convertAngele(trans.getRotation(new Quat4f()));
        return new IPhysicsEntity.RigidState(trans.origin.x, trans.origin.y, trans.origin.z, rot.getLeft(), rot.getMiddle(), rot.getRight());
    }

    public static AxisAngle4f convert4f(float x, float y, float z) {
        return new AxisAngle4f(x, y, z, 1f);
    }

    public static Triple<Float, Float, Float> convertAngele(Quat4f axisAngle4f) {
        var v4 = new Vector4f(axisAngle4f.x, axisAngle4f.y, axisAngle4f.z, axisAngle4f.w);
        return Triple.of((float) Math.toDegrees(v4.angle(X)), (float) Math.toDegrees(v4.angle(Y)), (float) Math.toDegrees(v4.angle(Z)));
    }
}
