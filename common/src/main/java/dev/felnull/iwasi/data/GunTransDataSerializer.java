package dev.felnull.iwasi.data;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

public class GunTransDataSerializer implements EntityDataSerializer<GunTransData> {
    @Override
    public void write(@NotNull FriendlyByteBuf buf, @NotNull GunTransData data) {
        data.write(buf);
    }

    @Override
    public GunTransData read(@NotNull FriendlyByteBuf buf) {
        return GunTransData.read(buf);
    }

    @Override
    public GunTransData copy(@NotNull GunTransData data) {
        return data.copy();
    }
}
