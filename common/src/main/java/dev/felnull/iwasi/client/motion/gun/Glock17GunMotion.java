package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.otyacraftengine.client.motion.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class Glock17GunMotion extends GunMotion {
    private static final MotionPoint HAND_BASE = new MotionPoint(-0.062500015f, -0.47891992f, -0.09528247f, -83.54764f, 173.175f, -0.25074303f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_LEFT = new MotionPoint(-0.4945002f, -0.36319706f, 0.06540306f, -86.88011f, 180.66992f, -10.550002f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_RIGHT = new MotionPoint(-0.5020002f, -0.36319706f, 0.06540306f, -86.88011f, 180.66992f, -10.380004f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_LOWER = new MotionPoint(-0.1625f, -0.5415312f, 0.01471753f, -100.595085f, 173.925f, -18.250744f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_NO_BOTH = new MotionPoint(-0.2720002f, -0.36319706f, 0.06540306f, -86.88011f, 177.66992f, -3.380004f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_UPPER = new MotionPoint(-0.062500015f, -0.3289199f, -0.14528248f, -59.547638f, 173.175f, 2.749257f, -0.055f, 0.6149996f, 0.0f, false, false, false);

    private static final MotionPoint OP_HAND_BASE = new MotionPoint(-0.18000144f, -0.54029214f, -0.044355575f, -87.54647f, -181.9746f, 40.24339f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);
    private static final MotionPoint OP_HAND_HOLD = new MotionPoint(-0.6050014f, -0.36029217f, 0.13564447f, -85.74651f, -179.27443f, 22.643402f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);
    private static final MotionPoint OP_HAND_HOLD_LOWER = new MotionPoint(-0.15000156f, -0.5652922f, -0.064355575f, -96.44647f, -182.9746f, 36.24339f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);

    private static final MotionPoint GUN_BASE = new MotionPoint(0.47500157f, 0.6508932f, 0.14970265f, -85.512436f, -10.5f, 0.0f, -0.5f, -0.14999999f, -0.35000002f, false, false, false);

    private static final MotionPoint OP_ITEM_BASE = new MotionPoint(0.0025002188f, 0.43288428f, -0.25255203f, -85.459404f, 45.200092f, 35.36559f, -5.401671E-8f, 0.13999972f, 0.1349999f, false, false, false);


    private static final ResourceLocation HAND_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/hand_reload");

    private static final ResourceLocation OP_HAND_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/opposite_hand_reload");
    private static final ResourceLocation OP_HAND_EMPTY_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/opposite_hand_empty_reload");

    private static final MotionPoint OP_HAND_HIDE = new MotionPoint(-0.9300009f, -0.966556f, -0.044355575f, -87.54647f, -181.9746f, 40.24339f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);

    private static final MotionPoint RECOIL_BASE = new MotionPoint(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.17700012f, 0.3194994f, 0.06700005f, false, false, false);
    private static final MotionPoint RECOIL_BASE_MAX = new MotionPoint(0.0f, 0.05f, 0.0f, 21.06244f, 1.5f, 0.0f, -0.17700012f, 0.3194994f, 0.06700005f, false, false, false);
    private static final Motion RECOIL_BASE_MOTION = Motion.of(RECOIL_BASE, RECOIL_BASE_MAX);

    private static final MotionPoint RECOIL_HOLD = new MotionPoint(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.56099993f, 0.4519122f, 0.21499982f, false, false, false);
    private static final MotionPoint RECOIL_HOLD_MAX = new MotionPoint(0.0f, 0.030000001f, 0.0f, 30.000013f, 0.0f, 0.0f, -0.56099993f, 0.4519122f, 0.21499982f, false, false, false);
    private static final Motion RECOIL_HOLD_MOTION = Motion.of(RECOIL_HOLD, RECOIL_HOLD_MAX);

    private static final MotionPoint RECOIL_HOLD_NO_BOTH = new MotionPoint(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.3594998f, 0.4519122f, 0.21499982f, false, false, false);
    private static final MotionPoint RECOIL_HOLD_NO_BOTH_MAX = new MotionPoint(0.0f, 0.030000001f, 0.0f, 32.40001f, 8.300001f, 0.0f, -0.3594998f, 0.4519122f, 0.21499982f, false, false, false);
    private static final Motion RECOIL_HOLD_NO_BOTH_MOTION = Motion.of(RECOIL_HOLD_NO_BOTH, RECOIL_HOLD_NO_BOTH_MAX);

    private static final MotionPoint ARM_GUN_BASE = new MotionPoint(0.3305003f, 0.59050006f, 0.06900002f, -90.0f, -4.59f, 0.0f, -0.34850004f, -0.10750002f, -0.25f, false, false, false);
    private static final MotionPoint ARM_GUN_HOLD = new MotionPoint(0.3305003f, 0.59050006f, 0.06900002f, -90.0f, -23.069996f, 0.0f, -0.34850004f, -0.10750002f, -0.25f, false, false, false);
    private static final MotionPoint ARM_GUN_HOLD_NO_BOTH = new MotionPoint(0.3305003f, 0.59050006f, 0.06900002f, -90.0f, -8.069996f, 0.0f, -0.34850004f, -0.10750002f, -0.25f, false, false, false);
    private static final MotionPoint ARM_GUN_HOLD_SIDE = new MotionPoint(0.3305003f, 0.59050006f, 0.06900002f, -90.0f, -0.06999588f, 0.0f, -0.34850004f, -0.10750002f, -0.25f, false, false, false);
    private static final Motion ARM_GUN_HOLD_MOTION = Motion.of(ARM_GUN_HOLD, ARM_GUN_HOLD_SIDE);
    private static final Motion ARM_GUN_HOLD_MOTION_NO_BOTH = Motion.of(ARM_GUN_HOLD_NO_BOTH, ARM_GUN_HOLD_SIDE);

    private static final MotionPoint ARM_POSE_BASE = new MotionPoint(0.0f, 0.0f, 0.0f, 86.0f, 15.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint ARM_POSE_HOLD = new MotionPoint(0.0f, 0.0f, 0.0f, 98.5f, 28.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint ARM_POSE_HOLD_BI_BOTH = new MotionPoint(0.0f, 0.0f, 0.0f, 98.5f, 15.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint ARM_POSE_LOWER = new MotionPoint(0.0f, 0.0f, 0.0f, 49.892914f, 14.25f, 7.522299f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint ARM_POSE_UPPER = new MotionPoint(0.0f, 0.0f, 0.0f, 102.29813f, 7.25f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);

    private static final MotionPoint OP_ARM_POSE_BASE = new MotionPoint(0.0f, 0.0f, 0.0f, 90.0f, -42.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint OP_ARM_POSE_HOLD = new MotionPoint(0.0f, 0.0f, 0.0f, 98.0f, -31.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint OP_ARM_POSE_LOWER = new MotionPoint(-0.0125f, 0.0f, 0.0f, 56.149876f, -57.5f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);


    private static final ResourceLocation ARM_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/arm_reload");
    private static final ResourceLocation OP_ARM_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/opposite_arm_reload");
    private static final ResourceLocation OP_ARM_EMPTY_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/opposite_arm_empty_reload");

    @Override
    public MotionPose getHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base) {
        return getReloadMotion(arm, infoGunTrans, base, false);
    }

    private MotionPose getReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base, boolean armed) {
        var gtd = infoGunTrans.gunTransData();
        var mt = MotionManager.getInstance().getMotion(armed ? ARM_RELOAD_MOTION : HAND_RELOAD_MOTION);
        if (gtd.gunTrans() == IWGunPlayerTrans.GLOCK_17_EMPTY_RELOAD)
            return mt.getPose(infoGunTrans.progressPar(), MotionSwapper.swapStartAndEnd(base, base), gtd.step(), gtd.step() + 1);

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
        return getOppositeReloadMotion(arm, infoGunTrans, base, false);
    }

    private MotionPose getOppositeReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base, boolean armed) {
        var gtd = infoGunTrans.gunTransData();
        var mt = MotionManager.getInstance().getMotion(gtd.gunTrans() == IWGunPlayerTrans.GLOCK_17_EMPTY_RELOAD ? (armed ? OP_ARM_EMPTY_RELOAD_MOTION : OP_HAND_EMPTY_RELOAD_MOTION) : (armed ? OP_ARM_RELOAD_MOTION : OP_HAND_RELOAD_MOTION));

        return mt.getPose(infoGunTrans.progressPar(), MotionSwapper.swapStartAndEnd(base, base), gtd.step(), gtd.step() + 1);
    }

    @Override
    public MotionPoint getHandFixedMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType) {
        if (holdType == HoldType.HOLD) {
            if (!bothHands) return HAND_HOLD_NO_BOTH;
            return arm == HumanoidArm.LEFT ? HAND_HOLD_LEFT : HAND_HOLD_RIGHT;
        }
        if (holdType == HoldType.LOWER)
            return HAND_LOWER;
        if (holdType == HoldType.UPPER)
            return HAND_UPPER;
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
    public MotionPose getRecoil(HoldType holdType, float par, boolean bothHands) {
        if (holdType == HoldType.HOLD)
            return bothHands ? RECOIL_HOLD_MOTION.getPose(par) : RECOIL_HOLD_NO_BOTH_MOTION.getPose(par);

        return RECOIL_BASE_MOTION.getPose(par);
    }

    @Override
    public MotionPose getArmGunMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType, float headX) {
        if (holdType == HoldType.HOLD)
            return (bothHands ? ARM_GUN_HOLD_MOTION : ARM_GUN_HOLD_MOTION_NO_BOTH).getPose(Math.abs(headX) / 90f);
        return ARM_GUN_BASE.getPose();
    }

    @Override
    public MotionPoint getArmPose(HumanoidArm arm, boolean bothHands, HoldType holdType) {
        if (holdType == HoldType.HOLD)
            return bothHands ? ARM_POSE_HOLD : ARM_POSE_HOLD_BI_BOTH;
        if (holdType == HoldType.LOWER)
            return ARM_POSE_LOWER;
        if (holdType == HoldType.UPPER)
            return ARM_POSE_UPPER;
        return ARM_POSE_BASE;
    }

    @Override
    public MotionPoint getOppositeArmPose(HumanoidArm arm, HoldType holdType) {
        if (holdType == HoldType.HOLD)
            return OP_ARM_POSE_HOLD;
        if (holdType == HoldType.LOWER)
            return OP_ARM_POSE_LOWER;
        return OP_ARM_POSE_BASE;
    }

    @Override
    public MotionPose getArmReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base) {
        return getReloadMotion(arm, infoGunTrans, base, true);
    }

    @Override
    public MotionPose getOppositeArmReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base) {
        return getOppositeReloadMotion(arm, infoGunTrans, base, true);
    }

}
