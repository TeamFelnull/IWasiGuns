package dev.felnull.iwasi.entity.bullet;

import dev.felnull.iwasi.entity.IWEntityType;
import dev.felnull.iwasi.util.IWPhysicsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Bullet extends Projectile {
    //速度(m/s);
    public static float DEFAULT_SPEED = 340f;

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
        HitResult hitResult = ProjectileUtil.getHitResult(this, this::canHitEntity);
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

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d = this.getX() + vec3.x;
        double e = this.getY() + vec3.y;
        double f = this.getZ() + vec3.z;
        this.updateRotation();
        float h;
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                float g = 0.25F;
                this.level.addParticle(ParticleTypes.BUBBLE, d - vec3.x * 0.25, e - vec3.y * 0.25, f - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
            }

            h = 0.8F;
        } else {
            h = 0.99F;
        }

        this.setDeltaMovement(vec3.scale(h));
        if (!this.isNoGravity()) {
            Vec3 vec32 = this.getDeltaMovement();
            this.setDeltaMovement(vec32.x, vec32.y - (double) IWPhysicsUtil.getGravity(level), vec32.z);
        }

        this.setPos(d, e, f);
    }


    @Override
    protected void defineSynchedData() {

    }
}
