package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets1_11$1
extends ValueTransformer<Short, Float> {
    PlayerPackets1_11$1(Type arg0) {
        super(arg0);
    }

    @Override
    public Float transform(PacketWrapper wrapper, Short inputValue) throws Exception {
        return Float.valueOf((float)inputValue.shortValue() / 16.0f);
    }
}
