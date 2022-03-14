package dev.felnull.iwasi.item;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.mojang.math.Vector3f;
import dev.felnull.iwasi.entity.TestBullet;
import dev.felnull.iwasi.util.JBulletUtil;
import dev.felnull.iwasi.util.PhysicsUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TestIWasiItem extends Item {
    public TestIWasiItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if (!level.isClientSide()) {
            for (int i = 0; i < (player.isCrouching() ? 10 : 1); i++) {
                var b = new TestBullet(level);
                b.setPos(player.position().add(0, 5, 0));
                b.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(b);
            }

            DynamicsWorld dynamicsWorld = JBulletUtil.createDynamicsWorld(PhysicsUtil.getDimensionGravity(level.dimension().location()));

            float mass = 1f;
            var shape = new BoxShape(new javax.vecmath.Vector3f(1, 1, 1));
            var vc = player.position();
            var rb = JBulletUtil.createRigidBody(mass, shape, new Vector3f((float) vc.x(), (float) vc.y(), (float) vc.z()), new Vector3f(19, 81, 36));
            dynamicsWorld.addRigidBody(rb);
            //  for (int i = 0; i < 20 * 10; i++) {
            //    JBulletUtil.tickStepSimulation(dynamicsWorld);
            // }
            var rot = JBulletUtil.getDegrees(rb);
            System.out.println(rot.x() + " " + rot.y() + " " + rot.z());
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }


}
