package net.minecraft.client;

import net.minecraft.client.gui.GuiYesNoCallback;

class Minecraft$17
implements GuiYesNoCallback {
    Minecraft$17() {
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (result) {
            Minecraft.this.getTwitchStream().func_152930_t();
        }
        Minecraft.this.displayGuiScreen(null);
    }
}
