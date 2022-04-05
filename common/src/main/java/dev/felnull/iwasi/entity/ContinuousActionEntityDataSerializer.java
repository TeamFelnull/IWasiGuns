package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.data.ContinuousActionData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

public class ContinuousActionEntityDataSerializer implements EntityDataSerializer<ContinuousActionData> {
    @Override
    public void write(@NotNull FriendlyByteBuf buf, @NotNull ContinuousActionData data) {
        data.write(buf);
    }

    @Override
    public ContinuousActionData read(@NotNull FriendlyByteBuf buf) {
        return ContinuousActionData.read(buf);
    }

    @Override
    public ContinuousActionData copy(@NotNull ContinuousActionData data) {
        return data.copy();
    }
}
