package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.data.ClientGunTrans;
import dev.felnull.iwasi.client.motion.gun.GunMotion;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

public abstract class IGunRenderer<M extends GunMotion> {
    Minecraft mc = Minecraft.getInstance();
    float SLIM_TRANS = 0.035f;

    abstract public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay);

    public void renderHand(M motion, PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        boolean off = hand == InteractionHand.MAIN_HAND;
        HumanoidArm arm = off ? mc.player.getMainArm() : mc.player.getMainArm().getOpposite();
        boolean slim = OEModelUtil.isSlimPlayerModel(mc.player);
        boolean handFlg = arm == HumanoidArm.LEFT;
        float t = handFlg ? -1f : 1f;
        boolean bothHand;
        if (hand == InteractionHand.MAIN_HAND)
            bothHand = mc.player.getItemInHand(OEEntityUtil.getHandByArm(mc.player, arm.getOpposite())).isEmpty();
        else
            bothHand = false;

        var cgt = ClientGunTrans.getGunTrans(hand, partialTicks);

        float holdPar = getHoldParent(cgt, stack, hand);

        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);
        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);

        motion.poseHandHold(poseStack, arm, bothHand, holdPar);

        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, arm, packedLight);

        if (bothHand) {
            poseStack.pushPose();
            var oparm = arm.getOpposite();
            motion.poseOppositeHandHold(poseStack, oparm, holdPar);
            OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, oparm, packedLight);
            poseStack.popPose();
        }

        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);

        motion.poseGun(poseStack, arm, bothHand, holdPar);

        if (handFlg)
            poseStack.translate(1f, 0, 0f);

        OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, stack, packedLight);

        poseStack.popPose();
    }

    private float getHoldParent(ClientGunTrans.DeltaGunTransData deltaGunTransData, ItemStack stack, InteractionHand hand) {
        if (!(stack.getItem() instanceof GunItem gunItem)) return 0f;
        boolean holding = IWPlayerData.isHolding(mc.player, hand);
        if (deltaGunTransData.gunTrans() == null)
            return holding ? 1f : 0f;
        float holdPar = (deltaGunTransData.progress() / ((float) deltaGunTransData.gunTrans().getProgress(gunItem.getGun(), deltaGunTransData.step()) - 1f));
        if (holding)
            holdPar = 1f - holdPar;
        return holdPar;
    }
}