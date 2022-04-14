package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.data.GunItemTransData;
import dev.felnull.iwasi.gun.Gun;
import dev.felnull.iwasi.gun.trans.item.GunItemTrans;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public record DeltaGunItemTransData(GunItemTrans gunTrans, float progress, int step) {
    public static DeltaGunItemTransData of(float delta, @NotNull GunItemTransData old, @NotNull GunItemTransData last, @NotNull Gun gun) {
        if (old.getGunTrans() != null) {
            int tp = last.progress();
            int tpo = old.progress();
            if (last.step() != old.step() || last.getGunTrans() == null)
                tp = old.getGunTrans().getProgress(gun, old.step()) - 1;
            return new DeltaGunItemTransData(last.getGunTrans(), Mth.lerp(delta, tpo, tp), old.step());
        }
        return new DeltaGunItemTransData(null, 0, 0);
    }
}
