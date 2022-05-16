package dev.felnull.iwasi.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class IWProjectileUtil {
    public static Vec3 onContinuousHitResult(Entity entity, Predicate<Entity> predicate, Function<HitResult, Boolean> hit, Consumer<HitResult> penetration) {
        var level = entity.level;
        var pre = entity.position();
        var post = nextPosition(pre, entity.getDeltaMovement());
        Vec3 retPos = null;
        HitResult ret;
        BlockPos last = null;

        boolean contHit;

        do {
            contHit = false;

            var conted = new ClipContext(pre, post, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            ret = level.clip(conted);

            if (((BlockHitResult) ret).getBlockPos().equals(last)) ret = null;

            if (ret != null) {
                last = ((BlockHitResult) ret).getBlockPos();
            }

            if (ret != null) {
                var state = level.getBlockState(((BlockHitResult) ret).getBlockPos());
                var shape = conted.getBlockShape(state, level, ((BlockHitResult) ret).getBlockPos());

                contHit = hit.apply(ret);
                if (contHit) {
                    var retRev = level.clipWithInteractionOverride(post, ret.getLocation(), ((BlockHitResult) ret).getBlockPos(), shape, state);
                    if (retRev != null) {
                        pre = retRev.getLocation();
                        penetration.accept(retRev);
                    } else {
                        pre = null;
                    }
                } else {
                    retPos = ret.getLocation();
                }
            }
        } while (contHit && pre != null);

        return retPos != null ? retPos : post;

        /*var level = entity.level;
        var pre = entity.position();
        var post = nextPosition(pre, entity.getDeltaMovement());

        HitResult ret = level.clip(new ClipContext(pre, post, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        if (ret.getType() != HitResult.Type.MISS)
            post = ret.getLocation();

        HitResult ret2 = ProjectileUtil.getEntityHitResult(level, entity, pre, post, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1.0), predicate);
        if (ret2 != null)
            ret = ret2;

        hit.accept(ret);*/
    }

    private static void testKame(Level level, Vec3 pos) {
        if (!level.isClientSide()) {
            var t = new Turtle(EntityType.TURTLE, level);
            t.setPos(pos);
            t.setNoGravity(true);
            t.setNoAi(true);
            t.setBaby(true);
            level.addFreshEntity(t);
        }
    }


    public static Vec3 nextPosition(Vec3 old, Vec3 move) {
        double x = old.x(), y = old.y(), z = old.z();
        x += move.x();
        y += move.y();
        z += move.z();
        return new Vec3(x, y, z);
    }
}
