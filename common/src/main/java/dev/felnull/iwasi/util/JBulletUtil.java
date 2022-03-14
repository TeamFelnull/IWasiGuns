package dev.felnull.iwasi.util;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import dev.felnull.fnjl.math.FNVec3f;
import dev.felnull.fnjl.math.FNVec4f;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class JBulletUtil {
    public static DynamicsWorld createDynamicsWorld(float gravity) {
        BroadphaseInterface broadphase = new DbvtBroadphase();
        DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
        DynamicsWorld dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -gravity, 0));
        return dynamicsWorld;
    }

    public static void tickStepSimulation(DynamicsWorld dynamicsWorld) {
        dynamicsWorld.stepSimulation(1f / 20f, 10);
    }

    public static RigidBody createRigidBody(float mass, CollisionShape shape, com.mojang.math.Vector3f position, com.mojang.math.Vector3f degreesRotation) {
        var fallInertia = new Vector3f(0, 0, 0);
        shape.calculateLocalInertia(mass, fallInertia);
        var tr = new Transform();
        tr.setIdentity();
        tr.origin.set(toJV3(position));
        var v4 = toFV3(degreesRotation).radians().toQuaternion();
        tr.basis.set(new AxisAngle4d(v4.getX(), v4.getY(), v4.getZ(), v4.getW()));
        return new RigidBody(new RigidBodyConstructionInfo(mass, new DefaultMotionState(tr), shape, fallInertia));
    }

    public static com.mojang.math.Vector3f getDegrees(RigidBody rigidBody) {
        var t = rigidBody.getWorldTransform(new Transform());
        var q = t.getRotation(new Quat4f());
        var v = new FNVec4f(q.x, q.y, q.z, q.w).toEulerAngle().degrees();
        return new com.mojang.math.Vector3f(v.getX(), v.getY(), v.getZ());
    }

    public static Vector3f toJV3(com.mojang.math.Vector3f vector3f) {
        return new Vector3f(vector3f.x(), vector3f.y(), vector3f.z());
    }

    public static FNVec3f toFV3(com.mojang.math.Vector3f vector3f) {
        return new FNVec3f(vector3f.x(), vector3f.y(), vector3f.z());
    }
}
