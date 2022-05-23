package dev.felnull.iwasi.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class TestParticle extends RisingParticle {

    protected TestParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
        super(clientLevel, d, e, f, g, h, i);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class TestParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public TestParticleProvider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            TestParticle particle = new TestParticle(clientLevel, d, e, f, g, h, i);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}
