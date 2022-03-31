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
        var rs = entity.getCurrentRigidState();//RigidState.lerp(g, entity.getOldRigidState(), entity.getCurrentRigidState());
        if (rs != null) {
            var r = rs.rotation().toPYRVec3(true);
            OERenderUtil.poseRotateX(poseStack, r.x());
            OERenderUtil.poseRotateY(poseStack, r.y());
            // System.out.println(r.y());
            // var q = new Quaternion((float) r.x(), (float) r.y(), (float) r.z(), (float) r.w());
            // System.out.println(PhysicsUtil.wrapZeroDegrees(Minecraft.getInstance().player.getXRot()) + " : " + PhysicsUtil.wrapZeroDegrees(Minecraft.getInstance().player.getYRot()));

            //   poseStack.mulPose(q);
        }
       /* if (entity.getCurrentRigidState() != null && entity.getOldRigidState() != null) {
            var rs = entity.getCurrentRigidState().rotation().toQuaternion();
            var ors = entity.getOldRigidState().rotation().toQuaternion();

            //var vr = rs.toXYZDegrees();
            //var ovr = ors.toXYZDegrees();

            var fv = new FNVec4f(rs.i(), rs.j(), rs.k(), rs.r()).toEulerAngle().degrees();

            OERenderUtil.poseRotateAll(poseStack, fv.getX(), fv.getY(), fv.getZ());

//            OERenderUtil.poseRotateAll(poseStack, Mth.lerp(f, ovr.x(), vr.x()), Mth.lerp(f, ovr.y(), vr.y()), Mth.lerp(f, ovr.z(), vr.z()));
        }*/
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.IRON_BLOCK), ItemTransforms.TransformType.FIXED, i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, entity.getId());
        poseStack.popPose();
        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull TestBullet entity) {
        return TRIDENT_LOCATION;
    }
}
