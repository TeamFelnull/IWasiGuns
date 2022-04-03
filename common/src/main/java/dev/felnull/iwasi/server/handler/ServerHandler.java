package dev.felnull.iwasi.server.handler;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.item.gun.GunItem;
import dev.felnull.iwasi.server.physics.ServerWorldPhysicsManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public class ServerHandler {
    public static void init() {
        TickEvent.SERVER_LEVEL_POST.register(ServerHandler::onLevelTick);
        LifecycleEvent.SERVER_STARTING.register(ServerHandler::serverStarting);
        LifecycleEvent.SERVER_STOPPING.register(ServerHandler::serverStopping);
        TickEvent.PLAYER_PRE.register(ServerHandler::onPlayerTick);
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

    private static void onPlayerTick(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        for (InteractionHand hand : InteractionHand.values()) {
            var item = serverPlayer.getItemInHand(hand);
            if (item.getItem() instanceof GunItem gunItem) {
                gunItem.handTick(item, serverPlayer, hand);
            }
        }
    }
}
