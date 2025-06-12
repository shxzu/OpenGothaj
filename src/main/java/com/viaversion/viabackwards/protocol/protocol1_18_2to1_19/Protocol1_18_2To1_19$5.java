package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;

class Protocol1_18_2To1_19$5
extends PacketHandlers {
    Protocol1_18_2To1_19$5() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            JsonElement content = wrapper.passthrough(Type.COMPONENT);
            Protocol1_18_2To1_19.this.translatableRewriter.processText(content);
            int typeId = wrapper.read(Type.VAR_INT);
            wrapper.write(Type.BYTE, typeId == 2 ? (byte)2 : (byte)0);
        });
        this.create(Type.UUID, ZERO_UUID);
    }
}
