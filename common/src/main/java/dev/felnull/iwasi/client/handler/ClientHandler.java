package dev.felnull.iwasi.client.handler;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.felnull.iwasi.client.physics.ClientWorldPhysicsManager;
import net.minecraft.client.Minecraft;

import java.util.List;

public class ClientHandler {
    public static void init() {
        ClientTickEvent.CLIENT_PRE.register(ClientHandler::onTick);
        ClientGuiEvent.DEBUG_TEXT_LEFT.register(ClientHandler::onLeftDebugText);
    }

    public static void onTick(Minecraft instance) {
        ClientWorldPhysicsManager.getInstance().tick();
    }

    public static void onLeftDebugText(List<String> strings) {
        strings.add(ClientWorldPhysicsManager.getInstance().getDebugText());
    }
}
