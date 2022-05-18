package dev.felnull.iwasi.util;

import dev.felnull.iwasi.entity.MoreEntityHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
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
        List<HitEntityResultEntry> entries = new ArrayList<>();
        var aabb = entity.getBoundingBox().expandTowards(entity.getDeltaMovement());//.inflate(0.1f);
        var level = entity.level;

        for (Entity entryEntity : level.getEntities(entity, aabb, predicate)) {
            AABB aABB2 = entryEntity.getBoundingBox();//.inflate(0.3f);
            Optional<Vec3> optional = aABB2.clip(from, to);
            if (optional.isPresent()) {
                double dist = from.distanceToSqr(optional.get());

                int num = -1;
                for (int i = 0; i < entries.size(); i++) {
                    var entry = entries.get(i);
                    if (dist < entry.distance())
                        num = i;
                }
                var retEntry = new HitEntityResultEntry(dist, entryEntity, optional.get());
                if (num < 0) {
                    entries.add(retEntry);
                } else {
                    entries.set(num, retEntry);
                }
            }
        }

        for (HitEntityResultEntry entry : entries) {
            var ret = new MoreEntityHitResult(entry.entity(), entry.hit());
            boolean contHit = hit.apply(ret);
            if (contHit) {
                var retRev = getMoreEntityOnlyHitResult(entry.entity(), to, ret.getHitLocation(), 0.1f);
                if (retRev != null)
                    penetration.accept(retRev);
            } else {
                return ret.getHitLocation();
            }
        }

        return null;
    }

    private static MoreEntityHitResult getMoreEntityOnlyHitResult(Entity entity, Vec3 from, Vec3 to, float inflate) {
        AABB aABB2 = entity.getBoundingBox();//.inflate(inflate);
        Optional<Vec3> optional = aABB2.clip(from, to);
        return optional.map(vec3 -> new MoreEntityHitResult(entity, vec3)).orElse(null);
    }

    public static Vec3 nextPosition(Vec3 old, Vec3 move) {
        double x = old.x(), y = old.y(), z = old.z();
        x += move.x();
        y += move.y();
        z += move.z();
        return new Vec3(x, y, z);
    }

    private static record HitEntityResultEntry(double distance, Entity entity, Vec3 hit) {

    }
}
