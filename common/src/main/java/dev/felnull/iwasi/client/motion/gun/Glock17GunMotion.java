package dev.felnull.iwasi.client.motion.gun;

import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.otyacraftengine.client.motion.MotionManager;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionPose;
import dev.felnull.otyacraftengine.client.motion.MotionSwapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class Glock17GunMotion extends GunMotion {
    private static final MotionPoint HAND_BASE = new MotionPoint(-0.062500015f, -0.47891992f, -0.09528247f, -83.54764f, 173.175f, -0.25074303f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_LEFT = new MotionPoint(-0.49500018f, -0.36319706f, 0.21540305f, -86.88011f, 180.66992f, -10.5800085f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_RIGHT = new MotionPoint(-0.5020002f, -0.36319706f, 0.21540305f, -86.88011f, 180.66992f, -10.380004f, -0.055f, 0.6149996f, 0.0f, false, false, false);

    private static final MotionPoint OP_HAND_BASE = new MotionPoint(-0.18000144f, -0.54029214f, -0.044355575f, -87.54647f, -181.9746f, 40.24339f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);
    private static final MotionPoint OP_HAND_HOLD = new MotionPoint(-0.62000144f, -0.37529215f, 0.27064434f, -88.34647f, -181.9746f, 33.043396f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);

    private static final MotionPoint GUN_BASE = new MotionPoint(0.47500157f, 0.6508932f, 0.14970265f, -85.512436f, -10.5f, 0.0f, -0.5f, -0.14999999f, -0.35000002f, false, false, false);

    private static final MotionPoint OP_ITEM_BASE = new MotionPoint(0.45750028f, 0.87901837f, 0.060312465f, -96.51788f, 47.450016f, 35.36559f, -0.48999998f, -0.21000001f, -0.315f, false, false, false);

    private static final ResourceLocation HAND_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/hand_reload");

    private static final ResourceLocation OP_HAND_RELOAD_MOTION = new ResourceLocation(IWasi.MODID, "glock_17/opposite_hand_reload");

    private static final MotionPoint OP_HAND_HIDE = new MotionPoint(-0.9300009f, -0.966556f, -0.044355575f, -87.54647f, -181.9746f, 40.24339f, 0.069000006f, 0.6063378f, -1.4881045E-4f, false, false, false);

    @Override
    public MotionPoint getHandFixedMotionPoint(HumanoidArm arm, boolean bothHands, boolean hold) {
        if (hold)
            return arm == HumanoidArm.LEFT ? HAND_HOLD_LEFT : HAND_HOLD_RIGHT;
        return HAND_BASE;
    }

    @Override
    public MotionPoint getOppositeHandFixedMotionPoint(HumanoidArm arm, boolean hold) {
        if (hold)
            return OP_HAND_HOLD;
        return OP_HAND_BASE;
    }

    @Override
    public MotionPoint getGunFixedMotionPoint(HumanoidArm arm, boolean bothHands, boolean hold) {
        return GUN_BASE;
    }

    @Override
    public MotionPose getHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPoint base) {
        var gtd = infoGunTrans.gunTransData();
        var mt = MotionManager.getInstance().getMotion(HAND_RELOAD_MOTION);
        if (gtd.step() == 0)
            return mt.getPose(infoGunTrans.progressPar(), MotionSwapper.swapStartAndEnd(base, base), 0, 1);
        if (gtd.step() == 1 || gtd.step() == 2) {
            float par;
            if (gtd.step() == 1)
                par = infoGunTrans.progressPar() / 2f;
            else
                par = 0.5f + infoGunTrans.progressPar() / 2f;
            return mt.getPose(par, MotionSwapper.swapStartAndEnd(base, base), 1, 2);
        }
        return mt.getPose(infoGunTrans.progressPar(), MotionSwapper.swapStartAndEnd(base, base), 2, 3);
    }

    @Override
    public MotionPose getOppositeHandReloadMotion(HumanoidArm arm, InfoGunTrans infoGunTrans, MotionPoint base) {
        var gtd = infoGunTrans.gunTransData();
        return MotionManager.getInstance().getMotion(OP_HAND_RELOAD_MOTION).getPose(infoGunTrans.progressPar(), MotionSwapper.swapStartAndEnd(base, base), gtd.step(), gtd.step() + 1);
    }

    @Override
    public MotionPoint getOppositeItemFixedMotionPoint(HumanoidArm arm, boolean hold) {
        return OP_ITEM_BASE;
    }

    @Override
    public MotionPoint getOppositeHandHideMotionPoint(HumanoidArm arm) {
        return OP_HAND_HIDE;
    }

}
