package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.gun.type.IWGunTypes;

//https://www.hyperdouraku.com/colum/cqbguam/glock17gen5.html
public class Glock17Gun extends Gun {
    public Glock17Gun() {
        super(IWGunTypes.HAND_GUN, new GunProperties.Builder().setShotCoolDown(3).setWeight(716).create());
    }
}
