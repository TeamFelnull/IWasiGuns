package dev.felnull.iwasi.integration.recipeguide.rei;

import dev.felnull.iwasi.integration.recipeguide.RecipeGuideInfos;
import dev.felnull.iwasi.item.IWGItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;

//https://github.com/shedaniel/RoughlyEnoughItems/blob/HEAD/default-plugin/src/main/java/me/shedaniel/rei/plugin/client/DefaultClientPlugin.java
public class IWasiGunsREIPlugin implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        var rationDisplays = RationREIRecipeDisplayMaker.create();
        for (Display rationDisplay : rationDisplays) {
            registry.add(rationDisplay);
        }

        DefaultInformationDisplay info = DefaultInformationDisplay.createFromEntry(EntryStacks.of(IWGItems.RATION.get()), IWGItems.RATION.get().asItem().getDescription());
        info.lines(RecipeGuideInfos.RATION_INFO, RecipeGuideInfos.RATION_INFO2);
        registry.add(info);
    }
}
