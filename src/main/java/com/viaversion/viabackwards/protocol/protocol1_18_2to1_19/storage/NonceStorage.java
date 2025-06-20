package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class NonceStorage
implements StorableObject {
    private final byte[] nonce;

    public NonceStorage(byte @Nullable [] nonce) {
        this.nonce = nonce;
    }

    public byte @Nullable [] nonce() {
        return this.nonce;
    }
}
