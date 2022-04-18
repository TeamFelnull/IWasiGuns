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
import net.minecraft.util.Mth;
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

        if (data.getLastHoldType() != holdType && data.getGunTrans(InteractionHand.MAIN_HAND).getGunTrans() == null && data.getGunTrans(InteractionHand.OFF_HAND).getGunTrans() == null) {
            int retv = IWPlayerUtil.getMaxHoldProgress(player) - data.getHoldProgress();
            data.setHoldProgress(retv);
            data.setHoldProgressOld(retv);
            data.setPreHoldType(data.getLastHoldType());
            data.setLastHoldType(holdType);
        }

        if (data.getCompHoldType() != data.getLastHoldType() && IWPlayerUtil.getMaxHoldProgress(player) >= data.getHoldProgress()) {
            if (data.getLastHoldType() == HoldType.HOLD) {
                for (InteractionHand hand : InteractionHand.values()) {
                    var item = player.getItemInHand(hand);
                    var gun = IWItemUtil.getGunNullable(item);
                    if (gun != null) gun.playSound(player, gun.getHoldSound(item));
                }
                data.setHoldGrace(holdGraceTime);
            }
            data.setCompHoldType(data.getLastHoldType());
        }

        data.setHoldProgressOld(data.getHoldProgress());
        if (IWPlayerUtil.getMaxHoldProgress(player) > data.getHoldProgress()) {
            data.setHoldProgress(data.getHoldProgress() + IWPlayerUtil.getHoldSpeed(player, data.getPreHoldType(), data.getHoldType(), data.getHoldGrace() > 0));
        }
        if (data.getHoldGrace() > 0) data.setHoldGrace(data.getHoldGrace() - 1);

        if (data.isPullTrigger()) data.setHoldGrace(holdGraceTime);

        for (InteractionHand hand : InteractionHand.values()) {
            var item = player.getItemInHand(hand);
            var gun = IWItemUtil.getGunNullable(item);
            if (gun == null) continue;
            int sc = GunItem.getShotCoolDown(item);
            boolean shot = false;
            if (data.isPullTrigger()) {
                if (data.getGunTrans(hand).getGunTrans() == null && (data.getHoldProgress() == 0 || data.getHoldProgress() >= gun.getRequiredHoldTime()) && sc <= 0 && GunItem.getContinuousShotCount(item) >= 0 && (gun.getMaxContinuousShotCount() == 0 || GunItem.getContinuousShotCount(item) < gun.getMaxContinuousShotCount())) {
                    var sret = gun.shot(player.level, player, hand, item);
                    if (sret.consumesAction()) {
                        shot = true;
                        if (!player.level.isClientSide()) {
                            int ac = GunItem.getContinuousShotCount(item) + 1;
                            if (sret == InteractionResult.CONSUME)
                                ac = gun.getMaxContinuousShotCount() <= 0 ? -1 : gun.getMaxContinuousShotCount();
                            GunItem.setContinuousShotCount(item, ac);
                        }
                    }
                }
            } else {
                if (!player.level.isClientSide())
                    GunItem.setContinuousShotCount(item, 0);
            }
            
            if (!player.level.isClientSide()) {
                if (shot) {
                    GunItem.setShotCoolDown(item, gun.getShotCoolDown());
                } else {
                    if (sc >= 1)
                        GunItem.setShotCoolDown(item, sc - 1);
                }
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
            boolean next = true;
            if (player instanceof ServerPlayer serverPlayer)
                next = gt.tick(serverPlayer, hand, gun, item, gd.progress(), gd.step());

            int mp = gt.getProgress(item, gd.step());

            GunPlayerTransData nd = null;
            if (mp - 1 <= gd.progress()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (next) next = gt.stepEnd(serverPlayer, hand, gun, item, gd.step());
                    if (next && gt.getStep(item) - 1 > gd.step()) {
                        nd = new GunPlayerTransData(gt, 0, gd.step() + 1, gd.updateId() + 1);
                    } else {
                        nd = new GunPlayerTransData(null, 0, 0, gd.updateId() + 1);
                    }
                }
            } else {
                nd = new GunPlayerTransData(gt, gd.progress() + 1, gd.step(), gd.updateId());
            }
            if (nd != null) data.setGunTrans(hand, nd);
        }

        for (InteractionHand hand : InteractionHand.values()) {
            var itm = player.getItemInHand(hand);
            var gun = IWItemUtil.getGunNullable(itm);
            if (gun == null) {
                data.setRecoiling(hand, false);
                data.setRecoil(hand, 0);
                continue;
            }

            if (data.isRecoiling(hand)) {
                int rs = gun.getRecoilSpeed(true);
                data.setRecoil(hand, data.getRecoil(hand) + rs);
                float ang = gun.getRecoilAngle() * ((float) rs / (float) gun.getRecoil());
                recoilTurn(player, -ang, 0);
                if (data.getRecoil(hand) >= gun.getRecoil())
                    data.setRecoiling(hand, false);
            } else {
                if (data.getRecoil(hand) > 0) {
                    int rs = gun.getRecoilSpeed(false);
                    data.setRecoil(hand, Math.max(data.getRecoil(hand) - rs, 0));
                    float ang = gun.getRecoilAngle() * ((float) rs / (float) gun.getRecoil());
                    recoilTurn(player, ang, 0);
                }
            }
        }
    }

    private static void recoilTurn(Player player, float x, float y) {
        player.setXRot(player.getXRot() + x);
        player.setYRot(player.getYRot() + y);
        player.setXRot(Mth.clamp(player.getXRot(), -90.0F, 90.0F));
        player.xRotO += x;
        player.yRotO += y;
        player.xRotO = Mth.clamp(player.xRotO, -90.0F, 90.0F);
    }
}
