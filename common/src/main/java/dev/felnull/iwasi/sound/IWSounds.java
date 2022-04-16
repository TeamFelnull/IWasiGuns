package dev.felnull.iwasi.sound;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasi;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class IWSounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(IWasi.MODID, Registry.SOUND_EVENT_REGISTRY);
    public static final RegistrySupplier<SoundEvent> SHOT_1 = register("gun.shot.shot_1");
    public static final RegistrySupplier<SoundEvent> SHOT_2 = register("gun.shot.shot_2");
    public static final RegistrySupplier<SoundEvent> SHOT_NO_AMMO = register("gun.shot.shot_no_ammo");

    public static final RegistrySupplier<SoundEvent> HOLD_1 = register("gun.hold.hold_1");
    public static final RegistrySupplier<SoundEvent> HOLD_2 = register("gun.hold.hold_2");

    public static final RegistrySupplier<SoundEvent> MAGAZINE_SET = register("gun.reload.magazine_set");
    public static final RegistrySupplier<SoundEvent> MAGAZINE_REMOVE = register("gun.reload.magazine_remove");


    private static RegistrySupplier<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(IWasi.MODID, name)));
    }

    public static void init() {
        SOUND_EVENTS.register();
    }
}
