package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.data.HoldType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

public class HoldTypeEntityDataSerializer implements EntityDataSerializer<HoldType> {
    @Override
    public void write(@NotNull FriendlyByteBuf buf, @NotNull HoldType holdType) {
        buf.writeEnum(holdType);
    }

    @Override
    public HoldType read(@NotNull FriendlyByteBuf buf) {
        return buf.readEnum(HoldType.class);
    }

    @Override
    public HoldType copy(@NotNull HoldType holdType) {
        return holdType;
    }
}
