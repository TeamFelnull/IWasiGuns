package dev.felnull.iwasi.client.renderer.item;

import dev.felnull.iwasi.item.IWItems;
import dev.felnull.otyacraftengine.client.renderer.item.ItemRendererRegister;

public class IWItemRenderers {
    public static void init() {
        ItemRendererRegister.register(IWItems.TEST_GUN, new TestGunItemRenderer());
    }
}
