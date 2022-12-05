package dev.felnull.iwasi;

import com.google.common.base.Suppliers;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.felnull.iwasi.client.IWasiGunsClient;
import dev.felnull.iwasi.gun.IWGGuns;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.recipe.IWGRecipeSerializers;
import dev.felnull.iwasi.server.handler.ServerHandler;
import dev.felnull.iwasi.server.loot.functions.IWGLootItemFunctions;
import dev.felnull.otyacraftengine.util.OEDataGenUtils;

import java.util.function.Supplier;

public class IWasiGuns {
    public static final String MODID = "iwasiguns";
    private static final Supplier<String> MODNAME = Suppliers.memoize(() -> Platform.getMod(MODID).getName());

    public static void init() {
        IWGGuns.init();
        IWGItems.init();
        IWGLootItemFunctions.init();

        IWGRecipeSerializers.init();

        ServerHandler.init();

        if (!OEDataGenUtils.isDataGenerating())
            EnvExecutor.runInEnv(Env.CLIENT, () -> IWasiGunsClient::preInit);
    }

    public static String getModName() {
        return MODNAME.get();
    }
}
