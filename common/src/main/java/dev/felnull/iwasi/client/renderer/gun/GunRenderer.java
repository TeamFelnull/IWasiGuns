package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.data.DeltaGunItemTransData;
import dev.felnull.iwasi.client.data.DeltaGunPlayerTransData;
import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.iwasi.client.motion.gun.GunMotion;
import dev.felnull.iwasi.client.util.IWClientPlayerData;
import dev.felnull.iwasi.data.GunItemTransData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.gun.trans.item.GunItemTrans;
import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.player.GunPlayerTrans;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.util.IWPlayerUtil;
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

    abstract public void render(ItemStack stack, ItemStack stackOld, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay);

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

        var cgtd = IWClientPlayerData.getGunTransData(mc.player, hand, partialTicks);
        //  cgtd = new DeltaGunTransData(IWGunTrans.GLOCK_17_RELOAD, 2f, 1);
//IWGunTrans.GLOCK_17_RELOAD.getProgress(IWItemUtil.getGun(stack), 1)

        var cgt = cgtd.gunTrans();
        var igt = new InfoGunTrans(cgtd, stack);

        if (cgt != null && cgt.isUseBothHand())
            bothHand = true;

        float holdPar = IWPlayerUtil.getHoldProgress(mc.player, hand, partialTicks);
        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);

        poseRecoil(motion, poseStack, arm, partialTicks);

        poseStack.pushPose();

        poseHand(motion, poseStack, arm, bothHand, holdPar, igt);
        //  MotionDebug.getInstance().onDebug(poseStack, multiBufferSource, 0.5f);

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

            ItemStack opItem = getOppositeItem(cgt, cgtd, OEEntityUtil.getOppositeHand(hand));

            if (!opItem.isEmpty()) {
                poseOppositeItem(motion, poseStack, arm, holdPar, igt);

                if (slim)
                    poseStack.translate(t * -SLIM_TRANS, 0, 0);

                if (handFlg)
                    poseStack.translate(1f, 0, 0f);
                OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, opItem, packedLight);
            }
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    protected ItemStack getOppositeItem(GunPlayerTrans gunPlayerTrans, DeltaGunPlayerTransData deltaGunPlayerTransData, InteractionHand hand) {
        ItemStack opItem = ItemStack.EMPTY;

        if (gunPlayerTrans instanceof AbstractReloadGunTrans && (deltaGunPlayerTransData.step() == 1 || deltaGunPlayerTransData.step() == 2)) {
            var lst = IWPlayerData.getTmpHandItems(mc.player, hand);
            if (!lst.isEmpty())
                opItem = lst.get(0);
        }

        return opItem;
    }

    protected void poseRecoil(M motion, PoseStack stack, HumanoidArm arm, float delta) {
        float rcp = IWPlayerData.getRecoil(mc.player, OEEntityUtil.getHandByArm(mc.player, arm), delta);
        var ht = IWPlayerData.getLastHold(mc.player);
        var pose = ht == HoldType.HOLD ? motion.getRecoilHold(rcp) : motion.getRecoilBase(rcp);

        if (arm == HumanoidArm.LEFT)
            pose = pose.reverse();
        pose.pose(stack);
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

        if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
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

        if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = bp.getPose();
        } else {
            pose = bp.getPose();
        }

        if (arm == HumanoidArm.LEFT)
            pose = pose.reverse();
        pose.pose(stack);
    }

    protected static void renderItem(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        mc.getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, poseStack, multiBufferSource, 0);
        poseStack.popPose();
    }

    protected DeltaGunItemTransData getGunItemTrans(GunItemTrans gunItemTrans, ItemStack stack, ItemStack oldStack, float delta) {
        if (stack.isEmpty() || oldStack.isEmpty()) return null;
        var ng = GunItem.getGunItemTrans(stack, gunItemTrans.getName());
        var og = GunItem.getGunItemTrans(oldStack, gunItemTrans.getName());
        if (ng == null && og == null) return null;
        if (ng == null)
            ng = new GunItemTransData(null);
        if (og == null)
            og = new GunItemTransData(null);

        return DeltaGunItemTransData.of(delta, og, ng, stack);
    }
}
