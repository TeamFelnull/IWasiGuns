package dev.felnull.iwasi.client.renderer.item;

import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.otyacraftengine.client.renderer.item.ItemRendererRegister;

public class IWGItemRenderers {
    public static void init() {
        ItemRendererRegister.register(IWGItems.SKEWER, new SkewerItemRenderer());
    }
}
