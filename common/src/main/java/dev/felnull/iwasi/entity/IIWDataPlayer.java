package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.data.GunPlayerTransData;
import dev.felnull.iwasi.data.HoldType;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

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

    NonNullList<ItemStack> getTmpHandItems(InteractionHand hand);

    void setTmpHandItems(InteractionHand hand, NonNullList<ItemStack> itemStacks);

    boolean isTmpHandItemsUpdate(InteractionHand hand);

    void setTmpHandItemsUpdate(InteractionHand hand, boolean update);

    int getRecoil(InteractionHand hand);

    void setRecoil(InteractionHand hand, int recoil);

    int getRecoilOld(InteractionHand hand);

    void setRecoilOld(InteractionHand hand, int recoil);

    boolean isRecoiling(InteractionHand hand);

    void setRecoiling(InteractionHand hand, boolean recoiling);
}
