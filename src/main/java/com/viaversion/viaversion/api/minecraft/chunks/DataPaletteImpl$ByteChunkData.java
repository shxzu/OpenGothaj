package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;

class DataPaletteImpl$ByteChunkData
implements DataPaletteImpl.ChunkData {
    private final byte[] data;

    public DataPaletteImpl$ByteChunkData(int size) {
        this.data = new byte[size];
    }

    @Override
    public int get(int idx) {
        return this.data[idx] & 0xFF;
    }

    @Override
    public void set(int idx, int val) {
        if (val > 255) {
            DataPaletteImpl.this.values = new DataPaletteImpl.ShortChunkData(this.data);
            DataPaletteImpl.this.values.set(idx, val);
            return;
        }
        this.data[idx] = (byte)val;
    }
}
