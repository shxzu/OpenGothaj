package xyz.cucumber.base.module.feat.visuals;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(category=Category.VISUALS, description="", name="Target ESP")
public class TargetESPModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Circle", "Dotted", "Sims", "Cock", "Rect", "Hitbox"});
    public ColorSettings color = new ColorSettings("Color", "Static", -1, -1, 100);
    public ColorSettings hitColor = new ColorSettings("Hurttime Color", "Static", -65536, -1, 100);
    KillAuraModule ka;
    private double animation;
    private boolean direction;

    public TargetESPModule() {
        this.addSettings(this.mode, this.color, this.hitColor);
    }

    @Override
    public void onEnable() {
        this.ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
    }

    @EventListener
    public void onRender3D(EventRender3D e) {
        this.ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
        if (!this.ka.isEnabled()) {
            return;
        }
        if (this.ka.target == null) {
            return;
        }
        EntityLivingBase entity = this.ka.target;
        GL11.glPushMatrix();
        RenderUtils.start3D();
        GL11.glShadeModel((int)7425);
        double x = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosX;
        double y = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosY;
        double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosZ;
        double size = entity.width / 2.0f;
        AxisAlignedBB bb = new AxisAlignedBB(x - size, y + (double)entity.height, z - size, x + size, y, z + size);
        switch (this.mode.getMode().toLowerCase()) {
            case "circle": {
                if (this.direction) {
                    this.animation -= 0.02;
                    if ((double)(-entity.height / 2.0f) > this.animation) {
                        this.direction = !this.direction;
                    }
                } else {
                    this.animation += 0.02;
                    if ((double)(entity.height / 2.0f) < this.animation) {
                        this.direction = !this.direction;
                    }
                }
                GL11.glBegin((int)8);
                double i = 0.0;
                while (i <= 360.0) {
                    double rX = x + Math.sin(i * Math.PI / 180.0) * (double)entity.width;
                    double rY = y + this.animation;
                    double rZ = z + Math.cos(i * Math.PI / 180.0) * (double)entity.width;
                    RenderUtils.color(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, i, 5.0), ColorUtils.getColor(this.hitColor, System.nanoTime() / 1000000L, i, 5.0), entity.hurtTime, 10.0));
                    GL11.glVertex3d((double)rX, (double)(rY + (double)(entity.height / 2.0f)), (double)rZ);
                    RenderUtils.color(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, i, 5.0), ColorUtils.getColor(this.hitColor, System.nanoTime() / 1000000L, i, 5.0), entity.hurtTime, 10.0), 0.0f);
                    GL11.glVertex3d((double)rX, (double)(rY + (double)(entity.height / 2.0f) - this.animation / (double)entity.height), (double)rZ);
                    i += 10.0;
                }
                GL11.glEnd();
                break;
            }
            case "dotted": {
                double rZ;
                double rY;
                double rX;
                if (this.direction) {
                    this.animation -= 0.02;
                    if ((double)(-entity.height / 2.0f) > this.animation) {
                        this.direction = !this.direction;
                    }
                } else {
                    this.animation += 0.02;
                    if ((double)(entity.height / 2.0f) < this.animation) {
                        this.direction = !this.direction;
                    }
                }
                GL11.glPointSize((float)10.0f);
                GL11.glTranslated((double)x, (double)y, (double)z);
                GL11.glRotatef((float)(((float)this.mc.thePlayer.ticksExisted + e.getPartialTicks()) * 8.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslated((double)(-x), (double)(-y), (double)(-z));
                GL11.glBegin((int)0);
                double i = 0.0;
                while (i <= 360.0) {
                    rX = x + Math.sin(i * Math.PI / 180.0) * (double)entity.width;
                    rY = y + this.animation;
                    rZ = z + Math.cos(i * Math.PI / 180.0) * (double)entity.width;
                    RenderUtils.color(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, i, 5.0), ColorUtils.getColor(this.hitColor, System.nanoTime() / 1000000L, i, 5.0), entity.hurtTime, 10.0));
                    GL11.glVertex3d((double)rX, (double)(rY + (double)(entity.height / 2.0f)), (double)rZ);
                    i += 40.0;
                }
                GL11.glEnd();
                GL11.glPointSize((float)18.0f);
                GL11.glBegin((int)0);
                i = 0.0;
                while (i <= 360.0) {
                    rX = x + Math.sin(i * Math.PI / 180.0) * (double)entity.width;
                    rY = y + this.animation;
                    rZ = z + Math.cos(i * Math.PI / 180.0) * (double)entity.width;
                    RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, i, 5.0), ColorUtils.getColor(this.hitColor, System.nanoTime() / 1000000L, i, 5.0), entity.hurtTime, 10.0), 20));
                    GL11.glVertex3d((double)rX, (double)(rY + (double)(entity.height / 2.0f)), (double)rZ);
                    i += 40.0;
                }
                GL11.glEnd();
                break;
            }
            case "cock": {
                GL11.glPushMatrix();
                GL11.glPopMatrix();
                break;
            }
            case "sims": {
                double rZ;
                double rX;
                GL11.glEnable((int)2929);
                GL11.glRotated((double)z, (double)x, (double)y, (double)size);
                GL11.glBegin((int)6);
                double rY = y + (double)entity.height + 0.5;
                RenderUtils.color(-1, this.color.getAlpha());
                GL11.glVertex3d((double)x, (double)(rY + 0.8), (double)z);
                double i = 0.0;
                while (i <= 360.0) {
                    rX = x + Math.sin(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    rZ = z + Math.cos(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    RenderUtils.color(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, 0.0, 5.0), ColorUtils.getColor(this.hitColor, System.nanoTime() / 1000000L, 0.0, 5.0), entity.hurtTime, 10.0));
                    GL11.glVertex3d((double)rX, (double)(rY + 0.4), (double)rZ);
                    i += 60.0;
                }
                GL11.glEnd();
                GL11.glBegin((int)6);
                GL11.glVertex3d((double)x, (double)rY, (double)z);
                i = 0.0;
                while (i <= 360.0) {
                    rX = x + Math.sin(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    rZ = z + Math.cos(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    GL11.glVertex3d((double)rX, (double)(rY + 0.4), (double)rZ);
                    i += 60.0;
                }
                GL11.glEnd();
                GL11.glDisable((int)2929);
                RenderUtils.color(0x25000000);
                GL11.glBegin((int)2);
                i = 0.0;
                while (i <= 360.0) {
                    rX = x + Math.sin(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    rZ = z + Math.cos(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    GL11.glVertex3d((double)x, (double)rY, (double)z);
                    GL11.glVertex3d((double)rX, (double)(rY + 0.4), (double)rZ);
                    i += 60.0;
                }
                GL11.glEnd();
                GL11.glBegin((int)2);
                i = 0.0;
                while (i <= 360.0) {
                    rX = x + Math.sin(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    rZ = z + Math.cos(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    GL11.glVertex3d((double)x, (double)(rY + 0.8), (double)z);
                    GL11.glVertex3d((double)rX, (double)(rY + 0.4), (double)rZ);
                    i += 60.0;
                }
                GL11.glEnd();
                GL11.glBegin((int)2);
                i = 0.0;
                while (i <= 360.0) {
                    rX = x + Math.sin(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    rZ = z + Math.cos(i * Math.PI / 180.0) * (double)entity.width / 3.0;
                    GL11.glVertex3d((double)rX, (double)(rY + 0.4), (double)rZ);
                    i += 60.0;
                }
                GL11.glEnd();
                break;
            }
            case "rect": {
                RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, 0.0, 5.0), ColorUtils.getColor(this.hitColor, System.nanoTime() / 1000000L, 0.0, 5.0), entity.hurtTime, 10.0), this.color.getAlpha()));
                RenderUtils.renderHitbox(new AxisAlignedBB(x - size, y + (double)entity.height + 0.3, z - size, x + size, y + (double)entity.height + 0.1, z + size), 7);
                break;
            }
            case "hitbox": {
                RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, 0.0, 5.0), ColorUtils.getColor(this.hitColor, System.nanoTime() / 1000000L, 0.0, 5.0), entity.hurtTime, 10.0), this.color.getAlpha()));
                RenderUtils.renderHitbox(bb, 7);
            }
        }
        RenderUtils.stop3D();
        GL11.glPopMatrix();
    }
}
