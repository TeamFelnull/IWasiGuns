package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionPose;
import net.minecraft.world.entity.HumanoidArm;

public abstract class GunMotion {
    abstract public MotionPoint getHandFixedMotionPoint(HumanoidArm arm, boolean bothHands, boolean hold);

    abstract public MotionPoint getOppositeHandFixedMotionPoint(HumanoidArm arm, boolean hold);

    abstract public MotionPoint getGunFixedMotionPoint(HumanoidArm arm, boolean bothHands, boolean hold);

    abstract public MotionPoint getOppositeItemFixedMotionPoint(HumanoidArm arm, boolean hold);

    public Motion getHandHoldMotion(HumanoidArm arm, boolean bothHands) {
        return Motion.of(getHandFixedMotionPoint(arm, bothHands, false), getHandFixedMotionPoint(arm, bothHands, true));
    }

    public Motion getOppositeHandHoldMotion(HumanoidArm arm) {
        return Motion.of(getOppositeHandFixedMotionPoint(arm, false), getOppositeHandFixedMotionPoint(arm, true));
    }

    public Motion getGunHoldMotion(HumanoidArm arm, boolean bothHands) {
        return Motion.of(getGunFixedMotionPoint(arm, bothHands, false), getGunFixedMotionPoint(arm, bothHands, true));
    }

    public Motion getOppositeItemHoldMotion(HumanoidArm arm) {
        return Motion.of(getOppositeItemFixedMotionPoint(arm, false), getOppositeItemFixedMotionPoint(arm, true));
    }

    abstract public MotionPose getHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPoint base);

    abstract public MotionPose getOppositeHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPoint base);
}
