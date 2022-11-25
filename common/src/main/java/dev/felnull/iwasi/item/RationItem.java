package dev.felnull.iwasi.item;

import dev.felnull.iwasi.item.ration.Ration;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RationItem extends Item {
    public RationItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        var foods = Ration.getFoods(itemStack);
        for (ItemStack food : foods) {
            if (food.getItem().isFoil(food))
                return true;
        }
        return super.isFoil(itemStack);
    }

    @Override
    public Rarity getRarity(ItemStack itemStack) {
        Rarity rarity = super.getRarity(itemStack);
        var foods = Ration.getFoods(itemStack);

        for (ItemStack food : foods) {
            if (rarity == Rarity.EPIC)
                break;
            var fr = food.getRarity();

            if (fr.ordinal() > rarity.ordinal())
                rarity = fr;
        }

        return rarity;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        List<ItemStack> stacks = Ration.getFoods(itemStack);

        for (ItemStack stack : stacks)
            Ration.eatFood(stack, level, livingEntity);

        var superItem = super.finishUsingItem(itemStack, level, livingEntity);

        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (livingEntity instanceof Player player && player.getAbilities().instabuild)
            return superItem;

        if (itemStack.isEmpty())
            return new ItemStack(IWGItems.RATION_CAN.get());

        if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
            ItemStack retStack = new ItemStack(IWGItems.RATION_CAN.get());
            if (!player.getInventory().add(retStack))
                player.drop(retStack, false);
        }

        return itemStack;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        List<ItemStack> foods = Ration.getFoods(itemStack);

        if (foods.isEmpty())
            return;

        if (foods.size() == 1) {
            list.add(Component.translatable("item.iwasiguns.ration.contain").append(" ").append(getContainDisplay(foods.get(0))));
            return;
        }

        list.add(Component.translatable("item.iwasiguns.ration.contains"));

        for (ItemStack food : foods) {
            list.add(Component.literal(" ").append(getContainDisplay(food)));
        }
    }

    private static MutableComponent getContainDisplay(ItemStack stack) {
        MutableComponent mutableComponent = stack.getDisplayName().copy();
        mutableComponent.append(" x").append(String.valueOf(stack.getCount()));
        return mutableComponent;
    }

    @Override
    public void fillItemCategory(CreativeModeTab creativeModeTab, NonNullList<ItemStack> nonNullList) {
        if (!this.allowedIn(creativeModeTab))
            return;

        nonNullList.add(Ration.createB1Unit(new ItemStack(this)));
        nonNullList.add(Ration.createB2Unit(new ItemStack(this)));
        nonNullList.add(Ration.createB3Unit(new ItemStack(this)));
    }
}
