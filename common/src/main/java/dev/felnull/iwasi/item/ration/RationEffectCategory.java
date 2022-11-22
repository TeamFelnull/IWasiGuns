package dev.felnull.iwasi.item.ration;

import dev.felnull.iwasi.utils.IWGUtils;
import dev.felnull.otyacraftengine.util.OEItemUtils;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public enum RationEffectCategory implements StringRepresentable {
    HEALTHY("healthy", 0xEE2124),
    MEDIOCRE("mediocre", 0xA9602C),
    UNHEALTHY("unhealthy", 0x2CC3AC);
    private final String name;
    private final int color;

    RationEffectCategory(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public static Pair<RationEffectCategory, Float> getByItemStack(ItemStack stack) {
        if (stack.is(Items.POTION)) {
            Potion potion = PotionUtils.getPotion(stack);
            return getByMobEffects(potion.getEffects().stream().map(n -> Pair.of(n, 1f)).toList());
        } else {
            FoodProperties fp = OEItemUtils.getFoodProperties(stack, null);
            if (fp != null)
                return getByMobEffects(fp.getEffects().stream().map(n -> Pair.of(n.getFirst(), n.getSecond())).toList());
        }
        return Pair.of(MEDIOCRE, 0f);
    }

    private static Pair<RationEffectCategory, Float> getByMobEffects(List<Pair<MobEffectInstance, Float>> effects) {
        List<MobEffect> mobEffects = new ArrayList<>();
        float maxNum = 0;

        for (Pair<MobEffectInstance, Float> effectValue : effects) {
            var effect = effectValue.getLeft();
            float num = (float) effect.getDuration() / 250f;
            num += (float) effect.getAmplifier() / 10f;
            num *= effectValue.getRight();

            if (num > maxNum) {
                maxNum = num;
                mobEffects.clear();
                mobEffects.add(effect.getEffect());
            } else if (num == maxNum) {
                mobEffects.add(effect.getEffect());
            }
        }


        return Pair.of(IWGUtils.getMostContain(mobEffects).map(RationEffectCategory::getByMobEffect).orElse(MEDIOCRE), maxNum);
    }

    private static RationEffectCategory getByMobEffect(MobEffect effect) {
        return switch (effect.getCategory()) {
            case BENEFICIAL -> HEALTHY;
            case HARMFUL -> UNHEALTHY;
            case NEUTRAL -> MEDIOCRE;
        };
    }
}
