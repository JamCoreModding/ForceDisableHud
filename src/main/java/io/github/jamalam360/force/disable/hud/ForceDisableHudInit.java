/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Jamalam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.jamalam360.force.disable.hud;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ForceDisableHudInit implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(
              literal("forcedisablehud")
                    .requires((s) -> s.hasPermissionLevel(2))
                    .then(
                          literal("enablehud")
                                .then(
                                      argument("targets", EntityArgumentType.players())
                                            .executes((context) -> {
                                                int i = 0;
                                                for (ServerPlayerEntity player : EntityArgumentType.getPlayers(context, "targets")) {
                                                    PacketByteBuf buf = PacketByteBufs.create();
                                                    buf.writeBoolean(true);
                                                    ServerPlayNetworking.send(player, NetworkChannels.DISABLED_HUD, buf);
                                                    i++;
                                                }

                                                context.getSource().sendFeedback(
                                                      Text.literal("Enabled HUD for " + i + " players."),
                                                      false
                                                );

                                                return 0;
                                            })
                                )
                    ).then(
                          literal("disablehud")
                                .then(
                                      argument("targets", EntityArgumentType.players())
                                            .executes((context) -> {
                                                int i = 0;
                                                for (ServerPlayerEntity player : EntityArgumentType.getPlayers(context, "targets")) {
                                                    PacketByteBuf buf = PacketByteBufs.create();
                                                    buf.writeBoolean(false);
                                                    ServerPlayNetworking.send(player, NetworkChannels.DISABLED_HUD, buf);
                                                    i++;
                                                }

                                                context.getSource().sendFeedback(
                                                      Text.literal("Disabled HUD for " + i + " players."),
                                                      false
                                                );

                                                return 0;
                                            })
                                )
                    )
        )));
    }
}
