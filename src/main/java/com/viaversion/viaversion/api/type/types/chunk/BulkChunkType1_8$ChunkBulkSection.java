package com.viaversion.viaversion.api.type.types.chunk;

import io.netty.buffer.ByteBuf;

public final class BulkChunkType1_8$ChunkBulkSection {
    private final int chunkX;
    private final int chunkZ;
    private final int bitmask;
    private final byte[] data;

    public BulkChunkType1_8$ChunkBulkSection(ByteBuf input, boolean skyLight) {
        this.chunkX = input.readInt();
        this.chunkZ = input.readInt();
        this.bitmask = input.readUnsignedShort();
        int setSections = Integer.bitCount(this.bitmask);
        this.data = new byte[setSections * (8192 + (skyLight ? 4096 : 2048)) + 256];
    }

    public void readData(ByteBuf input) {
        input.readBytes(this.data);
    }

    public int chunkX() {
        return this.chunkX;
    }

    public int chunkZ() {
        return this.chunkZ;
    }

    public int bitmask() {
        return this.bitmask;
    }

    public byte[] data() {
        return this.data;
    }

    static int access$000(BulkChunkType1_8$ChunkBulkSection x0) {
        return x0.chunkX;
    }

    static int access$100(BulkChunkType1_8$ChunkBulkSection x0) {
        return x0.chunkZ;
    }

    static int access$200(BulkChunkType1_8$ChunkBulkSection x0) {
        return x0.bitmask;
    }
}
