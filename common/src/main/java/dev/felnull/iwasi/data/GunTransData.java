package dev.felnull.iwasi.data;

import dev.felnull.iwasi.gun.trans.GunTrans;
import dev.felnull.iwasi.gun.trans.GunTransRegistry;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record GunTransData(int id, int progress, int step) {
    public GunTransData(GunTrans gunTrans, int progress, int step) {
        this(GunTransRegistry.getId(gunTrans), progress, step);
    }

    public GunTransData() {
        this(-1, 0, 0);
    }

    @NotNull
    public static GunTransData read(@NotNull FriendlyByteBuf buf) {
        return new GunTransData(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public void write(@NotNull FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(progress);
        buf.writeInt(step);
    }

    @NotNull
    public GunTransData copy() {
        return new GunTransData(id, progress, step);
    }

    @Nullable
    public GunTrans getGunTrans() {
        return GunTransRegistry.getById(id);
    }


    public GunTransData tickNext() {


        return null;
    }

}
