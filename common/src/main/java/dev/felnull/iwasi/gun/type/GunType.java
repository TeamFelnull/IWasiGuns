package dev.felnull.iwasi.gun.type;

public class GunType {
    private final String name;
    private final boolean upperGun;

    public GunType(String name, boolean upperGun) {
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
