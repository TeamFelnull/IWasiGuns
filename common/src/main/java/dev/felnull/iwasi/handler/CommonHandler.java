package dev.felnull.iwasi.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.data.ContinuousActionData;
import dev.felnull.iwasi.data.GunPlayerTransData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.iwasi.util.IWPlayerUtil;
import dev.felnull.otyacraftengine.event.MoreEntityEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class CommonHandler {
    private static final int holdGraceTime = 20 * 3;

    public static void init() {
        MoreEntityEvent.ENTITY_DEFINE_SYNCHED_DATA.register(CommonHandler::onDefineSynchedData);
        TickEvent.PLAYER_POST.register(CommonHandler::onPlayerTick);
    }

    public static void onDefineSynchedData(@NotNull Entity entity, @NotNull SynchedEntityData entityData) {
        if (!(entity instanceof Player)) return;
        entityData.define(IWPlayerData.CONTINUOUS_ACTION, new ContinuousActionData());
        entityData.define(IWPlayerData.MAIN_HAND_GUN_TRANS, new GunPlayerTransData());
        entityData.define(IWPlayerData.OFF_HAND_GUN_TRANS, new GunPlayerTransData());
        entityData.define(IWPlayerData.HOLDING, HoldType.NONE);
    }

    private static void onPlayerTick(Player player) {
        var data = (IIWDataPlayer) player;
        var holdType = data.getHoldType();

        if (data.getLastHoldType() != holdType) {
            int retv = IWPlayerUtil.getMaxHoldProgress(player) - data.getHoldProgress();
            data.setHoldProgress(retv);
            data.setHoldProgressOld(retv);
            data.setPreHoldType(data.getLastHoldType());
            data.setLastHoldType(holdType);
        }

        data.setHoldProgressOld(data.getHoldProgress());
        if (IWPlayerUtil.getMaxHoldProgress(player) > data.getHoldProgress()) {
            data.setHoldProgress(data.getHoldProgress() + IWPlayerUtil.getHoldSpeed(player, data.getPreHoldType(), data.getHoldType(), data.getHoldGrace() > 0));
        }
        if (data.getHoldGrace() > 0)
            data.setHoldGrace(data.getHoldGrace() - 1);

        if (data.isPullTrigger())
            data.setHoldGrace(holdGraceTime);

        for (InteractionHand hand : InteractionHand.values()) {
            var item = player.getItemInHand(hand);
            var gun = IWItemUtil.getGunNullable(item);
            if (gun == null) continue;
            int sc = GunItem.getShotCoolDown(item);
            boolean shot = false;
            if (data.isPullTrigger()) {
                if (data.getGunTrans(hand).getGunTrans() == null && (data.getHoldProgress() == 0 || data.getHoldProgress() >= gun.getRequiredHoldTime()) && sc <= 0 && (gun.getMaxContinuousShotCount() <= 0 || GunItem.getContinuousShotCount(item) < gun.getMaxContinuousShotCount())) {
                    var sret = gun.shot(player.level, player, hand, item);
                    if (sret == InteractionResult.SUCCESS) {
                        shot = true;
                        if (!player.level.isClientSide()) {
                            GunItem.setContinuousShotCount(item, GunItem.getContinuousShotCount(item) + 1);
                        }
                    }
                }
            } else {
                GunItem.setContinuousShotCount(item, 0);
            }

            if (shot) {
                GunItem.setShotCoolDown(item, gun.getShotCoolDown());
            } else {
                if (sc >= 1)
                    GunItem.setShotCoolDown(item, sc - 1);
            }
        }


        for (InteractionHand hand : InteractionHand.values()) {
            var gd = data.getGunTrans(hand);
            var gt = gd.getGunTrans();
            var item = player.getItemInHand(hand);
            var gun = IWItemUtil.getGunNullable(item);
            data.setGunTransOld(hand, gd);
            if (gun == null) {
                data.setGunTrans(hand, new GunPlayerTransData(null, 0, 0, gd.updateId() + 1));
                continue;
            }
            if (gt == null) continue;
            if (player instanceof ServerPlayer serverPlayer)
                gt.tick(serverPlayer, hand, gun, item, gd.progress(), gd.step());

            int mp = gt.getProgress(item, gd.step());

            GunPlayerTransData nd = null;
            if (mp - 1 <= gd.progress()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    gt.stepEnd(serverPlayer, hand, gun, item, gd.step());
                    if (gt.getStep(item) - 1 > gd.step()) {
                        nd = new GunPlayerTransData(gt, 0, gd.step() + 1, gd.updateId() + 1);
                    } else {
                        nd = new GunPlayerTransData(null, 0, 0, gd.updateId() + 1);
                    }
                }
            } else {
                nd = new GunPlayerTransData(gt, gd.progress() + 1, gd.step(), gd.updateId());
            }
            if (nd != null)
                data.setGunTrans(hand, nd);
        }
    }

}
