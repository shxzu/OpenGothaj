package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;

class ChunkRenderDispatcher$1
implements Runnable {
    private final ChunkCompileTaskGenerator val$chunkcompiletaskgenerator;

    ChunkRenderDispatcher$1(ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        this.val$chunkcompiletaskgenerator = chunkCompileTaskGenerator;
    }

    @Override
    public void run() {
        ChunkRenderDispatcher.this.queueChunkUpdates.remove(this.val$chunkcompiletaskgenerator);
    }
}
