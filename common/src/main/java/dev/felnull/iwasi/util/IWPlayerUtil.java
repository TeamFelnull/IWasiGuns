package dev.felnull.iwasi.util;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class IWPlayerUtil {
    public static float getHoldProgress(@NotNull Player player, InteractionHand hand, float delta) {
        var g = IWItemUtil.getGunNullable(player.getItemInHand(hand));
        if (g != null) {
            var data = (IIWDataPlayer) player;
            return Math.min(data.getHoldProgress(delta) / (float) g.getRequiredHoldTime(), 1f);
        }
        return 0;
    }

    public static int getMaxHoldProgress(@NotNull Player player) {
        var mg = IWItemUtil.getGunNullable(player.getMainHandItem());
        var og = IWItemUtil.getGunNullable(player.getOffhandItem());
        return Math.max(mg != null ? mg.getRequiredHoldTime() : 0, og != null ? og.getRequiredHoldTime() : 0);
    }

    public static int getHoldSpeed(@NotNull Player player, @NotNull HoldType old, @NotNull HoldType last, boolean hurry) {
        var mg = IWItemUtil.getGunNullable(player.getMainHandItem());
        var og = IWItemUtil.getGunNullable(player.getOffhandItem());
        if (mg == null && og == null) return 1;
        if (mg == null) return og.getHoldSpeed(old, last, hurry);
        if (og == null) return mg.getHoldSpeed(old, last, hurry);
        return Math.min(mg.getHoldSpeed(old, last, hurry), og.getHoldSpeed(old, last, hurry));
    }

    public static boolean isHolding(@NotNull Player player, InteractionHand hand) {
        var g = IWItemUtil.getGunNullable(player.getItemInHand(hand));
        if (g != null) {
            var data = (IIWDataPlayer) player;
            return data.getHoldProgress() >= g.getRequiredHoldTime();
        }
        return false;
    }

    public static ItemStack getFindAmmo(Player player, List<Predicate<ItemStack>> ammoFilters, ItemStack defaultsAmmo) {
        for (Predicate<ItemStack> ammoFilter : ammoFilters) {
            var itm = getFindAmmo(player, ammoFilter, null);
            if (itm != null)
                return itm;
        }
        return player.getAbilities().instabuild ? defaultsAmmo : ItemStack.EMPTY;
    }

    public static ItemStack getFindAmmo(Player player, Predicate<ItemStack> ammoFilter, ItemStack defaultsAmmo) {
        for (InteractionHand hand : InteractionHand.values()) {
            var itm = player.getItemInHand(hand);
            if (ammoFilter.test(itm))
                return itm;
        }
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack stack = player.getInventory().getItem(i);
            if (ammoFilter.test(stack)) {
                return stack;
            }
        }
        return player.getAbilities().instabuild ? defaultsAmmo : ItemStack.EMPTY;
    }

    public static void startRecoil(@NotNull Player player, InteractionHand hand) {
        var gun = IWItemUtil.getGunNullable(player.getItemInHand(hand));
        if (gun == null) return;
        var data = (IIWDataPlayer) player;
        data.setRecoil(hand, 0);
        data.setRecoiling(hand, true);
    }

    public static void shotGun(ServerPlayer player, InteractionHand hand, boolean trigger, boolean reduce) {
        var item = player.getItemInHand(hand);
        var gun = IWItemUtil.getGunNullable(item);
        if (gun == null) return;
        int sc = GunItem.getShotCoolDown(item);
        boolean shot = false;
        var data = (IIWDataPlayer) player;
        if (trigger) {
            if (data.getGunTrans(hand).getGunTrans() == null && (data.getHoldProgress() == 0 || data.getHoldProgress() >= gun.getRequiredHoldTime()) && sc <= 0 && GunItem.getContinuousShotCount(item) >= 0 && (gun.getMaxContinuousShotCount() == 0 || GunItem.getContinuousShotCount(item) < gun.getMaxContinuousShotCount())) {
                var sret = gun.shot(player.level, player, hand, item);
                if (sret.consumesAction()) {
                    shot = true;
                    int ac = GunItem.getContinuousShotCount(item) + 1;
                    if (sret == InteractionResult.CONSUME)
                        ac = gun.getMaxContinuousShotCount() <= 0 ? -1 : gun.getMaxContinuousShotCount();
                    GunItem.setContinuousShotCount(item, ac);
                    var gunId = IWItemUtil.getGunTmpID(item);
                    if (gunId != null) {
                        OEPlayerUtil.doPlayers((LevelChunk) player.getLevel().getChunk(player.blockPosition()), n -> {
                            NetworkManager.sendToPlayer(n, IWPackets.ACTION_CLIENT_SYNC, new IWPackets.ActionClientSyncMessage(player.getGameProfile().getId(), hand, gunId).toFBB());
                        });
                    }
                }
            }
        } else {
            GunItem.setContinuousShotCount(item, 0);
        }

        if (shot) {
            GunItem.setShotCoolDown(item, gun.getShotCoolDown());
        } else {
            if (reduce && sc >= 1)
                GunItem.setShotCoolDown(item, sc - 1);
        }
    }
}
