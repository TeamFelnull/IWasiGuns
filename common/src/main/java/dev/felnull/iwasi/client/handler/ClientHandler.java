package dev.felnull.iwasi.client.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.client.renderer.item.TestGunItemRenderer;
import dev.felnull.iwasi.client.state.ClientHold;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.iwasi.item.gun.GunItem;
import dev.felnull.iwasi.networking.IWPackets;
import dev.felnull.otyacraftengine.client.event.ClientEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClientHandler {
    public static void init() {
        ClientEvent.POSE_HUMANOID_ARM.register(ClientHandler::onPoseHumanoidArm);
        TickEvent.PLAYER_POST.register(ClientHandler::onPlayerTick);
        ClientTickEvent.CLIENT_PRE.register(ClientHandler::onClientTick);
        ClientEvent.HAND_ATTACK.register(ClientHandler::onHandAttack);
    }

    private static EventResult onHandAttack(@NotNull ItemStack itemStack) {
        if (itemStack.getItem() instanceof GunItem)
            return EventResult.interruptFalse();
        return EventResult.pass();
    }

    private static EventResult onPoseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity) {
        var item = livingEntity.getItemInHand(hand);
        if (item.is(IWItems.TEST_GUN.get().asItem())) {
            TestGunItemRenderer.poseHand(arm, model, item);
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    private static void onPlayerTick(Player player) {
        if (!player.level.isClientSide() || !(player instanceof LocalPlayer)) return;

        NetworkManager.sendToServer(IWPackets.HOLD_INPUT, new IWPackets.HoldInputMessage(ClientHold.isHolding()).toFBB());
    }

    private static void onClientTick(Minecraft instance) {
        ClientHold.tick();
        TestGunItemRenderer.tick();
    }
}
