package dev.felnull.iwasi.gun;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.iwasi.gun.type.GunType;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.iwasi.item.MagazineItem;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public class AR57Gun extends Gun {
    public AR57Gun() {
        super(GunType.SUB_MACHINE_GUN, new GunProperties.Builder().create());
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
        return ImmutableList.of(Glock17Gun.GLOCK_17_MG_ONLY);
    }
}
