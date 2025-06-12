package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.util.ComponentUtil;

class PlayerPackets1_11$2
extends PacketHandlers {
    PlayerPackets1_11$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int action = wrapper.get(Type.VAR_INT, 0);
            if (action == 2) {
                JsonElement message = wrapper.read(Type.COMPONENT);
                wrapper.clearPacket();
                wrapper.setPacketType(ClientboundPackets1_9_3.CHAT_MESSAGE);
                String legacy = ComponentUtil.jsonToLegacy(message);
                message = new JsonObject();
                message.getAsJsonObject().addProperty("text", legacy);
                wrapper.write(Type.COMPONENT, message);
                wrapper.write(Type.BYTE, (byte)2);
            } else if (action > 2) {
                wrapper.set(Type.VAR_INT, 0, action - 1);
            }
        });
    }
}
