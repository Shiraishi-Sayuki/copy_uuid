package com.sayuki.copy_uuid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@Environment(EnvType.CLIENT)
public class CopyUUIDClient implements ClientModInitializer {

    private boolean wasPressed = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null) return;

            LocalPlayer player = client.player;
            boolean isPressed = client.options.keyUse.isDown();

            if (isPressed && !wasPressed) {
                if (isHoldingStick(client)
                        && client.hitResult != null
                        && client.hitResult.getType() == HitResult.Type.ENTITY) {

                    Entity entity = ((EntityHitResult) client.hitResult).getEntity();
                    String uuid = entity.getStringUUID();
                    String name = entity.getName().getString();

                    client.keyboardHandler.setClipboard(uuid);
                    player.sendOverlayMessage(
                            Component.literal("§aCopied UUID of §e" + name + "§a: §f" + uuid)
                    );
                }
            }

            wasPressed = isPressed;
        });
    }

    private boolean isHoldingStick(Minecraft client) {
        return client.player.getMainHandItem().is(Items.STICK)
                || client.player.getOffhandItem().is(Items.STICK);
    }
}
