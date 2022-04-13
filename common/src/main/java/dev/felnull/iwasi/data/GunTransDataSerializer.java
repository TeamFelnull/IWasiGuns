package dev.felnull.iwasi.data;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

public class GunTransDataSerializer implements EntityDataSerializer<GunPlayerTransData> {
    @Override
    public void write(@NotNull FriendlyByteBuf buf, @NotNull GunPlayerTransData data) {
        data.write(buf);
    }

    @Override
    public GunPlayerTransData read(@NotNull FriendlyByteBuf buf) {
        return GunPlayerTransData.read(buf);
    }

    @Override
    public GunPlayerTransData copy(@NotNull GunPlayerTransData data) {
        return data.copy();
    }
}
