package dev.felnull.iwasi.mixin;

import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.item.GunItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin implements IIWDataPlayer {
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot arg);

    private int holdProgress;
    private int holdProgressOld;
    private HoldType lastHoldType = HoldType.NONE;
    private HoldType preHoldType = HoldType.NONE;
    private int holdGrace;
    private GunTransData mainHandGunTransOld = new GunTransData();
    private GunTransData offHandGunTransOld = new GunTransData();

    @Inject(method = "setItemSlot", at = @At("HEAD"))
    private void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack, CallbackInfo ci) {
        var ths = (Player) (Object) this;
        if (!(ths instanceof ServerPlayer)) return;
        if (equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND) {
            if (itemStack.getItem() instanceof GunItem)
                GunItem.resetTmpUUID(itemStack);
            var old = getItemBySlot(equipmentSlot);
            if (old.getItem() instanceof GunItem)
                GunItem.resetTmpUUID(old);
        }
    }

    @Override
    public int getHoldProgress() {
        return holdProgress;
    }

    @Override
    public void setHoldProgress(int progress) {
        holdProgress = progress;
    }

    @Override
    public int getHoldProgressOld() {
        return holdProgressOld;
    }

    @Override
    public void setHoldProgressOld(int progress) {
        holdProgressOld = progress;
    }

    @Override
    public HoldType getLastHoldType() {
        return lastHoldType;
    }

    @Override
    public void setLastHoldType(HoldType holdType) {
        lastHoldType = holdType;
    }

    @Override
    public HoldType getPreHoldType() {
        return preHoldType;
    }

    @Override
    public void setPreHoldType(HoldType holdType) {
        preHoldType = holdType;
    }

    @Override
    public HoldType getHoldType() {
        return IWPlayerData.getHold((Player) (Object) this);
    }

    @Override
    public void setHoldGrace(int grace) {
        holdGrace = grace;
    }

    @Override
    public int getHoldGrace() {
        return holdGrace;
    }

    @Override
    public GunTransData getGunTrans(InteractionHand hand) {
        return IWPlayerData.getGunTransData((Player) (Object) this, hand);
    }

    @Override
    public void setGunTrans(InteractionHand hand, GunTransData gunTransData) {
        if ((Object) this instanceof ServerPlayer serverPlayer)
            IWPlayerData.setGunTransData(serverPlayer, hand, gunTransData);
    }

    @Override
    public void setHoldType(HoldType holdType) {
        if ((Object) this instanceof ServerPlayer serverPlayer && getHoldProgress() >= IWPlayerData.getMaxHoldProgress(serverPlayer))
            IWPlayerData.setHold(serverPlayer, holdType);
    }

    @Override
    public GunTransData getGunTransOld(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? mainHandGunTransOld : offHandGunTransOld;
    }

    @Override
    public void setGunTransOld(InteractionHand hand, GunTransData gunTransData) {
        if (hand == InteractionHand.MAIN_HAND) {
            mainHandGunTransOld = gunTransData;
        } else {
            offHandGunTransOld = gunTransData;
        }
    }

    @Override
    public boolean isPullTrigger() {
        return IWPlayerData.isPullTrigger((Player) (Object) this);
    }
}
