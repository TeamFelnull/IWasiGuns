package dev.felnull.iwasi.gun.trans;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GunTransRegistry {
    private static final List<GunTrans> GUN_TRANS = new ArrayList<>();

    public static GunTrans register(@NotNull GunTrans trans) {
        Objects.requireNonNull(trans);
        if (GUN_TRANS.contains(trans))
            throw new IllegalStateException("Duplicate registration");
        GUN_TRANS.add(trans);
        return trans;
    }

    public static int getId(@NotNull GunTrans trans) {
        return GUN_TRANS.indexOf(trans);
    }

    @Nullable
    public static GunTrans getById(int id) {
        if (id < 0 || id >= GUN_TRANS.size())
            return null;
        return GUN_TRANS.get(id);
    }
}
