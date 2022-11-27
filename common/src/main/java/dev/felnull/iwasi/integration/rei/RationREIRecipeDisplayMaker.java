package dev.felnull.iwasi.integration.rei;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.ration.Ration;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomShapelessDisplay;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RationREIRecipeDisplayMaker {
    public static List<Display> create() {
        List<Display> ret = new ArrayList<>();

        EntryStack<ItemStack>[] foodStacks = EntryRegistry.getInstance().getEntryStacks()
                .filter(entry -> entry.getValueType() == ItemStack.class)
                .map(entry -> entry.<ItemStack>castValue())
                .filter(Ration::canContainFood)
                .map(EntryStacks::of)
                .toArray(EntryStack[]::new);

        EntryIngredient foods = EntryIngredient.of(foodStacks);
        EntryIngredient drinks = EntryIngredients.ofItemTag(Ration.containDrinkTags());
        EntryIngredient rationCan = EntryIngredients.of(new ItemStack(IWGItems.RATION_CAN.get()));
        EntryIngredient output = EntryIngredients.of(new ItemStack(IWGItems.RATION.get()));

        ret.add(new DefaultCustomShapelessDisplay(null, ImmutableList.of(rationCan, foods, foods, foods, foods, foods, foods, foods, foods), Collections.singletonList(output)));
        ret.add(new DefaultCustomShapelessDisplay(null, ImmutableList.of(rationCan, drinks, foods, foods, foods, foods, foods, foods, foods), Collections.singletonList(output)));

        return ret;
    }
}
