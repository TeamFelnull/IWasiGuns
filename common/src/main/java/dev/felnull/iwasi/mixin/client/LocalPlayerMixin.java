package dev.felnull.iwasi.mixin.client;

import dev.felnull.iwasi.client.data.ClientAction;
import dev.felnull.iwasi.data.HoldType;
import dev.felnull.iwasi.entity.IIWDataPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin implements IIWDataPlayer {
    @Override
    public HoldType getHoldType() {
        return HoldType.getIdeal(ClientAction.isHolding(), ((LocalPlayer) (Object) this).isSprinting(), getHoldGrace());
    }
}
