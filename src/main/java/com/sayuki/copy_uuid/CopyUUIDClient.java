package com.sayuki.copy_uuid;

import com.sayuki.copy_uuid.network.UUIDPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CopyUUIDClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(UUIDPayload.ID, (payload, context) -> {
            String uuid = payload.entityUuid().toString();
            String name = payload.entityName();

            context.client().execute(() -> {
                context.client().keyboard.setClipboard(uuid);
                context.player().sendMessage(
                        Text.literal("§aCopied UUID of §e" + name + "§a: §f" + uuid),
                        true
                );
            });
        });
    }
}