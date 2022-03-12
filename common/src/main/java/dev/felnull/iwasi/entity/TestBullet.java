package dev.felnull.iwasi.entity;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import dev.architectury.networking.NetworkManager;
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
    private static final EntityDataAccessor<Float> X_POS = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Y_POS = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Z_POS = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Z_ROT = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    private Vec3 nextPos;
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
        //  if (this.isControlledByLocalInstance()) {
        //      this.setPacketCoordinates(this.getX(), this.getY(), this.getZ());
        //  }
       /* if (!level.isClientSide()) {
            // if (nextPos == null)
            //      nextPos = position();

            setPos(getX(), getY() + 0.3, getZ());
            entityData.set(X_POS, (float) getX());
            entityData.set(Y_POS, (float) getY());
            entityData.set(Z_POS, (float) getZ());
            //setPos(nextPos);
            // nextPos = new Vec3(getX(), getY() + 0.3, getZ());
        } else {
            xo = getX();
            yo = getY();
            zo = getZ();
            setPos(entityData.get(X_POS), entityData.get(Y_POS), entityData.get(Z_POS));
        }*/
        if (!level.isClientSide()) {
            if (nextPos == null)
                nextPos = position();
            setPos(nextPos);

            nextPos = new Vec3(getX(), getY() + 0.1, getZ());

            entityData.set(X_POS, (float) nextPos.x);
            entityData.set(Y_POS, (float) nextPos.y);
            entityData.set(Z_POS, (float) nextPos.z);
        } else {
            setPos(entityData.get(X_POS), entityData.get(Y_POS), entityData.get(Z_POS));
        }
      /*  zRot0 = getZRot();

        CommonWorldPhysicsManager.getInstance().entityTick(this);
        var ors = getOldRigidState();
        if (ors != null) {
            //  xOld = ors.posX();
            //  yOld = ors.posY();
            //  zOld = ors.posZ();
            //   xRotO = ors.rotX();
            //   yRotO = ors.rotY();
            //   zRot0 = ors.rotZ();
        }
        var rs = getCurrentRigidState();
        if (rs != null) {
            setPos(rs.posX(), rs.posY(), rs.posZ());

            //setPos(getX(), getY() - 0.5f * (level.isClientSide() ? 1 : -1), getZ());

            setAllRot(rs.rotX(), rs.rotY(), rs.rotZ());
        }
        //  System.out.println(rs);*/
    }

    public void setAllRot(float pitch, float yaw, float roll) {
        setRot(pitch, yaw);
        setZRot(roll % 360f);
    }

    public Triple<Float, Float, Float> getAllRot() {
        return Triple.of((float) getX(), getYRot(), getZRot());
    }

    public float getZRot() {
        return this.entityData.get(Z_ROT);
    }

    public void setZRot(float f) {
        if (!Float.isFinite(f)) {
            Util.logAndPauseIfInIde("Invalid entity rotation: " + f + ", discarding.");
        } else {
            this.entityData.set(Z_ROT, f);
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(X_POS, (float) getX());
        this.entityData.define(Y_POS, (float) getY());
        this.entityData.define(Z_POS, (float) getZ());
        this.entityData.define(Z_ROT, 0f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(Z_ROT, tag.getFloat("ZRot"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("ZRot", this.entityData.get(Z_ROT));
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
