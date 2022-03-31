package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.state.IWPlayerState;
import dev.felnull.otyacraftengine.client.debug.MotionDebug;
import dev.felnull.otyacraftengine.client.motion.MotionManager;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.motion.MotionSwapper;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TestGunItemRenderer implements BEWLItemRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final float SLIM_TRANS = 0.05f;

    private static final MotionPoint TEST_POINT = new MotionPoint(0.13750024f, -0.06268585f, -1.0531217f, -37.360847f, -190.0f, -42.375637f, 0.0f, 0.0f, 0.0f, false, false, false);

    private static final MotionPoint HAND_BASE = new MotionPoint(-0.16250037f, -0.05014864f, 0.48894945f, -77.9814f, 166.0f, 3.5103831f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint HAND_HOLD = new MotionPoint(-0.62600005f, 0.27449983f, 0.79300016f, -90.84003f, 179.97002f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false);

    private static final MotionPoint GUN_BASE = new MotionPoint(-0.06999999f, 0.6999998f, -0.06999999f, -90.0f, 180.0f, 0.0f, 0.0025001233f, -0.23499998f, 0.12737861f, false, false, false);

    private static final ResourceLocation TEST_HOLD_MOTION = new ResourceLocation(IWasi.MODID, "test_hold");
    private static final MotionSwapper HOLDING_SWAP = MotionSwapper.swapStartAndEnd(HAND_BASE, HAND_HOLD);

    private static int holdRight;
    private static int holdLeft;
    private static int holdRightOld;
    private static int holdLeftOld;

    //-0.62600005f, 0.27449983f, 0.79300016f, -90.84003f, 179.97002f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false
//-0.57600003f, 0.27449983f, 0.79300016f, -90.84003f, 179.97002f, 0.0f, 0.0f, 0.0f, 0.0f, false, false, false

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float f, int light, int overlay) {
        var model = OEModelUtil.getModel(IWModels.TEST_GUN);
        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());
        //  MotionDebug.getInstance().onDebug(poseStack, multiBufferSource, 0.5f);
        OERenderUtil.renderModel(poseStack, vc, model, light, overlay);
    }

    public static void renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        boolean off = hand == InteractionHand.MAIN_HAND;
        HumanoidArm arm = off ? mc.player.getMainArm() : mc.player.getMainArm().getOpposite();
        boolean handFlg = arm == HumanoidArm.LEFT;
        float t = handFlg ? -1f : 1f;
        boolean slim = isSlimModel(mc.player);

        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);

        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);


        var th = MotionManager.getInstance().getMotion(TEST_HOLD_MOTION);
        float val = handFlg ? Mth.lerp(partialTicks, holdLeftOld, holdLeft) : Mth.lerp(partialTicks, holdRightOld, holdRight);
        var hbp = th.getPose(val / 5f);
        if (handFlg)
            hbp = hbp.reverse();
        hbp.pose(poseStack);

        MotionDebug.getInstance().onDebug(poseStack, multiBufferSource, 0.5f);
        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, arm, packedLight);

        if (slim)
            poseStack.translate(t * SLIM_TRANS, 0, 0);

        var gbp = GUN_BASE.getPose();
        if (handFlg)
            gbp = gbp.reverse();
        gbp.pose(poseStack);
        OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, stack, packedLight);

        poseStack.popPose();
    }

    public static boolean isSlimModel(AbstractClientPlayer player) {
        var pl = player.getModelName();
        return "slim".equals(pl);
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
