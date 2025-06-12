package com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_7_2_5To1_7_6_10$1
extends ValueTransformer<String, String> {
    Protocol1_7_2_5To1_7_6_10$1(Type arg0) {
        super(arg0);
    }

    @Override
    public String transform(PacketWrapper packetWrapper, String s) {
        return s.replace("-", "");
    }
}
