package dev.felnull.iwasi.mixin.client;

import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
    private static boolean forceSlim = true;

    @Inject(method = "getModelName", at = @At("RETURN"), cancellable = true)
    private void getModelName(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(forceSlim ? "slim" : "default");
    }
}
