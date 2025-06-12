package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.ByteArrayType;

public final class ByteArrayType$OptionalByteArrayType
extends OptionalType<byte[]> {
    public ByteArrayType$OptionalByteArrayType() {
        super(Type.BYTE_ARRAY_PRIMITIVE);
    }

    public ByteArrayType$OptionalByteArrayType(int length) {
        super(new ByteArrayType(length));
    }
}
