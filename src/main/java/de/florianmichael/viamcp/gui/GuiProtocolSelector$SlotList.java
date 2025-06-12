package de.florianmichael.viamcp.gui;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

class GuiProtocolSelector$SlotList
extends GuiSlot {
    public GuiProtocolSelector$SlotList(Minecraft mc, int width, int height, int top, int bottom) {
        super(mc, width, height, top + 30, bottom, 18);
    }

    @Override
    protected int getSize() {
        return ViaLoadingBase.getProtocols().size();
    }

    @Override
    protected void elementClicked(int i, boolean b, int i1, int i2) {
        ProtocolVersion protocolVersion = ViaLoadingBase.getProtocols().get(i);
        ViaLoadingBase.getInstance().reload(protocolVersion);
    }

    @Override
    protected boolean isSelected(int i) {
        return false;
    }

    @Override
    protected void drawBackground() {
        GuiProtocolSelector.this.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5) {
        GuiProtocolSelector.this.drawCenteredString(this.mc.fontRendererObj, String.valueOf(ViaLoadingBase.getInstance().getTargetVersion().getIndex() == i ? String.valueOf(EnumChatFormatting.GREEN.toString()) + (Object)((Object)EnumChatFormatting.BOLD) : EnumChatFormatting.GRAY.toString()) + ViaLoadingBase.getProtocols().get(i).getName(), this.width / 2, i2 + 2, -1);
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GuiProtocolSelector.this.drawCenteredString(this.mc.fontRendererObj, "PVN: " + ViaLoadingBase.getProtocols().get(i).getVersion(), this.width, (i2 + 2) * 2 + 20, -1);
        GlStateManager.popMatrix();
    }
}
