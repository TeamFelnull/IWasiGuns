package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionPose;
import net.minecraft.world.entity.HumanoidArm;

public abstract class GunMotion {
    private static final MotionPoint OP_KNIFE_BASE = new MotionPoint(0.15000014f, 0.59499985f, 0.04f, 39.999973f, 0.0f, 0.0f, -0.06999999f, 0.0f, 0.0f, false, false, false);

    abstract public MotionPoint getHandFixedMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType);

    abstract public MotionPoint getOppositeHandFixedMotionPoint(HumanoidArm arm, HoldType holdType);

    abstract public MotionPoint getGunFixedMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType);

    abstract public MotionPoint getOppositeItemFixedMotionPoint(HumanoidArm arm, boolean hold);

    public MotionPoint getOppositeKnifeFixedMotionPoint(HumanoidArm arm, boolean hold) {
        return OP_KNIFE_BASE;
    }

    public Motion getHandHoldMotion(HumanoidArm arm, boolean bothHands, HoldType preHoldType, HoldType holdType) {
        return Motion.of(getHandFixedMotionPoint(arm, bothHands, preHoldType), getHandFixedMotionPoint(arm, bothHands, holdType));
    }

    public Motion getOppositeHandHoldMotion(HumanoidArm arm, HoldType preHoldType, HoldType holdType) {
        return Motion.of(getOppositeHandFixedMotionPoint(arm, preHoldType), getOppositeHandFixedMotionPoint(arm, holdType));
    }

    public Motion getGunHoldMotion(HumanoidArm arm, boolean bothHands, HoldType preHoldType, HoldType holdType) {
        return Motion.of(getGunFixedMotionPoint(arm, bothHands, preHoldType), getGunFixedMotionPoint(arm, bothHands, holdType));
    }

    public Motion getOppositeItemHoldMotion(HumanoidArm arm) {
        return Motion.of(getOppositeItemFixedMotionPoint(arm, false), getOppositeItemFixedMotionPoint(arm, true));
    }

    abstract public MotionPose getHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base);

    abstract public MotionPose getOppositeHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base);

    abstract public MotionPoint getOppositeHandHideMotionPoint(HumanoidArm arm);

    abstract public MotionPose getRecoil(HoldType holdType, float par, boolean bothHands);

    abstract public MotionPose getArmGunMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType, float headX);

    abstract public MotionPoint getArmPose(HumanoidArm arm, boolean bothHands, HoldType holdType);

    abstract public MotionPoint getOppositeArmPose(HumanoidArm arm, HoldType holdType);

    public Motion getArmPoseHoldMotion(HumanoidArm arm, boolean bothHands, HoldType preHoldType, HoldType holdType) {
        return Motion.of(getArmPose(arm, bothHands, preHoldType), getArmPose(arm, bothHands, holdType));
    }

    public Motion getOppositeArmPoseHoldMotion(HumanoidArm arm, HoldType preHoldType, HoldType holdType) {
        return Motion.of(getOppositeArmPose(arm, preHoldType), getOppositeArmPose(arm, holdType));
    }

    public Motion getArmGunPoseHoldMotion(HumanoidArm arm, boolean bothHands, HoldType preHoldType, HoldType holdType, float headX) {
        return Motion.of(getArmGunMotionPoint(arm, bothHands, preHoldType, headX), getArmGunMotionPoint(arm, bothHands, holdType, headX));
    }

    abstract public MotionPose getArmReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base);

    abstract public MotionPose getOppositeArmReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPose base);
}
