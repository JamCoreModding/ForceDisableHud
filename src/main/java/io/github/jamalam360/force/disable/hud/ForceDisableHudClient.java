package io.github.jamalam360.force.disable.hud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

/**
 * @author Jamalam
 */
public class ForceDisableHudClient implements ClientModInitializer {
    public static boolean hudForceDisabled = false;

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkChannels.DISABLED_HUD, (client, handler, buf, responseSender) -> {
            boolean enabled = buf.readBoolean();
            MinecraftClient.getInstance().options.hudHidden = !enabled;
            hudForceDisabled = !enabled;
        });
    }
}
