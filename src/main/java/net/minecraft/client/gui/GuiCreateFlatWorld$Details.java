package net.minecraft.client.gui;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.FlatLayerInfo;

class GuiCreateFlatWorld$Details
extends GuiSlot {
    public int field_148228_k;

    public GuiCreateFlatWorld$Details() {
        super(GuiCreateFlatWorld.this.mc, GuiCreateFlatWorld.this.width, GuiCreateFlatWorld.this.height, 43, GuiCreateFlatWorld.this.height - 60, 24);
        this.field_148228_k = -1;
    }

    private void func_148225_a(int p_148225_1_, int p_148225_2_, ItemStack p_148225_3_) {
        this.func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
        GlStateManager.enableRescaleNormal();
        if (p_148225_3_ != null && p_148225_3_.getItem() != null) {
            RenderHelper.enableGUIStandardItemLighting();
            GuiCreateFlatWorld.this.itemRender.renderItemIntoGUI(p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
            RenderHelper.disableStandardItemLighting();
        }
        GlStateManager.disableRescaleNormal();
    }

    private void func_148226_e(int p_148226_1_, int p_148226_2_) {
        this.func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
    }

    private void func_148224_c(int p_148224_1_, int p_148224_2_, int p_148224_3_, int p_148224_4_) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(Gui.statIcons);
        float f = 0.0078125f;
        float f1 = 0.0078125f;
        int i = 18;
        int j = 18;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(p_148224_1_ + 0, p_148224_2_ + 18, zLevel).tex((float)(p_148224_3_ + 0) * 0.0078125f, (float)(p_148224_4_ + 18) * 0.0078125f).endVertex();
        worldrenderer.pos(p_148224_1_ + 18, p_148224_2_ + 18, zLevel).tex((float)(p_148224_3_ + 18) * 0.0078125f, (float)(p_148224_4_ + 18) * 0.0078125f).endVertex();
        worldrenderer.pos(p_148224_1_ + 18, p_148224_2_ + 0, zLevel).tex((float)(p_148224_3_ + 18) * 0.0078125f, (float)(p_148224_4_ + 0) * 0.0078125f).endVertex();
        worldrenderer.pos(p_148224_1_ + 0, p_148224_2_ + 0, zLevel).tex((float)(p_148224_3_ + 0) * 0.0078125f, (float)(p_148224_4_ + 0) * 0.0078125f).endVertex();
        tessellator.draw();
    }

    @Override
    protected int getSize() {
        return GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        this.field_148228_k = slotIndex;
        GuiCreateFlatWorld.this.func_146375_g();
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == this.field_148228_k;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        String s;
        FlatLayerInfo flatlayerinfo = GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().get(GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - entryID - 1);
        IBlockState iblockstate = flatlayerinfo.getLayerMaterial();
        Block block = iblockstate.getBlock();
        Item item = Item.getItemFromBlock(block);
        ItemStack itemstack = block != Blocks.air && item != null ? new ItemStack(item, 1, block.getMetaFromState(iblockstate)) : null;
        String string = s = itemstack == null ? "Air" : item.getItemStackDisplayName(itemstack);
        if (item == null) {
            if (block != Blocks.water && block != Blocks.flowing_water) {
                if (block == Blocks.lava || block == Blocks.flowing_lava) {
                    item = Items.lava_bucket;
                }
            } else {
                item = Items.water_bucket;
            }
            if (item != null) {
                itemstack = new ItemStack(item, 1, block.getMetaFromState(iblockstate));
                s = block.getLocalizedName();
            }
        }
        this.func_148225_a(p_180791_2_, p_180791_3_, itemstack);
        GuiCreateFlatWorld.this.fontRendererObj.drawString(s, p_180791_2_ + 18 + 5, p_180791_3_ + 3, 0xFFFFFF);
        String s1 = entryID == 0 ? I18n.format("createWorld.customize.flat.layer.top", flatlayerinfo.getLayerCount()) : (entryID == GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - 1 ? I18n.format("createWorld.customize.flat.layer.bottom", flatlayerinfo.getLayerCount()) : I18n.format("createWorld.customize.flat.layer", flatlayerinfo.getLayerCount()));
        GuiCreateFlatWorld.this.fontRendererObj.drawString(s1, p_180791_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(s1), p_180791_3_ + 3, 0xFFFFFF);
    }

    @Override
    protected int getScrollBarX() {
        return this.width - 70;
    }
}
