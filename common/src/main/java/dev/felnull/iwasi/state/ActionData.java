package dev.felnull.iwasi.state;

import net.minecraft.network.FriendlyByteBuf;

public record ActionData(boolean hold, boolean pullTrigger) {
    public ActionData() {
        this(false, false);
    }

    public static ActionData read(FriendlyByteBuf buf) {
        return new ActionData(buf.readBoolean(), buf.readBoolean());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(hold);
        buf.writeBoolean(pullTrigger);
    }

    public ActionData copy() {
        return new ActionData(hold, pullTrigger);
    }
}
