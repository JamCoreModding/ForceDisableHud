package io.github.jamalam360.force.disable.hud.mixin;

import io.github.jamalam360.force.disable.hud.ForceDisableHudClient;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Jamalam
 */

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Inject(
            method = "onKey",
            at = @At("HEAD"),
            cancellable = true
    )
    private void forcedisablehud$checkForForceDisable(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getWindow().getHandle() == window && key == 290 && ForceDisableHudClient.hudForceDisabled) {
            ci.cancel();
        }
    }
}
