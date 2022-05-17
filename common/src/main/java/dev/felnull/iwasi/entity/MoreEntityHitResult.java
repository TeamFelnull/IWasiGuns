package dev.felnull.iwasi.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class MoreEntityHitResult extends EntityHitResult {
    private final Vec3 hitLocation;

    public MoreEntityHitResult(Entity entity, Vec3 hitLocation) {
        super(entity);
        this.hitLocation = hitLocation;
    }

    public Vec3 getHitLocation() {
        return hitLocation;
    }
}
