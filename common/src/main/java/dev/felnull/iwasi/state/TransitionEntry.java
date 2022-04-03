package dev.felnull.iwasi.state;

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record TransitionEntry(@NotNull String name, @NotNull TransitionValue transitionValue, int value) {
    @Nullable
    public static TransitionEntry read(@NotNull String name, @NotNull CompoundTag tag) {
        if (!tag.contains(name, Tag.TAG_COMPOUND)) return null;
        var vtag = tag.getCompound(name);
        return new TransitionEntry(name, TransitionValue.read(vtag), vtag.getInt("value"));
    }

    @NotNull
    public static List<TransitionEntry> readAll(@NotNull CompoundTag tag) {
        ImmutableList.Builder<TransitionEntry> builder = ImmutableList.builder();
        for (String key : tag.getAllKeys()) {
            var vt = read(key, tag);
            if (vt != null)
                builder.add(vt);
        }
        return builder.build();
    }

    public void write(@NotNull CompoundTag tag) {
        var vtag = new CompoundTag();
        transitionValue.write(vtag);
        vtag.putInt("value", value);
        tag.put(name, vtag);
    }

    public TransitionEntry step() {
        return new TransitionEntry(name, transitionValue, transitionValue.transited(value));
    }

    public boolean isComplete() {
        return transitionValue.isComplete(value);
    }
}
