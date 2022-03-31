package dev.felnull.iwasi.client.renderer;

import dev.felnull.iwasi.item.IWItems;
import dev.felnull.otyacraftengine.client.gui.screen.debug.RenderTestScreen;
import dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest.ItemRenderTest;

public class IWTestRenderers {
    public static void init() {
        RenderTestScreen.addRenderTest(new ItemRenderTest(IWItems.TEST_GUN.get()));
    }
}
