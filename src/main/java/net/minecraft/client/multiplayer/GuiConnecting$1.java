package net.minecraft.client.multiplayer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;

class GuiConnecting$1
extends Thread {
    private final String val$ip;
    private final int val$port;

    GuiConnecting$1(String $anonymous0, String string, int n) {
        this.val$ip = string;
        this.val$port = n;
        super($anonymous0);
    }

    @Override
    public void run() {
        InetAddress inetaddress = null;
        try {
            if (GuiConnecting.this.cancel) {
                return;
            }
            inetaddress = InetAddress.getByName(this.val$ip);
            GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, this.val$port, Minecraft.getMinecraft().gameSettings.isUsingNativeTransport());
            GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
            GuiConnecting.this.networkManager.sendPacket(new C00Handshake(47, this.val$ip, this.val$port, EnumConnectionState.LOGIN));
            GuiConnecting.this.networkManager.sendPacket(new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
        }
        catch (UnknownHostException unknownhostexception) {
            if (GuiConnecting.this.cancel) {
                return;
            }
            logger.error("Couldn't connect to server", (Throwable)unknownhostexception);
            GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", "Unknown host")));
        }
        catch (Exception exception) {
            if (GuiConnecting.this.cancel) {
                return;
            }
            logger.error("Couldn't connect to server", (Throwable)exception);
            String s = exception.toString();
            if (inetaddress != null) {
                String s1 = String.valueOf(inetaddress.toString()) + ":" + this.val$port;
                s = s.replaceAll(s1, "");
            }
            GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", s)));
        }
    }
}
