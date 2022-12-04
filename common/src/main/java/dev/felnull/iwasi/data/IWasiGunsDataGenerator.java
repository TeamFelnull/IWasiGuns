package dev.felnull.iwasi.data;

import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class IWasiGunsDataGenerator {
    public static void init(CrossDataGeneratorAccess access) {
        access.addResourceInputFolders(Paths.get("../../resources"));

        access.addProvider(new IWGItemModelProviderWrapper(access));
        access.addProvider(new IWGRecipeProviderWrapper(access));

        var btp = new IWGBlockTagProviderWrapper(access);
        access.addProvider(btp);
        access.addProvider(new IWGItemTagProviderWrapper(access, btp));

        Map<Path, JsonObject> divedModels = new HashMap<>();
        access.addProvider(new IWGModelDivisionProviderWrapper(access, divedModels));
        access.addProvider(new IWGModelPointerProviderWrapper(access, divedModels));
    }
}
