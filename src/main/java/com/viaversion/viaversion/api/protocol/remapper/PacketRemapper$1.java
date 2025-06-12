package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import java.util.function.Function;

class PacketRemapper$1
extends ValueTransformer<T1, T2> {
    final Function val$transformer;

    PacketRemapper$1(Type outputType, Function function) {
        this.val$transformer = function;
        super(outputType);
    }

    @Override
    public T2 transform(PacketWrapper wrapper, T1 inputValue) throws Exception {
        return this.val$transformer.apply(inputValue);
    }
}
