package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.gun.trans.GunTrans;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class ClientGunTrans {
    private static final Minecraft mc = Minecraft.getInstance();
    private static GunTransData mainHandTrans = new GunTransData();
    private static GunTransData offHandTrans = new GunTransData();
    private static GunTransData mainHandTransOld = new GunTransData();
    private static GunTransData offHandTransOld = new GunTransData();

    public static void tick() {
        if (mc.level == null || mc.player == null) {
            reset();
            return;
        }
        for (InteractionHand hand : InteractionHand.values()) {
            var gtd = IWPlayerData.getGunTransData(mc.player, hand);
            boolean handFlg = hand == InteractionHand.MAIN_HAND;
            var lgtd = handFlg ? mainHandTrans : offHandTrans;
            if (lgtd.getGunTrans() != gtd.getGunTrans()) {
                if (handFlg)
                    mainHandTrans = gtd;
                else
                    offHandTrans = gtd;
                lgtd = gtd;
            }
            if (handFlg) {
                mainHandTransOld = lgtd;
            } else {
                offHandTransOld = lgtd;
            }
            var nd = lgtd.tickNext(mc.player, hand, mc.player.getItemInHand(hand));
            if (nd != null) {
                if (handFlg)
                    mainHandTrans = nd;
                else
                    offHandTrans = nd;
            }
        }
    }

    public static void reset(InteractionHand hand, GunTrans trans) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (mainHandTrans.getGunTrans() == trans) {
                mainHandTrans = new GunTransData();
                mainHandTransOld = new GunTransData();
            }
        } else {
            if (offHandTrans.getGunTrans() == trans) {
                offHandTrans = new GunTransData();
                offHandTransOld = new GunTransData();
            }
        }
    }

    public static void reset() {
        mainHandTrans = new GunTransData();
        offHandTrans = new GunTransData();
        mainHandTransOld = new GunTransData();
        offHandTransOld = new GunTransData();
    }

    public static DeltaGunTransData getGunTrans(InteractionHand hand, ItemStack stack, float delta) {
        if (stack.getItem() instanceof GunItem gunItem) {
            if (hand == InteractionHand.MAIN_HAND) {
                if (mainHandTrans.getGunTrans() == mainHandTransOld.getGunTrans() && mainHandTransOld.getGunTrans() != null) {
                    int tp = mainHandTrans.progress();
                    int tpo = offHandTransOld.progress();
                    if (mainHandTransOld.step() != mainHandTrans.step())
                        tp = offHandTransOld.getGunTrans().getProgress(gunItem.getGun(), mainHandTransOld.step()) - 1;
                    return new DeltaGunTransData(mainHandTrans.getGunTrans(), Mth.lerp(delta, tpo, tp), mainHandTransOld.step());
                }
            } else {
                if (offHandTrans.getGunTrans() == offHandTransOld.getGunTrans() && offHandTransOld.getGunTrans() != null) {
                    int tp = offHandTrans.progress();
                    int tpo = offHandTransOld.progress();
                    if (offHandTransOld.step() != offHandTrans.step())
                        tp = offHandTransOld.getGunTrans().getProgress(gunItem.getGun(), offHandTransOld.step()) - 1;
                    return new DeltaGunTransData(offHandTrans.getGunTrans(), Mth.lerp(delta, tpo, tp), offHandTransOld.step());
                }
            }
        }
        return new DeltaGunTransData(null, 0, 0);
    }

    public record DeltaGunTransData(GunTrans gunTrans, float progress, int step) {
    }
}
