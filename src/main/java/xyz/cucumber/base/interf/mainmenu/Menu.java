package xyz.cucumber.base.interf.mainmenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.RandomUtils;
import xyz.cucumber.base.interf.altmanager.AltManager;
import xyz.cucumber.base.interf.mainmenu.buttons.MenuButton;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BloomUtils;
import xyz.cucumber.base.utils.render.Particle;

public class Menu
extends GuiScreen {
    private List<MenuButton> buttons = new ArrayList<MenuButton>();
    private List<Particle> particles = new ArrayList<Particle>();
    private float startTime;
    public AltManager altmanager;
    private BloomUtils bloom = new BloomUtils();

    @Override
    public void initGui() {
        this.buttons.clear();
        this.particles.clear();
        this.startTime = System.nanoTime();
        this.altmanager = new AltManager(this);
        this.buttons.add(new MenuButton("Changelogs", new PositionUtils(10.0, 10.0, 90.0, 22.0, 1.0f), -5636248, -30327, 0));
        this.buttons.add(new MenuButton("Single Player", new PositionUtils(this.width / 2 - 80, this.height / 2 - 60, 160.0, 22.0, 1.0f), -2404361, -13929729, 1));
        this.buttons.add(new MenuButton("Multi Player", new PositionUtils(this.width / 2 - 80, this.height / 2 - 60 + 24, 160.0, 22.0, 1.0f), -12346117, -948229, 2));
        this.buttons.add(new MenuButton("Alt Manager", new PositionUtils(this.width / 2 - 80, this.height / 2 - 60 + 48, 79.0, 22.0, 1.0f), -748816, -6189618, 3));
        this.buttons.add(new MenuButton("Settings", new PositionUtils(this.width / 2 + 1, this.height / 2 - 60 + 48, 79.0, 22.0, 1.0f), -10592050, -8323109, 4));
        this.buttons.add(new MenuButton("Exit", new PositionUtils(this.width / 2 - 80, this.height / 2 - 60 + 72, 160.0, 22.0, 1.0f), -14384521, -7809877, 5));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        if (this.particles.size() < 200) {
            int needed = 200 - this.particles.size();
            int i = 0;
            while (i < needed) {
                this.particles.add(new Particle(RandomUtils.nextInt(0, this.width), RandomUtils.nextInt(0, this.height), RandomUtils.nextInt(2, 4) / 2, 2, -1, RandomUtils.nextInt(0, 360), RandomUtils.nextInt(1000, 3000)));
                ++i;
            }
        }
        Iterator<Particle> iterator = this.particles.iterator();
        while (iterator.hasNext()) {
            boolean b = iterator.next().draw();
            if (!b) continue;
            iterator.remove();
        }
        for (Particle p : this.particles) {
            double diffY;
            double diffX = p.getX() - (double)mouseX;
            double dist = Math.sqrt(diffX * diffX + (diffY = p.getY() - (double)mouseY) * diffY);
            if (dist < 50.0) {
                RenderUtils.drawLine(p.getX(), p.getY(), mouseX, mouseY, 0x20FFFFFF, 0.5f);
            }
            for (Particle p2 : this.particles) {
                double difY;
                double difX;
                double dit;
                if (p2 == p || !((dit = Math.sqrt((difX = p.getX() - p2.getX()) * difX + (difY = p.getY() - p2.getY()) * difY)) < 30.0)) continue;
                RenderUtils.drawLine(p.getX(), p.getY(), p2.getX(), p2.getY(), 0x5FFFFFF, 0.5f);
            }
        }
        for (MenuButton b : this.buttons) {
            b.draw(mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtils.drawImage(this.width / 2 - 60, this.height / 2 - 60 - 60, 120.0, 50.0, new ResourceLocation("client/images/gothaj_logo.png"), -1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (MenuButton b : this.buttons) {
            if (!b.getPosition().isInside(mouseX, mouseY)) continue;
            switch (b.getId()) {
                case 0: {
                    break;
                }
                case 1: {
                    this.mc.displayGuiScreen(new GuiSelectWorld(this));
                    break;
                }
                case 2: {
                    this.mc.displayGuiScreen(new GuiMultiplayer(this));
                    break;
                }
                case 3: {
                    this.mc.displayGuiScreen(this.altmanager);
                    return;
                }
                case 5: {
                    this.mc.shutdown();
                    return;
                }
                case 4: {
                    this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
