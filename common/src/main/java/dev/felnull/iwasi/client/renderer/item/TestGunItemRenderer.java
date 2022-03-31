package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
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

public class TestGunItemRenderer implements BEWLItemRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final MotionPoint TEST_POINT = new MotionPoint(0.13750024f, -0.06268585f, -1.0531217f, -37.360847f, -190.0f, -42.375637f, 0.0f, 0.0f, 0.0f, false, false, false);

    private static final MotionPoint HAND_BASE = new MotionPoint(-0.16250037f, -0.05014864f, 0.48894945f, -77.9814f, 166.0f, 3.5103831f, 0.0f, 0.0f, 0.0f, false, false, false);
    private static final MotionPoint GUN_BASE = new MotionPoint(-0.06999999f, 0.6999998f, -0.06999999f, -90.0f, 180.0f, 0.0f, 0.0025001233f, -0.23499998f, 0.12737861f, false, false, false);

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

        poseStack.pushPose();
        OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);
        // TEST_POINT.getPose().pose(poseStack);
        // MotionDebug.getInstance().onDebug(poseStack, multiBufferSource, 0.5f);
        HAND_BASE.getPose().pose(poseStack);
        OERenderUtil.renderPlayerArmNoTransAndRot(poseStack, multiBufferSource, arm, packedLight);

        GUN_BASE.getPose().pose(poseStack);
        OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, stack, packedLight);


        poseStack.popPose();
    }

    public static void poseHand(HumanoidArm arm, HumanoidModel<? extends LivingEntity> model, ItemStack stack) {

    }
}
