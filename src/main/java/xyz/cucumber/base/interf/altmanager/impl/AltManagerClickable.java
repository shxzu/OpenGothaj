package xyz.cucumber.base.interf.altmanager.impl;

import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.button.Button;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class AltManagerClickable
extends Button {
    private String text;
    private int animation;

    public AltManagerClickable(int id, String text, double x, double y, double width, double height) {
        this.setPosition(new PositionUtils(x, y, width, height, 1.0f));
        this.setId(id);
        this.text = text;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        this.animation = this.getPosition().isInside(mouseX, mouseY) ? (this.animation * 9 + 50) / 10 : (this.animation * 9 + 30) / 10;
        RenderUtils.drawRoundedRect(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getX2(), this.getPosition().getY2(), ColorUtils.getAlphaColor(0x35AAAAAA, this.animation), 2.0f);
        Fonts.getFont("rb-m").drawString(this.text, this.getPosition().getX() + this.getPosition().getWidth() / 2.0 - Fonts.getFont("rb-m").getWidth(this.text) / 2.0, this.getPosition().getY() + this.getPosition().getHeight() / 2.0 - (double)(Fonts.getFont("rb-m").getHeight(this.text) / 2.0f) - 1.0, -5592406);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int b) {
    }
}
