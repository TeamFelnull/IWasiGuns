package dev.felnull.iwasi.server.physics;

import dev.felnull.iwasi.entity.IPhysicsEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ServerWorldPhysicsManager {
    private static final ServerWorldPhysicsManager INSTANCE = new ServerWorldPhysicsManager();
    private final Map<ServerLevel, ServerWorldPhysics> PHYSICS = new HashMap<>();

    public void entityTick(@NotNull Entity entity) {
        if (entity instanceof IPhysicsEntity) {
            if (!isEntityExist(entity))
                addEntity(entity);
        }
    }

    public static ServerWorldPhysicsManager getInstance() {
        return INSTANCE;
    }

    public void tick(ServerLevel level) {
        var physic = PHYSICS.get(level);
        if (physic == null) {
            physic = new ServerWorldPhysics(level);
            PHYSICS.put(level, physic);
        }
        physic.tick();
    }

    public boolean isExist(ServerLevel serverLevel, IPhysicsEntity entity) {
        var py = PHYSICS.get(serverLevel);
        if (py != null)
            return py.isExist(entity);
        return false;
    }

    public void addEntity(ServerLevel serverLevel, IPhysicsEntity entity) {
        var py = PHYSICS.get(serverLevel);
        if (py != null)
            py.addEntity(entity);
    }

    public void clear() {
        PHYSICS.clear();
    }

    public boolean isEntityExist(@NotNull Entity entity) {
        if (entity instanceof IPhysicsEntity physicsEntity)
            return isExist((ServerLevel) entity.getLevel(), physicsEntity);
        return false;
    }

    public void addEntity(@NotNull Entity entity) {
        if (entity instanceof IPhysicsEntity physicsEntity)
            addEntity((ServerLevel) entity.getLevel(), physicsEntity);
    }
   /* private static DiscreteDynamicsWorld dynamicsWorld;
    private static RigidBody rigid;
    private static RigidBody rigids;

    public static void init() {
        BroadphaseInterface broadphase = new DbvtBroadphase();
        DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -1, 0));

        var shape = new BoxShape(new Vector3f(1, 1, 1));
        var tr = new Transform(new Matrix4f(new Quat4f(0, 0, 1, 1), new Vector3f(0, 30, 0), 1.0f));
        DefaultMotionState fallMotionState = new DefaultMotionState(tr);
        float mass = 0.001f;
        Vector3f fallInertia = new Vector3f(0, 0, 0);
        shape.calculateLocalInertia(mass, fallInertia);
        RigidBodyConstructionInfo fallRigidBodyCI = new RigidBodyConstructionInfo(mass, fallMotionState, shape, fallInertia);
        rigid = new RigidBody(fallRigidBodyCI);
        dynamicsWorld.addRigidBody(rigid);

        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 1f, 0), 1);
        DefaultMotionState groundMotionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(0, -1, 0), 1.0f)));
        RigidBodyConstructionInfo groundRigidBodyCI = new RigidBodyConstructionInfo(0, groundMotionState, groundShape, new Vector3f(0, 0, 0));
        rigids = new RigidBody(groundRigidBodyCI);

        dynamicsWorld.addRigidBody(rigids);

        // Transform trans = new Transform(new Matrix4f(new Quat4f(0, 0, 1, 1), new Vector3f(0, 30, 0), 1.0f));
        // rigid.getMotionState().setWorldTransform(trans);
    }

    public static Vec3 getPosition() {
        Transform trans = new Transform();
        rigid.getMotionState().getWorldTransform(trans);
        return new Vec3(trans.origin.x, trans.origin.y, trans.origin.z);
    }

    public static Vec3 getRoted() {
        Transform trans = new Transform();
        rigid.getMotionState().getWorldTransform(trans);
        var rot = new Quat4f();
        trans.getRotation(rot);
        return new Vec3(rot.x, rot.y, rot.z);
    }

    public static void setPosition(Vec3 vec3) {
        var tr = new Transform(new Matrix4f(new Quat4f(0, 0, 1, 1), new Vector3f((float) vec3.x, (float) vec3.y, (float) vec3.z), 1.0f));
        DefaultMotionState fallMotionState = new DefaultMotionState(tr);
        rigid.setMotionState(fallMotionState);

        var tr2 = new Transform(new Matrix4f(new Quat4f(0, 0, 1, 1), new Vector3f((float) vec3.x, (float) vec3.y - 10f, (float) vec3.z), 1.0f));
        DefaultMotionState fallMotionState2 = new DefaultMotionState(tr2);
        rigids.setMotionState(fallMotionState2);
    }

    public static void tick() {
        dynamicsWorld.stepSimulation(1f / 60.f, 10);
    }*/
}
