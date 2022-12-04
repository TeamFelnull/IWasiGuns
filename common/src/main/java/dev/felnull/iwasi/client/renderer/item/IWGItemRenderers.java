package dev.felnull.iwasi.client.renderer.item;

import dev.felnull.iwasi.gun.Gun;
import dev.felnull.iwasi.gun.GunRegistry;
import dev.felnull.otyacraftengine.client.renderer.item.ItemRendererRegister;

public class IWGItemRenderers {
    public static void init() {
        for (Gun gun : GunRegistry.getAll().values()) {
            ItemRendererRegister.register(gun.getItem(), new GunItemRenderer(gun));
        }
    }
}
