package dev.felnull.iwasi.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin {
    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (!(livingEntity instanceof Player) || !IWItemUtil.isKnife(itemStack) || OEEntityUtil.getHandByArm(livingEntity, humanoidArm) == InteractionHand.MAIN_HAND)
            return;
        var gun = IWItemUtil.getGunNullable(livingEntity.getItemInHand(OEEntityUtil.getHandByArm(livingEntity, humanoidArm.getOpposite())));
        if (gun == null || !gun.getType().canHaveWithKnife()) return;

        var renderer = (LivingEntityRenderer<? extends LivingEntity, ?>) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        var model = renderer.getModel();

        if (model instanceof HumanoidModel<? extends LivingEntity> humanoidModel) {
            var armPart = humanoidArm == HumanoidArm.RIGHT ? humanoidModel.rightArm : humanoidModel.leftArm;
            var cubes = armPart.cubes;
            if (cubes != null && cubes.size() == 1) {
                var cube = cubes.get(0);
                float sizeZ = cube.maxZ - cube.minZ;
                OERenderUtil.poseTrans16(poseStack, 0, -sizeZ, 0);
                OERenderUtil.poseRotateZ(poseStack, 180f);
            }
        }
    }
}
