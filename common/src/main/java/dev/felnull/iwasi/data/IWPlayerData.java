package dev.felnull.iwasi.data;

import dev.felnull.iwasi.entity.IWEntityDataSerializers;
import dev.felnull.iwasi.gun.trans.GunTrans;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IWPlayerData {
    public static final EntityDataAccessor<ContinuousActionData> CONTINUOUS_ACTION = SynchedEntityData.defineId(Player.class, IWEntityDataSerializers.CONTINUOUS_ACTION_DATA);
    public static final EntityDataAccessor<GunTransData> MAIN_HAND_GUN_TRANS = SynchedEntityData.defineId(Player.class, IWEntityDataSerializers.GUN_TRANS_DATA);
    public static final EntityDataAccessor<GunTransData> OFF_HAND_GUN_TRANS = SynchedEntityData.defineId(Player.class, IWEntityDataSerializers.GUN_TRANS_DATA);
    public static final EntityDataAccessor<Boolean> MAIN_HAND_HOLDING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> OFF_HAND_HOLDING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);

    public static boolean isPutHold(@NotNull Player player) {
        return getContinuousAction(player).hold();
    }

    public static boolean isPullTrigger(@NotNull Player player) {
        return getContinuousAction(player).pullTrigger();
    }

    @NotNull
    public static ContinuousActionData getContinuousAction(@NotNull Player player) {
        return player.getEntityData().get(IWPlayerData.CONTINUOUS_ACTION);
    }

    @Nullable
    public static GunTrans getGunTrans(@NotNull Player player, @NotNull InteractionHand hand) {
        return getGunTransData(player, hand).getGunTrans();
    }

    @NotNull
    public static GunTransData getGunTransData(@NotNull Player player, @NotNull InteractionHand hand) {
        var es = hand == InteractionHand.MAIN_HAND ? MAIN_HAND_GUN_TRANS : OFF_HAND_GUN_TRANS;
        return player.getEntityData().get(es);
    }

    public static boolean isHolding(@NotNull Player player, InteractionHand hand) {
        var es = hand == InteractionHand.MAIN_HAND ? MAIN_HAND_HOLDING : OFF_HAND_HOLDING;
        return player.getEntityData().get(es);
    }

    public static void setGunTrans(@NotNull ServerPlayer player, InteractionHand hand, GunTrans gunTrans) {
        var es = hand == InteractionHand.MAIN_HAND ? MAIN_HAND_GUN_TRANS : OFF_HAND_GUN_TRANS;
        var o = getGunTransData(player, hand);
        player.getEntityData().set(es, new GunTransData(gunTrans, 0, 0, o.updateId() + 1));
    }

}
