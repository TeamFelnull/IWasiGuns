package dev.felnull.iwasi.client.particles;

import dev.felnull.iwasi.particles.IWParticleTypes;
import dev.felnull.otyacraftengine.client.entrypoint.ParticleRegister;

public class IWClientParticles {
    public static void init(ParticleRegister register) {
        register.register(IWParticleTypes.TEST.get(), TestParticle.TestParticleProvider::new);
    }
}
