package com.viaversion.viarewind.protocol.protocol1_8to1_9;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_8To1_9$2
extends ValueTransformer<Float, Byte> {
    Protocol1_8To1_9$2(Type arg0) {
        super(arg0);
    }

    @Override
    public Byte transform(PacketWrapper packetWrapper, Float degrees) throws Exception {
        return (byte)(degrees.floatValue() / 360.0f * 256.0f);
    }
}
