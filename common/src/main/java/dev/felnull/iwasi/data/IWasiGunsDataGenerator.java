package dev.felnull.iwasi.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;

public class IWasiGunsDataGenerator {
    public static void init(CrossDataGeneratorAccess access) {
        access.addProvider(new IWGItemModelProviderWrapper(access));
        access.addProvider(new IWGRecipeProviderWrapper(access));

        var btp = new IWGBlockTagProviderWrapper(access);
        access.addProvider(btp);
        access.addProvider(new IWGItemTagProviderWrapper(access, btp));
    }
}
