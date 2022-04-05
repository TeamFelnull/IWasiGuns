package dev.felnull.iwasi.client.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.felnull.iwasi.client.data.ClientAction;
import dev.felnull.iwasi.client.data.ClientGunTrans;
import dev.felnull.iwasi.client.renderer.item.GunItemRenderer;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.otyacraftengine.client.event.ClientEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ClientHandler {
    public static void init() {
        ClientTickEvent.CLIENT_PRE.register(ClientHandler::onClientTick);
        ClientEvent.CHANGE_HAND_HEIGHT.register(ClientHandler::onChangeHandHeight);
        ClientEvent.HAND_ATTACK.register(ClientHandler::onHandAttack);
        ClientEvent.POSE_HUMANOID_ARM.register(ClientHandler::onPoseHumanoidArm);
    }

    private static void onClientTick(Minecraft instance) {
        ClientAction.tick();
        ClientGunTrans.tick();
    }

    private static EventResult onChangeHandHeight(InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        if (oldStack.getItem() instanceof GunItem && newStack.getItem() instanceof GunItem) {
            if (Objects.equals(GunItem.getTmpUUID(oldStack), GunItem.getTmpUUID(newStack)))
                return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    private static EventResult onHandAttack(@NotNull ItemStack itemStack) {
        if (itemStack.getItem() instanceof GunItem)
            return EventResult.interruptFalse();
        return EventResult.pass();
    }

    private static EventResult onPoseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity) {
        var item = livingEntity.getItemInHand(hand);
        if (item.getItem() instanceof GunItem gunItem) {
            var gir = GunItemRenderer.GUN_ITEM_RENDERERS.get(gunItem.getGun());
            if (gir != null)
                gir.poseHand(arm, model, item, livingEntity);
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }
}
