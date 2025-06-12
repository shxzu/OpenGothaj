package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;

class ChunkRenderDispatcher$2
implements Runnable {
    private final ChunkCompileTaskGenerator val$chunkcompiletaskgenerator;

    ChunkRenderDispatcher$2(ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        this.val$chunkcompiletaskgenerator = chunkCompileTaskGenerator;
    }

    @Override
    public void run() {
        ChunkRenderDispatcher.this.queueChunkUpdates.remove(this.val$chunkcompiletaskgenerator);
    }
}
