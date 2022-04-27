package dev.felnull.iwasi.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.data.IEntityHandRenderItem;
import dev.felnull.iwasi.client.entity.IClientItemHandRenderEntity;
import dev.felnull.iwasi.client.motion.gun.GunMotion;
import dev.felnull.iwasi.client.motion.gun.GunMotionRegister;
import dev.felnull.iwasi.client.renderer.gun.GunRenderer;
import dev.felnull.iwasi.client.renderer.gun.GunRendererRegister;
import dev.felnull.iwasi.gun.Gun;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
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
        ItemStack stackOld = ItemStack.EMPTY;
        var arm = getArmByTransform(transformType);
        var entity = ((IEntityHandRenderItem) (Object) stack).getRenderEntity();
        if (arm != null && entity != null) {
            IClientItemHandRenderEntity renderEntity = (IClientItemHandRenderEntity) entity;
            var hand = OEEntityUtil.getHandByArm(entity, arm);
            var item = renderEntity.getLastHandItem(hand);
            var itemOld = renderEntity.getLastHandItemOld(hand);
            if (!item.isEmpty() && !itemOld.isEmpty()) {
                stackOld = itemOld;
                stack = item;
            }
        }

        getGunRenderer().render(stack, stackOld, transformType, poseStack, multiBufferSource, f, light, overlay);
        poseStack.popPose();
    }


    public void renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        getGunRenderer().renderHand(getGunMotion(), poseStack, multiBufferSource, hand, packedLight, partialTicks, interpolatedPitch, swingProgress, equipProgress, stack);
    }

    public void renderArmWithItem(ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>> layer, LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm arm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, float delta) {
        getGunRenderer().renderArmWithItem(getGunMotion(), layer, livingEntity, itemStack, transformType, arm, poseStack, multiBufferSource, i, delta);
    }

    public void poseHand(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, ItemStack stack, LivingEntity livingEntity) {
        getGunRenderer().poseArm(getGunMotion(), hand, arm, model, stack, livingEntity);
    }

    private GunRenderer getGunRenderer() {
        return GunRendererRegister.getRenderer(gun);
    }

    private GunMotion getGunMotion() {
        return GunMotionRegister.getMotion(gun);
    }

    private static HumanoidArm getArmByTransform(ItemTransforms.TransformType transformType) {
        return switch (transformType) {
            case FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> HumanoidArm.LEFT;
            case FIRST_PERSON_RIGHT_HAND, THIRD_PERSON_RIGHT_HAND -> HumanoidArm.RIGHT;
            default -> null;
        };
    }
}
