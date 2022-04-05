package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.client.motion.gun.Glock17GunMotion;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class Glock17GunRenderer implements IGunRenderer<Glock17GunMotion> {
    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        var main = OEModelUtil.getModel(IWModels.GLOCK_17_MAIN);
        var slide = OEModelUtil.getModel(IWModels.GLOCK_17_SLIDE);
        var magazine = OEModelUtil.getModel(IWModels.GLOCK_17_MAGAZINE);

        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        poseStack.pushPose();

        OERenderUtil.poseTrans16(poseStack, -6.35, -2.0351, -0.8561);
        OERenderUtil.poseRotateY(poseStack, 180);
        poseStack.translate(-1f, 0f, -1f);

        OERenderUtil.poseTrans16(poseStack, 1.35 / 2f, 0, 0);

        OERenderUtil.renderModel(poseStack, vc, main, light, overlay);

        poseStack.pushPose();
        float sv = 0;// IWPlayerState.isPullTrigger(mc.player) ? Math.abs(-0.5f + OERenderUtil.getParSecond(100)) * 2f : 0;
        OERenderUtil.poseTrans16(poseStack, 0, 0, 1.625f * sv);
        OERenderUtil.renderModel(poseStack, vc, slide, light, overlay);
        poseStack.popPose();

        OERenderUtil.renderModel(poseStack, vc, magazine, light, overlay);

        poseStack.popPose();
    }
}
