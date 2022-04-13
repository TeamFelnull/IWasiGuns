package dev.felnull.iwasi.data;

import dev.felnull.iwasi.gun.trans.GunTransRegistry;
import dev.felnull.iwasi.gun.trans.player.GunPlayerTrans;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record GunPlayerTransData(int transId, int progress, int step, int updateId) {

    public GunPlayerTransData(GunPlayerTrans gunTrans, int progress, int step, int updateID) {
        this(GunTransRegistry.getId(gunTrans), progress, step, updateID);
    }

    public GunPlayerTransData() {
        this(-1, 0, 0, 0);
    }

    @NotNull
    public static GunPlayerTransData read(@NotNull FriendlyByteBuf buf) {
        return new GunPlayerTransData(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public void write(@NotNull FriendlyByteBuf buf) {
        buf.writeInt(transId);
        buf.writeInt(progress);
        buf.writeInt(step);
        buf.writeInt(updateId);
    }

    @NotNull
    public GunPlayerTransData copy() {
        return new GunPlayerTransData(transId, progress, step, updateId);
    }

    @Nullable
    public GunPlayerTrans getGunTrans() {
        return GunTransRegistry.getById(transId);
    }
}
