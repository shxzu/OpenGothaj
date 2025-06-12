package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

public class ChunkLightStorage$ChunkLight {
    private final byte[][] skyLight;
    private final byte[][] blockLight;

    public ChunkLightStorage$ChunkLight(byte[][] skyLight, byte[][] blockLight) {
        this.skyLight = skyLight;
        this.blockLight = blockLight;
    }

    public byte[][] skyLight() {
        return this.skyLight;
    }

    public byte[][] blockLight() {
        return this.blockLight;
    }
}
