package dev.felnull.iwasi.gun;

import com.mojang.math.Vector3f;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.bullet.Bullet;
import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.type.GunType;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.item.MagazineItem;
import dev.felnull.iwasi.sound.IWSounds;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.iwasi.util.IWPlayerUtil;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public abstract class Gun {
    private final GunType type;
    private final GunProperties properties;

    public Gun(@NotNull GunType type, @NotNull GunProperties properties) {
        this.type = type;
        this.properties = properties;
    }

    @NotNull
    public GunType getType() {
        return type;
    }

    public int getMaxContinuousShotCount() {
        return properties.maxContinuousShotCount();
    }

    public int getChamberCapacity() {
        return properties.chamberCapacity();
    }

    public int getShotCoolDown() {
        return properties.shotCoolDown();
    }

    public float getWeight() {
        return properties.weight();
    }

    public int getRequiredHoldTime() {
        return (int) (getWeight() / 200f);
    }

    public Vector3f getSize() {
        return properties.size();
    }

    public int getHoldSpeed(HoldType old, HoldType last, boolean hurry) {
        if (((old.isDisarmament() && last == HoldType.NONE) || old == HoldType.NONE && last.isDisarmament()) && hurry)
            return Math.max(getRequiredHoldTime() - 1, 1);
        return 1;
    }

    abstract public AbstractReloadGunTrans getReloadTrans(boolean empty);

    abstract public ItemStack getDefaultsAmmo(ItemStack stack);

    abstract public List<Predicate<ItemStack>> getAmmo(ItemStack stack);

    public void consumeAmmo(ServerPlayer player, ItemStack stack) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            var itm = player.getInventory().getItem(i);
            if (itm == stack) {
                if (!player.getAbilities().instabuild)
                    itm.shrink(1);
                break;
            }
        }
    }

    public SoundEvent getShotSound(ItemStack stack) {
        return IWSounds.SHOT_1.get();
    }

    public SoundEvent getNoAmmoShotSound(ItemStack stack) {
        return IWSounds.SHOT_NO_AMMO.get();
    }

    public SoundEvent getHoldSound(ItemStack stack) {
        return IWSounds.HOLD_1.get();
    }

    protected boolean canShot(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        return GunItem.getChamberRemainingBullets(itemStack) >= 1;
    }

    public InteractionResult shot(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (canShot(level, player, interactionHand, itemStack)) {
            playSound(player, getShotSound(itemStack), 2.0F);
            if (!level.isClientSide) {
                var bullet = createBulletEntity(level, player);
                level.addFreshEntity(bullet);

                shotAfter((ServerLevel) level, (ServerPlayer) player, interactionHand, itemStack);
            }
            IWPlayerUtil.startRecoil(player, interactionHand);
            return InteractionResult.SUCCESS;
        }
        playSound(player, getNoAmmoShotSound(itemStack));
        return InteractionResult.CONSUME;
    }

    protected Bullet createBulletEntity(Level level, Player player) {
        var bullet = new Bullet(level, player);
        bullet.setPos(new Vec3(player.position().x(), player.getEyeY() - 0.1d, player.position().z()));

        bullet.shot(player.getLookAngle(), Bullet.SPEED / 20f);
        return bullet;
    }

    protected void shotAfter(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        GunItem.setChamberRemainingBullets(itemStack, Math.max(GunItem.getChamberRemainingBullets(itemStack) - 1, 0));
        updateMagazineRemaining(itemStack, GunItem.getMagazine(itemStack));
    }

    public void reloadRemoveMagazine(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        var mg = GunItem.getMagazine(itemStack);
        IWPlayerData.setTmpHandItems(player, OEEntityUtil.getOppositeHand(interactionHand), NonNullList.of(ItemStack.EMPTY, mg.copy()));
        if (!mg.isEmpty()) {
            OEPlayerUtil.giveItem(player, mg.copy());
            GunItem.setMagazine(player, itemStack, ItemStack.EMPTY);
        }
    }

    public void reloadSetMagazine(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        var ret = getReloadedItem(player, interactionHand, itemStack);
        if (!ret.isEmpty()) {
            var ri = ret.copy();
            ri.setCount(1);
            GunItem.setMagazine(player, itemStack, ri);
            updateMagazineRemaining(itemStack, GunItem.getMagazine(itemStack));
            consumeAmmo(player, ret);
        }
        IWPlayerData.setTmpHandItems(player, OEEntityUtil.getOppositeHand(interactionHand), NonNullList.of(ItemStack.EMPTY, ItemStack.EMPTY));
    }

    private void updateMagazineRemaining(ItemStack gunItem, ItemStack magazineItem) {
        var gun = IWItemUtil.getGunNullable(gunItem);
        if (gun == null) return;
        int rb = MagazineItem.getRemainingBullets(magazineItem);
        int crb = GunItem.getChamberRemainingBullets(gunItem);
        int rcb = gun.getChamberCapacity() - crb;
        if (rcb == 0) return;
        if (rb <= rcb) {
            MagazineItem.setRemainingBullets(magazineItem, 0);
            GunItem.setChamberRemainingBullets(gunItem, crb + rb);
        } else {
            MagazineItem.setRemainingBullets(magazineItem, rb - rcb);
            GunItem.setChamberRemainingBullets(gunItem, gun.getChamberCapacity());
        }
    }

    public void playSound(Player player, SoundEvent soundEvent) {
        playSound(player, soundEvent, 0.5f);
    }

    public void playSound(Player player, SoundEvent soundEvent, float range) {
        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), soundEvent, SoundSource.PLAYERS, range, player.level.getRandom().nextFloat() * 0.1F + 0.95F);
    }

    public ItemStack getReloadedItem(ServerPlayer player, InteractionHand hand, ItemStack stack) {
        var mg = GunItem.getMagazine(stack);
        if (mg.isEmpty() || MagazineItem.getRemainingBullets(mg) < ((MagazineItem) mg.getItem()).getCapacity())
            return IWPlayerUtil.getFindAmmo(player, getAmmo(stack), getDefaultsAmmo(stack));
        return ItemStack.EMPTY;
    }

    public int getRecoil() {
        return 4;
    }

    public int getRecoilSpeed(boolean ret) {
        if (ret)
            return getRecoil();
        return 1;
    }

    public float getRecoilAngle() {
        return 3;
    }
}
