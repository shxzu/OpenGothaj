// ERROR: Unable to apply inner class name fixup
package net.minecraft.client.network;

import com.google.common.util.concurrent.FutureCallback;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;

class NetHandlerPlayClient.1
implements FutureCallback<Object> {
    private final String val$s1;

    NetHandlerPlayClient.1(String string) {
        this.val$s1 = string;
    }

    @Override
    public void onSuccess(Object p_onSuccess_1_) {
        this$1.this$0.netManager.sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
    }

    @Override
    public void onFailure(Throwable p_onFailure_1_) {
        this$1.this$0.netManager.sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
    }
}
