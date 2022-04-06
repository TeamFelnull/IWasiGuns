package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.gun.trans.GunTrans;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;

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
            if (getTrans(hand).updateId() != gtd.updateId())
                setTrans(hand, gtd);
            var lst = getTrans(hand);
            setTransOld(hand, lst);
            var nd = lst.next(mc.player, hand, mc.player.getItemInHand(hand));
            if (nd != null)
                setTrans(hand, new GunTransData(nd.transId(), nd.progress(), nd.step(), lst.updateId()));
        }
    }

    private static GunTransData getOldTrans(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? mainHandTransOld : offHandTransOld;
    }

    private static GunTransData getTrans(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? mainHandTrans : offHandTrans;
    }

    private static void setTrans(InteractionHand hand, GunTransData gunTransData) {
        if (hand == InteractionHand.MAIN_HAND) {
            mainHandTrans = gunTransData;
        } else {
            offHandTrans = gunTransData;
        }
    }

    private static void setTransOld(InteractionHand hand, GunTransData gunTransData) {
        if (hand == InteractionHand.MAIN_HAND) {
            mainHandTransOld = gunTransData;
        } else {
            offHandTransOld = gunTransData;
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

    public static DeltaGunTransData getGunTrans(InteractionHand hand, float delta) {
        if (mc.player != null) {
            var stack = mc.player.getItemInHand(hand);
            if (stack.getItem() instanceof GunItem gunItem) {
                var lst = getTrans(hand);
                var old = getOldTrans(hand);
                if (old.getGunTrans() != null) {
                    int tp = lst.progress();
                    int tpo = old.progress();
                    if (lst.step() != old.step() || lst.getGunTrans() == null)
                        tp = old.getGunTrans().getProgress(gunItem.getGun(), old.step()) - 1;
                    return new DeltaGunTransData(lst.getGunTrans(), Mth.lerp(delta, tpo, tp), old.step());
                }
            }
        }
        return new DeltaGunTransData(null, 0, 0);
    }

    public record DeltaGunTransData(GunTrans gunTrans, float progress, int step) {
    }
}
