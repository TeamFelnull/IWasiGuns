package dev.felnull.iwasi.server.physics;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import dev.felnull.iwasi.entity.IPhysicsEntity;
import dev.felnull.iwasi.physics.RigidState;
import dev.felnull.iwasi.util.JBulletUtil;
import dev.felnull.iwasi.util.PhysicsUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerWorldPhysics {
    private final ServerLevel level;
    private final DynamicsWorld dynamicsWorld;
    private final Map<IPhysicsEntity, RigidBody> RIGID_ENTITYS = new HashMap<>();

    protected ServerWorldPhysics(ServerLevel level) {
        this.level = level;
        dynamicsWorld = JBulletUtil.createDynamicsWorld(PhysicsUtil.getDimensionGravity(level.dimension().location()));
    }

    protected void tick() {
        List<IPhysicsEntity> rms = new ArrayList<>();
        RIGID_ENTITYS.keySet().forEach(n -> {
            if (!(n instanceof Entity entity) || level.getEntity(entity.getUUID()) == null)
                rms.add(n);
        });
        rms.forEach(this::remove);
        RIGID_ENTITYS.keySet().forEach(n -> n.setOldRigidState(n.getCurrentRigidState()));
        JBulletUtil.tickStepSimulation(dynamicsWorld);
        RIGID_ENTITYS.forEach((n, m) -> n.setCurrentRigidState(RigidState.of(m)));
    }

    protected void addEntity(IPhysicsEntity physicsEntity) {
        if (!(physicsEntity instanceof Entity)) return;
        var rb = physicsEntity.createRigidBody();
        RIGID_ENTITYS.put(physicsEntity, rb);
        if (rb != null)
            dynamicsWorld.addRigidBody(rb);
    }
//(0.0, 0.41960734, 0.0, 0.9077057)
    private void remove(IPhysicsEntity physicsEntity) {
        var rb = RIGID_ENTITYS.remove(physicsEntity);
        if (rb != null)
            dynamicsWorld.removeRigidBody(rb);
    }

    protected boolean isExist(IPhysicsEntity physicsEntity) {
        return RIGID_ENTITYS.containsKey(physicsEntity);
    }
}
