package dev.felnull.iwasi.client.state;

import net.minecraft.client.Minecraft;

public class ClientHold {
    private static final Minecraft mc = Minecraft.getInstance();
    private static boolean holding;

    public static void tick() {
        holding = mc.options.keyUse.isDown();
    }

    public static boolean isHolding() {
        return holding;
    }
}
