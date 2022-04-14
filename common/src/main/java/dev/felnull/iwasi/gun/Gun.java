package dev.felnull.iwasi.gun;

import com.mojang.math.Vector3f;
import dev.felnull.iwasi.gun.trans.player.AbstractReloadGunTrans;
import dev.felnull.iwasi.gun.type.GunType;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.item.MagazineItem;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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

    public int getHoldSpeed() {
        return (int) (getWeight() / 200f);
    }

    public Vector3f getSize() {
        return properties.size();
    }

    abstract public AbstractReloadGunTrans getReloadTrans();

    abstract public Item getMagazine();

    protected boolean canShot(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        return GunItem.getChamberRemainingBullets(itemStack) >= 1;
    }

    public InteractionResult shot(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (canShot(level, player, interactionHand, itemStack)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.IRON_GOLEM_HURT, SoundSource.PLAYERS, 0.5F, 2f);
            if (!level.isClientSide) {
                ThrownEgg thrownEgg = new ThrownEgg(level, player);
                thrownEgg.setItem(new ItemStack(Items.EGG));
                thrownEgg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(thrownEgg);
                shotAfter((ServerLevel) level, (ServerPlayer) player, interactionHand, itemStack);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    protected void shotAfter(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        GunItem.setChamberRemainingBullets(itemStack, Math.max(GunItem.getChamberRemainingBullets(itemStack) - 1, 0));
        updateMagazineRemaining(itemStack, GunItem.getMagazine(itemStack));
    }

    public void unReload(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        var mg = GunItem.getMagazine(itemStack);
        if (!mg.isEmpty()) {
            OEPlayerUtil.giveItem(player, mg.copy());
            GunItem.setMagazine(player, itemStack, ItemStack.EMPTY);
        }
    }

    public void reload(ServerLevel level, ServerPlayer player, InteractionHand interactionHand, ItemStack itemStack) {
        GunItem.setMagazine(player, itemStack, MagazineItem.setRemainingBullets(new ItemStack(getMagazine()), ((MagazineItem) getMagazine()).getCapacity()));
        updateMagazineRemaining(itemStack, GunItem.getMagazine(itemStack));
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
        if (GunItem.getChamberRemainingBullets(gunItem) >= gun.getChamberCapacity())
            GunItem.setSlided(gunItem, false);
    }
}
