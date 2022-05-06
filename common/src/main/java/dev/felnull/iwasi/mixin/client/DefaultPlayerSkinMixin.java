package dev.felnull.iwasi.mixin.client;

import net.minecraft.client.resources.DefaultPlayerSkin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DefaultPlayerSkin.class)
public class DefaultPlayerSkinMixin {
    /*@Inject(method = "isAlexDefault", at = @At("RETURN"), cancellable = true)
    private static void isAlexDefault(UUID uUID, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(IWasi.getConfig().testForceAlex);
    }*/
}
