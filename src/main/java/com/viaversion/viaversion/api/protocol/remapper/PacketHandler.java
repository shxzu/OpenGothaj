package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

@FunctionalInterface
public interface PacketHandler {
    public void handle(PacketWrapper var1) throws Exception;
}
