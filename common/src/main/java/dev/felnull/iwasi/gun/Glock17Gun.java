package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.data.GunItemTransData;
import dev.felnull.iwasi.gun.trans.item.IWGunItemTrans;
import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.iwasi.gun.type.IWGunTypes;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.iwasi.util.IWItemUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

//https://www.hyperdouraku.com/colum/cqbguam/glock17gen5.html
public class Glock17Gun extends Gun {
    public Glock17Gun() {
        super(IWGunTypes.HAND_GUN, new GunProperties.Builder().setMaxContinuousShotCount(1).setShotCoolDown(3).setWeight(716).setSize16(1.525f, 6.55f, 12.225f).create());
    }

    @Override
    public AbstractReloadGunTrans getReloadTrans() {
        return IWGunPlayerTrans.GLOCK_17_RELOAD;
    }

    @Override
    public Item getMagazine() {
        return IWItems.GLOCK_17_MAGAZINE.get();
    }

    @Override
    protected void shotAfter(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        super.shotAfter(level, player, interactionHand, itemStack);
        GunItem.addGunItemTrans(itemStack, new GunItemTransData(IWGunItemTrans.GLOCK_17_SLIDE_RECOIL, 0, 0, 0), true);
    }

    @Override
    public void reload(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        if (IWItemUtil.isSlideDown(itemStack))
            GunItem.addGunItemTrans(itemStack, new GunItemTransData(IWGunItemTrans.GLOCK_17_SLIDE_REVERS, 0, 0, 0), true);
        super.reload(level, player, interactionHand, itemStack);
    }
}
