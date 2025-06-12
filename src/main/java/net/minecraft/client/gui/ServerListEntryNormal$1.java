package net.minecraft.client.gui;

import java.net.UnknownHostException;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.util.EnumChatFormatting;

class ServerListEntryNormal$1
implements Runnable {
    ServerListEntryNormal$1() {
    }

    @Override
    public void run() {
        try {
            ServerListEntryNormal.this.owner.getOldServerPinger().ping(ServerListEntryNormal.this.server);
        }
        catch (UnknownHostException var2) {
            ((ServerListEntryNormal)ServerListEntryNormal.this).server.pingToServer = -1L;
            ((ServerListEntryNormal)ServerListEntryNormal.this).server.serverMOTD = (Object)((Object)EnumChatFormatting.DARK_RED) + "Can't resolve hostname";
        }
        catch (Exception var3) {
            ((ServerListEntryNormal)ServerListEntryNormal.this).server.pingToServer = -1L;
            ((ServerListEntryNormal)ServerListEntryNormal.this).server.serverMOTD = (Object)((Object)EnumChatFormatting.DARK_RED) + "Can't connect to server.";
        }
    }
}
