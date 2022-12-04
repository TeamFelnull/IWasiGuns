package dev.felnull.iwasi.client.model.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.felnull.iwasi.gun.Gun;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.item.ItemStack;

public abstract class GunModel<T extends Gun> extends Model {
    protected final GunModelPart rootPart;

    public GunModel(GunModelPart rootPart) {
        super(loc -> Sheets.cutoutBlockSheet());
        this.rootPart = rootPart;
    }

    public void prepare(ItemStack stack) {
    }

    public abstract void setupAnim(ItemStack stack);

    public void renderShape(PoseStack poseStack, MultiBufferSource multiBufferSource) {
        this.rootPart.renderShape(poseStack, multiBufferSource);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        this.rootPart.render(poseStack, vertexConsumer, i, j);
    }
}
