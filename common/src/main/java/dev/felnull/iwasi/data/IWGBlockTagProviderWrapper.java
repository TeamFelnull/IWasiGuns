package dev.felnull.iwasi.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.BlockTagProviderWrapper;
import net.minecraft.world.level.block.Block;

public class IWGBlockTagProviderWrapper extends BlockTagProviderWrapper {
    public IWGBlockTagProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    @Override
    public void generateTag(TagProviderAccess<Block> providerAccess) {

    }
}
