package dev.felnull.iwasi.mixin.client;

import dev.felnull.iwasi.data.GunTransData;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin implements IIWDataPlayer {
    private GunTransData mainHandGunTrans = new GunTransData();
    private GunTransData offHandGunTrans = new GunTransData();

    @Override
    public GunTransData getGunTrans(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? mainHandGunTrans : offHandGunTrans;
    }

    @Override
    public void setGunTrans(InteractionHand hand, GunTransData gunTransData) {
        if (hand == InteractionHand.MAIN_HAND) {
            mainHandGunTrans = gunTransData;
        } else {
            offHandGunTrans = gunTransData;
        }
    }
}
