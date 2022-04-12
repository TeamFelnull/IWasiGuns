package dev.felnull.iwasi.mixin.client;

import dev.felnull.iwasi.client.data.IEntityHandRenderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IEntityHandRenderItem {
    private LivingEntity lastRenderEntity;

    @Override
    public LivingEntity getRenderEntity() {
        return lastRenderEntity;
    }

    @Override
    public void setRenderEntity(LivingEntity livingEntity) {
        this.lastRenderEntity = livingEntity;
    }
}
