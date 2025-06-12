package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;

class ChunkRenderDispatcher$3
implements Runnable {
    private final EnumWorldBlockLayer val$player;
    private final WorldRenderer val$p_178503_2_;
    private final RenderChunk val$chunkRenderer;
    private final CompiledChunk val$compiledChunkIn;

    ChunkRenderDispatcher$3(EnumWorldBlockLayer enumWorldBlockLayer, WorldRenderer worldRenderer, RenderChunk renderChunk, CompiledChunk compiledChunk) {
        this.val$player = enumWorldBlockLayer;
        this.val$p_178503_2_ = worldRenderer;
        this.val$chunkRenderer = renderChunk;
        this.val$compiledChunkIn = compiledChunk;
    }

    @Override
    public void run() {
        ChunkRenderDispatcher.this.uploadChunk(this.val$player, this.val$p_178503_2_, this.val$chunkRenderer, this.val$compiledChunkIn);
    }
}
