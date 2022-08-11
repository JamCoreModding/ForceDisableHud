package io.github.jamalam360.force.disable.hud.mixin;

import io.github.jamalam360.force.disable.hud.ForceDisableHudClient;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Jamalam
 */

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(
            method = "isHudEnabled",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void forcedisablehud$checkForForceDisable(CallbackInfoReturnable<Boolean> cir) {
        if (ForceDisableHudClient.hudForceDisabled) {
            cir.setReturnValue(false);
        }
    }
}
