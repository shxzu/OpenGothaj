package net.minecraft.client.network;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import javax.crypto.SecretKey;

class NetHandlerLoginClient$1
implements GenericFutureListener<Future<? super Void>> {
    private final SecretKey val$secretkey;

    NetHandlerLoginClient$1(SecretKey secretKey) {
        this.val$secretkey = secretKey;
    }

    public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
        NetHandlerLoginClient.this.networkManager.enableEncryption(this.val$secretkey);
    }
}
