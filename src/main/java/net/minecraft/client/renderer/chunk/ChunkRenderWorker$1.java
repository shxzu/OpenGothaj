package net.minecraft.client.renderer.chunk;

import com.google.common.util.concurrent.ListenableFuture;

class ChunkRenderWorker$1
implements Runnable {
    private final ListenableFuture val$listenablefuture;

    ChunkRenderWorker$1(ListenableFuture listenableFuture) {
        this.val$listenablefuture = listenableFuture;
    }

    @Override
    public void run() {
        this.val$listenablefuture.cancel(false);
    }
}
