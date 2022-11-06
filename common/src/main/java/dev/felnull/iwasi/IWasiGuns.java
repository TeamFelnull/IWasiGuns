package dev.felnull.iwasi;

import com.google.common.base.Suppliers;
import dev.architectury.platform.Platform;

import java.util.function.Supplier;

public class IWasiGuns {
    public static final String MODID = "iwasiguns";
    private static final Supplier<String> MODNAME = Suppliers.memoize(() -> Platform.getMod(MODID).getName());

    public static void init() {

    }

    public static String getModName() {
        return MODNAME.get();
    }
}
