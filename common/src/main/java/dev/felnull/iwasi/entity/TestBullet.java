package dev.felnull.iwasi.entity;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.physics.CommonWorldPhysicsManager;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Triple;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class TestBullet extends Projectile implements IPhysicsEntity {
    private static final EntityDataAccessor<Float> Z_ROT = SynchedEntityData.defineId(TestBullet.class, EntityDataSerializers.FLOAT);
    public float zRot0;

    public TestBullet(EntityType<? extends TestBullet> entityType, Level level) {
        super(entityType, level);
    }

    public TestBullet(Level level) {
        super(IWEntityType.TEST_BULLET.get(), level);
    }

    @Override
    public void tick() {
        super.tick();
        zRot0 = getZRot();

        CommonWorldPhysicsManager.getInstance().entityTick(this);

        if (!level.isClientSide()) {
            setZRot((getZRot() + 10f) % 360f);
        }
        //Vec3 vec3 = this.getDeltaMovement();
        // if (!level.isClientSide()) {
        //      setYRot((getYRot() + 10f) % 360f);
        //  }
      /*  this.checkInsideBlocks();
        this.updateRotation();

        Vec3 vec3 = this.getDeltaMovement();
        double d = this.getX() + vec3.x;
        double e = this.getY() + vec3.y;
        double f = this.getZ() + vec3.z;

        this.setDeltaMovement(vec3.scale(0.99F));

        Vec3 vec32 = this.getDeltaMovement();
        this.setDeltaMovement(vec32.x, vec32.y - 0.03F, vec32.z);

        this.setPos(d, e, f);*/

        //   IWPhysics.tick();
        //   var p = IWPhysics.getPosition();
        //   var r = IWPhysics.getRoted();
        //   setRot((float) r.x, (float) r.z);
        //   this.setPos(p.x, p.y, p.z);
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
        float mass = 0.001f;
        var shape = new BoxShape(new Vector3f(1, 1, 1));
        var fallInertia = new Vector3f(0, 0, 0);
        shape.calculateLocalInertia(mass, fallInertia);
        var tr = new Transform(new Matrix4f(new Quat4f(0, 0, 1, 1), new Vector3f((float) getX(), (float) getY(), (float) getZ()), 1.0f));
        var rbc = new RigidBodyConstructionInfo(mass, new DefaultMotionState(tr), shape, fallInertia);
        return new RigidBody(rbc);
    }
}
