package dev.felnull.iwasi.data;

import dev.felnull.iwasi.item.IWGItemTags;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.BlockTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.ItemTagProviderWrapper;
import dev.felnull.otyacraftengine.tag.PlatformItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class IWGItemTagProviderWrapper extends ItemTagProviderWrapper {
    public IWGItemTagProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess, @NotNull BlockTagProviderWrapper blockTagProviderWrapper) {
        super(crossDataGeneratorAccess, blockTagProviderWrapper);
    }

    @Override
    public void generateTag(ItemTagProviderAccess providerAccess) {
        providerAccess.tag(IWGItemTags.MEAT_FOODS).addTag(PlatformItemTags.rawMeats(), PlatformItemTags.cookedMeats());
        providerAccess.tag(IWGItemTags.FISH_FOODS).addVanillaTag(ItemTags.FISHES).addTag(PlatformItemTags.rawFishes(), PlatformItemTags.cookedFishes());
        providerAccess.tag(IWGItemTags.GRAIN_FOODS).addTag(PlatformItemTags.breads());
        providerAccess.tag(IWGItemTags.VEGETABLE_FOODS).addTag(PlatformItemTags.vegetables(), PlatformItemTags.carrots(), PlatformItemTags.potatoes(), PlatformItemTags.beetroots());
        providerAccess.tag(IWGItemTags.SEAWEED_FOODS).add(Items.KELP);
        providerAccess.tag(IWGItemTags.FRUIT_FOODS).addTag(PlatformItemTags.fruits());
        providerAccess.tag(IWGItemTags.DESSERT_FOODS).add(Items.COOKIE);
        providerAccess.tag(IWGItemTags.DRINK_FOODS).addTag(PlatformItemTags.drinks());
        providerAccess.tag(IWGItemTags.DARK_FOODS).add(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.PUFFERFISH, Items.POISONOUS_POTATO);
        providerAccess.tag(IWGItemTags.GOLDEN_FOODS).add(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
    }
}
