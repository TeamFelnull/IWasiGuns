package dev.felnull.iwasi.data;

import dev.felnull.iwasi.item.IWGItemTags;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.BlockTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.ItemTagProviderWrapper;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class IWGItemTagProviderWrapper extends ItemTagProviderWrapper {
    public IWGItemTagProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess, @NotNull BlockTagProviderWrapper blockTagProviderWrapper) {
        super(crossDataGeneratorAccess, blockTagProviderWrapper);
    }

    @Override
    public void generateTag(ItemTagProviderAccess providerAccess) {
        providerAccess.tag(IWGItemTags.MEAT_FOODS).add(Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.BEEF, Items.COOKED_BEEF, Items.CHICKEN, Items.COOKED_CHICKEN, Items.ROTTEN_FLESH, Items.RABBIT, Items.COOKED_RABBIT, Items.MUTTON, Items.COOKED_MUTTON);
        providerAccess.tag(IWGItemTags.FISH_FOODS).add(Items.COD, Items.PUFFERFISH, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON);//.addTag(ItemTags.FISHES);
        providerAccess.tag(IWGItemTags.GRAIN_FOODS).add(Items.BREAD);
        providerAccess.tag(IWGItemTags.VEGETABLE_FOODS).add(Items.CARROT, Items.POTATO, Items.POISONOUS_POTATO, Items.BEETROOT);
        providerAccess.tag(IWGItemTags.SEAWEED_FOODS).add(Items.KELP);
        providerAccess.tag(IWGItemTags.FRUIT_FOODS).add(Items.APPLE, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.MELON, Items.SWEET_BERRIES, Items.GLOW_BERRIES);
        providerAccess.tag(IWGItemTags.DESSERT_FOODS).add(Items.COOKIE);
        providerAccess.tag(IWGItemTags.DRINK_FOODS).add(Items.POTION, Items.HONEY_BOTTLE);
        providerAccess.tag(IWGItemTags.DARK_FOODS).add(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.PUFFERFISH);
        providerAccess.tag(IWGItemTags.GOLDEN_FOODS).add(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
    }
}
