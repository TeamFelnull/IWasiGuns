package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.gun.trans.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.GunTrans;
import dev.felnull.iwasi.gun.trans.HoldGunTrans;
import dev.felnull.iwasi.gun.trans.IWGunTrans;
import dev.felnull.iwasi.gun.type.GunType;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public abstract class Gun {
    private final GunType type;
    private final GunProperties properties;

    public Gun(@NotNull GunType type, @NotNull GunProperties properties) {
        this.type = type;
        this.properties = properties;
    }

    @NotNull
    public GunType getType() {
        return type;
    }

    public int getChamberCapacity() {
        return properties.chamberCapacity();
    }

    public int getShotCoolDown() {
        return properties.shotCoolDown();
    }

    public float getWeight() {
        return properties.weight();
    }

    public int getHoldSpeed() {
        return (int) (getWeight() / 120f);
    }

    public HoldGunTrans getHoldTrans() {
        return IWGunTrans.HOLD;
    }

    public HoldGunTrans getUnHoldTrans() {
        return IWGunTrans.UN_HOLD;
    }

    abstract public AbstractReloadGunTrans getReloadTrans();

    abstract public Item getMagazine();
}
