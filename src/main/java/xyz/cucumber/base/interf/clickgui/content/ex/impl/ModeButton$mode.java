package xyz.cucumber.base.interf.clickgui.content.ex.impl;

import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ModeButton$mode {
    public String mode;
    public PositionUtils pos;

    public ModeButton$mode(String mode2, PositionUtils pos) {
        this.mode = mode2;
        this.pos = pos;
    }

    public void draw(int mouseX, int mouseY) {
        RenderUtils.drawRect(this.pos.getX(), this.pos.getY(), this.pos.getX2(), this.pos.getY2(), 0x30000000);
        Fonts.getFont("rb-r").drawString(this.mode, this.pos.getX() + this.pos.getWidth() / 2.0 - Fonts.getFont("rb-r").getWidth(this.mode) / 2.0, this.pos.getY() + this.pos.getHeight() / 2.0 - (double)(Fonts.getFont("rb-r").getHeight(this.mode) / 2.0f), -1);
    }

    public void click(int mouseX, int mouseY, ModeSettings setting, int button) {
        if (this.pos.isInside(mouseX, mouseY) && button == 0) {
            setting.setMode(this.mode);
        }
    }
}
