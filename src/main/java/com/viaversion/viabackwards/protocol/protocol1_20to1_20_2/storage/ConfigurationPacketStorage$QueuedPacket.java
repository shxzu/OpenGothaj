package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.storage;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import io.netty.buffer.ByteBuf;

public final class ConfigurationPacketStorage$QueuedPacket {
    private final ByteBuf buf;
    private final PacketType packetType;

    public ConfigurationPacketStorage$QueuedPacket(ByteBuf buf, PacketType packetType) {
        this.buf = buf;
        this.packetType = packetType;
    }

    public ByteBuf buf() {
        return this.buf;
    }

    public PacketType packetType() {
        return this.packetType;
    }
}
