package dev.felnull.iwasi.client.data;

import net.minecraft.world.item.ItemStack;

public record InfoGunTrans(DeltaGunPlayerTransData gunTransData, ItemStack stack) {

    public float progressPar() {
        return gunTransData.progress() / (float) (gunTransData.gunTrans().getProgress(stack, gunTransData.step()) - 1);
    }

    public boolean isLastStep() {
        return gunTransData.step() >= gunTransData().gunTrans().getStep(stack) - 1;
    }

    public boolean isFirstStep() {
        return gunTransData.step() == 0;
    }
}
