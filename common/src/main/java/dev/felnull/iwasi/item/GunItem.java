package dev.felnull.iwasi.item;

import dev.felnull.iwasi.gun.Gun;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
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

    public static CompoundTag getGunTag(ItemStack stack) {
        if (stack.getTag() != null)
            return stack.getTag().getCompound("GunData");
        return null;
    }

    public static CompoundTag getOrCreateGunTag(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (!tag.contains("GunData", Tag.TAG_COMPOUND))
            tag.put("GunData", new CompoundTag());
        return tag.getCompound("GunData");
    }

    public static ItemStack getMagazine(ItemStack itemStack) {
        var tag = getGunTag(itemStack);
        if (tag != null && tag.contains("Magazine", Tag.TAG_COMPOUND))
            return ItemStack.of(tag.getCompound("Magazine"));
        return ItemStack.EMPTY;
    }

    public static void setMagazine(Player player, ItemStack itemStack, ItemStack magazineStack) {
        SoundEvent s = null;
        if (!magazineStack.isEmpty() && getMagazine(itemStack).isEmpty())
            s = SoundEvents.IRON_TRAPDOOR_OPEN;
        else if (magazineStack.isEmpty() && !getMagazine(itemStack).isEmpty())
            s = SoundEvents.IRON_TRAPDOOR_CLOSE;

        if (s != null)
            player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(), s, SoundSource.NEUTRAL, 0.5F, 1f);
        setMagazine(itemStack, magazineStack);
    }

    public static void setMagazine(ItemStack itemStack, ItemStack magazineStack) {
        getOrCreateGunTag(itemStack).put("Magazine", magazineStack.save(new CompoundTag()));
    }

    public static boolean canReload(ItemStack itemStack) {
        return true;
    }

    public static int getContinuousShotCount(ItemStack itemStack) {
        var tag = getGunTag(itemStack);
        if (tag != null)
            return tag.getInt("ContinuousShotCount");
        return 0;
    }

    public static void setContinuousShotCount(ItemStack itemStack, int count) {
        getOrCreateGunTag(itemStack).putInt("ContinuousShotCount", count);
    }

    public static int getShotCoolDown(ItemStack itemStack) {
        var tag = getGunTag(itemStack);
        if (tag != null)
            return tag.getInt("ShotCoolDown");
        return 0;
    }

    public static void setShotCoolDown(ItemStack itemStack, int count) {
        getOrCreateGunTag(itemStack).putInt("ShotCoolDown", count);
    }
}
