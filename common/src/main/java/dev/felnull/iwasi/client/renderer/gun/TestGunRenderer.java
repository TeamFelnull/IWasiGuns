package dev.felnull.iwasi.client.renderer.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.motion.gun.TestGunMotion;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class TestGunRenderer extends GunRenderer<TestGunMotion> {
    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {

    }
}
