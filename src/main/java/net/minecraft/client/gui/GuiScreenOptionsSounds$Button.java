package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

class GuiScreenOptionsSounds$Button
extends GuiButton {
    private final SoundCategory field_146153_r;
    private final String field_146152_s;
    public float field_146156_o;
    public boolean field_146155_p;

    public GuiScreenOptionsSounds$Button(int p_i45024_2_, int p_i45024_3_, int p_i45024_4_, SoundCategory p_i45024_5_, boolean p_i45024_6_) {
        super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
        this.field_146156_o = 1.0f;
        this.field_146153_r = p_i45024_5_;
        this.field_146152_s = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
        this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(p_i45024_5_);
        this.field_146156_o = GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(p_i45024_5_);
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.field_146155_p) {
                this.field_146156_o = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
                this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0f, 1.0f);
                mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
                mc.gameSettings.saveOptions();
                this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.field_146156_o = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
            this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0f, 1.0f);
            mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
            mc.gameSettings.saveOptions();
            this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
            this.field_146155_p = true;
            return true;
        }
        return false;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        if (this.field_146155_p) {
            if (this.field_146153_r == SoundCategory.MASTER) {
                float f = 1.0f;
            } else {
                GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(this.field_146153_r);
            }
            GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
        }
        this.field_146155_p = false;
    }
}
