package dev.felnull.iwasi.entity;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.server.physics.ServerWorldPhysicsManager;
import dev.felnull.iwasi.util.PhysicsUtil;
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
import org.apache.commons.lang3.tuple.Triple;

import javax.vecmath.Vector3f;

public class TestBullet extends Projectile implements IPhysicsEntity {
    private static final EntityDataAccessor<Boolean> NO_FIRST_FLG = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> X_POS = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Y_POS = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Z_POS = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> X_ROT = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Y_ROT = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Z_ROT = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private Vec3 nextPos;
    private Vec3 nextRot;
    public float zRot;
    public float zRot0;
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
        this.zRot0 = getZRot();

        updatePhysics();
    }

    private void updatePhysics() {
        if (!level.isClientSide()) {
            if (nextPos != null)
                setPos(nextPos);
            if (nextRot != null)
                setAllRot((float) nextRot.x, (float) nextRot.y, (float) nextRot.z);

            ServerWorldPhysicsManager.getInstance().entityTick(this);
            var rs = getCurrentRigidState();
            if (rs != null) {
                nextPos = new Vec3(rs.posX(), rs.posY(), rs.posZ());
                nextRot = new Vec3(rs.rotX(), rs.rotY(), rs.rotZ());
            }
            if (rs != null) {
                entityData.set(X_POS, rs.posX());
                entityData.set(Y_POS, rs.posY());
                entityData.set(Z_POS, rs.posZ());
                entityData.set(X_ROT, rs.rotX());
                entityData.set(Y_ROT, rs.rotY());
                entityData.set(Z_ROT, rs.rotZ());
            }
            this.entityData.set(NO_FIRST_FLG, true);
        } else {
            if (entityData.get(NO_FIRST_FLG)) {
                setPos(entityData.get(X_POS), entityData.get(Y_POS), entityData.get(Z_POS));
                setAllRot(entityData.get(X_ROT), entityData.get(Y_ROT), entityData.get(Z_ROT));
            }
        }
    }

    public void setAllRot(float pitch, float yaw, float roll) {
        setRot(pitch, yaw);
        setZRot(roll % 360f);
    }

    public Triple<Float, Float, Float> getAllRot() {
        return Triple.of((float) getX(), getYRot(), getZRot());
    }

    public float getZRot() {
        return zRot;
    }

    public void setZRot(float f) {
        if (!Float.isFinite(f)) {
            Util.logAndPauseIfInIde("Invalid entity rotation: " + f + ", discarding.");
        } else {
            zRot = f;
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(NO_FIRST_FLG, false);
        this.entityData.define(X_POS, (float) getX());
        this.entityData.define(Y_POS, (float) getY());
        this.entityData.define(Z_POS, (float) getZ());
        this.entityData.define(X_ROT, getXRot());
        this.entityData.define(Y_ROT, getYRot());
        this.entityData.define(Z_ROT, getZRot());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.zRot = tag.getFloat("ZRot");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("ZRot", this.zRot);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public RigidBody createRigidBody() {
        float mass = 1f;
        var shape = new BoxShape(new Vector3f(1, 1, 1));
        var fallInertia = new Vector3f(0, 0, 0);
        shape.calculateLocalInertia(mass, fallInertia);
        //var tr = new Transform(new Matrix4f(new Quat4f(0, 0, 1, 1), new Vector3f((float) getX(), (float) getY(), (float) getZ()), 1.0f));
        var tr = new Transform();
        tr.setIdentity();
        tr.origin.set((float) getX(), (float) getY(), (float) getZ());
        tr.basis.set(PhysicsUtil.convert4f(getXRot(), getYRot(), getZRot()));
        var rbc = new RigidBodyConstructionInfo(mass, new DefaultMotionState(tr), shape, fallInertia);
        return new RigidBody(rbc);
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
