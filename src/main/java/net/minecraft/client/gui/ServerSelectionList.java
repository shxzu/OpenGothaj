package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerListEntryLanScan;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;

public class ServerSelectionList
extends GuiListExtended {
    private final GuiMultiplayer owner;
    private final List<ServerListEntryNormal> serverListInternet = Lists.newArrayList();
    private final List<ServerListEntryLanDetected> serverListLan = Lists.newArrayList();
    private final GuiListExtended.IGuiListEntry lanScanEntry = new ServerListEntryLanScan();
    private int selectedSlotIndex = -1;

    public ServerSelectionList(GuiMultiplayer ownerIn, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.owner = ownerIn;
    }

    @Override
    public GuiListExtended.IGuiListEntry getListEntry(int index) {
        if (index < this.serverListInternet.size()) {
            return this.serverListInternet.get(index);
        }
        if ((index -= this.serverListInternet.size()) == 0) {
            return this.lanScanEntry;
        }
        return this.serverListLan.get(--index);
    }

    @Override
    protected int getSize() {
        return this.serverListInternet.size() + 1 + this.serverListLan.size();
    }

    public void setSelectedSlotIndex(int selectedSlotIndexIn) {
        this.selectedSlotIndex = selectedSlotIndexIn;
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == this.selectedSlotIndex;
    }

    public int func_148193_k() {
        return this.selectedSlotIndex;
    }

    public void func_148195_a(ServerList p_148195_1_) {
        this.serverListInternet.clear();
        int i = 0;
        while (i < p_148195_1_.countServers()) {
            this.serverListInternet.add(new ServerListEntryNormal(this.owner, p_148195_1_.getServerData(i)));
            ++i;
        }
    }

    public void func_148194_a(List<LanServerDetector.LanServer> p_148194_1_) {
        this.serverListLan.clear();
        for (LanServerDetector.LanServer lanserverdetector$lanserver : p_148194_1_) {
            this.serverListLan.add(new ServerListEntryLanDetected(this.owner, lanserverdetector$lanserver));
        }
    }

    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 30;
    }

    @Override
    public int getListWidth() {
        return super.getListWidth() + 85;
    }
}
