package dev.felnull.iwasi.server.handler;

import dev.felnull.otyacraftengine.server.event.LootTableEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTables;

public class ServerHandler {
    public static void init() {
        LootTableEvent.LOOT_TABLE_MODIFY.register(ServerHandler::lootTableModify);
    }

    private static void lootTableModify(LootTables lootManager, ResourceLocation id, LootTableEvent.LootTableModify modifyAccess) {
        //var i = LootItem.lootTableItem(IWGItems.RATION.get());
       /* var antennaPoolB = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(rare ? 0.364364f : 0.1919810f))
                .add(LootItem.lootTableItem(IMPItems.PARABOLIC_ANTENNA.get()).setWeight(1))
                .add(LootItem.lootTableItem(IMPItems.RADIO_ANTENNA.get()).setWeight(rare ? 1 : 4));
        modifyAccess.addLootPool(new ResourceLocation(IamMusicPlayer.MODID, "antenna"), antennaPoolB);*/

    }
}
