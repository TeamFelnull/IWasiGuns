package dev.felnull.iwasi.util;

import dev.felnull.iwasi.entity.MoreEntityHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
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

            if (ret != null && ((BlockHitResult) ret).getBlockPos().equals(last))
                ret = null;

            if (ret != null) {
                var entityRet = getContinuousEntityHitResult(entity, pre, ret.getLocation(), predicate, hit, penetration);
                if (entityRet != null)
                    return entityRet;

                last = ((BlockHitResult) ret).getBlockPos();

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
    }

    private static Vec3 getContinuousEntityHitResult(Entity entity, Vec3 from, Vec3 to, Predicate<Entity> predicate, Function<HitResult, Boolean> hit, Consumer<HitResult> penetration) {
        Vec3 retPos = null;
        boolean contHit;
        Entity last = null;
        do {
            contHit = false;
            var neh = getMoreEntityHitResult(entity.getLevel(), entity, from, to, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(0.1f), predicate, 0.1f);

            if (neh != null && neh.getEntity() == last)
                neh = null;

            if (neh != null) {
                last = neh.getEntity();

                contHit = hit.apply(neh);
                if (contHit) {
                    var retRev = getMoreEntityOnlyHitResult(last, to, neh.getHitLocation(), 0.1f);
                    if (retRev != null) {
                        from = retRev.getHitLocation();
                        penetration.accept(retRev);
                    } else {
                        from = null;
                    }
                } else {
                    retPos = neh.getLocation();
                }
            }
        } while (contHit && from != null);

        return retPos;
    }

    private static MoreEntityHitResult getMoreEntityOnlyHitResult(Entity entity, Vec3 from, Vec3 to, float inflate) {
        AABB aABB2 = entity.getBoundingBox().inflate(inflate);
        Optional<Vec3> optional = aABB2.clip(from, to);
        return optional.map(vec3 -> new MoreEntityHitResult(entity, vec3)).orElse(null);
    }

    public static MoreEntityHitResult getMoreEntityHitResult(Level level, Entity entity, Vec3 from, Vec3 to, AABB aABB, Predicate<Entity> predicate, float inflate) {
        double d = Double.MAX_VALUE;
        Entity retEntity = null;
        Vec3 loc = null;

        for (Entity entryEntity : level.getEntities(entity, aABB, predicate)) {
            AABB aABB2 = entryEntity.getBoundingBox().inflate(inflate);
            Optional<Vec3> optional = aABB2.clip(from, to);
            if (optional.isPresent()) {
                double e = from.distanceToSqr(optional.get());
                if (e < d) {
                    retEntity = entryEntity;
                    loc = optional.get();
                    d = e;
                }
            }
        }
        if (retEntity == null)
            return null;
        return new MoreEntityHitResult(retEntity, loc);
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
