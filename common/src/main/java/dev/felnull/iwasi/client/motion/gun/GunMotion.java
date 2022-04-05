package dev.felnull.iwasi.client.motion.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.motion.Motion;
import net.minecraft.world.entity.HumanoidArm;

public abstract class GunMotion {

    public void poseHandHold(PoseStack stack, HumanoidArm arm, boolean bothHands, float hold) {
        var hbp = getHandHoldMotion(arm, bothHands).getPose(hold);
        if (arm == HumanoidArm.LEFT)
            hbp = hbp.reverse();
        hbp.pose(stack);
    }

    public void poseOppositeHandHold(PoseStack stack, HumanoidArm arm, float hold) {
        var hbp = getOppositeHandHoldMotion(arm).getPose(hold);
        if (arm != HumanoidArm.LEFT)
            hbp = hbp.reverse();
        hbp.pose(stack);
    }

    public void poseGun(PoseStack stack, HumanoidArm arm, boolean bothHands, float hold) {
        var hbp = getGunMotion(arm, bothHands).getPose(hold);
        if (arm == HumanoidArm.LEFT)
            hbp = hbp.reverse();
        hbp.pose(stack);
    }

    abstract protected Motion getHandHoldMotion(HumanoidArm arm, boolean bothHands);

    abstract protected Motion getOppositeHandHoldMotion(HumanoidArm arm);

    abstract protected Motion getGunMotion(HumanoidArm arm, boolean bothHands);
}
