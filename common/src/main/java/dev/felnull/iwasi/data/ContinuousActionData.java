package dev.felnull.iwasi.data;

import net.minecraft.network.FriendlyByteBuf;

public record ContinuousActionData(boolean hold, boolean pullTrigger) {
    public ContinuousActionData() {
        this(false, false);
    }

    public static ContinuousActionData read(FriendlyByteBuf buf) {
        return new ContinuousActionData(buf.readBoolean(), buf.readBoolean());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(hold);
        buf.writeBoolean(pullTrigger);
    }

    public ContinuousActionData copy() {
        return new ContinuousActionData(hold, pullTrigger);
    }
}
