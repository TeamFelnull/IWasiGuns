package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.data.GunTransDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class IWEntityDataSerializers {
    public static final ContinuousActionEntityDataSerializer CONTINUOUS_ACTION_DATA = new ContinuousActionEntityDataSerializer();
    public static final GunTransDataSerializer GUN_TRANS_DATA = new GunTransDataSerializer();
    public static final HoldTypeEntityDataSerializer HOLD_TYPE_DATA = new HoldTypeEntityDataSerializer();

    public static void init() {
        EntityDataSerializers.registerSerializer(CONTINUOUS_ACTION_DATA);
        EntityDataSerializers.registerSerializer(GUN_TRANS_DATA);
        EntityDataSerializers.registerSerializer(HOLD_TYPE_DATA);
    }
}
