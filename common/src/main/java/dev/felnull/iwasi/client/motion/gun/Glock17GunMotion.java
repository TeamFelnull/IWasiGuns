package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.otyacraftengine.client.motion.*;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class Glock17GunMotion extends GunMotion {
    private static final Minecraft mc = Minecraft.getInstance();

    private static final MotionPoint HAND_BASE = new MotionPoint(-0.062500015f, -0.47891992f, -0.09528247f, -83.54764f, 173.175f, -0.25074303f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_LEFT = new MotionPoint(-0.4945002f, -0.36319706f, 0.06540306f, -86.88011f, 180.66992f, -10.550002f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_RIGHT = new MotionPoint(-0.5020002f, -0.36319706f, 0.06540306f, -86.88011f, 180.66992f, -10.380004f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_LOWER = new MotionPoint(-0.1625f, -0.5415312f, 0.01471753f, -100.595085f, 173.925f, -18.250744f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_BOTH = new MotionPoint(-0.2720002f, -0.36319706f, 0.06540306f, -86.88011f, 177.66992f, -3.380004f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_LOWER_BOTH = new MotionPoint(-0.062500015f, -0.3289199f, -0.14528248f, -59.547638f, 173.175f, 2.749257f, -0.055f, 0.6149996f, 0.0f, false, false, false);

    private static final MotionPoint OP_HAND_BASE = new MotionPoint(-0.18000144f, -0.54029214f, -0.044355575f, -87.54647f, -181.9746f, 40.24339f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);
    private static final MotionPoint OP_HAND_HOLD = new MotionPoint(-0.6050014f, -0.36029217f, 0.13564447f, -85.74651f, -179.27443f, 22.643402f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);
    private static final MotionPoint OP_HAND_HOLD_LOWER = new MotionPoint(-0.15000156f, -0.5652922f, -0.064355575f, -96.44647f, -182.9746f, 36.24339f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);

    private static final MotionPoint GUN_BASE = new MotionPoint(0.47500157f, 0.6508932f, 0.14970265f, -85.512436f, -10.5f, 0.0f, -0.5f, -0.14999999f, -0.35000002f, false, false, false);

    private static final MotionPoint OP_ITEM_BASE = new MotionPoint(0.45750028f, 0.87901837f, 0.060312465f, -96.51788f, 47.450016f, 35.36559f, -0.48999998f, -0.21000001f, -0.315f, false, false, false);

    private static final ResourceLocation HAND_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/hand_reload");

    private static final ResourceLocation OP_HAND_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/opposite_hand_reload");

    private static final MotionPoint OP_HAND_HIDE = new MotionPoint(-0.9300009f, -0.966556f, -0.044355575f, -87.54647f, -181.9746f, 40.24339f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);

    private static final MotionPoint RECOIL_BASE = new MotionPoint(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.17700012f, 0.3194994f, 0.06700005f, false, false, false);
    private static final MotionPoint RECOIL_BASE_MAX = new MotionPoint(0.0f, 0.05f, 0.0f, 21.06244f, 1.5f, 0.0f, -0.17700012f, 0.3194994f, 0.06700005f, false, false, false);
    private static final Motion RECOIL_BASE_MOTION = Motion.of(RECOIL_BASE, RECOIL_BASE_MAX);

    private static final MotionPoint RECOIL_HOLD = new MotionPoint(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.56099993f, 0.4519122f, 0.21499982f, false, false, false);
    private static final MotionPoint RECOIL_HOLD_MAX = new MotionPoint(0.0f, 0.030000001f, 0.0f, 30.000013f, 0.0f, 0.0f, -0.56099993f, 0.4519122f, 0.21499982f, false, false, false);
    private static final Motion RECOIL_HOLD_MOTION = Motion.of(RECOIL_HOLD, RECOIL_HOLD_MAX);

    @Override
    public MotionPose getHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base) {
        var gtd = infoGunTrans.gunTransData();
        var mt = MotionManager.getInstance().getMotion(HAND_RELOAD_MOTION);
        if (gtd.step() == 0)
            return mt.getPose(infoGunTrans.progressPar(), MotionSwapper.swapStartAndEnd(base, base), 0, 1);
        if (gtd.step() == 1 || gtd.step() == 2) {
            float par;
            if (gtd.step() == 1) par = infoGunTrans.progressPar() / 2f;
            else par = 0.5f + infoGunTrans.progressPar() / 2f;
            return mt.getPose(par, MotionSwapper.swapStartAndEnd(base, base), 1, 2);
        }
        return mt.getPose(infoGunTrans.progressPar(), MotionSwapper.swapStartAndEnd(base, base), 2, 3);
    }

    @Override
    public MotionPose getOppositeHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base) {
        var gtd = infoGunTrans.gunTransData();
        return MotionManager.getInstance().getMotion(OP_HAND_RELOAD_MOTION).getPose(infoGunTrans.progressPar(), MotionSwapper.swapStartAndEnd(base, base), gtd.step(), gtd.step() + 1);
    }

    @Override
    public MotionPoint getHandFixedMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType) {
        if (holdType == HoldType.HOLD) {
            if (!bothHands) return HAND_HOLD_BOTH;
            return arm == HumanoidArm.LEFT ? HAND_HOLD_LEFT : HAND_HOLD_RIGHT;
        }


        if (holdType == HoldType.LOWER) {
            if (!bothHands && mc.player.getItemInHand(OEEntityUtil.getHandByArm(mc.player, arm.getOpposite())).getItem() instanceof GunItem)
                return HAND_LOWER_BOTH;
            return HAND_LOWER;
        }
        return HAND_BASE;
    }

    @Override
    public MotionPoint getOppositeHandFixedMotionPoint(HumanoidArm arm, HoldType holdType) {
        if (holdType == HoldType.HOLD) return OP_HAND_HOLD;
        if (holdType == HoldType.LOWER) return OP_HAND_HOLD_LOWER;
        return OP_HAND_BASE;
    }

    @Override
    public MotionPoint getGunFixedMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType) {
        return GUN_BASE;
    }

    @Override
    public MotionPoint getOppositeItemFixedMotionPoint(HumanoidArm arm, boolean hold) {
        return OP_ITEM_BASE;
    }

    @Override
    public MotionPoint getOppositeHandHideMotionPoint(HumanoidArm arm) {
        return OP_HAND_HIDE;
    }

    @Override
    public MotionPose getRecoilBase(float par) {
        return RECOIL_BASE_MOTION.getPose(par);
    }

    @Override
    public MotionPose getRecoilHold(float par) {
        return RECOIL_HOLD_MOTION.getPose(par);
    }

}
