// ERROR: Unable to apply inner class name fixup
package net.minecraft.client.network;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;

class NetHandlerPlayClient.1
implements GuiYesNoCallback {
    private final String val$s1;
    private final String val$s;

    NetHandlerPlayClient.1(String string, String string2) {
        this.val$s1 = string;
        this.val$s = string2;
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        this$0.gameController = Minecraft.getMinecraft();
        if (result) {
            if (this$0.gameController.getCurrentServerData() != null) {
                this$0.gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
            }
            this$0.netManager.sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.ACCEPTED));
            Futures.addCallback(this$0.gameController.getResourcePackRepository().downloadResourcePack(this.val$s, this.val$s1), (FutureCallback)new FutureCallback<Object>(){

                @Override
                public void onSuccess(Object p_onSuccess_1_) {
                    this$0.netManager.sendPacket(new C19PacketResourcePackStatus(val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                }

                @Override
                public void onFailure(Throwable p_onFailure_1_) {
                    this$0.netManager.sendPacket(new C19PacketResourcePackStatus(val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                }
            });
        } else {
            if (this$0.gameController.getCurrentServerData() != null) {
                this$0.gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
            }
            this$0.netManager.sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.DECLINED));
        }
        ServerList.func_147414_b(this$0.gameController.getCurrentServerData());
        this$0.gameController.displayGuiScreen(null);
    }
}
