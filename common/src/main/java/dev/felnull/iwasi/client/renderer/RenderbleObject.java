package dev.felnull.iwasi.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.felnull.otyacraftengine.client.model.ModelHolder;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;

public interface RenderbleObject {
    static RenderbleObject of(ModelHolder modelHolder) {
        return (poseStack, vertexConsumer, combinedLight, combinedOverlay) -> OERenderUtils.renderModel(poseStack, vertexConsumer, modelHolder.get(), combinedLight, combinedOverlay);
    }

    void render(PoseStack poseStack, VertexConsumer vertexConsumer, int combinedLight, int combinedOverlay);
}
