package net.minecraft.client;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.stats.IStatStringFormat;

class Minecraft$1
implements IStatStringFormat {
    Minecraft$1() {
    }

    @Override
    public String formatString(String str) {
        try {
            return String.format(str, GameSettings.getKeyDisplayString(Minecraft.this.gameSettings.keyBindInventory.getKeyCode()));
        }
        catch (Exception exception) {
            return "Error: " + exception.getLocalizedMessage();
        }
    }
}
