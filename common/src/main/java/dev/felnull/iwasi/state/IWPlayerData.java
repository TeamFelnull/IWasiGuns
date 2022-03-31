package dev.felnull.iwasi.state;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public class IWPlayerData {
    public static final EntityDataAccessor<Boolean> DATA_HOLD = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
}
