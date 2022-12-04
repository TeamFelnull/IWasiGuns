package dev.felnull.iwasi.data;

import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.DevToolProviderWrapper;
import net.minecraft.data.CachedOutput;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class IWGModelPointerProviderWrapper extends DevToolProviderWrapper {
    private final Map<Path, JsonObject> divedModels;

    public IWGModelPointerProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess, Map<Path, JsonObject> divedModels) {
        super(crossDataGeneratorAccess);
        this.divedModels = divedModels;
    }

    @Override
    public void run(CachedOutput cachedOutput) throws IOException {
      /*  divedModels.forEach((path, jsonObject) -> {
            System.out.println(path + ":" + jsonObject);
        });*/
//        DataProvider.saveStable(cachedOutput, null, null);
    }

    @Override
    public String getName() {
        return "Model Pointer";
    }
}
