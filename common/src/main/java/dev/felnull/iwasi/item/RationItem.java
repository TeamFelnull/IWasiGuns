package dev.felnull.iwasi.item;

import dev.felnull.iwasi.item.ration.Ration;
import dev.felnull.iwasi.item.ration.RationEffectCategory;
import dev.felnull.iwasi.item.ration.RationFoodCategory;
import dev.felnull.otyacraftengine.util.OENbtUtils;
import dev.felnull.otyacraftengine.util.OEPlayerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RationItem extends Item {
    public RationItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        List<ItemStack> stacks = getFoods(itemStack);

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

        if (livingEntity instanceof Player player && player.getAbilities().instabuild)
            return superItem;

        return new ItemStack(IWGItems.RATION_CAN.get());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        List<ItemStack> foods = getFoods(itemStack);

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

    public static void setFoods(ItemStack stack, List<ItemStack> foodStacks) {
        OENbtUtils.writeList(stack.getOrCreateTag(), "foods", foodStacks.stream().filter(n -> !n.isEmpty()).toList(), stack1 -> stack1.save(new CompoundTag()));
        setCategory(stack, Ration.getRationCategories(foodStacks));
    }

    public static void setCategory(ItemStack stack, Pair<RationFoodCategory, RationEffectCategory> category) {
        var tag = stack.getOrCreateTag();
        OENbtUtils.writeEnum(tag, "food", category.getLeft());
        OENbtUtils.writeEnum(tag, "effect", category.getRight());
    }

    public static List<ItemStack> getFoods(ItemStack stack) {
        List<ItemStack> stacks = new ArrayList<>();

        if (stack.hasTag())
            OENbtUtils.readList(stack.getTag(), "foods", stacks, tag -> ItemStack.of((CompoundTag) tag), Tag.TAG_COMPOUND);

        return stacks;
    }

    public static Pair<RationFoodCategory, RationEffectCategory> getCategory(ItemStack stack) {
        if (!stack.hasTag())
            return Pair.of(RationFoodCategory.NONE, RationEffectCategory.MEDIOCRE);

        var tag = stack.getTag();
        var food = OENbtUtils.readEnum(tag, "food", RationFoodCategory.class, RationFoodCategory.NONE);
        var effect = OENbtUtils.readEnum(tag, "effect", RationEffectCategory.class, RationEffectCategory.MEDIOCRE);

        return Pair.of(food, effect);
    }
}
