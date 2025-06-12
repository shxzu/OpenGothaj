package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.utils.ChatUtil;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;

class PlayerPacket1_13$4
extends PacketHandlers {
    PlayerPacket1_13$4() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE);
        this.handler(wrapper -> {
            byte mode = wrapper.get(Type.BYTE, 0);
            if (mode == 0 || mode == 2) {
                JsonElement value = wrapper.read(Type.COMPONENT);
                String legacyValue = ((Protocol1_12_2To1_13)PlayerPacket1_13.this.protocol).jsonToLegacy(value);
                wrapper.write(Type.STRING, ChatUtil.fromLegacy(legacyValue, 'f', 32));
                int type = wrapper.read(Type.VAR_INT);
                wrapper.write(Type.STRING, type == 1 ? "hearts" : "integer");
            }
        });
    }
}
