package net.minecraft.client.gui.spectator;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class PlayerMenuObject
implements ISpectatorMenuObject {
    private final GameProfile profile;
    private final ResourceLocation resourceLocation;

    public PlayerMenuObject(GameProfile profileIn) {
        this.profile = profileIn;
        this.resourceLocation = AbstractClientPlayer.getLocationSkin(profileIn.getName());
        AbstractClientPlayer.getDownloadImageSkin(this.resourceLocation, profileIn.getName());
    }

    @Override
    public void func_178661_a(SpectatorMenu menu) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C18PacketSpectate(this.profile.getId()));
    }

    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText(this.profile.getName());
    }

    @Override
    public void func_178663_a(float p_178663_1_, int alpha) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        GlStateManager.color(1.0f, 1.0f, 1.0f, (float)alpha / 255.0f);
        Gui.drawScaledCustomSizeModalRect(2.0, 2.0, 8.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect(2.0, 2.0, 40.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0f, 64.0f);
    }

    @Override
    public boolean func_178662_A_() {
        return true;
    }
}
