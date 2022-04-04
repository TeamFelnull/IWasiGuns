package dev.felnull.iwasi.client.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.felnull.iwasi.client.IWKeyMapping;
import dev.felnull.otyacraftengine.client.util.OEClientUtil;
import net.minecraft.client.Minecraft;

public class ClientGunHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void init() {
        ClientRawInputEvent.KEY_PRESSED.register(ClientGunHandler::keyPressed);
    }

    private static EventResult keyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
        System.out.println(keyCode + ":" + scanCode + ":" + action + ":" + modifiers);
        if (mc.level != null && mc.screen == null && action == 1) {
            if (OEClientUtil.getKey(IWKeyMapping.RELOAD).getValue() == scanCode) {
                System.out.println("nna");
            }
        }
        return EventResult.interruptDefault();
    }
}
