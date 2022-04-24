package dev.felnull.iwasi.data;

import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.entity.IWEntityDataSerializers;
import dev.felnull.iwasi.gun.trans.player.GunPlayerTrans;
import dev.felnull.iwasi.util.IWItemUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IWPlayerData {
    public static final EntityDataAccessor<ContinuousActionData> CONTINUOUS_ACTION = SynchedEntityData.defineId(Player.class, IWEntityDataSerializers.CONTINUOUS_ACTION_DATA);
    public static final EntityDataAccessor<GunPlayerTransData> MAIN_HAND_GUN_TRANS = SynchedEntityData.defineId(Player.class, IWEntityDataSerializers.GUN_TRANS_DATA);
    public static final EntityDataAccessor<GunPlayerTransData> OFF_HAND_GUN_TRANS = SynchedEntityData.defineId(Player.class, IWEntityDataSerializers.GUN_TRANS_DATA);
    public static final EntityDataAccessor<HoldType> HOLDING = SynchedEntityData.defineId(Player.class, IWEntityDataSerializers.HOLD_TYPE_DATA);

    public static boolean isPutHold(@NotNull Player player) {
        return getContinuousAction(player).hold();
    }

    public static boolean isPullTrigger(@NotNull Player player) {
        return getContinuousAction(player).pullTrigger();
    }

    @NotNull
    public static HoldType getHold(@NotNull Player player) {
        return player.getEntityData().get(HOLDING);
    }

    public static HoldType getPreHold(@NotNull Player player) {
        var data = (IIWDataPlayer) player;
        return data.getPreHoldType();
    }

    public static HoldType getLastHold(@NotNull Player player) {
        var data = (IIWDataPlayer) player;
        return data.getLastHoldType();
    }

    public static void setHold(@NotNull ServerPlayer player, HoldType holdType) {
        player.getEntityData().set(HOLDING, holdType);
    }

    @NotNull
    public static ContinuousActionData getContinuousAction(@NotNull Player player) {
        return player.getEntityData().get(IWPlayerData.CONTINUOUS_ACTION);
    }

    @Nullable
    public static GunPlayerTrans getGunTrans(@NotNull Player player, @NotNull InteractionHand hand) {
        return getGunTransData(player, hand).getGunTrans();
    }

    @NotNull
    public static GunPlayerTransData getGunTransData(@NotNull Player player, @NotNull InteractionHand hand) {
        var es = hand == InteractionHand.MAIN_HAND ? MAIN_HAND_GUN_TRANS : OFF_HAND_GUN_TRANS;
        return player.getEntityData().get(es);
    }

    public static void setGunTrans(@NotNull ServerPlayer player, InteractionHand hand, GunPlayerTrans gunTrans) {
        var es = hand == InteractionHand.MAIN_HAND ? MAIN_HAND_GUN_TRANS : OFF_HAND_GUN_TRANS;
        var o = getGunTransData(player, hand);
        player.getEntityData().set(es, new GunPlayerTransData(gunTrans, 0, 0, o.updateId() + 1));
    }

    public static void setGunTransData(@NotNull ServerPlayer player, InteractionHand hand, GunPlayerTransData gunTransData) {
        var es = hand == InteractionHand.MAIN_HAND ? MAIN_HAND_GUN_TRANS : OFF_HAND_GUN_TRANS;
        player.getEntityData().set(es, gunTransData);
    }

    public static NonNullList<ItemStack> getTmpHandItems(@NotNull Player player, InteractionHand hand) {
        var data = (IIWDataPlayer) player;
        return data.getTmpHandItems(hand);
    }

    public static void setTmpHandItems(@NotNull ServerPlayer player, InteractionHand hand, NonNullList<ItemStack> itemStacks) {
        var data = (IIWDataPlayer) player;
        data.setTmpHandItems(hand, itemStacks);
    }

    public static float getRecoil(@NotNull Player player, InteractionHand hand, float delta) {
        var data = (IIWDataPlayer) player;
        var itm = player.getItemInHand(hand);
        var gun = IWItemUtil.getGunNullable(itm);
        if (gun == null)
            return 0;
        float dp = Mth.lerp(delta, data.getRecoilOld(hand), data.getRecoil(hand));
        return dp / (float) gun.getRecoil();
    }

}
