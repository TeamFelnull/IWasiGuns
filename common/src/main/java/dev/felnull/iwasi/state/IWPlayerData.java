package dev.felnull.iwasi.state;

import dev.felnull.iwasi.entity.ActionDataEntityDataSerializer;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public class IWPlayerData {
    public static final EntityDataAccessor<ActionData> ACTION_DATA = SynchedEntityData.defineId(Player.class, ActionDataEntityDataSerializer.ACTION_DATA_STATE);
    public static final EntityDataAccessor<Integer> MAIN_HAND_HOLDING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> OFF_HAND_HOLDING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
}
