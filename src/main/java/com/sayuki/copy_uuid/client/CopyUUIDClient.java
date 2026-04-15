package com.sayuki.copy_uuid.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;

@OnlyIn(Dist.CLIENT)
public class CopyUUIDClient {

    public static void register(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(CopyUUIDClient::onUseKey);
    }

    private static void onUseKey(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isUseItem()) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        if (!isHoldingStick(client)) return;

        if (client.hitResult != null && client.hitResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) client.hitResult).getEntity();
            String uuid = entity.getStringUUID();
            String name = entity.getName().getString();

            client.keyboardHandler.setClipboard(uuid);
            client.player.sendOverlayMessage(
                    Component.literal("§aCopied UUID of §e" + name + "§a: §f" + uuid)
            );
        }
    }

    private static boolean isHoldingStick(Minecraft client) {
        return isStick(client.player.getMainHandItem().getItem())
                || isStick(client.player.getOffhandItem().getItem());
    }

    private static boolean isStick(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString().equals("minecraft:stick");
    }
}
