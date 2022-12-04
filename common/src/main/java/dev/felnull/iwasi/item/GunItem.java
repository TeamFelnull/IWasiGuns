package dev.felnull.iwasi.item;

import dev.felnull.iwasi.gun.Gun;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class GunItem extends Item {
    public static final Map<Gun, Item> ITEM_BY_GUN = new HashMap<>();
    private final Gun gun;

    public GunItem(Properties properties, Gun gun) {
        super(properties);
        this.gun = gun;
        if (ITEM_BY_GUN.containsKey(gun))
            throw new RuntimeException("Gun items that already exist");
        ITEM_BY_GUN.put(gun, this);
    }

    public Gun getGun() {
        return gun;
    }
}
