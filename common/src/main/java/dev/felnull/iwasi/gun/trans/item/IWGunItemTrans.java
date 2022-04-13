package dev.felnull.iwasi.gun.trans.item;

import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.gun.trans.GunTransRegistry;
import net.minecraft.resources.ResourceLocation;

public class IWGunItemTrans {
    public static final Glock17SlideRecoilGunItemTrans GLOCK_17_SLIDE_RECOIL = new Glock17SlideRecoilGunItemTrans();

    public static void init() {
        GunTransRegistry.register(new ResourceLocation(IWasi.MODID, "glock_17_slide_recoil"), GLOCK_17_SLIDE_RECOIL);
    }
}
