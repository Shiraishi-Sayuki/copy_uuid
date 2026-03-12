package com.sayuki.copy_uuid;

import com.sayuki.copy_uuid.client.CopyUUIDClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CopyUUIDMod.MOD_ID)
public class CopyUUIDMod {

    public static final String MOD_ID = "copy_uuid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public CopyUUIDMod(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            CopyUUIDClient.register(modEventBus);
        }
        LOGGER.info("Copy UUID mod initialised.");
    }
}
