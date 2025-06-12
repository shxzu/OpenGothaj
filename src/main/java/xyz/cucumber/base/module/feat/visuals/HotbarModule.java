package xyz.cucumber.base.module.feat.visuals;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRenderHotbar;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

@ModuleInfo(category=Category.VISUALS, description="Displays hotbar", name="Hotbar", priority=ArrayPriority.LOW)
public class HotbarModule
extends Mod {
    private ModeSettings mode = new ModeSettings("Mode", new String[]{"Style 1", "Style 2"});
    public ColorSettings textColor = new ColorSettings("Text Color", "Static", -1, -1, 100);
    public ColorSettings backgroundColor = new ColorSettings("Background Color", "Static", -1, -1, 100);
    private double animation;

    public HotbarModule() {
        this.addSettings(this.mode, this.textColor, this.backgroundColor);
    }

    @EventListener
    public void onRenderHotbar(EventRenderHotbar e) {
        double k;
        int j;
        e.setCancelled(true);
        RenderUtils.drawRect(0.0, e.getScaledResolution().getScaledHeight_double() - 23.0, e.getScaledResolution().getScaledWidth_double(), e.getScaledResolution().getScaledWidth_double(), ColorUtils.getColor(this.backgroundColor, System.nanoTime() / 1000000L, 0.0, 5.0));
        double centerX = e.getScaledResolution().getScaledWidth() / 2 - 103;
        this.animation = (this.animation * 10.0 + (double)(23 * this.mc.thePlayer.inventory.currentItem)) / 11.0;
        switch (this.mode.getMode().toLowerCase()) {
            case "style 1": {
                this.renderGradientCircle(centerX + 11.0 + this.animation, e.getScaledResolution().getScaledHeight_double() - 11.0, 13.0, 0x77000000);
                j = 0;
                while (j < 9) {
                    k = centerX + (double)(23 * j);
                    Fonts.getFont("rb-b").drawString(String.valueOf(j + 1), k + 11.0 - Fonts.getFont("rb-b").getWidth(String.valueOf(j + 1)) / 2.0 + 0.5, e.getScaledResolution().getScaledHeight_double() - 11.0 - 2.0, -5592406);
                    ++j;
                }
                break;
            }
            case "style 2": {
                RenderUtils.drawRect(centerX + this.animation, e.getScaledResolution().getScaledHeight() - 23, centerX + this.animation + 23.0, e.getScaledResolution().getScaledHeight(), 0x77000000);
            }
        }
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        j = 0;
        while (j < 9) {
            k = centerX + 3.0 + (double)(23 * j);
            double l = e.getScaledResolution().getScaledHeight() - 23 + 3;
            if (this.mc.thePlayer.inventory.currentItem != j) {
                e.getGuiIngame().renderHotbarItem(j, (int)k, (int)l, e.getPartialTicks(), this.mc.thePlayer);
            } else {
                GL11.glPushMatrix();
                GL11.glScaled((double)1.05, (double)1.05, (double)1.0);
                e.getGuiIngame().renderHotbarItem(j, (int)(k - k * 0.05) + 1, (int)(l - l * 0.05) + 1, e.getPartialTicks(), this.mc.thePlayer);
                GL11.glScaled((double)1.0, (double)1.0, (double)1.0);
                GL11.glPopMatrix();
            }
            ++j;
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.mc.thePlayer.getGameProfile().getId());
        int ping = 0;
        if (networkPlayerInfo != null) {
            ping = networkPlayerInfo.getResponseTime();
        }
        double size = 5.0 + Fonts.getFont("rb-b").getWidth("FPS: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Minecraft.debugFPS)) + 5.0 + Fonts.getFont("rb-b").getWidth("Ping: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(ping)) + 5.0;
        this.renderCustomText("X:", 5.0, e.getScaledResolution().getScaledHeight() - 19);
        Fonts.getFont("rb-m").drawString(String.valueOf(Math.round(this.mc.thePlayer.posX)), 5.0 + Fonts.getFont("rb-b").getWidth("X: "), e.getScaledResolution().getScaledHeight() - 19, -5592406);
        this.renderCustomText("Y:", 5.0 + Fonts.getFont("rb-b").getWidth("X: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Math.round(this.mc.thePlayer.posX))) + 5.0, e.getScaledResolution().getScaledHeight() - 19);
        Fonts.getFont("rb-m").drawString(String.valueOf(Math.round(this.mc.thePlayer.posY)), 5.0 + Fonts.getFont("rb-b").getWidth("X: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Math.round(this.mc.thePlayer.posX))) + 5.0 + Fonts.getFont("rb-b").getWidth("Y: "), e.getScaledResolution().getScaledHeight() - 19, -5592406);
        this.renderCustomText("Y:", 5.0 + Fonts.getFont("rb-b").getWidth("X: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Math.round(this.mc.thePlayer.posX))) + 5.0, e.getScaledResolution().getScaledHeight() - 19);
        this.renderCustomText("Z:", 5.0 + Fonts.getFont("rb-b").getWidth("X: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Math.round(this.mc.thePlayer.posX))) + 5.0 + Fonts.getFont("rb-b").getWidth("Y: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Math.round(this.mc.thePlayer.posY))) + 5.0, e.getScaledResolution().getScaledHeight() - 19);
        Fonts.getFont("rb-m").drawString(String.valueOf(Math.round(this.mc.thePlayer.posZ)), 5.0 + Fonts.getFont("rb-b").getWidth("X: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Math.round(this.mc.thePlayer.posX))) + 5.0 + Fonts.getFont("rb-b").getWidth("Y: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Math.round(this.mc.thePlayer.posY))) + 5.0 + Fonts.getFont("rb-b").getWidth("Z: "), e.getScaledResolution().getScaledHeight() - 19, -5592406);
        this.renderCustomText("FPS:", 5.0, e.getScaledResolution().getScaledHeight() - 8);
        Fonts.getFont("rb-m").drawString(String.valueOf(Math.round(Minecraft.debugFPS)), 5.0 + Fonts.getFont("rb-b").getWidth("FPS: "), e.getScaledResolution().getScaledHeight() - 8, -5592406);
        this.renderCustomText("Ping:", 5.0 + Fonts.getFont("rb-b").getWidth("FPS: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Minecraft.debugFPS)) + 5.0, e.getScaledResolution().getScaledHeight() - 8);
        Fonts.getFont("rb-m").drawString(String.valueOf(ping), 5.0 + Fonts.getFont("rb-b").getWidth("FPS: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Minecraft.debugFPS)) + 5.0 + Fonts.getFont("rb-b").getWidth("Ping: "), e.getScaledResolution().getScaledHeight() - 8, -5592406);
        double deltaX = this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX;
        double deltaZ = this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ;
        double bps = (double)Math.round(Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * 100.0 * 20.0) / 100.0;
        this.renderCustomText("BPS:", 5.0 + Fonts.getFont("rb-b").getWidth("FPS: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Minecraft.debugFPS)) + 5.0 + Fonts.getFont("rb-b").getWidth("Ping: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(ping)) + 5.0, e.getScaledResolution().getScaledHeight() - 8);
        Fonts.getFont("rb-m").drawString(String.valueOf(bps), 5.0 + Fonts.getFont("rb-b").getWidth("FPS: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(Minecraft.debugFPS)) + 5.0 + Fonts.getFont("rb-b").getWidth("Ping: ") + Fonts.getFont("rb-m").getWidth(String.valueOf(ping)) + 5.0 + Fonts.getFont("rb-b").getWidth("BPS: "), e.getScaledResolution().getScaledHeight() - 8, -5592406);
    }

    public void renderCustomText(String text, double x, double y) {
        String[] s = text.split("");
        double w = 0.0;
        String[] stringArray = s;
        int n = s.length;
        int n2 = 0;
        while (n2 < n) {
            String t = stringArray[n2];
            Fonts.getFont("rb-b").drawStringWithShadow(t, x + w, y, ColorUtils.getColor(this.textColor, System.nanoTime() / 1000000L, w, 5.0), 0x40000000);
            w += Fonts.getFont("rb-b").getWidth(t);
            ++n2;
        }
    }

    public void renderGradientCircle(double x, double y, double r, int color) {
        GL11.glPushMatrix();
        RenderUtils.start2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)6);
        RenderUtils.color(color);
        GL11.glVertex2d((double)x, (double)y);
        int i = 0;
        while (i <= 360) {
            RenderUtils.color(ColorUtils.getAlphaColor(color, 0));
            GL11.glVertex2d((double)(x + Math.sin((double)i * Math.PI / 180.0) * r), (double)(y - Math.cos((double)i * Math.PI / 180.0) * r));
            i += 10;
        }
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }
}
