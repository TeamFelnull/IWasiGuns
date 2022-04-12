package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.client.motion.gun.TestGunMotion;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class TestGunRenderer extends GunRenderer<TestGunMotion> {
    @Override
    public void render(ItemStack stack, ItemStack stackOld, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        var model = OEModelUtil.getModel(IWModels.TEST_GUN);
        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());
        poseStack.pushPose();
        OERenderUtil.poseTrans16(poseStack, -7.375, -3.575, -4.875);
        OERenderUtil.poseRotateY(poseStack, 180);
        poseStack.translate(-1f, 0f, -1f);
        OERenderUtil.poseTrans16(poseStack, 1.25f / 2f, 0, 0);
        OERenderUtil.renderModel(poseStack, vc, model, light, overlay);
        poseStack.popPose();
    }
}
