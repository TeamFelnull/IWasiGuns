package dev.felnull.iwasi.state;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public record TransitionValue(boolean sub, int startValue, int limitValue, int oneStepTransitedValue) {
    @NotNull
    public static TransitionValue read(@NotNull CompoundTag tag) {
        return new TransitionValue(tag.getBoolean("sub"), tag.getInt("start"), tag.getInt("limit"), tag.getInt("trans"));
    }

    public void write(@NotNull CompoundTag tag) {
        tag.putBoolean("sub", sub);
        tag.putInt("start", startValue);
        tag.putInt("limit", limitValue);
        tag.putInt("trans", oneStepTransitedValue);
    }

    public boolean isComplete(int value) {
        return sub ? value <= limitValue : value >= limitValue;
    }

    public int transited(int value) {
        int mul = sub ? -1 : 1;
        int v = value + oneStepTransitedValue * mul;
        return sub ? Math.max(v, limitValue) : Math.min(v, limitValue);
    }

    @NotNull
    public TransitionValue revers(int value, int newLimitValue) {
        return new TransitionValue(!sub, value, newLimitValue, oneStepTransitedValue);
    }
}
