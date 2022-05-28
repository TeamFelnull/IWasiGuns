package dev.felnull.iwasi.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public class BulletTrajectoryParticleOption implements ParticleOptions {
    public static final Codec<BulletTrajectoryParticleOption> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Vector3f.CODEC.fieldOf("move").forGetter((option) -> option.move), Codec.FLOAT.fieldOf("scale").forGetter((option) -> option.scale)).apply(instance, BulletTrajectoryParticleOption::new));
    public static final ParticleOptions.Deserializer<BulletTrajectoryParticleOption> DESERIALIZER = new Deserializer<>() {
        @Override
        public BulletTrajectoryParticleOption fromCommand(ParticleType<BulletTrajectoryParticleOption> particleType, StringReader stringReader) throws CommandSyntaxException {
            Vector3f vector3f = DustParticleOptionsBase.readVector3f(stringReader);
            stringReader.expect(' ');
            float f = stringReader.readFloat();
            return new BulletTrajectoryParticleOption(vector3f, f);
        }

        @Override
        public BulletTrajectoryParticleOption fromNetwork(ParticleType<BulletTrajectoryParticleOption> particleType, FriendlyByteBuf friendlyByteBuf) {
            return new BulletTrajectoryParticleOption(DustParticleOptionsBase.readVector3f(friendlyByteBuf), friendlyByteBuf.readFloat());
        }
    };

    private final Vector3f move;
    private final float scale;

    public BulletTrajectoryParticleOption(Vector3f move, float scale) {
        this.move = move;
        this.scale = scale;
    }

    @Override
    public ParticleType<?> getType() {
        return IWParticleTypes.BULLET_TRAJECTORY.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.move.x());
        buf.writeFloat(this.move.y());
        buf.writeFloat(this.move.z());
        buf.writeFloat(this.scale);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.move.x(), this.move.y(), this.move.z(), this.scale);
    }

    public float getScale() {
        return scale;
    }

    public Vector3f getMove() {
        return move;
    }
}
