package net.minecraft.client.network;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;

class NetHandlerPlayClient$3
implements Runnable {
    private final String val$s1;
    private final String val$s;

    NetHandlerPlayClient$3(String string, String string2) {
        this.val$s1 = string;
        this.val$s = string2;
    }

    @Override
    public void run() {
        NetHandlerPlayClient.this.gameController.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(){

            @Override
            public void confirmClicked(boolean result, int id) {
                NetHandlerPlayClient.this.gameController = Minecraft.getMinecraft();
                if (result) {
                    if (NetHandlerPlayClient.this.gameController.getCurrentServerData() != null) {
                        NetHandlerPlayClient.this.gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
                    }
                    NetHandlerPlayClient.this.netManager.sendPacket(new C19PacketResourcePackStatus(val$s1, C19PacketResourcePackStatus.Action.ACCEPTED));
                    Futures.addCallback(NetHandlerPlayClient.this.gameController.getResourcePackRepository().downloadResourcePack(val$s, val$s1), (FutureCallback)new FutureCallback<Object>(){

                        @Override
                        public void onSuccess(Object p_onSuccess_1_) {
                            NetHandlerPlayClient.this.netManager.sendPacket(new C19PacketResourcePackStatus(val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                        }

                        @Override
                        public void onFailure(Throwable p_onFailure_1_) {
                            NetHandlerPlayClient.this.netManager.sendPacket(new C19PacketResourcePackStatus(val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                        }
                    });
                } else {
                    if (NetHandlerPlayClient.this.gameController.getCurrentServerData() != null) {
                        NetHandlerPlayClient.this.gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
                    }
                    NetHandlerPlayClient.this.netManager.sendPacket(new C19PacketResourcePackStatus(val$s1, C19PacketResourcePackStatus.Action.DECLINED));
                }
                ServerList.func_147414_b(NetHandlerPlayClient.this.gameController.getCurrentServerData());
                NetHandlerPlayClient.this.gameController.displayGuiScreen(null);
            }
        }, I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
    }
}
