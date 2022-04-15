package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.data.GunItemTransData;
import dev.felnull.iwasi.gun.trans.item.GunItemTrans;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record DeltaGunItemTransData(GunItemTrans gunTrans, float progress, int step) {
    public static DeltaGunItemTransData of(float delta, @NotNull GunItemTransData old, @NotNull GunItemTransData last, ItemStack stack) {
        if (old.getGunTrans() != null) {
            int tp = last.progress();
            int tpo = old.progress();
            int st = old.step();
            if (last.step() > old.step() || last.getGunTrans() == null) {
                tp = old.getGunTrans().getProgress(stack, old.step()) - 1;
            } else if (last.updateId() != old.updateId()) {
                tp = tpo;
                st = last.step();
            }
            return new DeltaGunItemTransData(old.getGunTrans(), Mth.lerp(delta, tpo, tp), st);
        }
        return new DeltaGunItemTransData(null, 0, 0);
    }
}
