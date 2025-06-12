package com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_7_2_5To1_7_6_10$3
extends PacketHandlers {
    Protocol1_7_2_5To1_7_6_10$3() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.STRING, REMOVE_DASHES);
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            int size = packetWrapper.read(Type.VAR_INT);
            for (int i = 0; i < size; ++i) {
                packetWrapper.read(Type.STRING);
                packetWrapper.read(Type.STRING);
                packetWrapper.read(Type.STRING);
            }
        });
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Types1_7_6_10.METADATA_LIST);
    }
}
