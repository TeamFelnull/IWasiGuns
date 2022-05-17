package dev.felnull.iwasi.entity.bullet;

import dev.felnull.iwasi.entity.IWEntityType;
import dev.felnull.iwasi.entity.MoreEntityHitResult;
import dev.felnull.iwasi.util.IWProjectileUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Bullet extends Projectile {
    //速度(m/s);
    public static double DEFAULT_SPEED = 1600;//340f;

    public Bullet(EntityType<? extends Bullet> entityType, Level level) {
        super(entityType, level);
    }

    public Bullet(Level level, LivingEntity livingEntity) {
        super(IWEntityType.BULLET.get(), level);
        setOwner(livingEntity);
    }

    @Override
    public void tick() {
        super.tick();
        var nextPos = IWProjectileUtil.onContinuousHitResult(this, this::canHitEntity, this::onContinuousHit, this::onPenetration);

        this.checkInsideBlocks();
        this.updateRotation();

        var move = this.getDeltaMovement();

        /* float h;
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                float g = 0.25F;
                this.level.addParticle(ParticleTypes.BUBBLE, np.x() - move.x * 0.25, np.y() - move.y * 0.25, np.z() - move.z * 0.25, move.x, move.y, move.z);
            }
            h = 0.8F;
        } else {
            h = 0.99F;
        }

       this.setDeltaMovement(move.scale(h));
        if (!this.isNoGravity()) {
            Vec3 vec32 = this.getDeltaMovement();
            this.setDeltaMovement(vec32.x, vec32.y - (double) IWPhysicsUtil.getGravity(level), vec32.z);
        }*/

        this.setPos(nextPos);
    }


    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!level.isClientSide()) {
            var loc = hitResult.getLocation();

            if (hitResult instanceof MoreEntityHitResult entityHitResult)
                loc = entityHitResult.getHitLocation();

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult bhr = (BlockHitResult) hitResult;
                var bs = level.getBlockState(bhr.getBlockPos());
                if (bs.is(Blocks.GLASS)) {
//                    level.explode(this, loc.x(), loc.y(), loc.z(), 4.0f, Explosion.BlockInteraction.BREAK);
                    level.destroyBlock(((BlockHitResult) hitResult).getBlockPos(), true);
                } else if (!bs.isAir()) {
                    discard();
                }
                //   if (!bs.isAir()) {
                //      level.destroyBlock(((BlockHitResult) hitResult).getBlockPos(), true);
                //       discard();
                //   }
            } else if (hitResult.getType() == HitResult.Type.ENTITY && hitResult instanceof MoreEntityHitResult) {
                EntityHitResult ehr = (EntityHitResult) hitResult;

                ehr.getEntity().kill();
                //  discard();
            }

            //level.explode(this, loc.x(), loc.y(), loc.z(), 4.0f, Explosion.BlockInteraction.BREAK);
            //discard();
            /*var vlg = new Villager(EntityType.VILLAGER, level);
            vlg.setPos(loc);
            level.addFreshEntity(vlg);*/

            /*var t = new Turtle(EntityType.TURTLE, level);
            t.setPos(loc);
            t.setNoGravity(true);
            t.setNoAi(true);
            t.setBaby(true);
            level.addFreshEntity(t);*/
        }
    }

    protected boolean onContinuousHit(HitResult hitResult) {
        boolean bl = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            BlockState blockState = this.level.getBlockState(blockPos);
            if (blockState.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockPos);
                bl = true;
            } else if (blockState.is(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.level.getBlockEntity(blockPos);
                if (blockEntity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockPos, blockState, this, (TheEndGatewayBlockEntity) blockEntity);
                }
                bl = true;
            }
        }

        if (hitResult.getType() != HitResult.Type.MISS && !bl) {
            this.onHit(hitResult);
        }

        return isAlive();
    }

    protected void onPenetration(HitResult hitResult) {
        if (!level.isClientSide()) {
            /*var loc = hitResult.getLocation();

            if (hitResult instanceof MoreEntityHitResult entityHitResult)
                loc = entityHitResult.getHitLocation();

            var t = new Turtle(EntityType.TURTLE, level);
            t.setPos(loc);
            t.setNoGravity(true);
            t.setNoAi(true);
            t.setBaby(true);
            level.addFreshEntity(t);*/
        }
    }


    @Override
    protected void defineSynchedData() {

    }

    //角度、速度(m/t)
    public void shot(Vec3 angle, double speed) {
        setDeltaMovement(angle.normalize().scale(speed));
    }
}
