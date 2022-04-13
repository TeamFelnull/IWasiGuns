package dev.felnull.iwasi.gun.trans;

import dev.felnull.iwasi.gun.trans.item.GunItemTrans;
import dev.felnull.iwasi.gun.trans.player.GunPlayerTrans;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GunTransRegistry {
    private static final List<GunPlayerTrans> GUN_PLAYER_TRANS = new ArrayList<>();
    private static final Map<ResourceLocation, GunItemTrans> GUN_ITEM_TRANS = new HashMap<>();

    public static GunPlayerTrans register(@NotNull GunPlayerTrans trans) {
        Objects.requireNonNull(trans);
        if (GUN_PLAYER_TRANS.contains(trans))
            throw new IllegalStateException("Duplicate registration");
        GUN_PLAYER_TRANS.add(trans);
        return trans;
    }

    public static int getId(GunPlayerTrans trans) {
        return GUN_PLAYER_TRANS.indexOf(trans);
    }

    @Nullable
    public static GunPlayerTrans getById(int id) {
        if (id < 0 || id >= GUN_PLAYER_TRANS.size())
            return null;
        return GUN_PLAYER_TRANS.get(id);
    }

    public static GunItemTrans register(@NotNull ResourceLocation name, @NotNull GunItemTrans trans) {
        Objects.requireNonNull(trans);
        Objects.requireNonNull(name);
        if (GUN_ITEM_TRANS.containsKey(name) || GUN_ITEM_TRANS.containsValue(trans))
            throw new IllegalStateException("Duplicate registration");
        GUN_ITEM_TRANS.put(name, trans);
        return trans;
    }

    @Nullable
    public static ResourceLocation getName(GunItemTrans trans) {
        if (GUN_ITEM_TRANS.containsValue(trans))
            return GUN_ITEM_TRANS.entrySet().stream().filter(n -> n.getValue().equals(trans)).map(Map.Entry::getKey).findFirst().orElse(null);
        return null;
    }

    @Nullable
    public static GunItemTrans getByName(ResourceLocation name) {
        return GUN_ITEM_TRANS.get(name);
    }
}
