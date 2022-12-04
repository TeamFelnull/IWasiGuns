package dev.felnull.iwasi.client.model.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.felnull.fnjl.util.FNForUtil;
import dev.felnull.iwasi.client.renderer.RenderbleObject;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.util.HashMap;
import java.util.Map;

public class GunModelPart {
    private final RenderbleObject renderbleObject;
    private final Vector3f size;
    private final Vector3f position;
    private final Vector3f origin;
    private final Vector3f rotation = new Vector3f();
    private final Map<String, GunModelPart> children = new HashMap<>();

    public GunModelPart(RenderbleObject renderbleObject, float sizeX, float sizeY, float sizeZ, float posX, float posY, float posZ, float oriX, float oriY, float oriZ) {
        this(renderbleObject, new Vector3f(sizeX, sizeY, sizeZ), new Vector3f(posX, posY, posZ), new Vector3f(oriX, oriY, oriZ));
    }

    public GunModelPart(RenderbleObject renderbleObject, Vector3f size, Vector3f position, Vector3f origin) {
        this.renderbleObject = renderbleObject;
        this.size = size;
        this.position = position;
        this.origin = origin;
    }

    public void addChildren(String id, GunModelPart gunModelPart) {
        this.children.put(id, gunModelPart);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        poseStack.translate(position.x(), position.y(), position.z());
        OERenderUtils.poseCenterConsumer(poseStack, origin.x(), origin.y(), origin.z(), (pose) -> OERenderUtils.poseRotateAll(poseStack, rotation.x(), rotation.y(), rotation.z()));

        this.renderbleObject.render(poseStack, vertexConsumer, combinedLight, combinedOverlay);

        for (GunModelPart part : children.values()) {
            part.render(poseStack, vertexConsumer, combinedLight, combinedOverlay);
        }

        poseStack.popPose();
    }

    public void renderShape(PoseStack poseStack, MultiBufferSource multiBufferSource) {
        var vc = multiBufferSource.getBuffer(RenderType.lines());
        float onePixel = 1f / 16f;

        poseStack.pushPose();
        poseStack.translate(position.x(), position.y(), position.z());

        OERenderUtils.poseCenterConsumer(poseStack, origin.x(), origin.y(), origin.z(), (pose) -> {
            OERenderUtils.poseRotateAll(poseStack, rotation.x(), rotation.y(), rotation.z());

            renderLine(poseStack, vc, -onePixel, 0, 0, onePixel, 0, 0, 0xFF0000FF);
            renderLine(poseStack, vc, 0, -onePixel, 0, 0, onePixel, 0, 0xFF0000FF);
            renderLine(poseStack, vc, 0, 0, -onePixel, 0, 0, onePixel, 0xFF0000FF);
        });

        FNForUtil.forBoxEdge(0, 0, 0, size.x(), size.y(), size.z(), ret -> {
            var st = ret.getLeft();
            var en = ret.getRight();
            renderLine(poseStack, vc, st.getX(), st.getY(), st.getZ(), en.getX(), en.getY(), en.getZ(), 0xFFFF0000);
        });

        for (GunModelPart part : children.values()) {
            part.renderShape(poseStack, multiBufferSource);
        }

        poseStack.popPose();
    }

    private static void renderLine(PoseStack poseStack, VertexConsumer vertexConsumer, float stX, float stY, float stZ, float enX, float enY, float enZ, int color) {
        float nx = enX - stX;
        float ny = enY - stY;
        float nz = enZ - stZ;
        float t = Mth.sqrt(nx * nx + ny * ny + nz * nz);
        nx /= t;
        ny /= t;
        nz /= t;

        float r = (float) FastColor.ARGB32.red(color) / 255f;
        float g = (float) FastColor.ARGB32.green(color) / 255f;
        float b = (float) FastColor.ARGB32.blue(color) / 255f;
        float a = (float) FastColor.ARGB32.alpha(color) / 255f;

        var pose = poseStack.last();
        vertexConsumer.vertex(pose.pose(), stX, stY, stZ).color(r, g, b, a).normal(pose.normal(), nx, ny, nz).endVertex();
        vertexConsumer.vertex(pose.pose(), enX, enY, enZ).color(r, g, b, a).normal(pose.normal(), nx, ny, nz).endVertex();
    }
}
