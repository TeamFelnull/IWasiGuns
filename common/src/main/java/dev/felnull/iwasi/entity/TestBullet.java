package dev.felnull.iwasi.entity;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.RigidBody;
import com.mojang.math.Vector3f;
import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.physics.RigidState;
import dev.felnull.iwasi.server.physics.ServerWorldPhysicsManager;
import dev.felnull.iwasi.util.JBulletUtil;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TestBullet extends Projectile implements IPhysicsEntity {
    private static final EntityDataAccessor<RigidState> RIGID_STATE = SynchedEntityData.defineId(TestBullet.class, RigidStateEntityDataSerializer.RIGID_STATE);
    private static final EntityDataAccessor<Boolean> NO_FIRST_FLG = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.BOOLEAN);
    private Vec3 nextPos;
    private Vector3f nextRot;
    private RigidState rigidState;
    private RigidState oldRigidState;

    public TestBullet(EntityType<? extends TestBullet> entityType, Level level) {
        super(entityType, level);
    }

    public TestBullet(Level level) {
        super(IWEntityType.TEST_BULLET.get(), level);
    }

    @Override
    public void tick() {
        super.tick();

        updatePhysics();
    }

    private void updatePhysics() {
        if (!level.isClientSide()) {
            if (nextPos != null)
                setPos(nextPos);
            if (nextRot != null)
                setAllRot(nextRot.x(), nextRot.y(), nextRot.z());

            ServerWorldPhysicsManager.getInstance().entityTick(this);
            var rs = getOldRigidState();
            if (rs != null) {
                nextPos = rs.position().toVec3();
                nextRot = rs.rotation().toPYRVec3(true);
            }
            if (rs != null) {
                entityData.set(RIGID_STATE, rs);
                entityData.set(NO_FIRST_FLG, true);
            }
        } else {
            oldRigidState = rigidState;
            if (entityData.get(NO_FIRST_FLG)) {
                rigidState = entityData.get(RIGID_STATE);
                setPos(rigidState.position().toVec3());
                setAllRot(rigidState.rotation().toPYRVec3(true));
            }
        }
    }

    public void setAllRot(Vector3f rotation) {
        setAllRot(rotation.x(), rotation.y(), rotation.z());
    }

    public void setAllRot(float pitch, float yaw, float roll) {
        setRot(pitch, yaw);
        setZRot(roll % 360f);
    }


    public void setZRot(float f) {
        if (!Float.isFinite(f)) {
            Util.logAndPauseIfInIde("Invalid entity rotation: " + f + ", discarding.");
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(RIGID_STATE, new RigidState(position().x(), position().y(), position().z(), 0, 0, 0, 0));
        this.entityData.define(NO_FIRST_FLG, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public RigidBody createRigidBody() {
        var shape = new BoxShape(new javax.vecmath.Vector3f(1, 1, 1));
        return JBulletUtil.createRigidBody(1f, shape, new Vector3f((float) getX(), (float) getY(), (float) getZ()), new Vector3f(getXRot(), getYRot(), 0));
    }

    @Override
    public RigidState getCurrentRigidState() {
        return rigidState;
    }

    @Override
    public RigidState getOldRigidState() {
        return oldRigidState;
    }

    @Override
    public void setCurrentRigidState(RigidState state) {
        this.rigidState = state;
    }

    @Override
    public void setOldRigidState(RigidState state) {
        this.oldRigidState = state;
    }
}
