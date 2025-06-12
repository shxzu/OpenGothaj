package xyz.cucumber.base.interf.config.ext;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import xyz.cucumber.base.interf.config.ConfigManager;
import xyz.cucumber.base.interf.config.ext.panel.LocalPanel;
import xyz.cucumber.base.interf.config.ext.panel.Panel;
import xyz.cucumber.base.interf.config.ext.panel.PublicPanel;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.button.Button;
import xyz.cucumber.base.utils.cfgs.PublicConfigUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ConfigNavButton
extends Button {
    private String name;
    private ConfigManager configManager;
    private double animation;
    public ArrayList<Panel> configs = new ArrayList();

    public ConfigNavButton(ConfigManager configManager, int id, String name, double x, double y) {
        this.position = new PositionUtils(x, y, 50.0, 13.0, 1.0f);
        this.id = id;
        this.name = name;
        this.configManager = configManager;
        this.updateValues();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        this.animation = this.configManager.active == this ? (this.animation * 9.0 + 100.0) / 10.0 : this.animation * 9.0 / 10.0;
        RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), ColorUtils.getAlphaColor(-11378697, (int)this.animation), 3.0f);
        Fonts.getFont("rb-m-13").drawString(this.name, this.position.getX() + this.position.getWidth() / 2.0 - Fonts.getFont("rb-m-13").getWidth(this.name) / 2.0, this.position.getY() + 5.0, -1);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int b) {
        if (this.position.isInside(mouseX, mouseY)) {
            this.configManager.active = this;
        }
    }

    public void updateValues() {
        block4: {
            block3: {
                this.configs.clear();
                if (this.id != 0) break block3;
                File[] fileArray = new File("Gothaj/configs").listFiles();
                int n = fileArray.length;
                int n2 = 0;
                while (n2 < n) {
                    File f = fileArray[n2];
                    if (f.getName().endsWith(".json")) {
                        this.configs.add(new LocalPanel(f));
                    }
                    ++n2;
                }
                break block4;
            }
            if (this.id != 1) break block4;
            for (Map.Entry<String, String> entry : PublicConfigUtils.getPublicConfigs().entrySet()) {
                this.configs.add(new PublicPanel(entry.getKey(), entry.getValue()));
            }
        }
    }
}
