package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.item.gun.GunItem;
import dev.felnull.iwasi.state.IWPlayerState;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
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
    private static final MotionPoint HAND_HOLD_RIGHT = new MotionPoint(-0.5020002f, -0.36319706f, 0.21540305f, -86.88011f, 180.66992f, -10.380004f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD_LEFT = new MotionPoint(-0.49500018f, -0.36319706f, 0.21540305f, -86.88011f, 180.66992f, -10.5800085f, -0.055f, 0.6149996f, 0.0f, false, false, false);
    private static final Motion HAND_HOLD_MOTION_RIGHT = Motion.of(HAND_BASE, HAND_HOLD_RIGHT);
    private static final Motion HAND_HOLD_MOTION_LEFT = Motion.of(HAND_BASE, HAND_HOLD_LEFT);

    private static final MotionPoint OP_HAND_BASE = new MotionPoint(-0.12500003f, -0.034925662f, 0.080000006f, 0.25074324f, 10.0f, 51.82061f, 0.06249994f, 0.6270069f, 0.0050000004f, false, false, false);
    private static final MotionPoint OP_HAND_HOLD = new MotionPoint(-0.12500003f, 0.015074354f, 0.080000006f, 0.25074324f, 10.0f, 35.82061f, 0.06249994f, 0.6270069f, 0.0050000004f, false, false, false);
    private static final Motion OP_HAND_HOLD_MOTION = Motion.of(OP_HAND_BASE, OP_HAND_HOLD);

    private static final MotionPoint GUN_BASE = new MotionPoint(0.47500157f, 0.6508932f, 0.14970265f, -85.512436f, -10.5f, 0.0f, -0.5f, -0.14999999f, -0.35000002f, false, false, false);
    //private static final MotionPoint GUN_HOLD = new MotionPoint(0.47500157f, 0.6508932f, 0.14970265f, -88.512436f, -21.5f, 0.0f, -0.5f, -0.14999999f, -0.35000002f, false, false, false);
    //  private static final Motion GUN_HOLD_MOTION = Motion.of(GUN_BASE, GUN_HOLD);

    private static int holdLeft;
    private static int holdRight;
    private static int holdLeftOld;
    private static int holdRightOld;

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float f, int light, int overlay) {
        var main = OEModelUtil.getModel(IWModels.GLOCK_MAIN);
        var slide = OEModelUtil.getModel(IWModels.GLOCK_SLIDE);
        var magazine = OEModelUtil.getModel(IWModels.GLOCK_MAGAZINE);

        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        poseStack.pushPose();

        if (isAdjustmentModel(transformType)) {
            OERenderUtil.poseTrans16(poseStack, -6.35, -2.0351, -0.8561);
            OERenderUtil.poseRotateY(poseStack, 180);
            poseStack.translate(-1f, 0f, -1f);

            OERenderUtil.poseTrans16(poseStack, 1.35 / 2f, 0, 0);
        }

        OERenderUtil.renderModel(poseStack, vc, main, light, overlay);

        poseStack.pushPose();
        float sv = 0;// IWPlayerState.isPullTrigger(mc.player) ? Math.abs(-0.5f + OERenderUtil.getParSecond(100)) * 2f : 0;
        OERenderUtil.poseTrans16(poseStack, 0, 0, 1.625f * sv);
        OERenderUtil.renderModel(poseStack, vc, slide, light, overlay);
        poseStack.popPose();

        OERenderUtil.renderModel(poseStack, vc, magazine, light, overlay);
        poseStack.popPose();
    }

    private static boolean isAdjustmentModel(ItemTransforms.TransformType transform) {
        return transform == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transform == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || transform == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transform == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
    }

    public static void renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        boolean off = hand == InteractionHand.MAIN_HAND;
        HumanoidArm arm = off ? mc.player.getMainArm() : mc.player.getMainArm().getOpposite();
        HumanoidArm opArm = arm.getOpposite();
        boolean handFlg = arm == HumanoidArm.LEFT;
        float t = handFlg ? -1f : 1f;
        boolean slim = OEModelUtil.isSlimPlayerModel(mc.player);
        float hold = getHoldingPar(arm, partialTicks);

        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);

        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);

        var hbp = handFlg ? HAND_HOLD_MOTION_LEFT.getPose(hold) : HAND_HOLD_MOTION_RIGHT.getPose(hold);
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

        var gbp = GUN_BASE.getPose(); //GUN_HOLD_MOTION.getPose(hold);
        if (handFlg)
            gbp = gbp.reverse();

        gbp.pose(poseStack);

        if (handFlg)
            poseStack.translate(1f, 0, 0f);

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
            holdRight = Math.min(holdRight + 1, getMaxHolding(HumanoidArm.RIGHT));
            holdLeft = Math.min(holdLeft + 1, getMaxHolding(HumanoidArm.LEFT));
        } else {
            holdRight = Math.max(holdRight - 1, 0);
            holdLeft = Math.max(holdLeft - 1, 0);
        }
    }

    public static void reset(HumanoidArm arm) {
        if (arm == HumanoidArm.LEFT) {
            holdLeftOld = 0;
            holdLeft = 0;
        } else {
            holdRightOld = 0;
            holdRight = 0;
        }
    }

    private static float getHoldingPar(HumanoidArm arm, float delta) {
        if (arm == HumanoidArm.LEFT) {
            return Mth.lerp(delta, holdLeftOld, holdLeft) / (float) getMaxHolding(arm);
        } else {
            return Mth.lerp(delta, holdRightOld, holdRight) / (float) getMaxHolding(arm);
        }
        //int prgress = IWPlayerState.getHoldingProgress(mc.player, getHandByArm(mc.player, arm));
        //return (float) prgress / (float) getMaxHolding(arm);
    }

    private static int getMaxHolding(HumanoidArm arm) {
        var item = mc.player.getItemInHand(OEEntityUtil.getHandByArm(mc.player, arm));
        if (item.getItem() instanceof GunItem gunItem)
            return gunItem.getHoldingTime();
        return 0;
    }
}
