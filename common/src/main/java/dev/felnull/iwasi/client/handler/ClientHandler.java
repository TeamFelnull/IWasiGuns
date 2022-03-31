package dev.felnull.iwasi.client.handler;

import dev.architectury.event.EventResult;
import dev.felnull.iwasi.client.renderer.item.TestGunItemRenderer;
import dev.felnull.iwasi.item.IWItems;
import dev.felnull.otyacraftengine.client.event.ClientEvent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class ClientHandler {
    public static void init() {
        ClientEvent.POSE_HUMANOID_ARM.register(ClientHandler::onPoseHumanoidArm);
    }

    private static EventResult onPoseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity) {
        var item = livingEntity.getItemInHand(hand);
        if (item.is(IWItems.TEST_GUN.get().asItem())) {
            TestGunItemRenderer.poseHand(arm, model, item);
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }
}
