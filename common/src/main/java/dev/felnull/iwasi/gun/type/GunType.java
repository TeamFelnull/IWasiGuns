package dev.felnull.iwasi.gun.type;

public enum GunType {
    HAND_GUN("hand_gun", true, true),
    SUB_MACHINE_GUN("sub_machine_gun", true, false),
    MACHINE_GUN("machine_gun", false, false);
    private final String name;
    private final boolean upperGun;
    private final boolean haveWithKnife;

    GunType(String name, boolean upperGun, boolean holdWithKnife) {
        this.name = name;
        this.upperGun = upperGun;
        this.haveWithKnife = holdWithKnife;
    }

    public String getName() {
        return name;
    }

    public boolean isUpperGun() {
        return upperGun;
    }

    public boolean canHaveWithKnife() {
        return haveWithKnife;
    }
}
