package dev.felnull.iwasi.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.entity.TestBullet;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class TestBulletRenderer extends EntityRenderer<TestBullet> {
    public static final ResourceLocation TRIDENT_LOCATION = new ResourceLocation("textures/entity/trident.png");

    protected TestBulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TestBullet entity, float f, float g, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        OERenderUtil.poseRotateAll(poseStack, Mth.lerp(g, entity.xRotO, entity.getXRot()), Mth.lerp(g, entity.yRotO, entity.getYRot()), Mth.lerp(g, entity.zRot0, entity.getZRot()));
        //  poseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(g, entity.xRotO, entity.getXRot()) + 90.0F));
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.IRON_BLOCK), ItemTransforms.TransformType.FIXED, i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, entity.getId());
        poseStack.popPose();
        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull TestBullet entity) {
        return TRIDENT_LOCATION;
    }
}
