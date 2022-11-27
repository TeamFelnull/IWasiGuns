package dev.felnull.iwasi.item.ration;

import com.google.common.collect.ImmutableList;
import dev.felnull.iwasi.item.IWGItemTags;
import dev.felnull.iwasi.item.RationItem;
import dev.felnull.otyacraftengine.util.OEItemUtils;
import dev.felnull.otyacraftengine.util.OENbtUtils;
import dev.felnull.otyacraftengine.util.OEPlayerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Ration {

    public static Pair<RationFoodCategory, RationEffectCategory> getRationCategories(List<ItemStack> foods) {

        Map<RationEffectCategory, Float> effectCategories = new HashMap<>();
        Map<RationFoodCategory, Float> foodCategories = new HashMap<>();

        for (ItemStack food : foods) {
            var ecatE = RationEffectCategory.getByItemStack(food);
            var ecat = ecatE.getLeft();
            float ecatCount = 0;
            var ecatCt = effectCategories.get(ecat);
            if (ecatCt != null) ecatCount = ecatCt;

            effectCategories.put(ecat, ecatCount + food.getCount() * Math.max(ecatE.getRight(), 1f));

            var fcatE = RationFoodCategory.getByItemStack(food);
            var fcat = fcatE.getLeft();

            float fcatCount = 0;

            var fcatCt = foodCategories.get(fcat);
            if (fcatCt != null) fcatCount = fcatCt;

            foodCategories.put(fcat, fcatCount + food.getCount() * Math.max(fcatE.getRight(), 1f));
        }

        var mostEffect = effectCategories.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).map(Map.Entry::getKey);
        var mostFood = foodCategories.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).map(Map.Entry::getKey);

        return Pair.of(mostFood.orElse(RationFoodCategory.NONE), mostEffect.orElse(RationEffectCategory.MEDIOCRE));
    }

    public static ItemStack create(ItemStack stack, List<ItemStack> foods) {
        setFoods(stack, foods);
        var cat = Ration.getRationCategories(foods);
        setCategory(stack, cat.getLeft(), cat.getRight());
        return stack;
    }

    public static List<ItemStack> sortFoods(List<ItemStack> foods) {
        List<ItemStack> ret = new ArrayList<>(List.copyOf(OEItemUtils.overlapItemStacks(foods)));
        ret.sort(Comparator.comparingInt(value -> {
            if (canContainDrink(value))
                return 1;
            return 0;
        }));
        return ret;
    }

    public static void setFoods(ItemStack stack, List<ItemStack> foodStacks) {
        var tag = stack.getOrCreateTag();
        var rationTag = tag.getCompound("ration");

        OENbtUtils.writeList(rationTag, "foods", foodStacks.stream().filter(n -> !n.isEmpty()).toList(), stack1 -> stack1.save(new CompoundTag()));

        tag.put("ration", rationTag);
    }

    public static void setCategory(ItemStack stack, RationFoodCategory foodCategory, RationEffectCategory effectCategory) {
        var tag = stack.getOrCreateTag();
        var rationTag = tag.getCompound("ration");

        OENbtUtils.writeEnum(rationTag, "food_category", foodCategory);
        OENbtUtils.writeEnum(rationTag, "effect_category", effectCategory);

        tag.put("ration", rationTag);
    }

    public static List<ItemStack> getFoods(ItemStack stack) {
        List<ItemStack> stacks = new ArrayList<>();

        if (stack.hasTag())
            OENbtUtils.readList(stack.getTag().getCompound("ration"), "foods", stacks, tag -> ItemStack.of((CompoundTag) tag), Tag.TAG_COMPOUND);

        return stacks;
    }

    public static Pair<RationFoodCategory, RationEffectCategory> getCategory(ItemStack stack) {
        if (!stack.hasTag())
            return Pair.of(RationFoodCategory.NONE, RationEffectCategory.MEDIOCRE);

        var tag = stack.getTag().getCompound("ration");
        var food = OENbtUtils.readEnum(tag, "food_category", RationFoodCategory.class, RationFoodCategory.NONE);
        var effect = OENbtUtils.readEnum(tag, "effect_category", RationEffectCategory.class, RationEffectCategory.MEDIOCRE);

        return Pair.of(food, effect);
    }

    public static void eatFood(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (OEItemUtils.getFoodProperties(stack, livingEntity) != null || stack.is(IWGItemTags.DRINKS)) {
            ItemStack food = stack.copy();
            int count = food.getCount();

            for (int i = 0; i < count; i++) {
                var ret = food.finishUsingItem(level, livingEntity);

                if (ret != food && livingEntity instanceof ServerPlayer player)
                    OEPlayerUtils.giveItem(player, ret);
            }
        } else if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof CakeBlock) {
            if (!level.isClientSide() && livingEntity instanceof Player player) {
                for (int i = 0; i < CakeBlock.MAX_BITES + 1; i++) {
                    player.awardStat(Stats.EAT_CAKE_SLICE);
                    player.getFoodData().eat(2, 0.1F);
                }
            }
        }
    }

    public static boolean canContainDrink(ItemStack stack) {
        return stack.is(IWGItemTags.DRINKS);
    }

    public static boolean canContainFood(ItemStack stack) {
        if (canContainDrink(stack))
            return false;

        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof CakeBlock)
            return true;

        FoodProperties fp = OEItemUtils.getFoodProperties(stack, null);
        return fp != null && !(stack.getItem() instanceof RationItem);
    }

    public static boolean canContainDrink(Item item) {
        return item.builtInRegistryHolder().is(IWGItemTags.DRINKS);
    }

    public static boolean canContainFood(Item item) {
        if (canContainDrink(item))
            return false;

        if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof CakeBlock)
            return true;

        FoodProperties fp = item.getFoodProperties();
        return fp != null && !(item instanceof RationItem);
    }

    public static ItemStack createB1Unit(ItemStack stack) {
        List<ItemStack> foods = ImmutableList.of(Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.PORKCHOP, Items.COOKED_COD, Items.COOKIE, Items.BREAD).stream().map(ItemStack::new).toList();
        setFoods(stack, foods);

        setCategory(stack, RationFoodCategory.MEAT, RationEffectCategory.MEDIOCRE);
        return stack;
    }

    public static ItemStack createB2Unit(ItemStack stack) {
        List<ItemStack> foods = ImmutableList.of(Items.RABBIT_STEW, Items.MUSHROOM_STEW, Items.BEETROOT_SOUP).stream().map(ItemStack::new).toList();
        setFoods(stack, foods);

        setCategory(stack, RationFoodCategory.VEGETABLE, RationEffectCategory.MEDIOCRE);
        return stack;
    }

    public static ItemStack createB3Unit(ItemStack stack) {
        List<ItemStack> foods = new ArrayList<>(ImmutableList.of(Items.PORKCHOP, Items.COOKED_CHICKEN, Items.COOKED_CHICKEN).stream().map(ItemStack::new).toList());
        foods.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
        setFoods(stack, foods);

        setCategory(stack, RationFoodCategory.DRINK, RationEffectCategory.MEDIOCRE);
        return stack;
    }

}
