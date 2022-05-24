package dev.felnull.iwasi.particles;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasi;
import dev.felnull.otyacraftengine.util.OERegistryUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class IWParticleTypes {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(IWasi.MODID, Registry.PARTICLE_TYPE_REGISTRY);
    public static final RegistrySupplier<SimpleParticleType> TEST = register("test", false);
    public static final RegistrySupplier<SimpleParticleType> BULLET_TRAJECTORY = register("bullet_trajectory", false);

    private static RegistrySupplier<SimpleParticleType> register(String name, boolean overrideLimiter) {
        return PARTICLE_TYPES.register(name, () -> OERegistryUtil.createSimpleParticleType(overrideLimiter));
    }

    public static void init() {
        PARTICLE_TYPES.register();
    }
}
