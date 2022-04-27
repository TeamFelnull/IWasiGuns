package dev.felnull.iwasi.gun;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.data.GunItemTransData;
import dev.felnull.iwasi.gun.trans.item.IWGunItemTrans;
import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.iwasi.gun.type.IWGunTypes;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.iwasi.item.MagazineItem;
import dev.felnull.iwasi.util.IWItemUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

//https://www.hyperdouraku.com/colum/cqbguam/glock17gen5.html
public class Glock17Gun extends Gun {
    public static final Predicate<ItemStack> GLOCK_17_MG_ONLY = stack -> stack.is(IWItems.GLOCK_17_MAGAZINE.get()) && MagazineItem.getRemainingBullets(stack) >= 1;

    public Glock17Gun() {
        super(IWGunTypes.HAND_GUN, new GunProperties.Builder().setMaxContinuousShotCount(1).setShotCoolDown(8).setWeight(716).setSize16(1.525f, 6.55f, 12.225f).create());
    }

    @Override
    public AbstractReloadGunTrans getReloadTrans(boolean empty) {
        return empty ? IWGunPlayerTrans.GLOCK_17_EMPTY_RELOAD : IWGunPlayerTrans.GLOCK_17_RELOAD;
    }

    @Override
    public ItemStack getDefaultsAmmo(ItemStack stack) {
        return MagazineItem.setRemainingBullets(new ItemStack(IWItems.GLOCK_17_MAGAZINE.get()), ((MagazineItem) IWItems.GLOCK_17_MAGAZINE.get()).getCapacity());
    }

    @Override
    public List<Predicate<ItemStack>> getAmmo(ItemStack stack) {
        return ImmutableList.of(GLOCK_17_MG_ONLY);
    }

    @Override
    protected void shotAfter(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        super.shotAfter(level, player, interactionHand, itemStack);
        GunItem.addGunItemTrans(itemStack, new GunItemTransData(IWGunItemTrans.GLOCK_17_SLIDE_RECOIL, 0, 0, 0), true);
    }

    @Override
    public void reloadSetMagazine(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        if (IWItemUtil.isSlideDown(itemStack))
            GunItem.addGunItemTrans(itemStack, new GunItemTransData(IWGunItemTrans.GLOCK_17_SLIDE_REVERS, 0, 0, 0), true);
        super.reloadSetMagazine(level, player, interactionHand, itemStack);
    }
}
