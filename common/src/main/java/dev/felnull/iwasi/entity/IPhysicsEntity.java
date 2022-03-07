package dev.felnull.iwasi.entity;

import com.bulletphysics.dynamics.RigidBody;

public interface IPhysicsEntity {
    public RigidBody createRigidBody();
}
