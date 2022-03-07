package dev.felnull.iwasi.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.iwasi.IWasi;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class IWEntityType {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(IWasi.MODID, Registry.ENTITY_TYPE_REGISTRY);
    public static final RegistrySupplier<EntityType<TestBullet>> TEST_BULLET = register("test_bullet", () -> EntityType.Builder.of((EntityType.EntityFactory<TestBullet>) TestBullet::new, MobCategory.MISC).sized(1f, 1f));

    private static <T extends Entity> RegistrySupplier<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builderSupplier) {
        return ENTITY_TYPES.register(name, () -> builderSupplier.get().build(name));
    }

    public static void init() {
        ENTITY_TYPES.register();
    }
}
