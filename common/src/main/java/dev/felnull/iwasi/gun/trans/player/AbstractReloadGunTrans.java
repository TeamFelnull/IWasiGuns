package dev.felnull.iwasi.gun.trans.player;

import dev.felnull.iwasi.util.IWItemUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractReloadGunTrans extends GunPlayerTrans {
    @Override
    public boolean isUseBothHand() {
        return true;
    }

    protected void reload(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        var gun = IWItemUtil.getGunNullable(itemStack);
        if (gun == null) return;
        gun.reload(level, player, interactionHand, itemStack);
    }

    protected void unReload(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        var gun = IWItemUtil.getGunNullable(itemStack);
        if (gun == null) return;
        gun.unReload(level, player, interactionHand, itemStack);
    }
}
