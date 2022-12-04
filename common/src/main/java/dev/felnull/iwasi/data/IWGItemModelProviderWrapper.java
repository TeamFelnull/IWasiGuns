package dev.felnull.iwasi.data;

import dev.felnull.iwasi.gun.GunRegistry;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;

public class IWGItemModelProviderWrapper extends ItemModelProviderWrapper {
    public IWGItemModelProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    @Override
    public void generateItemModels(ItemModelProviderAccess providerAccess) {
        providerAccess.basicFlatItem(IWGItems.RATION_CAN.get());

        GunRegistry.getAll().forEach((id, gun) -> providerAccess.builtinEntity(gun.getItem()));
    }
}
