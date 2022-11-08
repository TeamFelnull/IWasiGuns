package dev.felnull.iwasi.item;

import dev.felnull.otyacraftengine.util.OENbtUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class RationItem extends Item {
    public RationItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        var superItem = super.finishUsingItem(itemStack, level, livingEntity);

        if (livingEntity instanceof Player player && player.getAbilities().instabuild)
            return superItem;

        return new ItemStack(IWGItems.RATION_CAN.get());
    }

    public static void setFoods(ItemStack stack, List<ItemStack> foodStacks) {
        OENbtUtils.writeList(stack.getOrCreateTag(), "foods", foodStacks.stream().filter(n -> !n.isEmpty()).toList(), stack1 -> stack1.save(new CompoundTag()));
    }

    public static List<ItemStack> getFoods(ItemStack stack) {
        List<ItemStack> stacks = new ArrayList<>();

        if (stack.hasTag())
            OENbtUtils.readList(stack.getTag(), "foods", stacks, tag -> ItemStack.of((CompoundTag) tag), Tag.TAG_COMPOUND);

        return stacks;
    }
}
