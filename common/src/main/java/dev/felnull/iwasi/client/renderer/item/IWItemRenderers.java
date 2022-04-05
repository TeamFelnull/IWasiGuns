package dev.felnull.iwasi.client.renderer.item;

import dev.felnull.iwasi.item.GunItem;
import dev.felnull.otyacraftengine.client.renderer.item.ItemRendererRegister;

public class IWItemRenderers {
    public static void init() {
        GunItem.GUN_ITEMS.forEach((n, m) -> ItemRendererRegister.register(m, new GunItemRenderer(n)));
    }
}
