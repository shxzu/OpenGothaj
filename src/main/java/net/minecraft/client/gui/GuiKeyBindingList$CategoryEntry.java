package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.resources.I18n;

public class GuiKeyBindingList$CategoryEntry
implements GuiListExtended.IGuiListEntry {
    private final String labelText;
    private final int labelWidth;

    public GuiKeyBindingList$CategoryEntry(String p_i45028_2_) {
        this.labelText = I18n.format(p_i45028_2_, new Object[0]);
        this.labelWidth = ((GuiKeyBindingList)GuiKeyBindingList.this).mc.fontRendererObj.getStringWidth(this.labelText);
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
        ((GuiKeyBindingList)GuiKeyBindingList.this).mc.fontRendererObj.drawString(this.labelText, ((GuiKeyBindingList)GuiKeyBindingList.this).mc.currentScreen.width / 2 - this.labelWidth / 2, y + slotHeight - ((GuiKeyBindingList)GuiKeyBindingList.this).mc.fontRendererObj.FONT_HEIGHT - 1, 0xFFFFFF);
    }

    @Override
    public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
        return false;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
    }

    @Override
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
    }
}
