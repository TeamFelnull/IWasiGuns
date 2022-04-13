package dev.felnull.iwasi.gun.trans.item;

import dev.felnull.iwasi.gun.trans.GunTrans;
import dev.felnull.iwasi.gun.trans.GunTransRegistry;
import net.minecraft.resources.ResourceLocation;

public abstract class GunItemTrans extends GunTrans {
    public ResourceLocation getName() {
        return GunTransRegistry.getName(this);
    }
}
