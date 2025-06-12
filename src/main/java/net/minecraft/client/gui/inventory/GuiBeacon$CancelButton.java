package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.resources.I18n;

class GuiBeacon$CancelButton
extends GuiBeacon.Button {
    public GuiBeacon$CancelButton(int p_i1074_2_, int p_i1074_3_, int p_i1074_4_) {
        super(p_i1074_2_, p_i1074_3_, p_i1074_4_, beaconGuiTextures, 112, 220);
    }

    @Override
    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
        GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.cancel", new Object[0]), mouseX, mouseY);
    }
}
