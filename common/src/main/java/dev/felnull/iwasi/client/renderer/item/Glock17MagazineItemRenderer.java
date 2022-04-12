package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class Glock17MagazineItemRenderer implements BEWLItemRenderer {
    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float f, int light, int overlay) {
        poseStack.pushPose();
        var model = OEModelUtil.getModel(IWModels.GLOCK_17_MAGAZINE);
        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        OERenderUtil.poseTrans16(poseStack, -6.35, -2.0351, -0.8561);
        OERenderUtil.poseRotateY(poseStack, 180);
        poseStack.translate(-1f, 0f, -1f);
        OERenderUtil.poseTrans16(poseStack, 1.35 / 2f, 0, 0);
        OERenderUtil.poseTrans16(poseStack, -0.1, 0.025, 0.075);

        OERenderUtil.renderModel(poseStack, vc, model, light, overlay);
        poseStack.popPose();
        
    }
}
