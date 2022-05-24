package dev.felnull.iwasi.particles;

import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class BulletTrajectoryParticleOption implements ParticleOptions {
    public static final Codec<BulletTrajectoryParticleOption> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Vector3f.CODEC.fieldOf("move").forGetter((dustParticleOptions) -> {
            return dustParticleOptions.move;
        })).apply(instance, n -> {
            return new BulletTrajectoryParticleOption(null, n);
        });
    });


    private final ParticleType<BulletTrajectoryParticleOption> type;
    private final Vector3f move;

    public BulletTrajectoryParticleOption(ParticleType<BulletTrajectoryParticleOption> type, Vector3f move) {
        this.type = type;
        this.move = move;
    }

    @Override
    public ParticleType<?> getType() {
        return this.type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {

    }

    @Override
    public String writeToString() {
        ResourceLocation location = Registry.PARTICLE_TYPE.getKey(this.getType());
        return location.toString();
    }
}
