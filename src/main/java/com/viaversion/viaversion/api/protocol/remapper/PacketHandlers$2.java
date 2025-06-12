package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import java.util.function.Function;

class PacketHandlers$2
extends ValueTransformer<T1, T2> {
    final Function val$transformer;

    PacketHandlers$2(Type outputType, Function function) {
        this.val$transformer = function;
        super(outputType);
    }

    @Override
    public T2 transform(PacketWrapper wrapper, T1 inputValue) {
        return this.val$transformer.apply(inputValue);
    }
}
