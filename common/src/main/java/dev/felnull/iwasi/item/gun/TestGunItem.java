package dev.felnull.iwasi.item.gun;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class TestGunItem extends GunItem {

    public TestGunItem(Properties properties) {
        super(properties);
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        //  if (!level.isClientSide()) {
        //     ItemStack itemStack = player.getItemInHand(interactionHand);
        //       boolean hold = isHold(itemStack);
        //      setHold(itemStack, !hold);
        player.displayClientMessage(new TextComponent(level.isClientSide() + " : " + IWPlayerState.isHolding(player)), false);
        //  }
        return super.use(level, player, interactionHand);
    }*/

    public static CompoundTag getGunStateTag(ItemStack stack) {
        if (stack.getTag() != null)
            return stack.getTag().getCompound("GunState");
        return null;
    }

    public static CompoundTag getOrCreateGunStateTag(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if (!tag.contains("GunState"))
            tag.put("GunState", new CompoundTag());
        return tag.getCompound("GunState");
    }
/*
    public static boolean isHold(ItemStack itemStack) {
        var tag = getGunStateTag(itemStack);
        if (tag != null)
            return tag.getBoolean("Hold");
        return false;
    }

    public static void setHold(ItemStack itemStack, boolean power) {
        getOrCreateGunStateTag(itemStack).putBoolean("Hold", power);
    }*/

}
