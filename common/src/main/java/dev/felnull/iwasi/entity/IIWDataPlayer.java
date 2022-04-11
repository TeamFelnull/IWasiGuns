package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.data.HoldType;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;

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

    HoldType getHoldType();

    int getHoldGrace();

    void setHoldGrace(int grace);

    GunTransData getGunTrans(InteractionHand hand);

    void setGunTrans(InteractionHand hand, GunTransData gunTransData);

    GunTransData getGunTransOld(InteractionHand hand);

    void setGunTransOld(InteractionHand hand, GunTransData gunTransData);
}
