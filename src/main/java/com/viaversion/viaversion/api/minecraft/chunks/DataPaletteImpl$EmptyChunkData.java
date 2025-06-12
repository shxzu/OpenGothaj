package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;

class DataPaletteImpl$EmptyChunkData
implements DataPaletteImpl.ChunkData {
    private final int size;

    public DataPaletteImpl$EmptyChunkData(int size) {
        this.size = size;
    }

    @Override
    public int get(int idx) {
        return 0;
    }

    @Override
    public void set(int idx, int val) {
        if (val != 0) {
            DataPaletteImpl.this.values = new DataPaletteImpl.ByteChunkData(DataPaletteImpl.this, this.size);
            DataPaletteImpl.this.values.set(idx, val);
        }
    }
}
