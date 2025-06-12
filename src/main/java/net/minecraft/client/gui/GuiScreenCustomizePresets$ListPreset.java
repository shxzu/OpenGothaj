package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiScreenCustomizePresets;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

class GuiScreenCustomizePresets$ListPreset
extends GuiSlot {
    public int field_178053_u;

    public GuiScreenCustomizePresets$ListPreset() {
        super(GuiScreenCustomizePresets.this.mc, GuiScreenCustomizePresets.this.width, GuiScreenCustomizePresets.this.height, 80, GuiScreenCustomizePresets.this.height - 32, 38);
        this.field_178053_u = -1;
    }

    @Override
    protected int getSize() {
        return field_175310_f.size();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        this.field_178053_u = slotIndex;
        GuiScreenCustomizePresets.this.func_175304_a();
        GuiScreenCustomizePresets.this.field_175317_i.setText(((GuiScreenCustomizePresets.Info)field_175310_f.get((int)((GuiScreenCustomizePresets)GuiScreenCustomizePresets.this).field_175311_g.field_178053_u)).field_178954_c.toString());
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == this.field_178053_u;
    }

    @Override
    protected void drawBackground() {
    }

    private void func_178051_a(int p_178051_1_, int p_178051_2_, ResourceLocation p_178051_3_) {
        int i = p_178051_1_ + 5;
        GuiScreenCustomizePresets.this.drawHorizontalLine(i - 1, i + 32, p_178051_2_ - 1, -2039584);
        GuiScreenCustomizePresets.this.drawHorizontalLine(i - 1, i + 32, p_178051_2_ + 32, -6250336);
        GuiScreenCustomizePresets.this.drawVerticalLine(i - 1, p_178051_2_ - 1, p_178051_2_ + 32, -2039584);
        GuiScreenCustomizePresets.this.drawVerticalLine(i + 32, p_178051_2_ - 1, p_178051_2_ + 32, -6250336);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(p_178051_3_);
        int j = 32;
        int k = 32;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(i + 0, p_178051_2_ + 32, 0.0).tex(0.0, 1.0).endVertex();
        worldrenderer.pos(i + 32, p_178051_2_ + 32, 0.0).tex(1.0, 1.0).endVertex();
        worldrenderer.pos(i + 32, p_178051_2_ + 0, 0.0).tex(1.0, 0.0).endVertex();
        worldrenderer.pos(i + 0, p_178051_2_ + 0, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        GuiScreenCustomizePresets.Info guiscreencustomizepresets$info = (GuiScreenCustomizePresets.Info)field_175310_f.get(entryID);
        this.func_178051_a(p_180791_2_, p_180791_3_, guiscreencustomizepresets$info.field_178953_b);
        GuiScreenCustomizePresets.this.fontRendererObj.drawString(guiscreencustomizepresets$info.field_178955_a, p_180791_2_ + 32 + 10, p_180791_3_ + 14, 0xFFFFFF);
    }
}
