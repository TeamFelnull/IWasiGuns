package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.data.ClientGunTrans;
import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.iwasi.client.motion.gun.GunMotion;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.gun.trans.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.HoldGunTrans;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.otyacraftengine.client.motion.MotionPose;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

public abstract class GunRenderer<M extends GunMotion> {
    protected static final Minecraft mc = Minecraft.getInstance();
    protected static final float SLIM_TRANS = 0.035f / 2f;

    abstract public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay);

    public void renderHand(M motion, PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        boolean off = hand == InteractionHand.MAIN_HAND;
        HumanoidArm arm = off ? mc.player.getMainArm() : mc.player.getMainArm().getOpposite();
        boolean slim = OEModelUtil.isSlimPlayerModel(mc.player);
        boolean handFlg = arm == HumanoidArm.LEFT;
        float t = handFlg ? -1f : 1f;
        boolean bothHand = false;

        if (hand == InteractionHand.MAIN_HAND)
            bothHand = mc.player.getItemInHand(OEEntityUtil.getHandByArm(mc.player, arm.getOpposite())).isEmpty();

        boolean hideOp = hand == InteractionHand.OFF_HAND || !bothHand;

        var cgtd = ClientGunTrans.getGunTrans(hand, partialTicks);
        //  cgtd = new DeltaGunTransData(IWGunTrans.GLOCK_17_RELOAD, 2f, 1);
//IWGunTrans.GLOCK_17_RELOAD.getProgress(IWItemUtil.getGun(stack), 1)

        var cgt = cgtd.gunTrans();
        var igt = new InfoGunTrans(cgtd, stack);

        if (cgt != null && cgt.isUseBothHand())
            bothHand = true;

        float holdPar = getHoldParent(stack, hand, partialTicks);
        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);
        poseStack.pushPose();

        poseHand(motion, poseStack, arm, bothHand, holdPar, igt);

        if (slim)
            poseStack.translate(t * -SLIM_TRANS, 0, 0);

        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, arm, packedLight);

        poseGun(motion, poseStack, arm, bothHand, holdPar, igt);
        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);

        if (handFlg)
            poseStack.translate(1f, 0, 0f);
        OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, stack, packedLight);

        poseStack.popPose();

        if (bothHand) {
            poseStack.pushPose();
            var oparm = arm.getOpposite();
            poseOppositeHand(motion, poseStack, oparm, holdPar, igt, hideOp);

            if (slim)
                poseStack.translate(t * SLIM_TRANS, 0, 0);
            OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, oparm, packedLight);

            ItemStack opItem = ItemStack.EMPTY;
            if (cgt instanceof AbstractReloadGunTrans && (cgtd.step() == 1 || cgtd.step() == 2))
                opItem = new ItemStack(IWItems.GLOCK_17_MAGAZINE.get());

            if (!opItem.isEmpty()) {
                if (handFlg)
                    poseStack.translate(1f, 0, 0f);
                poseOppositeItem(motion, poseStack, arm, holdPar, igt);

                if (slim)
                    poseStack.translate(t * -SLIM_TRANS, 0, 0);
                OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, opItem, packedLight);
            }
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    protected void poseHand(M motion, PoseStack stack, HumanoidArm arm, boolean bothHands, float hold, InfoGunTrans gunTrans) {
        MotionPose pose = motion.getHandHoldMotion(arm, bothHands, IWPlayerData.getPreHold(mc.player), IWPlayerData.getLastHold(mc.player)).getPose(hold);

        if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = motion.getHandReloadMotion(arm, gunTrans, pose);
        }
        if (arm == HumanoidArm.LEFT)
            pose = pose.reverse();
        pose.pose(stack);
    }

    protected void poseOppositeHand(M motion, PoseStack stack, HumanoidArm arm, float hold, InfoGunTrans gunTrans, boolean hide) {
        MotionPose pose = hide ? motion.getOppositeHandHideMotionPoint(arm).getPose() : motion.getOppositeHandHoldMotion(arm, IWPlayerData.getPreHold(mc.player), IWPlayerData.getLastHold(mc.player)).getPose(hold);

        if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = motion.getOppositeHandReloadMotion(arm, gunTrans, pose);
        }

        if (arm != HumanoidArm.LEFT)
            pose = pose.reverse();
        pose.pose(stack);
    }

    protected void poseGun(M motion, PoseStack stack, HumanoidArm arm, boolean bothHands, float hold, InfoGunTrans gunTrans) {
        MotionPose pose;
        var bp = motion.getGunFixedMotionPoint(arm, bothHands, IWPlayerData.getLastHold(mc.player));

        if (gunTrans.gunTransData().gunTrans() instanceof HoldGunTrans) {
            pose = motion.getGunHoldMotion(arm, bothHands, IWPlayerData.getPreHold(mc.player), IWPlayerData.getHold(mc.player)).getPose(hold);
        } else if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = bp.getPose();
        } else {
            pose = bp.getPose();
        }

        if (arm == HumanoidArm.LEFT)
            pose = pose.reverse();
        pose.pose(stack);
    }

    protected void poseOppositeItem(M motion, PoseStack stack, HumanoidArm arm, float hold, InfoGunTrans gunTrans) {
        MotionPose pose;
        var bp = motion.getOppositeItemFixedMotionPoint(arm, hold > 0.5);

        if (gunTrans.gunTransData().gunTrans() instanceof HoldGunTrans) {
            pose = motion.getOppositeItemHoldMotion(arm).getPose(hold);
        } else if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = bp.getPose();
        } else {
            pose = bp.getPose();
        }

        if (arm == HumanoidArm.LEFT)
            pose = pose.reverse();
        pose.pose(stack);
    }

    protected float getHoldParent(ItemStack stack, InteractionHand hand, float delta) {
        return IWPlayerData.getHoldProgress(mc.player, hand, delta);
       /* if (!(stack.getItem() instanceof GunItem gunItem)) return 0f;
        boolean holding = IWPlayerData.isHolding(mc.player, hand);
        if (deltaGunTransData.gunTrans() == null || (deltaGunTransData.gunTrans() != gunItem.getGun().getHoldTrans() && deltaGunTransData.gunTrans() != gunItem.getGun().getUnHoldTrans()))
            return holding ? 1f : 0f;
        float holdPar = (deltaGunTransData.progress() / ((float) deltaGunTransData.gunTrans().getProgress(gunItem.getGun(), deltaGunTransData.step()) - 1f));
        if (deltaGunTransData.gunTrans() instanceof HoldGunTrans holdGunTrans && holdGunTrans.isRevers())
            holdPar = 1f - holdPar;
        return holdPar;*/
    }

    protected static void renderItem(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        mc.getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, poseStack, multiBufferSource, 0);
        poseStack.popPose();
    }
}
