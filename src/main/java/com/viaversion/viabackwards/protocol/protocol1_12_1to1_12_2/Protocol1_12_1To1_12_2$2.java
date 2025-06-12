package com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2;

import com.viaversion.viabackwards.protocol.protocol1_12_1to1_12_2.KeepAliveTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_12_1To1_12_2$2
extends PacketHandlers {
    Protocol1_12_1To1_12_2$2() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            long realKeepAlive;
            int keepAlive = packetWrapper.read(Type.VAR_INT);
            if (keepAlive != Long.hashCode(realKeepAlive = packetWrapper.user().get(KeepAliveTracker.class).getKeepAlive())) {
                packetWrapper.cancel();
                return;
            }
            packetWrapper.write(Type.LONG, realKeepAlive);
            packetWrapper.user().get(KeepAliveTracker.class).setKeepAlive(Integer.MAX_VALUE);
        });
    }
}
