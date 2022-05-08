package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;

public interface IMagazineRenderer {
    void renderMagazine(ItemStack stack, PoseStack poseStack, MultiBufferSource ms, float delta, int light, int overlay);
}
