package dev.felnull.iwasi.entity.bullet;

import dev.felnull.iwasi.entity.IWDamageSource;
import dev.felnull.iwasi.entity.MoreEntityHitResult;
import dev.felnull.iwasi.util.IWPhysicsUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BulletHit {

    public static boolean hit(Bullet bullet, HitResult result) {
        var level = bullet.getLevel();
        if (result.getType() == HitResult.Type.ENTITY && result instanceof MoreEntityHitResult entityHitResult) {
            return hitEntity(level, bullet, entityHitResult);
        } else if (result.getType() == HitResult.Type.BLOCK && result instanceof BlockHitResult blockHitResult) {
            return hitBlock(level, bullet, blockHitResult);
        }
        return bullet.isAlive();
    }

    private static boolean hitBlock(Level level, Bullet bullet, BlockHitResult result) {
        var bs = level.getBlockState(result.getBlockPos());
        if (bs.is(Blocks.GLASS)) {
            if (!level.isClientSide())
                level.destroyBlock(result.getBlockPos(), true);
            return true;
        } else if (bs.getBlock() instanceof TntBlock) {
            if (!level.isClientSide()) {
                var entity = bullet.getOwner();
                TntBlock.explode(level, result.getBlockPos(), entity instanceof LivingEntity ? (LivingEntity) entity : null);
                level.removeBlock(result.getBlockPos(), false);
            }
            return true;
        } else if (bs.is(Blocks.WHITE_WOOL)) {
            return true;
        } else if (!bs.isAir()) {
            if (!level.isClientSide())
                bullet.discard();
            return false;
        }
        return false;
    }

    private static boolean hitEntity(Level level, Bullet bullet, MoreEntityHitResult result) {
        var entity = result.getEntity();
        if (!level.isClientSide()) {
            float dmg = IWPhysicsUtil.getDamage(0);
            if (dmg >= 0)
                entity.hurt(IWDamageSource.bullet(bullet, bullet.getOwner()), 3f);
            bullet.discard();
        }
        return false;
    }
}
