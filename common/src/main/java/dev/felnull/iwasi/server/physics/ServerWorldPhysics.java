package dev.felnull.iwasi.server.physics;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import dev.felnull.iwasi.entity.IPhysicsEntity;
import dev.felnull.iwasi.util.PhysicsUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerWorldPhysics {
    private final ServerLevel level;
    private final DiscreteDynamicsWorld dynamicsWorld;
    private final Map<IPhysicsEntity, RigidBody> RIGID_ENTITYS = new HashMap<>();

    protected ServerWorldPhysics(ServerLevel level) {
        this.level = level;
        BroadphaseInterface broadphase = new DbvtBroadphase();
        DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -PhysicsUtil.getDimensionGravity(level.dimension().location()), 0));
    }

    protected void tick() {
        List<IPhysicsEntity> rms = new ArrayList<>();
        RIGID_ENTITYS.keySet().forEach(n -> {
            if (!(n instanceof Entity entity) || level.getEntity(entity.getUUID()) == null)
                rms.add(n);
        });
        rms.forEach(this::remove);
        RIGID_ENTITYS.keySet().forEach(n -> n.setOldRigidState(n.getCurrentRigidState()));
        dynamicsWorld.stepSimulation(1f / 20f, 10);
        RIGID_ENTITYS.forEach((n, m) -> n.setCurrentRigidState(PhysicsUtil.getRigidState(m)));
    }

    protected void addEntity(IPhysicsEntity physicsEntity) {
        if (!(physicsEntity instanceof Entity)) return;
        var rb = physicsEntity.createRigidBody();
        RIGID_ENTITYS.put(physicsEntity, rb);
        if (rb != null)
            dynamicsWorld.addRigidBody(rb);
    }

    private void remove(IPhysicsEntity physicsEntity) {
        var rb = RIGID_ENTITYS.remove(physicsEntity);
        if (rb != null)
            dynamicsWorld.removeRigidBody(rb);
    }

    protected boolean isExist(IPhysicsEntity physicsEntity) {
        return RIGID_ENTITYS.containsKey(physicsEntity);
    }
}
