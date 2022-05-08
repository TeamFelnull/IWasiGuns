package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.client.motion.gun.Glock17GunMotion;
import dev.felnull.iwasi.client.renderer.item.Glock17MagazineItemRenderer;
import dev.felnull.iwasi.gun.trans.item.IWGunItemTrans;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class Glock17GunRenderer extends GunRenderer<Glock17GunMotion> {
    @Override
    public void render(ItemStack stack, ItemStack stackOld, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        var main = OEModelUtil.getModel(IWModels.GLOCK_17_MAIN);
        var slide = OEModelUtil.getModel(IWModels.GLOCK_17_SLIDE);

        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        poseStack.pushPose();

        poseStack.pushPose();
        OERenderUtil.poseTrans16(poseStack, -6.35, -2.0351, -0.8561);
        OERenderUtil.poseRotateY(poseStack, 180);
        poseStack.translate(-1f, 0f, -1f);
        OERenderUtil.poseTrans16(poseStack, 1.35f / 2f, 0, 0);
        OERenderUtil.renderModel(poseStack, vc, main, light, overlay);

        poseStack.pushPose();
        float sv = IWItemUtil.isSlideDown(stack) ? 1f : 0f;
        float rs = getSlide(stack, stackOld, delta);
        if (rs >= 0)
            sv = rs;

        OERenderUtil.poseTrans16(poseStack, 0, 0, 1.625f * sv);
        OERenderUtil.renderModel(poseStack, vc, slide, light, overlay);
        poseStack.popPose();

        poseStack.popPose();

        var magazineStack = GunItem.getMagazine(stack);
        if (!magazineStack.isEmpty()) {
            poseStack.pushPose();
            OERenderUtil.poseTrans16(poseStack, -0.1f, -0.45, -0.15f);
            renderMagazine(magazineStack, poseStack, multiBufferSource, delta, light, overlay);
            poseStack.popPose();
        }

        poseStack.popPose();
    }

    private static final Glock17MagazineItemRenderer renderer = new Glock17MagazineItemRenderer();

    @Override
    void renderMagazine(ItemStack stack, PoseStack poseStack, MultiBufferSource ms, float delta, int light, int overlay) {
        renderer.renderMagazine(stack, poseStack, ms, delta, light, overlay);
    }

    private float getSlide(ItemStack stack, ItemStack oldStack, float delta) {
        var delt = getGunItemTrans(IWGunItemTrans.GLOCK_17_SLIDE_RECOIL, stack, oldStack, delta);
        if (delt == null) {
            delt = getGunItemTrans(IWGunItemTrans.GLOCK_17_SLIDE_REVERS, stack, oldStack, delta);
            if (delt == null)
                return -1f;
            return 1f - delt.progress() / (IWGunItemTrans.GLOCK_17_SLIDE_REVERS.getProgress(stack, 0) - 1f);
        }
        float ret;
        if (delt.step() == 0)
            ret = delt.progress() / (IWGunItemTrans.GLOCK_17_SLIDE_RECOIL.getProgress(stack, 0) - 1f);
        else
            ret = 1f - (delt.progress() / (IWGunItemTrans.GLOCK_17_SLIDE_RECOIL.getProgress(stack, delt.step()) - 1f));
        return Mth.clamp(ret, 0f, 1f);
    }

}
