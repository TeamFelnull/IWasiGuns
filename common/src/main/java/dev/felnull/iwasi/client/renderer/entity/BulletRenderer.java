package dev.felnull.iwasi.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.model.IWModels;
import dev.felnull.iwasi.entity.bullet.Bullet;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.NotNull;

// https://github.com/TeamFelnull/IWasi/blob/b9c4e77f37fe20e341ca84a7a3c8d114ebddac78/common/src/main/java/dev/felnull/iwasi/client/renderer/entity/TestBulletRenderer.java
public class BulletRenderer extends EntityRenderer<Bullet> {

    protected BulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull Bullet entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        var vec = entity.getDeltaMovement();

        poseStack.pushPose();

        var model = OEModelUtil.getModel(IWModels.BULLET);
        var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());
        OERenderUtil.renderModel(poseStack, vc, model, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull Bullet entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
