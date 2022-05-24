package dev.felnull.iwasi.client.particles;

import dev.felnull.iwasi.particles.BulletTrajectoryParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.Nullable;

public class BulletTrajectoryParticle extends RisingParticle {

    protected BulletTrajectoryParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
        super(clientLevel, d, e, f, g, h, i);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<BulletTrajectoryParticleOption> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(BulletTrajectoryParticleOption particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new BulletTrajectoryParticle(clientLevel, d, e, f, g, h, i);
        }
    }
}
