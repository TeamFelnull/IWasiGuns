package dev.felnull.iwasi.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.particles.BulletTrajectoryParticleOption;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BulletTrajectoryParticle extends Particle {
    private static final ResourceLocation BULLET_LOCATION = new ResourceLocation(IWasi.MODID, "textures/entity/bullet/bullet.png");
    private final Model model;
    private final RenderType renderType;

    protected BulletTrajectoryParticle(BulletTrajectoryParticleOption particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
        super(clientLevel, d, e, f, g, h, i);
        this.renderType = RenderType.entityTranslucent(ElderGuardianRenderer.GUARDIAN_ELDER_LOCATION);
        this.model = new GuardianModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.ELDER_GUARDIAN));
        this.gravity = 0.03F;
        this.lifetime = 1;
        var mv = particleOptions.getMove();
        this.xd = mv.x();
        this.yd = mv.y();
        this.zd = mv.z();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
        Vec3 cpos = camera.getPosition();
        float xpos = (float) (Mth.lerp(f, this.xo, this.x) - cpos.x());
        float ypos = (float) (Mth.lerp(f, this.yo, this.y) - cpos.y());
        float zpos = (float) (Mth.lerp(f, this.zo, this.z) - cpos.z());
        Quaternion quaternion = new Quaternion(0f, OERenderUtil.getParSecond(3000) * 360f, 0, true);// camera.rotation();

        /*MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vec = bufferSource.getBuffer(Sheets.cutoutBlockSheet());
        vertex(vec, xpos, ypos, zpos, quaternion, f);
        bufferSource.endBatch();*/

        //  Vector3f[] vector3fs = new Vector3f[]{
        //         new Vector3f(-1.0F, /*-1.0F*/1f / 16f * 3f, 0.0F),
        //          new Vector3f(-1.0F, 1.0F, 0.0F),
        //          new Vector3f(1.0F, 1.0F, 0.0F),
        //          new Vector3f(1.0F, /*-1.0F*/1f / 16f * 3f, 0.0F)};
       /* float size = 1f;

        for (int l = 0; l < 4; ++l) {
            Vector3f vector3f2 = vector3fs[l];
            vector3f2.transform(quaternion);
            vector3f2.mul(size);
            vector3f2.add(xpos, ypos, zpos);
        }

        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV0() + (sprite.getV1() - sprite.getV0()) / 16f * 3f;

        int light = this.getLightColor(f);
        vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();*/

       /* Vec3 cpos = camera.getPosition();
        float px = (float) (Mth.lerp(f, this.xo, this.x) - cpos.x());
        float py = (float) (Mth.lerp(f, this.yo, this.y) - cpos.y());
        float pz = (float) (Mth.lerp(f, this.zo, this.z) - cpos.z());

        float g = ((float) this.age + f) / (float) this.lifetime;
        float h = 0.05F + 0.5F * Mth.sin(g * 3.1415927F);
        PoseStack poseStack = new PoseStack();
        //poseStack.mulPose(camera.rotation());
        poseStack.translate(px, py, pz);
        //   poseStack.mulPose(Vector3f.XP.rotationDegrees(150.0F * g - 60.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0, -1.1009999513626099, 1.5);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer2 = bufferSource.getBuffer(Sheets.cutoutBlockSheet());
        // this.model.renderToBuffer(poseStack, vertexConsumer2, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, h);

        int q = this.getLightColor(f);
        var model = OEModelUtil.getModel(Blocks.DIAMOND_BLOCK.defaultBlockState());
        OERenderUtil.renderModel(poseStack, vertexConsumer2, model, q, OverlayTexture.NO_OVERLAY);

        bufferSource.endBatch();*/
    }

    private void vertex(VertexConsumer vertexConsumer, float x, float y, float z, Quaternion quaternion, float f) {
        Vector3f[] base = new Vector3f[]{
                new Vector3f(-1.0F, 1f / 16f * 3f, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1f / 16f * 3f, 0.0F)};
        for (int l = 0; l < 4; ++l) {
            Vector3f vector3f2 = base[l];
            vector3f2.transform(quaternion);
            vector3f2.add(x, y, z);
        }

     /*   float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV0() + (sprite.getV1() - sprite.getV0()) / 16f * 3f;

        int light = this.getLightColor(f);
        vertexConsumer.vertex(base[0].x(), base[0].y(), base[0].z()).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(base[1].x(), base[1].y(), base[1].z()).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(base[2].x(), base[2].y(), base[2].z()).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(base[3].x(), base[3].y(), base[3].z()).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();*/
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    public static class Provider implements ParticleProvider<BulletTrajectoryParticleOption> {
        @Nullable
        @Override
        public Particle createParticle(BulletTrajectoryParticleOption particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new BulletTrajectoryParticle(particleOptions, clientLevel, d, e, f, g, h, i);
        }
    }
}
