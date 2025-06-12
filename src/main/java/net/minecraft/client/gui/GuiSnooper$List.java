package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiSlot;

class GuiSnooper$List
extends GuiSlot {
    public GuiSnooper$List() {
        super(GuiSnooper.this.mc, GuiSnooper.this.width, GuiSnooper.this.height, 80, GuiSnooper.this.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
    }

    @Override
    protected int getSize() {
        return GuiSnooper.this.field_146604_g.size();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return false;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        GuiSnooper.this.fontRendererObj.drawString((String)GuiSnooper.this.field_146604_g.get(entryID), 10.0, p_180791_3_, 0xFFFFFF);
        GuiSnooper.this.fontRendererObj.drawString((String)GuiSnooper.this.field_146609_h.get(entryID), 230.0, p_180791_3_, 0xFFFFFF);
    }

    @Override
    protected int getScrollBarX() {
        return this.width - 10;
    }
}
