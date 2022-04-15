package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.data.GunPlayerTransData;
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

    HoldType getCompHoldType();

    void setCompHoldType(HoldType holdType);

    HoldType getHoldType();

    void setHoldType(HoldType holdType);

    int getHoldGrace();

    void setHoldGrace(int grace);

    GunPlayerTransData getGunTrans(InteractionHand hand);

    void setGunTrans(InteractionHand hand, GunPlayerTransData gunTransData);

    GunPlayerTransData getGunTransOld(InteractionHand hand);

    void setGunTransOld(InteractionHand hand, GunPlayerTransData gunTransData);

    boolean isPullTrigger();
}
