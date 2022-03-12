package dev.felnull.iwasi.client.physics;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import dev.felnull.iwasi.entity.IPhysicsEntity;
import dev.felnull.iwasi.physics.IWorldPhysicsManager;
import dev.felnull.iwasi.util.PhysicsUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ClientWorldPhysicsManager implements IWorldPhysicsManager {
    private static final ClientWorldPhysicsManager INSTANCE = new ClientWorldPhysicsManager();
    private static final Minecraft mc = Minecraft.getInstance();
    private DiscreteDynamicsWorld dynamicsWorld;
    private final Map<IPhysicsEntity, RigidBody> RIGID_ENTITYS = new HashMap<>();
    private ClientLevel lastLevel;
    private long lastProsesTime;

    public static ClientWorldPhysicsManager getInstance() {
        return INSTANCE;
    }

    public void tick() {
        long st = System.currentTimeMillis();
        if (mc.level != lastLevel) {
            lastLevel = mc.level;
            clear();
        }
        if (mc.level == null) return;
        if (dynamicsWorld == null)
            dynamicsWorld = createDynamicsWorld();

        List<IPhysicsEntity> rms = new ArrayList<>();
        RIGID_ENTITYS.keySet().forEach(n -> {
            if (!(n instanceof Entity entity) || mc.level.getEntity(entity.getId()) == null)
                rms.add(n);
        });
        rms.forEach(this::remove);

        RIGID_ENTITYS.keySet().forEach(n -> n.setOldRigidState(n.getCurrentRigidState()));
        dynamicsWorld.stepSimulation(1f / 20f, 10);
        RIGID_ENTITYS.forEach((n, m) -> n.setCurrentRigidState(PhysicsUtil.getRigidState(m)));

        lastProsesTime = System.currentTimeMillis() - st;
    }

    public void clear() {
        RIGID_ENTITYS.clear();
        dynamicsWorld = null;
    }

    private DiscreteDynamicsWorld createDynamicsWorld() {
        BroadphaseInterface broadphase = new DbvtBroadphase();
        DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
        var dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -PhysicsUtil.getDimensionGravity(mc.level.dimension().location()), 0));
        return dynamicsWorld;
    }

    public String getDebugText() {
        return "Client Physics: " + RIGID_ENTITYS.size() + " RigidBody " + lastProsesTime + " ms";
    }

    private void remove(IPhysicsEntity physicsEntity) {
        var rb = RIGID_ENTITYS.remove(physicsEntity);
        if (rb != null && dynamicsWorld != null)
            dynamicsWorld.removeRigidBody(rb);
    }

    @Override
    public boolean isEntityExist(@NotNull Entity entity) {
        if (entity instanceof IPhysicsEntity physicsEntity)
            return RIGID_ENTITYS.containsKey(physicsEntity);
        return false;
    }

    @Override
    public void addEntity(@NotNull Entity entity) {
        if (entity instanceof IPhysicsEntity physicsEntity) {
            var rb = physicsEntity.createRigidBody();
            RIGID_ENTITYS.put(physicsEntity, rb);
            if (rb != null && dynamicsWorld != null)
                dynamicsWorld.addRigidBody(rb);
        }
    }
}
