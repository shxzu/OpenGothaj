package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;

class DataPaletteImpl$ShortChunkData
implements DataPaletteImpl.ChunkData {
    private final short[] data;

    public DataPaletteImpl$ShortChunkData(byte[] data) {
        this.data = new short[data.length];
        for (int i = 0; i < data.length; ++i) {
            this.data[i] = (short)(data[i] & 0xFF);
        }
    }

    @Override
    public int get(int idx) {
        return this.data[idx];
    }

    @Override
    public void set(int idx, int val) {
        this.data[idx] = (short)val;
    }
}
