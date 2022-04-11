package dev.felnull.iwasi.handler;

import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.data.ContinuousActionData;
import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.otyacraftengine.event.MoreEntityEvent;
import net.minecraft.network.syncher.SynchedEntityData;
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
        entityData.define(IWPlayerData.MAIN_HAND_HOLDING, false);
        entityData.define(IWPlayerData.OFF_HAND_HOLDING, false);
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
    }

}
