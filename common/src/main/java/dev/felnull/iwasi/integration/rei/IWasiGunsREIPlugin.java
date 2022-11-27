package dev.felnull.iwasi.integration.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.Display;

//https://github.com/shedaniel/RoughlyEnoughItems/blob/HEAD/default-plugin/src/main/java/me/shedaniel/rei/plugin/client/DefaultClientPlugin.java
public class IWasiGunsREIPlugin implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        var rationDisplays = RationREIRecipeDisplayMaker.create();
        for (Display rationDisplay : rationDisplays) {
            registry.add(rationDisplay);
        }
    }
}
