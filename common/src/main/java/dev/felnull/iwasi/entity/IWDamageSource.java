package dev.felnull.iwasi.entity;

import dev.felnull.iwasi.entity.bullet.Bullet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class IWDamageSource {
    public static DamageSource bullet(Bullet bullet, @Nullable Entity entity) {
        return new IndirectEntityDamageSource("bullet", bullet, entity);
    }
}
