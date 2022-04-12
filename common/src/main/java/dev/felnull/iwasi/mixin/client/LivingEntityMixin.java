package dev.felnull.iwasi.mixin.client;

import dev.felnull.iwasi.client.entity.IClientItemHandRenderEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements IClientItemHandRenderEntity {
    @Shadow
    public abstract boolean hasEffect(MobEffect mobEffect);

    private ItemStack lastMainHandItem = ItemStack.EMPTY;
    private ItemStack lastOffHandItem = ItemStack.EMPTY;
    private ItemStack lastMainHandItemOld = ItemStack.EMPTY;
    private ItemStack lastOffHandItemOld = ItemStack.EMPTY;

    @Override
    public ItemStack getLastHandItem(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? lastMainHandItem : lastOffHandItem;
    }

    @Override
    public void setLastHandItem(InteractionHand hand, ItemStack stack) {
        if (hand == InteractionHand.MAIN_HAND) {
            lastMainHandItem = stack;
        } else {
            lastOffHandItem = stack;
        }
    }

    @Override
    public ItemStack getLastHandItemOld(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? lastMainHandItemOld : lastOffHandItemOld;
    }

    @Override
    public void setLastHandItemOld(InteractionHand hand, ItemStack stack) {
        if (hand == InteractionHand.MAIN_HAND) {
            lastMainHandItemOld = stack;
        } else {
            lastOffHandItemOld = stack;
        }
    }
}
