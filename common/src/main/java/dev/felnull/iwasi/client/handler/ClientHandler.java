package dev.felnull.iwasi.client.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.felnull.iwasi.client.data.ClientAction;
import dev.felnull.iwasi.client.data.InfoGunTrans;
import dev.felnull.iwasi.client.entity.IClientItemHandRenderEntity;
import dev.felnull.iwasi.client.renderer.item.GunItemRenderer;
import dev.felnull.iwasi.client.util.IWClientPlayerData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.otyacraftengine.client.event.ClientEvent;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.event.MoreEntityEvent;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void init() {
        ClientTickEvent.CLIENT_PRE.register(ClientHandler::onClientTick);
        ClientEvent.CHANGE_HAND_HEIGHT.register(ClientHandler::onChangeHandHeight);
        ClientEvent.HAND_ATTACK.register(ClientHandler::onHandAttack);
        ClientEvent.POSE_HUMANOID_ARM.register(ClientHandler::onPoseHumanoidArm);
        ClientEvent.POSE_HUMANOID_ARM_POST.register(ClientHandler::onPoseHumanoidArmPost);
        ClientEvent.EVALUATE_RENDER_HANDS.register(ClientHandler::onEvaluateRenderHands);
        TickEvent.PLAYER_PRE.register(ClientHandler::onPlayerTick);
        MoreEntityEvent.ENTITY_TICK.register(ClientHandler::onEntityTick);
    }

    private static EventResult onEntityTick(Entity entity) {
        if (!entity.level.isClientSide()) return EventResult.pass();
        if (entity instanceof LivingEntity livingEntity) {
            IClientItemHandRenderEntity handRender = (IClientItemHandRenderEntity) livingEntity;
            for (InteractionHand hand : InteractionHand.values()) {
                var hitem = livingEntity.getItemInHand(hand);
                if (hitem.getItem() instanceof GunItem) {
                    handRender.setLastHandItemOld(hand, handRender.getLastHandItem(hand));
                    handRender.setLastHandItem(hand, hitem.copy());
                } else {
                    handRender.setLastHandItemOld(hand, ItemStack.EMPTY);
                    handRender.setLastHandItem(hand, ItemStack.EMPTY);
                }
            }
        }
        return EventResult.pass();
    }

    private static void onPlayerTick(Player player) {
        if (!(player instanceof AbstractClientPlayer clientPlayer) || !player.getLevel().isClientSide()) return;

        var data = (IIWDataPlayer) player;
        for (InteractionHand hand : InteractionHand.values()) {
            var sd = IWPlayerData.getGunTransData(clientPlayer, hand);
            var cd = data.getGunTrans(hand);
            if (sd.updateId() != cd.updateId())
                data.setGunTrans(hand, sd);
        }

        if (player instanceof LocalPlayer) {
            data.setHoldType(HoldType.getIdeal(player, ClientAction.isHolding()));
        }
    }

    private static void onEvaluateRenderHands(ClientEvent.HandRenderSelectionWrapper handRenderSelection, LocalPlayer player, ClientEvent.EvaluateRenderHandSetter setter) {
        if (player != mc.player) return;
        boolean mgf = player.getMainHandItem().getItem() instanceof GunItem;
        boolean ogf = player.getOffhandItem().getItem() instanceof GunItem;

        var mgt = IWPlayerData.getGunTrans(player, InteractionHand.MAIN_HAND);
        if (mgf && mgt != null && mgt.isUseBothHand()) {
            setter.setEvaluate(ClientEvent.HandRenderSelectionWrapper.RENDER_MAIN_HAND_ONLY);
            return;
        }

        var ogt = IWPlayerData.getGunTrans(player, InteractionHand.OFF_HAND);
        if (ogf && ogt != null && ogt.isUseBothHand()) {
            setter.setEvaluate(ClientEvent.HandRenderSelectionWrapper.RENDER_OFF_HAND_ONLY);
        }
    }

    private static void onClientTick(Minecraft mc) {
        ClientAction.tick();
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
        if (!(livingEntity instanceof Player player)) return EventResult.pass();
        var opHand = OEEntityUtil.getOppositeHand(hand);
        var item = livingEntity.getItemInHand(hand);

        var opgt = IWPlayerData.getGunTrans(player, opHand);

        if (opgt == null || !opgt.isUseBothHand()) {
            if (item.getItem() instanceof GunItem gunItem) {
                var gir = GunItemRenderer.GUN_ITEM_RENDERERS.get(gunItem.getGun());
                if (gir != null)
                    gir.poseHand(arm, hand, model, item, livingEntity);
                return EventResult.interruptFalse();
            }
        }

        if (!isReplaceArm(livingEntity, opHand)) {
            var offItem = livingEntity.getItemInHand(opHand);
            if (offItem.getItem() instanceof GunItem gunItem) {
                var gir = GunItemRenderer.GUN_ITEM_RENDERERS.get(gunItem.getGun());
                if (gir != null)
                    gir.poseOppositeHand(arm, hand, model, item, livingEntity);
                return EventResult.interruptFalse();
            }
        }

        return EventResult.pass();
    }

    private static void onPoseHumanoidArmPost(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player)) return;
        var opHand = OEEntityUtil.getOppositeHand(hand);

        var opgt = IWPlayerData.getGunTrans(player, opHand);
        if (opgt != null && opgt.isUseBothHand()) {
            var item = livingEntity.getItemInHand(hand);
            if (item.getItem() instanceof GunItem gunItem) {
                var gir = GunItemRenderer.GUN_ITEM_RENDERERS.get(gunItem.getGun());
                if (gir != null)
                    gir.poseHand(arm, hand, model, item, livingEntity);
            }
        }

        if (isReplaceArm(livingEntity, opHand)) {
            var item = livingEntity.getItemInHand(opHand);
            if (item.getItem() instanceof GunItem gunItem) {
                var gir = GunItemRenderer.GUN_ITEM_RENDERERS.get(gunItem.getGun());
                if (gir != null)
                    gir.poseOppositeHand(arm, hand, model, item, livingEntity);
            }
        }
    }


    private static boolean isReplaceArm(LivingEntity livingEntity, InteractionHand hand) {
        if (!(livingEntity instanceof Player player)) return false;
        var gt = IWClientPlayerData.getGunTransData(player, hand, OERenderUtil.getPartialTicks());
        var gti = new InfoGunTrans(gt, player.getItemInHand(hand));
        return gti.isFirstStep() || gti.isLastStep();
    }
}
