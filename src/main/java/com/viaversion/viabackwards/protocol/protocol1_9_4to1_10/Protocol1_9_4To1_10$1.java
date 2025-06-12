package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_9_4To1_10$1
extends ValueTransformer<Float, Short> {
    Protocol1_9_4To1_10$1(Type arg0) {
        super(arg0);
    }

    @Override
    public Short transform(PacketWrapper packetWrapper, Float inputValue) throws Exception {
        return (short)Math.round(inputValue.floatValue() * 63.5f);
    }
}
