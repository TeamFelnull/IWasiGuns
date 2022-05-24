package dev.felnull.iwasi.client.particles;

import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.particles.IWParticleTypes;
import dev.felnull.otyacraftengine.client.entrypoint.ParticleRegister;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class IWClientParticles {
    public static void init(ParticleRegister register) {
        register.register(IWParticleTypes.TEST.get(), TestParticle.TestParticleProvider::new);
        // register.register(IWParticleTypes.BULLET_TRAJECTORY.get(), BulletTrajectoryParticle.Provider::new);
    }

    public static void addSprite(Consumer<ResourceLocation> spriteAdder) {
        spriteAdder.accept(new ResourceLocation(IWasi.MODID, "particle/test"));
    }
}
