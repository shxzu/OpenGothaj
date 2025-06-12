// ERROR: Unable to apply inner class name fixup
package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_13To1_13_1.1
extends ValueTransformer<String, String> {
    Protocol1_13To1_13_1.1(Type arg0) {
        super(arg0);
    }

    @Override
    public String transform(PacketWrapper wrapper, String inputValue) {
        return !inputValue.startsWith("/") ? "/" + inputValue : inputValue;
    }
}
