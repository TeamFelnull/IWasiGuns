package dev.felnull.iwasi.physics;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class CommonWorldPhysicsManager implements IWorldPhysicsManager {
    private static final CommonWorldPhysicsManager INSTANCE = new CommonWorldPhysicsManager();
    private IWorldPhysicsManager serverWorldPhysicsManager;
    private IWorldPhysicsManager clientWorldPhysicsManager;

    public static CommonWorldPhysicsManager getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isEntityExist(@NotNull Entity entity) {
        var wpm = getWorldPhysicsManager(entity.level.isClientSide());
        if (wpm != null)
            return wpm.isEntityExist(entity);
        return false;
    }

    @Override
    public void addEntity(@NotNull Entity entity) {
        var wpm = getWorldPhysicsManager(entity.level.isClientSide());
        if (wpm != null)
            wpm.addEntity(entity);
    }

    public void setClientWorldPhysicsManager(IWorldPhysicsManager clientWorldPhysicsManager) {
        this.clientWorldPhysicsManager = clientWorldPhysicsManager;
    }

    public void setServerWorldPhysicsManager(IWorldPhysicsManager serverWorldPhysicsManager) {
        this.serverWorldPhysicsManager = serverWorldPhysicsManager;
    }

    private IWorldPhysicsManager getWorldPhysicsManager(boolean clientSide) {
        if (clientSide) return clientWorldPhysicsManager;
        return serverWorldPhysicsManager;
    }
}
