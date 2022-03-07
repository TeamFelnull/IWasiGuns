package dev.felnull.iwasi.physics;

import dev.felnull.iwasi.entity.IPhysicsEntity;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface IWorldPhysicsManager {
    default public void entityTick(@NotNull Entity entity) {
        if (entity instanceof IPhysicsEntity) {
            if (!isEntityExist(entity))
                addEntity(entity);
        }
    }

    public boolean isEntityExist(@NotNull Entity entity);

    public void addEntity(@NotNull Entity entity);
}
