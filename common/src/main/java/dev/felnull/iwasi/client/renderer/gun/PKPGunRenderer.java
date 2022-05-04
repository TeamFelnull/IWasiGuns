package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class PKPGunRenderer extends Glock17GunRenderer {
    @Override
    public void render(ItemStack stack, ItemStack stackOld, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        var main = OEModelUtil.getModel(IWModels.PKP_MAIN);
        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        poseStack.pushPose();
        OERenderUtil.renderModel(poseStack, vc, OEModelUtil.getModel(IWModels.ORIGIN), light, overlay);

        OERenderUtil.poseRotateY(poseStack, 180f);
        poseStack.translate(-1f, 0f, -1f);
        OERenderUtil.renderModel(poseStack, vc, main, light, overlay);
        poseStack.popPose();
    }
}
