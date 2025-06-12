package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_13To1_13_1$1
extends PacketHandlers {
    Protocol1_13To1_13_1$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.STRING, new ValueTransformer<String, String>(Type.STRING){

            @Override
            public String transform(PacketWrapper wrapper, String inputValue) {
                return !inputValue.startsWith("/") ? "/" + inputValue : inputValue;
            }
        });
    }
}
