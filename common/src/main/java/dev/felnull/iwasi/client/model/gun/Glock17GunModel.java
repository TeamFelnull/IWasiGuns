package dev.felnull.iwasi.client.model.gun;

import dev.felnull.iwasi.client.model.IWGModels;
import dev.felnull.iwasi.client.renderer.RenderbleObject;
import dev.felnull.iwasi.gun.Glock17Gun;
import net.minecraft.world.item.ItemStack;

public class Glock17GunModel extends GunModel<Glock17Gun> {
    private final GunModelPart slidePart = new GunModelPart(RenderbleObject.of(IWGModels.GLOCK_17_SLIDE), 0.1f, 0.1f, 0.1f, 0.45f, 1f, 0.45f, 0.05f, 0.05f, 0.05f);

    public Glock17GunModel() {
        super(new GunModelPart(RenderbleObject.of(IWGModels.GLOCK_17_MAIN), 1f, 1f, 1f, 0, 0, 0, 0.5f, 0.5f, 0.5f));
        this.rootPart.addChildren("slide", slidePart);
    }

    @Override
    public void setupAnim(ItemStack stack) {
        //this.rootPart.getRotation().set(0, 0, OEClientUtils.getParSecond(10000) * 360f);
        //this.slidePart.getRotation().set(0, OEClientUtils.getParSecond(1000) * 360f, 0);
    }
}
