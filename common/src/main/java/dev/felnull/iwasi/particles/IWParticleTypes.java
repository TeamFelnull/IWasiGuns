package dev.felnull.iwasi.particles;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasi;
import dev.felnull.otyacraftengine.util.OERegistryUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Function;

public class IWParticleTypes {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(IWasi.MODID, Registry.PARTICLE_TYPE_REGISTRY);
    public static final RegistrySupplier<SimpleParticleType> TEST = register("test", false);
    public static final RegistrySupplier<ParticleType<BulletTrajectoryParticleOption>> BULLET_TRAJECTORY = register("bullet_trajectory", BulletTrajectoryParticleOption.DESERIALIZER, n -> BulletTrajectoryParticleOption.CODEC);

    private static <T extends ParticleOptions> RegistrySupplier<ParticleType<T>> register(String name, ParticleOptions.Deserializer<T> deserializer, Function<ParticleType<T>, Codec<T>> codec) {
        return PARTICLE_TYPES.register(name, () -> new ParticleType<T>(false, deserializer) {
            public Codec<T> codec() {
                return codec.apply(this);
            }
        });
    }

    private static RegistrySupplier<SimpleParticleType> register(String name, boolean overrideLimiter) {
        return PARTICLE_TYPES.register(name, () -> OERegistryUtil.createSimpleParticleType(overrideLimiter));
    }

    public static void init() {
        PARTICLE_TYPES.register();
    }
}
