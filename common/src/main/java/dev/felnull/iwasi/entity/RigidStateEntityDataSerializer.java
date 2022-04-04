package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.physics.RigidState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.jetbrains.annotations.NotNull;

public class RigidStateEntityDataSerializer implements EntityDataSerializer<RigidState> {
    public static final RigidStateEntityDataSerializer RIGID_STATE = new RigidStateEntityDataSerializer();

    public static void init(){
        EntityDataSerializers.registerSerializer(RIGID_STATE);
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buf, @NotNull RigidState rigidState) {
        rigidState.write(buf);
    }

    @Override
    public RigidState read(@NotNull FriendlyByteBuf buf) {
        return RigidState.read(buf);
    }

    @Override
    public RigidState copy(RigidState rigidState) {
        return rigidState.copy();
    }

}
