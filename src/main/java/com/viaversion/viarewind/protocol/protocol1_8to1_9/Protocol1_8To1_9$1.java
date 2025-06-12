package com.viaversion.viarewind.protocol.protocol1_8to1_9;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_8To1_9$1
extends ValueTransformer<Double, Integer> {
    Protocol1_8To1_9$1(Type arg0) {
        super(arg0);
    }

    @Override
    public Integer transform(PacketWrapper wrapper, Double inputValue) {
        return (int)(inputValue * 32.0);
    }
}
