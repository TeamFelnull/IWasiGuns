package dev.felnull.iwasi.gun.trans.player;

import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractReloadGunTrans extends GunPlayerTrans {
    @Override
    public boolean isUseBothHand() {
        return true;
    }

    protected void setMagazine(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        var gun = IWItemUtil.getGunNullable(itemStack);
        if (gun == null) return;
        gun.reloadSetMagazine(level, player, interactionHand, itemStack);
    }

    protected void removeMagazine(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        var gun = IWItemUtil.getGunNullable(itemStack);
        if (gun == null) return;
        gun.reloadRemoveMagazine(level, player, interactionHand, itemStack);
    }

    protected boolean swapHoldMagazine(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        var gun = IWItemUtil.getGunNullable(itemStack);
        if (gun == null) return false;
        var ng = gun.getReloadedItem(player, interactionHand, itemStack);
        if (!ng.isEmpty()) {
            IWPlayerData.setTmpHandItems(player, OEEntityUtil.getOppositeHand(interactionHand), NonNullList.of(ItemStack.EMPTY, ng));
            return true;
        }
        return false;
    }
}
