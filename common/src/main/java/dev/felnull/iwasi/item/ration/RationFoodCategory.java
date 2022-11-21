package dev.felnull.iwasi.item.ration;

import net.minecraft.util.StringRepresentable;

public enum RationFoodCategory implements StringRepresentable {
    NONE("none", 0),
    MEAT("meat", 0),
    FISH("fish", 0),
    GRAIN("grain", 0),
    VEGETABLE("vegetable", 0),
    SEAWEED("seaweed", 0),
    FRUIT("fruit", 0),
    DESSERT("dessert", 0),
    DRINK("drink", 0),
    GOLDEN("golden", 0);
    private final String name;
    private final int color;

    RationFoodCategory(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
