package dev.felnull.iwasi.entity;

import com.bulletphysics.dynamics.RigidBody;

public interface IPhysicsEntity {
    public RigidBody createRigidBody();

    public RigidState getCurrentRigidState();

    public RigidState getOldRigidState();

    public void setCurrentRigidState(RigidState state);

    public void setOldRigidState(RigidState state);

    public static record RigidState(float posX, float posY, float posZ, float rotX, float rotY, float rotZ) {
    }
}
