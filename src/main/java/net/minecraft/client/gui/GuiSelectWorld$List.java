package net.minecraft.client.gui;

import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.storage.SaveFormatComparator;
import org.apache.commons.lang3.StringUtils;

class GuiSelectWorld$List
extends GuiSlot {
    public GuiSelectWorld$List(Minecraft mcIn) {
        super(mcIn, GuiSelectWorld.this.width, GuiSelectWorld.this.height, 32, GuiSelectWorld.this.height - 64, 36);
    }

    @Override
    protected int getSize() {
        return GuiSelectWorld.this.field_146639_s.size();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        boolean flag;
        GuiSelectWorld.this.selectedIndex = slotIndex;
        ((GuiSelectWorld)GuiSelectWorld.this).selectButton.enabled = flag = GuiSelectWorld.this.selectedIndex >= 0 && GuiSelectWorld.this.selectedIndex < this.getSize();
        ((GuiSelectWorld)GuiSelectWorld.this).deleteButton.enabled = flag;
        ((GuiSelectWorld)GuiSelectWorld.this).renameButton.enabled = flag;
        ((GuiSelectWorld)GuiSelectWorld.this).recreateButton.enabled = flag;
        if (isDoubleClick && flag) {
            GuiSelectWorld.this.func_146615_e(slotIndex);
        }
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == GuiSelectWorld.this.selectedIndex;
    }

    @Override
    protected int getContentHeight() {
        return GuiSelectWorld.this.field_146639_s.size() * 36;
    }

    @Override
    protected void drawBackground() {
        GuiSelectWorld.this.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        SaveFormatComparator saveformatcomparator = (SaveFormatComparator)GuiSelectWorld.this.field_146639_s.get(entryID);
        String s = saveformatcomparator.getDisplayName();
        if (StringUtils.isEmpty(s)) {
            s = String.valueOf(GuiSelectWorld.this.field_146637_u) + " " + (entryID + 1);
        }
        String s1 = saveformatcomparator.getFileName();
        s1 = String.valueOf(s1) + " (" + GuiSelectWorld.this.field_146633_h.format(new Date(saveformatcomparator.getLastTimePlayed()));
        s1 = String.valueOf(s1) + ")";
        String s2 = "";
        if (saveformatcomparator.requiresConversion()) {
            s2 = String.valueOf(GuiSelectWorld.this.field_146636_v) + " " + s2;
        } else {
            s2 = GuiSelectWorld.this.field_146635_w[saveformatcomparator.getEnumGameType().getID()];
            if (saveformatcomparator.isHardcoreModeEnabled()) {
                s2 = (Object)((Object)EnumChatFormatting.DARK_RED) + I18n.format("gameMode.hardcore", new Object[0]) + (Object)((Object)EnumChatFormatting.RESET);
            }
            if (saveformatcomparator.getCheatsEnabled()) {
                s2 = String.valueOf(s2) + ", " + I18n.format("selectWorld.cheats", new Object[0]);
            }
        }
        GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s, p_180791_2_ + 2, p_180791_3_ + 1, 0xFFFFFF);
        GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s1, p_180791_2_ + 2, p_180791_3_ + 12, 0x808080);
        GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s2, p_180791_2_ + 2, p_180791_3_ + 12 + 10, 0x808080);
    }
}
