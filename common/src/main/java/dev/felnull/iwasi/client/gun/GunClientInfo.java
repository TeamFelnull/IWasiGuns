package dev.felnull.iwasi.client.gun;

import dev.felnull.iwasi.client.model.gun.GunModel;
import dev.felnull.iwasi.client.renderer.gun.GunRenderer;
import dev.felnull.iwasi.gun.Gun;

public class GunClientInfo<T extends Gun> {
    private final GunRenderer<T, ? extends GunModel<T>> renderer;

    public GunClientInfo(GunRenderer<T, ? extends GunModel<T>> renderer) {
        this.renderer = renderer;
    }

    public GunRenderer<T, ? extends GunModel<T>> getRenderer() {
        return renderer;
    }
}
