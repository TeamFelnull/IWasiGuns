package dev.felnull.iwasi.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class IWProjectileUtil {
    public static void onContinuousHitResult(Entity entity, Predicate<Entity> predicate, Consumer<HitResult> hit) {
        var level = entity.level;
        var pre = entity.position();
        var post = nextPosition(pre, entity.getDeltaMovement());
        HitResult ret;
        BlockPos last = null;

        do {
            var conted = new ClipContext(pre, post, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            ret = level.clip(conted);

            if (((BlockHitResult) ret).getBlockPos().equals(last))
                ret = null;

            if (ret != null) {
                last = ((BlockHitResult) ret).getBlockPos();
            }

            if (ret != null) {
                var state = level.getBlockState(((BlockHitResult) ret).getBlockPos());
                var shape = conted.getBlockShape(state, level, ((BlockHitResult) ret).getBlockPos());
                var rev = reversPosition(pre, ((BlockHitResult) ret).getDirection(), shape, ((BlockHitResult) ret).getBlockPos());

                if (rev != null) {
                    var retRev = level.clipWithInteractionOverride(rev, post, ((BlockHitResult) ret).getBlockPos(), shape, state);
                    //  if (retRev != null) {
                    if (!level.isClientSide()) {
                        var t = new Turtle(EntityType.TURTLE, level);
                        t.setPos(rev);
                        t.setNoGravity(true);
                        t.setNoAi(true);
                        t.setBaby(true);
                        level.addFreshEntity(t);
                        //  }
                    }
                }
                pre = ret.getLocation();
                hit.accept(ret);
            }
        } while (entity.isAlive() && ret != null);

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

    private static Vec3 reversPosition(Vec3 target, Direction direction, VoxelShape shape, BlockPos pos) {
        if (shape.isEmpty()) return null;
        double tdT = (shapeDirectionValue(shape, direction) + pos.get(direction.getAxis())) - target.get(direction.getAxis());
        double ovT = shapeDirectionValue(shape, direction.getOpposite());

        var dirR = Direction.values()[(direction.ordinal() + 2) % Direction.values().length];
        var dirL = Direction.values()[(direction.ordinal() + 4) % Direction.values().length];

        double tdR = (shapeDirectionValue(shape, dirR) + pos.get(dirR.getAxis())) - target.get(dirR.getAxis());
        double ovR = shapeDirectionValue(shape, dirR.getOpposite());

        double tdL = (shapeDirectionValue(shape, dirL) + pos.get(dirL.getAxis())) - target.get(dirL.getAxis());
        double ovL = shapeDirectionValue(shape, dirL.getOpposite());

        var vec = target.with(direction.getAxis(), pos.get(direction.getAxis()) + ovT + tdT);
        vec = vec.with(dirR.getAxis(), pos.get(dirR.getAxis()) + ovR + tdR);
        vec = vec.with(dirL.getAxis(), pos.get(dirL.getAxis()) + ovL + tdL);
        return vec;
    }

    private static double shapeDirectionValue(VoxelShape shape, Direction direction) {
        return switch (direction) {
            case DOWN -> shape.min(Direction.Axis.Y);
            case UP -> shape.max(Direction.Axis.Y);
            case NORTH -> shape.min(Direction.Axis.Z);
            case SOUTH -> shape.max(Direction.Axis.Z);
            case WEST -> shape.min(Direction.Axis.X);
            case EAST -> shape.max(Direction.Axis.X);
        };
    }

    public static Vec3 nextPosition(Vec3 old, Vec3 move) {
        double x = old.x(), y = old.y(), z = old.z();
        x += move.x();
        y += move.y();
        z += move.z();
        return new Vec3(x, y, z);
    }
}
