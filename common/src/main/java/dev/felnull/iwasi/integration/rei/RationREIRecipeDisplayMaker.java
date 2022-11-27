package dev.felnull.iwasi.integration.rei;

import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.item.ration.Ration;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RationREIRecipeDisplayMaker {
    public static List<Display> create() {
        List<Display> ret = new ArrayList<>();

        EntryIngredient output = EntryIngredients.of(Ration.createB2Unit(new ItemStack(IWGItems.RATION.get())));

        EntryStack<ItemStack>[] foods = EntryRegistry.getInstance().getEntryStacks()
                .filter(entry -> entry.getValueType() == ItemStack.class)
                .map(entry -> entry.<ItemStack>castValue())
                .filter(Ration::canContainFood)
                .map(EntryStacks::of)
                .toArray(EntryStack[]::new);

        EntryIngredient food = EntryIngredient.of(foods);

        List<EntryIngredient> input = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            input.add(food);
        input.add(EntryIngredients.of(new ItemStack(IWGItems.RATION_CAN.get())));
        for (int i = 0; i < 4; i++)
            input.add(food);


        ret.add(new DefaultCustomDisplay(null, input, Collections.singletonList(output)));

        return ret;
    }
}
