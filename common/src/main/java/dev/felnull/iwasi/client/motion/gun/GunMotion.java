package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionPose;
import net.minecraft.world.entity.HumanoidArm;

public abstract class GunMotion {

    abstract public MotionPoint getHandFixedMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType);

    abstract public MotionPoint getOppositeHandFixedMotionPoint(HumanoidArm arm, HoldType holdType);

    abstract public MotionPoint getGunFixedMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType);

    abstract public MotionPoint getOppositeItemFixedMotionPoint(HumanoidArm arm, boolean hold);

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

    abstract public MotionPoint getArmGunFixedMotionPoint(HumanoidArm arm, boolean bothHands, HoldType holdType);

    abstract public MotionPoint getArmPose(HumanoidArm arm, boolean bothHands, HoldType holdType);

    abstract public MotionPoint getOppositeArmPose(HumanoidArm arm, HoldType holdType);

    public Motion getArmPoseHoldMotion(HumanoidArm arm, boolean bothHands, HoldType preHoldType, HoldType holdType) {
        return Motion.of(getArmPose(arm, bothHands, preHoldType), getArmPose(arm, bothHands, holdType));
    }
}
