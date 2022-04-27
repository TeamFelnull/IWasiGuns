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
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
        boolean bothHand = isBothHand(mc.player, hand);

        boolean hideOp = hand == InteractionHand.OFF_HAND || !bothHand;

        var cgtd = IWClientPlayerData.getGunTransData(mc.player, hand, partialTicks);
        //  cgtd = new DeltaGunTransData(IWGunTrans.GLOCK_17_RELOAD, 2f, 1);
//IWGunTrans.GLOCK_17_RELOAD.getProgress(IWItemUtil.getGun(stack), 1)

        var cgt = cgtd.gunTrans();
        var igt = new InfoGunTrans(cgtd, stack);

        boolean tmpBothHand = bothHand;
        if (cgt != null && cgt.isUseBothHand()) tmpBothHand = true;

        float holdPar = IWPlayerUtil.getHoldProgress(mc.player, hand, partialTicks);
        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);

        poseRecoil(motion, poseStack, arm, bothHand, partialTicks);

        poseStack.pushPose();

        poseHand(motion, poseStack, arm, bothHand, holdPar, igt);

        if (slim) poseStack.translate(t * -SLIM_TRANS, 0, 0);

        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, arm, packedLight);

        poseGun(motion, poseStack, arm, bothHand, holdPar, igt);
        if (slim) poseStack.translate(t * SLIM_TRANS, 0, 0);

        if (handFlg) poseStack.translate(1f, 0, 0f);
        OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, stack, packedLight);

        poseStack.popPose();

        if (bothHand || tmpBothHand) {
            poseStack.pushPose();
            var oparm = arm.getOpposite();
            poseOppositeHand(motion, poseStack, oparm, holdPar, igt, hideOp);

            if (slim) poseStack.translate(t * SLIM_TRANS, 0, 0);
            OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, oparm, packedLight);

            ItemStack opItem = getOppositeItem(cgt, cgtd, OEEntityUtil.getOppositeHand(hand));

            if (!opItem.isEmpty()) {
                poseOppositeItem(motion, poseStack, arm, holdPar, igt);

                if (slim) poseStack.translate(t * -SLIM_TRANS, 0, 0);

                if (handFlg) poseStack.translate(1f, 0, 0f);
                OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, opItem, packedLight);
            }
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    public void renderArmWithItem(M motion, ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>> layer, LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm arm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, float delta) {
        if (!(livingEntity instanceof AbstractClientPlayer player)) return;
        boolean handFlg = arm == HumanoidArm.LEFT;
        boolean slim = OEModelUtil.isSlimPlayerModel(player);
        var cgtd = IWClientPlayerData.getGunTransData(player, OEEntityUtil.getHandByArm(livingEntity, arm), delta);
        var igt = new InfoGunTrans(cgtd, itemStack);
        boolean bothHand = isBothHand(player, OEEntityUtil.getHandByArm(player, arm));
        float t = handFlg ? -1f : 1f;
        float holdPar = IWPlayerUtil.getHoldProgress(player, OEEntityUtil.getHandByArm(player, arm), delta);

        poseStack.pushPose();
        layer.getParentModel().translateToHand(arm, poseStack);

        if (slim) poseStack.translate(t * -SLIM_TRANS, 0, 0);

        poseArmGun(motion, player, poseStack, arm, bothHand, igt, holdPar, delta);

        OERenderUtil.poseScaleAll(poseStack, 0.7f);
        if (handFlg) poseStack.translate(1f, 0, 0f);

        Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemStack, transformType, handFlg, poseStack, multiBufferSource, i);
        poseStack.popPose();
    }


    public void poseArm(M motion, InteractionHand hand, HumanoidArm arm, HumanoidModel<? extends LivingEntity> model, ItemStack itemStack, LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player)) return;
        boolean bothHand = isBothHand(player, hand);
        float delta = OERenderUtil.getPartialTicks();
        var headPart = model.head;
        var mainPart = arm == HumanoidArm.LEFT ? model.leftArm : model.rightArm;
        var offPart = arm == HumanoidArm.LEFT ? model.rightArm : model.leftArm;
        var cgtd = IWClientPlayerData.getGunTransData(player, OEEntityUtil.getHandByArm(livingEntity, arm), delta);
        var igt = new InfoGunTrans(cgtd, itemStack);
        float holdPar = IWPlayerUtil.getHoldProgress(player, hand, delta);
        //marm.xRot = -(float) Math.PI / 2f - 0.1f;
        //marm.yRot = 0.5f * rv;

        float headParY = 1f;
        float headParX = 1f;
        var lh = IWPlayerData.getLastHold(player);

        if (lh.isDisarmament())
            headParY = 1f - holdPar;

        if (lh != HoldType.UPPER && lh.isDisarmament())
            headParX = 1f - holdPar;

        poseArm(motion, player, mainPart, headPart, arm, bothHand, holdPar, headParY, headParX, igt);

        if (bothHand) {
            poseOppositeArm(motion, player, offPart, headPart, arm, holdPar, headParY, headParX, igt);
        }
    }

    protected void poseArm(M motion, Player player, ModelPart armPart, ModelPart headPart, HumanoidArm arm, boolean bothHands, float hold, float headParY, float headParX, InfoGunTrans gunTrans) {
        MotionPose pose = motion.getArmPoseHoldMotion(arm, bothHands, IWPlayerData.getPreHold(player), IWPlayerData.getLastHold(player)).getPose(hold);

        if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = motion.getHandReloadMotion(arm, gunTrans, pose);
        }

        if (arm == HumanoidArm.LEFT)
            pose = pose.reverse();

        setArmByPose(armPart, headPart, pose, headParY, headParX);
    }

    protected void poseOppositeArm(M motion, Player player, ModelPart oppositeArmPart, ModelPart headPart, HumanoidArm arm, float hold, float headParY, float headParX, InfoGunTrans gunTrans) {
        MotionPose pose = motion.getOppositeArmPoseHoldMotion(arm, IWPlayerData.getPreHold(player), IWPlayerData.getLastHold(player)).getPose(hold);

        if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = motion.getOppositeHandReloadMotion(arm, gunTrans, pose);
        }

        if (arm == HumanoidArm.LEFT)
            pose = pose.reverse();

        setArmByPose(oppositeArmPart, headPart, pose, headParY, headParX);
    }

    private void setArmByPose(ModelPart part, ModelPart head, MotionPose pose) {
        setArmByPose(part, head, pose, 1f, 1f);
    }

    private void setArmByPose(ModelPart part, ModelPart head, MotionPose pose, float headParY, float headParX) {
        float b = -(float) Math.PI * 2f / 360f;
        var ang = pose.rotation().angle();

        part.xRot = b * ang.x() + (head.xRot * headParY);
        part.yRot = b * ang.y() + (head.yRot * headParX);
        part.zRot = b * ang.z();
    }

    protected boolean isBothHand(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND)
            return player.getItemInHand(OEEntityUtil.getOppositeHand(hand)).isEmpty();
        return false;
    }

    protected void poseArmGun(M motion, Player player, PoseStack poseStack, HumanoidArm arm, boolean bothHands, InfoGunTrans gunTrans, float holdPar, float delta) {
        var pose = motion.getArmGunPoseHoldMotion(arm, bothHands, IWPlayerData.getPreHold(player), IWPlayerData.getLastHold(player), player.getViewXRot(delta)).getPose(holdPar);
        if (arm == HumanoidArm.LEFT)
            pose = pose.reverse();
        pose.pose(poseStack);
    }

    protected ItemStack getOppositeItem(GunPlayerTrans gunPlayerTrans, DeltaGunPlayerTransData deltaGunPlayerTransData, InteractionHand hand) {
        ItemStack opItem = ItemStack.EMPTY;

        if (gunPlayerTrans instanceof AbstractReloadGunTrans && (deltaGunPlayerTransData.step() == 1 || deltaGunPlayerTransData.step() == 2)) {
            var lst = IWPlayerData.getTmpHandItems(mc.player, hand);
            if (!lst.isEmpty()) opItem = lst.get(0);
        }

        return opItem;
    }

    protected void poseRecoil(M motion, PoseStack stack, HumanoidArm arm, boolean bothHands, float delta) {
        float rcp = IWPlayerData.getRecoil(mc.player, OEEntityUtil.getHandByArm(mc.player, arm), delta);
        var ht = IWPlayerData.getLastHold(mc.player);
        var pose = motion.getRecoil(ht, rcp, bothHands);

        if (arm == HumanoidArm.LEFT) pose = pose.reverse();
        pose.pose(stack);
    }

    protected void poseHand(M motion, PoseStack stack, HumanoidArm arm, boolean bothHands, float hold, InfoGunTrans gunTrans) {
        MotionPose pose = motion.getHandHoldMotion(arm, bothHands, IWPlayerData.getPreHold(mc.player), IWPlayerData.getLastHold(mc.player)).getPose(hold);

        if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = motion.getHandReloadMotion(arm, gunTrans, pose);
        }

        if (arm == HumanoidArm.LEFT) pose = pose.reverse();
        pose.pose(stack);
    }

    protected void poseOppositeHand(M motion, PoseStack stack, HumanoidArm arm, float hold, InfoGunTrans gunTrans, boolean hide) {
        MotionPose pose = hide ? motion.getOppositeHandHideMotionPoint(arm).getPose() : motion.getOppositeHandHoldMotion(arm, IWPlayerData.getPreHold(mc.player), IWPlayerData.getLastHold(mc.player)).getPose(hold);

        if (gunTrans.gunTransData().gunTrans() instanceof AbstractReloadGunTrans) {
            pose = motion.getOppositeHandReloadMotion(arm, gunTrans, pose);
        }

        if (arm != HumanoidArm.LEFT) pose = pose.reverse();
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

        if (arm == HumanoidArm.LEFT) pose = pose.reverse();
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

        if (arm == HumanoidArm.LEFT) pose = pose.reverse();
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
        if (ng == null) ng = new GunItemTransData(null);
        if (og == null) og = new GunItemTransData(null);

        return DeltaGunItemTransData.of(delta, og, ng, stack);
    }
}
