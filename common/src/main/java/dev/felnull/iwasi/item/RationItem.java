package dev.felnull.iwasi.item;

import dev.felnull.iwasi.item.ration.Ration;
import dev.felnull.otyacraftengine.util.OEPlayerUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        List<ItemStack> stacks = Ration.getFoods(itemStack);

        for (ItemStack stack : stacks) {
            if (!stack.isEdible())
                continue;

            ItemStack food = stack.copy();
            int count = food.getCount();

            for (int i = 0; i < count; i++) {
                var ret = food.finishUsingItem(level, livingEntity);

                if (ret != food && livingEntity instanceof ServerPlayer player)
                    OEPlayerUtils.giveItem(player, ret);
            }
        }

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

        for (ItemStack food : foods) {
            list.add(Component.empty().append(food.getDisplayName()).append(" × " + food.getCount()));
        }

       /* if (foods.isEmpty())
            return;

        if (foods.size() == 1) {
            list.add(Component.translatable("item.iwasiguns.ration.desc", foods.get(0).getHoverName()));
        } else {
            List<Item> items = new ArrayList<>();
            MutableComponent comp = Component.empty();

            for (int i = 0; i < foods.size() - 1; i++) {
                ItemStack food = foods.get(i);
                if (items.contains(food.getItem()))
                    continue;
                items.add(food.getItem());
                comp = comp.append(food.getHoverName());
                if (i < foods.size() - 2)
                    comp.append(", ");
            }

            list.add(Component.translatable("item.iwasiguns.ration.desc.multiple", comp, foods.get(foods.size() - 1).getHoverName()));
        }*/
    }


}
