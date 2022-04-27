package dev.felnull.iwasi.gun;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.trans.player.IWGunPlayerTrans;
import dev.felnull.iwasi.gun.type.IWGunTypes;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.iwasi.item.MagazineItem;
import dev.felnull.iwasi.sound.IWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public class TestGun extends Gun {

    public TestGun() {
        super(IWGunTypes.HAND_GUN, new GunProperties.Builder().setSize16(1.4f, 5.6f, 8.175f).create());
    }

    @Override
    public AbstractReloadGunTrans getReloadTrans(boolean empty) {
        return IWGunPlayerTrans.GLOCK_17_RELOAD;
    }

    @Override
    public ItemStack getDefaultsAmmo(ItemStack stack) {
        return MagazineItem.setRemainingBullets(new ItemStack(IWItems.GLOCK_17_MAGAZINE.get()), ((MagazineItem) IWItems.GLOCK_17_MAGAZINE.get()).getCapacity());
    }

    @Override
    public List<Predicate<ItemStack>> getAmmo(ItemStack stack) {
        return ImmutableList.of(Glock17Gun.GLOCK_17_MG_ONLY);
    }

    @Override
    public SoundEvent getShotSound(ItemStack stack) {
        return IWSounds.SHOT_2.get();
    }

    @Override
    public SoundEvent getHoldSound(ItemStack stack) {
        return IWSounds.HOLD_2.get();
    }
}
