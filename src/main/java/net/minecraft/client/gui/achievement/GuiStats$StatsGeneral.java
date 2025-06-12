package net.minecraft.client.gui.achievement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

class GuiStats$StatsGeneral
extends GuiSlot {
    public GuiStats$StatsGeneral(Minecraft mcIn) {
        super(mcIn, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 10);
        this.setShowSelectionBox(false);
    }

    @Override
    protected int getSize() {
        return StatList.generalStats.size();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return false;
    }

    @Override
    protected int getContentHeight() {
        return this.getSize() * 10;
    }

    @Override
    protected void drawBackground() {
        GuiStats.this.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        StatBase statbase = StatList.generalStats.get(entryID);
        GuiStats.this.drawString(GuiStats.this.fontRendererObj, statbase.getStatName().getUnformattedText(), p_180791_2_ + 2, p_180791_3_ + 1, entryID % 2 == 0 ? 0xFFFFFF : 0x909090);
        String s = statbase.format(GuiStats.this.field_146546_t.readStat(statbase));
        GuiStats.this.drawString(GuiStats.this.fontRendererObj, s, p_180791_2_ + 2 + 213 - GuiStats.this.fontRendererObj.getStringWidth(s), p_180791_3_ + 1, entryID % 2 == 0 ? 0xFFFFFF : 0x909090);
    }
}
