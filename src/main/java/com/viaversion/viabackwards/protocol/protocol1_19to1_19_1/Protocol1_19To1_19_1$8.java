package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_19To1_19_1$8
extends PacketHandlers {
    Protocol1_19To1_19_1$8() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.STRING);
        this.handler(wrapper -> {
            String identifier = wrapper.get(Type.STRING, 0);
            if (identifier.equals("velocity:player_info")) {
                byte[] data = wrapper.passthrough(Type.REMAINING_BYTES);
                if (data.length == 1 && data[0] > 1) {
                    data[0] = 1;
                } else if (data.length == 0) {
                    data = new byte[]{1};
                    wrapper.set(Type.REMAINING_BYTES, 0, data);
                } else {
                    ViaBackwards.getPlatform().getLogger().warning("Received unexpected data in velocity:player_info (length=" + data.length + ")");
                }
            }
        });
    }
}
