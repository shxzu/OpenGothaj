package net.minecraft.client.gui.stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.stream.GuiIngestServers;
import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.util.EnumChatFormatting;
import tv.twitch.broadcast.IngestServer;

class GuiIngestServers$ServerList
extends GuiSlot {
    public GuiIngestServers$ServerList(Minecraft mcIn) {
        super(mcIn, GuiIngestServers.this.width, GuiIngestServers.this.height, 32, GuiIngestServers.this.height - 35, (int)((double)mcIn.fontRendererObj.FONT_HEIGHT * 3.5));
        this.setShowSelectionBox(false);
    }

    @Override
    protected int getSize() {
        return this.mc.getTwitchStream().func_152925_v().length;
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        this.mc.gameSettings.streamPreferredServer = this.mc.getTwitchStream().func_152925_v()[slotIndex].serverUrl;
        this.mc.gameSettings.saveOptions();
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return this.mc.getTwitchStream().func_152925_v()[slotIndex].serverUrl.equals(this.mc.gameSettings.streamPreferredServer);
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        IngestServer ingestserver = this.mc.getTwitchStream().func_152925_v()[entryID];
        String s = ingestserver.serverUrl.replaceAll("\\{stream_key\\}", "");
        String s1 = String.valueOf((int)ingestserver.bitrateKbps) + " kbps";
        String s2 = null;
        IngestServerTester ingestservertester = this.mc.getTwitchStream().func_152932_y();
        if (ingestservertester != null) {
            if (ingestserver == ingestservertester.func_153040_c()) {
                s = (Object)((Object)EnumChatFormatting.GREEN) + s;
                s1 = String.valueOf((int)(ingestservertester.func_153030_h() * 100.0f)) + "%";
            } else if (entryID < ingestservertester.func_153028_p()) {
                if (ingestserver.bitrateKbps == 0.0f) {
                    s1 = (Object)((Object)EnumChatFormatting.RED) + "Down!";
                }
            } else {
                s1 = (Object)((Object)EnumChatFormatting.OBFUSCATED) + "1234" + (Object)((Object)EnumChatFormatting.RESET) + " kbps";
            }
        } else if (ingestserver.bitrateKbps == 0.0f) {
            s1 = (Object)((Object)EnumChatFormatting.RED) + "Down!";
        }
        p_180791_2_ -= 15;
        if (this.isSelected(entryID)) {
            s2 = (Object)((Object)EnumChatFormatting.BLUE) + "(Preferred)";
        } else if (ingestserver.defaultServer) {
            s2 = (Object)((Object)EnumChatFormatting.GREEN) + "(Default)";
        }
        GuiIngestServers.this.drawString(GuiIngestServers.this.fontRendererObj, ingestserver.serverName, p_180791_2_ + 2, p_180791_3_ + 5, 0xFFFFFF);
        GuiIngestServers.this.drawString(GuiIngestServers.this.fontRendererObj, s, p_180791_2_ + 2, p_180791_3_ + ((GuiIngestServers)GuiIngestServers.this).fontRendererObj.FONT_HEIGHT + 5 + 3, 0x303030);
        GuiIngestServers.this.drawString(GuiIngestServers.this.fontRendererObj, s1, this.getScrollBarX() - 5 - GuiIngestServers.this.fontRendererObj.getStringWidth(s1), p_180791_3_ + 5, 0x808080);
        if (s2 != null) {
            GuiIngestServers.this.drawString(GuiIngestServers.this.fontRendererObj, s2, this.getScrollBarX() - 5 - GuiIngestServers.this.fontRendererObj.getStringWidth(s2), p_180791_3_ + 5 + 3 + ((GuiIngestServers)GuiIngestServers.this).fontRendererObj.FONT_HEIGHT, 0x808080);
        }
    }

    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 15;
    }
}
