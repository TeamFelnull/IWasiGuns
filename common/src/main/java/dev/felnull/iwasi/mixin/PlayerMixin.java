package dev.felnull.iwasi.mixin;

import dev.felnull.iwasi.data.GunPlayerTransData;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.data.IWPlayerData;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.item.GunItem;
import dev.felnull.iwasi.util.IWPlayerUtil;
import net.minecraft.core.NonNullList;
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

    @Shadow
    public int takeXpDelay;
    private int holdProgress;
    private int holdProgressOld;
    private HoldType lastHoldType = HoldType.NONE;
    private HoldType preHoldType = HoldType.NONE;
    private HoldType compHoldType = HoldType.NONE;
    private int holdGrace;
    private GunPlayerTransData mainHandGunTransOld = new GunPlayerTransData();
    private GunPlayerTransData offHandGunTransOld = new GunPlayerTransData();
    private NonNullList<ItemStack> mainHandTmpItems = NonNullList.of(ItemStack.EMPTY);
    private NonNullList<ItemStack> offHandTmpItems = NonNullList.of(ItemStack.EMPTY);
    private boolean mainHandTmpItemsUpdate;
    private boolean offHandTmpItemsUpdate;

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
    public HoldType getCompHoldType() {
        return compHoldType;
    }

    @Override
    public void setCompHoldType(HoldType compHoldType) {
        this.compHoldType = compHoldType;
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
    public GunPlayerTransData getGunTrans(InteractionHand hand) {
        return IWPlayerData.getGunTransData((Player) (Object) this, hand);
    }

    @Override
    public void setGunTrans(InteractionHand hand, GunPlayerTransData gunTransData) {
        if ((Object) this instanceof ServerPlayer serverPlayer)
            IWPlayerData.setGunTransData(serverPlayer, hand, gunTransData);
    }

    @Override
    public void setHoldType(HoldType holdType) {
        if ((Object) this instanceof ServerPlayer serverPlayer && getHoldProgress() >= IWPlayerUtil.getMaxHoldProgress(serverPlayer))
            IWPlayerData.setHold(serverPlayer, holdType);
    }

    @Override
    public GunPlayerTransData getGunTransOld(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? mainHandGunTransOld : offHandGunTransOld;
    }

    @Override
    public void setGunTransOld(InteractionHand hand, GunPlayerTransData gunTransData) {
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

    @Override
    public NonNullList<ItemStack> getTmpHandItems(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? mainHandTmpItems : offHandTmpItems;
    }

    @Override
    public void setTmpHandItems(InteractionHand hand, NonNullList<ItemStack> itemStacks) {
        if (hand == InteractionHand.MAIN_HAND) {
            mainHandTmpItems = itemStacks;
            if ((Object) this instanceof ServerPlayer)
                mainHandTmpItemsUpdate = true;
        } else {
            offHandTmpItems = itemStacks;
            if ((Object) this instanceof ServerPlayer)
                offHandTmpItemsUpdate = true;
        }
    }

    @Override
    public boolean isTmpHandItemsUpdate(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? mainHandTmpItemsUpdate : offHandTmpItemsUpdate;
    }

    @Override
    public void setTmpHandItemsUpdate(InteractionHand hand, boolean update) {
        if (hand == InteractionHand.MAIN_HAND) {
            mainHandTmpItemsUpdate = update;
        } else {
            offHandTmpItemsUpdate = update;
        }
    }
}
