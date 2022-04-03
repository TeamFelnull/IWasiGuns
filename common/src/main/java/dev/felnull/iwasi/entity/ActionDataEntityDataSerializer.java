package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.state.ActionData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.jetbrains.annotations.NotNull;

public class ActionDataEntityDataSerializer implements EntityDataSerializer<ActionData> {
    public static final ActionDataEntityDataSerializer ACTION_DATA_STATE = new ActionDataEntityDataSerializer();

    public static void init() {
        EntityDataSerializers.registerSerializer(ACTION_DATA_STATE);
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buf, @NotNull ActionData data) {
        data.write(buf);
    }

    @Override
    public ActionData read(@NotNull FriendlyByteBuf buf) {
        return ActionData.read(buf);
    }

    @Override
    public ActionData copy(@NotNull ActionData data) {
        return data.copy();
    }
}
