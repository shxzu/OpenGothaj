package xyz.cucumber.base.module.feat.visuals;

import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.visuals.MurderFinderModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(category=Category.VISUALS, description="Allows you see players behind obsticles", name="ESP3D")
public class ESP3DModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Hitbox", "Circle"});
    public BooleanSettings filled = new BooleanSettings("Filled", true);
    public ColorSettings color = new ColorSettings("Fill Color", "Static", -1, -1, 50);
    public BooleanSettings outline = new BooleanSettings("Outline", true);
    public ColorSettings outlineColor = new ColorSettings("Outline Color", "Static", -1, -1, 100);
    public ColorSettings hurtColor = new ColorSettings("Hit Fill Color", "Static", -1, -1, 100);
    public ColorSettings hurtOutlineColor = new ColorSettings("Hit Outline Color", "Static", -1, -1, 100);
    public ColorSettings murdColor = new ColorSettings("Murder Fill Color", "Static", -1, -1, 100);
    public ColorSettings murdOutlineColor = new ColorSettings("Murder Outline Color", "Static", -1, -1, 100);
    public NumberSettings size = new NumberSettings("Size", 1.0, 0.1, 2.0, 0.05);
    public NumberSettings outlineThick = new NumberSettings("Outline Thickness", 1.0, 0.1, 5.0, 0.05);

    public ESP3DModule() {
        this.addSettings(this.size, this.outlineThick, this.mode, this.filled, this.color, this.outline, this.outlineColor, this.hurtColor, this.hurtOutlineColor, this.murdColor, this.murdOutlineColor);
    }

    @EventListener
    public void onRender3D(EventRender3D e) {
        GL11.glPushMatrix();
        for (EntityPlayer player : this.mc.theWorld.playerEntities) {
            if (!RenderUtils.isInViewFrustrum(player) || player == this.mc.thePlayer && this.mc.gameSettings.thirdPersonView == 0) continue;
            GL11.glPushMatrix();
            double x = player.prevPosX + (player.posX - player.prevPosX) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosX;
            double y = player.prevPosY + (player.posY - player.prevPosY) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosY;
            double z = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosZ;
            switch (this.mode.getMode().toLowerCase()) {
                case "hitbox": {
                    MurderFinderModule mod;
                    int color;
                    double width = (double)player.width * this.size.getValue() / 2.0;
                    AxisAlignedBB bb = new AxisAlignedBB(x - width, y + (double)player.height + (player.isSneaking() ? 0.0 : 0.2), z - width, x + width, y, z + width);
                    GL11.glPushMatrix();
                    double yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * e.getPartialTicks();
                    GL11.glTranslated((double)x, (double)y, (double)z);
                    GL11.glRotated((double)(-yaw), (double)0.0, (double)1.0, (double)0.0);
                    GL11.glTranslated((double)(-x), (double)(-y), (double)(-z));
                    RenderUtils.start2D();
                    if (this.filled.isEnabled()) {
                        color = ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, 0.0, 5.0), ColorUtils.getColor(this.hurtColor, System.nanoTime() / 1000000L, 0.0, 5.0), player.hurtTime, 10.0), this.color.getAlpha());
                        if (Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class).isEnabled()) {
                            mod = (MurderFinderModule)Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class);
                            for (Map.Entry<String, Entity> entry : mod.murders.entrySet()) {
                                if (!entry.getKey().equals(player.getName())) continue;
                                color = ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.murdColor, System.nanoTime() / 1000000L, 0.0, 5.0), ColorUtils.getColor(this.hurtColor, System.nanoTime() / 1000000L, 0.0, 5.0), player.hurtTime, 10.0), this.murdColor.getAlpha());
                                break;
                            }
                        }
                        RenderUtils.color(color);
                        RenderUtils.renderHitbox(bb, 7);
                    }
                    if (this.outline.isEnabled()) {
                        GL11.glLineWidth((float)((float)this.outlineThick.getValue()));
                        color = ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.outlineColor, System.nanoTime() / 1000000L, 0.0, 5.0), ColorUtils.getColor(this.hurtOutlineColor, System.nanoTime() / 1000000L, 0.0, 5.0), player.hurtTime, 10.0), this.outlineColor.getAlpha());
                        if (Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class).isEnabled()) {
                            mod = (MurderFinderModule)Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class);
                            for (Map.Entry<String, Entity> entry : mod.murders.entrySet()) {
                                if (!entry.getKey().equalsIgnoreCase(player.getName())) continue;
                                color = ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.murdOutlineColor, System.nanoTime() / 1000000L, 0.0, 5.0), ColorUtils.getColor(this.hurtColor, System.nanoTime() / 1000000L, 0.0, 5.0), player.hurtTime, 10.0), this.murdOutlineColor.getAlpha());
                                break;
                            }
                        }
                        RenderUtils.color(color);
                        RenderUtils.renderHitbox(bb, 2);
                    }
                    RenderUtils.stop2D();
                    GL11.glPopMatrix();
                    break;
                }
                case "circle": {
                    double width = (double)player.width * this.size.getValue() / 2.0;
                    GL11.glPushMatrix();
                    RenderUtils.start2D();
                    GL11.glShadeModel((int)7425);
                    GL11.glBegin((int)8);
                    double i = 0.0;
                    while (i <= 360.0) {
                        double px = x + Math.sin(i * Math.PI / 180.0) * width;
                        double pz = z - Math.cos(i * Math.PI / 180.0) * width;
                        int color = ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, i, 5.0), ColorUtils.getColor(this.hurtColor, System.nanoTime() / 1000000L, i, 5.0), player.hurtTime, 10.0), this.color.getAlpha());
                        if (Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class).isEnabled()) {
                            MurderFinderModule mod = (MurderFinderModule)Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class);
                            for (Map.Entry<String, Entity> entry : mod.murders.entrySet()) {
                                if (!entry.getKey().equalsIgnoreCase(player.getName())) continue;
                                color = ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.murdColor, System.nanoTime() / 1000000L, 0.0, 5.0), ColorUtils.getColor(this.hurtColor, System.nanoTime() / 1000000L, 0.0, 5.0), player.hurtTime, 10.0), this.murdColor.getAlpha());
                                break;
                            }
                        }
                        RenderUtils.color(color);
                        GL11.glVertex3d((double)px, (double)y, (double)pz);
                        RenderUtils.color(color, 0.0f);
                        GL11.glVertex3d((double)px, (double)(y + 1.0), (double)pz);
                        i += 10.0;
                    }
                    GL11.glEnd();
                    RenderUtils.stop2D();
                    GL11.glPopMatrix();
                }
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
}
