package dev.felnull.iwasi.data;

public enum HoldType {
    NONE, HOLD, LOWER;

    public static HoldType getIdeal(boolean hold, boolean sprint, int grace) {
        if (hold) return HOLD;
        if (sprint && grace <= 0) return LOWER;
        return NONE;
    }
}
