package dev.felnull.iwasi.server.handler;

import dev.architectury.event.events.common.LootEvent;
import dev.felnull.fnjl.util.FNArrayUtil;
import dev.felnull.iwasi.item.IWGItems;
import dev.felnull.iwasi.server.loot.RationLootType;
import dev.felnull.iwasi.server.loot.functions.SetRationTypeFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ServerHandler {
    public static void init() {
        LootEvent.MODIFY_LOOT_TABLE.register(ServerHandler::modifyLootTable);
    }

    // /loot give @a loot minecraft:chests/simple_dungeon
    private static void modifyLootTable(LootTables lootTables, ResourceLocation id, LootEvent.LootTableModificationContext context, boolean builtin) {
        modifyRationLoot(lootTables, id, context);
    }

    private static void modifyRationLoot(LootTables lootTables, ResourceLocation id, LootEvent.LootTableModificationContext context) {
        if (isAnyMatch(id, BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.STRONGHOLD_CORRIDOR, BuiltInLootTables.STRONGHOLD_CROSSING)) {

            var rationPool = LootPool.lootPool().setRolls(UniformGenerator.between(1, 2))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 13f))
                    .add(rationLoot(RationLootType.B1).setWeight(15))
                    .add(rationLoot(RationLootType.B2).setWeight(15))
                    .add(rationLoot(RationLootType.B3).setWeight(15))
                    .add(rationLoot(RationLootType.GOLDEN).setWeight(1))
                    .add(rationLoot(RationLootType.DARK).setWeight(1))
                    .add(rationLoot(RationLootType.CAVE).setWeight(5));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.ABANDONED_MINESHAFT)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 20f))
                    .add(rationLoot(RationLootType.CAVE).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.JUNGLE_TEMPLE, BuiltInLootTables.DESERT_PYRAMID)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 30f))
                    .add(rationLoot(RationLootType.GOLDEN).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.ANCIENT_CITY)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 30f))
                    .add(rationLoot(RationLootType.DARK).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.BURIED_TREASURE)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 5f))
                    .add(rationLoot(RationLootType.SEA).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.UNDERWATER_RUIN_BIG, BuiltInLootTables.SHIPWRECK_SUPPLY)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 20f))
                    .add(rationLoot(RationLootType.SEA).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.FISHING_JUNK)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 100f))
                    .add(LootItem.lootTableItem(IWGItems.RATION_CAN.get()).setWeight(15))
                    .add(rationLoot(RationLootType.SEA).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.NETHER_BRIDGE)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 20f))
                    .add(rationLoot(RationLootType.NETHER).setWeight(10))
                    .add(rationLoot(RationLootType.GOLDEN).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.BASTION_OTHER)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 20f))
                    .add(rationLoot(RationLootType.NETHER).setWeight(1))
                    .add(rationLoot(RationLootType.GOLDEN).setWeight(2));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.PIGLIN_BARTERING)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 100f))
                    .add(rationLoot(RationLootType.NETHER).setWeight(1))
                    .add(rationLoot(RationLootType.GOLDEN).setWeight(2));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.END_CITY_TREASURE)) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 6f))
                    .add(rationLoot(RationLootType.END).setWeight(7))
                    .add(rationLoot(RationLootType.GOLDEN).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.IGLOO_CHEST)) {

            var rationPool = LootPool.lootPool().setRolls(UniformGenerator.between(1, 2))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 5f))
                    .add(rationLoot(RationLootType.B1).setWeight(1))
                    .add(rationLoot(RationLootType.B3).setWeight(1))
                    .add(rationLoot(RationLootType.B2).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.PILLAGER_OUTPOST)) {

            var rationPool = LootPool.lootPool().setRolls(UniformGenerator.between(0, 4))
                    .when(LootItemRandomChanceCondition.randomChance(1f))
                    .add(rationLoot(RationLootType.B1).setWeight(1))
                    .add(rationLoot(RationLootType.B3).setWeight(1))
                    .add(rationLoot(RationLootType.B2).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, BuiltInLootTables.PILLAGER_OUTPOST)) {

            var rationPool = LootPool.lootPool().setRolls(UniformGenerator.between(0, 4))
                    .when(LootItemRandomChanceCondition.randomChance(1f))
                    .add(rationLoot(RationLootType.B1).setWeight(1))
                    .add(rationLoot(RationLootType.B3).setWeight(1))
                    .add(rationLoot(RationLootType.B2).setWeight(1));
            context.addPool(rationPool);

        } else if (isAnyMatch(id, EntityType.PILLAGER.getDefaultLootTable())) {

            var rationPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(1f / 6f))
                    .add(rationLoot(RationLootType.B1).setWeight(1))
                    .add(rationLoot(RationLootType.B3).setWeight(1))
                    .add(rationLoot(RationLootType.B2).setWeight(1));
            context.addPool(rationPool);

        }
    }

    private static boolean isAnyMatch(ResourceLocation id, ResourceLocation... targets) {
        return FNArrayUtil.contains(targets, id);
    }

    private static LootPoolSingletonContainer.Builder<?> rationLoot(RationLootType rationLootType) {
        return LootItem.lootTableItem(IWGItems.RATION.get()).apply(SetRationTypeFunction.rationType(rationLootType));
    }
}
