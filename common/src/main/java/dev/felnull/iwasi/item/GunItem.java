package dev.felnull.iwasi.item;

import dev.felnull.iwasi.gun.Gun;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GunItem extends Item {
    public static final Map<Gun, Item> GUN_ITEMS = new HashMap<>();
    private final Gun gun;

    public GunItem(@NotNull Gun gun, @NotNull Properties properties) {
        super(properties.stacksTo(1));
        GUN_ITEMS.put(gun, this);
        this.gun = gun;
    }

    @NotNull
    public Gun getGun() {
        return gun;
    }

    @Nullable
    public static UUID getTmpUUID(@NotNull ItemStack stack) {
        var tag = stack.getTag();
        if (tag != null && tag.hasUUID("TmpId"))
            return tag.getUUID("TmpId");
        return null;
    }

    @NotNull
    public static ItemStack setTmpUUID(@NotNull ItemStack stack, @Nullable UUID uuid) {
        var tag = stack.getOrCreateTag();
        if (uuid != null) {
            tag.putUUID("TmpId", uuid);
        } else {
            if (tag.hasUUID("TmpId"))
                tag.remove("TmpId");
        }
        return stack;
    }

    @NotNull
    public static ItemStack resetTmpUUID(@NotNull ItemStack stack) {
        setTmpUUID(stack, UUID.randomUUID());
        return stack;
    }
}
