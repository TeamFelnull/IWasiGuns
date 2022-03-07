package dev.felnull.iwasi.server.handler;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.server.physics.ServerWorldPhysicsManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class ServerHandler {
    public static void init() {
        TickEvent.SERVER_LEVEL_POST.register(ServerHandler::onLevelTick);
        LifecycleEvent.SERVER_STARTING.register(ServerHandler::serverStarting);
        LifecycleEvent.SERVER_STOPPING.register(ServerHandler::serverStopping);
    }

    private static void onLevelTick(ServerLevel serverLevel) {
        ServerWorldPhysicsManager.getInstance().tick(serverLevel);
    }

    private static void serverStarting(MinecraftServer server) {
        ServerWorldPhysicsManager.getInstance().clear();
    }

    private static void serverStopping(MinecraftServer server) {
        ServerWorldPhysicsManager.getInstance().clear();
    }
}
