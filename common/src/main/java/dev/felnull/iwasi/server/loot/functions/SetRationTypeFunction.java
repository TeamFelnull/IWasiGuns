package dev.felnull.iwasi.server.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.server.loot.RationLootType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class SetRationTypeFunction extends LootItemConditionalFunction {
    @NotNull
    private final RationLootType rationLootType;

    protected SetRationTypeFunction(LootItemCondition[] lootItemConditions, @NotNull RationLootType rationLootType) {
        super(lootItemConditions);
        this.rationLootType = rationLootType;
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext lootContext) {
        if (itemStack.is(IWGItems.RATION.get())) {
            itemStack = rationLootType.apply(itemStack);
        }
        return itemStack;
    }

    @Override
    public LootItemFunctionType getType() {
        return IWGLootItemFunctions.SET_RATION_TYPE.get();
    }

    public static LootItemConditionalFunction.Builder<?> rationType(RationLootType type) {
        return simpleBuilder(lootItemConditions -> new SetRationTypeFunction(lootItemConditions, type));
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SetRationTypeFunction> {
        @Override
        public void serialize(JsonObject jsonObject, SetRationTypeFunction lootItemConditionalFunction, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, lootItemConditionalFunction, jsonSerializationContext);
            jsonObject.addProperty("type", lootItemConditionalFunction.rationLootType.getSerializedName());
        }

        @Override
        public SetRationTypeFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditions) {
            var je = jsonObject.get("type");
            RationLootType lootType = RationLootType.B1;

            if (je != null && je.isJsonPrimitive() && je.getAsJsonPrimitive().isString()) {
                lootType = RationLootType.getByName(je.getAsString());
            }

            return new SetRationTypeFunction(lootItemConditions, lootType);
        }
    }
}
