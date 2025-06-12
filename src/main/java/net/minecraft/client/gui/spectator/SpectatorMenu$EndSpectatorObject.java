package net.minecraft.client.gui.spectator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

class SpectatorMenu$EndSpectatorObject
implements ISpectatorMenuObject {
    private SpectatorMenu$EndSpectatorObject() {
    }

    @Override
    public void func_178661_a(SpectatorMenu menu) {
        menu.func_178641_d();
    }

    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText("Close menu");
    }

    @Override
    public void func_178663_a(float p_178663_1_, int alpha) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture(0.0f, 0.0f, 128.0f, 0.0f, 16.0, 16.0, 256.0, 256.0);
    }

    @Override
    public boolean func_178662_A_() {
        return true;
    }
}
