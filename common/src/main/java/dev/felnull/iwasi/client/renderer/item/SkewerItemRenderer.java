package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class SkewerItemRenderer implements BEWLItemRenderer {
    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
     /*   var model = IWGModels.SKEWER.get();
        var vc = ItemRenderer.getFoilBufferDirect(multiBufferSource, Sheets.cutoutBlockSheet(), true, stack.hasFoil());
        OERenderUtils.renderModel(poseStack, vc, model, light, overlay);*/
    }
}
