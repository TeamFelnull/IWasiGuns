package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.state.IWPlayerState;
import dev.felnull.otyacraftengine.client.debug.MotionDebug;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TestGunItemRendererOld implements BEWLItemRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final float SLIM_TRANS = 0.035f;

    private static final MotionPoint TEST_POINT = new MotionPoint(0.13750024f, -0.06268585f, -1.0531217f, -37.360847f, -190.0f, -42.375637f, 0.0f, 0.0f, 0.0f, false, false, false);

    //private static final MotionPoint HAND_BASE = new MotionPoint(-0.04875036f, 0.05385135f, 0.5539495f, -77.9814f, 166.0f, 3.5103831f, -0.056500003f, -0.10650001f, 0.012999999f, false, false, false);
    private static final MotionPoint HAND_BASE = new MotionPoint(0.10124964f, 0.0038513467f, 0.5539495f, -63.9814f, 167.25f, -15.51935f, -0.056500003f, -0.10650001f, 0.012999999f, false, false, false);

    //  private static final MotionPoint HAND_HOLD = new MotionPoint(-0.62600005f, 0.27449983f, 0.79300016f, -90.84003f, 179.97002f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD = new MotionPoint(-0.17599997f, 0.074499846f, 0.5430001f, -75.84003f, 183.97002f, -54.0f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final Motion HAND_HOLD_MOTION = Motion.of(HAND_BASE, HAND_HOLD);

    private static final MotionPoint OP_HAND_BASE = new MotionPoint(0.26374936f, 0.15050092f, 0.016838621f, -4.4059463f, 15.75f, 38.46692f, 0.05f, -0.10000001f, 0.0f, false, false, false);

    private static final MotionPoint GUN_BASE = new MotionPoint(-0.025000002f, 0.67999977f, -0.12f, -76.89992f, 167.40002f, -8.999996f, 0.0025001233f, -0.23499998f, 0.12737861f, false, false, false);
    private static final MotionPoint GUN_HOLD = new MotionPoint(-0.025000002f, 0.67999977f, -0.12f, -61.08233f, 125.650024f, -17.787912f, 0.0025001233f, -0.23499998f, 0.12737861f, false, false, false);
    private static final Motion GUN_HOLD_MOTION = Motion.of(GUN_BASE, GUN_HOLD);

    private static int holdRight;
    private static int holdLeft;
    private static int holdRightOld;
    private static int holdLeftOld;

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float f, int light, int overlay) {
        var model = OEModelUtil.getModel(IWModels.TEST_GUN);
        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        OERenderUtil.renderModel(poseStack, vc, model, light, overlay);
    }

    public static void renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        boolean off = hand == InteractionHand.MAIN_HAND;
        HumanoidArm arm = off ? mc.player.getMainArm() : mc.player.getMainArm().getOpposite();
        HumanoidArm opArm = off ? mc.player.getMainArm().getOpposite() : mc.player.getMainArm();
        boolean handFlg = arm == HumanoidArm.LEFT;
        float t = handFlg ? -1f : 1f;
        boolean slim = OEModelUtil.isSlimPlayerModel(mc.player);

        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);


        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);


       /* float hbpval = handFlg ? Mth.lerp(partialTicks, holdLeftOld, holdLeft) : Mth.lerp(partialTicks, holdRightOld, holdRight);
        var hbp = HAND_HOLD_MOTION.getPose(hbpval / 5f);
        if (handFlg)
            hbp = hbp.reverse();
        hbp.pose(poseStack);*/
        MotionDebug.getInstance().onDebug(poseStack, multiBufferSource, 0.5f);

        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, arm, packedLight);

        poseStack.pushPose();

        var obp = OP_HAND_BASE.getPose();
        if (handFlg)
            obp = obp.reverse();
        obp.pose(poseStack);

        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, opArm, packedLight);
        poseStack.popPose();

        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);

        var gbp = GUN_HOLD.getPose();
        if (handFlg)
            gbp = gbp.reverse();
        gbp.pose(poseStack);

      /*  float gbpval = handFlg ? Mth.lerp(partialTicks, holdLeftOld, holdLeft) : Mth.lerp(partialTicks, holdRightOld, holdRight);
        var gbp = GUN_HOLD_MOTION.getPose(gbpval / 5f);
        if (handFlg)
            gbp = gbp.reverse();
        gbp.pose(poseStack);*/



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
