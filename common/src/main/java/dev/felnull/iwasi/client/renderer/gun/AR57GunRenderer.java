package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.client.motion.gun.AR57GunMotion;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class AR57GunRenderer extends GunRenderer<AR57GunMotion> {
    @Override
    public void render(ItemStack stack, ItemStack stackOld, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        var main = OEModelUtil.getModel(IWModels.AR_57_MAIN);
        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        poseStack.pushPose();
        OERenderUtil.renderModel(poseStack, vc, OEModelUtil.getModel(IWModels.ORIGIN), light, overlay);

        OERenderUtil.poseRotateY(poseStack, 90);
        poseStack.translate(-1f, 0f, 0);
        OERenderUtil.renderModel(poseStack, vc, main, light, overlay);
        poseStack.popPose();
    }

    @Override
    void renderMagazine(ItemStack stack, PoseStack poseStack, MultiBufferSource ms, float delta, int light, int overlay) {

    }
}
