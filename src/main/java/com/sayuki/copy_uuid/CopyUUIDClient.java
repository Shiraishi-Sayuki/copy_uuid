package com.sayuki.copy_uuid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

@Environment(EnvType.CLIENT)
public class CopyUUIDClient implements ClientModInitializer {

    private boolean wasPressed = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            ClientPlayerEntity player = client.player;
            boolean isPressed = client.options.useKey.isPressed();

            if (isPressed && !wasPressed) {
                if (isHoldingStick(client)
                        && client.crosshairTarget != null
                        && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {

                    Entity entity = ((EntityHitResult) client.crosshairTarget).getEntity();
                    String uuid = entity.getUuidAsString();
                    String name = entity.getName().getString();

                    client.keyboard.setClipboard(uuid);
                    player.sendMessage(
                            Text.literal("§aCopied UUID of §e" + name + "§a: §f" + uuid),
                            true
                    );
                }
            }

            wasPressed = isPressed;
        });
    }

    private boolean isHoldingStick(MinecraftClient client) {
        // Compare by registry ID string — works across all versions
        return isStick(client.player.getMainHandStack().getItem())
                || isStick(client.player.getOffHandStack().getItem());
    }

    private boolean isStick(Item item) {
        return Registries.ITEM.getId(item).toString().equals("minecraft:stick");
    }
}