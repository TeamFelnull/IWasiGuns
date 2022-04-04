package dev.felnull.iwasi.mixin.client;

import net.minecraft.client.resources.DefaultPlayerSkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(DefaultPlayerSkin.class)
public class DefaultPlayerSkinMixin {
    private static final boolean alex = false;

    @Inject(method = "isAlexDefault", at = @At("RETURN"), cancellable = true)
    private static void isAlexDefault(UUID uUID, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(alex);
    }
}
