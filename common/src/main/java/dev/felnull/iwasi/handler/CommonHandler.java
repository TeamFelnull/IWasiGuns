package dev.felnull.iwasi.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.data.ContinuousActionData;
import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.util.IWItemUtil;
import dev.felnull.otyacraftengine.event.MoreEntityEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
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
        entityData.define(IWPlayerData.MAIN_HAND_GUN_TRANS, new GunTransData());
        entityData.define(IWPlayerData.OFF_HAND_GUN_TRANS, new GunTransData());
        entityData.define(IWPlayerData.HOLDING, HoldType.NONE);
    }

    private static void onPlayerTick(Player player) {
        var data = (IIWDataPlayer) player;
        var holdType = data.getHoldType();

        if (data.getLastHoldType() != holdType) {
            data.setHoldProgress(0);
            data.setHoldProgressOld(0);
            data.setPreHoldType(data.getLastHoldType());
            data.setLastHoldType(holdType);
        }

        data.setHoldProgressOld(data.getHoldProgress());
        if (IWPlayerData.getMaxHoldProgress(player) > data.getHoldProgress())
            data.setHoldProgress(data.getHoldProgress() + 1);

        if (data.getHoldGrace() > 0)
            data.setHoldGrace(data.getHoldGrace() - 1);

        var ca = IWPlayerData.getContinuousAction(player);
        if (ca.pullTrigger())
            data.setHoldGrace(holdGraceTime);


        for (InteractionHand hand : InteractionHand.values()) {
            var gd = data.getGunTrans(hand);
            var gt = gd.getGunTrans();
            var item = player.getItemInHand(hand);
            var gun = IWItemUtil.getGunNullable(item);
            data.setGunTransOld(hand, gd);
            if (gun == null) {
                data.setGunTrans(hand, new GunTransData(null, 0, 0, gd.updateId() + 1));
                continue;
            }
            if (gt == null) continue;
            if (player instanceof ServerPlayer serverPlayer)
                gt.tick(serverPlayer, hand, gun, item, gd.progress(), gd.step());

            int mp = gt.getProgress(gun, gd.step());

            GunTransData nd = null;
            if (mp - 1 <= gd.progress()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    gt.stepEnd(serverPlayer, hand, gun, item, gd.step());
                    if (gt.getStep() - 1 > gd.step()) {
                        nd = new GunTransData(gt, 0, gd.step() + 1, gd.updateId() + 1);
                    } else {
                        nd = new GunTransData(null, 0, 0, gd.updateId() + 1);
                    }
                }
            } else {
                nd = new GunTransData(gt, gd.progress() + 1, gd.step(), gd.updateId());
            }
            if (nd != null)
                data.setGunTrans(hand, nd);
        }
    }

}
