package dev.felnull.iwasi.item;

import dev.felnull.iwasi.data.IWPlayerData;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TestItem extends Item {
    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if (!level.isClientSide()) {
            IWPlayerData.setTmpHandItems((ServerPlayer) player, interactionHand, NonNullList.of(ItemStack.EMPTY, new ItemStack(IWItems.GLOCK_17_MAGAZINE.get())));
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
