package xyz.cucumber.base.interf.clickgui.content.ex.impl;

import java.awt.Color;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import xyz.cucumber.base.interf.clickgui.ClickGui;
import xyz.cucumber.base.interf.clickgui.content.ex.impl.SettingsButton;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ColorButton
extends SettingsButton {
    private ColorSettings setting;
    private PositionUtils color1;
    private PositionUtils hue1;
    private PositionUtils color2;
    private PositionUtils hue2;
    private PositionUtils alpha;
    private PositionUtils mode;
    private boolean open;
    private float h1;
    private float h2;
    private double height;
    private double rollAnimation;

    public ColorButton(ColorSettings setting, PositionUtils position, PositionUtils color1, PositionUtils color2, PositionUtils alpha, PositionUtils mode) {
        this.settingMain = setting;
        this.position = position;
        this.setting = setting;
        this.color1 = color1;
        this.color2 = color2;
        this.alpha = alpha;
        this.mode = mode;
        this.hue1 = new PositionUtils(0.0, 0.0, 65.0, 4.0, 1.0f);
        this.hue2 = new PositionUtils(0.0, 0.0, 65.0, 4.0, 1.0f);
        this.height = position.getHeight();
        this.h1 = 0.0f;
        this.h2 = 0.0f;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Fonts.getFont("rb-r").drawString(this.setting.getName(), this.position.getX() + 8.0, this.position.getY() + 3.0, -1);
        RenderUtils.drawRoundedRect(this.position.getX() + this.position.getWidth() / 2.0 - 15.0, this.position.getY() + 2.0, this.position.getX() + this.position.getWidth() / 2.0 - 1.0, this.position.getY() + this.height - 4.0, this.setting.getMainColor(), 0.2f);
        RenderUtils.drawRoundedRect(this.position.getX() + this.position.getWidth() / 2.0 + 1.0, this.position.getY() + 2.0, this.position.getX() + this.position.getWidth() / 2.0 + 15.0, this.position.getY() + this.height - 4.0, this.setting.getSecondaryColor(), 0.2f);
        Fonts.getFont("rb-r").drawString("Alpha: §f" + this.setting.getAlpha() + "%", this.position.getX2() - Fonts.getFont("rb-r").getWidth("Alpha: " + this.setting.getAlpha() + "%") - 5.0, this.position.getY() + 3.0, -12424715);
        if (this.open) {
            this.rollAnimation = (this.rollAnimation * 11.0 + 65.0) / 12.0;
            this.position.setHeight(this.height + this.rollAnimation);
            this.color1.setX(this.position.getX() + 15.0);
            this.color1.setY(this.position.getY() + this.height + 2.5);
            this.hue1.setX(this.color1.getX());
            this.hue1.setY(this.color1.getY2() + 2.5);
            RenderUtils.drawColorPicker(this.color1.getX(), this.color1.getY(), this.color1.getWidth(), this.color1.getHeight(), this.h1);
            RenderUtils.drawColorSlider(this.hue1.getX(), this.hue1.getY(), this.hue1.getWidth(), this.hue1.getHeight());
            this.color2.setX(this.color1.getX2() + 5.0);
            this.color2.setY(this.position.getY() + this.height + 2.5);
            this.hue2.setX(this.color2.getX());
            this.hue2.setY(this.color2.getY2() + 2.5);
            RenderUtils.drawColorPicker(this.color2.getX(), this.color2.getY(), this.color2.getWidth(), this.color2.getHeight(), this.h2);
            RenderUtils.drawColorSlider(this.hue2.getX(), this.hue2.getY(), this.hue2.getWidth(), this.hue2.getHeight());
            this.mode.setX(this.color2.getX2() + 2.5);
            this.mode.setY(this.position.getY() + this.height + (this.position.getHeight() - this.height) / 2.0 - (double)(Fonts.getFont("rb-r").getHeight("Mode: " + this.setting.getMode()) / 2.0f));
            this.mode.setWidth(Fonts.getFont("rb-r").getWidth("Mode: " + this.setting.getMode()));
            this.mode.setHeight(10.0);
            Fonts.getFont("rb-r").drawString("Mode: §f" + this.setting.getMode(), this.mode.getX(), this.mode.getY() + 3.0, -12424715);
            this.alpha.setX(this.color1.getX());
            this.alpha.setY(this.hue1.getY2() + 2.5);
            RenderUtils.drawImage(this.alpha.getX() + 1.0, this.alpha.getY() + 1.0, this.alpha.getWidth() - 1.0, this.alpha.getHeight() - 1.0, new ResourceLocation("client/images/alpha.png"), -1);
            RenderUtils.drawGradientRectSideways(this.alpha.getX(), this.alpha.getY(), this.alpha.getX2(), this.alpha.getY2(), 0, -16777216);
            if (ClickGui.getContent().getPosition().isInside(mouseX, mouseY)) {
                double diffX;
                double diffY;
                double diffX2;
                double y;
                double x;
                if (this.color1.isInside(mouseX, mouseY)) {
                    x = this.color1.getX();
                    while (x < this.color1.getX2()) {
                        y = this.color1.getY();
                        while (y < this.color1.getY2()) {
                            if ((double)mouseX > x && (double)mouseX < x + 1.0 && (double)mouseY > y && (double)mouseY < y + 1.0 && Mouse.isButtonDown((int)0)) {
                                diffX2 = x - this.color1.getX();
                                diffY = this.color1.getY2() - y;
                                this.setting.setMainColor(Color.HSBtoRGB(this.h1, (float)(diffX2 / this.color1.getWidth()), (float)(diffY / this.color1.getHeight())));
                            }
                            y += 1.0;
                        }
                        x += 1.0;
                    }
                }
                if (this.color2.isInside(mouseX, mouseY)) {
                    x = this.color2.getX();
                    while (x < this.color2.getX2()) {
                        y = this.color2.getY();
                        while (y < this.color2.getY2()) {
                            if ((double)mouseX > x && (double)mouseX < x + 1.0 && (double)mouseY > y && (double)mouseY < y + 1.0 && Mouse.isButtonDown((int)0)) {
                                diffX2 = x - this.color2.getX();
                                diffY = this.color2.getY2() - y;
                                this.setting.setSecondaryColor(Color.HSBtoRGB(this.h2, (float)(diffX2 / this.color2.getWidth()), (float)(diffY / this.color2.getHeight())));
                            }
                            y += 1.0;
                        }
                        x += 1.0;
                    }
                }
                if (this.hue1.isInside(mouseX, mouseY)) {
                    x = this.hue1.getX();
                    while (x < this.hue1.getX2()) {
                        if ((double)mouseX > x && (double)mouseX < x + 1.0 && (double)mouseY > this.hue1.getY() && (double)mouseY < this.hue1.getY2() && Mouse.isButtonDown((int)0)) {
                            diffX = x - this.hue1.getX();
                            this.h1 = (float)(diffX / this.hue1.getWidth());
                        }
                        x += 1.0;
                    }
                }
                if (this.hue2.isInside(mouseX, mouseY)) {
                    x = this.hue2.getX();
                    while (x < this.hue2.getX2()) {
                        if ((double)mouseX > x && (double)mouseX < x + 1.0 && (double)mouseY > this.hue2.getY() && (double)mouseY < this.hue2.getY2() && Mouse.isButtonDown((int)0)) {
                            diffX = x - this.hue2.getX();
                            this.h2 = (float)(diffX / this.hue2.getWidth());
                        }
                        x += 1.0;
                    }
                }
                if (this.alpha.isInside(mouseX, mouseY)) {
                    x = this.alpha.getX();
                    while (x < this.alpha.getX2()) {
                        if ((double)mouseX > x && (double)mouseX < x + 1.0 && (double)mouseY > this.alpha.getY() && (double)mouseY < this.alpha.getY2() && Mouse.isButtonDown((int)0)) {
                            diffX = x - this.alpha.getX() - 1.0;
                            this.setting.setAlpha((int)((this.alpha.getWidth() - diffX) / this.alpha.getWidth() * 100.0));
                        }
                        x += 1.0;
                    }
                }
            }
        } else {
            this.rollAnimation = (this.rollAnimation * 11.0 + this.height) / 12.0;
            this.position.setHeight(this.rollAnimation);
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        if (this.position.isInside(mouseX, mouseY) && button == 1) {
            boolean bl = this.open = !this.open;
        }
        if (this.position.isInside(mouseX, mouseY) && this.mode.isInside(mouseX, mouseY) && button == 0) {
            this.setting.cycleModes();
        }
    }

    public ColorSettings getSetting() {
        return this.setting;
    }

    public void setSetting(ColorSettings setting) {
        this.setting = setting;
    }

    public PositionUtils getColor1() {
        return this.color1;
    }

    public void setColor1(PositionUtils color1) {
        this.color1 = color1;
    }

    public PositionUtils getColor2() {
        return this.color2;
    }

    public void setColor2(PositionUtils color2) {
        this.color2 = color2;
    }

    public PositionUtils getAlpha() {
        return this.alpha;
    }

    public void setAlpha(PositionUtils alpha) {
        this.alpha = alpha;
    }

    public PositionUtils getMode() {
        return this.mode;
    }

    public void setMode(PositionUtils mode) {
        this.mode = mode;
    }
}
