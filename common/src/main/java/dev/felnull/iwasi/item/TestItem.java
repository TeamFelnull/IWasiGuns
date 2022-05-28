package dev.felnull.iwasi.item;

import dev.felnull.otyacraftengine.client.util.OEShaderUtil;
import net.minecraft.resources.ResourceLocation;
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

        if (level.isClientSide()) {
            OEShaderUtil.loadShader(new ResourceLocation("shaders/post/phosphor.json"));
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
