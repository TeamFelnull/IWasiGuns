package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.felnull.iwasi.client.model.gun.GunModel;
import dev.felnull.iwasi.gun.Gun;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public abstract class GunRenderer<T extends Gun, M extends GunModel<T>> {
    protected final M gunModel;

    public GunRenderer(M gunModel) {
        this.gunModel = gunModel;
    }


    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        this.gunModel.prepare(stack);
        this.gunModel.setupAnim(stack);

        //Debug only
        this.gunModel.renderShape(poseStack, multiBufferSource);

        this.gunModel.renderToBuffer(poseStack, getVertexConsumer(multiBufferSource, stack), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    protected VertexConsumer getVertexConsumer(MultiBufferSource multiBufferSource, ItemStack stack) {
        return ItemRenderer.getFoilBufferDirect(multiBufferSource, Sheets.cutoutBlockSheet(), true, stack.hasFoil());
    }
}
