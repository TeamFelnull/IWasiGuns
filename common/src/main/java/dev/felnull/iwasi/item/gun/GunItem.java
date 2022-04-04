package dev.felnull.iwasi.item.gun;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.state.IWPlayerState;
import dev.felnull.iwasi.state.TransitionEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public abstract class GunItem extends Item {

    public GunItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public void handTick(ItemStack stack, ServerPlayer player, InteractionHand hand) {
        if (getTmpUUID(stack) == null)
            setTmpUUID(stack, UUID.randomUUID());

        int cd = getCoolDown(stack);
        if (cd > 0)
            setCoolDown(stack, cd - 1);

        if (IWPlayerState.isPullTrigger(player)) {
            if (canShot(stack))
                shot(stack, player, hand);
        }
    }

    public void reset(ItemStack stack, ServerPlayer player, InteractionHand hand) {
        // System.out.println(stack + ":" + hand);
    }

    public boolean canShot(ItemStack stack) {
        return getCoolDown(stack) <= 0 && getCapacity(stack) > 0;
    }

    public int getHoldingTime() {
        return 5;
    }

    public int getShotCoolDown() {
        return 3;
    }

    public int getMaxCapacity() {
        return 18;
    }

    public void shot(ItemStack stack, ServerPlayer player, InteractionHand hand) {
        int cap = getCapacity(stack);
        setCapacity(stack, cap - 1);
        setCoolDown(stack, getShotCoolDown());
        var level = player.getLevel();
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            ThrownEgg thrownEgg = new ThrownEgg(level, player);
            thrownEgg.setItem(new ItemStack(Items.EGG));
            thrownEgg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownEgg);
        }
    }

    @Nullable
    public static CompoundTag getGunDataTag(@NotNull ItemStack stack) {
        if (stack.getTag() != null)
            return stack.getTag().getCompound("GunData");
        return null;
    }

    @Nullable
    public static CompoundTag getGunStateTag(@NotNull ItemStack stack) {
        if (stack.getTag() != null)
            return stack.getTag().getCompound("GunState");
        return null;
    }

    @NotNull
    public static CompoundTag getOrCreateGunStateTag(@NotNull ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (!tag.contains("GunState"))
            tag.put("GunState", new CompoundTag());
        return tag.getCompound("GunState");
    }

    @NotNull
    public static CompoundTag getOrCreateGunDataTag(@NotNull ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (!tag.contains("GunData"))
            tag.put("GunData", new CompoundTag());
        return tag.getCompound("GunData");
    }

    @NotNull
    public static List<TransitionEntry> getAllTransitionEntry(@NotNull ItemStack stack) {
        var tag = getGunStateTag(stack);
        if (tag == null) return ImmutableList.of();
        return TransitionEntry.readAll(tag.getCompound("Transition"));
    }

    @Nullable
    public static TransitionEntry getTransitionEntry(@NotNull ItemStack stack, @NotNull String name) {
        var tag = getGunStateTag(stack);
        if (tag == null) return null;
        return TransitionEntry.read(name, tag.getCompound("Transition"));
    }

    @NotNull
    public static ItemStack setTransitionEntry(@NotNull ItemStack stack, @NotNull TransitionEntry entry) {
        var tag = getOrCreateGunStateTag(stack).getCompound("Transition");
        entry.write(tag);
        return stack;
    }

    public static int getCoolDown(@NotNull ItemStack stack) {
        var tag = getGunStateTag(stack);
        if (tag != null)
            return tag.getInt("CoolDown");
        return 0;
    }

    @NotNull
    public static ItemStack setCoolDown(@NotNull ItemStack stack, int coolDown) {
        getOrCreateGunStateTag(stack).putInt("CoolDown", coolDown);
        return stack;
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

    public static GunMotionType getMotion(ItemStack stack) {
        var tag = getGunStateTag(stack);
        if (tag != null)
            return GunMotionType.getByName(tag.getString("Motion"));
        return GunMotionType.NONE;
    }

    public static ItemStack setMotion(ItemStack stack, GunMotionType motion) {
        getOrCreateGunStateTag(stack).putString("Motion", motion.getName());
        return stack;
    }

    public static int getCapacity(ItemStack stack) {
        var tag = getGunDataTag(stack);
        if (tag != null)
            return tag.getInt("Capacity");
        return 0;
    }

    public static ItemStack setCapacity(ItemStack stack, int capacity) {
        getOrCreateGunDataTag(stack).putInt("Capacity", capacity);
        return stack;
    }

    public static enum GunMotionType {
        NONE("none"),
        HOLD_TRANS("hold_trans"),
        RECOILING("recoiling"),
        RELOADING("reloading");
        private final String name;

        GunMotionType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @NotNull
        public static GunMotionType getByName(String name) {
            for (GunMotionType value : values()) {
                if (value.getName().equals(name))
                    return value;
            }
            return NONE;
        }
    }
}
