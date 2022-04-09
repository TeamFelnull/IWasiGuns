package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.data.HoldType;
import net.minecraft.util.Mth;

public interface IIWDataPlayer {
    int getHoldProgress();

    void setHoldProgress(int progress);

    int getHoldProgressOld();

    void setHoldProgressOld(int progress);

    default float getHoldProgress(float delta) {
        return Mth.lerp(delta, getHoldProgressOld(), getHoldProgress());
    }

    HoldType getLastHoldType();

    void setLastHoldType(HoldType holdType);

    HoldType getPreHoldType();

    void setPreHoldType(HoldType holdType);
}
