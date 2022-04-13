package dev.felnull.iwasi.gun;

import dev.felnull.iwasi.data.GunItemTransData;
import dev.felnull.iwasi.gun.trans.item.IWGunItemTrans;
import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.iwasi.gun.type.IWGunTypes;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.item.IWItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
    public InteractionResult shot(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        var ret = super.shot(level, player, interactionHand, itemStack);
        if (ret == InteractionResult.SUCCESS) {
            GunItem.addGunItemTrans(itemStack, new GunItemTransData(IWGunItemTrans.GLOCK_17_SLIDE_RECOIL, 0, 0), true);
        }
        return ret;
    }
}
