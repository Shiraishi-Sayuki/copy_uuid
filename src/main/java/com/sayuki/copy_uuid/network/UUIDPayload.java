package com.sayuki.copy_uuid.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record UUIDPayload(UUID entityUuid, String entityName) implements CustomPayload {

    public static final Id<UUIDPayload> ID =
            new Id<>(Identifier.of("copy_uuid", "copy_uuid"));

    private static final PacketCodec<PacketByteBuf, UUID> UUID_CODEC = PacketCodec.of(
            (uuid, buf) -> {
                buf.writeLong(uuid.getMostSignificantBits());
                buf.writeLong(uuid.getLeastSignificantBits());
            },
            buf -> new UUID(buf.readLong(), buf.readLong())
    );

    public static final PacketCodec<PacketByteBuf, UUIDPayload> CODEC =
            PacketCodec.tuple(
                    UUID_CODEC,          UUIDPayload::entityUuid,
                    PacketCodecs.STRING, UUIDPayload::entityName,
                    UUIDPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}