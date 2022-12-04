package dev.felnull.iwasi.client.renderer.item;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.iwasi.client.gun.ClientGunRegistry;
import dev.felnull.iwasi.client.gun.GunClientInfo;
import dev.felnull.iwasi.gun.Gun;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class GunItemRenderer implements BEWLItemRenderer {
    private final Gun gun;
    public final Supplier<GunClientInfo<?>> gunClientInfo;

    public GunItemRenderer(Gun gun) {
        this.gun = gun;
        this.gunClientInfo = Suppliers.memoize(() -> ClientGunRegistry.get(gun));
    }

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float delta, int light, int overlay) {
        gunClientInfo.get().getRenderer().render(stack, transformType, poseStack, multiBufferSource, delta, light, overlay);
    }
}
