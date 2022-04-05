package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import net.minecraft.world.entity.HumanoidArm;

public class Glock17GunMotion extends GunMotion {
    private static final MotionPoint HAND_BASE = new MotionPoint(-0.062500015f, -0.47891992f, -0.09528247f, -83.54764f, 173.175f, -0.25074303f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_LEFT = new MotionPoint(-0.49500018f, -0.36319706f, 0.21540305f, -86.88011f, 180.66992f, -10.5800085f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_RIGHT = new MotionPoint(-0.5020002f, -0.36319706f, 0.21540305f, -86.88011f, 180.66992f, -10.380004f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final Motion HAND_HOLD_MOTION_LEFT = Motion.of(HAND_BASE, HAND_HOLD_LEFT);
    private static final Motion HAND_HOLD_MOTION_RIGHT = Motion.of(HAND_BASE, HAND_HOLD_RIGHT);

    private static final MotionPoint OP_HAND_BASE = new MotionPoint(-0.12500003f, -0.034925662f, 0.080000006f, 0.25074324f, 10.0f, 51.82061f, 0.06249994f, 0.6270069f, 0.0050000004f, false, false, false);
    private static final MotionPoint OP_HAND_HOLD = new MotionPoint(-0.12500003f, 0.015074354f, 0.080000006f, 0.25074324f, 10.0f, 35.82061f, 0.06249994f, 0.6270069f, 0.0050000004f, false, false, false);
    private static final Motion OP_HAND_HOLD_MOTION = Motion.of(OP_HAND_BASE, OP_HAND_HOLD);

    private static final MotionPoint GUN_BASE = new MotionPoint(0.47500157f, 0.6508932f, 0.14970265f, -85.512436f, -10.5f, 0.0f, -0.5f, -0.14999999f, -0.35000002f, false, false, false);
    private static final Motion GUN_BASE_MOTION = Motion.of(GUN_BASE);

    @Override
    protected Motion getHandHoldMotion(HumanoidArm arm, boolean bothHands) {
        return arm == HumanoidArm.LEFT ? HAND_HOLD_MOTION_LEFT : HAND_HOLD_MOTION_RIGHT;
    }

    @Override
    protected Motion getOppositeHandHoldMotion(HumanoidArm arm) {
        return OP_HAND_HOLD_MOTION;
    }

    @Override
    protected Motion getGunMotion(HumanoidArm arm, boolean bothHands) {
        return GUN_BASE_MOTION;
    }
}
