package dev.felnull.iwasi.entity;

import com.bulletphysics.dynamics.RigidBody;
import dev.felnull.iwasi.physics.RigidState;

public interface IPhysicsEntity {
    public RigidBody createRigidBody();

    public RigidState getCurrentRigidState();

    public RigidState getOldRigidState();

    public void setCurrentRigidState(RigidState state);

    public void setOldRigidState(RigidState state);

}
