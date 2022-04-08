package dev.felnull.iwasi.client.renderer.item;

import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.otyacraftengine.client.gui.screen.debug.RenderTestScreen;
import dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest.ItemRenderTest;
import dev.felnull.otyacraftengine.client.renderer.item.ItemRendererRegister;

public class IWItemRenderers {
    public static void init() {
        GunItem.GUN_ITEMS.forEach((n, m) -> ItemRendererRegister.register(m, new GunItemRenderer(n)));
        ItemRendererRegister.register(IWItems.GLOCK_17_MAGAZINE, new Glock17MagazineItemRenderer());

        RenderTestScreen.addRenderTest(new ItemRenderTest(IWItems.GLOCK_17_GUN.get()));
    }
}
