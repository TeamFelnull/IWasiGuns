package dev.felnull.iwasi.handler;

import dev.felnull.iwasi.state.ActionData;
import dev.felnull.iwasi.state.IWPlayerData;
import dev.felnull.otyacraftengine.event.MoreEntityEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class CommonHandler {
    public static void init() {
        MoreEntityEvent.ENTITY_DEFINE_SYNCHED_DATA.register(CommonHandler::onDefineSynchedData);
    }

    public static void onDefineSynchedData(@NotNull Entity entity, @NotNull SynchedEntityData entityData) {
        if (!(entity instanceof Player)) return;
        entityData.define(IWPlayerData.ACTION_DATA, new ActionData());
        entityData.define(IWPlayerData.MAIN_HAND_HOLDING, 0);
        entityData.define(IWPlayerData.OFF_HAND_HOLDING, 0);
    }
}
