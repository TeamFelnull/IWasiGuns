package dev.felnull.iwasi.gun.type;

public enum GunType {
    HAND_GUN("hand_gun", true),
    SUB_MACHINE_GUN("sub_machine_gun", true),
    MACHINE_GUN("machine_gun", false);
    private final String name;
    private final boolean upperGun;

    GunType(String name, boolean upperGun) {
        this.name = name;
        this.upperGun = upperGun;
    }

    public String getName() {
        return name;
    }

    public boolean isUpperGun() {
        return upperGun;
    }
}
