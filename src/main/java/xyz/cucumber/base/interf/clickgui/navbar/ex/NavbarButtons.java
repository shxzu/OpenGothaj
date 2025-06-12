package xyz.cucumber.base.interf.clickgui.navbar.ex;

import xyz.cucumber.base.utils.position.PositionUtils;

public class NavbarButtons {
    PositionUtils position;
    NavCategory navcategory;

    public void draw(int mouseX, int mouseY) {
    }

    public void clicked(int mouseX, int mouseY, int button) {
    }

    public void released(int mouseX, int mouseY, int button) {
    }

    public PositionUtils getPosition() {
        return this.position;
    }

    public void setPosition(PositionUtils position) {
        this.position = position;
    }

    public NavCategory getNavCategory() {
        return this.navcategory;
    }

    public void setNavCategory(NavCategory category) {
        this.navcategory = category;
    }

    public static enum NavCategory {
        Modules,
        Client;

    }
}
