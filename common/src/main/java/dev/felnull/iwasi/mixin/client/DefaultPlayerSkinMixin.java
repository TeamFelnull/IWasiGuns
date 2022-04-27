package dev.felnull.iwasi.mixin.client;

import dev.felnull.iwasi.IWasi;
import net.minecraft.client.resources.DefaultPlayerSkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(DefaultPlayerSkin.class)
public class DefaultPlayerSkinMixin {
    @Inject(method = "isAlexDefault", at = @At("RETURN"), cancellable = true)
    private static void isAlexDefault(UUID uUID, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(IWasi.getConfig().testForceAlex);
    }
}
