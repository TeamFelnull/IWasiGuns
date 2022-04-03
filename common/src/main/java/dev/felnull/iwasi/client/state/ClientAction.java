package dev.felnull.iwasi.client.state;

import dev.felnull.iwasi.state.ActionData;
import net.minecraft.client.Minecraft;

public class ClientAction {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final boolean toggle = true;
    private static boolean holding;
    private static boolean lastHold;
    private static boolean pullTrigger;

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
        pullTrigger = mc.options.keyAttack.isDown();
    }

    public static boolean isHolding() {
        return holding;
    }

    public static boolean isPullTrigger() {
        return pullTrigger;
    }

    public static ActionData getData() {
        return new ActionData(isHolding(), isPullTrigger());
    }
}
