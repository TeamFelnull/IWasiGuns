package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWGModels;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public class TestGlock17ItemRenderer implements BEWLItemRenderer {
    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        var model = IWGModels.GLOCK_17_MAIN.get();
        var vc = ItemRenderer.getFoilBufferDirect(multiBufferSource, Sheets.cutoutBlockSheet(), true, stack.hasFoil());
        OERenderUtils.renderModel(poseStack, vc, model, light, overlay);
    }
}
