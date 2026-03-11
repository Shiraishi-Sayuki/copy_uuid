package com.sayuki.copy_uuid;

import com.sayuki.copy_uuid.network.UUIDPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyUUIDMod implements ModInitializer {

    public static final String MOD_ID = "copy_uuid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playS2C().register(UUIDPayload.ID, UUIDPayload.CODEC);

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient() && player.getStackInHand(hand).isOf(Items.STICK)) {
                UUIDPayload payload = new UUIDPayload(
                        entity.getUuid(),
                        entity.getName().getString()
                );
                ServerPlayNetworking.send((ServerPlayerEntity) player, payload);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });

        LOGGER.info("Copy UUID mod initialised.");
    }
}
