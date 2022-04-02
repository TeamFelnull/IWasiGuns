package dev.felnull.iwasi.client.state;

import net.minecraft.client.Minecraft;

public class ClientHold {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final boolean toggle = true;
    private static boolean holding;
    private static boolean lastHold;

    public static void tick() {
        if (toggle) {
            boolean n = mc.options.keyUse.isDown();
            if (lastHold != n) {
                if (n)
                    holding = !holding;
                lastHold = n;
            }
        } else {
            holding = mc.options.keyUse.isDown();
        }
    }

    public static boolean isHolding() {
        return holding;
    }
}
