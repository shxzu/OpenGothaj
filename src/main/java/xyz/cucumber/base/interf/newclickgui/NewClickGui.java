package xyz.cucumber.base.interf.newclickgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.interf.newclickgui.buttons.NewCategoryButton;
import xyz.cucumber.base.interf.newclickgui.buttons.NewModuleButton;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BloomUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class NewClickGui
extends GuiScreen {
    private PositionUtils position = new PositionUtils(0.0, 0.0, 400.0, 200.0, 1.0f);
    private PositionUtils modulePosition = new PositionUtils(100.0, 22.0, 298.0, 176.0, 1.0f);
    public ResourceLocation logo = new ResourceLocation("client/images/gothaj.png");
    public int c = -5592406;
    public int activeColor = -881934;
    BloomUtils bloom = new BloomUtils();
    private double scrollY;
    private double temp;
    public NewCategoryButton active;
    private List<NewCategoryButton> buttons = new ArrayList<NewCategoryButton>();
    private double animation;
    private boolean dragging;
    private double dragX;
    private double dragY;
    private double rotateAnimation;
    private PositionUtils button = new PositionUtils(0.0, 0.0, 25.0, 25.0, 1.0f);

    public NewClickGui() {
        Category[] categoryArray = Category.values();
        int n = categoryArray.length;
        int n2 = 0;
        while (n2 < n) {
            NewCategoryButton newCategoryButton;
            Category c = categoryArray[n2];
            if (this.active == null) {
                newCategoryButton = new NewCategoryButton(c);
                this.active = this.active;
            } else {
                newCategoryButton = new NewCategoryButton(c);
            }
            this.buttons.add(newCategoryButton);
            ++n2;
        }
        this.position.setX(50.0);
        this.position.setY(50.0);
    }

    @Override
    public void initGui() {
        this.dragging = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.button.setX(10.0);
        this.button.setY(this.height - 35);
        this.modulePosition.setX(this.position.getX() + 100.0);
        this.modulePosition.setY(this.position.getY() + 22.0);
        if (this.dragging) {
            this.position.setX((double)mouseX - this.dragX);
            this.position.setY((double)mouseY - this.dragY);
        }
        RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -15724013, 3.0f);
        RenderUtils.drawOutlinedRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -1879048192, 3.0, 1.0);
        RenderUtils.drawRoundedRect(this.modulePosition.getX(), this.modulePosition.getY(), this.modulePosition.getX2(), this.modulePosition.getY2(), -15658219, 3.0f);
        Fonts.getFont("mitr").drawString("GOTHAJ", this.position.getX() + 30.0, this.position.getY() + 9.0, -1);
        Fonts.getFont("rb-m-13").drawString(Client.INSTANCE.version, this.position.getX() + 30.0 + Fonts.getFont("mitr").getWidth("GOTHAJ") + 2.0, this.position.getY() + 9.0, -5592406);
        RenderUtils.drawLine(this.position.getX() + 6.0, this.position.getY() + 22.0, this.position.getX() + 96.0, this.position.getY() + 22.0, 0x30AAAAAA, 2.0f);
        double i = 0.0;
        for (NewCategoryButton button : this.buttons) {
            button.position.setX(this.position.getX() + 2.0);
            button.position.setY(this.position.getY() + 24.0 + 22.0 * i);
            button.isActive = button == this.active;
            button.draw(mouseX, mouseY);
            i += 1.0;
        }
        this.animation = (this.animation * 9.0 + (double)(22 * this.buttons.indexOf(this.active))) / 10.0;
        RenderUtils.drawRoundedRect(this.position.getX() + 10.0, this.position.getY() + 24.0 + 3.0 + this.animation, this.position.getX() + 12.0, this.position.getY() + 24.0 + 17.0 + this.animation, this.activeColor, 0.5);
        double h = 2.0;
        RenderUtils.enableScisor();
        RenderUtils.scissor(new ScaledResolution(this.mc), this.modulePosition.getX(), this.modulePosition.getY(), this.modulePosition.getWidth(), this.modulePosition.getHeight());
        for (NewModuleButton button : this.active.modules) {
            button.position.setX(this.modulePosition.getX() + 2.0);
            button.position.setY(this.modulePosition.getY() + h - this.scrollY);
            button.draw(mouseX, mouseY);
            h += button.getPosition().getHeight() + 2.0;
        }
        RenderUtils.disableScisor();
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtils.drawImage(this.position.getX() + 5.0, this.position.getY() + 2.0, 20.0, 20.0, this.logo, -1);
        this.rotateAnimation = Client.INSTANCE.getClientSettings().open ? (this.rotateAnimation * 9.0 + 360.0) / 10.0 : this.rotateAnimation * 9.0 / 10.0;
        double save = this.position.getHeight();
        if (save < h + 24.0) {
            float g = Mouse.getEventDWheel();
            double maxScrollY = h + 24.0 - save;
            double size = Mouse.getDWheel() / 60;
            if (size != 0.0) {
                this.temp += size;
            }
            if (Math.round(this.temp) != 0L) {
                this.temp = this.temp * 9.0 / 10.0;
                double l = this.scrollY;
                this.scrollY -= this.temp;
                if (this.scrollY < 0.0) {
                    this.scrollY = 0.0;
                } else if (this.scrollY > maxScrollY) {
                    this.scrollY = maxScrollY;
                }
            } else {
                this.temp = 0.0;
            }
        } else {
            this.scrollY = 0.0;
        }
        RenderUtils.drawCircle(this.button.getX() + this.button.getWidth() / 2.0, this.button.getY() + this.button.getHeight() / 2.0, this.button.getHeight() / 2.0, -12877341, 10.0);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(this.button.getX() + 12.5), (double)(this.button.getY() + 12.5), (double)0.0);
        GL11.glRotated((double)this.rotateAnimation, (double)0.0, (double)0.0, (double)1.0);
        GL11.glTranslated((double)(-this.button.getX() - 12.5), (double)(-this.button.getY() - 12.5), (double)0.0);
        RenderUtils.drawImage(this.button.getX() + 5.0, this.button.getY() + 5.0, 15.0, 15.0, new ResourceLocation("client/images/cs.png"), -1);
        GL11.glPopMatrix();
        Client.INSTANCE.getClientSettings().draw(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (NewModuleButton button : this.active.modules) {
            button.onKey(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Client.INSTANCE.getClientSettings().onClick(mouseX, mouseY, mouseButton);
        if (Client.INSTANCE.getClientSettings().open) {
            return;
        }
        if (this.button.isInside(mouseX, mouseY)) {
            Client.INSTANCE.getClientSettings().init();
            Client.INSTANCE.getClientSettings().open = true;
        }
        for (NewCategoryButton newCategoryButton : this.buttons) {
            if (!newCategoryButton.getPosition().isInside(mouseX, mouseY) || mouseButton != 0) continue;
            this.active = newCategoryButton;
            return;
        }
        if (this.modulePosition.isInside(mouseX, mouseY)) {
            for (NewModuleButton newModuleButton : this.active.modules) {
                newModuleButton.onClick(mouseX, mouseY, mouseButton);
            }
            return;
        }
        if (this.position.isInside(mouseX, mouseY) && mouseButton == 0) {
            this.dragging = true;
            this.dragX = (double)mouseX - this.position.getX();
            this.dragY = (double)mouseY - this.position.getY();
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        Client.INSTANCE.getClientSettings().onRelease(mouseX, mouseY, state);
        if (Client.INSTANCE.getClientSettings().open) {
            return;
        }
        for (NewModuleButton button : this.active.modules) {
            button.onRelease(mouseX, mouseY, state);
        }
        this.dragging = false;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
