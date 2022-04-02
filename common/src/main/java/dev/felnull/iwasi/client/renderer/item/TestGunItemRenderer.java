package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.state.IWPlayerState;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TestGunItemRenderer implements BEWLItemRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final float SLIM_TRANS = 0.035f;

    private static final MotionPoint HAND_BASE = new MotionPoint(-0.062500015f, -0.47891992f, -0.09528247f, -83.54764f, 173.175f, -0.25074303f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD = new MotionPoint(-0.5250022f, -0.36830327f, 0.15249941f, -86.59672f, 181.76714f, -21.66596f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final Motion HAND_HOLD_MOTION = Motion.of(HAND_BASE, HAND_HOLD);

    private static final MotionPoint OP_HAND_BASE = new MotionPoint(-0.12500003f, -0.034925662f, 0.080000006f, 0.25074324f, 10.0f, 42.82061f, 0.06249994f, 0.6270069f, 0.0050000004f, false, false, false);
    private static final MotionPoint OP_HAND_HOLD = new MotionPoint(-0.12500003f, 0.015074354f, 0.080000006f, 0.25074324f, 10.0f, 35.82061f, 0.06249994f, 0.6270069f, 0.0050000004f, false, false, false);
    private static final Motion OP_HAND_HOLD_MOTION = Motion.of(OP_HAND_BASE, OP_HAND_HOLD);

    private static final MotionPoint GUN_BASE = new MotionPoint(-0.09874919f, 0.54875624f, -0.44717556f, -85.0f, -189.875f, 0.0f, 0.065f, -0.025000002f, 0.24500018f, false, false, false);
    private static final MotionPoint GUN_HOLD = new MotionPoint(-0.09874919f, 0.54875624f, -0.44717556f, -85.0f, -201.875f, 0.0f, 0.065f, -0.025000002f, 0.24500018f, false, false, false);
    private static final Motion GUN_HOLD_MOTION = Motion.of(GUN_BASE, GUN_HOLD);

    private static int holdRight;
    private static int holdLeft;
    private static int holdRightOld;
    private static int holdLeftOld;

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float f, int light, int overlay) {
        var main = OEModelUtil.getModel(IWModels.GLOCK_MAIN);
        var slide = OEModelUtil.getModel(IWModels.GLOCK_SLIDE);
        var magazine = OEModelUtil.getModel(IWModels.GLOCK_MAGAZINE);

        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        OERenderUtil.renderModel(poseStack, vc, main, light, overlay);
        OERenderUtil.renderModel(poseStack, vc, slide, light, overlay);
        OERenderUtil.renderModel(poseStack, vc, magazine, light, overlay);
    }

    public static void renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        boolean off = hand == InteractionHand.MAIN_HAND;
        HumanoidArm arm = off ? mc.player.getMainArm() : mc.player.getMainArm().getOpposite();
        HumanoidArm opArm = off ? mc.player.getMainArm().getOpposite() : mc.player.getMainArm();
        boolean handFlg = arm == HumanoidArm.LEFT;
        float t = handFlg ? -1f : 1f;
        boolean slim = OEModelUtil.isSlimPlayerModel(mc.player);
        float hold = (handFlg ? Mth.lerp(partialTicks, holdLeftOld, holdLeft) : Mth.lerp(partialTicks, holdRightOld, holdRight)) / 5f;

        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);

        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);

        var hbp = HAND_HOLD_MOTION.getPose(hold);
        if (handFlg)
            hbp = hbp.reverse();
        hbp.pose(poseStack);

        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, arm, packedLight);

        poseStack.pushPose();

        var ohbp = OP_HAND_HOLD_MOTION.getPose(hold);
        if (handFlg)
            ohbp = ohbp.reverse();
        ohbp.pose(poseStack);

        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, opArm, packedLight);
        poseStack.popPose();

        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);

        var gbp = GUN_HOLD_MOTION.getPose(hold);
        if (handFlg)
            gbp = gbp.reverse();
        gbp.pose(poseStack);

        OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, stack, packedLight);

        poseStack.popPose();
    }

    public static void poseHand(HumanoidArm arm, HumanoidModel<? extends LivingEntity> model, ItemStack stack) {

    }

    public static void tick() {
        if (mc.player == null || mc.level == null) return;
        holdRightOld = holdRight;
        holdLeftOld = holdLeft;
        if (IWPlayerState.isHolding(mc.player)) {
            holdRight = Math.min(holdRight + 1, 5);
            holdLeft = Math.min(holdLeft + 1, 5);
        } else {
            holdRight = Math.max(holdRight - 1, 0);
            holdLeft = Math.max(holdLeft - 1, 0);
        }
    }
}
