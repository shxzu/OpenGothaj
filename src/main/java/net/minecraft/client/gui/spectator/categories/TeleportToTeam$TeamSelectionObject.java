package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

class TeleportToTeam$TeamSelectionObject
implements ISpectatorMenuObject {
    private final ScorePlayerTeam field_178676_b;
    private final ResourceLocation field_178677_c;
    private final List<NetworkPlayerInfo> field_178675_d;

    public TeleportToTeam$TeamSelectionObject(ScorePlayerTeam p_i45492_2_) {
        this.field_178676_b = p_i45492_2_;
        this.field_178675_d = Lists.newArrayList();
        for (String s : p_i45492_2_.getMembershipCollection()) {
            NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(s);
            if (networkplayerinfo == null) continue;
            this.field_178675_d.add(networkplayerinfo);
        }
        if (!this.field_178675_d.isEmpty()) {
            String s1 = this.field_178675_d.get(new Random().nextInt(this.field_178675_d.size())).getGameProfile().getName();
            this.field_178677_c = AbstractClientPlayer.getLocationSkin(s1);
            AbstractClientPlayer.getDownloadImageSkin(this.field_178677_c, s1);
        } else {
            this.field_178677_c = DefaultPlayerSkin.getDefaultSkinLegacy();
        }
    }

    @Override
    public void func_178661_a(SpectatorMenu menu) {
        menu.func_178647_a(new TeleportToPlayer(this.field_178675_d));
    }

    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText(this.field_178676_b.getTeamName());
    }

    @Override
    public void func_178663_a(float p_178663_1_, int alpha) {
        int i = -1;
        String s = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
        if (s.length() >= 2) {
            i = Minecraft.getMinecraft().fontRendererObj.getColorCode(s.charAt(1));
        }
        if (i >= 0) {
            float f = (float)(i >> 16 & 0xFF) / 255.0f;
            float f1 = (float)(i >> 8 & 0xFF) / 255.0f;
            float f2 = (float)(i & 0xFF) / 255.0f;
            Gui.drawRect(1.0, 1.0, 15.0, 15.0, MathHelper.func_180183_b(f * p_178663_1_, f1 * p_178663_1_, f2 * p_178663_1_) | alpha << 24);
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178677_c);
        GlStateManager.color(p_178663_1_, p_178663_1_, p_178663_1_, (float)alpha / 255.0f);
        Gui.drawScaledCustomSizeModalRect(2.0, 2.0, 8.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect(2.0, 2.0, 40.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0f, 64.0f);
    }

    @Override
    public boolean func_178662_A_() {
        return !this.field_178675_d.isEmpty();
    }
}
