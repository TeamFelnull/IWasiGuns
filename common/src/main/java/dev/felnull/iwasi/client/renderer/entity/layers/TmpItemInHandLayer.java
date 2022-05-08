package dev.felnull.iwasi.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TmpItemInHandLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public TmpItemInHandLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        if (!(entity instanceof Player player)) return;
        NonNullList<ItemStack> rightTempItems = IWPlayerData.getTmpHandItems(player, OEEntityUtil.getHandByArm(entity, HumanoidArm.RIGHT));
        NonNullList<ItemStack> leftTempItems = IWPlayerData.getTmpHandItems(player, OEEntityUtil.getHandByArm(entity, HumanoidArm.LEFT));

        if (!rightTempItems.isEmpty() || !leftTempItems.isEmpty()) {
            poseStack.pushPose();
            if (this.getParentModel().young) {
                poseStack.translate(0.0, 0.75, 0.0);
                poseStack.scale(0.5F, 0.5F, 0.5F);
            }

            this.renderArmWithItem(player, rightTempItems, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, i);
            this.renderArmWithItem(player, leftTempItems, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, multiBufferSource, i);
            poseStack.popPose();
        }
    }

    protected void renderArmWithItem(Player player, NonNullList<ItemStack> itemStacks, ItemTransforms.TransformType transformType, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (!itemStacks.isEmpty()) {
            poseStack.pushPose();
            ((ArmedModel)this.getParentModel()).translateToHand(humanoidArm, poseStack);
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            boolean bl = humanoidArm == HumanoidArm.LEFT;
            poseStack.translate((float)(bl ? -1 : 1) / 16.0F, 0.125, -0.625);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(player, itemStacks.get(0), transformType, bl, poseStack, multiBufferSource, i);
            poseStack.popPose();
        }
    }
}
