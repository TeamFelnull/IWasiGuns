package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.gun.type.IWGunTypes;

public class TestGun extends Gun {
    public TestGun() {
        super(IWGunTypes.HAND_GUN, new GunProperties.Builder().create());
    }
}
