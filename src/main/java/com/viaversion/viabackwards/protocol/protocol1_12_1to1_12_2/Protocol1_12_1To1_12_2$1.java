package com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2;

import com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.KeepAliveTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_12_1To1_12_2$1
extends PacketHandlers {
    Protocol1_12_1To1_12_2$1() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            Long keepAlive = packetWrapper.read(Type.LONG);
            packetWrapper.user().get(KeepAliveTracker.class).setKeepAlive(keepAlive);
            packetWrapper.write(Type.VAR_INT, keepAlive.hashCode());
        });
    }
}
