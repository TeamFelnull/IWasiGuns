package dev.felnull.iwasi.data;

import com.google.common.collect.ImmutableList;
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
        providerAccess.tag(IWGItemTags.MEAT_FOODS).addTagHolders(ImmutableList.of(PlatformItemTags.rawMeats(), PlatformItemTags.cookedMeats()));
        providerAccess.tag(IWGItemTags.FISH_FOODS).addVanillaTag(ItemTags.FISHES).addTagHolders(ImmutableList.of(PlatformItemTags.rawFishes(), PlatformItemTags.cookedFishes()));
        providerAccess.tag(IWGItemTags.GRAIN_FOODS).addTagHolder(PlatformItemTags.breads());
        providerAccess.tag(IWGItemTags.VEGETABLE_FOODS).addTagHolders(ImmutableList.of(PlatformItemTags.vegetables(), PlatformItemTags.carrots(), PlatformItemTags.potatoes(), PlatformItemTags.beetroots()));
        providerAccess.tag(IWGItemTags.SEAWEED_FOODS).add(Items.DRIED_KELP);
        providerAccess.tag(IWGItemTags.FRUIT_FOODS).addTagHolder(PlatformItemTags.fruits());
        providerAccess.tag(IWGItemTags.DESSERT_FOODS).add(Items.COOKIE);
        providerAccess.tag(IWGItemTags.DRINKS).addTagHolder(PlatformItemTags.drinks()).add(Items.POTION, Items.MILK_BUCKET, Items.HONEY_BOTTLE);
        providerAccess.tag(IWGItemTags.DARK_FOODS).add(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.PUFFERFISH, Items.POISONOUS_POTATO);
        providerAccess.tag(IWGItemTags.GOLDEN_FOODS).add(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);

        PlatformItemTags.ironNuggets().registering(providerAccess);
    }
}
