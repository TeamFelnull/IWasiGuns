package dev.felnull.iwasi.mixin.client;

import dev.felnull.iwasi.client.data.ClientAction;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import dev.felnull.iwasi.util.IWPlayerUtil;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin implements IIWDataPlayer {
    private HoldType clientHoldType;

    @Override
    public HoldType getHoldType() {
        return clientHoldType;
    }

    @Override
    public void setHoldType(HoldType holdType) {
        if (getHoldProgress() >= IWPlayerUtil.getMaxHoldProgress((LocalPlayer) (Object) this))
            this.clientHoldType = holdType;
    }

    @Override
    public boolean isPullTrigger() {
        return ClientAction.isPullTrigger();
    }
}
