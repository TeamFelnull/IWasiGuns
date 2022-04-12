package dev.felnull.iwasi.client.data;

import net.minecraft.world.entity.LivingEntity;

public interface IEntityHandRenderItem {
    LivingEntity getRenderEntity();

    void setRenderEntity(LivingEntity livingEntity);
}
