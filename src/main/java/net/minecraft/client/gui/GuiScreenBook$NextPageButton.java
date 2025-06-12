package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

class GuiScreenBook$NextPageButton
extends GuiButton {
    private final boolean field_146151_o;

    public GuiScreenBook$NextPageButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_) {
        super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
        this.field_146151_o = p_i46316_4_;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            mc.getTextureManager().bindTexture(bookGuiTextures);
            int i = 0;
            int j = 192;
            if (flag) {
                i += 23;
            }
            if (!this.field_146151_o) {
                j += 13;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, i, j, 23, 13);
        }
    }
}
