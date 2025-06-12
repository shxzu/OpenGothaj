package xyz.cucumber.base.interf.DropdownClickGui.copy;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.interf.DropdownClickGui.ext.DropdownButton;
import xyz.cucumber.base.interf.DropdownClickGui.ext.DropdownCategory;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BlurUtils;

public class DropdownClickGui
extends GuiScreen {
    private ArrayList<DropdownButton> buttons = new ArrayList();
    private double initTimer = 0.0;
    private PositionUtils position = new PositionUtils(0.0, 0.0, 25.0, 25.0, 1.0f);
    private double rotateAnimation;

    public DropdownClickGui() {
        double centerX = 20.0;
        double centerY = 20.0;
        this.buttons.clear();
        int i = 0;
        Category[] categoryArray = Category.values();
        int n = categoryArray.length;
        int n2 = 0;
        while (n2 < n) {
            Category c = categoryArray[n2];
            DropdownCategory category = new DropdownCategory(c);
            category.getPosition().setX(centerX + (double)(112 * i));
            category.getPosition().setY(centerY);
            category.getPosition().setHeight(20.0);
            this.buttons.add(category);
            ++i;
            ++n2;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.initTimer < 7.0) {
            this.initTimer += 0.5;
        }
        this.position.setX(10.0);
        this.position.setY(this.height - 35);
        BlurUtils.renderBlur((float)this.initTimer);
        for (DropdownButton b : this.buttons) {
            b.draw(mouseX, mouseY);
        }
        Client.INSTANCE.getConfigManager().draw(mouseX, mouseY);
        Client.INSTANCE.getClientSettings().draw(mouseX, mouseY);
        RenderUtils.drawCircle(this.position.getX() + this.position.getWidth() / 2.0, this.position.getY() + this.position.getHeight() / 2.0, this.position.getHeight() / 2.0, -12877341, 10.0);
        this.rotateAnimation = Client.INSTANCE.getClientSettings().open ? (this.rotateAnimation * 9.0 + 360.0) / 10.0 : this.rotateAnimation * 9.0 / 10.0;
        GL11.glPushMatrix();
        GL11.glTranslated((double)(this.position.getX() + 12.5), (double)(this.position.getY() + 12.5), (double)0.0);
        GL11.glRotated((double)this.rotateAnimation, (double)0.0, (double)0.0, (double)1.0);
        GL11.glTranslated((double)(-this.position.getX() - 12.5), (double)(-this.position.getY() - 12.5), (double)0.0);
        RenderUtils.drawImage(this.position.getX() + 5.0, this.position.getY() + 5.0, 15.0, 15.0, new ResourceLocation("client/images/cs.png"), -1);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        Client.INSTANCE.getConfigManager().onKey(keyCode, typedChar);
        if (Client.INSTANCE.getConfigManager().open) {
            return;
        }
        for (DropdownButton b : this.buttons) {
            b.onKey(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Client.INSTANCE.getClientSettings().onClick(mouseX, mouseY, mouseButton);
        if (Client.INSTANCE.getClientSettings().open) {
            return;
        }
        Client.INSTANCE.getConfigManager().onClick(mouseX, mouseY, mouseButton);
        if (Client.INSTANCE.getConfigManager().open) {
            return;
        }
        if (this.position.isInside(mouseX, mouseY)) {
            Client.INSTANCE.getClientSettings().init();
            Client.INSTANCE.getClientSettings().open = true;
        }
        for (DropdownButton b : this.buttons) {
            b.onClick(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        Client.INSTANCE.getClientSettings().onRelease(mouseX, mouseY, state);
        if (Client.INSTANCE.getClientSettings().open) {
            return;
        }
        Client.INSTANCE.getConfigManager().onRelease(mouseX, mouseY, state);
        for (DropdownButton b : this.buttons) {
            b.onRelease(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
        this.initTimer = 1.0;
        Client.INSTANCE.getConfigManager().initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
