package dev.felnull.iwasi.gun;

import com.google.common.base.Suppliers;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class Gun {
    private final Supplier<Item> itemCache = Suppliers.memoize(() -> GunItem.ITEM_BY_GUN.get(this));
    private final GunType gunType;
    public final GunProperties gunProperties;

    public Gun(GunType gunType, GunProperties gunProperties) {
        this.gunType = gunType;
        this.gunProperties = gunProperties;
    }

    public GunType getGunType() {
        return gunType;
    }

    public GunProperties getGunProperties() {
        return gunProperties;
    }

    @NotNull
    public Item getItem() {
        return Objects.requireNonNull(itemCache.get());
    }
}
