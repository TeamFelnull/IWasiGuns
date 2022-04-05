package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.gun.trans.GunTrans;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

public class ClientGunTrans {
    private static final Minecraft mc = Minecraft.getInstance();
    private static GunTransData lastMainHandTrans = new GunTransData();
    private static GunTransData lastOffHandTrans = new GunTransData();

    public static void tick() {
        if (mc.level == null || mc.player == null) {
            reset();
            return;
        }
        for (InteractionHand hand : InteractionHand.values()) {
            var gtd = IWPlayerData.getGunTransData(mc.player, hand);
            boolean handFlg = hand == InteractionHand.MAIN_HAND;
            var lgtd = handFlg ? lastMainHandTrans : lastOffHandTrans;
            if (lgtd.getGunTrans() != gtd.getGunTrans()) {
                if (handFlg)
                    lastMainHandTrans = gtd;
                else
                    lastOffHandTrans = gtd;
                lgtd = gtd;
            }
            if (lgtd.getGunTrans() != null) {

            }
        }
    }

    public static void reset(InteractionHand hand, GunTrans trans) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (lastMainHandTrans.getGunTrans() == trans)
                lastMainHandTrans = new GunTransData();
        } else {
            if (lastOffHandTrans.getGunTrans() == trans)
                lastOffHandTrans = new GunTransData();
        }
    }

    public static void reset() {
        lastMainHandTrans = new GunTransData();
        lastOffHandTrans = new GunTransData();
    }
}
