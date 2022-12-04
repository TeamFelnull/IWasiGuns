package dev.felnull.iwasi.data;

import com.google.gson.JsonObject;
import dev.felnull.fnjl.util.FNStringUtil;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.ModelDivisionProviderWrapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IWGModelDivisionProviderWrapper extends ModelDivisionProviderWrapper {
    private final Map<TranslateKey, TranslateEntry> translates = new HashMap<>();
    private final Map<Path, JsonObject> divedModels;

    public IWGModelDivisionProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess, Map<Path, JsonObject> divedModels) {
        super(crossDataGeneratorAccess);
        this.divedModels = divedModels;

        this.translates.put(TranslateKey.gun("glock_17/main"), TranslateEntry.sw(8.2f, 1.575f, 0.705f));
    }

    @Override
    protected boolean isTarget(Path inputFolder, Path path) {
        return isChildDir(inputFolder, path, Paths.get("assets\\iwasiguns\\models\\gun"));
    }

    @Override
    protected JsonObject processModel(Path orizinalPath, Path divOutPath, String name, JsonObject model) {
        divedModels.put(divOutPath, model.deepCopy());

        Optional<TranslateEntry> translate = translates.entrySet().stream().filter(n -> n.getKey().match(orizinalPath)).map(Map.Entry::getValue).findFirst();
        System.out.println(translate.orElse(null));

        return super.processModel(orizinalPath, divOutPath, name, model);
    }

    private record TranslateKey(String modelName) {
        public static TranslateKey gun(String name) {
            return new TranslateKey("gun/" + name);
        }

        public boolean match(Path path) {
            String[] pathDiv = modelName.split("/");

            for (int i = 0; i < pathDiv.length; i++) {
                var chkName = String.valueOf(path.getName(path.getNameCount() - pathDiv.length + i));
                if (i == pathDiv.length - 1)
                    chkName = FNStringUtil.removeExtension(chkName);

                if (!pathDiv[i].equals(chkName))
                    return false;
            }

            return true;
        }
    }

    private record TranslateEntry(TranslateStartPoint startPoint, double x, double y, double z) {
        public static TranslateEntry nw(double x, double y, double z) {
            return new TranslateEntry(TranslateStartPoint.NW, x, y, z);
        }

        public static TranslateEntry sw(double x, double y, double z) {
            return new TranslateEntry(TranslateStartPoint.SW, x, y, z);
        }

        public static TranslateEntry ne(double x, double y, double z) {
            return new TranslateEntry(TranslateStartPoint.NE, x, y, z);
        }

        public static TranslateEntry se(double x, double y, double z) {
            return new TranslateEntry(TranslateStartPoint.SE, x, y, z);
        }
    }

    private enum TranslateStartPoint {
        NW, SW, NE, SE;
    }
}
