package dev.felnull.iwasi.client.data;

import dev.felnull.iwasi.gun.Gun;
import dev.felnull.iwasi.util.IWItemUtil;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record InfoGunTrans(DeltaGunTransData gunTransData, @NotNull Gun gun) {
    public InfoGunTrans(DeltaGunTransData gunTransData, ItemStack stack) {
        this(gunTransData, IWItemUtil.getGun(stack));
    }

    public float progressPar() {
        return gunTransData.progress() / (float) (gunTransData.gunTrans().getProgress(gun, gunTransData.step()) - 1);
    }
}
