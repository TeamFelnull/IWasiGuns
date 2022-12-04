package dev.felnull.iwasi.client.renderer.gun;

import dev.felnull.iwasi.client.model.gun.Glock17GunModel;
import dev.felnull.iwasi.gun.Glock17Gun;

public class Glock17GunRenderer extends GunRenderer<Glock17Gun, Glock17GunModel> {
    public Glock17GunRenderer() {
        super(new Glock17GunModel());
    }
}
