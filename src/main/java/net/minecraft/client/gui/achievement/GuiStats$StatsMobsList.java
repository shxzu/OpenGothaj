package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;

class GuiStats$StatsMobsList
extends GuiSlot {
    private final List<EntityList.EntityEggInfo> field_148222_l;

    public GuiStats$StatsMobsList(Minecraft mcIn) {
        super(mcIn, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, ((GuiStats)GuiStats.this).fontRendererObj.FONT_HEIGHT * 4);
        this.field_148222_l = Lists.newArrayList();
        this.setShowSelectionBox(false);
        for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.entityEggs.values()) {
            if (GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151512_d) <= 0 && GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151513_e) <= 0) continue;
            this.field_148222_l.add(entitylist$entityegginfo);
        }
    }

    @Override
    protected int getSize() {
        return this.field_148222_l.size();
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
        return this.getSize() * ((GuiStats)GuiStats.this).fontRendererObj.FONT_HEIGHT * 4;
    }

    @Override
    protected void drawBackground() {
        GuiStats.this.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        EntityList.EntityEggInfo entitylist$entityegginfo = this.field_148222_l.get(entryID);
        String s = I18n.format("entity." + EntityList.getStringFromID(entitylist$entityegginfo.spawnedID) + ".name", new Object[0]);
        int i = GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151512_d);
        int j = GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151513_e);
        String s1 = I18n.format("stat.entityKills", i, s);
        String s2 = I18n.format("stat.entityKilledBy", s, j);
        if (i == 0) {
            s1 = I18n.format("stat.entityKills.none", s);
        }
        if (j == 0) {
            s2 = I18n.format("stat.entityKilledBy.none", s);
        }
        GuiStats.this.drawString(GuiStats.this.fontRendererObj, s, p_180791_2_ + 2 - 10, p_180791_3_ + 1, 0xFFFFFF);
        GuiStats.this.drawString(GuiStats.this.fontRendererObj, s1, p_180791_2_ + 2, p_180791_3_ + 1 + ((GuiStats)GuiStats.this).fontRendererObj.FONT_HEIGHT, i == 0 ? 0x606060 : 0x909090);
        GuiStats.this.drawString(GuiStats.this.fontRendererObj, s2, p_180791_2_ + 2, p_180791_3_ + 1 + ((GuiStats)GuiStats.this).fontRendererObj.FONT_HEIGHT * 2, j == 0 ? 0x606060 : 0x909090);
    }
}
