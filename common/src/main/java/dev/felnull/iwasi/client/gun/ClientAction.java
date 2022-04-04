package dev.felnull.iwasi.client.gun;

import dev.felnull.iwasi.client.renderer.item.TestGunItemRenderer;
import dev.felnull.iwasi.item.gun.GunItem;
import dev.felnull.iwasi.state.ActionData;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class ClientAction {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final boolean toggle = true;
    private static boolean holding;
    private static boolean lastHold;
    private static boolean pullTrigger;

    private static ItemStack lastMainHandItem = ItemStack.EMPTY;
    private static ItemStack lastOffHandItem = ItemStack.EMPTY;

    public static void tick() {
        if (mc.level == null) {
            reset();
            return;
        }

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

        boolean resetFlg = false;

        for (InteractionHand hand : InteractionHand.values()) {
            var lastItem = hand == InteractionHand.MAIN_HAND ? lastMainHandItem : lastOffHandItem;
            var item = mc.player.getItemInHand(hand);
            if (lastItem != item) {
                UUID oid = null;
                if (lastItem.getItem() instanceof GunItem)
                    oid = GunItem.getTmpUUID(lastItem);
                UUID nid = null;
                if (item.getItem() instanceof GunItem)
                    nid = GunItem.getTmpUUID(item);
                if (!Objects.equals(oid, nid)) {
                    TestGunItemRenderer.reset(OEEntityUtil.getArmByHand(mc.player, hand));
                    resetFlg = true;
                }
                if (hand == InteractionHand.MAIN_HAND) {
                    lastMainHandItem = item;
                } else {
                    lastOffHandItem = item;
                }
            }
        }

        if (resetFlg)
            reset();
    }

    public static void reset() {
        holding = false;
        lastHold = false;
        pullTrigger = false;
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
