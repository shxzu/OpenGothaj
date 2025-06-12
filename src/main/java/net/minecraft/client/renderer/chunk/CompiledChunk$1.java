package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;

class CompiledChunk$1
extends CompiledChunk {
    CompiledChunk$1() {
    }

    @Override
    protected void setLayerUsed(EnumWorldBlockLayer layer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLayerStarted(EnumWorldBlockLayer layer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
        return false;
    }

    @Override
    public void setAnimatedSprites(EnumWorldBlockLayer p_setAnimatedSprites_1_, BitSet p_setAnimatedSprites_2_) {
        throw new UnsupportedOperationException();
    }
}
