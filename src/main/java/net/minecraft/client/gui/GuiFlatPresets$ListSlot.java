package net.minecraft.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiFlatPresets;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class GuiFlatPresets$ListSlot
extends GuiSlot {
    public int field_148175_k;

    public GuiFlatPresets$ListSlot() {
        super(GuiFlatPresets.this.mc, GuiFlatPresets.this.width, GuiFlatPresets.this.height, 80, GuiFlatPresets.this.height - 37, 24);
        this.field_148175_k = -1;
    }

    private void func_178054_a(int p_178054_1_, int p_178054_2_, Item p_178054_3_, int p_178054_4_) {
        this.func_148173_e(p_178054_1_ + 1, p_178054_2_ + 1);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GuiFlatPresets.this.itemRender.renderItemIntoGUI(new ItemStack(p_178054_3_, 1, p_178054_4_), p_178054_1_ + 2, p_178054_2_ + 2);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    private void func_148173_e(int p_148173_1_, int p_148173_2_) {
        this.func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
    }

    private void func_148171_c(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(Gui.statIcons);
        float f = 0.0078125f;
        float f1 = 0.0078125f;
        int i = 18;
        int j = 18;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(p_148171_1_ + 0, p_148171_2_ + 18, zLevel).tex((float)(p_148171_3_ + 0) * 0.0078125f, (float)(p_148171_4_ + 18) * 0.0078125f).endVertex();
        worldrenderer.pos(p_148171_1_ + 18, p_148171_2_ + 18, zLevel).tex((float)(p_148171_3_ + 18) * 0.0078125f, (float)(p_148171_4_ + 18) * 0.0078125f).endVertex();
        worldrenderer.pos(p_148171_1_ + 18, p_148171_2_ + 0, zLevel).tex((float)(p_148171_3_ + 18) * 0.0078125f, (float)(p_148171_4_ + 0) * 0.0078125f).endVertex();
        worldrenderer.pos(p_148171_1_ + 0, p_148171_2_ + 0, zLevel).tex((float)(p_148171_3_ + 0) * 0.0078125f, (float)(p_148171_4_ + 0) * 0.0078125f).endVertex();
        tessellator.draw();
    }

    @Override
    protected int getSize() {
        return FLAT_WORLD_PRESETS.size();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        this.field_148175_k = slotIndex;
        GuiFlatPresets.this.func_146426_g();
        GuiFlatPresets.this.field_146433_u.setText(((GuiFlatPresets.LayerItem)FLAT_WORLD_PRESETS.get((int)((GuiFlatPresets)GuiFlatPresets.this).field_146435_s.field_148175_k)).field_148233_c);
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == this.field_148175_k;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        GuiFlatPresets.LayerItem guiflatpresets$layeritem = (GuiFlatPresets.LayerItem)FLAT_WORLD_PRESETS.get(entryID);
        this.func_178054_a(p_180791_2_, p_180791_3_, guiflatpresets$layeritem.field_148234_a, guiflatpresets$layeritem.field_179037_b);
        GuiFlatPresets.this.fontRendererObj.drawString(guiflatpresets$layeritem.field_148232_b, p_180791_2_ + 18 + 5, p_180791_3_ + 6, 0xFFFFFF);
    }
}
