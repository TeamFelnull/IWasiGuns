package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.motion.gun.GunMotion;
import dev.felnull.iwasi.client.motion.gun.GunMotionRegister;
import dev.felnull.iwasi.client.renderer.gun.GunRenderer;
import dev.felnull.iwasi.client.renderer.gun.GunRendererRegister;
import dev.felnull.iwasi.gun.Gun;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GunItemRenderer implements BEWLItemRenderer {
    private static final float guiSize = 1f - 1f / 6f;
    public static final Map<Gun, GunItemRenderer> GUN_ITEM_RENDERERS = new HashMap<>();
    protected final Gun gun;

    public GunItemRenderer(Gun gun) {
        GUN_ITEM_RENDERERS.put(gun, this);
        this.gun = gun;
    }

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float f, int light, int overlay) {
        poseStack.pushPose();
        if (transformType == ItemTransforms.TransformType.GUI) {
            var size = gun.getSize();
            float sc;
            float xs = 0;
            float ys = 0;
            if (size.z() > size.y()) {
                sc = guiSize / size.z();
                ys = (size.y() / 2f) / sc;
            } else {
                sc = guiSize / size.y();
                xs = (size.z() / 2f) / sc;
            }
            poseStack.translate((1f - 1f / 12f) - xs, (1f / 12f) + ys, 0f);
            OERenderUtil.poseScaleAll(poseStack, sc);
            OERenderUtil.poseRotateY(poseStack, -90);
        }
        getGunRenderer().render(stack, transformType, poseStack, multiBufferSource, f, light, overlay);
        poseStack.popPose();
    }


    public void renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        getGunRenderer().renderHand(getGunMotion(), poseStack, multiBufferSource, hand, packedLight, partialTicks, interpolatedPitch, swingProgress, equipProgress, stack);
    }

    public void poseHand(HumanoidArm arm, HumanoidModel<? extends LivingEntity> model, ItemStack stack, LivingEntity livingEntity) {

    }

    private GunRenderer getGunRenderer() {
        return GunRendererRegister.getRenderer(gun);
    }

    private GunMotion getGunMotion() {
        return GunMotionRegister.getMotion(gun);
    }
}
