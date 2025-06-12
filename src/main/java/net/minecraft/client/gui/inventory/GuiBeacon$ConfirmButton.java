package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.resources.I18n;

class GuiBeacon$ConfirmButton
extends GuiBeacon.Button {
    public GuiBeacon$ConfirmButton(int p_i1075_2_, int p_i1075_3_, int p_i1075_4_) {
        super(p_i1075_2_, p_i1075_3_, p_i1075_4_, beaconGuiTextures, 90, 220);
    }

    @Override
    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
        GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.done", new Object[0]), mouseX, mouseY);
    }
}
