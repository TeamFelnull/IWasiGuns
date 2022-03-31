package dev.felnull.iwasi.item;

import dev.felnull.iwasi.entity.TestBullet;
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
            for (int i = 0; i < (player.isCrouching() ? 30 : 1); i++) {
                var b = new TestBullet(level);
                b.setPos(player.position().add(0, 5, 0));
               // b.setYRot(player.getYHeadRot());
               // b.setXRot(0);
                //  b.setXRot(player.yRotO);

                System.out.println(PhysicsUtil.wrapZeroDegrees(player.getYHeadRot()));

                b.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(b);
            }
        }

        //player.setXRot(0);
        //  player.setYRot(0);

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

}
